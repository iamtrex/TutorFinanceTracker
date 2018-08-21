package com.rweqx.controller;

import com.rweqx.constants.Constants;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.util.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfilePaymentItemController extends StudentEventItemController implements Initializable {

    @FXML
    private Button bEdit;

    @FXML
    private Label lEventType;

    @FXML
    private Label lDate;

    @FXML
    private Label lAmount;

    @FXML
    private Label lPaymentType;


    @Override
    public void setEvent(Event e){
        super.setEvent(e);

        Payment p = (Payment) getEvent();
        lDate.setText(DateUtil.getYearMonthDayFromDate(p.getDate()));

        lAmount.setText(String.valueOf(p.getPaymentAmount()));
        lPaymentType.setText(p.getPaymentType());

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bEdit.setFont(Constants.BASE_FONT);
        lEventType.setFont(Constants.BASE_FONT);
        lDate.setFont(Constants.BASE_FONT);
        lAmount.setFont(Constants.BASE_FONT);
        lPaymentType.setFont(Constants.BASE_FONT);

    }
}
