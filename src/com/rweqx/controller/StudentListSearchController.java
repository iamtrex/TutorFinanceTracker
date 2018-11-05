package com.rweqx.controller;

import com.rweqx.components.StudentListButton;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import com.rweqx.model.Student;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class StudentListSearchController extends BaseController {

    private StudentListButton.CommandInterface fnCallback;
    private ObservableList<Student> allStudents;
    private Map<Student, Boolean> selectedMap;

    @FXML
    private ListView<Student> searchListView;

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

        allStudents = modelManager.getStudentManager().getStudents();
    }


}
