<%-- 
    Document   : update-cake
    Created on : Sep 14, 2021, 8:25:56 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Update</title>
        <style>
            label{
                min-width: 120px;
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <h1>Update Cake</h1>
        Welcome ${sessionScope.userdata.userName} | <a href="LoadCake">Index</a>
        <form action="UpdateCake" method="POST" enctype="multipart/form-data">
            <c:if test ="${not empty requestScope.msgUpdate}">
                ${requestScope.msgUpdate}
            </c:if>
            <div>
                <input type="hidden" value="${cake.cakeID}" name="cakeID">
            </div>
            <div>
                <label> Cake Name:</label> <input type="text" value="${cake.cakeName}" name="cakeName">
                <c:if test ="${not empty requestScope.msgCakeName}">
                    ${requestScope.msgCakeName}
                </c:if>
            </div>
            <div>
                <label>Cake Description:</label> <input type="text" value="${cake.cakeDescription}" name="cakeDescription">
                <c:if test ="${not empty requestScope.msgCakeDescription}">
                    ${requestScope.msgCakeDescription}
                </c:if>
            </div>

            <div>
                <label>Cake Price:</label> <input type="text" value="${cake.cakePrice}" name="cakePrice">
                <c:if test ="${not empty requestScope.msgCakePrice}">
                    ${requestScope.msgCakePrice}
                </c:if>
            </div>

            <div>
                <label>Cake Quantity:</label> <input type="text" value="${cake.cakeQuantity}" name="cakeQuantity">
                <c:if test ="${not empty requestScope.msgCakeQuantity}">
                    ${requestScope.msgCakeQuantity}
                </c:if>
            </div>
            <div>
                <label>Create Date :</label> ${cake.createDate}
            </div>

            <div>
                <label>Expiration Day:</label> <input type="date" value="${cake.expirationDate}" name="expirationDate">
                <c:if test ="${not empty requestScope.msgExpirationDate}">
                    ${requestScope.msgExpirationDate}
                </c:if>
            </div>

            <div>
                <label>Category:</label> <c:if test="${not empty requestScope.categories}">
                    <c:set var="categories" value="${requestScope.categories}"></c:set>
                        <select name="categoryID">
                        <c:forEach var="category" items="${requestScope.categories}">
                            <c:choose>
                                <c:when test="${cake.category.categoryID eq category.categoryID}">
                                    <option value="${category.categoryID}" selected="selected">${category.categoryName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${category.categoryID}">${category.categoryName}</option>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select>
                </c:if>
            </div>
            <div>
                <label>Status :</label>
                        <select name="cakeStatus">
                        <c:choose>
                            <c:when test="${cake.cakeStatus == true}">
                                <option value="true" selected="selected">Sale</option>
                                <option value="false">Not Sale</option>
                            </c:when>
                            <c:otherwise>
                                <option value="true" >Sale</option>
                                <option value="false" selected="selected">Not Sale</option>
                            </c:otherwise>
                        </c:choose>
                    </select> 

            </div>
            <div>
                <label>Input Image URL :</label> <input type="file" name="cakeImage" value="${cake.cakeImage}">
            </div>
            <div>
                <input type="submit" value="Update">
                <input type="reset" value="Reset">
            </div>
        </form>
    </body>
</html>
