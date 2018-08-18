package com.rweqx.model;

import java.time.LocalDate;

public class Payment extends Event{

    public Payment(long eventID, LocalDate date, String paymentType, double paymentAmount) {
        super(eventID, date);
    }
}
