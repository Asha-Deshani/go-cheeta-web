<%-- 
    Document   : branch
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Booking"%>
<%@page import="lk.gocheeta.web.service.controller.BookingWebService_Service"%>
<%@page import="java.util.List"%>
<%@page import="lk.gocheeta.web.service.controller.Branch"%>
<%@page import="lk.gocheeta.web.service.controller.BranchWebService_Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="masterpage.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Manage Branch!</h1>
        <div class="containercolumn">
         <div class="left">
            <h2>Create a Branch</h2>
            <br />
            <form action = "branch.jsp" method = "POST">
                <label>Name</label><input type = "text" name = "name" required="true"/>
                <br />
                <label>City</label><input type = "text" name = "city" required="true"/>
                <br />
                <input type = "submit" name ="newbranch" value = "Create Branch" />
            </form>
          </div>
         <%
             
                 BookingWebService_Service bookingWebService_Service = new BookingWebService_Service();

              String bookvehicleId = request.getParameter("bookvehicleId");
            if(bookvehicleId != null) {
              String branchId = request.getParameter("branchId");


                Booking booking = new Booking();
                booking.setBranchId(Integer.parseInt(branchId));
                booking.setCustomerId(loginUserId);
                booking.setVehicleId(Integer.parseInt(bookvehicleId));
                booking.setStatus("STARTED");
                booking.setDistance(distance);
                booking.setFare(fare);
                bookingWebService_Service.getBookingWebServicePort().addBooking(booking);

             %><<h2>Booking added successfully</h2> <%
            }
        
            %>
  
        </div>
    </body>
</html>
