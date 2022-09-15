<%-- 
    Document   : acountcreated.jsp
    Created on : 07/09/2022, 12:33:00 AM
    Author     : asha
--%>

<%@page import="java.util.Date"%>
<%@page import="lk.gocheeta.web.service.controller.DistanceWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.Vehicle"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleType"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleTypeWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.Location"%>
<%@page import="lk.gocheeta.web.service.controller.LocationWebService_Service"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="lk.gocheeta.web.service.controller.Branch"%>
<%@page import="lk.gocheeta.web.service.controller.BranchWebService_Service"%>
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
         <%
                 BranchWebService_Service branchService = new BranchWebService_Service();
                 List<Branch> branchList = branchService.getBranchWebServicePort().getBranches(); 
                 Map<Integer, String> branchIdNameMap = new HashMap();
                    for(Branch branchItem : branchList){
                       branchIdNameMap.put(branchItem.getId(), branchItem.getName());
                    }
                    
                String branchId = request.getParameter("branch");
               String vehicletype = request.getParameter("vehicletype");
               String origineId = request.getParameter("origine");
               String destinationId = request.getParameter("destination");
               
               if (branchId == null) {
                  branchId = "-1";
               }
               if (origineId == null) {
                  origineId = "-1";
               }
               if (destinationId == null) {
                  destinationId = "-1";
               }
          %>
           <div class="containercolumn">
           <div class="containerrow">
            <div class="leftsmall">
                <h2>Lets book a ride</h2>
                <br />
            <form action = "" method = "POST">
                <label>Select your Branch</label><select type = "text" name = "branch" required="true">
                    <% for (Branch branchSelect : branchList) { %>
                    <option value="<%= branchSelect.getId()%>" <%= Integer.parseInt(branchId) == branchSelect.getId() ? "selected" : "" %> ''>
                                <%= branchSelect.getCity() + " - " + branchSelect.getName()%></option>
                    <%}%>
                    </select>
                <br />
                <input type = "submit" name ="branchselect" value = "Create Location" />
            </form>
          </div>
               <%
                    LocationWebService_Service locationService = new LocationWebService_Service();
                    VehicleTypeWebService_Service vehicleTypeWebService = new VehicleTypeWebService_Service();
                    
                    List<Location> locationList = null;
                    List<VehicleType> vehicleTypeList = null;
                     
                      String branchselect = request.getParameter("branchselect");
             if(branchselect != null) {
      
                  out.print(branchId);
                try {
                    locationList = locationService.getLocationWebServicePort().getLocationsByBranchId(Integer.parseInt(branchId));
                     vehicleTypeList = vehicleTypeWebService.getVehicleTypeWebServicePort().getVehicleTypes();
                } catch (Exception ex) {
                out.print(ex);
                    %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
                }
          
                       %>
                       
                       <div class="rightbig">
                <br />
                <br />
                <br />
                <br />
            <form action = "" method = "POST">
                <label>Select Origin</label><select type = "text" name = "origine" required="true">
                    <% for (Location locationOrigin : locationList) { %>
                    <%= locationOrigin.getAddress()%>
                            <option value="<%= locationOrigin.getId()%>" 
                                    <%= Integer.parseInt(origineId) == locationOrigin.getId() ? "selected" : "" %>>
                                <%= locationOrigin.getAddress()%></option>
                    <%}%>
                    </select>
                <label>Select Destination</label><select type = "text" name = "destination" required="true">
                    <% for (Location locationDestination : locationList) { %>
                            <option value="<%= locationDestination.getId()%>"
                                    <%= Integer.parseInt(destinationId) == locationDestination.getId() ? "selected" : "" %>>
                                <%= locationDestination.getAddress()%></option>
                    <%} %>
                    </select>
                 <label>Select Vehicle Type</label><select type = "text" name = "vehicletype" required="true">
                    <% for (VehicleType vehicleType : vehicleTypeList) { %>
                            <option value="<%= vehicleType.getId() + "," + vehicleType.getRate() %>"
                                <%= (vehicleType.getId() + "," + vehicleType.getRate()).equals(vehicletype) ? "selected" : "" %>>
                                <%= vehicleType.getName() + " - Rs." + vehicleType.getRate() %></option>
                    <%}%>
                    </select>
                    <br />
                 <input type ="hidden" name ="branchselect" value="branchselect"/>
                 <input type ="hidden" name ="branch" value="<%=branchId%>"/>
                <input type = "submit" name ="searchvehicle" value = "Search Vehicles" />
            </form>
          </div> 
                    <% } %>
           </div>
           <div class="containerrow">
                <div class="left">
              <% String searchvehicle = request.getParameter("searchvehicle");
             VehicleWebService_Service vehicleWebService = new VehicleWebService_Service();
             DistanceWebService_Service distanceWebService_Service = new DistanceWebService_Service();
                                 
             if(searchvehicle != null) {
        
               String[] vehicleInfoArray = vehicletype.split(",");
               int vehicleTypeId = Integer.parseInt(vehicleInfoArray[0]);
               float vehicleRate = Float.parseFloat(vehicleInfoArray[1]);
               
                List<Vehicle> vehicleList = vehicleWebService.getVehicleWebServicePort().getVehicleByBranchIdAndVehicleTypeId(Integer.parseInt(branchId), vehicleTypeId);
                float distance = distanceWebService_Service.getDistanceWebServicePort().getDistance(Integer.parseInt(origineId), Integer.parseInt(destinationId));
            
              %>
                <form action = "" method = "POST">
                    <H5>Distance <%=distance%> km</H5> <H5>Amount Rs. <%=vehicleRate*distance%></H5>
              <br/>
                                 <table border="2">
                   <tr>
                        <th>Make</th>
                        <th>Model</th>
                        <th>Year</th>
                        <th>Book Taxi</th>
                        <th>Delete</th>
                   </tr>
                   <%
                for (Vehicle vehicle : vehicleList) {
                %>
                    <tr>
                        <td><%=vehicle.getModel()%></td>
                        <td><%=vehicle.getModel()%></td>
                        <td><%=vehicle.getYear()%></td>
                        <td><a href="branch.jsp?bookId=<%=vehicle.getId()%>">Book Taxi</a></td>
                        <td><a href="branch.jsp?deleteId=<%=vehicle.getId()%>">Delete</a></td>
                    </tr>
               <%}%>
                             </table>
                               </form>
                                                          </div>

               </div>
             <%}
              String bookId = request.getParameter("bookId");
            if(bookId != null) {
                out.print("bookId " + bookId);
                out.print("bookId " + bookId);
                out.print("bookId " + bookId);
            }
             
             
             
             %>
           </div>
    </body>
 </html>
