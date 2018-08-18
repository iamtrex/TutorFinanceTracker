package com.rweqx.model;

import java.util.Map;

public class Student {
    public long getID() {
        return studentID;
    }

    public void setID(long studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getClassRates() {
        return classRates;
    }

    public void setClassRates(Map classRates) {
        this.classRates = classRates;
    }

    public void addClassRate(String classType, double rate){
        classRates.put(classType, rate);
    }

    private long studentID;
    private String name;
    private Map classRates;

    public Student(long studentID, String name, Map<String, Double> classRates){
        this.studentID = studentID;
        this.name = name;
        this.classRates = classRates;
    }



}
