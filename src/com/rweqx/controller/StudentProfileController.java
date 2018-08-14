package com.rweqx.controller;

import com.rweqx.model.DataModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfileController implements Initializable {

    @FXML
    private Label lName;

    private int studentID;
    private StringProperty studentName;

    private DataModel dataModel;


    public StudentProfileController(){
        studentName = new SimpleStringProperty();

    }


    public void initModel(DataModel dataModel){
        this.dataModel = dataModel;


    }

    public void setStudent(int studentID) {
        this.studentID = studentID;
        this.studentName.set(dataModel.getStudentsModel().getStudentByID(studentID).getName());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lName.textProperty().bind(studentName);
        studentName.addListener((obs, oldVal, newVal)->{

            lName.setText(newVal);
            //Load other things about student.

        });

    }
}
