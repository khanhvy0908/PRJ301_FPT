<%-- 
    Document   : addExam
    Created on : 3 Jul 2025, 00:14:50
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
            <c:when test="${sessionScope.user.role ne 'Instructor'}">
                <c:redirect url="welcome.jsp"/>
            </c:when>
            <c:otherwise>
                <h1>Create new exams!</h1>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="addExam" />

                    <div>
                        <label for="examId">Exam ID*</label>
                        <input type="text" name="examId" id="examId" required="required" 
                               value="${not empty requestScope.exam? requestScope.exam.getExamId(): ""}" />
                        <span style="color:red">${requestScope.idError}</span>
                    </div>

                    <div>
                        <label for="examTitle">Exam Title*</label>
                        <input type="text" name="examTitle" id="examTitle" required="required" 
                               value="${not empty requestScope.exam? requestScope.exam.getExamTitle(): ""}" />
                        <span style="color:red">${requestScope.titleError}</span>
                    </div>

                    <div>
                        <label for="subject">Subject*</label>
                        <input type="text" name="subject" id="subject" required="required"
                               value="${not empty requestScope.exam? requestScope.exam.getSubject(): ""}" />
                    </div>

                    <div>
                        <label for="categoryId">Category*</label>

                        <select name="categoryId" id="categoryId" required="required">
                            <option value="" selected>-- Select Category --</option>
                            <c:forEach var="cat" items="${requestScope.listCategory}">
                                <option value="${cat.categoryId}"
                                        <c:if test="${not empty requestScope.exam && requestScope.exam.getCategoryId() == cat.categoryId}">selected</c:if>>
                                    ${cat.categoryName} 
                                </option>
                            </c:forEach>

                        </select>
                    </div>

                    <div>
                        <label for="totalMarks">Total Marks*</label>
                        <input type="number" name="totalMarks" id="totalMarks" required="required" min="10" 
                               value="${not empty requestScope.exam? requestScope.exam.getTotalMarks(): ""}" />
                    </div>

                    <div>
                        <label for="duration">Duration (minutes)*</label>
                        <input type="number" name="duration" id="duration" required="required" min="5"
                               value="${not empty requestScope.exam? requestScope.exam.getDuration(): ""}" />
                    </div>

                    <div>
                        <input type="submit" value="Add Exam" />
                        <input type="reset" value="Reset" />
                    </div>
                    <c:if test="${not empty requestScope.checkError}">
                        <span style="color: red">${requestScope.checkError}</span>
                    </c:if>
                    <c:if test="${not empty requestScope.message}">
                        <span style="color: green">${requestScope.message}</span>
                    </c:if>
                    <a href="welcome.jsp">Back to Dashboard</a>
                </form>
            </c:otherwise>
        </c:choose>

    </body>
</html>
