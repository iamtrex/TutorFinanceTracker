package com.rweqx.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StuDurPaidTableItem {
    public String getStudentNameProp() {
        return studentNameProp.get();
    }

    public StringProperty studentNamePropProperty() {
        return studentNameProp;
    }

    public double getDurationProp() {
        return durationProp.get();
    }

    public DoubleProperty durationPropProperty() {
        return durationProp;
    }

    public double getPaidProp() {
        return paidProp.get();
    }

    public DoubleProperty paidPropProperty() {
        return paidProp;
    }

    private StringProperty studentNameProp;
    private DoubleProperty durationProp;
    private DoubleProperty paidProp;

    public StuDurPaidTableItem(String stuName, double duration, double paid){
        studentNameProp = new SimpleStringProperty(stuName);
        durationProp = new SimpleDoubleProperty(duration);
        paidProp = new SimpleDoubleProperty(paid);
    }
}
