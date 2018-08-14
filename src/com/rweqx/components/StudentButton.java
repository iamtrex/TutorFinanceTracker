package com.rweqx.components;

import javafx.scene.control.Button;

public class StudentButton extends Button {
    private String studentName;
    private int studentID;

    public StudentButton(int studentID, String studentName){
        super();

        this.studentID = studentID;
        this.studentName = studentName;

        this.setText(studentName);

        //Square button.
        this.setPrefHeight(100);
        this.setPrefWidth(100);
    }

    public String getStudentName(){
        return this.studentName;
    }

    public int getStudentID() {
        return this.studentID;
    }
}
