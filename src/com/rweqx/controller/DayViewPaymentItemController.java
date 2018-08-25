package com.rweqx.controller;

import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DayViewPaymentItemController extends EventItemController {
    @FXML
    Button bEdit;

    @FXML
    private Label lPaymentType;

    @FXML
    private Label lAmount;

    @FXML
    private Label lStudent;

    @FXML
    private Label lEventType;

    @Override
    public void setEvent(Event e){
        super.setEvent(e);
        Payment p = (Payment) e;
        lPaymentType.setText(p.getPaymentType());
        lAmount.setText(String.valueOf(p.getPaymentAmount()));
        lStudent.setText(modelManager.getStudentManager().getStudentByID(p.getStudentID()).getName());
    }


}
