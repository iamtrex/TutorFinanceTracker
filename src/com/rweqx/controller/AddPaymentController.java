package com.rweqx.controller;

import com.rweqx.components.WarningPopUp;
import com.rweqx.constants.Constants;
import com.rweqx.model.DataModel;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import com.rweqx.util.DateUtil;
import com.rweqx.util.StringUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddPaymentController implements Initializable {

    @FXML
    private TextField tStudent;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField tPaid;

    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;


    private DataModel model;

    public void loadPayment(Payment p){}

    public void initModel(DataModel model){
        this.model = model;
        List<String> students = model.getStudentsModel().getAllStudents()
                .stream()
                .map(s->s.getName())
                .collect(Collectors.toList());
        TextFields.bindAutoCompletion(tStudent, t ->
                students.stream()
                        .filter(elem -> elem.toLowerCase()
                                .contains(t.getUserText().toLowerCase()))
                        .collect(Collectors.toList()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        bSave.setOnAction((e)->{

            if(savePayment()){
                reset();
            }
        });

        bCancel.setOnAction((e)->{
            reset();
        });
    }

    private void reset() {
        tPaid.setText("");
        tStudent.setText("");
        datePicker.setValue(LocalDate.now());

    }

    private boolean savePayment(){
        String text = tPaid.getText();
        if(!StringUtil.isPositiveNumber(text)){
            new WarningPopUp("Amount paid invalid");
            return false;
        }else if(Double.parseDouble(tPaid.getText()) == 0){
            new WarningPopUp("Amount paid is $0????");
        }else if(datePicker.getValue() == null){
            new WarningPopUp("No date selected");
            return false;
        }else if(tStudent.getText().equals("")){
            new WarningPopUp("No student selected");
            return false;
        }
        Student student = model.getStudentsModel().getStudentByName(tStudent.getText().trim());
        if (student == null) {
            new WarningPopUp("Invalid student.");
            return false;
        }

        Date date = DateUtil.localDateToDate(datePicker.getValue());

        double amount = Double.parseDouble(tPaid.getText());

        long pid = model.createAndAddPayment(student, date, amount);

        System.out.println("Success with pid " + pid);
        //TODO show confirmation window...

        return true;



    }
}
