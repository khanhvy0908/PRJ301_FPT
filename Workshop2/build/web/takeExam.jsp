<%-- 
    Document   : takeExam
    Created on : 4 Jul 2025, 04:12:11
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
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <c:redirect url="MainController"/>
            </c:when>
            <c:otherwise>
                <h1>Take Exam ${requestScope.examTitle}</h1>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="submitExam" />
                    <input type="hidden" name="examId" value="${examId}" />
                    <input type="hidden" name="examTitle" value="${exam.examTitle}" />

                    <c:forEach var="q" items="${requestScope.questionList}">
                        <p>${q.questionText}</p>
                        <input type="radio" name="answer_${q.questionId}" value="A" required/> ${q.optionA}<br/>
                        <input type="radio" name="answer_${q.questionId}" value="B"/> ${q.optionB}<br/>
                        <input type="radio" name="answer_${q.questionId}" value="C"/> ${q.optionC}<br/>
                        <input type="radio" name="answer_${q.questionId}" value="D"/> ${q.optionD}<br/>
                        <hr/>
                    </c:forEach>

                        <input type="submit" value="Submit Exam" />
                </form>
            </c:otherwise>
        </c:choose>

    </body>
</html>
