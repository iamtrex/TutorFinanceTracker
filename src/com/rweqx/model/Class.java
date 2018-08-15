package com.rweqx.model;

import java.util.*;

public class Class {

    private long classID;

    private Set<Student> students;
    private Map<Student, Double> studentPaid;
    private Map<Student, Double> studentDuration;

    private Date date;
    private String classType;

    public Class(long classID, String classType, Date date){
        this.classID = classID;
        this.classType = classType;
        this.date = date;

        students = new HashSet<>();
        studentPaid = new HashMap<>();
        studentDuration = new HashMap<>();

    }

    public void addStudent(Student student, double duration, double paid){
        students.add(student);
        studentDuration.put(student, duration);
        studentPaid.put(student, paid);
    }

    public Date getDate(){
        return date;
    }

    public String getClassType(){
        return classType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return classID == aClass.classID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID);
    }

    @Override
    public String toString() {
        return "Class{" +
                "classID=" + classID +
                ", students=" + students.toString() +
                ", studentPaid=" + studentPaid +
                ", studentDuration=" + studentDuration +
                ", date=" + date +
                ", classType='" + classType + '\'' +
                '}';
    }
}
