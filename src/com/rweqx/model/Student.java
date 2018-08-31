package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Student {



    private LocalDate date;
    private long studentID;
    private String name;
    private List<PaymentRatesAtTime> paymentRates;
    private String comment;
    private List<String> groups;
    public List<String> getGroups(){
        return groups;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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

    public PaymentRatesAtTime getLatestPaymentRates(){
        Collections.sort(paymentRates, Comparator.comparing(PaymentRatesAtTime::getDate));
        return paymentRates.get(paymentRates.size()-1); //Return latest.
    }

    public double getPaymentRateAtTime(LocalDate date, String classType) {
        Collections.sort(paymentRates, Comparator.comparing(PaymentRatesAtTime::getDate));

        if(date.isBefore(paymentRates.get(0).getDate())){
            Logger.getInstance().log("Error - Rate was unset at this time, could not search for it. Returning 0 for now. ", LogLevel.W);

            return 0; //Rate was unset at this time
        }

        for(int i=0; i<paymentRates.size()-1; i++){
            if(paymentRates.get(i+1).getDate().isAfter(date)){
                Double value = paymentRates.get(i).getRateByType(classType);
                if(value == null){
                    Logger.getInstance().log("Error - Could not find this classType during this time... " +
                            "Returning a rate of 0 instead. ", LogLevel.W);
                    return 0;
                }
                return value;
            }
        }
        return paymentRates.get(paymentRates.size()-1).getRateByType(classType);
    }

    /* //Removed cuz bad. Should modify by addPaymentRate
    public void modifyPaymentRate(LocalDate date, String paymentType, double rate){
        PaymentRatesAtTime newRates = new PaymentRatesAtTime(getLatestPaymentRates());
        newRates.addChangePayment(paymentType, rate);
        newRates.setDate(date);
        paymentRates.add(newRates);

    }*/

    public List<PaymentRatesAtTime> getPaymentRates() {
        return paymentRates;
    }

    public void setPaymentRates(List<PaymentRatesAtTime> paymentRates) {
        this.paymentRates = paymentRates;
    }

    public Student(long studentID, String name, LocalDate date, String comment, List<PaymentRatesAtTime> paymentRates, List<String> groups){
        this.date = date;
        this.studentID = studentID;
        this.name = name;
        this.paymentRates = paymentRates;
        this.groups = groups;
    }


    public void addPaymentRates(PaymentRatesAtTime prat) {
        paymentRates.add(prat);
    }

}
