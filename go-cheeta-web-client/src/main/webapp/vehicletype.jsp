<%-- 
    Document   : branch
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.VehicleType"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleTypeWebService_Service"%>
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
        <h1>Manage Vehicle Type!</h1>
        <div class="containercolumn">
         <div class="left">
            <h2>Create a Vehicle Type</h2>
            <br />
            <form action = "" method = "POST">
                <label>Name</label><input type = "text" name = "name" required="true"/>
                <br />
                <label>Rate</label><input type = "text" name = "rate" required="true"/>
                <br />
                <input type = "submit" name ="newvehicletype" value = "Create Vehicle Type" />
            </form>
          </div>
         <%
             VehicleTypeWebService_Service vehicleeTypeservie = new VehicleTypeWebService_Service();
             
             String newvehicletype = request.getParameter("newvehicletype");
             if(newvehicletype != null) {
                String name = request.getParameter("name");
                String rate = request.getParameter("rate");
                
                if(name == null || rate == null){
                  %> <h3>Name and Rate can't be empty!</h3> <%
                  return;
                }

                VehicleType vehicleType = new VehicleType();
                vehicleType.setName(name);
                vehicleType.setRate(Float.parseFloat(rate));

                try {
                    vehicleType = vehicleeTypeservie.getVehicleTypeWebServicePort().addVehicleType(vehicleType);
                    %> <h4>Vehicle Type <%= vehicleType.getName() %> successfully created!</h4> <%
                } catch (Exception e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                     }
                }
             }
            List<VehicleType> vehicleTypeList = null;
            try {
                vehicleTypeList = vehicleeTypeservie.getVehicleTypeWebServicePort().getVehicleTypes();
            } catch (Exception ex) {
                %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
            }
            if(vehicleTypeList != null && vehicleTypeList.size() > 0){
                %>
               <div class="left">
                <h2>Vehicle Type List count <%=vehicleTypeList.size()%></h2>
                <br />
                <table border="2">
                   <tr>
                        <th>Name</th>
                        <th>Rate</th>
                        <th>Update</th>
                        <th>Delete</th>
                   </tr>
                   <%
                for (VehicleType vehicleType : vehicleTypeList) {
                %>
                    <tr>
                        <td><%=vehicleType.getName()%></td>
                        <td><%=vehicleType.getRate()%></td>
                        <td><a href="vehicletype.jsp?editId=<%=vehicleType.getId()%>">Update</a></td>
                        <td><a href="vehicletype.jsp?deleteId=<%=vehicleType.getId()%>">Delete</a></td>
                    </tr>
               <%}%>
                             </table>
            </div>
            <%}
             
  
            String deleteId = request.getParameter("deleteId");
            if(deleteId != null) {
                int id = Integer.parseInt(deleteId);
                try {
                    vehicleeTypeservie.getVehicleTypeWebServicePort().deleteVehicleType(id);
                    %> <h4>Branch successfully deleted!</h4> <%
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
                VehicleType editVehicleType = null;
                try {
                    editVehicleType = vehicleeTypeservie.getVehicleTypeWebServicePort().getVehicleType(id);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editVehicleType == null){
                    %> <h3>Error: No vehicle type with the selected id!</h3> <%
                    return;
                } 
%>
            <div class="left">
                <h2>Update Vehicle type</h2>
                <br />
                <form action = "" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editVehicleType.getId()%>"/>
                    <br />
                    <label>Name</label><input type = "text" name = "name" required="true" value="<%=editVehicleType.getName()%>"/>
                    <br />
                    <label>Rate</label><input type = "text" name = "rate" required="true" value="<%=editVehicleType.getRate()%>"/>
                    <br />
                    <input type = "submit" name ="editVehicleType" value = "Update Vehicle Type" />
                </form>
            </div>
<%
            }
            %>
            <%
                String editVehicleType = request.getParameter("editVehicleType");
                if(editVehicleType != null) {
                    String name = request.getParameter("name");
                    String rate = request.getParameter("rate");
                    String id = request.getParameter("id");
                    
                    if(name == null || rate == null || id == null){
                        %> <h3>Id, Name and Rate can't be empty!</h3> <%
                        return;
                    }

                    VehicleType vehicleType = new VehicleType();
                    vehicleType.setName(name);
                    vehicleType.setRate(Float.parseFloat(rate));
                    vehicleType.setId(Integer.parseInt(id));

                    try {
                        vehicleType = vehicleeTypeservie.getVehicleTypeWebServicePort().updateVehicleType(vehicleType);
                        %> <h4>Vehicle Type <%= vehicleType.getName() %> successfully updated!</h4> <%
                    } catch (Exception e) {
                        if (e.getMessage().contains("Duplicate entry")) {
                            String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                            %> <h3>Error: <%=error%>!</h3> <%
                         }
                    }
                }
            %>
  
        </div>
    </body>
</html>
