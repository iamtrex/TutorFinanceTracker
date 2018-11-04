package com.rweqx.controller;

import com.rweqx.components.SideBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditClass3Controller extends BaseController implements Initializable {

    @FXML
    private HBox addClassToolbar;

    @FXML
    private Button bSave;

    @FXML
    private MenuButton bAddOptions;

    @FXML
    private MenuItem bNewClass;

    @FXML
    private MenuItem bAddDuplicateClass;

    @FXML
    private MenuItem bAddDuplicateSameDateClass;

    @FXML
    private Button bCancel;

    @FXML
    private ScrollPane scrollClasses;

    @FXML
    private VBox classBox;

    @FXML
    private SideBar addClassSideBar;



    @Override
    public void sceneLoaded(){

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bSave.setOnAction(this::onSaveClicked);

    }

    public void onSaveClicked(ActionEvent e){
        addClassSideBar.openSideBar();
    }

    public void onCancelClicked(ActionEvent e){

    }

    public void onAddClassClicked(ActionEvent e){
        Object source = e.getSource();
        if(source == bAddDuplicateClass){

        }else if(source == bAddDuplicateSameDateClass){

        }else if(source == bNewClass){

        }

    }







}
