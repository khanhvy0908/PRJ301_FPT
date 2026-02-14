<%-- 
    Document   : error
    Created on : 25 Jun 2025, 10:14:14
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Oops! Something went wrong.</h1>
        <p> An unexpected error occurred. Please try again later.</p>
        <c:if test="${not empty requestScope.errorMessage}">
            <p style="color: red;">Error details: ${requestScope.errorMessage}</p>
        </c:if>
        <a href="javascript:history.back()">Go back </a>
    </body>
</html>
