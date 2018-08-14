package com.rweqx.controller;

import com.rweqx.constants.Constants;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.DataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private GridPane root;

    @FXML
    private Button bOverview;
    @FXML
    private Button bAddClass;
    @FXML
    private Button bAddPayment;
    @FXML
    private Button bStudents;
    @FXML
    private Button bPrintJobs;
    @FXML
    private Button bCalendar;
    @FXML
    private Button bStats;
    @FXML
    private Button bBackupAndRestore;
    @FXML
    private Button bSettings;

    @FXML
    private StackPane content;

    @FXML
    private Label lVersion;

    private Map<Button, Pane> clickMap;

    private DataModel dataModel;

    private Logger logger;
    public RootController(){
        dataModel = new DataModel();
        logger = Logger.getInstance();
        /**
         * Load shit.
         */

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lVersion.setText("Version - " + Constants.VERSION);
        clickMap = new HashMap<>();
        try {
            FXMLLoader addClassLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/add-class.fxml"));
            FXMLLoader studentsLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/show-students.fxml"));

            //Load and put into map.
            clickMap.put(bAddClass, addClassLoader.load());
            clickMap.put(bStudents, studentsLoader.load());


            //Init models.
            AddClass addClassController = addClassLoader.getController();
            addClassController.initModel(dataModel);

            ShowStudents showStudentsController = studentsLoader.getController();
            showStudentsController.initModel(dataModel);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void switchScene(ActionEvent e){
        Object source = e.getSource();
        clickMap.forEach((b, p) -> {
            if(b == source){
                content.getChildren().setAll(p);
            }
        });

    }

    public void loadScene(Pane p){
        content.getChildren().setAll(p);
    }
    public void loadScene(String fxml) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        try{
            Pane p = loader.load();
            content.getChildren().setAll(p);
        }catch(IOException e){
            //Don't load.
            logger.log("Cannot load " + fxml, LogLevel.S);
        }
    }
}
