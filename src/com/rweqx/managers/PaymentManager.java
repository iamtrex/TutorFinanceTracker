package com.rweqx.managers;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.util.DateUtil;

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

    public List<Payment> getAllPaymentsByInMonth(long id, int month) {
        List<Payment> allPayments = new ArrayList<>();
        for(Payment p : payments){
            if(p.getStudentID() == id){
                if(month == p.getDate().getMonthValue()) {
                    allPayments.add(p);
                }
            }
        }
        return allPayments;
    }

    public void removePaymentByID(long pid) {
        Payment p = paymentMap.get(pid);

    }
}
