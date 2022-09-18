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
        <h1>Manage Booking!</h1>
        <div class="containercolumn">
         <div class="left">
            <h2>Don't forget to leave a feedback for your trip</h2>
       
            <% 
               BookingWebService_Service bookingWebService_Service = new BookingWebService_Service();
               List<Booking> bookinglist = bookingWebService_Service.getBookingWebServicePort().getBookingsByCustomerId(loginUserId);
                           
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
                        <td><%=booking.getStatus().equals(STATUS_COMPLETED) ? "<a href=\"booking.jsp?bookingid=" +booking.getId() + "\">Give feedback</a>" : ""%> </td>
                    </tr>
               <%}%>
                             </table>
                                                      </div>    </div>

           <%}   
            String bookingid = request.getParameter("bookingid");
            if(bookingid != null) {
                int id = Integer.parseInt(bookingid);
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
                <form action = "booking.jsp" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editBooking.getId()%>"/>
                    <br />
                    <label>Customer Feedback</label><textarea name = "customerfeedback" rows="4" cols="50" required="true"><%=editBooking.getCustomerFeedback()%></textarea>
                    <br />
                    <br />
                    <input type = "submit" name ="editbooking" value = "Give Feedback" />
                </form>
            </div>
<%
            }

                String editbooking = request.getParameter("editbooking");
                if(editbooking != null) {
                    String customerfeedback = request.getParameter("customerfeedback");
                    String id = request.getParameter("id");
                    if(id == null){
                        %> <h3>Id can't be empty!</h3> <%
                        return;
                    }

                    Booking editBooking = bookingWebService_Service.getBookingWebServicePort().getBooking(Integer.parseInt(id));
                    editBooking.setCustomerFeedback(customerfeedback);

                    try {
                        editBooking = bookingWebService_Service.getBookingWebServicePort().updateBooking(editBooking);
                        %> <h4>Booking <%= editBooking.getCustomerFeedback()%> successfully updated!</h4> <%
                    } catch (Exception e) {
                        if (e.getMessage().contains("Duplicate entry")) {
                            String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                            %> <h3>Error: <%=error%>!</h3> <%
                         }
                    }
                    response.sendRedirect("booking.jsp");
                }
%>
        </div>
    </body>
</html>
