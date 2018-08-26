package com.rweqx.model;

import java.time.LocalDate;

public abstract class Event {
    private long eventID;
    private LocalDate date;
    private String comment;

    public Event(long eventID, LocalDate date, String comment) {
        this.eventID = eventID;
        this.date = date;
        this.comment = comment;
    }

    public String getComment(){
        return comment;
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
