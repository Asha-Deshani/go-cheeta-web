<%-- 
    Document   : index.jsp
    Created on : 06/09/2022, 11:40:55 PM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Admin"%>
<%@page import="lk.gocheeta.web.service.controller.AdminWebService_Service"%>
<%@page import="java.util.UUID"%>
<%@page import="lk.gocheeta.web.service.controller.Login"%>
<%@page import="lk.gocheeta.web.service.controller.CustomerWebService_Service"%>
<%@page import="lk.gocheeta.web.service.controller.Customer"%>
<%@page import="lk.gocheeta.web.service.controller.LoginWebService_Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="masterpage.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
         <div class="containerrow">
                <div class="containerrow">
                     <div class="left">
                         <h2>Please sign in</h2>
                         <br />
                         <form action = "index.jsp" method = "POST">
                              <label>Username</label> <input type = "text" name = "username" required="true"/>
                             <br />
                              <label>Password</label> <input type = "password" name = "password" required="true"/>
                             <br />
                             <input type = "submit" name ="signin" value = "Sign in" />
                         </form>
                     </div>
                    <div class="right">
                            <h2>Or simply create an account</h2>
                            <br />
                         <form action = "index.jsp" method = "POST">
                             <label>Name</label> <input type = "text" name = "name" required="true">
                             <br />
                             <label>Telephone</label> <input type = "text" name = "telephone" required="true" />
                             <br />
                             <label>Email</label> <input type = "text" name = "email" required="true"/>
                             <br />
                             <label>Username</label> <input type = "text" name = "username" required="true"/>
                             <br />
                             <label>Password</label> <input type = "password" name = "password" required="true"/>
                             <br />
                             <input type = "submit" name ="join" value = "Join in me" />
                         </form>
                     </div>
                </div>
              </div>
            <%
            final String SESSESION_ID = "sessionId";     
            final String ROLE_CUSTOMER = "CUSTOMER";     
            final String ROLE_ADMIN = "ADMIN";
            final String ROLE = "ROLE";
            
            LoginWebService_Service loginService = new LoginWebService_Service();
            CustomerWebService_Service customerService = new CustomerWebService_Service();
            AdminWebService_Service adminWebService = new AdminWebService_Service();
            
            String signin = request.getParameter("signin");
            String join = request.getParameter("join");
                
            if(signin != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
                if(username == null || password == null){
                  %> <h3>Username and Password can't be empty!</h3> <%
                  return;
                }

                Login login = loginService.getLoginWebServicePort().authenticate(username, password);
                if (login == null) {
                    %><h3>Authentication fail. Please check username and password</h3> <%
                    return;
                }

                Customer customer = null;
                Admin admin = null;
                if(login.getRole().equals(ROLE_CUSTOMER)){
                    customer = customerService.getCustomerWebServicePort().geteCustomer(login.getReferenceId());
                    if(customer == null){
                        %> <h3>Error: User information missing. Please contact admin!</h3> <%
                        return;
                    }
                } else if(login.getRole().equals(ROLE_ADMIN)){
                    admin = adminWebService.getAdminWebServicePort().geteAdmin(login.getReferenceId());
                    if(admin == null){
                        %> <h3>Error: Issue with Admin account. Please check Database!</h3> <%
                        return;
                    }
                }
        
                String id = UUID.randomUUID().toString().replace("-", "");
                Cookie cookieSession = new Cookie(SESSESION_ID, id);
                cookieSession.setMaxAge(5 * 60 * 60);
                response.addCookie(cookieSession);

                if(customer != null){
                    session.setAttribute(id, customer);
                    session.setAttribute(id + ROLE, ROLE_CUSTOMER);
                    response.sendRedirect("home.jsp");
                } else if(admin != null) {
                    session.setAttribute(id, admin);
                    session.setAttribute(id + ROLE, ROLE_CUSTOMER);
                    response.sendRedirect("adminhome.jsp");
                }
                return;
            } else if(join != null) {
                String name = request.getParameter("name");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String telephone = request.getParameter("telephone");
                String email = request.getParameter("email");

                Customer customerNew = new Customer();
                customerNew.setName(name);
                customerNew.setEmail(email);
                customerNew.setTelephone(telephone);
    
                try {
                    customerNew = customerService.getCustomerWebServicePort().addCustomer(customerNew);
                } catch (Exception e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                         return;
                     }
                }

                Login newLogin = new Login();
                newLogin.setUsername(username);
                newLogin.setPassword(password);
                newLogin.setRole(ROLE_CUSTOMER);
                newLogin.setReferenceId(customerNew.getId());
                
                try {
                  newLogin = loginService.getLoginWebServicePort().addLogin(newLogin);
                } catch (Exception e) {
                  customerService.getCustomerWebServicePort().deleteCustomer(customerNew.getId());
                    if (e.getMessage().contains("Duplicate entry")) {
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h3>Error: <%=error%>!</h3> <%
                         return;
                     }
                }

                String id = UUID.randomUUID().toString().replace("-", "");
                Cookie cookieSession = new Cookie(SESSESION_ID, id);
                cookieSession.setMaxAge(5 * 60 * 60);
                session.setAttribute(id, customerNew);
                session.setAttribute(id + ROLE, ROLE_CUSTOMER);
                response.addCookie(cookieSession);
                response.sendRedirect("home.jsp");
            }
    %>
    </body>
</html>

