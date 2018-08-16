package com.rweqx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StudentsModel {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public StudentsModel(){
        students = new ArrayList<>();

        //TODO Fake Students: Will read from save in the future...
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(4, "Joshua Pak"));
        students.add(new Student(20, "Howard Lin"));
        students.add(new Student(30, "Sammy v2"));
        students.add(new Student(10, "Rex v2"));
        students.add(new Student(300, "Sammy is a pig"));


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
