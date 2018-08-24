package com.rweqx.controller;

import com.rweqx.components.ChosenStudent;
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
import java.util.*;
import java.util.stream.Collectors;

public class AddEditClassController2 extends BaseController implements Initializable {

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


    }
    public AddEditClassController2() {
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

        studentSearchBar.focusedProperty().addListener((obs, oldVal, newVal)->{
            if(newVal){
                Bounds boundsInScene = studentSearchBar.localToParent(studentSearchBar.getBoundsInLocal());
                System.out.println(boundsInScene);
                AnchorPane.setLeftAnchor(searchScroll,studentSearchBar.getLayoutX());
                AnchorPane.setTopAnchor(searchScroll,studentSearchBar.getLayoutY() + studentSearchBar.getHeight());
                AnchorPane.setRightAnchor(searchScroll, (upperLayer.getWidth() - studentSearchBar.getLayoutX() - studentSearchBar.getWidth()));
                AnchorPane.setBottomAnchor(searchScroll, (upperLayer.getHeight() - 205 - studentSearchBar.getLayoutY() - studentSearchBar.getHeight()));
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
        chosenStudentsLabels.remove(source);
        chosenStudents.remove(source.getStudent());
        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);
        });
    }
    private void addStudent(Student selected) {
        System.out.println("Woudl add student " + selected.getName());

        chosenStudents.add(selected);
        ChosenStudent cs = new ChosenStudent(selected);
        cs.setOnAction(this::removeStudent);
        chosenStudentsLabels.add(cs);

        Platform.runLater(()->{
            selectedStudentsBox.getChildren().setAll(chosenStudentsLabels);

            studentSearchBar.setText("");
        });

        studentSearchBar.setText("");
        searchListView.refresh();
        loseFocus();
    }

    @Override
    public void sceneLoaded(){
        if(sceneModel.getCurrentClass() != null){
            currentlyEditingClass = sceneModel.getCurrentClass();
            bDelete.setVisible(true);
            bSave.setText("Save Class");
            currentMode = EDIT_MODE;
        }else{
            plusClassClicked(null);
            bSave.setText("Add Class");
            bDelete.setVisible(false);
            currentMode = ADD_MODE;
        }
    }

    public void plusClassClicked(ActionEvent event){
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
        System.out.println("Delete editing class. ");
        reset();
    }

    public void cancelClicked(ActionEvent e){
        System.out.println("Try to cancel");
        reset();
    }


    public void saveClicked(ActionEvent e){
        System.out.println("Try to save");
        reset();
    }
    public void minusClassClicked(ActionEvent e){
        //Remove last.
        classes.remove(classes.size()-1);
        classesBox.getChildren().remove(classesBox.getChildren().size()-1);
    }

    @FXML
    private StackPane pane;

    public void loseFocus(){
        pane.requestFocus();
    }

}