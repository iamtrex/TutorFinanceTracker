package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Class extends Event{

    private Set<StuDurPaid> studentsInfoSet;

    public Class(long eventID, LocalDate date) {
        super(eventID, date);
        studentsInfoSet = new HashSet<>();

    }

    public Set<Long> getStudents(){
        Set<Long> students = new HashSet<>();
        for(StuDurPaid sdp : studentsInfoSet){
            students.add(sdp.getStuID());
        }
        return students;
    }

    /**
     * Add student without payment.
     * @param stuID
     * @param duration
     */
    public void addStudent(long stuID, double duration){
        addStudent(stuID, duration, -1);
    }

    /**
     * Add student with payment.
     * @param stuID
     * @param duration
     * @param paymentID
     */
    public void addStudent(long stuID, double duration, long paymentID){
        if(containsStudent(stuID)){
            Logger.getInstance().log("Already has this student, will overwrite data... ", LogLevel.W);
        }
        studentsInfoSet.add(new StuDurPaid(stuID, duration, paymentID));
    }

    private boolean containsStudent(long stuID) {
        return studentsInfoSet.stream()
                .filter(s-> s.getStuID() == stuID)
                .findFirst()
                .isPresent();

    }
}
