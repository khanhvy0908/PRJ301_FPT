<%-- 
    Document   : add
    Created on : 26 Jun 2025, 10:08:47
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
        <h1>Create New Projects Here!</h1>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="addProject" />

            <div>
                <label for="id">ID*</label>
                <input type="text" name="id" id="id" required="required" value="${not empty requestScope.project?requestScope.project.getProjectId():""}"/>
                <span style="color:red">${requestScope.idError}</span>
            </div>

            <div>
                <label for="name">Name*</label>
                <input type="text" name="name" id="name" required="required" value="${not empty requestScope.project?requestScope.project.getProjectName():""}"/>
                <span style="color:red">${requestScope.nameError}</span>
            </div>

            <div>
                <label for="description">Description</label> 
                <textarea name="description" id="description">${not empty requestScope.project?requestScope.project.getDescription():""}</textarea>
            </div>

            <div>
                <label for="status">Project Status*</label> 
                <select name="status" id="status" required="">
                    <option value="">-- Select Status --</option>
                    <option value="Ideation">Ideation</option>
                    <option value="Development">Development</option>
                    <option value="Launch">Launch</option>
                    <option value="Scaling">Scaling</option>
                </select>
            </div>

            <div>
                <label for="launch">Estimated Launch Date</label>
                <input type="date" name="launch" id="launch" required="required" min="<%= java.time.LocalDate.now() %>" />
            </div>

            <div>
                <input type="submit" value="Add Product"/>
                <input type="reset" value="Reset"/>
               
            </div>
            <a href="MainController?action=listProjects">Back to projects</a>
        </form>


    </body>
</html>
