package com.rweqx.controller;

import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.util.MathUtil;
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
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ResourceBundle;


public class DayViewController extends BaseController implements Initializable {

    private boolean refreshDisabled = false;

    private LocalDate startDate;
    private LocalDate endDate;

    @FXML
    private ScrollPane eventScroll;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;


    @FXML
    private VBox eventsBox;

    @FXML
    private Label lHours;

    @FXML
    private Label lIncome;



    @Override
    public void sceneLoaded(){
        if(sceneModel.getCurrentDate() != null){
            setMonth(sceneModel.getCurrentDate().getYear(), sceneModel.getCurrentDate().getMonthValue());
        }else{
            thisMonthClicked();
        }
    }


    private void refreshEventBox() {
        if(refreshDisabled || startDate == null || endDate == null){
            return;
        }
        eventsBox.getChildren().clear();

        double hours = 0, income = 0;

        List<Event> events = modelManager.getAllEventsBetweenDates(startDate, endDate);
        for (Event e : events) {
            try {
                FXMLLoader loader;
                HBox item;
                if(e instanceof Class){
                    loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/day-view-class-item.fxml"));
                    item = loader.load();
                }else{
                    loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/day-view-payment-item.fxml"));
                    item = loader.load();

                }

                eventsBox.getChildren().add(item);
                EventItemController eic = loader.getController();
                eic.initModel(modelManager, sceneModel);
                eic.setEvent(e);

                if(e instanceof Class){

                    Class c = (Class) e;
                    hours += c.getAvgDuration();
                    income += modelManager.getExpectedIncomeOfClass(c);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        lHours.setText(String.valueOf(MathUtil.round(hours, 2)));
        lIncome.setText(String.valueOf(MathUtil.round(income, 2)));

        if(events.size() == 0){
            eventsBox.getChildren().setAll(new Label("No Events on this Day"));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         startDatePicker.valueProperty().addListener((obs, oldVal, newVal)->{
            startDate = newVal;
             refreshEventBox();
         });
         endDatePicker.valueProperty().addListener((obs, oldVal, newVal)->{
             endDate = newVal;
             refreshEventBox();
         });
         eventScroll.setFitToWidth(true);
    }


    public void lastMonthClicked(){
        refreshDisabled = true;
        startDatePicker.setValue(LocalDate.now().minus(1, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()));
        refreshDisabled = false;
        endDatePicker.setValue(LocalDate.now().minus(1, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()));
    }

    private void setMonth(int year, int month) {
        refreshDisabled = true;
        startDatePicker.setValue(LocalDate.of(year, month, 1));
        refreshDisabled = false;
        endDatePicker.setValue(LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()));
    }
    public void thisMonthClicked(){
        refreshDisabled = true;
        startDatePicker.setValue(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        refreshDisabled = false;
        endDatePicker.setValue(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
    }
    public void todayClicked(){
        refreshDisabled = true;
        startDatePicker.setValue(LocalDate.now());
        refreshDisabled = false;
        endDatePicker.setValue(LocalDate.now());
    }
}
