package com.rweqx.controller;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.*;
import com.rweqx.model.Class;
import com.rweqx.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Set;

public class ViewClassController extends BaseController implements Initializable {

    @FXML
    private Label lClassType;

    @FXML
    private Label lDate;

    @FXML
    private TableView studentTable;


    private long classID;

    @Override
    public void sceneLoaded(){
        Class c = sceneModel.getCurrentClass();
        lDate.setText(DateUtil.getYearMonthDayFromDate(c.getDate()));
        lClassType.setText(c.getClassType());

        Set<Long> students = c.getStudents();
        System.out.println("Viewing this many students " + students.size());

        ObservableList<StuDurPaidTableItem> stuElts = FXCollections.observableArrayList();
        for(long stuID : students){
            String name = modelManager.getStudentManager().getStudentByID(stuID).getName();
            double duration = c.getDurationOfStudent(stuID);
            Payment payment = modelManager.getPaymentManager().getPaymentByID(c.getPaidIDOfStudent(stuID));
            double paid;
            if(payment == null){
                paid = 0;
            }else{
                paid = payment.getPaymentAmount();
            }
            stuElts.add(new StuDurPaidTableItem(name, duration, paid));

        }
        studentNameCol.setCellValueFactory(new PropertyValueFactory<StuDurPaidTableItem, String>("studentNameProp"));
        durationCol.setCellValueFactory(new PropertyValueFactory<StuDurPaidTableItem, Double>("durationProp"));
        paidCol.setCellValueFactory(new PropertyValueFactory<StuDurPaidTableItem, Double>("paidProp"));
        studentTable.setItems(stuElts);

    }

    @FXML
    private TableColumn studentNameCol;
    @FXML
    private TableColumn durationCol;
    @FXML
    private TableColumn paidCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



    }
}
