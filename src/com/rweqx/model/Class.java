package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.util.*;

/**
 * While saving student name is redundant, it makes it easier for someone to read logs in cases where that is needed. Thus we are keeping it.
 *
 */
public class Class extends Event{


    private String classType;
    private Map<Integer, Double> durationMap;
    private Map<Integer, Long> paymentMap;

    private Set<Student> students;


    public Class(long eventID, String classType, Date date){
        super(eventID, date);
        this.classType = classType;
        students = new HashSet<>();
        durationMap = new HashMap<>();
        paymentMap = new HashMap<>();

    }

    public void addPayment(Student s, long pid){
        if(!students.contains(s)){
            Logger.getInstance().log("Student " + s + " tried to pay, but wasn't in this class", LogLevel.W);
        }else{
            paymentMap.put(s.getID(), pid);
        }
    }

    public void addStudent(Student s, double duration){
        students.add(s);
        durationMap.put(s.getID(), duration);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Double getDurationFromStudent(int sid){
        return durationMap.get(sid);
    }

    public long getPaidIDFromStudent(int sid){
        return paymentMap.get(sid) == null ? -1 : paymentMap.get(sid);
    }

    public String getClassType(){
        return classType;
    }

}
