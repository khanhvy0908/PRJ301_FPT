<%-- 
    Document   : welcome
    Created on : 23 May 2025, 07:41:06
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- Tao cac bien dung nhieu lan -->
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID == 'AD'}"/>
<c:set var="keyword" value="${requestScope.keyword}"/>
<c:set var="productList" value="${requestScope.list}" />
<c:set var="hasKeyword" value="${not empty keyword}" />
<c:set var="hasProducts" value="${not empty productList}" />
<c:set var="productCount" value="${fn:length(productList)}" />
<c:set var="keywordParam" value="${hasKeyword ? keyword : ''}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Kiểm tra đăng nhập -->
        <c:choose>
            <c:when test="${not isLogged}">
                <c:redirect url="MainController"/>
            </c:when>
            <c:otherwise>
                <h1>Wellcome ${sessionScope.user.fullName}!</h1><br/>
                <a href="MainController?action=logout">Logout</a>

                <%-- Search action --%>        
                <br/><label>Search by name</label>
                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="searchProduct" />
                    <input type="text" name="strKeyword" value="${keywordParam}" />
                    <input type="submit" value="Search" />
                </form>
                <%-- Add new product link --%>
                <c:if test="${isAdmin}">
                    <a href="productForm.jsp">Add new Product</a>
                </c:if>

                <%--- lay object duoc gui tu servlet(list) khi user search --%>
                <c:choose>
                    <c:when test="${hasProducts and productCount == 0}">
                        No products have names that match the keyword!
                    </c:when>
                    <c:when test="${hasProducts and productCount > 0}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Size</th>
                                    <th>Status</th>
                                        <c:if test="${isAdmin}">
                                        <th>Actions</th>
                                        </c:if>                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="product" items="${productList}">
                                    <tr>
                                        <td>${product.id}</td>
                                        <td>${product.name}</td>                                   
                                        <td>${product.description}</td>
                                        <td class="price">$${product.price}</td>
                                        <td>${product.size}</td>
                                        <td class="${product.status ? 'status-true' : 'status-false'}">
                                            ${product.status ? 'Active' : 'Inactive'}
                                        </td>
                                        <c:if test="${isAdmin}">
                                            <td>
                                                <form action="MainController" method="POST">
                                                    <input type="hidden" name="action" value="editProduct" />
                                                    <input type="hidden" name="productId" value="${product.id}" />
                                                    <input type = "hidden" name = "strKeyword" value ="${keywordParam}" />
                                                    <input type="submit" name="edit" value="Edit" />
                                                </form>
                                                <form action="MainController" method="POST">
                                                    <input type="hidden" name="action" value="changeProductStatus" />
                                                    <input type="hidden" name="productId" value="${product.id}" /> 
                                                    <input type="hidden" name="strKeyword" value="${keywordParam}" />
                                                    <input type="submit" name="delete" value="Delete"
                                                           onclick="return confirm('Are you sure to delete this product?')"/>
                                                </form>
                                            </td>
                                        </c:if>

                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </c:when>
                </c:choose>

            </c:otherwise>
        </c:choose>
        
    </body>
</html>
