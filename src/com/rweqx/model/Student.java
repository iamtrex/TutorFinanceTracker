package com.rweqx.model;

import java.util.Map;

public class Student {
    private String name;
    private int id;
    private Map<Integer, Double> classFee;

    public Student(int id, String name, Map<Integer, Double> classFee){
        this.name = name;
        this.id = id;
        this.classFee = classFee;

    }

    public Double getClassFeeByType(int i){
        return classFee.get(i) == null ? 0.0 : classFee.get(i);

    }
    public Map<Integer, Double> getClassFee(){
        return classFee;
    }

    public String getName(){
        return name;
    }


    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
