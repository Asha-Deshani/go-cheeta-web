<%-- 
    Document   : acountcreated.jsp
    Created on : 07/09/2022, 12:33:00 AM
    Author     : asha
--%>

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
                    
          %>
            <div class="left">
                <h2>Lets book a ride</h2>
                <br />
            <form action = "" method = "POST">
                Select your Branch <select type = "text" name = "branch" required="true">
                    <% for (int i = 0; i < branchList.size(); i++) { %>
                            <option value="<%= branchList.get(i).getId()%>"><%= branchList.get(i).getCity() + " - " + branchList.get(i).getName()%></option>
                    <%}%>
                    </select>
                <br />
                <input type = "submit" name ="branchselect" value = "Create Location" />
            </form>
          </div>
               <%
                    LocationWebService_Service locationService = new LocationWebService_Service();
                     List<Location> locationList = null;
                     
                      String branchselect = request.getParameter("branchselect");
             if(branchselect != null) {
                String branchId = request.getParameter("branch");
                  out.print(branchId);
                try {
                    locationList = locationService.getLocationWebServicePort().getLocationsByBranchId(Integer.parseInt(branchId));
                } catch (Exception ex) {
                out.print(ex);
                    %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
                }
          
                       %>
                       
                       <div class="right">
                <br />
                <br />
                <br />
                <br />
            <form action = "" method = "POST">
                Select Origin <select type = "text" name = "origine" required="true">
                    <% for (int i = 0; i < locationList.size(); i++) { %>
                    <%= locationList.get(i).getAddress()%>
                            <option value="<%= locationList.get(i).getId()%>"><%= locationList.get(i).getAddress()%></option>
                    <%}%>
                    </select>
                Select Destination <select type = "text" name = "destination" required="true">
                    <% for (int i = 0; i < locationList.size(); i++) { %>
                            <option value="<%= locationList.get(i).getId()%>"><%= locationList.get(i).getAddress()%></option>
                    <%}%>
                    </select>
                <br />
                <input type = "submit" name ="locationselect" value = "Create Location" />
            </form>
          </div> 
                    <% } %>
    </body>
 </html>
