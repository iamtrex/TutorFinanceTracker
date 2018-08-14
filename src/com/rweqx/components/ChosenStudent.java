package com.rweqx.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ChosenStudent extends Pane {
    public String getName() {
        return name;
    }



    private Button removeButton;
    private Label lName;

    private String name;

    public ChosenStudent(String name){
        super();

        this.name = name;

        lName = new Label(name);
        removeButton = new Button("x");
        removeButton.setOnAction((e)->{
            fireEvent(e);
        });


        HBox hbox = new HBox(10, lName, removeButton);
        this.getChildren().add(hbox);


    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }

    public final void setOnAction(EventHandler<ActionEvent> value) { onActionProperty().set(value); }

    public final EventHandler<ActionEvent> getOnAction() { return onActionProperty().get(); }

    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }

        @Override
        public Object getBean() {
            return ChosenStudent.this;
        }

        @Override
        public String getName() {
            return "onAction";
        }
    };
}
