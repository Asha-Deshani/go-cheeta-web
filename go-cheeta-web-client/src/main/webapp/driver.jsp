<%-- 
    Document   : location
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Login"%>
<%@page import="lk.gocheeta.web.service.controller.LoginWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.DriverWebService_Service"%>
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
        <h1>Manage Drivers!</h1>
        <div class="containercolumn">
         <div class="left">
            <h2>Create a Driver</h2>
            <br />
            <%
                 BranchWebService_Service branchService = new BranchWebService_Service();
                 List<Branch> branchList = branchService.getBranchWebServicePort().getBranches(); 
                 
                 Map<Integer, String> branchIdNameMap = new HashMap();
                 for(Branch branchItem : branchList){
                    branchIdNameMap.put(branchItem.getId(), branchItem.getName());
                 }
            %>
            <form action = "" method = "POST">
                <label>Name</label><input type = "text" name = "name" required="true"/>
                <br />
                 <label>Telephone</label><input type = "text" name = "telephone" required="true"/>
                <br />
                 <label>Email</label><input type = "text" name = "email" required="false"/>
                <br />
                <label>Branch</label><select type = "text" name = "branch" required="true">
                    <% for (int i = 0; i < branchList.size(); i++) { %>
                            <option value="<%= branchList.get(i).getId()%>"><%=  branchList.get(i).getName()%></option>
                    <%}%>
                    </select>
                <br />
                <label>Username</label> <input type = "text" name = "username" required="true"/>
                <br />
                <label>Password</label> <input type = "password" name = "password" required="true"/>
                <br />
                <input type = "submit" name ="newdriver" value = "Create Driver" />
            </form>
          </div>
         <%
             DriverWebService_Service  driverService = new DriverWebService_Service();
             LoginWebService_Service loginService = new LoginWebService_Service();
             
             String newdriver = request.getParameter("newdriver");
             if(newdriver != null) {
                String name = request.getParameter("name");
                String telephone = request.getParameter("telephone");
                String email = request.getParameter("email");
                String branchId = request.getParameter("branch");
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
                if(name == null || telephone == null || branchId == null ||
                    username == null || password == null){
                  %> <h3>Name, Telephone, Username, password and Branch can't be empty!</h3> <%
                  return;
                }

                Driver driver = new Driver();
                driver.setName(name);
                driver.setTelephone(telephone);
                driver.setEmail(email);
                driver.setBranchId(Integer.parseInt(branchId));

                try {
                    driver = driverService.getDriverWebServicePort().addDriver(driver);
                
                    Login newLogin = new Login();
                    newLogin.setUsername(username);
                    newLogin.setPassword(password);
                    newLogin.setRole(ROLE_DRIVER);
                    newLogin.setReferenceId(driver.getId());

                    try {
                      newLogin = loginService.getLoginWebServicePort().addLogin(newLogin);
                    } catch (Exception e) {
                      driverService.getDriverWebServicePort().deleteDriver(driver.getId());
                        if (e.getMessage().contains("Duplicate entry")) {
                            String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                            %> <h3>Error: <%=error%>!</h3> <%
                             return;
                         }
                    }
                    %> <h4>Location <%= driver.getName()%> successfully created!</h4> <%
                } catch (Exception e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                     }
                }
             }
            List<Driver> driverList = null;
            try {
                driverList = driverService.getDriverWebServicePort().getDrivers();
            } catch (Exception ex) {
                %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
            }
            if(driverList != null && driverList.size() > 0){
                %>
               <div class="left">
                <h2>Driver List count <%=driverList.size()%></h2>
                <br />
                <table border="2">
                   <tr>
                        <th>Name</th>
                        <th>Telephone</th>
                        <th>Email</th>
                        <th>Branch</th>
                        <th>Update</th>
                        <th>Delete</th>
                   </tr>
                   <%
                for (Driver driver : driverList) {
                %>
                    <tr>
                        <td><%=driver.getName()%></td>
                        <td><%=driver.getTelephone()%></td>
                        <td><%=driver.getEmail()%></td>
                        <td><%=branchIdNameMap.get(driver.getBranchId())%></td>
                        <td><a href="driver.jsp?editId=<%=driver.getId()%>">Update</a></td>
                        <td><a href="driver.jsp?deleteId=<%=driver.getId()%>">Delete</a></td>
                    </tr>
           <%}%>
                             </table>
            </div>
            <%}
  
            String deleteId = request.getParameter("deleteId");
            if(deleteId != null) {
                int id = Integer.parseInt(deleteId);
                try {
                    driverService.getDriverWebServicePort().deleteDriver(id);
                    %> <h4>Driver successfully deleted!</h4> <%
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
                Driver editDriver = null;
                try {
                    editDriver = driverService.getDriverWebServicePort().getDriver(id);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editDriver == null){
                    %> <h3>Error: No Driver with the selected id!</h3> <%
                    return;
                } 
%>
            <div class="left">
                <h2>Update Driver</h2>
                <br />
                <form action = "" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editDriver.getId()%>"/>
                    <br />
                    <label>Name</label><input type = "text" name = "name" required="true" value="<%=editDriver.getName()%>"/>
                    <br />
                     <label>Telephone</label><input type = "text" name = "telephone" required="true" value="<%=editDriver.getTelephone()%>"/>
                    <br />
                     <label>Email</label><input type = "text" name = "email" required="false" value="<%=editDriver.getEmail()%>"/>
                    <br />
                    <label>Branch</label><select type = "text" name = "branch" required="true">
                        <%for (Branch itemEdit : branchList) {%>
                        <option value="<%= itemEdit.getId()%>" <%= editDriver.getBranchId()== itemEdit.getId() ? "selected" : "" %>><%= itemEdit.getName()%></option>
                        <%}%>
                        </select>
                <br />
                <input type = "submit" name ="editdriver" value = "Update Driver" />
                </form>
            </div>
<%
            }
            %>
            <%
                String editdriver = request.getParameter("editdriver");
                if(editdriver != null) {
                    String name = request.getParameter("name");
                    String telephone = request.getParameter("telephone");
                    String email = request.getParameter("email");
                    String branch = request.getParameter("branch");
                    String idEdit = request.getParameter("id");
                    
                    if(name == null || telephone == null || idEdit == null){
                        %> <h3>Id, name, telephone and branch can't be empty!</h3> <%
                        return;
                    }

                    Driver driver = new Driver();
                    driver.setName(name);
                    driver.setTelephone(telephone);
                    driver.setEmail(email);
                    driver.setBranchId(Integer.parseInt(branch));
                    driver.setId(Integer.parseInt(idEdit));

                    try {
                        driver = driverService.getDriverWebServicePort().updateDriver(driver);
                        %> <h4>Driver <%= driver.getName()%> successfully updated!</h4> <%
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
