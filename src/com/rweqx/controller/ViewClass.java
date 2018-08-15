package com.rweqx.controller;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.DataModel;
import com.rweqx.model.StudentInClassElement;
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

public class ViewClass implements Initializable {

    private DataModel dataModel;

    @FXML
    private Label lClassType;

    @FXML
    private Label lDate;

    @FXML
    private TableView studentTable;


    public void initModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    private long classID;

    public void giveClass(long classID){
        this.classID = classID;

        if(dataModel == null){
            Logger.getInstance().log("No Model in ViewClass class", LogLevel.S);
            throw new IllegalStateException();
        }


        Class c = dataModel.getClassManager().getClassByID(classID);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
        lDate.setText(sdf.format(c.getDate()));
        lClassType.setText(c.getClassType());

        ObservableList<StudentInClassElement> stuElts = FXCollections.observableArrayList(c.getStuElts());

        studentNameCol.setCellValueFactory(
                new PropertyValueFactory<StudentInClassElement, String>("studentName"));
        durationCol.setCellValueFactory(
                new PropertyValueFactory<StudentInClassElement, Double>("duration"));
        paidCol.setCellValueFactory(
                new PropertyValueFactory<StudentInClassElement, Double>("paid"));

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
