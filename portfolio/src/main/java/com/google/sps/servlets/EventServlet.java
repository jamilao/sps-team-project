package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/event")
public class EventServlet extends HttpServlet { 
  ArrayList<EventData> events = new ArrayList<>();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      int eventKey = Integer.parseInt(request.getParameter("key"));   
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Entity entity = datastore.get(eventKey);

      String organizer = (String) entity.getProperty("organizer");
      String eventName = (String) entity.getProperty("eventName");
      String location = (String) entity.getProperty("location");
      String description = (String) entity.getProperty("description");
      Date date = (Date) entity.getProperty("date");

      EventData event = new EventData(organizer,eventName,location,description,date);
      events.add(event);
      
      String json_events = convertToJson(events);
      response.setContentType("application/json;");
      response.getWriter().println(json_events);
  }
}