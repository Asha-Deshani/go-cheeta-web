<%-- 
    Document   : branch
    Created on : 11/09/2022, 7:48:14 PM
    Author     : asha
--%>

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
        <div>
         <div class="left">
            <h2>Create a Branch</h2>
            <br />
            <form action = "branch.jsp" method = "POST">
                Name <input type = "text" name = "name" required="true"/>
                <br />
                City <input type = "text" name = "city" required="true"/>
                <br />
                <input type = "submit" name ="newbranch" value = "Create Branch" />
            </form>
          </div>
         <%
             BranchWebService_Service branchService = new BranchWebService_Service();
             
             String newbranch = request.getParameter("newbranch");
             if(newbranch != null) {
                String name = request.getParameter("name");
                String city = request.getParameter("city");
                
                if(name == null || city == null){
                  %> <h3>Name and City can't be empty!</h3> <%
                  return;
                }

                Branch branch = new Branch();
                branch.setName(name);
                branch.setCity(city);

                try {
                    branch = branchService.getBranchWebServicePort().addBranch(branch);
                    %> <h4>Branch <%= branch.getName() %> successfully created!</h4> <%
                } catch (Exception e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                     }
                }
             }
            List<Branch> branchList = null;
            try {
                branchList = branchService.getBranchWebServicePort().getBranches();
            } catch (Exception ex) {
                %> <h3>Error: <%=ex.getMessage()%>!</h3> <%
            }
            if(branchList != null && branchList.size() > 0){
                %>
               <div class="left">
                <h2>Branch List count <%=branchList.size()%></h2>
                <br />
                <table border="2">
                   <tr>
                        <td>Name</td>
                        <td>City</td>
                        <td>Update</td>
                        <td>Delete</td>
                   </tr>
                   <%
                for (Branch branch : branchList) {
                %>
                    <tr>
                        <td><%=branch.getName()%></td>
                        <td><%=branch.getCity()%></td>
                        <td><a href="branch.jsp?editId=<%=branch.getId()%>">Update</a></td>
                        <td><a href="branch.jsp?deleteId=<%=branch.getId()%>">Delete</a></td>
                    </tr>
               <%}
            }
             
  
            String deleteId = request.getParameter("deleteId");
            if(deleteId != null) {
                int id = Integer.parseInt(deleteId);
                try {
                    branchService.getBranchWebServicePort().deleteBranch(id);
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
                Branch editBranch = null;
                try {
                    editBranch = branchService.getBranchWebServicePort().getBranch(id);
                } catch (Exception e) {
                    %> <h3>Error: <%=e.getMessage()%>!</h3> <%
                }

                if(editBranch == null){
                    %> <h3>Error: No branch with the selected id!</h3> <%
                    return;
                } 
%>
            <div class="left">
                <h2>Update Branch</h2>
                <br />
                <form action = "branch.jsp" method = "POST">
                    <input type = "hidden" name = "id" required="true" value="<%=editBranch.getId()%>"/>
                    <br />
                    Name <input type = "text" name = "name" required="true" value="<%=editBranch.getName()%>"/>
                    <br />
                    City <input type = "text" name = "city" required="true" value="<%=editBranch.getCity()%>"/>
                    <br />
                    <input type = "submit" name ="editbranch" value = "Update Branch" />
                </form>
            </div>
<%
            }
            %>
            <%
                String editbranch = request.getParameter("editbranch");
                if(editbranch != null) {
                    String name = request.getParameter("name");
                    String city = request.getParameter("city");
                    String id = request.getParameter("id");
                    
                    if(name == null || city == null || id == null){
                        %> <h3>Id, Name and City can't be empty!</h3> <%
                        return;
                    }

                    Branch branch = new Branch();
                    branch.setName(name);
                    branch.setCity(city);
                    branch.setId(Integer.parseInt(id));

                    try {
                        branch = branchService.getBranchWebServicePort().updateBranch(branch);
                        %> <h4>Branch <%= branch.getName() %> successfully updated!</h4> <%
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
