<%-- 
    Document   : addQuestion
    Created on : 3 Jul 2025, 23:08:45
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Add New Question</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${sessionScope.user.role ne 'Instructor'}">
                <c:redirect url="welcome.jsp"/>
            </c:when>
            <c:otherwise>

                <h1>Add New Question to Exam</h1>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="addQuestion" />

                    <div>
                        <label for="questionId">Question ID*</label>
                        <input type="text" name="questionId" id="questionId" required="required"
                               value="${not empty requestScope.question? requestScope.question.getQuestionId():""}" />
                        <span style="color: red">${not empty requestScope.questionIdError? requestScope.questionIdError: ""}</span>
                    </div>

                    <div>
                        <label for="examId">Select Exam*</label>
                        <select name="examId" id="examId" required>
                            <option value="" selected>-- Select Exam --</option>
                            <c:forEach var="exam" items="${requestScope.listExamForQuestionAdding}">
                                <option value="${exam.examId}"
                                        <c:if test="${not empty requestScope.question && requestScope.question.getExamId() == exam.examId}">
                                            selected
                                        </c:if>>
                                    ${exam.examTitle}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div>
                        <label for="questionText">Question Text*</label><br/>
                        <textarea name="questionText" id="questionText" rows="4" cols="60" required> ${requestScope.question != null ? requestScope.question.questionText : ''}</textarea>

                    </div>

                    <div>
                        <label for="optionA">Option A*</label>
                        <input type="text" name="optionA" id="optionA" required 
                               value="${not empty requestScope.question ? requestScope.question.optionA : ""}"/>
                    </div>
                    <div>
                        <label for="optionB">Option B*</label>
                        <input type="text" name="optionB" id="optionB" required 
                               value="${not empty requestScope.question ? requestScope.question.optionB : ""}"/>
                    </div>
                    <div>
                        <label for="optionC">Option C*</label>
                        <input type="text" name="optionC" id="optionC" required 
                               value="${not empty requestScope.question ? requestScope.question.optionC : ""}"/>
                    </div>
                    <div>
                        <label for="optionD">Option D*</label>
                        <input type="text" name="optionD" id="optionD" required 
                               value="${not empty requestScope.question ? requestScope.question.optionD : ""}"/>
                    </div>

                    <div>
                        <label for="correctOption">Correct Option*</label>
                        <select name="correctOption" id="correctOption" required>
                            <option value="">-- Choose --</option>

                            <option value="A"
                                    <c:if test="${requestScope.question != null && requestScope.question.correctOption == 'A'.charAt(0)}">selected</c:if>>
                                        A
                                    </option>

                                    <option value="B"
                                    <c:if test="${requestScope.question != null && requestScope.question.correctOption == 'B'.charAt(0)}">selected</c:if>>
                                        B
                                    </option>

                                    <option value="C"
                                    <c:if test="${requestScope.question != null && requestScope.question.correctOption == 'C'.charAt(0)}">selected</c:if>>
                                        C
                                    </option>

                                    <option value="D"
                                    <c:if test="${requestScope.question != null && requestScope.question.correctOption == 'D'.charAt(0)}">selected</c:if>>
                                        D
                                    </option>
                            </select>

                        </div>

                        <div>
                            <input type="submit" value="Add Question" />
                            <input type="reset" value="Reset" />
                        </div>

                        <div>
                        <c:if test="${not empty requestScope.checkError}">
                            <span style="color: red">${requestScope.checkError}</span>
                        </c:if>
                        <c:if test="${not empty requestScope.message}">
                            <span style="color: green">${requestScope.message}</span>
                        </c:if>
                        <a href="MainController?action=listQuestion">Back to All Questions</a>
                    </div>
                </form>

            </c:otherwise>
        </c:choose>
    </body>
</html>
