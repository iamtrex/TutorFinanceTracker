package com.rweqx.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;


public class WarningPopUp {
    public static boolean isShown = false;
    public WarningPopUp(String s) {
        if(isShown){ //Dont' show if shown.
            return;
        }
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("WARNING!");
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label label = new Label(s);
        Button closeButton = new Button("I understand!");
        closeButton.setOnAction(e->{
           window.close();
        });
        VBox layout = new VBox(10, label, closeButton);

        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        isShown = true;
        window.show();

        window.setOnHidden(e->{
            isShown = false;
        });

    }
}
