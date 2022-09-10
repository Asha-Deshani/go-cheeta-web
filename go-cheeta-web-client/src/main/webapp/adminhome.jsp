<%-- 
    Document   : acountcreated.jsp
    Created on : 07/09/2022, 12:33:00 AM
    Author     : asha
--%>

<%@page import="lk.gocheeta.web.service.controller.Admin"%>
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
        <title>Welcome Admin</title>
    </head>
    <body>
        <%
            final String SESSESION_ID = "sessionId";   
            boolean isAthuenticated = false;
            Cookie[] cookieArray = request.getCookies();
            if(cookieArray == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            
            for(Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals(SESSESION_ID)) {
                    Admin sessionAdmin = (Admin)session.getAttribute(cookie.getValue());
                    if(sessionAdmin != null) {
                        isAthuenticated = true;
                        %> <h2>Welcome Admin, <%=sessionAdmin.getName()%>!</h3> <%
                    }
                }
            }
            if (!isAthuenticated) {
                response.sendRedirect("index.jsp");
                return;
            }
        %>
    </body>
 </html>
