package com.rweqx.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * Classes and Payments are events...
 */
public class Event {


    private long eventID;
    private Date date;

    public Event(long eventID, Date date){
        this.eventID = eventID;
        this.date = date;
    }

    public long getEventID() {
        return eventID;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventID == event.eventID;
    }

    @Override
    public int hashCode() {

        return Objects.hash(eventID);
    }
}
