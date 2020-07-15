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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
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
    ArrayList<EventData> events = new ArrayList<>();
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Entity eventEntity = new Entity("Event");

    String organizer = "PLACEHOLDER"; //TO-DO: Grab this from whoever is logged in.
    String eventName = request.getParameter("eventName");
    String location = request.getParameter("location");
    String description = request.getParameter("description");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    try{
        date = sdf.parse(request.getParameter("date"));
    } 
    catch (ParseException pe){
        System.out.println(pe);
        return;
    }

    eventEntity.setProperty("organizer", organizer);
    eventEntity.setProperty("eventName", eventName);
    eventEntity.setProperty("location", location);
    eventEntity.setProperty("description", description);
    eventEntity.setProperty("date", date);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(eventEntity);
    response.sendRedirect("/testForm.html");
  }
  //TODO add doGet method
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
            Date date = (Date) entity.getProperty("date");
            EventData event = new EventData(organizer,eventName,location,description,date);
            events.add(event);
        }
        String json_events = convertToJson(events);
        response.setContentType("application/json;");
        response.getWriter().println(json_events);
  }
   private String convertToJson(ArrayList items){
      Gson gson = new Gson();
      String json = gson.toJson(items);
      return json;
  }
  private class EventData {
      String organizer;
      String eventName;
      String location;
      String description;
      String date;
      public EventData(String organizer, String eventName, String location, String description, Date date){
          this.organizer = organizer;
          this.eventName = eventName;
          this.location = location;
          this.description = description;
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
          this.date = dateFormat.format(date);
      }
  }
}
