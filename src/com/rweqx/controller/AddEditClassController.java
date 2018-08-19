package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.DurationItem;
import com.rweqx.components.PaidItem;
import com.rweqx.model.Class;
import com.rweqx.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AddEditClassController extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int current_mode = ADD_MODE; //Default is addClass.


    private StringProperty currentSearch;

    private ObservableList<Student> chosenStudents;
    private List<ChosenStudent> chosenStudentsLabels;

    private ObservableList<Student> searchMatchNames;
    private ObservableList<String> classTypes;


    private Map<String, DurationItem> durationMap;
    private Map<String, PaidItem> paidMap;

    @FXML
    private HBox selectedStudentsBox;
    @FXML
    private TextField studentSearchBar;
    @FXML
    private ListView<Student> studentsListView;

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

    public AddEditClassController(){
        currentSearch = new SimpleStringProperty();
        chosenStudents = FXCollections.observableArrayList();
        chosenStudentsLabels = new ArrayList<>();
        searchMatchNames = FXCollections.observableArrayList();
        classTypes = FXCollections.observableArrayList();
        durationMap = new HashMap<>();
        paidMap = new HashMap<>();

    }

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
        tSingleDuration.setText("");
        sameDurationCheck.setSelected(false);
        datePicker.setValue(LocalDate.now());

        bSave.setText("Add Class");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setup.
        scrollDuration.setFitToWidth(true);
        scrollPaid.setFitToWidth(true);

        //Setup list to properly display students being searched.
        studentsListView.setItems(searchMatchNames);
        studentsListView.setCellFactory(stu -> new ListCell<>(){
            @Override
            protected void updateItem(Student s, boolean empty){
                super.updateItem(s, empty);
                if(empty || s == null){
                    setText(null);
                }else{
                    setText(s.getName());
                }

            }
        });
        studentsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->{
            Student selected = newVal;
            if(selected == null){
                return;
            }
            addStudent(selected);
        });


        //Setup search bar to modify items in studentsListview when searching...
        studentSearchBar.textProperty().addListener((obs, oldVal, newVal) ->{
            currentSearch.set(newVal);
        });
        studentSearchBar.setOnAction((e)->{
            if(studentsListView.getItems().size() > 0) { //Same as selecting top student.
                Student s = studentsListView.getItems().get(0);
                addStudent(s);
            }
        });
        currentSearch.addListener((obs, oldVal, newVal) ->{

            String search = newVal;
            search = search.trim().toLowerCase();

            if (search.equals("")){
                searchMatchNames.clear();
                studentsListView.getSelectionModel().clearSelection();
                studentsListView.getItems().clear();

            }else{
                String finalSearch = search;

                searchMatchNames.setAll(
                        modelManager.getStudentManager().getStudents()
                        .stream()
                        .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                        .collect(Collectors.toList()));

            }

            studentsListView.refresh();
        });

    }

    private void saveClicked(){

    }
    private void cancelClicked(){

    }
    private void addStudent(Student selected) {

    }
}
