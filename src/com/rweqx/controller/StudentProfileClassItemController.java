package com.rweqx.controller;

import com.rweqx.constants.Constants;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.util.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfileClassItemController extends StudentEventItemController implements Initializable {


    @FXML
    private Button bEdit;

    @FXML
    private Label lEventType;

    @FXML
    private Label lDate;

    @FXML
    private Label lDuration;

    @FXML
    private Label lClassType;


    @Override
    public void setEvent(Event e){
        super.setEvent(e);
        Class c = (Class) getEvent();
        lDate.setText(DateUtil.getYearMonthDayFromDate(c.getDate()));
        lDuration.setText(String.valueOf(c.getDurationOfStudent(student.getID())));
        lClassType.setText(c.getClassType());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bEdit.setFont(Constants.BASE_FONT);
        lEventType.setFont(Constants.BASE_FONT);
        lDate.setFont(Constants.BASE_FONT);
        lDuration.setFont(Constants.BASE_FONT);
        lClassType.setFont(Constants.BASE_FONT);

    }
}
