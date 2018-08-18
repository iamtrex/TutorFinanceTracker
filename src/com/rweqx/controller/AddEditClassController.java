package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.DurationItem;
import com.rweqx.components.PaidItem;
import com.rweqx.model.Class;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AddEditClassController extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int current_mode = ADD_MODE; //Default is addClass.


    private StringProperty currentSearch;

    private ObservableList<String> chosenStudents;
    private List<ChosenStudent> chosenStudentsLabels;

    private ObservableList<String> searchMatchNames;
    private ObservableList<String> classTypes;


    private Map<String, DurationItem> durationMap;
    private Map<String, PaidItem> paidMap;

    @FXML
    private HBox selectedStudentsBox;
    @FXML
    private TextField studentsBar;
    @FXML
    private ListView<String> studentsListView;

    @FXML
    private VBox paidBox;
    @FXML
    private VBox durationBox;

    @FXML
    private ScrollPane scrollDuration;

    @FXML
    private ScrollPane scrollPaid;


    @FXML
    private CheckBox sameDurationCheck;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> classTypeChoices;
    @FXML
    private TextField tSingleDuration;

    @FXML
    private Button bSave;

    @FXML
    private Button bCancel;

    private Class currentlyEditingClass;

    @Override
    public void sceneLoaded(){
        Class c = sceneModel.getCurrentClass();
        if(c == null){
            //regular add mode.
            currentlyEditingClass = null;
            current_mode = ADD_MODE;
        }else{
            //Load class mode
            currentlyEditingClass = c;
            current_mode = EDIT_MODE;
        }
    }

    public void reset(){
        chosenStudents.clear();
        chosenStudentsLabels.clear();
        searchMatchNames.clear();
        classTypes.clear();
        durationMap.clear();
        paidMap.clear();
    }

    public AddEditClassController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setup.


    }
}
