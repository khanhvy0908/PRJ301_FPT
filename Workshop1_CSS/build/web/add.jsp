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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card shadow p-4">
                        <h1 class="mb-4 text-center">Create New Projects Here!</h1>
                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="addProject" />
                            <div class="mb-3">
                                <label for="id" class="form-label">ID*</label>
                                <input type="text" class="form-control" name="id" id="id" required value="${not empty requestScope.project?requestScope.project.getProjectId():""}"/>
                                <span class="text-danger small">${requestScope.idError}</span>
                            </div>
                            <div class="mb-3">
                                <label for="name" class="form-label">Name*</label>
                                <input type="text" class="form-control" name="name" id="name" required value="${not empty requestScope.project?requestScope.project.getProjectName():""}"/>
                                <span class="text-danger small">${requestScope.nameError}</span>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" name="description" id="description">${not empty requestScope.project?requestScope.project.getDescription():""}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Project Status*</label>
                                <select class="form-select" name="status" id="status" required>
                                    <option value="">-- Select Status --</option>
                                    <option value="Ideation">Ideation</option>
                                    <option value="Development">Development</option>
                                    <option value="Launch">Launch</option>
                                    <option value="Scaling">Scaling</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="launch" class="form-label">Estimated Launch Date</label>
                                <input type="date" class="form-control" name="launch" id="launch" required min="<%= java.time.LocalDate.now() %>" />
                            </div>
                            <div class="d-flex gap-2 mb-3">
                                <input type="submit" class="btn btn-success" value="Add Product"/>
                                <input type="reset" class="btn btn-secondary" value="Reset"/>
                            </div>
                            <a href="MainController?action=listProjects" class="btn btn-link">Back to projects</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
