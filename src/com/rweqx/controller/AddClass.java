package com.rweqx.controller;

import com.rweqx.components.*;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.DataModel;
import com.rweqx.model.StudentInClassElement;
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

    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

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

    }

    public void initModel(DataModel model) {
        this.dataModel = model;
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
            if(true){ //TODO Perform checks
                String classType = classTypeChoices.getValue();
                LocalDate localDate = datePicker.getValue();
                Date date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));

                List<StudentInClassElement> studentElts = new ArrayList<>();

                double sameDuration = 0;
                if(sameDurationCheck.isSelected()) {
                    try {
                        sameDuration = Double.parseDouble(tSingleDuration.getText().trim());
                    } catch (Exception exp) {

                        exp.printStackTrace();
                    }
                }

                System.out.println(chosenStudents.size());
                for(String student : chosenStudents) {
                    double duration, paid;
                    if(sameDurationCheck.isSelected()){
                        duration = sameDuration;
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

                    studentElts.add(new StudentInClassElement(student, duration, paid));
                }
                long classID = dataModel.getClassManager().createAndAddClass(classType, date, studentElts);

                switchToViewClass(classID);

            }
        });

        bCancel.setOnAction((e)->{
           ViewNavigator.loadScene("/com/rweqx/ui/add-class.fxml");
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

        studentsBar.setOnAction(e->{
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
                searchMatchNames.removeAll();
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

