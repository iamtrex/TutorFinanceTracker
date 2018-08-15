package com.rweqx.model;

import java.util.Date;

public class Payment {

    private int studentID;
    private double value;
    private Date date;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payment(int studentID, double value, Date date){
        this.studentID = studentID;
        this.value = value;
        this.date = date;
    }

}
