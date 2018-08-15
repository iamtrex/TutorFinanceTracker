package com.rweqx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentsModel {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public StudentsModel(){
        students = new ArrayList<>();

        //Fake Students:
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(4, "Joshua Pak"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));
        students.add(new Student(1, "Rex"));
        students.add(new Student(2, "Howard Lin"));
        students.add(new Student(3, "Sammy"));


    }

    public Student getStudentByID(int id) {
        Optional<Student> result = students.stream().filter(s->s.getID() == id).findFirst();

        return result.get();
    }

    public Student getStudentByName(String studentName) {
        Optional<Student> result = students.stream().filter(s->s.getName().equals(studentName)).findFirst();

        return result.get();
    }
}
