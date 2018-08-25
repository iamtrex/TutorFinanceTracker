package com.rweqx.controller;

import com.rweqx.components.WarningPopUp;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.Class;
import com.rweqx.model.Payment;
import com.rweqx.model.SceneModel;
import com.rweqx.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddEditPaymentController extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int current_mode = ADD_MODE; //Default is addClass.
    private Payment currentlyEditingPayment;

    private ObservableList<Student> searchMatchNames;
    private StringProperty currentSearch;
    private Student selectedStudent;

    @FXML
    private TextField tStudent;

    @FXML
    private ListView<Student> studentsListView;

    @FXML
    private TextField tPaid;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox paymentType;

    @FXML
    private Button bCancel;

    @FXML
    private Button bSave;
    @FXML
    private Button bDelete;

    public AddEditPaymentController(){
        currentSearch = new SimpleStringProperty();
        searchMatchNames = FXCollections.observableArrayList();
    }

    @Override
    public void initModel(ModelManager modelManager, SceneModel sceneModel){
        super.initModel(modelManager, sceneModel);
        paymentType.setItems(modelManager.getPaymentTypes().getTypesList());
    }


    @Override
    public void sceneLoaded(){

        Payment p = sceneModel.getCurrentPayment();
        if(p == null){
            //regular add mode.
            currentlyEditingPayment = null;
            current_mode = ADD_MODE;
            bDelete.setVisible(false);
            bSave.setText("Add Payment");

            System.out.println("Current mode - adding");

        }else{
            //Load class mode
            currentlyEditingPayment = p;
            current_mode = EDIT_MODE;

            selectStudent(modelManager.getStudentManager().getStudentByID(p.getStudentID()));
            tPaid.setText(String.valueOf(p.getPaymentAmount()));
            paymentType.setValue(p.getPaymentType());
            datePicker.setValue(p.getDate());
            bSave.setText("Save");
            bDelete.setVisible(true);
            System.out.println("Current mode - editing");
        }
    }

    public void reset(){
        searchMatchNames.clear();
        tStudent.setText("");
        tPaid.setText("");
        paymentType.getSelectionModel().clearSelection();
        datePicker.setValue(LocalDate.now());
    }

    public void saveClicked(){
        if(runChecks()){
            saveCurrentPayment();
        }
    }

    private void saveCurrentPayment() {

        long pid;
        if(current_mode == EDIT_MODE){
            pid = modelManager.replacePayment(currentlyEditingPayment.getID(),
                    selectedStudent, datePicker.getValue(), paymentType.getValue().toString(), Double.parseDouble(tPaid.getText().trim()));
        }else {
            pid = modelManager.addPayment(selectedStudent, datePicker.getValue(), paymentType.getValue().toString(), Double.parseDouble(tPaid.getText().trim()));

        }

        Payment p = modelManager.getPaymentManager().getPaymentByID(pid);
        sceneModel.setCurrentPayment(p);
        sceneModel.setCurrentDate(datePicker.getValue());
        sceneModel.setScene(DayViewController.class.getSimpleName());
        reset();
    }

    private boolean runChecks() {
        if(selectedStudent == null){
            new WarningPopUp("No student selected");
            return false;
        }else if(datePicker.getValue() == null){

            new WarningPopUp("No date chosen" );
            return false;
        }else if(paymentType.getValue() == null){
            new WarningPopUp("No payment type chosen");
            return false;
        }
        try{
            Double.parseDouble(tPaid.getText().trim());
        }catch(NumberFormatException e){
            //Silent e.printStackTrace();
            new WarningPopUp("Paid amount isn't a recognizable number.");
        }
        return true;
    }

    public void deleteClicked(ActionEvent e){
        modelManager.deletePayment(currentlyEditingPayment.getID());
        currentlyEditingPayment = null;
        sceneModel.setCurrentPayment(null);
        sceneModel.backClicked();
        reset();
    }
    public void cancelClicked(){
        if(current_mode == ADD_MODE) {
            reset();
        }else{
            currentlyEditingPayment = null;
            sceneModel.setCurrentPayment(null);
            sceneModel.backClicked();
        }
    }

    public void selectStudent(Student student){
        this.selectedStudent = student;
        if(student != null) {
            tStudent.setText(student.getName());
        }else{
            tStudent.setText("");
        }
        currentSearch.set(""); //Wipe search after selecting student.
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsListView.setItems(searchMatchNames);
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

        studentsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->{
            Student selected = newVal;
            if(selected == null){
                return;
            }
            selectStudent(selected);
        });
        tStudent.textProperty().addListener((obs, oldVal, newVal) ->{
            currentSearch.set(newVal);
        });
        tStudent.setOnAction((e)->{
            if(studentsListView.getItems().size() > 0) { //Same as selecting top student.
                Student s = studentsListView.getItems().get(0);
                selectStudent(s);
            }
        });
        tStudent.setOnMousePressed((e)->{
            selectStudent(null);
        });
        currentSearch.addListener((obs, oldVal, newVal) ->{
            String search = newVal;
            search = search.trim().toLowerCase();

            if (search.equals("")){
                searchMatchNames.clear();
                studentsListView.getSelectionModel().clearSelection();
                studentsListView.getItems().clear();
            }else{
                String finalSearch = search; //cuz apparently it has to be final :/?
                searchMatchNames.setAll(
                        modelManager.getStudentManager().getStudents()
                                .stream()
                                .filter(s -> s.getName().toLowerCase().contains(finalSearch))
                                .collect(Collectors.toList()));

            }
            studentsListView.refresh();
        });
    }

}
