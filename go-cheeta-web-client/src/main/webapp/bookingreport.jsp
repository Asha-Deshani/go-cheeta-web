<%-- 
    Document   : branch
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Calendar"%>
<%@page import="javax.xml.datatype.Duration"%>
<%@page import="javax.xml.datatype.DatatypeFactory"%>
<%@page import="javax.xml.datatype.XMLGregorianCalendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="lk.gocheeta.web.service.controller.BookingDetail"%>
<%@page import="lk.gocheeta.web.service.controller.CustomerWebService_Service"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleType"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleTypeWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.DriverWebService_Service"%>
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
         <%
                BranchWebService_Service branchService = new BranchWebService_Service();
                List<Branch> branchList = branchService.getBranchWebServicePort().getBranches(); 
                 
                String branchId = request.getParameter("branch");
                String startdate = request.getParameter("startdate");
                String enddate = request.getParameter("enddate");
                 BigDecimal totalSales = BigDecimal.ZERO;
                 if(branchId == null) {
                    branchId = "-1";
                }
                 String branchselect = request.getParameter("branchselect");
            %>
        <h1>Booking Report!</h1>
        <div class="containercolumn">
            <div class="containerrow">
            <div class="left">
               <h2>GoCheeta bookings and income report</h2>
                <br />
            <form action = "" method = "POST">
                <label>Select Branch</label><select type = "text" name = "branch" required="true">
                    <option value="-1" <%= Integer.parseInt(branchId) == -1 ? "selected" : "" %> ''>All Branches</option>
                    <% for (Branch branchSelect : branchList) { %>
                    <option value="<%= branchSelect.getId()%>" <%= Integer.parseInt(branchId) == branchSelect.getId() ? "selected" : "" %> ''>
                                <%= branchSelect.getCity() + " - " + branchSelect.getName()%></option>
                    <%}%>
                    </select>
                    <label>Start Date</label><input type = "date" name = "startdate" required="true" value="<%=startdate%>"/>
                    <label>End Date</label><input type = "date" name = "enddate" required="true" value="<%=enddate%>"/>
                <br />
                <input type = "submit" name ="branchselect" value = "Search Bookings" />
            </form>
          </div> </div>
                    <%
             if(branchselect != null) {
                       %>
                <div class="containerrow">
                    <div class="containercolumnreport">
                    <div class="leftfull">
                        <% 
                            String[] startTimeArray = startdate.split("-");
                            String[] endTimeArray = enddate.split("-");
                            
                           BookingWebService_Service bookingWebService_Service = new BookingWebService_Service();
                            GregorianCalendar gcal = new GregorianCalendar(Integer.parseInt(startTimeArray[0]), Integer.parseInt(startTimeArray[1]) -1, Integer.parseInt(startTimeArray[2]));
                            XMLGregorianCalendar xgcalStart = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
                            
                            gcal.set(Integer.parseInt(endTimeArray[0]), Integer.parseInt(endTimeArray[1]) -1, Integer.parseInt(endTimeArray[2]));
                            gcal.add(Calendar.DATE, 1);
                            XMLGregorianCalendar xgcalEnd = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
                    
                           List<BookingDetail> bookingDetailList = bookingWebService_Service.getBookingWebServicePort().getBookingsDetailsByBranchId(Integer.parseInt(branchId), xgcalStart, xgcalEnd);
                           
                         if(bookingDetailList != null) {
                           
                          %>
                              <h2>Total booking count: <%=bookingDetailList.size()%></h2>
                          <br/>
                                             <table border="2">
                               <tr>
                                    <th>Customer</th>
                                    <th>Driver</th>
                                    <th>Vehicle</th>
                                    <th>Fare Rs.</th>
                                    <th>Distance</th>
                                    <th>Status</th>
                                    <th>Book time</th>
                                    <th>Start time</th>
                                    <th>End time</th>
                                    <th>Customer feedback</th>
                                    <th>Driver feedback</th>
                               </tr>
                               <%
                            for (BookingDetail booking : bookingDetailList) {
                                if(booking.getStatus().equals(STATUS_COMPLETED)) {
                                    totalSales = totalSales.add(booking.getFare());
                                 }
                            %>
                                <tr>
                                    <td><%=booking.getCustomerName()%></td>
                                    <td><%=booking.getDriverName()%></td>
                                    <td><%=booking.getVehicleDetail()%></td>
                                    <td><%=booking.getFare() == null? "" : booking.getFare()%></td>
                                    <td><%=booking.getDistance()%></td>
                                    <td><%=booking.getStatus() == null? "" : booking.getStatus()%></td>
                                    <td><%=booking.getBooktime() == null? "" : booking.getBooktime()%></td>
                                    <td><%=booking.getStarttime() == null? "" : booking.getStarttime()%></td>
                                    <td><%=booking.getEndtime() == null? "" : booking.getEndtime()%></td>
                                    <td><%=booking.getCustomerFeedback() == null? "" : booking.getCustomerFeedback()%></td>
                                    <td><%=booking.getDriverFeedback() == null? "" : booking.getDriverFeedback()%></td>
                                </tr>
                           <%}%>
                                         </table>
                                                                  </div> 
                                           <div class="left">
                    <h2>Total completed trips sales for above selection is <%=totalSales%></h2>
                                         </div>
                </div> 
                </div>
                                      
                       
         <% } 
    } %>
        </div>
    </body>
</html>
