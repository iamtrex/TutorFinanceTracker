package com.rweqx.controller;

import com.rweqx.components.EventItemController;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
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


    private LongProperty studentID;
    private Student currentStudent;


    public StudentProfileController() {
        studentID = new SimpleLongProperty();
        currentDisplayMode = DISPLAY_ALL;


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lName.textProperty().bind(studentName);
        studentID.addListener((obs, oldVal, newVal) -> {
            if (oldVal == newVal) {
                return;
            }
            int ID = newVal.intValue();
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
        studentID.set(sceneModel.getCurrentStudent().getID());

    }


    private void refreshEventBox() {
        eventBox.getChildren().clear();
        List<Event> events;

        switch (currentDisplayMode) {
            case DISPLAY_ALL:
                events = modelManager.getAllEventsByStudent(currentStudent);
                break;
            case DISPLAY_MONTH:
                int month = LocalDate.now().getMonthValue();
                events = modelManager.getAllEventsByStudentInMonth(currentStudent, month);
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
                    loader = new FXMLLoader(getClass().getResource("/com/rweqx/components/studnet-profile-class-item.fxml"));
                } else if (e instanceof Payment) {
                    loader = new FXMLLoader(getClass().getResource("/com/rweqx/components/studnet-profile-payment-item.fxml"));
                }

                loader.setRoot(new HBox());
                HBox eventItem = loader.load();
                eventBox.getChildren().add(eventItem);

                StudentEventItemController seic = loader.getController();
                seic.setEvent(e);
                seic.initModel(modelManager, sceneModel);
                seic.setStudent(currentStudent);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (events.size() == 0) {
            eventBox.getChildren().add(new Label("No Events on this Day"));
        }
    }

    /**
     * Resets layout.
     */
    private void reset() {

    }

    public void showAllClicked(ActionEvent e) {

    }

    public void showOutstandingClicked(ActionEvent e) {

    }

    public void showMonthClicked(ActionEvent e) {

    }

    public void backClicked(ActionEvent e) {

    }
}
