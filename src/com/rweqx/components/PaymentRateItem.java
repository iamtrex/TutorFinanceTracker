package com.rweqx.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PaymentRateItem extends HBox {
    private TextField tRate;
    private Label lType;

    public Double getRate(){
        try {
            Double d = Double.parseDouble(tRate.getText());
            return d;
        }catch(NumberFormatException nfe){
            return 0.0;
        }
    }

    public PaymentRateItem(String rateType){

        tRate = new TextField();
        lType = new Label(rateType);

        this.setSpacing(10);
        this.getChildren().setAll(lType, new Pane(), tRate);
    }
}
