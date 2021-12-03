<%-- 
    Document   : cart
    Created on : Sep 15, 2021, 10:45:49 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Cart</title>
    </head>
    <body>
        <h1>Cart</h1>
        Welcome ${sessionScope.userdata.userName}
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <div><a href="LoadCake">Index </a></div>
                <div><a href="Logout">Log out</a></div>
            </c:when>              
            <c:otherwise>
                <div><a href="LoadCake">Index </a></div>
                <div><a href="Login">Log In</a></div>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty requestScope.msgRemove}">
            ${requestScope.msgRemove}
        </c:if>
        <c:if test="${not empty requestScope.msgDelete}">
            ${requestScope.msgDelete}
        </c:if>
        <c:choose>
            <c:when test="${not empty sessionScope.cart}">
                <table border="1">
                    <thead>
                        <tr>
                            <th> Items</th>
                            <th> Unit Price</th>
                            <th> Quantity</th>
                            <th> Amount of money </th>
                            <th> Operation</th>
                        </tr>
                    </thead>
                    <tbody>                  
                        <c:set var="total" value="0"></c:set>
                        <c:forEach var="cakeList" items="${sessionScope.cart}">
                            <c:set var="total" value="${pageScope.total+cakeList.cake.cakePrice*cakeList.quantity}"></c:set>
                                <tr>
                                    <td>
                                        <div>
                                            <img src="images/${cakeList.cake.cakeImage}" alt="${cakeList.cake.cakeImage}" width="120" height="120">
                                        ${cakeList.cake.cakeName}
                                    </div>
                                </td>
                                <td>${cakeList.cake.cakePrice}</td>
                                <td>${cakeList.quantity}</td>
                                <td>${cakeList.cake.cakePrice*cakeList.quantity}</td>
                                <td><a href="RemoveCake?cakeID=${cakeList.cake.cakeID}" onclick='return confirm("Remove this cake?");'>Remove</a></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="4"></td>
                            <td>Total : ${pageScope.total} </td>
                        </tr>
                    </tbody>
                </table>
                        <form action="Cart" method="POST">
                            <input type="submit" value="Buy">
                        </form>
                        <a href="DeleteCart" onclick='return confirm("Remove all items?");'>Remove all items</a>
            </c:when>
            <c:otherwise>
                No item in cart
            </c:otherwise>
        </c:choose>
    </body>
</html>
