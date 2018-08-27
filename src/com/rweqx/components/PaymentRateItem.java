package com.rweqx.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PaymentRateItem extends HBox {
    private TextField tRate;
    private Label lType;
    private String type;


    public String getType(){
        return type;
    }
    public Double getRate(){
        try {
            Double d = Double.parseDouble(tRate.getText());
            return d;
        }catch(NumberFormatException nfe){
            return 0.0;
        }
    }

    public PaymentRateItem(String rateType){

        type = rateType;
        tRate = new TextField();
        lType = new Label(rateType);

        this.setSpacing(10);
        Pane p = new Pane();

        HBox.setHgrow(p, Priority.ALWAYS);

        this.getChildren().setAll(lType, p, tRate);
    }

    /**
     * //TODO SORTA HACKY RN, SHOUDL WE MAKE THIS A SEPERATE CLASS?
     * Uneditable rate type.
     * @param rateType
     * @param rate
     */
    public PaymentRateItem(String rateType, double rate){
        type = rateType;
        tRate = new TextField();
        tRate.setText(String.valueOf(rate));
        lType = new Label(rateType);
        tRate.setEditable(false);
        tRate.setStyle("-fx-background-color:rgba(255, 255, 255, 0.0);"); //Hide background, make it look like a label

        this.setSpacing(10);
        Pane p = new Pane();

        HBox.setHgrow(p, Priority.ALWAYS);

        this.getChildren().setAll(lType, p, tRate);
    }


    public void setRate(Double rate) {
        tRate.setText(String.valueOf(rate));

    }
}
