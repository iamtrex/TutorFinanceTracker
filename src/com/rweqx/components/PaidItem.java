package com.rweqx.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private String name;

    public PaidItem(String name) {
        super();
        this.name = name;
        paid = new SimpleDoubleProperty(0.0);
        tPaid = new TextField("");
        tPaid.setPromptText("$ Paid");

        lName = new Label(name);
        Pane spacer = new Pane();

        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.setSpacing(10);
        this.getChildren().setAll(lName, spacer, tPaid);


        tPaid.textProperty().addListener((obs, oldVal, newVal) -> {
            String text = newVal.trim();
            //System.out.println(text);
            if (text.equalsIgnoreCase("")) {
                paid.set(0);
            } else {
                //Match regex.
                Pattern p = Pattern.compile("\\d*\\.\\d*");
                Matcher m = p.matcher(text);
                if (m.matches() && !text.equals(".")) {
                    //System.out.println("IS number. ");
                    paid.set(Double.parseDouble(text));
                }
            }
        });


    }

    public String getName() {
        return name;
    }

    public Double getPaid() {

        return paid.doubleValue();
    }


}
