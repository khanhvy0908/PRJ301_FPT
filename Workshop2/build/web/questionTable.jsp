<%-- 
    Document   : questionTable
    Created on : 3 Jul 2025, 14:15:43
    Author     : Admin
--%>

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
            <c:when test="${sessionScope.user.role ne 'Instructor'}">
                <c:redirect url="MainController"/>
            </c:when>
            <c:otherwise>
                <c:set var="questions" value="${requestScope[param.listName]}"/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Question ID</th>
                            <th>Exam ID</th>
                            <th>Question Text</th>
                            <th>Option A</th>
                            <th>Option B</th>
                            <th>Option C</th>
                            <th>Option D</th>
                            <th>Correct Option</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="question" items="${questions}">
                            <tr>
                                <td>${question.questionId}</td>
                                <td>${question.examId}</td>
                                <td>${question.questionText}</td>
                                <td>${question.optionA}</td>
                                <td>${question.optionB}</td>
                                <td>${question.optionC}</td> 
                                <td>${question.optionD}</td>
                                <td>${question.correctOption}</td>
                            </tr>
                        </c:forEach>               
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
