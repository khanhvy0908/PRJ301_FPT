<%-- 
    Document   : welcome
    Created on : 24 Jun 2025, 23:05:44
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- Kiem tra dang nhap --%>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <c:redirect url="login.jsp"/>
            </c:when>
            <c:otherwise>
                <h1>Welcome ${sessionScope.user.name}!</h1>
                <a href="MainController?action=logout">Logout</a><br/>
                <%--Display all projects && search project By Name --%>
                <a href="MainController?action=listProjects">Display all projects</a><br/>

                <%-- Founder action only --%>
                <c:if test="${sessionScope.user.role eq 'Founder'}">
                <%-- Search section --%>
                    <label>Search by name:</label>
                    <form action="ProjectController" method="POST">
                        <input type="hidden" name="action" value="searchProject" />
                        <input type="text" name="strKeyword" value="${not empty requestScope.keyword ? requestScope.keyword : ""}" />
                        <input type="submit" value="Search" />    
                    </form>
                <%-- add section --%>
                <a href="MainController?action=addProject">Create New Projects</a>
                
                </c:if>                
                <%-- Founder action only --%>   


                <%--Xu ly search and list table --%>
                <c:choose>
                    <c:when test="${requestScope.listSearch != null and fn:length(requestScope.listSearch) == 0}">
                        <h2>No projects found!</h2>
                    </c:when>

                    <c:when test="${not empty requestScope.listSearch and fn:length(requestScope.listSearch) > 0}">
                        <h2>Search Results</h2>
                        <jsp:include page="table.jsp">
                            <jsp:param name="listName" value="listSearch"/>
                        </jsp:include>
                    </c:when>

                    <c:when test="${empty requestScope.listSearch and not empty requestScope.listAll}">
                        <h2>All Projects</h2>
                        <jsp:include page="table.jsp">
                            <jsp:param name="listName" value="listAll"/>
                        </jsp:include>
                    </c:when>

                </c:choose>





            </c:otherwise>
        </c:choose>



    </body>
</html>
