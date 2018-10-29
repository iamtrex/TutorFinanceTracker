package com.rweqx.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtil {
    public static boolean sameDate(LocalDate a, LocalDate b){
        return a.getYear() == b.getYear() && a.getDayOfYear() == b.getDayOfYear();
    }

    public static String getYearMonthDayFromDate(LocalDate d){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("EEE MMM dd yyyy");
        return d.format(df);
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

    public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return sameOrBefore(date, endDate) && sameOrAfter(date, startDate);
    }

    public static boolean sameOrBefore(LocalDate date, LocalDate laterDate){
        return date.isBefore(laterDate) || (date.getYear() == laterDate.getYear() && date.getDayOfYear() == laterDate.getDayOfYear());
    }
    public static boolean sameOrAfter(LocalDate date, LocalDate earlierDate){
        return sameOrBefore(earlierDate, date);
    }

    public static LocalDate endOfMonth(int year, int month) {
        return  LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());

    }

    public static LocalDate startOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static String getNameFromCurrentDate(LocalDateTime now) {
        return now.getYear() + "_" + now.getMonthValue() + "_" + now.getDayOfMonth() + "_" + now.getHour() +
                "_" + now.getMinute() + "_" + now.getSecond() + "_" + now.getNano();
    }
}
