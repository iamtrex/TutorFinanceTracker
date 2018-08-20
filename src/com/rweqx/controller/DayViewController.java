package com.rweqx.controller;

import com.rweqx.model.Event;
import com.rweqx.model.Class;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;


public class DayViewController extends BaseController implements Initializable {

    public void prevDayClicked(){
        loadDate(dateShown.minus(1, ChronoUnit.DAYS));
    }
    public void nextDayClicked(){
        loadDate(dateShown.plus(1, ChronoUnit.DAYS));
    }

    private LocalDate dateShown;

    @FXML
    private ScrollPane eventScroll;
    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox eventsBox;

    @FXML
    private Label lHours;

    @FXML
    private Label lIncome;



    @Override
    public void sceneLoaded(){
        if(sceneModel.getCurrentDate() != null){
            loadDate(sceneModel.getCurrentDate());
        }else{
            loadDate(LocalDate.now());
        }
    }

    private void loadDate(LocalDate date) {
        dateShown = date;
        datePicker.setValue(date);
        eventsBox.getChildren().clear();
        List<Event> events = modelManager.getAllEventsOnDate(date);
        for (Event e : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/components/EventItem.fxml"));
                loader.setRoot(new HBox());
                HBox eventItem = loader.load();

                eventsBox.getChildren().add(eventItem);
                EventItemController eic = loader.getController();
                eic.initModel(modelManager, sceneModel);
                eic.setEvent(e);


                if(e instanceof Class){
                    //TODO FILL IN LHOURS AND LINCOME
                    lHours.setText("TBD");
                    lIncome.setText("TBD");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(events.size() == 0){
            eventsBox.getChildren().setAll(new Label("No Events on this Day"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         datePicker.valueProperty().addListener((obs, oldVal, newVal)->{
            loadDate(newVal);
         });
         eventScroll.setFitToWidth(true);
    }
}
