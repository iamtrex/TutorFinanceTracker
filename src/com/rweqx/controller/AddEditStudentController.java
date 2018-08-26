package com.rweqx.controller;

import com.rweqx.components.PaymentRateItem;
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
        paymentRateItems.clear();
        paymentRatesBox.getChildren().clear();
    }
    @Override
    public void sceneLoaded() {
        Student s = sceneModel.getCurrentStudent();
        if (s == null) {
            currentMode = ADD_MODE;
            bDelete.setVisible(false);
            bSave.setText("Add Student");
        } else {
            currentMode = EDIT_MODE;
            bDelete.setVisible(true);
            bSave.setText("Save Student");
        }

        paymentRatesBox.getChildren().removeAll();

        List<String> types = new ArrayList<>(modelManager.getClassTypes().getTypesList());

        types.forEach(type -> {
            PaymentRateItem pri = new PaymentRateItem(type);
            paymentRatesBox.getChildren().add(pri);
            paymentRateItems.add(pri);
        });


    }


    //TODO IMPLEMENT THESE METHODS.
    public void saveClicked() {
        //TODO - Check validity.
        Map<String, Double> rates = new HashMap<>();
        paymentRateItems.forEach(pri->{
            rates.put(pri.getType(), pri.getRate());
        });
        PaymentRatesAtTime prat = new PaymentRatesAtTime(LocalDate.now(), rates);

        String studentName = tStudentName.getText();
        String studentComment = tComment.getText();

        modelManager.createAndAddStudent(studentName, studentComment, prat);
        //Jump back.
        reset();
        sceneModel.getBackProperty().set(true);
    }

    public void cancelClicked() {
        reset();
        sceneModel.getBackProperty().set(true);
    }


    public void deleteClicked() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);
        paymentRatesBox.setFillWidth(true);
        //paymentRatesBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


    }
}
