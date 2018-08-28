package com.rweqx.controller;

import com.rweqx.components.PaymentRateItem;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.PaymentRatesAtTime;
import com.rweqx.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddEditStudentController extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int currentMode = ADD_MODE; //Default is addClass.

    private Student currentlyEditingStudent;

    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDelete;

    @FXML
    private TextField tStudentName;

    @FXML
    private TextField tComment;

    private List<PaymentRateItem> paymentRateItems;

    @FXML
    private VBox paymentRatesBox;

    @FXML
    private ScrollPane scrollPane;

    public AddEditStudentController(){
        paymentRateItems = new ArrayList<>();
    }
    private void reset() {
        tStudentName.setText("");
        tComment.setText("");
        paymentRateItems.clear();
        paymentRatesBox.getChildren().clear();
        currentlyEditingStudent = null;
    }

    @Override
    public void sceneLoaded() {
        Student s = sceneModel.getCurrentStudent();


        paymentRatesBox.getChildren().removeAll();

        List<String> types = new ArrayList<>(modelManager.getClassTypes().getTypesList());

        types.forEach(type -> {
            PaymentRateItem pri = new PaymentRateItem(type);
            paymentRatesBox.getChildren().add(pri);
            paymentRateItems.add(pri);
        });

        if (s == null) {
            currentlyEditingStudent = null;
            currentMode = ADD_MODE;
            bDelete.setVisible(false);
            bSave.setText("Add Student");
        } else {
            currentlyEditingStudent = s;
            currentMode = EDIT_MODE;
            bDelete.setVisible(true);
            bSave.setText("Save Student");

            tStudentName.setText(s.getName());
            tComment.setText(s.getComment());
            paymentRateItems.forEach(pri->{
                pri.setRate(s.getLatestPaymentRates().getRateByType(pri.getType()));
            });
        }

    }


    //TODO IMPLEMENT THESE METHODS.
    public void saveClicked() {
        //TODO - Check validity.
        Map<String, Double> rates = new HashMap<>();
        paymentRateItems.forEach(pri -> {
            rates.put(pri.getType(), pri.getRate());
        });
        PaymentRatesAtTime prat = new PaymentRatesAtTime(LocalDate.now(), rates);

        String studentName = tStudentName.getText();
        String studentComment = tComment.getText();
        List<String> groups = new ArrayList<>(); //TODO REPLACE WITH REAL GROUP TAGS FROM USER INPUT.

        if(currentMode == ADD_MODE) {
            modelManager.createAndAddStudent(studentName, studentComment, prat, groups);
            //Jump back.
        }else{
            modelManager.updateStudent(currentlyEditingStudent, studentName, studentComment, prat);
        }
        reset();
        sceneModel.getBackProperty().set(true);
    }

    public void cancelClicked() {
        reset();
        sceneModel.getBackProperty().set(true);
    }


    public void deleteClicked() {
        Logger.getInstance().log("Unsupported feature -> Delete student. ", LogLevel.W);
        //DO NOT SUPPORT THIS FEATURE YET.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);
        paymentRatesBox.setFillWidth(true);
        //paymentRatesBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


    }
}
