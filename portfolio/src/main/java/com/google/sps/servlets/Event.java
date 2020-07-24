package com.google.sps.servlets;
import com.google.appengine.api.datastore.Key;
import java.util.*;

public final class Event{
    private final String organizer;
    private final String eventName;
    private final String location;
    private final String description;
    private final Date start;
    private final Date end;
    private String password;
    private final Key key;
    private String centerCoord;
    private String pathCoords;
    private String hashtag;
    private String phone;
    private String email;

    public Event(String organizer, String eventName, String location, String description, Date start, Date end, Key key, String centerCoord, String pathCoords, String hashtag, String phone, String email){
        this.organizer = organizer;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
        this.start = start;
        this.end = end;
        this.password = generatePassword();
        this.key = key;
        this.centerCoord = centerCoord;
        this.pathCoords = pathCoords;
        this.hashtag = hashtag;
        this.phone = phone;
        this.email = email;
    }

    private String generatePassword(){
        String password = "";
        for (int i = 0; i < 5; i++){
            char rand = (char)((int)(Math.random() * 57) + 65); 
            password = password + rand;
        }
        return password;
    }

    public String getPassword(){
        return password;
    }

    /** Addresses new password generation each time a new Event object is initialized from datastore values, 
    allowing for existing passwords to be set on such new objects from their saved values in entities. **/
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setCenterCoord(String centerCoord){
        this.centerCoord = centerCoord;
    }

    public void setPathCoords(String pathCoords){
        this.pathCoords = pathCoords;
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
    
    public Date getStart(){
        return start;
    }

    public Date getEnd(){
        return end;
    }

    public String getCenterCoord(){
        return centerCoord;
    }

    public String getPathCoords(){
        return pathCoords;
    }
}
