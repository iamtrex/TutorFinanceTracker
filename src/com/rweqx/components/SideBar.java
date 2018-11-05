package com.rweqx.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class SideBar extends VBox {

    private boolean extended = true;
    private final int TRANSLATE_AMOUNT = 200;

    @FXML
    private HBox toolbar;

    @FXML
    private HBox bottomBar;

    @FXML
    private Button bExtend;

    public SideBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SideBar.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(bExtend);
        bExtend.setOnAction(this::onExtendClicked);

    }

    public boolean openSideBar(){
        if(extended){
            return false;
        }else{
            onExtendClicked(null);
            return true;
        }
    }
    public void onExtendClicked(ActionEvent e) {
        boolean wasExtended = extended;
        final int COUNT = 30; // # of cycles.

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<>() {
            double width = wasExtended ? 200 : 0;
            @Override
            public void handle(ActionEvent event) {
                width += 200/((double)COUNT) * (wasExtended ? -1 : 1);
                System.out.println(width);
                SideBar.this.setWidth(width);
                SideBar.this.setPrefWidth(width);
                SideBar.this.setMaxWidth(width);
                SideBar.this.setMinWidth(width);
            }

        }));
        fiveSecondsWonder.setCycleCount(COUNT);
        fiveSecondsWonder.play();
        extended = !extended;
    }


    public void setContent(Pane content) {

    }
}
