package com.rweqx.controller;

import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import com.rweqx.util.MathUtil;
import com.rweqx.util.PrintUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.util.List;

public class PrintSummaryTemplateController extends BaseController{
    private Student student;
    private List<Event> events;

    @FXML
    private Button bPrint;

    @FXML
    private VBox contentPane;

    @FXML
    private Label lName;

    @FXML
    private Label lInvoiceDate;

    @FXML
    private Label lInvoicePeriod;

    @FXML
    private GridPane eventsGrid;

    @FXML
    private Label lTotal;


    public void setEvents(long stuID, LocalDate start, LocalDate end, List<Event> events) {
        this.events = events;
        student = modelManager.getStudentManager().getStudentByID(stuID);

        double total = 0;
        for (Event e : events) {
            FieldLabel eventDate = new FieldLabel(e.getDate().toString());
            FieldLabel eventDesc = new FieldLabel();
            FieldLabel eventHours = new FieldLabel();
            FieldLabel eventRates = new FieldLabel();
            FieldLabel eventAmount = new FieldLabel();
            eventAmount.setTextAlignment(TextAlignment.RIGHT);

            if (e instanceof Class) {
                Class c = (Class) e;
                if(c.getComment().trim().equals("")){
                    eventDesc.setText("Class (" +c.getClassType() + ")");
                }else {
                    eventDesc.setText("Class (" + c.getClassType() + ") - " + c.getComment());
                }
                double duration = c.getDurationOfStudent(stuID);
                double rate = c.getCustomRateOfStudent(stuID);
                if (rate == -1) {
                    rate = student.getPaymentRateAtTime(c.getDate(), c.getClassType());
                }
                double amount = MathUtil.round(duration * rate, 2);

                eventHours.setText(String.valueOf(duration));
                eventRates.setText(String.valueOf(rate));
                eventAmount.setText(String.valueOf(amount));
                total += amount;
            } else {
                Payment p = (Payment) e;
                eventDesc.setText("Paid - " + p.getPaymentType());
                double amount = p.getPaymentAmount();
                eventAmount.setText("-" + String.valueOf(amount));
                total -= amount;
            }
            eventsGrid.addRow(eventsGrid.getRowCount(), eventDate, eventDesc, eventHours, eventRates, eventAmount);
        }
        lName.setText(student.getName());
        lInvoiceDate.setText(LocalDate.now().toString());
        lInvoicePeriod.setText(start.toString() + " - " + end.toString());
        lTotal.setText("$" + String.valueOf(total));

    }

    public Node getContentPane() {
        return contentPane;
    }


    private class FieldLabel extends Label{
        public FieldLabel(){
            super();
            this.setText("");
            this.getStyleClass().addAll("field-label");
            this.setAlignment(Pos.CENTER);
            GridPane.setFillWidth(this, true);
        }
        public FieldLabel(String s){
            super(s);
            this.getStyleClass().addAll("field-label");
            this.setAlignment(Pos.CENTER);
            GridPane.setFillWidth(this, true);
        }

    }

    public void printClicked(ActionEvent evt){

        PrintUtil.print(contentPane, contentPane.getScene().getWindow());
        contentPane.getScene().getWindow().hide();
    }
}
