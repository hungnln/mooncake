<%-- 
    Document   : create-cake
    Created on : Sep 14, 2021, 2:24:39 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Create</title>
        <style>
            label{
                min-width: 120px;
                display: inline-block;
            }
        </style>
    </head>
    <body>       
        <div>
            <h1>Create Cake</h1>
            Welcome ${sessionScope.userdata.userName}| <a href="LoadCake">Index</a>
        </div>
        <form action="CreateCake" method="POST" enctype="multipart/form-data">
            <c:if test ="${not empty requestScope.msgCreate}">
                ${requestScope.msgCreate}
            </c:if>
            <div>
                <label>Cake Name :</label> <input type="text" value="${param.cakeName}" name="cakeName">
                <c:if test ="${not empty requestScope.msgCakeName}">
                    ${requestScope.msgCakeName}
                </c:if>
            </div>
            <div>
                <label>Cake Description : </label> <input type="text" value="${param.cakeDescription}" name="cakeDescription">
                <c:if test ="${not empty requestScope.msgCakeDescription}">
                    ${requestScope.msgCakeDescription}
                </c:if>
            </div>

            <div>
                <label> Cake Price :</label> <input type="text" value="${param.cakePrice}" name="cakePrice">
                <c:if test ="${not empty requestScope.msgCakePrice}">
                    ${requestScope.msgCakePrice}
                </c:if>
            </div>
            <div>
                <label>Cake Quantity :</label> <input type="text" value="${param.cakeQuantity}" name="cakeQuantity">
                <c:if test ="${not empty requestScope.msgCakeQuantity}">
                    ${requestScope.msgCakeQuantity}
                </c:if>
            </div>

            <div>
                <label> Expiration Day :</label> <input type="date" value="${param.expirationDate}" name="expirationDate">
                <c:if test ="${not empty requestScope.msgExpirationDate}">
                    ${requestScope.msgExpirationDate}
                </c:if>
            </div>

            <div>
                <label>Category :</label> <c:if test="${not empty requestScope.categories}">
                    <c:set var="categories" value="${requestScope.categories}"></c:set>

                        <select name="categoryID">
                        <c:forEach var="category" items="${requestScope.categories}">
                            <option value="${category.categoryID}">${category.categoryName}</option>   
                        </c:forEach>
                    </select>

                </c:if>
            </div>
            <div>
                <label> Status :</label>
                <select name="cakeStatus">
                    <option value="true" selected="selected">Sale</option>
                    <option value="false">Not Sale</option>
                </select>
            </div>
            <div>
                <label>Input Image URL : </label> <input type="file" name="cakeImage">
            </div>
            <div>
                <input type="submit" value="Create">
                <input type="reset" value="Reset">
            </div>
        </form>
    </body>
</html>
