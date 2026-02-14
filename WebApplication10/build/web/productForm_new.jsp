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
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f6f8;
                margin: 0;
                padding: 40px;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            form {
                max-width: 600px;
                margin: auto;
                padding: 25px;
                background-color: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            div {
                margin-bottom: 20px;
            }

            label {
                display: block;
                font-weight: bold;
                margin-bottom: 6px;
                color: #555;
            }

            input[type="text"],
            input[type="number"],
            textarea {
                width: 100%;
                padding: 10px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
            }

            textarea {
                min-height: 80px;
                resize: vertical;
            }

            input[type="checkbox"] {
                margin-right: 8px;
                transform: scale(1.2);
            }

            input[type="submit"],
            input[type="reset"] {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                margin-right: 10px;
            }

            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
            }

            input[type="reset"] {
                background-color: #f44336;
                color: white;
            }

            .message {
                text-align: center;
                color: #d8000c;
                margin-top: 20px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <%
            if (AuthUtils.isAdmin(request)) {
            // là cách bạn lấy dữ liệu từ request
            //object trong JSP mà Servlet đã truyền sang
            ProductDTO product = (ProductDTO)request.getAttribute("product");
            String checkError = (String)request.getAttribute("checkError");
            String message = (String)request.getAttribute("message");
        %>
        <h1>Product Form</h1>  <br/>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="addProduct" />
            <div>
                <label for="id">ID*</label>
                <input type="text" name="id" id="id" required="required"
                       value="<%=product!=null?product.getId():""%>"
                       />
            </div>
            <div>
                <label for="name">Name*</label>
                <input type="text" name="name" id="name" required="required"
                       value="<%=product!=null?product.getName():""%>"
                       />
            </div>
             <div>
                <label for="image">Image</label>
                <input type="text" name="image" id="image"
                       value="<%=product!=null?product.getImage():""%>"
                       />
            </div>
            <div>
                <label for="description">Description</label> 
                <textarea type="text" name="description" id="description">
                   <%=product!=null?product.getDescription():""%>
                </textarea>
            </div>
            <div>
                <label for="price">Price*</label> 
                <input type="number" name="price" id="price" min="0" step="0.01" required="required"
                        value="<%=product!=null?product.getPrice():""%>"
                       />
            </div>
            <div>
                <label for="size">Size</label> 
                <input type="text" name="size" id="size"
                       value="<%=product!=null?product.getSize():""%>"
                       />
            </div>
            <div>
                <label for="status">Status (Active Product)</label> 
                <input type="checkbox" name="status" id="status" value="true"
                       <%=product!=null && product.isStatus()?"checked='checked'":""%>
                       />
            </div>
            <div>
                <input type="submit" value="Add Product"/>
                <input type="reset" value="Reset"/>
            </div>
        </form>
        <%=checkError!=null?checkError:""%>
        <%=message!=null?message:""%>
        <%
            } else {
         %> 
         <%=AuthUtils.getAccessDeniedMessage("product-form action")%>        
        <%    
            }
        %>
    </body>
</html>
