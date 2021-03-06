package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
import com.rweqx.components.WarningPopUp;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.Student;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddEditClassController extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int currentMode = ADD_MODE; //Default is addClass.

    private List<SingleClassController> classes;


    private StringProperty currentSearch;

    private ObservableList<Student> chosenStudents;
    private List<ChosenStudent> chosenStudentsLabels;

    private ObservableList<Student> searchMatchNames;
    private ObservableList<String> classTypes;



    @FXML
    private VBox classesBox;

    @FXML
    private HBox selectedStudentsBox;

    @FXML
    private TextField studentSearchBar;
    @FXML
    private ListView<Student> searchListView;
    @FXML
    private AnchorPane upperLayer;
    @FXML
    private ScrollPane searchScroll;

    @FXML
    private ScrollPane classScroll;

    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDelete;
    @FXML
    private Button bPlusClass;

    private Class currentlyEditingClass;


    private void reset() {
        classes.clear();
        classesBox.getChildren().clear();

        currentSearch.set("");

        chosenStudents.clear();
        chosenStudentsLabels.clear();

        searchMatchNames.clear();
        searchListView.refresh();

        selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
        plusClassClicked(null);
    }

    @Override
    public void sceneLoaded(){

        reset(); //TODO -> Do I need this?
        if(sceneModel.getCurrentClass() != null){
            currentlyEditingClass = sceneModel.getCurrentClass();
            bDelete.setVisible(true);
            bSave.setText("Save Class");
            currentMode = EDIT_MODE;

            //TODO LOAD THE CLASS TO EDIT.
            for(long sid : currentlyEditingClass.getStudents()){
                addStudent(modelManager.getStudentManager().getStudentByID(sid));
            }
            classes.get(0).loadClass(currentlyEditingClass);
        }else{
            bSave.setText("Add Class");
            bDelete.setVisible(false);
            currentMode = ADD_MODE;
        }
    }
    public AddEditClassController() {
        classes = new ArrayList<>();

        currentSearch = new SimpleStringProperty();
        chosenStudents = FXCollections.observableArrayList();
        chosenStudentsLabels = new ArrayList<>();
        searchMatchNames = FXCollections.observableArrayList();
        classTypes = FXCollections.observableArrayList();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classScroll.setFitToWidth(true);
        classScroll.setFitToHeight(true);
        searchScroll.setFitToWidth(true);
        //Setup list to properly display students being searched.
        searchListView.setItems(searchMatchNames);
        searchListView.setCellFactory(stu -> new ListCell<>(){
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
        classesBox.heightProperty().addListener(observable -> classScroll.setVvalue(1D));

        searchListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->{
            Student selected = newVal;
            if(selected == null){
                return;
            }
            addStudent(selected);
        });

        studentSearchBar.localToSceneTransformProperty().addListener((obs, oldVal, newVal)->{
            //Setup the overlaying pane to properly be underneath the searchbar.
            //Bounds boundsInScene = studentSearchBar.localToParent(studentSearchBar.getBoundsInLocal());
            //System.out.println(boundsInScene);
            AnchorPane.setLeftAnchor(searchScroll,studentSearchBar.getLayoutX());
            AnchorPane.setTopAnchor(searchScroll,studentSearchBar.getLayoutY() + studentSearchBar.getHeight());
            AnchorPane.setRightAnchor(searchScroll, (upperLayer.getWidth() - studentSearchBar.getLayoutX() - studentSearchBar.getWidth()));
            AnchorPane.setBottomAnchor(searchScroll, (upperLayer.getHeight() - 205 - studentSearchBar.getLayoutY() - studentSearchBar.getHeight()));
        });
        studentSearchBar.focusedProperty().addListener((obs, oldVal, newVal)->{
            if(newVal){

                if(!studentSearchBar.getText().trim().equals("")){
                    upperLayer.setVisible(true);
                }
            }else{
                upperLayer.setVisible(false);
            }
        });

        studentSearchBar.setOnAction((e)->{
            if(searchListView.getItems().size() > 0) { //Same as selecting top student.
                Student s = searchListView.getItems().get(0);
                addStudent(s);
            }
        });
        studentSearchBar.textProperty().addListener((obs, oldVal, newVal)->{
            String search = newVal;
            search = search.trim().toLowerCase();

            if (search.equals("")){
                searchMatchNames.clear();
                searchListView.getSelectionModel().clearSelection();
                upperLayer.setVisible(false);

            }else{
                String finalSearch = search; //cuz apparently it has to be final :/?
                searchMatchNames.setAll(
                        modelManager.getStudentManager().getStudents()
                                .stream()
                                .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                                .collect(Collectors.toList()));
                upperLayer.setVisible(true);
            }
            searchListView.refresh();
        });
    }

    private void removeStudent(ActionEvent e){
        ChosenStudent source = (ChosenStudent)e.getSource();

        Logger.getInstance().log(this.getClass().getSimpleName(),"Removing Student " + source.getStudent().getName(), LogLevel.D);
        chosenStudentsLabels.remove(source);
        chosenStudents.remove(source.getStudent());
        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
        });
    }
    private void addStudent(Student selected) {
        if(!chosenStudents.contains(selected)) {
            //Don't add repeats.

            Logger.getInstance().log(this.getClass().getSimpleName(),"AddingStudent " + selected.getName(), LogLevel.D);

            chosenStudents.add(selected);
            ChosenStudent cs = new ChosenStudent(selected);
            cs.setOnAction(this::removeStudent);
            chosenStudentsLabels.add(cs);

            Platform.runLater(() -> {
                selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
            });
        }else{

            Logger.getInstance().log(this.getClass().getSimpleName(),"Repeat student, not adding." + selected.getName(), LogLevel.D);
        }
        studentSearchBar.setText("");
        searchListView.refresh();
    }

    public void plusDuplicateClassClicked(ActionEvent event){

        Logger.getInstance().log(this.getClass().getSimpleName(),"Adding duplicate of last class", LogLevel.D);
        SingleClassController toCopy = classes.get(classes.size()-1); //Get last class
        FXMLLoader singleClassLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/single-class.fxml"));

        try {
            Pane p = singleClassLoader.load();
            SingleClassController scc = singleClassLoader.getController();
            scc.initModel(modelManager, sceneModel);
            scc.setChosenStudents(chosenStudents);
            scc.copy(toCopy);
            classesBox.getChildren().add(p);
            classes.add(scc);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void plusClassSameDateClicked(ActionEvent event){

        Logger.getInstance().log(this.getClass().getSimpleName(),"Adding a new class with same date.", LogLevel.D);
        LocalDate date = classes.get(0).getDate();

        if(saveClicked(null)){
            //Set the class to be of the current date.
            classes.get(0).setDate(date);
        }



    }

    public void plusClassClicked(ActionEvent event){

        Logger.getInstance().log(this.getClass().getSimpleName(),"Adding a new class to chain.", LogLevel.D);
        FXMLLoader singleClassLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/single-class.fxml"));
        try {
            Pane p = singleClassLoader.load();
            SingleClassController scc = singleClassLoader.getController();
            scc.initModel(modelManager, sceneModel);
            scc.setChosenStudents(chosenStudents);

            classesBox.getChildren().add(p);
            classes.add(scc);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void deleteClicked(ActionEvent e){
        Logger.getInstance().log(this.getClass().getSimpleName(),"Deleting the editing class", LogLevel.D);
        modelManager.deleteClass(currentlyEditingClass.getID());
        reset();

        //Go back.
        currentlyEditingClass = null;
        sceneModel.setCurrentPayment(null);
        sceneModel.backClicked();

    }

    public void cancelClicked(ActionEvent e){
        Logger.getInstance().log(this.getClass().getSimpleName(),"Cancel changes to current class.", LogLevel.D);
        reset();

        currentlyEditingClass = null;
        sceneModel.setCurrentPayment(null);

        if(currentMode == EDIT_MODE) {
            sceneModel.backClicked();
        }else{
            //Don't move, just reload.
            sceneLoaded(); //Call self's loaded scene to reset UI.
        }
    }


    public boolean saveClicked(ActionEvent e){

        Logger.getInstance().log(this.getClass().getSimpleName(),"Saving class.", LogLevel.D);

        if(classes.size() != classesBox.getChildren().size()){
            Logger.getInstance().log(getClass().getSimpleName(),"Different size of classes detected! Possible loss of data!", LogLevel.S);
        }

        if(chosenStudents.size() == 0 || classes.size() == 0){
            new WarningPopUp("No students/class to add.");
            return false; //Don't add.
        }
        for(SingleClassController scc : classes){
            boolean checks = scc.isValidInput();
            if(!checks){
                Logger.getInstance().log(this.getClass().getSimpleName(),"Check failed for class # " + classes.indexOf(scc), LogLevel.D);
                return false;
            }
        }

        //All valid, building classes.
        for(SingleClassController scc : classes){
            long cid = scc.buildAndAddClass();

        }

        //Successfully saved... Delete the previous class:
        if(currentlyEditingClass != null){
            Logger.getInstance().log(this.getClass().getSimpleName(),"Since overwrite successful, deleting previous class", LogLevel.D);
            modelManager.deleteClass(currentlyEditingClass.getID());
            currentlyEditingClass = null;
        }

        //TODO CONSIDER TRANSITIONS -> MOVE TO END SCREEN?
        //Have another "save and add another class with new students" button
        reset();
        //TODO MAKE THIS TRANSITION BETTER...
        sceneModel.setCurrentClass(null);
        sceneModel.setCurrentDate(LocalDate.now());
        //sceneModel.setScene(DayViewController.class.getSimpleName()); Don't change scene...
        return true;

    }
    public void minusClassClicked(ActionEvent e){

        Logger.getInstance().log(this.getClass().getSimpleName(),"Removing last class in chain.", LogLevel.D);

        classes.remove(classes.size()-1);
        classesBox.getChildren().remove(classesBox.getChildren().size()-1);

        if(classes.size() == 0){
            plusClassClicked(null); //Just add the class back, so essentially reset the class.
        }
        if(classes.size() == 1){
            if(currentMode == ADD_MODE){
                bSave.setText("Add class");
            }else {
                bSave.setText("Save class");
            }
        }else{
            if(currentMode == ADD_MODE){
                bSave.setText("Add classes");
            }else{
                bSave.setText("Save changes");
            }

        }
    }

    @FXML
    private StackPane pane;

    public void loseFocus(){
        pane.requestFocus();
    }

}