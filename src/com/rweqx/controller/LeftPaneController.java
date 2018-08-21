package com.rweqx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LeftPaneController extends BaseController implements Initializable {
    @FXML
    private VBox leftPaneBox;


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

    private Map<Button, String> clickMap;

    public LeftPaneController(){
        clickMap = new HashMap<>();
    }


    public void switchScene(ActionEvent e){
        Button b = (Button) e.getSource();
        if(b == bAddClass){
            sceneModel.setCurrentClass(null);
        }else if(b == bAddPayment){
            sceneModel.setCurrentPayment(null);
        }
        sceneModel.setScene(clickMap.get(b));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clickMap = new HashMap<>();

        System.out.println("Initialized...");
        clickMap.put(bOverview, OverviewController.class.getSimpleName());
        clickMap.put(bAddClass, AddEditClassController.class.getSimpleName());
        clickMap.put(bAddPayment, AddEditPaymentController.class.getSimpleName());
        clickMap.put(bStudents, StudentProfilesListController.class.getSimpleName());
        clickMap.put(bPrintJobs, PrintController.class.getSimpleName());
        clickMap.put(bCalendar, DayViewController.class.getSimpleName());
        clickMap.put(bStats, StatsController.class.getSimpleName());
        clickMap.put(bBackupAndRestore, BackupRestoreController.class.getSimpleName());
        clickMap.put(bSettings, SettingsController.class.getSimpleName());

        leftPaneBox.setFillWidth(true);

    }
}
