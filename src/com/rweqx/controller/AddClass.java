package com.rweqx.controller;

import com.rweqx.components.*;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.DataModel;
import com.rweqx.components.WarningPopUp;
import com.rweqx.model.Student;
import com.rweqx.model.StudentInClassElement;
import com.rweqx.util.DateUtil;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class AddClass implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int current_mode = ADD_MODE; //Default is addClass.

    private DataModel dataModel;

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

    public void selectMode(int i){
        //Selects mode.
        current_mode = i;
    }

    public void initModel(DataModel model) {
        this.dataModel = model;
    }

    public void loadClass(Class c) {
        //TODO SETUP EDITING... RIGHT NOW WON'T SUPPORT IT...
        reset();

        for(Student s : c.getStudents()){
            String name = s.getName();
            addStudent(name);
            DurationItem di = durationMap.get(name);
            di.setDuration(c.getDurationFromStudent(s.getID()));

            PaidItem pi = paidMap.get(name);
            if(c.getPaidIDFromStudent(s.getID()) != -1) {
                pi.setPaid(dataModel.getPaymentModel().getPaymentByID(c.getPaidIDFromStudent(s.getID())));
            }

        }
    }

    public void reset(){
        durationMap.clear();
        paidMap.clear();
        currentSearch.setValue("");
        chosenStudents.clear();
        searchMatchNames.clear();
        chosenStudentsLabels.clear();

        classTypeChoices.setValue(null);

        durationBox.getChildren().clear();
        paidBox.getChildren().clear();

        sameDurationCheck.setSelected(false);
        datePicker.setValue(LocalDate.now());

        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
            currentSearch.set("");
        });
    }
    public AddClass(){
        durationMap = new HashMap<>();
        paidMap = new HashMap<>();

        currentSearch = new SimpleStringProperty();
        chosenStudents = FXCollections.observableArrayList();
        searchMatchNames = FXCollections.observableArrayList();
        classTypes = FXCollections.observableArrayList();

        classTypes.addAll("1 on 1", "Group", "1 on 2", "1 on 3");


        chosenStudentsLabels = new ArrayList<>();
    }

    private void addStudent(String s) {
        //Add student!
        System.out.println("Adding Student " + s);
        chosenStudents.add(s);
        ChosenStudent cs = new ChosenStudent(s);
        chosenStudentsLabels.add(cs);
        cs.setOnAction(this::removeStudent);

        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
            currentSearch.set("");
        });
    }

    private void removeStudent(ActionEvent e) {
        ChosenStudent cs = (ChosenStudent)e.getSource();

        System.out.println("Removing Student " + cs.getName());
        chosenStudents.remove(cs.getName());
        chosenStudentsLabels.remove(cs);
        selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());

        classTypeChoices.setItems(classTypes);
        scrollDuration.setFitToWidth(true);
        scrollPaid.setFitToWidth(true);

        bSave.setOnAction((e)->{
            if(saveAndSwitchToViewClass()){ //If succeeded
                reset();
            }
        });

        bCancel.setOnAction((e)->{
           reset();
        });

        sameDurationCheck.selectedProperty().addListener((obs, oldVal, newVal)->{
            if(sameDurationCheck.isSelected()) {
                durationBox.setVisible(false);
                tSingleDuration.setVisible(true);
            }else{
                durationBox.setVisible(true);
                tSingleDuration.setVisible(false);
            }
        });

        chosenStudents.addListener((ListChangeListener<String>) c -> {
            if(c.next()) {

                if (c.wasPermutated() || c.wasUpdated()) {
                    System.out.println(c);
                } else {
                    //TODO - BUG IF SAME NAME.
                    for (String remItm : c.getRemoved()) {
                        DurationItem di = durationMap.get(remItm);
                        if(di != null){
                            durationBox.getChildren().remove(di);
                        }
                        PaidItem pi = paidMap.get(remItm);
                        if(pi != null){
                            paidBox.getChildren().remove(pi);
                        }
                    }

                    for (String addItm : c.getAddedSubList()) {
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


        studentsListView.setItems(searchMatchNames);
        studentsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            String selectedItem = studentsListView.getSelectionModel().getSelectedItem();
            if(selectedItem == null)
                return;
            addStudent(selectedItem);
        });

        studentsBar.setOnAction(e->{ //Add First item in list on enter press.
            if(studentsListView.getItems().size() > 0) {
                String s = studentsListView.getItems().get(0);
                addStudent(s);
            }
        });

        currentSearch.addListener((obs, oldVal, newVal)->{
            studentsBar.setText(newVal);

            String search = newVal;
            search = search.trim().toLowerCase();

            if (search.equals("")){
                searchMatchNames.clear();
                studentsListView.getSelectionModel().clearSelection();
                studentsListView.getItems().clear();

            }else{
                String finalSearch = search;

                searchMatchNames.setAll(dataModel.getStudentsModel().getStudents()
                        .stream()
                        .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                        .map(s -> s.getName())
                        .collect(Collectors.toList()));
            }

            studentsListView.refresh();
        });

        studentsBar.textProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal.equals(currentSearch.get())){
                currentSearch.set(newVal);

            }
        });
    }

    private boolean saveAndSwitchToViewClass() {
        String classType = classTypeChoices.getValue();
        LocalDate localDate = datePicker.getValue();
        boolean sameDuration = sameDurationCheck.isSelected();
        double singleDurationLength = 0.0;

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
            for(String student: chosenStudents){
                DurationItem di = durationMap.get(student);
                if(di == null){
                    Logger.getInstance().log("Duration item could not be found for " + student, LogLevel.S);
                    return false;
                }else if(di.getDuration() <= 0){
                    new WarningPopUp("Duration for " + student + " is 0 or malformed");
                    return false;
                }
            }
        }
        //All Checks passed:

        Date date = DateUtil.localDateToDate(localDate);

        List<StudentInClassElement> studElts = new ArrayList<>();
        for(String student : chosenStudents) {
            double duration, paid;
            if(sameDuration){
                duration = singleDurationLength;
            }else{
                DurationItem di = durationMap.get(student);
                if(di != null){
                    duration = di.getDuration();
                }else {
                    Logger.getInstance().log("Duration Item was null, data was corrupted, cannot add student :(", LogLevel.S);
                    throw new IllegalStateException();
                }
            }

            PaidItem pi = paidMap.get(student);
            if (pi != null) {
                paid = pi.getPaid();
            }else{
                Logger.getInstance().log("Paid item was null, data was corrupted, cannot add student :(", LogLevel.S);
                throw new IllegalStateException();
            }

            studElts.add(new StudentInClassElement(student, duration, paid));
        }

        long classID = dataModel.createAndAddClass(classType, date, studElts);
        switchToViewClass(classID);
        return true;
    }

    private void switchToViewClass(long classID) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/view-class.fxml"));
        try {
            Pane p = loader.load();

            ViewClass viewClass = loader.getController();
            viewClass.initModel(dataModel);
            viewClass.giveClass(classID);

            ViewNavigator.loadScene(p);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}

