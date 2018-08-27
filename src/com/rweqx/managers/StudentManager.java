package com.rweqx.managers;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.Payment;
import com.rweqx.model.PaymentRatesAtTime;
import com.rweqx.model.Student;

import java.time.LocalDate;
import java.util.*;

public class StudentManager {
    private List<Student> students;

    private Map<Long, Student> studentIDMap;

    private Random idGenerator;

    public StudentManager(){
        idGenerator = new Random();
        students = new ArrayList<>();
        studentIDMap = new HashMap<>();

        /*
        //TEMP CODE, REMOVE LATER.
        Map<String, Double> classRates = new HashMap<>();
        classRates.put("1 on 1", 75.0);
        classRates.put("1 on 2", 35.0);
        classRates.put("1 on 3", 30.0);
        classRates.put("Group", 25.0);

        List<PaymentRatesAtTime> prat = List.of(new PaymentRatesAtTime(LocalDate.now(), classRates));
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Rex", prat));
        students.add(new Student(2, "Howard Lin", prat));
        students.add(new Student(3, "Sammy", prat));
        students.add(new Student(4, "Joshua Pak", prat));
        students.add(new Student(20, "Howard Lin", prat));
        students.add(new Student(10, "Rex v2", prat));
        students.add(new Student(300, "Sammy is a pig", prat));
        addStudents(students);*/
    }

    public void addStudents(List<Student> newStudents){
        newStudents.forEach(this::addStudent);
    }

    public void addStudent(Student newStudent){
        students.add(newStudent);
        studentIDMap.put(newStudent.getID(), newStudent);
        System.out.println("Created new student " + newStudent.getID() + ": " + newStudent.getName());
    }
    public Student getStudentByID(long id) {
        if(studentIDMap.get(id) != null){
            return studentIDMap.get(id);
        }
        Logger.getInstance().log("Cannot find student with ID " + id, LogLevel.S);
        return null;
    }


    public List<Student> getStudents() {
        return students;
    }

    public void createAndAddStudent(String name, String comment, PaymentRatesAtTime rates) {
        //addStudent(new Student(getNewID(), name, List.of(rates)));
        LocalDate registeredDate = LocalDate.now();
        List<PaymentRatesAtTime> list = new ArrayList<>();
        list.add(rates);
        addStudent(new Student(getNewID(), name, registeredDate, comment, list));
    }

    private long getNewID() {
        long l = idGenerator.nextLong();

        while(studentIDMap.containsKey(l)){
            l = idGenerator.nextLong();
        }
        return l;
    }
}
