package com.rweqx.model;

import java.time.LocalDate;

public class Payment extends Event{

    private long studentID;
    private String paymentType;
    private double paymentAmount;

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    public Payment(long eventID, long studentID, LocalDate date, String paymentType, double paymentAmount) {
        super(eventID, date);
        this.studentID = studentID;
        this.paymentAmount = paymentAmount;
        this.paymentType = paymentType;

    }

}
