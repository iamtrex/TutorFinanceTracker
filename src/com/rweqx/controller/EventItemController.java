package com.rweqx.controller;

import com.rweqx.controller.AddEditClassController;
import com.rweqx.controller.AddEditPaymentController;
import com.rweqx.controller.BaseController;

import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;
import javafx.scene.layout.HBox;

public class EventItemController extends BaseController {
    protected Event event;

    public Event getEvent(){
        return event;
    }

    public void setEvent(Event e){
        event = e;
    }

    public void editClicked(){
        if(event instanceof Payment){
            sceneModel.setCurrentPayment((Payment)event);
            sceneModel.setScene(AddEditPaymentController.class.getSimpleName());
        }else if(event instanceof Class){
            sceneModel.setCurrentClass((Class)event);
            sceneModel.setScene(AddEditClassController.class.getSimpleName());
        }else{
            throw new IllegalStateException();
        }
    }
}
