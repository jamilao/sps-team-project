package com.google.sps.servlets;
import java.util.*;

public final class Event{
    private final String organizer;
    private final String eventName;
    private final String location;
    private final String description;
    private final Date date;
    private final String password;

    public Event(String organizer, String eventName, String location, String description, Date date){
        this.organizer = organizer;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
        this.date = date;
        this.password = generatePassword();
    }

    private String generatePassword(){
        String password = "";
        for (int i = 0; i < 5; i++){
            char rand = (char)((int)(Math.random() * 97) + 33); 
            password = password + rand;
        }
        return password;
    }

    public boolean validatePassword(String input){
        return (input.equals(this.password));
    }

    public String getPassword(){
        return password;
    }

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
