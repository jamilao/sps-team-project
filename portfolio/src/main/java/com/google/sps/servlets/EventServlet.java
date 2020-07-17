package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.EntityNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/event")
public class EventServlet extends HttpServlet { 
  ArrayList<Event> events = new ArrayList<>();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Key eventKey = KeyFactory.stringToKey(request.getParameter("key"));   
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      findEvent(datastore, eventKey);

      String json_events = convertToJsonUsingGson(events);
      response.setContentType("application/json;");
      response.getWriter().println(json_events);
  }

  // A separate method to try and handle finding entities.
  private void findEvent(DatastoreService datastore, Key eventKey) {
    try {
        Entity entity = datastore.get(eventKey);
        
        String organizer = (String) entity.getProperty("organizer");
        String eventName = (String) entity.getProperty("eventName");
        String location = (String) entity.getProperty("location");
        String description = (String) entity.getProperty("description");
        Date start = (Date) entity.getProperty("start");
        Date end = (Date) entity.getProperty("end");
        Key key = entity.getKey();
        Event event = new Event(organizer,eventName,location,description,start,end,key);
        event.setPassword((String) entity.getProperty("password"));
        events.add(event);
      }
    catch (EntityNotFoundException enfe) {
        throw new RuntimeException();
    }
  }

  private String convertToJsonUsingGson(ArrayList aList) {
    Gson gson = new Gson();
    String json = gson.toJson(aList);
    return json;
  }
}