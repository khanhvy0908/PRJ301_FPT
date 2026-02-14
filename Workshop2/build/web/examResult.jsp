<%-- 
    Document   : examResult
    Created on : 4 Jul 2025, 04:46:13
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Exam Completed!</h2>
        <p>Total Questions: ${total}</p>
        <p>Correct Answers: ${correct}</p>
        <p>Your Score: <strong>${score}%</strong></p>
        <a href="welcome.jsp">Back to Dashboard</a>
    </body>
</html>
 