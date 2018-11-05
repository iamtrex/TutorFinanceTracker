package com.rweqx.components;

import com.rweqx.model.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class StudentListButton extends Button {
    private Student student;
    private boolean isSelected;
    private CommandInterface fnCallback;

    public StudentListButton(Student s, boolean isSelected, CommandInterface fnCallback){
        this.student = s;
        this.isSelected = isSelected;
        this.fnCallback = fnCallback;

        this.getStyleClass().add("student-list-button");

        this.setText(s.getName());
        this.setOnAction(this::select);
    }

    private void select(ActionEvent e){
        this.isSelected = !this.isSelected;
        fnCallback.callback(this.student, this.isSelected);
    }


    public interface CommandInterface {
        void callback(Student student, boolean isSelected);
    }

}