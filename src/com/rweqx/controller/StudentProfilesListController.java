package com.rweqx.controller;

import com.rweqx.components.StudentButton;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import com.rweqx.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import org.controlsfx.glyphfont.INamedCharacter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * TODO - Add flag for when Student is added/deleted to refresh buttons on interface. (in sceneModel)
 *
 *
 */
public class StudentProfilesListController extends BaseController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane studentsPane;

    @FXML
    private TextField searchbar;

    @FXML
    private Button bNewStudent;

    private List<StudentButton> studentButtons;
    private String currSearch;

    public StudentProfilesListController(){
        studentButtons = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);
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
            studentsPane.getChildren().setAll(
                    studentButtons.stream()
                    .filter(b -> b.getStudentName().toLowerCase().contains(s))
                    .collect(Collectors.toList()));

        }
    }

    @Override
    public void sceneLoaded(){
        studentButtons.clear();
        List<Student> students = modelManager.getStudentManager().getStudents();
        for(Student s : students){
            StudentButton sb = new StudentButton(s.getID(), s.getName());
            studentButtons.add(sb);
            sb.setOnAction(this::handleStudentButton);
        }
        studentButtons.sort(Comparator.comparing(StudentButton::getStudentName));
        studentsPane.getChildren().setAll(studentButtons);
    }


    private void handleStudentButton(ActionEvent e){
        StudentButton sb = (StudentButton)e.getSource();
        System.out.println(sb.getStudentName() + " button in student profile clicked");
        System.out.println(modelManager.getStudentManager().getStudentByID(sb.getStudentID()));
        sceneModel.setCurrentStudent(modelManager.getStudentManager().getStudentByID(sb.getStudentID()));
        sceneModel.setScene(StudentProfileController.class.getSimpleName());

    }

    public void newStudentClicked(ActionEvent e){
        //sceneModel.setCurrentStudent(null);
        sceneModel.setScene(AddEditStudentController.class.getSimpleName());
    }


}
