package com.rweqx.controller;

import com.rweqx.model.DataModel;

public class ViewClass {

    private DataModel dataModel;

    public void initModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    private long classID;

    public void giveClass(long classID){
        this.classID = classID;
        //Load class.

    }
}
