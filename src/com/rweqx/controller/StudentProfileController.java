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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    private final int DISPLAY_ALL = 0;
    private final int DISPLAY_MONTH = 1;

    private int currentDisplayMode;

    @FXML
    private Label lOutstanding;

    @FXML
    private Label lName;

    @FXML
    private Button bBack;

    @FXML
    private Button bShowAll;
    @FXML
    private Button bShowMonth;

    @FXML
    private ScrollPane eventScroll;

    @FXML
    private VBox eventBox;

    @FXML
    private AnchorPane root;

    @FXML
    private DatePicker datePicker;
    private LocalDate currentDate;


    private LongProperty studentID;
    private Student currentStudent;


    public StudentProfileController() {
        studentID = new SimpleLongProperty();
        currentDisplayMode = DISPLAY_ALL;


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lName.textProperty().bind(studentName);
        currentDate = LocalDate.now();

        datePicker.valueProperty().addListener((obs, oldVal, newVal)->{
            currentDisplayMode = DISPLAY_MONTH;
            if (currentDate.getMonthValue() != newVal.getMonthValue() || currentDate.getYear() != newVal.getYear()) {
                currentDate = newVal;
                refreshEventBox();
            }
        });

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
        studentID.set(sceneModel.getCurrentStudent().getID());
        if(force)
            refreshEventBox();

    }


    private void refreshEventBox() {
        System.out.println("Refreshing box");

        eventBox.getChildren().clear();
        List<Event> events;

        datePicker.setValue(currentDate);
        switch (currentDisplayMode) {
            case DISPLAY_ALL:
                events = modelManager.getAllEventsByStudent(currentStudent);
                break;
            case DISPLAY_MONTH:
                events = modelManager.getAllEventsByStudentInYearMonth(currentStudent, currentDate.getYear(), currentDate.getMonthValue());
                break;
            default:
                events = new ArrayList<>();
                break;
        }

        double outstanding = modelManager.getAllEventsByStudentOutstanding(currentStudent);
        lOutstanding.setText("Amount outstanding " + outstanding);

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

    public void prevMonthClicked(ActionEvent e){
        currentDate = currentDate.minus(1, ChronoUnit.MONTHS);
        currentDisplayMode = DISPLAY_MONTH;
        refreshEventBox();
    }
    public void nextMonthClicked(ActionEvent e){
        currentDate = currentDate.plus(1, ChronoUnit.MONTHS);
        currentDisplayMode = DISPLAY_MONTH;
        refreshEventBox();
    }
    public void showAllClicked(ActionEvent e) {
        currentDisplayMode = DISPLAY_ALL;
        refreshEventBox();
    }

    public void showOutstandingClicked(ActionEvent e) {
        //TODO.
    }

    public void showMonthClicked(ActionEvent e) {
        currentDisplayMode = DISPLAY_MONTH;
        currentDate = LocalDate.now();
        refreshEventBox();
    }

    public void backClicked(ActionEvent e) {
        reset();
        sceneModel.backClicked();
    }
}
