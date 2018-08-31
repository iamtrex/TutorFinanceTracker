package com.rweqx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {

    private final Double DELTA = 1e-6;

    @BeforeEach
    void setUp() {


    }

    @Test
    void getLatestPaymentRates() {
        Map<String, Double> rates1 = new HashMap<>();
        rates1.put("1 on 1", 75.0);
        rates1.put("1 on 2", 45.0);

        PaymentRatesAtTime prat1 = new PaymentRatesAtTime(LocalDate.now().minus(2, ChronoUnit.DAYS), rates1);

        Map<String, Double> rates2 = new HashMap<>();
        rates2.put("1 on 1", 75.0);
        rates2.put("1 on 2", 35.0);
        rates2.put("Group", 25.0);
        PaymentRatesAtTime prat2 = new PaymentRatesAtTime(LocalDate.now(), rates2);


        List<PaymentRatesAtTime> prats = new ArrayList<>(List.of(prat1, prat2));

        Student sRex = new Student(1, "Rex Lin", LocalDate.now(), "", prats, null);

        assertEquals(35.0, sRex.getLatestPaymentRates().getRateByType("1 on 2"), DELTA);
        assertEquals(75.0,sRex.getLatestPaymentRates().getRateByType("1 on 1"),  DELTA);


        assertEquals(45.0,sRex.getPaymentRateAtTime(LocalDate.now().minus(1, ChronoUnit.DAYS), "1 on 2"),  DELTA);
        assertEquals(75.0,sRex.getPaymentRateAtTime(LocalDate.now().minus(1, ChronoUnit.DAYS), "1 on 1"),  DELTA);

        assertEquals(35.0,sRex.getPaymentRateAtTime(LocalDate.now().plus(1, ChronoUnit.DAYS), "1 on 2"),  DELTA);
        assertEquals(75.0,sRex.getPaymentRateAtTime(LocalDate.now().plus(1, ChronoUnit.DAYS), "1 on 1"),  DELTA);
        assertEquals(25.0,sRex.getPaymentRateAtTime(LocalDate.now().plus(1, ChronoUnit.DAYS), "Group"),  DELTA);


        assertEquals(0,sRex.getPaymentRateAtTime(LocalDate.now().minus(1, ChronoUnit.DAYS), "Group"),  DELTA);
        assertEquals(0,sRex.getPaymentRateAtTime(LocalDate.now().minus(3, ChronoUnit.DAYS), "1 on 1"),  DELTA);





    }

    @Test
    void getPaymentRateAtTime() {
    }


    @Test
    void addPaymentRates() {
    }
}