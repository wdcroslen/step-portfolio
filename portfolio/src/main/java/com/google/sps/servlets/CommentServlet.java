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
import java.io.IOException;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that processes text. */
@WebServlet("/text")
public final class CommentServlet extends HttpServlet {

  public List<String> messages;

//   @Override
//   public void init(){
//     //   messages = new ArrayList<>();
//     //   messages.add("Potato");
//     //   messages.add("hot");
      
//   }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment"); //.addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    List<String> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

      comments.add(text);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));

    // response.setContentType("application/json");
    // String json = new Gson().toJson(messages);
    // response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "text-input", "oof");
    long timestamp = System.currentTimeMillis();
    
    Entity taskEntity = new Entity("Comment");
    taskEntity.setProperty("text", text);
    taskEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);
    response.sendRedirect("/comment.html");

  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
    private String convertToJsonUsingGson(ArrayList messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
  
  private String convertToJson(ArrayList messages) {
    String json = "{";
    json += "\"first\": ";
    json += "\"" + messages.get(0) + "\"";
    json += ", ";
    json += "\"second\": ";
    json += "\"" + messages.get(1) + "\"";
    json += ", ";
    json += "\"third\": ";
    json += "\"" + messages.get(2) + "\"";
    json += "}";
    return json;
  }
}