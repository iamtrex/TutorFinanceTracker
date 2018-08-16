package com.rweqx.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static boolean sameDate(Date a, Date b){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(a);
        cal2.setTime(b);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static String getYearMonthDayFromDate(Date d){

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
        return (sdf.format(d));
    }


}
