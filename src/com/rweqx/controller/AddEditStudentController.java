package com.rweqx.controller;

import com.rweqx.components.PaymentRateItem;
import com.rweqx.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddEditStudentController extends BaseController implements Initializable {

    //TODO MAKE THIS CHANGE BUTTON LOOKS...
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    private int current_mode = ADD_MODE; //Default is addClass.

    @FXML
    private Button bSave;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDelete;

    @FXML
    private TextField tStudentName;

    @FXML
    private List<PaymentRateItem> paymentRateItems;

    @FXML
    private VBox paymentRatesBox;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void sceneLoaded(){
        Student s = sceneModel.getCurrentStudent();
        if(s == null){
            bDelete.setVisible(false);
            bSave.setText("Add Student");
        }else{
            bDelete.setVisible(true);
            bSave.setText("Save Student");
        }

    }


    //TODO IMPLEMENT THESE METHODS.
    public void saveClicked(){

    }

    public void cancelClicked(){

    }

    public void deleteClicked(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
