<%-- 
    Document   : signup
    Created on : 07/09/2022, 12:12:44 AM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.LoginWebService_Service"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Join with us</title>
    </head>
    <body>
        <h1>Hello There!</h1> 
        <h2>Simply create an account, So that we can serve you better. It is simple to join with us.</h2>
        
        <form action = "home.jsp" method = "POST">
            Name: <input type = "text" name = "name">
            <br />
            Telephone: <input type = "text" name = "telephone" />
            <br />
            Email <input type = "text" name = "email" />
            <br />
            Username <input type = "text" name = "username" />
            <br />
            Password <input type = "password" name = "password" />
            <input type = "submit" value = "Submit" />
        </form>
    </body>
</html>
