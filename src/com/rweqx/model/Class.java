package com.rweqx.model;

import com.rweqx.controller.StudentEventItemController;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Class extends Event{

    private Set<StuDurPaid> studentsInfoSet;
    private String classType;
    private List<String> tags;

    public Class(long eventID, LocalDate date, String classType, String comment, List<String> tags) {
        super(eventID, date, comment);
        this.classType = classType;
        studentsInfoSet = new HashSet<>();
        //this.tags = tags;
        tags = new ArrayList<>(); //Fill empty lists.
    }

    public Class(Class copy) {
        super(copy.getID(), copy.getDate(), copy.getComment());
        this.classType = copy.classType;
        studentsInfoSet = new HashSet<>();
        for(StuDurPaid sdp : copy.getAllData()){
            studentsInfoSet.add(new StuDurPaid(sdp.getStuID(), sdp.getDuration(), sdp.getPaidID()));
        }
        this.tags = new ArrayList<>();
        for(String s : copy.getTags()){
            tags.add(s);
        }
        // Full Deep copy of class.
    }

    public List<String> getTags(){
        return tags;
    }

    public Set<StuDurPaid> getAllData(){
        return studentsInfoSet;
    }

    public Set<Long> getStudents(){
        Set<Long> students = new HashSet<>();
        for(StuDurPaid sdp : studentsInfoSet){
            students.add(sdp.getStuID());
        }
        return students;
    }

    public void addStudent(StuDurPaid sdp){
        if(studentsInfoSet.stream()
                .anyMatch(e -> e.getStuID() == sdp.getStuID())){
            Logger.getInstance().log("Actually overwrote student data -_-", LogLevel.W);
        }
        this.studentsInfoSet.add(sdp);

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

    public boolean containsStudent(long stuID) {
        return studentsInfoSet.stream()
                .filter(s-> s.getStuID() == stuID)
                .findFirst()
                .isPresent();

    }

    //TOOD can we make this a stream?
    public long getPaidIDOfStudent(long stuID){
        for(StuDurPaid sdp : studentsInfoSet){
            if(sdp.getStuID() == stuID){
                return sdp.getPaidID();
            }
        }
        Logger.getInstance().log("Cannot find student in class, should not happen... ", LogLevel.S);
        return -1;
    }

    public Double getDurationOfStudent(long stuID) {
        for(StuDurPaid sdp : studentsInfoSet){
            if(sdp.getStuID() == stuID){
                return sdp.getDuration();
            }
        }
        Logger.getInstance().log("Cannot find student in class, should not happen... ", LogLevel.S);
        return 0.0;
    }

    public String getClassType() {
        return classType;
    }

    public double getAvgDuration() {
        double total = 0;
        double count = 0;

        for(StuDurPaid sdp : studentsInfoSet){
            total += sdp.getDuration();
            count ++;
        }

        if(count == 0){
            return 0;
        }
        return total/count;
    }
    public String getDurationRange() {
        //If only one student, return his/her duration.
        if(studentsInfoSet.size() == 1){
            StuDurPaid sdp = studentsInfoSet.iterator().next();
            return sdp.getDuration() + " hrs";

        }

        //Otherwise find the range of durations from shortest to longest.
        double small = Double.MAX_VALUE;
        double large = Double.MIN_VALUE;
        for(StuDurPaid sdp : studentsInfoSet){
            if(sdp.getDuration() > large) {
                large = sdp.getDuration();
            }
            if(sdp.getDuration() < small){
                small = sdp.getDuration();
            }
        }
        if(small == large){
            return small + " hrs";
        }
        return small + " - " + large + " hrs";


    }

    public void removePayment(long id) {
        replacePayment(id, -1);
    }

    public void replacePayment(long replaced, long replacer) {
        for(StuDurPaid sdp : studentsInfoSet){
            if(sdp.getPaidID() == replaced){
                sdp.setPaidID(replacer); //No paid ID.
            }
        }
    }

}
