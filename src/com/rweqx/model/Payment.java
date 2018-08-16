package com.rweqx.model;

import java.util.Date;

/**
 * While saving student name is redundant, it makes it easier for someone to read logs in cases where that is needed. Thus we are keeping it.
 *
 */
public class Payment extends Event{

    private double value;

    public Student getStudent() {
        return student;
    }

    private Student student;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public Payment(long eventID, Student student, double value, Date date){
        super(eventID, date);
        this.value = value;
        this.student = student;

    }

}
