package com.rweqx.components;

import com.rweqx.model.Student;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class ChosenStudent extends AnchorPane {
    public String getName() {
        return name;
    }


    private Student student;
    private String name;

    public ChosenStudent(Student student){
        super();

        this.student = student;
        this.name = student.getName();
        Text tName = new Text(name);


        Text iDel = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.TIMES, "20");
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setLeftAnchor(tName, 2.0);
        AnchorPane.setRightAnchor(iDel, 2.0);
        AnchorPane.setTopAnchor(tName, 0.0);
        AnchorPane.setTopAnchor(iDel, 0.0);
        AnchorPane.setBottomAnchor(tName, 0.0);
        AnchorPane.setBottomAnchor(iDel, 0.0);

        anchorPane.getChildren().addAll(tName, iDel);
        Button removeButton = new Button("",anchorPane);

        removeButton.setOnAction((e)->{
            fireEvent(e);
        });

        this.setPrefWidth(200);
        this.setPrefHeight(30);
        this.getChildren().setAll(removeButton);
        AnchorPane.setLeftAnchor(removeButton, 0.0);
        AnchorPane.setRightAnchor(removeButton, 0.0);

    }

    public Student getStudent(){
        return student;
    }
    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }

    public final void setOnAction(EventHandler<ActionEvent> value) { onActionProperty().set(value); }

    public final EventHandler<ActionEvent> getOnAction() { return onActionProperty().get(); }

    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<>() {
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
