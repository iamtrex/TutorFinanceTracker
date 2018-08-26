package com.rweqx.managers;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.util.DateUtil;
import javafx.beans.property.LongProperty;

import java.time.LocalDate;
import java.util.*;

public class PaymentManager {

    private List<Payment> payments;
    private Map<Long, Payment> paymentMap;

    public PaymentManager(){
        payments = new ArrayList<>();
        paymentMap = new HashMap<>();
    }

    public List<Payment> getAllPayments(){
        return payments;
    }

    public void deletePayment(long paymentID){
        Payment p = paymentMap.get(paymentID);
        if(p == null){
            Logger.getInstance().log("Cannot find payment with ID " + paymentID + " when trying to remove", LogLevel.S);
            return;
        }
        payments.remove(p);
        paymentMap.remove(p);
    }

    public void addPayments(List<Payment> newPayments) {
        newPayments.forEach(this::addPayment);
    }

    public void addPayment(Payment p){
        this.payments.add(p);
        this.paymentMap.put(p.getID(), p);
    }

    public Payment getPaymentByID(long paymentID){
        Payment p = paymentMap.get(paymentID);
        if(p== null){
            Logger.getInstance().log("Cannot find payment with ID " + paymentID + " it doesn't exist.", LogLevel.S);

        }
        return p;
    }

    public List<Payment> getPaymentsOnDate(LocalDate date) {
        List<Payment> todayPayments = new ArrayList<>();
        for(Payment p : payments){
            LocalDate d = p.getDate();
            if(DateUtil.sameDate(d, date))
                todayPayments.add(p);
        }
        return todayPayments;
    }


    public List<Payment> getAllPaymentsBy(long id) {
        List<Payment> allPayments = new ArrayList<>();
        for(Payment p : payments){
            if(p.getStudentID() == id){
                allPayments.add(p);
            }
        }
        return allPayments;
    }

    public List<Payment> getAllPaymentsByInYearMonth(long id, int year, int month) {
        List<Payment> allPayments = new ArrayList<>();
        for(Payment p : payments){
            if(p.getStudentID() == id){
                if(month == p.getDate().getMonthValue() && year == p.getDate().getYear()) {
                    allPayments.add(p);
                }
            }
        }
        return allPayments;
    }

    public void removePaymentByID(long pid) {
        Payment p = paymentMap.get(pid);
        payments.remove(p);
        paymentMap.remove(pid);
    }

    public List<Payment> getAllPaymentsOnDate(LocalDate date) {
        List<Payment> allPayments = new ArrayList<>();
        for(Payment p : payments){
            if(DateUtil.sameDate(date, p.getDate())){
               allPayments.add(p);
            }
        }
        return allPayments;
    }

    public List<Payment> getAllPaymentsByStudentBetween(long studentID, LocalDate startDate, LocalDate endDate) {
        List<Payment> allPayments = new ArrayList<>();
        for(Payment p : payments){
            if(p.getStudentID() == studentID && DateUtil.isBetween(p.getDate(), startDate, endDate)){
                allPayments.add(p);
            }
        }
        return allPayments;
    }
}
