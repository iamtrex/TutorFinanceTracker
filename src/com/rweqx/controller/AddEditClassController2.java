package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.DurationItem;
import com.rweqx.components.PaidItem;
import com.rweqx.components.WarningPopUp;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.Class;
import com.rweqx.model.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AddEditClassController2 extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int currentMode = ADD_MODE; //Default is addClass.

    private List<SingleClassController> classes;

    private StringProperty currentSearch;

    private ObservableList<Student> chosenStudents;
    private List<ChosenStudent> chosenStudentsLabels;

    private ObservableList<Student> searchMatchNames;
    private ObservableList<String> classTypes;


    @FXML
    private VBox classesBox;

    @FXML
    private TextField studentSearchBar;

    @FXML
    private ScrollPane classScroll;

    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDelete;
    @FXML
    private Button bPlusClass;

    private Class currentlyEditingClass;

    public AddEditClassController2() {
        currentSearch = new SimpleStringProperty();
        chosenStudents = FXCollections.observableArrayList();
        chosenStudentsLabels = new ArrayList<>();
        searchMatchNames = FXCollections.observableArrayList();
        classTypes = FXCollections.observableArrayList();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classScroll.setFitToWidth(true);

    }

    @Override
    public void sceneLoaded(){
        if(sceneModel.getCurrentClass() != null){
            currentlyEditingClass = sceneModel.getCurrentClass();
            bDelete.setVisible(true);
            bSave.setText("Save Class");
            currentMode = EDIT_MODE;
        }else{
            bSave.setText("Add Class");
            bDelete.setVisible(false);
            currentMode = ADD_MODE;
        }
    }

    public void plusClassClicked(ActionEvent event){
        FXMLLoader singleClassLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/single-class.fxml"));
        SingleClassController scc = singleClassLoader.getController();

        try {
            Pane p = singleClassLoader.load();
            classesBox.getChildren().add(p);
            classes.add(scc);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void deleteClicked(ActionEvent e){
        System.out.println("Delete editing class. ");
    }

    public void cancelClicked(ActionEvent e){
        System.out.println("Try to cancel");
    }
    public void saveClicked(ActionEvent e){
        System.out.println("Try to save");
    }
    public void minusClassClicked(ActionEvent e){
        //Remove last.
        classes.remove(classes.size()-1);
        classesBox.getChildren().remove(classesBox.getChildren().size()-1);
    }

}