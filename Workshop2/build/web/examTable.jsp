<%-- 
    Document   : examTable
    Created on : 2 Jul 2025, 23:27:36
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
                <c:set var="exams" value="${requestScope[param.listName]}"/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Exam title</th>
                            <th>Subject</th>
                            <th>Total marks</th>
                            <th>Duration</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="exam" items="${exams}">
                            <tr>
                                <td>${exam.examTitle}</td>
                                <td>${exam.subject}</td>
                                <td>${exam.totalMarks}</td>
                                <td>${exam.duration}</td>
                                <td>
                                    <form action="MainController" method="POST">
                                        <input type="hidden" name="action" value="startExam" />
                                        <input type="hidden" name="examId" value="${exam.examId}" />
                                        <input type="hidden" name="examTitle" value="${exam.examTitle}" />
                                        <input type="submit" value="Take Exam" />
                                    </form>

                                </td>
                            </tr>
                        </c:forEach>                                
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

    </body>
</html>
