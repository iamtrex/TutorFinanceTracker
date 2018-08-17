package com.rweqx.model;

import java.util.*;

public class StudentsModel {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public StudentsModel(){
        students = new ArrayList<>();

        //TODO Fake Students: Will read from save in the future...
        Map<Integer, Double> payMap = new HashMap<>();
        payMap.put(1, 75.0);
        payMap.put(2, 50.0);
        payMap.put(3, 40.0);
        payMap.put(4, 25.0);

        students.add(new Student(1, "Rex", payMap));
        students.add(new Student(2, "Howard Lin", payMap));
        students.add(new Student(3, "Sammy", payMap));
        students.add(new Student(4, "Joshua Pak", payMap));
        students.add(new Student(20, "Howard Lin", payMap));
        students.add(new Student(30, "Sammy v2", payMap));
        students.add(new Student(10, "Rex v2", payMap));
        students.add(new Student(300, "Sammy is a pig", payMap));


    }

    public Student getStudentByID(int id) {
        Optional<Student> result = students.stream().filter(s->s.getID() == id).findFirst();

        return result.get();
    }

    //TODO REMOVE SINCE BAD FOR REPEAT NAMES...
    public Student getStudentByName(String studentName) {
        Optional<Student> result = students.stream().filter(s->s.getName().equals(studentName)).findFirst();

        return result.get();
    }

    public List<Student> getAllStudents() {
        return students;
    }
}
