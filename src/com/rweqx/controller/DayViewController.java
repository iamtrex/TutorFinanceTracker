package com.rweqx.controller;

import com.rweqx.model.DataModel;
import com.rweqx.model.Event;
import com.rweqx.model.Class;
import com.rweqx.model.Payment;
import com.rweqx.util.DateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayViewController {

    @FXML
    private ScrollPane eventScroll;

    @FXML
    private DatePicker datePicker;

    private Date showingDate;

    private DataModel dataModel;


    public void initModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }



    public void setDate(Date date) {
        eventScroll.setFitToWidth(true);
        classesBox.getChildren().clear();

        System.out.println("Setting Date");

        datePicker.setValue(DateUtil.dateToLocalDate(date));
        datePicker.valueProperty().addListener((obs, oldVal, newVal) ->{
           setDate(DateUtil.localDateToDate(newVal));
        });

        System.out.println(Calendar.getInstance().getTime());

        List<Event> events = dataModel.getAllEventsOnDate(date);
        for (Event e : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/components/EventItem.fxml"));
                loader.setRoot(new HBox());
                HBox eventItem = loader.load();

                classesBox.getChildren().add(eventItem);
                EventItemController eic = loader.getController();
                eic.initModel(dataModel);
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
            classesBox.getChildren().add(new Label("No Events on this Day"));
        }

        this.showingDate = date;

    }

    @FXML
    private Button prevDay;
    @FXML
    private Button nextDay;

    @FXML
    private VBox classesBox;

    @FXML
    private Label lDate;

    @FXML
    private Label lHours;
    @FXML
    private Label lIncome;


    public void prevDayClicked(ActionEvent e) {
        setDate(Date.from(showingDate.toInstant().minus(1, ChronoUnit.DAYS)));
    }

    public void nextDayClicked(ActionEvent e) {
        setDate(Date.from(showingDate.toInstant().plus(1, ChronoUnit.DAYS)));
    }

}
