package com.rweqx.managers;

import com.rweqx.constants.Constants;
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
        List<Class> classes = saveReader.readClassFromJson(Constants.SAVE_FOLDER + Constants.CLASS_SAVE_FILE);
        classManager.addClasses(classes);

        List<Payment> payments = saveReader.readPaymentFromJson(Constants.SAVE_FOLDER + Constants.PAYMENT_SAVE_FILE);
        paymentManager.addPayments(payments);

        //Read students...  //TODO REMOVE TEMP CODE.
        Pair<String, Double> a = new Pair<>("1 on 1", 75.0);
        Pair<String, Double> b = new Pair<>("Group", 25.0);
        Pair<String, Double> c = new Pair<>("1 on 2", 75.0);
        Pair<String, Double> d = new Pair<>("1 on 3", 75.0);
        List<Pair<String, Double>> list = new ArrayList<>(List.of(a, b, c, d));


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

    public long addPayment(Student student, LocalDate date, String paymentType, double paid) {
        long id = getNewID();
        Payment p = new Payment(id, student.getID(), date, paymentType, paid);
        paymentManager.addPayment(p);
        return id;
    }

}
