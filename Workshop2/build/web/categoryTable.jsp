<%-- 
    Document   : categoryTable
    Created on : 2 Jul 2025, 23:06:50
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%--Kiem tra dang nhap --%>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <c:redirect url="MainController"/>
            </c:when>
            <c:otherwise>
                <c:set var="categories" value="${requestScope[param.listName]}"/>
                <table border="1">
                    <thead>
                        <tr>
                            
                            <th>Category Name</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                
                                <td>${category.categoryName}</td>
                                <td>${category.description}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>


    </body>
</html>
