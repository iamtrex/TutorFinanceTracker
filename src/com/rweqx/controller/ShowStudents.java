package com.rweqx.controller;

import com.rweqx.components.StudentButton;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.DataModel;
import com.rweqx.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowStudents implements Initializable {

    private DataModel dataModel;

    @FXML
    private FlowPane studentsPane;

    @FXML
    private TextField searchbar;


    private List<StudentButton> studentButtons;
    private String currSearch;

    public ShowStudents(){
        studentButtons = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currSearch = searchbar.getText();
        searchbar.textProperty().addListener((obs, oldVal, newVal)->{
            filterList(newVal);
        });
    }


    private void filterList(String searchString) {
        String s = searchString.trim().toLowerCase();

        if(s.equals("")){
            //Blank string, restore all.
            studentsPane.getChildren().setAll(studentButtons);
        }else{
            studentsPane.getChildren().setAll(studentButtons.stream()
                    .filter(b -> b.getStudentName().toLowerCase().contains(s))
                    .collect(Collectors.toList()));

        }
    }


    public void initModel(DataModel dataModel) {
        this.dataModel = dataModel;

        List<Student> students = dataModel.getStudentsModel().getStudents();

        for(Student s : students){
            StudentButton sb = new StudentButton(s.getID(), s.getName());
            studentButtons.add(sb);
            sb.setOnAction(this::handleStudentButton);

        }

        studentsPane.getChildren().setAll(studentButtons);

    }

    private void handleStudentButton(ActionEvent e){
        StudentButton sb = (StudentButton)e.getSource();

        //Open student profile.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/student-profile.fxml"));
            Pane p = loader.load();
            StudentProfileController spc = loader.getController();
            spc.initModel(dataModel);
            spc.setStudent(sb.getStudentID());
            ViewNavigator.loadScene(p);
        }catch(IOException io){
            Logger.getInstance().log("Cannot load Student " + sb.getStudentName(), LogLevel.S);
        }

    }
}
