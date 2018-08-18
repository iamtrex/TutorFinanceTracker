package com.rweqx.model;

import java.time.LocalDate;

public abstract class Event {
    private long eventID;
    private LocalDate date;

    public Event(long eventID, LocalDate date) {
        this.eventID = eventID;
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }

    public long getID(){
        return eventID;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

}
