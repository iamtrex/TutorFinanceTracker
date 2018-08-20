package com.rweqx.controller;

import com.rweqx.model.Class;
import com.rweqx.model.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddEditPaymentController extends BaseController {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int current_mode = ADD_MODE; //Default is addClass.
    private Payment currentlyEditingPayment;

    @FXML
    private TextField tStudent;

    @FXML
    private TextField tPaid;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox paymentType;

    @FXML
    private Button bCancel;

    @FXML
    private Button bSave;

    @Override
    public void sceneLoaded(){
        Payment p = sceneModel.getCurrentPayment();
        if(p == null){
            //regular add mode.
            currentlyEditingPayment = null;
            current_mode = ADD_MODE;

            System.out.println("Current mode - adding");

        }else{
            //Load class mode
            currentlyEditingPayment = p;
            current_mode = EDIT_MODE;

            System.out.println("Current mode - editing");
        }
    }

    public void reset(){
        tStudent.setText("");
        tPaid.setText("");
        paymentType.getSelectionModel().clearSelection();
        datePicker.setValue(LocalDate.now());
    }


}
