package com.rweqx.controller;

import com.rweqx.components.StudentListButton;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import com.rweqx.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class StudentListSearchController extends BaseController implements Initializable {

    private StudentListButton.CommandInterface fnCallback;
    private ObservableList<Student> filteredStudents;
    private List<Student> allStudents;
    private Map<Student, Boolean> selectedMap;


    private StringProperty currentSearch;

    @FXML
    private ListView<Student> studentsListView;

    @FXML
    private TextField tSearchBar;


    public void loadClass(SingleClassController scc){
        //Get Selected Students and sync that with selected map...
        Set<Student> selectedStudents = new HashSet<>(scc.getSelectedStudents());

        selectedMap.keySet().forEach((s) -> {
            selectedMap.put(s, selectedStudents.contains(s));
        });

    }

    public void setCallback(StudentListButton.CommandInterface fnCallback){
        this.fnCallback = fnCallback;
    }


    @Override
    public void sceneLoaded(){
        //Load Students
        filteredStudents.setAll(modelManager.getStudentManager().getStudents());
        FXCollections.sort(filteredStudents, Comparator.comparing(Student::getName));
        studentsListView.refresh();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allStudents = new ArrayList<>();
        currentSearch = new SimpleStringProperty();
        filteredStudents = FXCollections.observableArrayList();

        studentsListView.setItems(filteredStudents);
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


        tSearchBar.textProperty().addListener((obs, oldVal, newVal)->{
            String search = newVal;
            search = search.trim().toLowerCase();

            if (search.equals("")){
                filteredStudents.setAll(modelManager.getStudentManager().getStudents());
            } else {
                String finalSearch = search; //cuz apparently it has to be final :/?
                filteredStudents.setAll(
                        modelManager.getStudentManager().getStudents()
                                .stream()
                                .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                                .collect(Collectors.toList()));
            }

            FXCollections.sort(filteredStudents, Comparator.comparing(Student::getName));
            studentsListView.refresh();
        });

    }
}
