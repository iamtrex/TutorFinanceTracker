package com.rweqx.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Student {



    private long studentID;
    private String name;
    private List<PaymentRatesAtTime> paymentRates;
    private String comment;

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return studentID;
    }

    public void setID(long studentID) {
        this.studentID = studentID;
    }

    public PaymentRatesAtTime getLatestPaymentRates(){
        Collections.sort(paymentRates, Comparator.comparing(PaymentRatesAtTime::getDate));
        return paymentRates.get(paymentRates.size()-1); //Return latest.

    }
    public void modifyPaymentRate(LocalDate date, String paymentType, double rate){
        PaymentRatesAtTime newRates = new PaymentRatesAtTime(getLatestPaymentRates());
        newRates.addChangePayment(paymentType, rate);
        newRates.setDate(date);
        paymentRates.add(newRates);

    }


    public List<PaymentRatesAtTime> getPaymentRates() {
        return paymentRates;
    }

    public void setPaymentRates(List<PaymentRatesAtTime> paymentRates) {
        this.paymentRates = paymentRates;
    }

    public Student(long studentID, String name, String comment, List<PaymentRatesAtTime> paymentRates){
        this.studentID = studentID;
        this.name = name;
        this.paymentRates = paymentRates;
    }



}
