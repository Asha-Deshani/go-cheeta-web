<%-- 
    Document   : location
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.function.Function"%>
<%@page import="lk.gocheeta.web.service.controller.Branch"%>
<%@page import="lk.gocheeta.web.service.controller.BranchWebService_Service"%>
<%@page import="java.util.List"%>
<%@page import="lk.gocheeta.web.service.controller.Location"%>
<%@page import="lk.gocheeta.web.service.controller.LocationWebService_Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="masterpage.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Manage Locations!</h1>
        <div>
         <div class="left">
            <h2>Create a Location</h2>
            <br />
            <%
                 BranchWebService_Service branchService = new BranchWebService_Service();
                 List<Branch> branchList = branchService.getBranchWebServicePort().getBranches(); 
                 
                 Map<Integer, String> branchIdNameMap = new HashMap();
                 for(Branch branchItem : branchList){
                    branchIdNameMap.put(branchItem.getId(), branchItem.getName());
                 }
            %>
            <form action = "location.jsp" method = "POST">
                Address <input type = "text" name = "address" required="true"/>
                <br />
                Branch <select type = "text" name = "branch" required="true">
                    <% for (int i = 0; i < branchList.size(); i++) { %>
                            <option value="<%= branchList.get(i).getId()%>"><%=  branchList.get(i).getName()%></option>
                    <%}%>
                    </select>
                <br />
                <input type = "submit" name ="newlocation" value = "Create Location" />
            </form>
          </div>
         <%
             LocationWebService_Service locationService = new LocationWebService_Service();
             
             String newlocation = request.getParameter("newlocation");
             if(newlocation != null) {
                String address = request.getParameter("address");
                String branchId = request.getParameter("branch");
                
                if(address == null || branchId == null){
                  %> <h3>Address and Branch can't be empty!</h3> <%
                  return;
                }

                Location location = new Location();
                location.setAddress(address);
                location.setBranchId(Integer.parseInt(branchId));

                try {
                    location = locationService.getLocationWebServicePort().addLocation(location);
                    %> <h4>Location <%= location.getAddress()%> successfully created!</h4> <%
                } catch (Exception e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                     }
                }
             }
            List<Location> locationList = null;
            try {
                locationList = locationService.getLocationWebServicePort().getLocations();
            } catch (Exception ex) {
                %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
            }
            if(locationList != null && locationList.size() > 0){
                %>
               <div class="left">
                <h2>Location List count <%=locationList.size()%></h2>
                <br />
                <table border="2">
                   <tr>
                        <td>Address</td>
                        <td>Branch</td>
                        <td>Update</td>
                        <td>Delete</td>
                   </tr>
                   <%
                for (Location location : locationList) {
                %>
                    <tr>
                        <td><%=location.getAddress()%></td>
                        <td><%=branchIdNameMap.get(location.getBranchId())%></td>
                        <td><a href="location.jsp?editId=<%=location.getId()%>">Update</a></td>
                        <td><a href="location.jsp?deleteId=<%=location.getId()%>">Delete</a></td>
                    </tr>
               <%}
            }
             
  
            String deleteId = request.getParameter("deleteId");
            if(deleteId != null) {
                int id = Integer.parseInt(deleteId);
                try {
                    locationService.getLocationWebServicePort().deleteLocation(id);
                    %> <h4>Location successfully deleted!</h4> <%
                } catch (Exception e) {
                    if (e.getMessage().contains("foreign key constraint fails")) {
                        %> <h3>Error: Delete child records before delete this record!</h3> <%
                     } else {
                        %> <h3>Error: <%=e.getMessage()%></h3> <%
                    }
                }
            }

            String editId = request.getParameter("editId");
            if(editId != null) {
                int id = Integer.parseInt(editId);
                Location editLocation = null;
                try {
                    editLocation = locationService.getLocationWebServicePort().getLocation(id);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editLocation == null){
                    %> <h3>Error: No location with the selected id!</h3> <%
                    return;
                } 
%>
            <div class="left">
                <h2>Update Location</h2>
                <br />
                <form action = "location.jsp" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editLocation.getId()%>"/>
                    <br />
                    Address <input type = "text" name = "address" required="true" value="<%=editLocation.getAddress()%>"/>
                    <br />
                    Branch <select type = "text" name = "branch" required="true">
                        <%for (Branch itemEdit : branchList) {%>
                        <option value="<%= itemEdit.getId()%>"><%= itemEdit.getName()%></option>
                        <%}%>
                        </select>
                <br />
                <input type = "submit" name ="editlocation" value = "Update Location" />
                </form>
            </div>
<%
            }
            %>
            <%
                String editlocation = request.getParameter("editlocation");
                if(editlocation != null) {
                    String address = request.getParameter("address");
                    String branch = request.getParameter("branch");
                    String idEdit = request.getParameter("id");
                    
                    if(address == null || branch == null || idEdit == null){
                        %> <h3>Id, address and branch can't be empty!</h3> <%
                        return;
                    }

                    Location location = new Location();
                    location.setAddress(address);
                    location.setBranchId(Integer.parseInt(branch));
                    location.setId(Integer.parseInt(idEdit));

                    try {
                        location = locationService.getLocationWebServicePort().updateLocation(location);
                        %> <h4>Location <%= location.getAddress()%> successfully updated!</h4> <%
                    } catch (Exception e) {
                        if (e.getMessage().contains("Duplicate entry")) {
                            String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                            %> <h3>Error: <%=error%>!</h3> <%
                         }
                    }
                }
            %>
                </table>
            </div>
        </div>
    </body>
</html>
