package com.rweqx.model;

import com.rweqx.constants.Constants;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.util.DateUtil;

import java.util.*;
import java.util.stream.Collectors;

public class DataModel {


    private ClassManager classManager;
    private PaymentModel paymentModel;
    private StudentsModel studentsModel;
    private ClassTypes classTypeManager;



    private JSONWriter saveWriter;
    private JSONReader saveReader;

    public ClassTypes getClassTypeManager() {
        return classTypeManager;
    }
    public StudentsModel getStudentsModel() {
        return studentsModel;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    private Random idGenerator;

    public DataModel(){
        idGenerator = new Random();
        classTypeManager = new ClassTypes();
        List<String> types = new ArrayList(List.of("1 on 1", "Group", "1 on 2", "1 on 3"));
        //TODO READ FROM FILE.
        classTypeManager.setClassTypes(types);

        classManager = new ClassManager(this);
        paymentModel = new PaymentModel();
        studentsModel = new StudentsModel();
        saveWriter = new JSONWriter();
        saveReader = new JSONReader();
        readSaves();

    }

    private void readSaves() {
        Set<Class> classes = saveReader.readClassFromJson(Constants.SAVE_FOLDER + Constants.CLASS_SAVE_FILE);
        classManager.addClasses(classes);

        Set<Payment> payments = saveReader.readPaymentFromJson(Constants.SAVE_FOLDER + Constants.PAYMENT_SAVE_FILE);
        paymentModel.addPayments(payments);

    }

    public void saveAll() {
        saveWriter.writeClassesToFile(classManager.getAllClasses(), Constants.CLASS_SAVE_FILE);
        saveWriter.writePaymentToFile(paymentModel.getAllPayments(), Constants.PAYMENT_SAVE_FILE);
        saveWriter.writeStudentsToFile(studentsModel.getAllStudents(), Constants.STUDENT_SAVE_FILE);
    }

    public long createAndAddClass(String classType, Date date, List<StudentInClassElement> studentElts){

        long id = getNewID();

        Class cl = new Class(id, classType, date);
        classManager.addClass(cl);

        //ADD ALL STUDENTS AND PAYMENTS TO CLASS.
        for(StudentInClassElement stuElt : studentElts){
            Student stu = studentsModel.getStudentByName(stuElt.getStudentName());
            cl.addStudent(stu, stuElt.getDuration()); //Must add class before adding payment.

            if(stuElt.getPaid() != 0){ //TODO TECHNICALLY :/
                //Create Payment
                long pid = getNewID();
                Payment p = paymentModel.createPayment(pid, stu, stuElt.getPaid(), date);
                cl.addPayment(stu, pid);
            }
        }

        Logger.getInstance().log("Added new class" + cl.toString() , LogLevel.D);

        return id;
    }


    private long getNewID() {
        long l = idGenerator.nextLong();

        while(classManager.getAllClasses().contains(new Class(l, null, null))
                || paymentModel.getAllPayments().contains(new Payment(l, null, -1, null))
                || l == -1){ //If ID is already used.
            l = idGenerator.nextLong();
        }

        return l;
    }

    public List<Event> getAllEventsOnDate(Date date) {
        List<Event> events = new ArrayList<>();
        events.addAll(classManager.getClassesOnDate(date));
        events.addAll(paymentModel.getPaymentsOnDate(date));


        return events;

    }

    public long createAndAddPayment(Student student, Date date, double amount) {
        long pid = getNewID();
        paymentModel.createPayment(pid, student, amount, date);
        return pid;
    }

    public List<Event> getAllEventsByStudent(Student currentStudent) {
        List<Event> allEvents = new ArrayList<>();
        allEvents.addAll(classManager.getAllClassesBy(currentStudent.getID()));
        allEvents.addAll(paymentModel.getAllPaymentsBy(currentStudent.getID()));
        return allEvents;
    }

    public List<Event> getAllEventsByStudentInMonth(Student currentStudent, int month) {
        List<Event> allEvents = getAllEventsByStudent(currentStudent);
        List<Event> monthEvents = allEvents.stream()
                .filter(e -> month == DateUtil.getMonthFromDate(e.getDate()))
                .collect(Collectors.toList());
        return monthEvents;
    }

    public double getAllEventsByStudentOutstanding(Student currentStudent) {
        double allTimePaid = paymentModel.getTotalPaidBy(currentStudent.getID());
        double allTimeCost = classManager.getTotalOwedBy(currentStudent);

        System.out.println("Studnet " + currentStudent.getName() + " has costs : " + allTimeCost + " of which they have paid" +
                allTimePaid + " of which outstanding  " + (allTimeCost - allTimePaid));

        return allTimeCost-allTimePaid;
    }
}
