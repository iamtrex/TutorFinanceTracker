package com.rweqx.components;

import com.rweqx.controller.SettingsController;
import com.rweqx.model.SceneModel;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class ToolBar extends HBox {

    private boolean extended = true;
    private final int TRANSLATE_AMOUNT = 200;

    private SceneModel sm;

    @FXML
    private Button bBack;

    @FXML
    private Label lTitle;

    @FXML
    private Pane spacer;

    @FXML
    private Button bSettings;


    public ToolBar() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/rweqx/components/ToolBar.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getStyleClass().add("global-toolbar");

        bSettings.setOnAction(this::onOpenSettingsClicked);
    }

    private void onOpenSettingsClicked(ActionEvent event) {
        if(sm != null){
            sm.setScene(SettingsController.class.getSimpleName());
        }
    }

    public void setSceneModel(SceneModel sm) {
        this.sm = sm;
    }

    public void changeTitle(String s) {
        this.lTitle.setText(s);
    }


}
