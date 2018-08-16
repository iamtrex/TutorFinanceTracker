package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.util.DateUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

public class PaymentModel {
    private Set<Payment> payments;

    public PaymentModel() {
        payments = new HashSet<>();
    }

    public Set<Payment> getAllPayments() {
        return payments;
    }

    public Payment createPayment(long pid, Student student, Double paid, Date date) {
        Payment payment = new Payment(pid, student, paid, date);
        if (payments.contains(payment)) {
            Logger.getInstance().log("ALREADY HAVE PAYMENT ERROR!!!", LogLevel.S);
        }
        payments.add(payment);
        return payment;
    }

    public void addPayments(Set<Payment> payments) {
        this.payments.addAll(payments);
    }

    public List<Payment> getPaymentsOnDate(Date date) {
        List<Payment> todayPayments = new ArrayList<>();

        for(Payment p : payments){
            Date d = p.getDate();
            if(DateUtil.sameDate(d, date))
                todayPayments.add(p);

        }
        return todayPayments;
    }

    public Double getPaymentByID(long pid) {
        if(pid == -1){
            return 0.0;
        }
        for(Payment p : payments){
            if(p.getEventID() == pid){
                return p.getValue();
            }
        }
        return 0.0;
    }
}
