<%-- 
    Document   : productForm
    Created on : 6 Jun 2025, 07:44:55
    Author     : Admin
--%>
<%@page import="model.ProductDTO"%>
<%@page import="model.ProductDAO"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Form</title>
    </head>
    <body>

        <%
            if (AuthUtils.isAdmin(request)) {
                // là cách bạn lấy dữ liệu từ request
                //object trong JSP mà Servlet đã truyền sang
                ProductDTO product = (ProductDTO) request.getAttribute("product");
                String checkError = (String) request.getAttribute("checkError");
                String message = (String) request.getAttribute("message");
                String keyword = (String) request.getAttribute("keyword");
                boolean isEdit = request.getAttribute("isEdit") != null;
        %>
        <%-- Buoi 10: remake for 2 option(edit and add new) --%>
        <h1><%=isEdit ? "Edit Product" : "Add new Product"%></h1>  <br/>

        <% if (keyword != null && !keyword.isEmpty()) {%>
        <a href="ProductController?action=searchProduct&strKeyword=<%=keyword%>">Back to Product List</a>

        <% } else {%>
        <a href="welcome.jsp">Back to Dashboard</a>
        <%
            }
        %>
        <%--
            Buoi 10: remake for 2 option(edit and add new)
            Form for 2 distinct action2(edit or add)
        --%>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="<%=isEdit ? "updateProduct" : "addProduct"%>" />

            <%
                if (keyword != null) {%>
            <input type="hidden" name="strKeyword" value="<%=keyword%>"/>
            <%
                }
            %>

            <div>
                <label for="id">ID*</label>
                <input type="text" name="id" id="id" required="required"
                       value="<%=product != null ? product.getId() : ""%>"
                       />
                <%
                    String idError = (String) request.getAttribute("idError");
                    if (idError != null) {
                %>
                <div style="color: red"><%=idError%></div>
                <%
                    }
                %>
            </div>
            <div>
                <label for="name">Name*</label>
                <input type="text" name="name" id="name" required="required"
                       value="<%=product != null ? product.getName() : ""%>"
                       />
            </div>
            <div>
                <label for="image">Image</label>
                <input type="text" name="image" id="image"
                       value="<%=product != null ? product.getImage() : ""%>"
                       />
            </div>
            <div>
                <label for="description">Description</label> 
                <textarea type="text" name="description" id="description">
                    <%=product != null ? product.getDescription() : ""%>
                </textarea>
            </div>
            <div>
                <label for="price">Price*</label> 
                <input type="number" name="price" id="price" min="0" step="0.01" required="required"
                       value="<%=product != null ? product.getPrice() : ""%>"
                       />
                <%
                    String priceError = (String) request.getAttribute("priceError");
                    if (priceError != null) {
                %>
                <div style="color: red"><%=priceError%></div>
                <%
                    }
                %>
            </div>
            <div>
                <label for="size">Size</label> 
                <input type="text" name="size" id="size"
                       value="<%=product != null ? product.getSize() : ""%>"
                       />
            </div>
            <div>
                <label for="status">Status (Active Product)</label> 
                <input type="checkbox" name="status" id="status" value="true"
                       <%=product != null && product.isStatus() ? "checked='checked'" : ""%>
                       />
            </div>
            <div>
                <input type="submit" value="<%=isEdit ? "Update Product" : "Add Product"%>"/>
            </div>
        </form>

        <% if (checkError != null || message != null) { %>
        <div class="message-container">
            <% if (checkError != null) {%>
            <div class="error-message"><%=checkError%></div>
            <% } %>
            <% if (message != null) {%>
            <div class="success-message"><%=message%></div>
            <% } %>
        </div>
        <% } %>


        <%
        } else {
        %> 
        <%=AuthUtils.getAccessDeniedMessage("product-form action")%>        
        <%
            }
        %>
    </body>
</html>
