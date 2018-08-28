package com.rweqx.controller;

import com.rweqx.components.PaymentRateItem;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private String filterType;

    public StudentProfileController() {
        studentID = new SimpleLongProperty(-1);
        filterType = "All";
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

        datePickerStart.setValue(LocalDate.now().minus(1, ChronoUnit.MONTHS));
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
            refreshContent();
        });

        filterPicker.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            if (newVal != null && !newVal.equals(oldVal)) {
                filterType = newVal;
                refreshEventBox();

            }
        });
        eventScroll.setFitToWidth(true);

    }

    @Override
    public void sceneLoaded() {
        //Load currentStudent...
        super.sceneLoaded();
        System.out.println("Loading Student Profile Scene");
        System.out.println("Student " + sceneModel.getCurrentStudent().getName());

        ObservableList<String> filterItems = FXCollections.observableArrayList();
        filterItems.setAll("Classes", "Payments", "All");
        filterPicker.setItems(filterItems);

        boolean force = sceneModel.getCurrentStudent().getID() == studentID.get();
        if(force)
            studentID.set(-1); //Resets studentID, forcing refresh of student... sorta hacky but yeah...

        studentID.set(sceneModel.getCurrentStudent().getID());

    }

    private void refreshContent(){
        if(currentStudent == null){
            return;
        }
        lName.setText(currentStudent.getName());
        lAdded.setText("Date Added - " + currentStudent.getDate().toString());
        lGroup.setText("Group - none"); //TODO
        lNote.setText("Comments - " + currentStudent.getComment());

        List<String> types = new ArrayList<>(modelManager.getClassTypes().getTypesList());
        ratesBox.getChildren().clear();
        types.forEach(type -> {
            PaymentRateItem pri = new PaymentRateItem(type, currentStudent.getLatestPaymentRates().getRateByType(type));
            ratesBox.getChildren().add(pri);
        });
        refreshEventBox();
    }


    private void refreshEventBox() {
        if(startDate == null || endDate == null || studentID.get() == -1){
            return; //skip
        }
        System.out.println("Refreshing box");
        eventBox.getChildren().clear();
        List<Event> events = modelManager.getAllEventsByStudentBetween(studentID.get(), startDate, endDate);


        for (Event e : events) {
            if(filterType.equals("Classes") && e instanceof Payment){ //TODO NOT MOST OPTIMAL SINCE WE GET ALL FROM MODELMANAGER,
                //TODO - SHOUDL INSTEAD PASS TO MODEL WHAT OUR FILTER IS AND HAVE MODELMANAGER DECIDE WHAT EVENTS TO RETURN.
                continue;
            }else if(filterType.equals("Payments") && e instanceof Class){
                continue;
            }
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
        //ratesBox.getChildren().clear(); //TODO SHOUDL BE HERE BUT CURRENTLY ABOVE.
        filterType = "All";
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
