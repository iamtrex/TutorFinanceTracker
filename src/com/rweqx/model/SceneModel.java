package com.rweqx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class SceneModel {
    public Class getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
    }

    public Payment getCurrentPayment() {
        return currentPayment;
    }

    public void setCurrentPayment(Payment currentPayment) {
        this.currentPayment = currentPayment;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public StringProperty getSceneNameProperty(){
        return sceneNameProperty;
    }

    private StringProperty sceneNameProperty;
    private Class currentClass;
    private Payment currentPayment;
    private Student currentStudent;

    public void setScene(String sceneName){
        this.sceneNameProperty.set(sceneName);
    }

    public SceneModel(){
        sceneNameProperty = new SimpleStringProperty();
    }


}
