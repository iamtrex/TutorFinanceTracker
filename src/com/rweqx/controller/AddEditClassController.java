package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.DurationItem;
import com.rweqx.components.PaidItem;
import com.rweqx.components.WarningPopUp;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.Class;
import com.rweqx.model.SceneModel;
import com.rweqx.model.StuDurPaid;
import com.rweqx.model.Student;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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


    private Map<Student, DurationItem> durationMap;
    private Map<Student, PaidItem> paidMap;

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

            System.out.println("Current mode - adding");

        }else{
            //Load class mode
            currentlyEditingClass = c;
            current_mode = EDIT_MODE;
            //TODO LOAD THE CLASS...

            System.out.println("Current mode - editing");
        }
    }

    public void reset(){
        chosenStudents.clear();
        chosenStudentsLabels.clear();
        selectedStudentsBox.getChildren().clear();
        searchMatchNames.clear();
        classTypes.clear();
        durationMap.clear();
        paidMap.clear();
        tSingleDuration.setText("");
        sameDurationCheck.setSelected(false);
        classTypeChoices.getSelectionModel().clearSelection();
        datePicker.setValue(LocalDate.now());

        bSave.setText("Add Class");
    }

    @Override
    public void initModel(ModelManager modelManager, SceneModel scene){
        super.initModel(modelManager, scene);
        classTypeChoices.setItems(modelManager.getClassTypes().getTypesList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Basic setup.
        datePicker.setValue(LocalDate.now()); //TODO REMOVE?
        scrollDuration.setFitToWidth(true);
        scrollPaid.setFitToWidth(true);

        sameDurationCheck.selectedProperty().addListener((obs, oldVal, newVal)->{
            if(sameDurationCheck.isSelected()) {
                durationBox.setVisible(false);
                tSingleDuration.setVisible(true);
            }else{
                durationBox.setVisible(true);
                tSingleDuration.setVisible(false);
            }
        });

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
                String finalSearch = search; //cuz apparently it has to be final :/?
                searchMatchNames.setAll(
                        modelManager.getStudentManager().getStudents()
                        .stream()
                        .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                        .collect(Collectors.toList()));

            }
            studentsListView.refresh();
        });


        //Bind Paid and Duration Boxes to watch chosenStudents
        chosenStudents.addListener((ListChangeListener<Student>) c -> {
            if(c.next()) {
                if (c.wasPermutated() || c.wasUpdated()) {
                    System.out.println(c);
                } else {
                    //Fixed (BUG)- BUG IF SAME NAME.
                    for (Student remItm : c.getRemoved()) {
                        DurationItem di = durationMap.get(remItm);
                        if(di != null){
                            durationBox.getChildren().remove(di);
                        }
                        PaidItem pi = paidMap.get(remItm);
                        if(pi != null){
                            paidBox.getChildren().remove(pi);
                        }
                    }

                    for (Student addItm : c.getAddedSubList()) {
                        DurationItem di = new DurationItem(addItm);
                        PaidItem pi = new PaidItem(addItm);
                        durationMap.put(addItm, di);
                        paidMap.put(addItm, pi);
                        durationBox.getChildren().add(di);
                        paidBox.getChildren().add(pi);
                    }
                }
            }

        });
    }

    public void saveClicked(){
        if(runChecks()){
            saveCurrentClass();
        }
        
    }

    private boolean runChecks() {
        String classType = classTypeChoices.getValue();
        LocalDate localDate = datePicker.getValue();
        boolean sameDuration = sameDurationCheck.isSelected();
        double singleDurationLength;

        if(localDate == null){
            new WarningPopUp("No Date Picked!");
            return false;
        }else if(classType == null){
            new WarningPopUp("No Type Picked!");
            return false;
        }else if(chosenStudents.size() == 0){
            new WarningPopUp("No Students Picked!");
            return false;
        }else if(sameDuration){
            try {
                singleDurationLength = Double.parseDouble(tSingleDuration.getText());
            }catch(NumberFormatException nfe) {
                //Ignore
                singleDurationLength = 0;
            }
            if(singleDurationLength <= 0){
                new WarningPopUp("Duration is 0 or malformed");
                return false;
            }
        }else{
            for(Student student: chosenStudents){
                DurationItem di = durationMap.get(student);
                if(di == null){
                    Logger.getInstance().log("Duration item could not be found for " + student, LogLevel.S);
                    return false;
                }else if(di.getDuration() <= 0){
                    new WarningPopUp("Duration for " + student + " is 0 or malformed");
                    return false;
                }

                PaidItem pi = paidMap.get(student);
                if(pi.getPaid() != 0) {
                    if(pi.getPaidType() == null){
                        new WarningPopUp("Paid type for " + student.getName() + " is not chosen");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void saveCurrentClass() {
        LocalDate date = datePicker.getValue();
        String classType = classTypeChoices.getValue();
        boolean sameDuration = sameDurationCheck.isSelected();

        double singleDurationLength = 0;
        if(sameDuration){
            singleDurationLength = Double.parseDouble(tSingleDuration.getText());
        }

        List<StuDurPaid> sdp = new ArrayList<>();

        for(Student student : chosenStudents){
            double duration, paid;

            if(sameDuration){
                duration = singleDurationLength;
            }else{
                DurationItem di = durationMap.get(student);
                duration = di.getDuration();
            }
            paid = paidMap.get(student).getPaid();
            String paymentType = paidMap.get(student).getPaidType();

            if(paid > 0) {
                long pid = modelManager.addPayment(student, date, paymentType, paid);
                sdp.add(new StuDurPaid(student.getID(), duration, pid));
            }else{
                sdp.add(new StuDurPaid(student.getID(), duration, -1));
            }
        }

        if(current_mode == EDIT_MODE){ //delete and replace the class.
            modelManager.getClassManager().deleteClass(currentlyEditingClass.getID());
        }

        long cid = modelManager.createAndAddClass(date, classType, sdp);


        sceneModel.setCurrentClass(modelManager.getClassManager().getClassByID(cid));
        sceneModel.setScene(ViewClassController.class.getSimpleName());
        reset();
    }

    public void cancelClicked(){
        if(current_mode == ADD_MODE){
            reset();
        }else {
            //Class back = currentlyEditingClass;
            currentlyEditingClass = null;
            sceneModel.setCurrentClass(null);
            sceneModel.backClicked();
            reset();
        }

    }

    private void removeStudent(ActionEvent e){
        ChosenStudent source = (ChosenStudent)e.getSource();
        chosenStudentsLabels.remove(source);
        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
        });
    }

    private void addStudent(Student selected) {
        chosenStudents.add(selected);
        ChosenStudent cs = new ChosenStudent(selected);
        cs.setOnAction(this::removeStudent);
        chosenStudentsLabels.add(cs);

        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);

            studentSearchBar.setText("");
        });

    }
}
