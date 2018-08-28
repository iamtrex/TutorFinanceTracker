package com.rweqx.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

public class StudentButton extends Button {
    private String studentName;
    private long studentID;

    public StudentButton(long studentID, String studentName){
        super();
        this.studentID = studentID;
        this.studentName = studentName;

        String name = studentName;
        if(name.length() > 9) {
            //name = name.replace(" ", "\n");
            name = name.replaceFirst(" " , "\n");
        }
        System.out.println("Name " + name);
        System.out.println(name.length());

        this.setAlignment(Pos.CENTER);
        this.setText(name);
        this.textAlignmentProperty().set(TextAlignment.CENTER);
        //Square button.
        this.setPrefHeight(125);
        this.setPrefWidth(125);
    }

    public long getStudentID() {
        return this.studentID;
    }

    public String getStudentName() {
        return studentName;
    }
}
