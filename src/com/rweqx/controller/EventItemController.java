package com.rweqx.controller;

import com.rweqx.logger.LogLevel;
import com.rweqx.model.*;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.util.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;


import javafx.scene.layout.Pane;
import java.io.IOException;

public class EventItemController {

    @FXML
    private Label lStudents;

    @FXML
    private Label lType;

    @FXML
    private Label lPaid;

    @FXML
    private Label lDuration;

    @FXML
    private Button bEdit;

    private DataModel model;

    public void setEvent(Event event){
        //lDate.setText(DateUtil.getYearMonthDayFromDate(event.getDate()));

        if(event instanceof Class){
            lType.setText("Class");
            Class c = (Class) event;
            String students = "";
            for(Student s : c.getStudents()){
                students += s.getName() + ", ";
            }
            students = students.substring(0, students.length() - 2);
            lStudents.setText(students);


        }else if(event instanceof Payment){
            lType.setText("Payment");
            Payment p = (Payment) event;
            lStudents.setText(p.getStudent().getName());
            lPaid.setText(String.valueOf(p.getValue()));

        }
        bEdit.setOnAction(actionEvent ->{
            if(event instanceof Class){
                Class c = (Class) event;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/add-class.fxml"));
                try{
                    /*
                    Pane p = loader.load();
                    AddClass addClass = loader.getController();
                    addClass.initModel(model);
                    addClass.selectMode(AddClass.EDIT_MODE);
                    addClass.loadClass(c);

                    ViewNavigator.loadScene(p);
                    */
                    //TODO for now, don't do anything...

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initModel(DataModel dataModel) {
        this.model = dataModel;
    }
}
