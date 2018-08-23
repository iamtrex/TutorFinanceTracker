package com.rweqx.components;

import com.rweqx.util.StringUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;

public class ClickEditTextField extends TextField {

    private String original = "";
    public ClickEditTextField(){
        super();

        disableEdit();

        this.setOpacity(1);
        this.setOnMouseClicked(this::enableEdit);
        this.focusedProperty().addListener((obs, oldVal, newVal)->{
            if(!newVal){
                submit();
            }
        });

        this.setOnAction((e)->{
            submit();
        });

    }
    private void submit(){
        if(validateInput()) {
            disableEdit();
        }else{
            new WarningPopUp("Rate is not a number, undoing change");
            this.setText(original);
            disableEdit();
        }
    }

    private boolean validateInput() {
        return StringUtil.isPositiveNumber(this.getText());
    }

    private void enableEdit(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            original = this.getText();

            this.setEditable(true);
            this.setStyle(""); //Make default background
            this.selectAll(); //Select all text.
        }
    }

    private void disableEdit() {
        this.setEditable(false);
        this.setStyle("-fx-background-color:rgba(255, 255, 255, 0.0);"); //Hide background, make it look like a label
    }
}
