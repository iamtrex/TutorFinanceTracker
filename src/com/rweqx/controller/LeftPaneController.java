package com.rweqx.controller;

import com.rweqx.constants.Constants;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML
    private Button bSave;

    private Map<Button, String> clickMap;

    @FXML
    private Label lVersion;

    public LeftPaneController(){
        clickMap = new HashMap<>();
    }


    public void switchScene(ActionEvent e){
        Button b = (Button) e.getSource();
        if(b == bSave){ //TODO probably better if this does it in a back thread, but must fix it so that there wont' be concurrent editing issues
            //TODO and it doesn't crash if the program is closed while it's trying to save...
            Logger.getInstance().log(getClass().getSimpleName(),"Saving all data", LogLevel.D);
            modelManager.saveAll();
            Logger.getInstance().log(getClass().getSimpleName(),"Save complete", LogLevel.D);
            return;
        }
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

        lVersion.setText("Version - " + Constants.VERSION);
        leftPaneBox.setFillWidth(true);

    }
}
