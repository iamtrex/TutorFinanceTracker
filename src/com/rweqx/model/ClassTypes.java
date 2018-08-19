package com.rweqx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ClassTypes {
    private ObservableList<String> types;

    public ClassTypes(){
        types = FXCollections.observableArrayList();

        //TODO DEFAULT
        types.add("1 on 1");
        types.add("Group");
        types.add("1 on 2");
        types.add("1 on 3");

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
