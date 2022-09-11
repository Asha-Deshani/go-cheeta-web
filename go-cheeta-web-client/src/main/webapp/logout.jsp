<%-- 
    Document   : logout
    Created on : 11/09/2022, 7:02:46 PM
    Author     : asha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="masterpage.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1><font color="Blue">You are Sucessfully logged out...</font></h1>
        <a href="index.jsp">Go-Back To Home Page</a>
        <%
            session.invalidate();
        %>
    </body>
</html>
