<%-- 
    Document   : acountcreated.jsp
    Created on : 07/09/2022, 12:33:00 AM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Login"%>
<%@page import="lk.gocheeta.web.service.controller.Customer"%>
<%@page import="lk.gocheeta.web.service.controller.CustomerWebService_Service"%>
<%@page import="java.util.Enumeration"%>
<%@page import="lk.gocheeta.web.service.controller.LoginWebService_Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <%
            Customer customer = null;

            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            LoginWebService_Service loginService = new LoginWebService_Service();
            CustomerWebService_Service customerService = new CustomerWebService_Service();
            if (name != null) {
                String telephone = request.getParameter("telephone");
                String email = request.getParameter("email");

                customer = new Customer();
                customer.setName(name);
                customer.setEmail(email);
                customer.setTelephone(telephone);
                
                try {
                customer = customerService.getCustomerWebServicePort().addCustomer(customer);
                } catch (Exception e){
                    if(e.getMessage().contains("Duplicate entry")){
                        String error = e.getMessage().substring(0, e.getMessage().indexOf("for"));
                        %> <h2>Error: <%=error%>!</h2> <%
                        return;
                    }
                }

                Login newLogin = new Login();
                newLogin.setUsername(username);
                newLogin.setPassword(password);
                newLogin.setRole("CUSTOMER");
                newLogin.setReferenceId(customer.getId());

                loginService.getLoginWebServicePort().addLogin(newLogin);
            }

            Login login = loginService.getLoginWebServicePort().authenticate(username, password);
            if (login == null) {
                %><h1>Authentication fail. Please check username and password</h1> <%
            } else if (customer == null) {
                        customer = customerService.getCustomerWebServicePort().geteCustomer(login.getReferenceId());
                        name = customer.getName();
                %> <h1>Welcome back to GoCheeta <%=name%>!</h1> <%
            } else {
                %> <h1>welcome to GoCheeta <%=name%>!</h1> <%
            }
%>
    </body>
 </html>
