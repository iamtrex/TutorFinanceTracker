package com.rweqx.controller;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.Payment;

public class EventItemController extends BaseController {
    protected Event event;

    public Event getEvent(){
        return event;
    }

    public void setEvent(Event e){
        event = e;
    }

    public void editClicked(){
        Logger.getInstance().log("Editing Event " + event + " switching scene now.", LogLevel.D);
        if(event instanceof Payment){
            sceneModel.setCurrentPayment((Payment)event);
            sceneModel.setScene(AddEditPaymentController.class.getSimpleName());
        }else if(event instanceof Class){
            System.out.println("Edit class clicked");
            sceneModel.setCurrentClass((Class)event);
            sceneModel.setScene(AddEditClassController.class.getSimpleName());
        }else{
            throw new IllegalStateException();
        }
    }
}
