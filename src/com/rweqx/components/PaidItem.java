package com.rweqx.components;

import com.rweqx.constants.Constants;
import com.rweqx.model.Student;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaidItem extends HBox {

    private TextField tPaid;
    private Label lName;
    private DoubleProperty paid;
    private Student student;

    private ComboBox<String> choosePaymentType;


    public PaidItem(Student student) {
        super();
        this.student = student;
        paid = new SimpleDoubleProperty(0.0);
        tPaid = new TextField("");
        tPaid.setPromptText("$ Paid");

        lName = new Label(student.getName());
        Pane spacer = new Pane();

        ObservableList<String> paymentTypes = FXCollections.observableArrayList();
        paymentTypes.addAll("Cash", "Cheque");

        choosePaymentType = new ComboBox<>();
        choosePaymentType.setItems(paymentTypes);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.setSpacing(10);
        this.getChildren().setAll(lName, spacer, tPaid, choosePaymentType);


        tPaid.textProperty().addListener((obs, oldVal, newVal) -> {
            String text = newVal.trim();
            //System.out.println(text);
            if (text.equalsIgnoreCase("")) {
                paid.set(0);
            } else {
                //Match regex.
                Pattern p = Pattern.compile(Constants.NUMBER_REGEX);
                Matcher m = p.matcher(text);
                if (m.matches() && !text.equals(".")) {
                    //System.out.println("IS number. ");
                    paid.set(Double.parseDouble(text));
                }
            }
        });


    }

    public Student getStudent(){
        return student;
    }

    public Double getPaid() {

        return paid.doubleValue();
    }

    public void setPaid(Double d){
        paid.set(d);
        tPaid.setText(String.valueOf(d));
    }


    public String getPaidType() {
        return choosePaymentType.getSelectionModel().getSelectedItem();
    }

    public void setPayType(String paymentType) {
        choosePaymentType.setValue(paymentType);
    }
}
