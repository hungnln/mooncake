<%-- 
    Document   : order
    Created on : Sep 19, 2021, 3:25:15 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order | View Order</title>
    </head>
    <body>
        <h1>All Orders</h1> 
        Welcome ${sessionScope.userdata.userName}<a href="LoadCake">Index</a>
        <table border="1">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Total</th>
                    <th>Customer</th>
                    <th>Status</th>
                    <th>Detail</th>
                </tr>
            </thead>
            <tbody>               

                <c:if test="${not empty requestScope.orders}">
                    <c:set var="orders" value="${requestScope.orders}"></c:set>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.orderDate}</td>
                            <td>${order.orderTotal}</td>
                            <td>${order.user.userID}</td>
                            <td>${order.orderStatus}</td>
                            <td><a href="OrderDetail?orderID=${order.orderID}">Get detail</a>
                        </tr>
                    </c:forEach>
                </c:if>

            </tbody>
        </table>
    </body>
</html>
