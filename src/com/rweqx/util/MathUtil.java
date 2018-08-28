package com.rweqx.util;

public class MathUtil {

    public static double round(double value, int digits){
        return (Math.round(value * Math.pow(10, digits)))/Math.pow(10, digits);
    }
}
