package com.rweqx.controller;

import com.rweqx.model.Event;
import com.rweqx.model.Class;

import com.rweqx.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DayViewClassItemController extends DayViewEventItemController{
    @FXML
    Button bEdit;

    @FXML
    private Label lClassType;

    @FXML
    private Label lDurations;

    @FXML
    private Label lStudents;

    @FXML
    private Label lEventType;

    @Override
    public void setEvent(Event e){
        super.setEvent(e);
        Class c = (Class) e;
        lClassType.setText(c.getClassType());
        lDurations.setText(c.getDurationRange());

        String studentsStr = "";
        for(long sid : c.getStudents()){
            studentsStr += modelManager.getStudentManager().getStudentByID(sid).getName() + ", ";
        }
        studentsStr = studentsStr.substring(0, studentsStr.length() - 2);
        lStudents.setText(studentsStr);

    }

    public void editClicked(){
        sceneModel.setCurrentClass((Class) event);
        sceneModel.setScene(AddEditClassController.class.getSimpleName());
    }

}
