package com.rweqx.components;

import javafx.scene.control.Button;

public class StudentButton extends Button {
    private String studentName;
    private long studentID;

    public StudentButton(long studentID, String studentName){
        super();
        this.studentID = studentID;
        this.studentName = studentName;

        this.setText(studentName);

        //Square button.
        this.setPrefHeight(100);
        this.setPrefWidth(100);
    }

    public long getStudentID() {
        return this.studentID;
    }

    public String getStudentName() {
        return studentName;
    }
}
