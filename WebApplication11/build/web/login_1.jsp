<%-- 
    Document   : login
    Created on : 23 May 2025, 07:23:14
    Author     : Admin
--%>

<%taglib uri="http://java.sun.com/jsp/j"

<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
           if(AuthUtils.isLoggedIn(request)) {
           response.sendRedirect("welcome.jsp");
            }else{
         %>
        <h1>Login</h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="login"/>
            Username: <input type="text" name="strUsername"/> <br/><!-- comment -->
            Password: <input type="password" name="strPassword"/><br/>
            <input type="submit" value="Login"/>
        </form>
        
        <% 
            Object objMessage = request.getAttribute("message");// Attribute la kieu du lieu Object
            String message = objMessage==null?"":(objMessage+"");// Object + "" ==> string
        %>
        <span style="color: red"><%=message%></span>
    <%
        } 
     %>
    </body>
</html>
