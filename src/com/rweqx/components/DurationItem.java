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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationItem extends HBox{


    private TextField tDuration;
    private Label lName;
    private DoubleProperty duration;
    private String name;

    public DurationItem(String name){
        super();
        this.name = name;
        duration = new SimpleDoubleProperty(0);
        tDuration = new TextField("");
        tDuration.setPromptText("Hours");

        lName = new Label(name);
        Pane spacer = new Pane();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setSpacing(10);
        this.getChildren().setAll(lName, spacer, tDuration);

        //Checks if positive double and if so, sets the double to it.
        tDuration.textProperty().addListener((obs, oldVal, newVal) -> {
            String text = newVal.trim();
            //System.out.println(text);
            if (text.equalsIgnoreCase("")) {
                duration.set(0);
            } else {
                //Match regex.
                Pattern p = Pattern.compile("\\d*\\.\\d*");
                Matcher m = p.matcher(text);
                if (m.matches() && !text.equals(".")) {
                    //System.out.println("IS number. ");
                    duration.set(Double.parseDouble(text));
                }
            }
        });
    }

    public Double getDuration(){
        return duration.getValue();
    }
    public String getName() {
        return name;
    }
}
