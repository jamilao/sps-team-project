// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.ArrayList;
import java.text.DateFormat; 
import java.text.SimpleDateFormat;
import java.text.ParseException;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    ArrayList<Event> events = new ArrayList<>();
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson g = new Gson();
    Key key = g.fromJson(request.getParameter("key"), com.google.appengine.api.datastore.Key.class);
    key = KeyFactory.createKey("Event",key.getId()); 
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity eventEntity = new Entity("Event");

    if (key == null){
        key = eventEntity.getKey();
    }
    else {
        try {
            eventEntity = datastore.get(key);
        }
        catch (EntityNotFoundException enfe) {
            System.out.println(enfe);
            key = eventEntity.getKey();
        }
    }

    String organizer = "PLACEHOLDER"; //TO-DO: Grab this from whoever is logged in.
    String eventName = request.getParameter("eventName");
    String location = request.getParameter("location");
    String description = request.getParameter("description");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
    Date start = new Date();
    try {
        start = sdf.parse(request.getParameter("start"));
    } 
    catch (ParseException pe) {
        System.out.println(pe);
        return;
    }
    Date end = new Date();
    try {
        end = sdf.parse(request.getParameter("end"));
    } 
    catch (ParseException pe) {
        System.out.println(pe);
        return;
    }
    String centerCoord = request.getParameter("centerCoord");
    String pathCoords = request.getParameter("pathCoords");

    eventEntity.setProperty("organizer", organizer);
    eventEntity.setProperty("eventName", eventName);
    eventEntity.setProperty("location", location);
    eventEntity.setProperty("description", description);
    eventEntity.setProperty("start", start);
    eventEntity.setProperty("end", end);
    eventEntity.setProperty("centerCoord", centerCoord);
    eventEntity.setProperty("pathCoords", pathCoords);
    Event event = new Event(organizer, eventName, location, description, start, end, key, centerCoord, pathCoords);
    String password = event.getPassword();
    eventEntity.setProperty("password", password);

    datastore.put(eventEntity);

    // Displays password for the newly generated event in a new window.
    response.getOutputStream().println("<p>Your password is " + password + "</p><br>");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Event");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable()){
            String organizer = (String) entity.getProperty("organizer");
            String eventName = (String) entity.getProperty("eventName");
            String location = (String) entity.getProperty("location");
            String description = (String) entity.getProperty("description");
            Date start = (Date) entity.getProperty("start");
            Date end = (Date) entity.getProperty("end");
            Key key = entity.getKey();
            String centerCoord = (String) entity.getProperty("centerCoord");
            String pathCoords = (String) entity.getProperty("pathCoords");
            Event event = new Event(organizer,eventName,location,description,start,end,key, centerCoord, pathCoords);
            event.setPassword((String) entity.getProperty("password"));
            events.add(event);
        }
        String json_events = convertToJson(events);
        response.setContentType("application/json;");
        response.getWriter().println(json_events);
  }
  public String convertToJson(ArrayList items){
      Gson gson = new Gson();
      String json = gson.toJson(items);
      return json;
  }
}
