package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.DurationCell;
import com.rweqx.model.DataModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddClass implements Initializable {

    private DataModel dataModel;

    private StringProperty currentSearch;

    private ObservableList<String> chosenStudents;
    private List<ChosenStudent> chosenStudentsLabels;


    @FXML
    private HBox selectedStudentsBox;

    @FXML
    private TextField studentsBar;

    @FXML
    private ListView<String> studentsListView;

    private ObservableList<String> searchMatchNames;

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

    public AddClass(){
        currentSearch = new SimpleStringProperty();
        chosenStudents = FXCollections.observableArrayList();
        searchMatchNames = FXCollections.observableArrayList();

        chosenStudentsLabels = new ArrayList<>();
    }
    public void initModel(DataModel model) {
        this.dataModel = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentsListView.setItems(searchMatchNames);

        durationListView.setItems(chosenStudents);
        durationListView.setCellFactory((list)->{
            return new DurationCell();
        });

        studentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{

        });

        studentsBar.setOnAction(e->{
            if(studentsListView.getItems().size() > 0) {
                String s = studentsListView.getItems().get(0);
                //Add student!
                chosenStudents.add(s);
                ChosenStudent cs = new ChosenStudent(s);
                chosenStudentsLabels.add(cs);
                cs.setOnAction(this::removeButton);

                selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
                currentSearch.set("");
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

    private void removeButton(ActionEvent e) {
        ChosenStudent cs = (ChosenStudent)e.getSource();
        chosenStudents.remove(cs.getName());

        selectedStudentsBox.getChildren().setAll();

    }
}

