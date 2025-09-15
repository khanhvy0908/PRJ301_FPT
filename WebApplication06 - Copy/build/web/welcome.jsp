<%-- 
    Document   : welcome
    Created on : 23 May 2025, 07:41:06
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
          UserDTO user = (UserDTO)request.getAttribute("user");  
        %>
        <h1>Welcome <%=user.getFullName()%></h1>
    </body>
</html>
