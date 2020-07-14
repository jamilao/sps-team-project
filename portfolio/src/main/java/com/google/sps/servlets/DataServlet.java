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
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

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
}
