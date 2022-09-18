<%-- 
    Document   : acountcreated.jsp
    Created on : 07/09/2022, 12:33:00 AM
    Author     : asha
--%>

<%@page import="javax.xml.datatype.DatatypeFactory"%>
<%@page import="javax.xml.datatype.XMLGregorianCalendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="lk.gocheeta.web.service.controller.Booking"%>
<%@page import="java.util.List"%>
<%@page import="lk.gocheeta.web.service.controller.BookingWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.Admin"%>
<%@page import="lk.gocheeta.web.service.controller.Login"%>
<%@page import="lk.gocheeta.web.service.controller.Customer"%>
<%@page import="lk.gocheeta.web.service.controller.CustomerWebService_Service"%>
<%@page import="java.util.Enumeration"%>
<%@page import="lk.gocheeta.web.service.controller.LoginWebService_Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="masterpage.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Manage Booking!</h1>
        <div class="containercolumn">
         <div class="left">
            <h2>Don't forget to leave a feedback for your trip</h2>
       
            <% 
               BookingWebService_Service bookingWebService_Service = new BookingWebService_Service();
               List<Booking> bookinglist = bookingWebService_Service.getBookingWebServicePort().getBookingsByDriverId(loginUserId);
                           
             if(bookinglist != null) {
              %>
                  <H5>Total booking count: <%=bookinglist.size()%></H5>
              <br/>
                                 <table border="2">
                   <tr>
                        <th>Fare</th>
                        <th>Distance</th>
                        <th>Status</th>
                        <th>Book time</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Customer feedback</th>
                        <th>Driver feedback</th>
                        <th>Give feedback</th>
                   </tr>
                   <%
                for (Booking booking : bookinglist) {
                %>
                    <tr>
                        <td><%=booking.getFare() == null? "" : booking.getFare()%></td>
                        <td><%=booking.getDistance()%></td>
                        <td><%=booking.getStatus() == null? "" : booking.getStatus()%></td>
                        <td><%=booking.getBooktime() == null? "" : booking.getBooktime()%></td>
                        <td><%=booking.getStarttime() == null? "" : booking.getStarttime()%></td>
                        <td><%=booking.getEndtime() == null? "" : booking.getEndtime()%></td>
                        <td><%=booking.getCustomerFeedback() == null? "" : booking.getCustomerFeedback()%></td>
                        <td><%=booking.getDriverFeedback() == null? "" : booking.getDriverFeedback()%></td>
                        <td><%=booking.getStatus().equals(STATUS_COMPLETED) ? "<a href=\"driverhome.jsp?bookingcompletedid=" +booking.getId() + "\">Give feedback</a>" : 
                               booking.getStatus().equals(STATUS_STARTED) ? "<a href=\"driverhome.jsp?bookingstartid=" +booking.getId() + "\">Complete trip</a>" : 
                               booking.getStatus().equals(STATUS_PENDING) ? "<a href=\"driverhome.jsp?bookingpendingid=" +booking.getId() + "\">Accept trip</a>" : 
                               booking.getStatus().equals(STATUS_ACCEPTED) ? "<a href=\"driverhome.jsp?bookingacceptedid=" +booking.getId() + "\">Start trip</a>" : ""%> </td>
                    </tr>
               <%}%>
                             </table>
                                                      </div>    </div>

           <%}   
            String bookingcompletedid = request.getParameter("bookingcompletedid");
            if(bookingcompletedid != null) {
                int id = Integer.parseInt(bookingcompletedid);
                Booking editBooking = null;
                try {
                    editBooking = bookingWebService_Service.getBookingWebServicePort().getBooking(id);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editBooking == null){
                    %> <h3>Error: No booking with the selected id!</h3> <%
                    return;
                } 
%>
            <div class="left">
                <h2>Give Feedback</h2>
                <br />
                <form action = "driverhome.jsp" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editBooking.getId()%>"/>
                    <br />
                    <label>Driver Feedback</label><textarea name = "driverfeedback" rows="4" cols="50" required="true"><%=editBooking.getDriverFeedback()%></textarea>
                    <br/>
                    <br/>
                    <input type = "submit" name ="editbooking" value = "Give Feedback" />
                </form>
            </div>
<%
            }
                String editbooking = request.getParameter("editbooking");
                if(editbooking != null) {
                    String driverfeedback = request.getParameter("driverfeedback");
                    String id = request.getParameter("id");
                    if(id == null){
                        %> <h3>Id can't be empty!</h3> <%
                        return;
                    }

                    Booking editBooking = bookingWebService_Service.getBookingWebServicePort().getBooking(Integer.parseInt(id));
                    editBooking.setDriverFeedback(driverfeedback);

                    try {
                        editBooking = bookingWebService_Service.getBookingWebServicePort().updateBooking(editBooking);
                        %> <h4>Booking <%= editBooking.getCustomerFeedback()%> successfully updated!</h4> <%
                    } catch (Exception e) {
                        if (e.getMessage().contains("Duplicate entry")) {
                            String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                            %> <h3>Error: <%=error%>!</h3> <%
                         }
                    }
                    response.sendRedirect("driverhome.jsp");
                }

            String bookingstartid = request.getParameter("bookingstartid");
            if(bookingstartid != null) {
                int id = Integer.parseInt(bookingstartid);
                Booking editBooking = null;
                try {
                    editBooking = bookingWebService_Service.getBookingWebServicePort().getBooking(id);

                    GregorianCalendar gcal = new GregorianCalendar();
                    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(gcal);

                    editBooking.setStatus(STATUS_COMPLETED);
                    editBooking.setEndtime(xgcal);
                    bookingWebService_Service.getBookingWebServicePort().updateBooking(editBooking);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editBooking == null){
                    %> <h3>Error: No booking with the selected id!</h3> <%
                    return;
                } 
                response.sendRedirect("driverhome.jsp");
            }

            String bookingpendingid = request.getParameter("bookingpendingid");
            if(bookingpendingid != null) {
                int id = Integer.parseInt(bookingpendingid);
                Booking editBooking = null;
                try {
                    editBooking = bookingWebService_Service.getBookingWebServicePort().getBooking(id);

                    editBooking.setStatus(STATUS_ACCEPTED);
                    bookingWebService_Service.getBookingWebServicePort().updateBooking(editBooking);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editBooking == null){
                    %> <h3>Error: No booking with the selected id!</h3> <%
                    return;
                } 
                response.sendRedirect("driverhome.jsp");
            }

            String bookingacceptedid = request.getParameter("bookingacceptedid");
            if(bookingacceptedid != null) {
                int id = Integer.parseInt(bookingacceptedid);
                Booking editBooking = null;
                try {
                    editBooking = bookingWebService_Service.getBookingWebServicePort().getBooking(id);
                    GregorianCalendar gcal = new GregorianCalendar();
                    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(gcal);

                    editBooking.setStatus(STATUS_STARTED);
                    editBooking.setStarttime(xgcal);
                    bookingWebService_Service.getBookingWebServicePort().updateBooking(editBooking);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editBooking == null){
                    %> <h3>Error: No booking with the selected id!</h3> <%
                    return;
                } 
                response.sendRedirect("driverhome.jsp");
            }

%>
        </div>
    </body>
 </html>
