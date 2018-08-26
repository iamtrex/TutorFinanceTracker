package com.rweqx.controller;

import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class StudentProfileController extends BaseController implements Initializable {

    @FXML
    private Label lOutstanding;

    @FXML
    private Label lName;

    @FXML
    private Label lAdded;

    @FXML
    private Label lGroup;

    @FXML
    private Label lNote;

    @FXML
    private VBox ratesBox;

    @FXML
    private ComboBox<String> filterPicker;



    @FXML
    private Button bBack;
    @FXML
    private Button bEdit;



    @FXML
    private ScrollPane eventScroll;

    @FXML
    private VBox eventBox;

    @FXML
    private AnchorPane root;

    @FXML
    private DatePicker datePickerStart;
    @FXML
    private DatePicker datePickerEnd;



    private LocalDate startDate;
    private LocalDate endDate;

    private LongProperty studentID;
    private Student currentStudent;


    public StudentProfileController() {
        studentID = new SimpleLongProperty(-1);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        datePickerStart.valueProperty().addListener((obs, oldVal, newVal)->{
            if(oldVal == newVal)
                return;

            startDate = newVal;
            refreshEventBox();
        });

        datePickerEnd.valueProperty().addListener((obs, oldVal, newVal)->{
            if(oldVal == newVal)
                return;

            endDate = newVal;
            refreshEventBox();
        });

        datePickerStart.setValue(LocalDate.now());
        datePickerEnd.setValue(LocalDate.now());

        studentID.addListener((obs, oldVal, newVal) -> {
            /*if (oldVal == newVal) {
                return;
            }*/ //Sometimes updated even tho name not changed (ie deleted a payment)
            long ID = newVal.longValue();
            if (ID == -1) {
                reset();
            }

            currentStudent = modelManager.getStudentManager().getStudentByID(ID);
            lName.setText(currentStudent.getName());

            refreshEventBox();
        });


        eventScroll.setFitToWidth(true);

    }

    @Override
    public void sceneLoaded() {
        //Load currentStudent...
        super.sceneLoaded();
        System.out.println("Loading Student Profile Scene");
        System.out.println("Student " + sceneModel.getCurrentStudent().getName());

        boolean force = sceneModel.getCurrentStudent().getID() == studentID.get();
        if(force)
            studentID.set(-1); //Resets studentID, forcing refresh of student... sorta hacky but yeah...

        studentID.set(sceneModel.getCurrentStudent().getID());

    }


    private void refreshEventBox() {
        if(startDate == null || endDate == null || studentID.get() == -1){
            return; //skip
        }
        System.out.println("Refreshing box");
        eventBox.getChildren().clear();
        List<Event> events = modelManager.getAllEventsByStudentBetween(studentID.get(), startDate, endDate);


        for (Event e : events) {
            try {
                FXMLLoader loader = null;
                if (e instanceof Class) {
                    loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/student-profile-class-item.fxml"));
                } else if (e instanceof Payment) {
                    loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/student-profile-payment-item.fxml"));
                }

                //loader.setRoot(new HBox());
                HBox eventItem = loader.load();
                eventBox.getChildren().add(eventItem);

                StudentEventItemController seic = loader.getController();
                seic.setStudent(currentStudent);
                seic.setEvent(e);
                seic.initModel(modelManager, sceneModel);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (events.size() == 0) {
            eventBox.getChildren().add(new Label("No Events this month."));
        }
    }

    /**
     * Resets layout.
     */
    private void reset() {
        System.out.println("Will need to reset stu profile. ");
    }

    public void backClicked(ActionEvent e) {
        reset();
        sceneModel.backClicked();
    }

    public void editClicked(ActionEvent e){
        sceneModel.setCurrentStudent(currentStudent);
        sceneModel.setScene(AddEditStudentController.class.getSimpleName());
    }
}
