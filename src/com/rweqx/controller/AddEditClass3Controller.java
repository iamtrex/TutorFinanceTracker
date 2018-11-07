package com.rweqx.controller;

import com.rweqx.components.SideBar;
import com.rweqx.components.StudentListButton;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import com.rweqx.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddEditClass3Controller extends BaseController implements Initializable {
    public static final int ADD_MODE = 0;
    public static final int EDIT_MODE = 1;

    private int currentMode = ADD_MODE;

    private List<SingleClassController> classes;


    @FXML
    private HBox addClassToolbar;

    @FXML
    private Button bSave;

    @FXML
    private MenuButton bAddOptions;

    @FXML
    private MenuItem bNewClass;

    @FXML
    private MenuItem bAddDuplicateClass;

    @FXML
    private MenuItem bAddDuplicateSameDateClass;

    @FXML
    private Button bCancel;

    @FXML
    private ScrollPane scrollClasses;

    @FXML
    private VBox classBox;

    @FXML
    private SideBar addClassSideBar;

    private StudentListSearchController studentListSearchController;


    @Override
    public void sceneLoaded(){
        //Call dependancy's scene loaded:
        if(studentListSearchController != null){
            studentListSearchController.sceneLoaded();
        }
    }

    @Override
    public void initModel(ModelManager mm, SceneModel sm){
        super.initModel(mm, sm);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/student-list-search.fxml"));
            Pane studentListSearch = loader.load();
            StudentListSearchController slsc = loader.getController();
            slsc.initModel(modelManager, sceneModel);
            slsc.setCallback((student, isSelected) -> onStudentSelected(student, isSelected));
            this.studentListSearchController = slsc;
            addClassSideBar.setContent(studentListSearch);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bSave.setOnAction(this::onSaveClicked);
    }

    private void onStudentSelected(Student student, boolean isSelected) {
        //TODO DO SOMETHING.
    }

    public void onSaveClicked(ActionEvent e){
        addClassSideBar.openSideBar(); //TODO remove this.


    }

    public void onCancelClicked(ActionEvent e){

    }

    public void onAddClassClicked(ActionEvent e){
        Object source = e.getSource();
        if(source == bAddDuplicateClass){

        }else if(source == bAddDuplicateSameDateClass){

        }else if(source == bNewClass){

        }

    }







}
