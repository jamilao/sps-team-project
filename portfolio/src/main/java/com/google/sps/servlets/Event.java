package com.google.sps;
import java.util.*;

public final class Event{
    private final String organizer;
    private final String eventName;
    private final String location;
    private final String description;
    private final Date date;

    public Event(String organizer, String eventName, String location, String description){
        this.organizer = organizer;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
   
   public String getOrganizer(){
       return organizer;
   }
   public String getEventName(){
       return eventName;
   }
   public String getLocation(){
       return location;
   }
   public String getDescription(){
       return description;
   }
}
