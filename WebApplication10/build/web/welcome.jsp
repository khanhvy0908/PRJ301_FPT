<%-- 
    Document   : welcome
    Created on : 23 May 2025, 07:41:06
    Author     : Admin
--%>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="model.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            if (!AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("MainController");
            } else {
                String keyword = (String) request.getAttribute("keyword");
        %>
        <h1>Welcome <%=user.getFullName()%> !</h1>
        <a href="MainController?action=logout">Logout</a>
        <br/>

        <%--
        Search action
        --%>        
        <br/><label>Search by name</label>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="searchProduct" />
            <input type="text" name="strKeyword" value="<%=keyword != null ? keyword : ""%>" />
            <input type="submit" value="Search" />
        </form>
        <%--
     Add new product link
        --%>
        <%
            if (AuthUtils.isAdmin(request)) {
        %>
        <a href="productForm.jsp">Add new Product</a>
        <%
            }
        %>
        <%---
        lay object duoc gui tu servlet(list) khi user search
        --%>
        <%
            List<ProductDTO> list = (List<ProductDTO>) request.getAttribute("list");
            if (list != null && list.isEmpty()) {
        %>
        No products have names that match the keyword!
        <%
        } else if (list != null && !list.isEmpty()) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Size</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (ProductDTO p : list) {
                %>
                <tr>
                    <td><%=p.getId()%></td>
                    <td><%=p.getName()%></td>
                    <td><%=p.getDescription()%></td>
                    <td><%=p.getPrice()%></td>
                    <td><%=p.getSize()%></td>
                    <td><%=p.isStatus()%></td>
                    <td>
                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="editProduct" />
                            <input type="hidden" name="productId" value="<%=p.getId()%>" />
                            <input type = "hidden" name = "strKeyword" value ="<%=keyword!=null?keyword:""%>" />
                            <input type="submit" name="edit" value="Edit" />
                        </form>
                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="changeProductStatus" />
                            <input type="hidden" name="productId" value="<%=p.getId()%>" /> 
                            <input type="hidden" name="strKeyword" value="<%=keyword != null ? keyword : ""%>" />
                            <input type="submit" name="delete" value="Delete"
                                   onclick="return confirm('Are you sure to delete this product?')"/>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <%
            }
        %>
        <%
            }
        %>

    </body>
</html>
