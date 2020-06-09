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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      String urlToRedirectToAfterUserLogsIn = "/login";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

        // To make this page look nice I added html injections
      response.getWriter().println("<style> body {margin-left:auto; margin-right:auto; width:650px; background-color: lightblue;} #canvas{background-color: lightblue;}</style> <p>Hello stranger.</p>");
      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
      return;
    }
    
    String nickname = getUserNickname(userService.getCurrentUser().getUserId());
    if (nickname == null) {
      response.sendRedirect("/nickname");
      return;
    }

    String userEmail = userService.getCurrentUser().getEmail();
    String urlToRedirectToAfterUserLogsOut = "/login";
    String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

    response.getWriter().println("<p>Hello " + userEmail + "! You have successfully logged in!</p>");
    response.getWriter().println("<p>This is your nickname: " + nickname  + "</p>");
    response.getWriter().println("<a href=\"" + logoutUrl + "\"><button>Logout</button/></a>");

    }


   private String getUserNickname(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query =
        new Query("UserInfo");
            // .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
      return null;
    }
    String nickname = (String) entity.getProperty("nickname");
    return nickname;
  }
}
