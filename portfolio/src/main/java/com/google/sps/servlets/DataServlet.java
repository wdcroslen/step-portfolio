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

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private ArrayList<String> words;
  @Override
  public void init(){
      words = new ArrayList<>();
      words.add("Potato");
      words.add("Banana");
      words.add("Orange");
      words.add("Cereal");
  }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    ArrayList<String> messages = new ArrayList<String>();

    messages.add("Hello");
    messages.add("There");
    messages.add("Ladies");
    messages.add("and");
    messages.add("Gentlemen");

    // Convert the ArrayList to JSON
    String json = convertToJsonUsingGson(messages);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);  

    //Retrieve data from /data and display
    // response.setContentType("text/html;");
    // response.getWriter().println("<h1>Hello William!</h1>");

    // String word = words.get((int) (Math.random() * words.size()));
    // response.setContentType("text/html;");
    // response.getWriter().println(word);
  }

  private String convertToJsonUsingGson(ArrayList messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
  
//   private String convertToJson(ArrayList messages) {
//     String json = "{";
//     json += "\"startTime\": ";
//     json += "\"" + serverStats.getStartTime() + "\"";
//     json += ", ";
//     json += "\"currentTime\": ";
//     json += "\"" + serverStats.getCurrentTime() + "\"";
//     json += ", ";
//     json += "\"maxMemory\": ";
//     json += serverStats.getMaxMemory();
//     json += ", ";
//     json += "\"usedMemory\": ";
//     json += serverStats.getUsedMemory();
//     json += "}";
//     return json;
//   }
}
