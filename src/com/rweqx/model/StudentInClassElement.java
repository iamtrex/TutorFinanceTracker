package com.rweqx.model;

public class StudentInClassElement {

    private String studentName;

    private Double duration, paid;

    public StudentInClassElement(String studentName, Double duration, Double paid){
        this.studentName = studentName;
        this.duration = duration;
        this.paid = paid;

    }

    public String getStudentName() {
        return studentName;
    }

    public Double getPaid() {
        return paid;
    }

    public Double getDuration() {
        return duration;
    }

}
