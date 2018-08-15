package com.rweqx.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentInClassElement {

    private final SimpleStringProperty studentName;
    private final SimpleDoubleProperty duration;
    private final SimpleDoubleProperty paid;

    //private Double duration, paid;

    public StudentInClassElement(String studentName, Double duration, Double paid) {
        this.studentName = new SimpleStringProperty(studentName);
        this.duration = new SimpleDoubleProperty(duration);
        this.paid = new SimpleDoubleProperty(paid);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public Double getPaid() {
        return paid.get();
    }

    public Double getDuration() {
        return duration.get();
    }

}
