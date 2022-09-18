<%-- 
    Document   : location
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Vehicle"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleType"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleTypeWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.DriverWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.VehicleWebService_Service"%>
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
        <h1>Manage Vehicle!</h1>
        <div class="containercolumn">
         <div class="left">
            <h2>Create a Vehicle</h2>
            <br />
            <%
                BranchWebService_Service branchService = new BranchWebService_Service();
                DriverWebService_Service driverService = new DriverWebService_Service();
                VehicleTypeWebService_Service vehicleTypeService = new VehicleTypeWebService_Service();
                 
                 List<Branch> branchList = branchService.getBranchWebServicePort().getBranches(); 
                 List<Driver> driverList = driverService.getDriverWebServicePort().getDrivers(); 
                 List<VehicleType> vehicleTypeList = vehicleTypeService.getVehicleTypeWebServicePort().getVehicleTypes(); 
                 
                 Map<Integer, String> branchIdNameMap = new HashMap();
                 for(Branch branchItem : branchList){
                    branchIdNameMap.put(branchItem.getId(), branchItem.getName());
                 }
                 
                 Map<Integer, String> driverIdNameMap = new HashMap();
                 for(Driver driverItem : driverList){
                    driverIdNameMap.put(driverItem.getId(), driverItem.getName());
                 }
                 
                 Map<Integer, String> vTypeIdNameMap = new HashMap();
                 for(VehicleType vehicleType : vehicleTypeList){
                    vTypeIdNameMap.put(vehicleType.getId(), vehicleType.getName());
                 }
            %>
            <form action = "" method = "POST">
                <label>Make</label><input type = "text" name = "make" required="true"/>
                <br />
                <label>Model</label><input type = "text" name = "model" required="true"/>
                <br />
                <label>Year</label><input type = "number" name = "year" required="true"/>
                <br />
                <label>Vehicle Type</label><select type = "text" name = "vehicletype" required="true">
                    <% for (VehicleType vehicleType : vehicleTypeList) { %>
                            <option value="<%= vehicleType.getId()%>"><%=  vehicleType.getName()%></option>
                    <%}%>
                    </select>
                <br />
                <label>Driver</label><select type = "text" name = "driver" required="true">
                    <% for (Driver driver : driverList) { %>
                            <option value="<%= driver.getId()%>"><%=driver.getName()%></option>
                    <%}%>
                    </select>
                <br />
                <label>Branch</label><select type = "text" name = "branch" required="true">
                    <% for (Branch branch : branchList) { %>
                            <option value="<%= branch.getId()%>"><%=  branch.getName()%></option>
                    <%}%>
                    </select>
                <br />
                <input type = "submit" name ="newvehicle" value = "Create Vehicle" />
            </form>
          </div>
         <%
              VehicleWebService_Service vehicleService = new VehicleWebService_Service();
             
             String newvehicle = request.getParameter("newvehicle");
             if(newvehicle != null) {
                String make = request.getParameter("make");
                String model = request.getParameter("model");
                String year = request.getParameter("year");
                String vehicletype = request.getParameter("vehicletype");
                String driver = request.getParameter("driver");
                String branch = request.getParameter("branch");
                
                if(make == null || model == null || year == null || vehicletype == null || driver == null || branch == null){
                  %> <h3>Make, Model, Year, Vehicle Type, Driver and Branch can't be empty!</h3> <%
                  return;
                }

                Vehicle vehicle = new Vehicle();
                vehicle.setMake(make);
                vehicle.setModel(model);
                vehicle.setYear(year);
                vehicle.setVehicleTypeId(Integer.parseInt(vehicletype));
                vehicle.setDriverId(Integer.parseInt(driver));
                vehicle.setBranchId(Integer.parseInt(branch));

                try {
                    vehicle = vehicleService.getVehicleWebServicePort().addVehicle(vehicle);
                    %> <h4>Vehicle <%= vehicle.getMake() + " " + vehicle.getModel()%> successfully created!</h4> <%
                } catch (Exception e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                     }
                }
             }
            List<Vehicle> vehicleList = null;
            try {
                vehicleList = vehicleService.getVehicleWebServicePort().getVehicles();
            } catch (Exception ex) {
                %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
            }
            if(vehicleList != null && vehicleList.size() > 0){
                %>
               <div class="left">
                <h2>Vehicle List count <%=vehicleList.size()%></h2>
                <br />
                <table border="2">
                   <tr>
                        <th>Make</th>
                        <th>Model</th>
                        <th>Year</th>
                        <th>Vehicle Type</th>
                        <th>Driver</th>
                        <th>Branch</th>
                        <th>Update</th>
                        <th>Delete</th>
                   </tr>
                   <%
                for (Vehicle vehicle : vehicleList) {
                %>
                    <tr>
                        <td><%=vehicle.getMake()%></td>
                        <td><%=vehicle.getModel()%></td>
                        <td><%=vehicle.getYear()%></td>
                        <td><%=vTypeIdNameMap.get(vehicle.getVehicleTypeId())%></td>
                        <td><%=driverIdNameMap.get(vehicle.getDriverId())%></td>
                        <td><%=branchIdNameMap.get(vehicle.getBranchId())%></td>
                        <td><a href="vehicle.jsp?editId=<%=vehicle.getId()%>">Update</a></td>
                        <td><a href="vehicle.jsp?deleteId=<%=vehicle.getId()%>">Delete</a></td>
                    </tr>
           <%}%>
                             </table>
            </div>
            <%}
  
            String deleteId = request.getParameter("deleteId");
            if(deleteId != null) {
                int id = Integer.parseInt(deleteId);
                try {
                    vehicleService.getVehicleWebServicePort().deleteVehicle(id);
                    %> <h4>Vehicle successfully deleted!</h4> <%
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
                Vehicle editVehicle = null;
                try {
                    editVehicle = vehicleService.getVehicleWebServicePort().getVehicle(id);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editVehicle == null){
                    %> <h3>Error: No vehicle with the selected id!</h3> <%
                    return;
                } 
%>
            <div class="left">
                <h2>Update Vehicle</h2>
                <br />
                <form action = "" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editVehicle.getId()%>"/>
                    <br />
                    <label>Make</label><input type = "text" name = "make" required="true" value="<%=editVehicle.getMake()%>"/>
                    <br />
                    <label>Model</label><input type = "text" name = "model" required="true" value="<%=editVehicle.getModel()%>"/>
                    <br />
                    <label>Year</label><input type = "text" name = "year" required="true" value="<%=editVehicle.getYear()%>"/>
                    <br />
                    <label>Vehicle Type</label><select type = "text" name = "vehicletype" required="true">
                        <%for (VehicleType itemEdit : vehicleTypeList) {%>
                        <option value="<%= itemEdit.getId()%>" <%= editVehicle.getVehicleTypeId() == itemEdit.getId() ? "selected" : "" %>><%= itemEdit.getName()%></option>
                        <%}%>
                        </select>
                         <br />
                    <label>Driver</label><select type = "text" name = "driver" required="true">
                    <%for (Driver itemEdit : driverList) {%>
                    <option value="<%= itemEdit.getId()%>" <%= editVehicle.getDriverId()== itemEdit.getId() ? "selected" : "" %>><%= itemEdit.getName()%></option>
                    <%}%>
                    </select>
                     <br />
                    <label>Branch</label><select type = "text" name = "branch" required="true">
                    <%for (Branch itemEdit : branchList) {%>
                    <option value="<%= itemEdit.getId()%>" <%= editVehicle.getBranchId()== itemEdit.getId() ? "selected" : "" %>><%= itemEdit.getName()%></option>
                    <%}%>
                    </select>
                <br />
                <input type = "submit" name ="editvehicle" value = "Update Vehicle" />
                </form>
            </div>
<%
            }
            %>
            <%
                String editvehicle = request.getParameter("editvehicle");
                if(editvehicle != null) {
                    String id = request.getParameter("id");
                    String make = request.getParameter("make");
                    String model = request.getParameter("model");
                    String year = request.getParameter("year");
                    String vehicletype = request.getParameter("vehicletype");
                    String driver = request.getParameter("driver");
                    String branch = request.getParameter("branch");

                    if(make == null || model == null || year == null || vehicletype == null || driver == null || branch == null){
                      %> <h3>Make, Model, Year, Vehicle Type, Driver and Branch can't be empty!</h3> <%
                      return;
                    }

                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(Integer.parseInt(id));
                    vehicle.setMake(make);
                    vehicle.setModel(model);
                    vehicle.setYear(year);
                    vehicle.setVehicleTypeId(Integer.parseInt(vehicletype));
                    vehicle.setDriverId(Integer.parseInt(driver));
                    vehicle.setBranchId(Integer.parseInt(branch));

                    try {
                        vehicle = vehicleService.getVehicleWebServicePort().updateVehicle(vehicle);
                        %> <h4>Vehicle <%= vehicle.getMake() + " " + vehicle.getModel()%> successfully updated!</h4> <%
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
