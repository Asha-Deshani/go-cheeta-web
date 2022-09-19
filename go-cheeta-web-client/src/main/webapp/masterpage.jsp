<%-- 
    Document   : masterpage
    Created on : 11/09/2022, 4:52:53 PM
    Author     : asha
--%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="lk.gocheeta.web.service.controller.Driver"%>
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
          <%
            final String SESSESION_ID = "sessionId";     
            final String ROLE_CUSTOMER = "CUSTOMER";     
            final String ROLE_ADMIN = "ADMIN";
            final String ROLE_DRIVER = "DRIVER";
            final String ROLE = "ROLE";
            
            final String STATUS_PENDING = "PENDING";
            final String STATUS_ACCEPTED = "ACCEPTED";
            final String STATUS_STARTED = "STARTED";
            final String STATUS_COMPLETED = "COMPLETED";
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            
            int loginUserId = -1;
            String loginRole = null;
            
              if(!request.getServletPath().equals("/index.jsp")){
                  boolean isAthuenticated = false;
                  
                  Cookie[] cookieArray = request.getCookies();
                  if(cookieArray == null) {
                      response.sendRedirect("index.jsp");
                      return;
                  }

                  for(Cookie cookie : request.getCookies()) {
                      if(cookie.getName().equals(SESSESION_ID)) {
                          Object sessionObject = session.getAttribute(cookie.getValue());
                          loginRole = (String)session.getAttribute(cookie.getValue()+ ROLE);
                          
                          if(sessionObject != null && loginRole != null){
                            if(loginRole.equals(ROLE_ADMIN)) {
                                Admin admin = (Admin)sessionObject;
                                isAthuenticated = true;
                                loginUserId = admin.getId();
                                %> <h2>Welcome Admin, <%=admin.getName()%>!</h3>
                                 <div class="navbar">
                                       <a href="branch.jsp">Manage Branches</a>
                                      <a href="location.jsp">Manage Locations</a>
                                      <a href="driver.jsp">Manage Drivers</a>
                                   <a href="vehicletype.jsp">Manage Vehicle Types</a>
                                      <a href="vehicle.jsp">Manage Vehicles</a>
                                    <a href="bookingreport.jsp">Booking Report</a>
                                    <a href="logout.jsp">Logout</a>
                                </div>
                                 <% 
                             } else if (loginRole.equals(ROLE_CUSTOMER)) {
                                Customer customer = (Customer)sessionObject;
                                isAthuenticated = true;
                                loginUserId = customer.getId();
                                %> <h2>Welcome <%=customer.getName()%>, book your next taxi from here!</h3> <%
                                
%>                             <div class="navbar">
                                  <a href="home.jsp">Home</a>
                                  <a href="booking.jsp">Manage Bookings</a>
                                  <a href="logout.jsp">Logout</a>
                                </div>
                                    <% 
                             } else if (loginRole.equals(ROLE_DRIVER)) {
                                Driver driver = (Driver)sessionObject;
                                isAthuenticated = true;
                                loginUserId = driver.getId();
                                %> <h2>Welcome <%=driver.getName()%>, Start and Finish your bookings from here!</h3>
                                    <div class="navbar">
                                  <a href="logout.jsp">Logout</a>
                                </div> <%
                             }
                          }
                        break;
                      }

                  }
                  if (!isAthuenticated) {
                      response.sendRedirect("index.jsp");
                      return;
                  }
                  if(!ROLE_ADMIN.equals(loginRole)) {
                    if (request.getServletPath().equals("/adminhome.jsp") ||
                            request.getServletPath().equals("/branch.jsp") ||
                            request.getServletPath().equals("/location.jsp") ||
                            request.getServletPath().equals("/vehicletype.jsp") ||
                            request.getServletPath().equals("/vehicle.jsp") ||
                            request.getServletPath().equals("/bookingreport.jsp") ||
                            request.getServletPath().equals("/driver.jsp")) { 
                                response.sendRedirect("index.jsp");
                    }
                  }
                  if(!ROLE_CUSTOMER.equals(loginRole)) {
                    if (request.getServletPath().equals("/home.jsp") ||
                            request.getServletPath().equals("/booking.jsp")) {
                                response.sendRedirect("index.jsp");
                    }
                  }
                  if(!ROLE_DRIVER.equals(loginRole)) {
                    if (request.getServletPath().equals("/driverhome.jsp")) {
                                response.sendRedirect("index.jsp");
                    }
                  }
            }
        %>
         </div>
    </body>
</html>
