<%-- 
    Document   : masterpage
    Created on : 11/09/2022, 4:52:53 PM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Admin"%>
<%@page import="lk.gocheeta.web.service.controller.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="main.css"/>
        <title>GoCheeta Leading Taxi</title>
    </head>
    <body>
        <div class="master">
             <h1>GoCheeta Taxi!</h1>
             <h5>Safest way to travel around</h5>
           
             <a href="logout.jsp">Logout</a>
          <%
              int loginUserId = -1;
              if(!request.getServletPath().equals("/index.jsp")){
                final String SESSESION_ID = "sessionId";   
                  boolean isAthuenticated = false;
                  boolean isAdmin = false;
                  Cookie[] cookieArray = request.getCookies();
                  if(cookieArray == null) {
                      response.sendRedirect("index.jsp");
                      return;
                  }

                  for(Cookie cookie : request.getCookies()) {
                      if(cookie.getName().equals(SESSESION_ID)) {
                          Object sessionObject = session.getAttribute(cookie.getValue());
                          if(sessionObject instanceof Admin) {
                              Admin admin = (Admin)sessionObject;
                              isAthuenticated = true;
                              isAdmin = true;
                              loginUserId = admin.getId();
                              %> <h2>Welcome Admin, <%=admin.getName()%>!</h3>
                                   <a href="branch.jsp">Manage Branches</a>
                                   <br/>
                                   <a href="location.jsp">Manage Locations</a>
                               <% 
                          } else if(sessionObject instanceof Customer) {
                              Customer customer = (Customer)sessionObject;
                              isAthuenticated = true;
                              isAdmin = false;
                              loginUserId = customer.getId();
                              %> <h2>Welcome, <%=customer.getName()%>, book your next taxi from here!</h3> <%
                          } 
                      }
                  }
                  if (!isAthuenticated) {
                      response.sendRedirect("index.jsp");
                      return;
                  }
                  if(!isAdmin) {
                    if (request.getServletPath().equals("/adminhome.jsp") ||
                            request.getServletPath().equals("/branch.jsp") ||
                            request.getServletPath().equals("/location.jsp")) { 
                                response.sendRedirect("index.jsp");
                    }
                  }
            }
        %>
         </div>
    </body>
</html>
