<%-- 
    Document   : view-cake
    Created on : Sep 20, 2021, 9:51:52 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | ${requestScope.cake.cakeName}</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <div><a href="LoadCake">Index </a> | <a href="Cart"> View Cart</a></div>
                <div>Welcome ${sessionScope.userdata.userName}<a href="Logout">Log out</a></div>
            </c:when>              
            <c:otherwise>
                <div><a href="LoadCake">Index </a> | <a href="Cart"> View Cart</a></div>
                <div><a href="Login">Log In</a>  | <a href="SignUp">Sign Up</a></div>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty requestScope.cake}">
            <h1>Moon Cake</h1>
            <c:set var="cake" value="${requestScope.cake}"></c:set>
                <div>
                    <div><img src="images/${cake.cakeImage}" alt="${cake.cakeName}" width="400"/></div>
                <div>${cake.cakeName}</div>
                <div>Description :${cake.cakeDescription}</div>
                <div>Category: ${cake.category.categoryName}</div>
                <div>Price : ${cake.cakePrice}</div>
                <div>
                    <c:choose>
                        <c:when test="${cake.cakeStatus == false || cake.cakeQuantity <= 0}">Contact</c:when>
                        <c:otherwise><a href="StoreCake?cakeID=${cake.cakeID}">Buy</a></c:otherwise>
                    </c:choose>
                </div>               
            </div>
        </c:if>
    </body>
</html>
