package com.rweqx.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class DurationCell extends ListCell<String> {

    private HBox box;

    private TextField tDuration;
    private Label lName;
    private IntegerProperty duration;

    public DurationCell(){
        super();
        duration = new SimpleIntegerProperty(0);
        tDuration = new TextField("");
        tDuration.setPromptText("Hours");

        lName = new Label("(empty)");
        Pane spacer = new Pane();

        HBox.setHgrow(spacer, Priority.ALWAYS);


        box = new HBox(10, lName, spacer, tDuration);

    }
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if(empty){
            setGraphic(null);
        }else{
            lName.setText(item);
            setGraphic(box);
        }
    }
}

