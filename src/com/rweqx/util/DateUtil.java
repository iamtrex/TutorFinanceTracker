package com.rweqx.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static boolean sameDate(LocalDate a, LocalDate b){
        return a.getYear() == b.getYear() && a.getDayOfYear() == b.getDayOfYear();
    }

    public static String getYearMonthDayFromDate(LocalDate d){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
        return (sdf.format(d));
    }


    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
    }

    public static LocalDate dateToLocalDate(Date date) {
        return LocalDate.from(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    public static int getMonthFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }
}
