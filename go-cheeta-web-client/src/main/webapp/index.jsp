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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoCheeta Taxi</title>
        <link rel="stylesheet" href="main.css"/>
    </head>
    <body>
        <h1>Lets book a taxi and go around!</h1>
        <h2>Please sign in</h2>
           <br />
           
        <form action = "index.jsp" method = "POST">
            Username <input type = "text" name = "username" required="true"/>
            <br />
            Password <input type = "password" name = "password" required="true"/>
            <input type = "submit" name ="signin" value = "Sign in" />
        </form>
           
           <h2>Or simply create an account</h2>
           <br />
           
        <form action = "index.jsp" method = "POST">
            Name: <input type = "text" name = "name" required="true">
            <br />
            Telephone: <input type = "text" name = "telephone" required="true" />
            <br />
            Email <input type = "text" name = "email" required="true"/>
            <br />
            Username <input type = "text" name = "username" required="true"/>
            <br />
            Password <input type = "password" name = "password" required="true"/>
            <input type = "submit" name ="join" value = "Join in me" />
        </form>
            <%
            final String SESSESION_ID = "sessionId";     
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

                Customer customer = customerService.getCustomerWebServicePort().geteCustomer(login.getReferenceId());
                Admin admin = adminWebService.getAdminWebServicePort().geteAdmin(login.getReferenceId());
                if(customer == null && admin == null){
                    %> <h3>Error: User information missing. Please contact admin!</h3> <%
                } else {
                    String id = UUID.randomUUID().toString().replace("-", "");
                    Cookie cookieSession = new Cookie(SESSESION_ID, id);
                    cookieSession.setMaxAge(5 * 60 * 60);
                    response.addCookie(cookieSession);

                    if(customer != null){
                        session.setAttribute(id, customer);
                        response.sendRedirect("home.jsp");
                    } else if(admin != null) {
                        session.setAttribute(id, admin);
                        response.sendRedirect("adminhome.jsp");
                    }
                }
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
                newLogin.setRole("CUSTOMER");
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
                response.addCookie(cookieSession);
                response.sendRedirect("home.jsp");
            }
    %>
    </body>
</html>

