package com.rweqx.util;

import java.text.DecimalFormat;

public class MathUtil {

    public static double round(double value, int digits){
        return (Math.round(value * Math.pow(10, digits)))/Math.pow(10, digits);
    }

    public static String dollarValueFrom(double amount) {
        DecimalFormat format;
        if(Math.floor(amount) == amount){
            format = new DecimalFormat("#");
        }else {
            format = new DecimalFormat("#.00");
        }
        return format.format(amount);
    }
}
