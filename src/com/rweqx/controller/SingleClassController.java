package com.rweqx.controller;

import com.rweqx.components.ClickEditTextField;
import com.rweqx.components.WarningPopUp;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import com.rweqx.model.Student;
import com.rweqx.util.StringUtil;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleClassController extends BaseController{


    @FXML
    private Pane pane;
    @FXML
    private VBox internals;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> classTypeChoices;

    @FXML
    private CheckBox sameDuration;

    @FXML
    private GridPane titleGrid;
    @FXML
    private GridPane classDetailsGrid;

    private Map<Student, Label> nameMap;
    private Map<Student, TextField> durationMap;
    private Map<Student, TextField> rateMap;
    private Map<Student, TextField> paidMap;
    private Map<Student, ComboBox> paymentTypeMap;



    private ObservableList<Student> chosenStudents;

    public SingleClassController(){
        nameMap = new HashMap<>();
        durationMap = new HashMap<>();
        rateMap = new HashMap<>();
        paidMap = new HashMap<>();
        paymentTypeMap = new HashMap<>();
    }

    @Override
    public void initModel(ModelManager modelManager, SceneModel sceneModel){
        super.initModel(modelManager, sceneModel);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(this::loadComponents);
        classTypeChoices.setItems(modelManager.getClassTypes().getTypesList());

        classTypeChoices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            //Update rates for the selected class type.
            updateClassRateAll(newVal);
        });

        sameDuration.selectedProperty().addListener((obs, oldVal, newVal)->{

            for(Student s : chosenStudents){
                durationMap.get(s).setVisible(!newVal);
            }
            //Always show first.
            Student first = chosenStudents.get(0);
            durationMap.get(first).setVisible(true);

        });

        datePicker.setValue(LocalDate.now());

    }

    private void updateClassRateAll(String classType) {
        for(Student s : chosenStudents){
            if(classType != null) {
                rateMap.get(s).setText(String.valueOf(
                        s.getLatestPaymentRates().getRateByType(classType)));
            }else{
                rateMap.get(s).setText("");
            }
        }
    }

    public void setChosenStudents(ObservableList<Student> students){
        this.chosenStudents = students;
    }


    public void loadComponents(){
        //Wait for modelManager and chosenStudents
        while(modelManager == null || chosenStudents == null){
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        chosenStudents.addListener((ListChangeListener<Student>) c->{
            if(c.next()) {
                if (c.wasPermutated() || c.wasUpdated()) {
                    System.out.println(c);
                } else {
                    for (Student s : c.getRemoved()) {
                        removeStudent(s);
                    }

                    for (Student s : c.getAddedSubList()) {
                        addStudent(s);
                    }
                }
            }
        });

        //Register current students;
        System.out.println("Registering current students " + chosenStudents.size());
        chosenStudents.forEach(s->{
            addStudent(s);
        });

    }

    private void removeStudent(Student s) {
        classDetailsGrid.getChildren().removeAll(nameMap.get(s), durationMap.get(s), rateMap.get(s), paidMap.get(s), paymentTypeMap.get(s));
        nameMap.remove(s);
        durationMap.remove(s);
        rateMap.remove(s);
        paidMap.remove(s);
        paymentTypeMap.remove(s);
        updateHeight();

    }

    private void updateHeight(){
        pane.setMinHeight(chosenStudents.size()*30 + 70); //Gives an approx nice feel for students
    }

    private void addStudent(Student s) {
        nameMap.put(s, new Label(s.getName()));
        durationMap.put(s, new TextField());
        rateMap.put(s, new ClickEditTextField());

        String type = classTypeChoices.getSelectionModel().getSelectedItem();
        if(type != null){
            rateMap.get(s).setText(String.valueOf(s.getLatestPaymentRates().getRateByType(type)));
        }

        paidMap.put(s, new TextField());

        ComboBox choices = new ComboBox();
        choices.setStyle("-fx-pref-width: 1000;");
        choices.setItems(modelManager.getPaymentTypes().getTypesList());
        paymentTypeMap.put(s, choices);
        Platform.runLater(()->{
            int numRow = classDetailsGrid.getRowCount();
            classDetailsGrid.addRow(numRow, nameMap.get(s), durationMap.get(s), rateMap.get(s), paidMap.get(s), paymentTypeMap.get(s));
            updateHeight();
        });
    }

    public long buildAndAddClass() {


        return -1;
    }

    public boolean isValidInput() {
        if(datePicker.getValue() == null){
            new WarningPopUp("No date selected");
            return false;
        }
        for(Student s : chosenStudents){
            if(!StringUtil.isPositiveNumber(durationMap.get(s).getText())){
                new WarningPopUp("Invalid Duration for student " + s.getName());
                return false;
            }else if(!StringUtil.isPositiveNumber(paidMap.get(s).getText())){

                //MIGHT JUST BE BLANK... TODO.
            }
        }
        return true;
    }
}
