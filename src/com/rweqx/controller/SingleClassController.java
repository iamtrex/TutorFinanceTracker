package com.rweqx.controller;

import com.rweqx.components.ClickEditTextField;
import com.rweqx.components.WarningPopUp;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.*;
import com.rweqx.model.Class;
import com.rweqx.util.StringUtil;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleClassController extends BaseController{

    private boolean loadDone;

    @FXML
    private Pane pane;
    @FXML
    private VBox internals;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField lComment;
    @FXML
    private ComboBox<String> classTypeChoices;
    @FXML
    private CheckBox sameDuration;
    @FXML
    private GridPane titleGrid;
    @FXML
    private GridPane classDetailsGrid;

    private Map<Student, Label> nameMap;
    private Map<Student, TextField> durationMap;
    private Map<Student, TextField> rateMap;
    private Map<Student, TextField> paidMap;
    private Map<Student, ComboBox<String>> paymentTypeMap;
    private Map<Student, TextField> commentMap;

    private ObservableList<Student> chosenStudents;

    public SingleClassController(){
        loadDone = false;
        nameMap = new HashMap<>();
        durationMap = new HashMap<>();
        rateMap = new HashMap<>();
        paidMap = new HashMap<>();
        paymentTypeMap = new HashMap<>();
        commentMap = new HashMap<>();
    }

    @Override
    public void initModel(ModelManager modelManager, SceneModel sceneModel){
        super.initModel(modelManager, sceneModel);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(this::loadComponents);

    }

    private void updateClassRateAll() {
        String classType = classTypeChoices.getValue();
        for(Student s : chosenStudents){
            if(classType != null && datePicker.getValue() != null) {
                rateMap.get(s).setText(String.valueOf(
                        s.getPaymentRateAtTime(datePicker.getValue(), classType)));
            }else{
                rateMap.get(s).setText("");
            }
        }
    }

    public void setChosenStudents(ObservableList<Student> students){
        this.chosenStudents = students;
    }


    public void loadComponents(){

        //Wait for modelManager and chosenStudents
        while(modelManager == null || chosenStudents == null){
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        classTypeChoices.setItems(modelManager.getClassTypes().getTypesList());

        classTypeChoices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            //Update rates for the selected class type.
            updateClassRateAll();
        });

        sameDuration.selectedProperty().addListener((obs, oldVal, newVal)->{

            if(chosenStudents.size() == 0){
                return;
            }
            for(Student s : chosenStudents){
                durationMap.get(s).setVisible(!newVal);
            }
            //Always show first.
            Student first = chosenStudents.get(0);
            durationMap.get(first).setVisible(true);

        });

        datePicker.setValue(LocalDate.now());

        datePicker.valueProperty().addListener((obs, oldVal, newVal)->{
            updateClassRateAll();
        });

        chosenStudents.addListener((ListChangeListener<Student>) c->{
            if(c.next()) {
                if (c.wasPermutated() || c.wasUpdated()) {
                    System.out.println(c);
                } else {
                    for (Student s : c.getRemoved()) {
                        removeStudent(s);
                    }

                    for (Student s : c.getAddedSubList()) {
                        addStudent(s);
                    }
                }
            }
        });

        //Register current students;
        System.out.println("Registering current students " + chosenStudents.size());
        chosenStudents.forEach(s->{
            addStudent(s);
        });

        loadDone = true;

    }

    private void removeStudent(Student s) {
        classDetailsGrid.getChildren().removeAll(nameMap.get(s), durationMap.get(s), rateMap.get(s), paidMap.get(s), paymentTypeMap.get(s), commentMap.get(s));
        nameMap.remove(s);
        durationMap.remove(s);
        rateMap.remove(s);
        paidMap.remove(s);
        paymentTypeMap.remove(s);
        commentMap.remove(s);
        updateHeight();

    }

    private void updateHeight(){
        pane.setMinHeight(chosenStudents.size()*30 + 70); //Gives an approx nice feel for students
    }

    private void addStudent(Student s) {
        nameMap.put(s, new Label(s.getName()));
        durationMap.put(s, new TextField());
        rateMap.put(s, new ClickEditTextField());

        String type = classTypeChoices.getSelectionModel().getSelectedItem();
        if(type != null && datePicker.getValue()!= null){
            rateMap.get(s).setText(String.valueOf(s.getPaymentRateAtTime(datePicker.getValue(), type)));
            //rateMap.get(s).setText(String.valueOf(s.getLatestPaymentRates().getRateByType(type)));
        }

        paidMap.put(s, new TextField());
        commentMap.put(s, new TextField());

        ComboBox choices = new ComboBox();
        choices.setStyle("-fx-pref-width: 1000;");
        choices.setItems(modelManager.getPaymentTypes().getTypesList());
        paymentTypeMap.put(s, choices);
        Platform.runLater(()->{
            int numRow = classDetailsGrid.getRowCount();
            classDetailsGrid.addRow(numRow, nameMap.get(s), durationMap.get(s), rateMap.get(s), paidMap.get(s), paymentTypeMap.get(s), commentMap.get(s));
            updateHeight();
        });
    }

    public long buildAndAddClass() {
        Logger.getInstance().log(this.getClass().getSimpleName(),"Building and adding single class in chain.", LogLevel.D);

        LocalDate date = datePicker.getValue();
        String classType = classTypeChoices.getValue();
        String comment = lComment.getText();
        List<String> tags = new ArrayList<>(); //TODO Get tags from user input in the future.

        Class c = modelManager.createAndAddEmptyClass(date, classType, comment, tags);

        boolean isSameDuration = sameDuration.isSelected();
        double sameDur = 0;
        if(isSameDuration){
            sameDur = Double.parseDouble(durationMap.get(chosenStudents.get(0)).getText().trim());
        }

        for(Student s : chosenStudents){
            double duration, paymentAmount = 0, customRate = -1;
            String paymentType = "";
            String paidComment = commentMap.get(s).getText();
            if(isSameDuration){
                duration = sameDur;
            }else{
                duration = Double.parseDouble(durationMap.get(s).getText().trim());
            }
            if(!paidMap.get(s).getText().trim().equals("")){
                paymentAmount = Double.parseDouble(paidMap.get(s).getText().trim());
                paymentType = paymentTypeMap.get(s).getValue();
            }
            customRate = Double.parseDouble(rateMap.get(s).getText().trim());
            if(customRate != s.getPaymentRateAtTime(datePicker.getValue(), classType)){
                modelManager.addStuDurPaidToClass(c, s, duration, paymentAmount, paymentType, paidComment, customRate);
            }else{
                modelManager.addStuDurPaidToClass(c, s, duration, paymentAmount, paymentType, paidComment); //Same as giving -1 custom rate.
            }
        }

        return c.getID();
    }

    public boolean isValidInput() {
        if(datePicker.getValue() == null){
            new WarningPopUp("No date selected");
            return false;
        }else if(classTypeChoices.getValue() == null){
            new WarningPopUp("No class type selected");
            return false;
        }

        boolean isSameDuration = sameDuration.isSelected();
        if(isSameDuration && !StringUtil.isPositiveNumber(durationMap.get(chosenStudents.get(0)).getText())){
            new WarningPopUp("Invalid Duration");
            System.out.println("Student " + chosenStudents.get(0).getName());
            return false;
        }

        for(Student s : chosenStudents) {
            TextField tPaid = paidMap.get(s);
            boolean isNumber = StringUtil.isPositiveNumber(tPaid.getText());

            if (!isSameDuration && !StringUtil.isPositiveNumber(durationMap.get(s).getText())) {
                //Skip this check if same duraiton, only had to check the first student since rest are copied.
                new WarningPopUp("Invalid Duration for student " + s.getName());
                return false;
            } else if (!isNumber && !tPaid.getText().trim().equals("")) {
                new WarningPopUp("Invalid payment for student " + s.getName());
                return false;
            } else if (isNumber && paymentTypeMap.get(s).getValue() == null) {
                new WarningPopUp("Payment Type not selected for student " + s.getName());
                return false;
            }
            //Else valid student! check others.
        }
        return true;
    }

    public void loadClass(Class c) {

        Logger.getInstance().log(this.getClass().getSimpleName(),"Loading class with ID into single class -> " + c.getID(), LogLevel.D);
        while(!loadDone){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        datePicker.setValue(c.getDate());
        Set<StuDurPaid> data = c.getAllData();
        String type = c.getClassType();
        this.classTypeChoices.setValue(type);
        data.forEach((sdp)->{
            Student s = modelManager.getStudentManager().getStudentByID(sdp.getStuID());
            double duration = sdp.getDuration();
            durationMap.get(s).setText(String.valueOf(duration));

            long pid = sdp.getPaidID();
            if(pid != -1){
                Payment p = modelManager.getPaymentManager().getPaymentByID(pid);
                paidMap.get(s).setText(String.valueOf(p.getPaymentAmount()));
                paymentTypeMap.get(s).setValue(p.getPaymentType());
                commentMap.get(s).setText(p.getComment());
            }

            double rate = sdp.getCustomRate();
            if(rate != -1){
                rateMap.get(s).setText(String.valueOf(rate));
            }else{
                rateMap.get(s).setText(String.valueOf(s.getLatestPaymentRates().getRateByType(type)));
            }
        });
        //Class successfully loaded.
    }

    public void copy(SingleClassController toCopy) {

        while(!loadDone){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        this.datePicker.setValue(toCopy.getDatePicker().getValue());
        this.lComment.setText(toCopy.getlComment().getText());
        this.classTypeChoices.getSelectionModel().select(toCopy.getClassTypeChoices().getSelectionModel().getSelectedItem());
        this.sameDuration.setSelected(toCopy.getSameDuration().isSelected());
        for(Student s : chosenStudents){
            this.nameMap.get(s).setText(toCopy.getNameMap().get(s).getText());
            this.durationMap.get(s).setText(toCopy.getDurationMap().get(s).getText());
            this.rateMap.get(s).setText(toCopy.getRateMap().get(s).getText());
            this.paidMap.get(s).setText(toCopy.getPaidMap().get(s).getText());
            this.paymentTypeMap.get(s).getSelectionModel().select(toCopy.getPaymentTypeMap().get(s).getSelectionModel().getSelectedIndex());
            this.commentMap.get(s).setText(toCopy.getCommentMap().get(s).getText());
        }
    }

    public LocalDate getDate(){
        return datePicker.getValue();
    }
    public void setDate(LocalDate date){
        datePicker.setValue(date);
    }
    private DatePicker getDatePicker() {
        return datePicker;
    }

    private TextField getlComment() {
        return lComment;
    }

    private ComboBox<String> getClassTypeChoices() {
        return classTypeChoices;
    }

    private CheckBox getSameDuration() {
        return sameDuration;
    }

    private Map<Student, Label> getNameMap() {
        return nameMap;
    }

    private Map<Student, TextField> getDurationMap() {
        return durationMap;
    }

    private Map<Student, TextField> getRateMap() {
        return rateMap;
    }

    private Map<Student, TextField> getPaidMap() {
        return paidMap;
    }

    private Map<Student, ComboBox<String>> getPaymentTypeMap() {
        return paymentTypeMap;
    }

    private Map<Student, TextField> getCommentMap() {
        return commentMap;
    }
}
