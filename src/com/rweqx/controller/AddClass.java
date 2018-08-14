package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.DurationCell;
import com.rweqx.components.PaidCell;
import com.rweqx.model.DataModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddClass implements Initializable {

    private DataModel dataModel;

    private StringProperty currentSearch;

    private ObservableList<String> chosenStudents;
    private List<ChosenStudent> chosenStudentsLabels;

    private ObservableList<String> searchMatchNames;
    private ObservableList<String> classTypes;


    @FXML
    private HBox selectedStudentsBox;
    @FXML
    private TextField studentsBar;
    @FXML
    private ListView<String> studentsListView;
    @FXML
    private ListView<String> durationListView;
    @FXML
    private ListView<String> paidListView;
    @FXML
    private CheckBox sameDurationCheck;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> classTypeChoices;
    @FXML
    private TextField tSingleDuration;


    public AddClass(){
        currentSearch = new SimpleStringProperty();
        chosenStudents = FXCollections.observableArrayList();
        searchMatchNames = FXCollections.observableArrayList();
        classTypes = FXCollections.observableArrayList();

        classTypes.addAll("1 on 1", "Group", "1 on 2", "1 on 3");


        chosenStudentsLabels = new ArrayList<>();
    }
    public void initModel(DataModel model) {
        this.dataModel = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tSingleDuration.setVisible(false);

        classTypeChoices.setItems(classTypes);

        sameDurationCheck.selectedProperty().addListener((obs, oldVal, newVal)->{
            if(sameDurationCheck.isSelected()) {
                durationListView.setVisible(false);
                tSingleDuration.setVisible(true);
            }else{
                durationListView.setVisible(true);
                tSingleDuration.setVisible(false);
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

        durationListView.setItems(chosenStudents);
        durationListView.setCellFactory(param -> new DurationCell());

        paidListView.setItems(chosenStudents);
        paidListView.setCellFactory(param-> new PaidCell());

        studentsBar.setOnAction(e->{
            if(studentsListView.getItems().size() > 0) {
                String s = studentsListView.getItems().get(0);
                addStudent(s);
            }

        });

        currentSearch.addListener((obs, oldVal, newVal)->{
            System.out.println("Curr Search Changed");
            studentsBar.setText(newVal);

            String search = newVal;
            search = search.trim().toLowerCase();

            if (search.equals("")){
                System.out.println("Removing all...");
                searchMatchNames.removeAll();
                studentsListView.getSelectionModel().clearSelection();
                studentsListView.getItems().clear();

            }else{
                String finalSearch = search;
                System.out.println("Match to " + finalSearch);

                searchMatchNames.setAll(dataModel.getStudentsModel().getStudents()
                        .stream()
                        .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                        .map(s -> s.getName())
                        .collect(Collectors.toList()));
            }

            studentsListView.refresh();
        });

        studentsBar.textProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Student Bar Changed");
            if(!newVal.equals(currentSearch.get())){
                currentSearch.set(newVal);

            }
        });
    }

    private void addStudent(String s) {
        //Add student!
        System.out.println("Adding Student " + s);
        chosenStudents.add(s);
        ChosenStudent cs = new ChosenStudent(s);
        chosenStudentsLabels.add(cs);
        cs.setOnAction(this::removeButton);

        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
            currentSearch.set("");
        });
    }

    private void removeButton(ActionEvent e) {
        ChosenStudent cs = (ChosenStudent)e.getSource();

        System.out.println("Removing Student " + cs.getName());
        chosenStudents.remove(cs.getName());
        chosenStudentsLabels.remove(cs);
        selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);

    }
}

