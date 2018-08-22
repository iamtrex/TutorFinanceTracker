package com.rweqx.managers;

import com.rweqx.constants.Constants;
import com.rweqx.io.FileBackup;
import com.rweqx.io.JSONReader;
import com.rweqx.io.JSONWriter;
import com.rweqx.model.*;
import com.rweqx.model.Class;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelManager {

    private Random idGenerator;

    private ClassTypes classTypes;
    private PaymentTypes paymentTypes;
    private StudentManager studentManager;
    private ClassManager classManager;
    private PaymentManager paymentManager;

    private JSONReader saveReader;
    private JSONWriter saveWriter;

    public ModelManager() {
        saveReader = new JSONReader();
        saveWriter = new JSONWriter();
        classTypes = new ClassTypes();
        paymentTypes = new PaymentTypes();
        studentManager = new StudentManager();
        classManager = new ClassManager(this);
        paymentManager = new PaymentManager();

        idGenerator = new Random();
        loadSavedData();

    }

    private void loadSavedData() {
        //Before loading, always back up if latest file today...
        FileBackup.backupFile(Constants.SAVE_FOLDER + Constants.CLASS_SAVE_FILE);
        FileBackup.backupFile(Constants.SAVE_FOLDER + Constants.PAYMENT_SAVE_FILE);
        FileBackup.backupFile(Constants.SAVE_FOLDER + Constants.STUDENT_SAVE_FILE);

        List<Class> classes = saveReader.readClassFromJson(Constants.SAVE_FOLDER + Constants.CLASS_SAVE_FILE);
        classManager.addClasses(classes);

        List<Payment> payments = saveReader.readPaymentFromJson(Constants.SAVE_FOLDER + Constants.PAYMENT_SAVE_FILE);
        paymentManager.addPayments(payments);

        List<Student> students = saveReader.readStudentFromJson(Constants.SAVE_FOLDER + Constants.STUDENT_SAVE_FILE);
        studentManager.addStudents(students);

    }

    /**
     * Saves all data on program close.
     */
    public void saveAll() {
        saveWriter.writeClassesToFile(classManager.getAllClasses(), Constants.CLASS_SAVE_FILE);
        saveWriter.writePaymentToFile(paymentManager.getAllPayments(), Constants.PAYMENT_SAVE_FILE);
        saveWriter.writeStudentsToFile(studentManager.getStudents(), Constants.STUDENT_SAVE_FILE);

    }


    public ClassTypes getClassTypes() {
        return classTypes;
    }

    public PaymentTypes getPaymentTypes() {
        return paymentTypes;
    }


    public StudentManager getStudentManager() {
        return studentManager;
    }


    public ClassManager getClassManager() {
        return classManager;
    }

    public PaymentManager getPaymentManager() {
        return paymentManager;
    }

    public List<Event> getAllEventsByStudent(Student currentStudent) {
        List<Event> events = new ArrayList<>();
        events.addAll(classManager.getAllClassesBy(currentStudent.getID()));
        events.addAll(paymentManager.getAllPaymentsBy(currentStudent.getID()));
        return events;
    }

    public List<Event> getAllEventsByStudentInMonth(Student currentStudent, int month) {
        List<Event> events = new ArrayList<>();
        events.addAll(classManager.getAllClassesByInMonth(currentStudent.getID(), month));
        events.addAll(paymentManager.getAllPaymentsByInMonth(currentStudent.getID(), month));
        return events;
    }

    public List<Event> getAllEventsOnDate(LocalDate date) {
        List<Event> events = new ArrayList<>();
        events.addAll(classManager.getAllClassesOnDate(date));
        events.addAll(paymentManager.getAllPaymentsOnDate(date));
        return events;
    }
    public double getAllEventsByStudentOutstanding(Student currentStudent) {
        return 0.0;
    }

    public Class createAndAddEmptyClass(LocalDate date, String classType) {
        Class c = new Class(getNewID(), date, classType);
        classManager.addClass(c);
        return c;
    }

    public void addStuDurPaidToClass(Class c, Student s, Double duration, Double paymentAmount, String paymentType){
        StuDurPaid sdp;
        if(paymentAmount == 0){
            sdp = new StuDurPaid(s.getID(), duration, -1);
        }else {
            Payment p = new Payment(getNewID(), s.getID(), c.getDate(), paymentType, paymentAmount, c);
            paymentManager.addPayment(p);
            sdp = new StuDurPaid(s.getID(), duration, p.getID());
        }
        c.addStudent(sdp);
    }

    public long createAndAddClass(LocalDate date, String classType, List<StuDurPaid> sdp) {
        Class c = new Class(getNewID(), date, classType);

        sdp.forEach(s->{
           c.addStudent(s);
        });

        classManager.addClass(c);
        return c.getID();
    }

    private long getNewID() {
        long l = idGenerator.nextLong();

        while(classManager.getAllClasses().contains(new Class(l, null, null))
                || paymentManager.getAllPayments().contains(new Payment(l, -1, null, "", 0))
                || l == -1){ //If ID is already used.
            l = idGenerator.nextLong();
        }

        return l;
    }

    public long replacePayment(long replaced, Student student, LocalDate date, String paymentType, double paid){
        long id = getNewID();

        Class c = paymentManager.getPaymentByID(replaced).getLinkedClass();
        if(c != null) {
            Payment p = new Payment(id, student.getID(), date, paymentType, paid, c);
            paymentManager.addPayment(p);
            paymentManager.deletePayment(replaced);
            c.replacePayment(replaced, id);
        }else{
            Payment p = new Payment(id, student.getID(), date, paymentType, paid);
            paymentManager.addPayment(p);
        }
        return id;
    }

    public long addPayment(Student student, LocalDate date, String paymentType, double paid) {
        long id = getNewID();
        Payment p = new Payment(id, student.getID(), date, paymentType, paid);
        paymentManager.addPayment(p);
        return id;
    }

    public void deletePayment(long id) {
        Payment p = paymentManager.getPaymentByID(id);
        Class c = p.getLinkedClass();
        if(c != null){
            c.removePayment(p.getID());
        }

        paymentManager.deletePayment(id);
    }

    public void createAndAddStudent(String studentName, PaymentRatesAtTime prat) {
        studentManager.createAndAddStudent(studentName, prat);
    }
}
