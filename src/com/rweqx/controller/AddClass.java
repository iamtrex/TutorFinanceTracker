package com.rweqx.controller;

import com.rweqx.model.DataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private List<Label> selectedStudents;

    @FXML
    private HBox selectedStudentsBox;

    @FXML
    private TextField studentsBar;

    @FXML
    private ListView<String> studentsListView;

    private ObservableList<String> searchMatchNames;

    @FXML
    private ListView<Label> durationListView;

    @FXML
    private ListView<Label> paidListView;

    @FXML
    private CheckBox sameDurationCheck;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> classTypeChoices;

    public AddClass(){
        searchMatchNames = FXCollections.observableArrayList();
        selectedStudents = new ArrayList<>();
    }
    public void initModel(DataModel model) {
        this.dataModel = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentsListView.setItems(searchMatchNames);

        studentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{

        });

        studentsBar.setOnAction(e->{
            if(studentsListView.getItems().size() > 0) {
                String s = studentsListView.getItems().get(0);
                //Add student!
                selectedStudents.add(new Label(s));
                selectedStudentsBox.getChildren().setAll(selectedStudents);
                studentsBar.setText("");

            }

        });

        studentsBar.textProperty().addListener((obs, oldVal, newVal) -> {
            String search = newVal;
            search = search.trim().toLowerCase();
            if (search.equals("")){
                searchMatchNames.removeAll();
            }else{
                String finalSearch = search;
                System.out.println("Match to " + finalSearch);

                searchMatchNames.setAll(dataModel.getStudentsModel().getStudents()
                                .stream()
                                .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                                .map(s -> s.getName())
                                .collect(Collectors.toList()));

                studentsListView.refresh();
            }


        });
    }
}

