package com.rweqx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PaymentTypes {
    private ObservableList<String> types;

    public PaymentTypes(){
        types = FXCollections.observableArrayList();

        //TODO remove.
        types.addAll("Cash", "Cheque");
    }
    public void addType(String s){
        types.add(s);
    }
    public void removeType(String s){
        types.remove(s);
    }

    public ObservableList getTypesList(){
        return types;
    }

}
