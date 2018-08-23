package com.rweqx.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PaymentRatesAtTime {
    private LocalDate date;

    private Map<String, Double> rates;

    public PaymentRatesAtTime(LocalDate date, Map<String, Double> rates){
        this.date = date;
        this.rates = rates;

    }

    public PaymentRatesAtTime(PaymentRatesAtTime latestPaymentRates) {
        this.date = latestPaymentRates.getDate();
        this.rates = new HashMap<>();
        latestPaymentRates.getRates().forEach((s, d) -> rates.put(s, d));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public void addChangePayment(String paymentType, double rate) {
        rates.put(paymentType, rate); //Overwrites or adds entry for the corresponding payment type.
    }

    public Double getRateByType(String newVal) {
        return rates.get(newVal);
    }
}
