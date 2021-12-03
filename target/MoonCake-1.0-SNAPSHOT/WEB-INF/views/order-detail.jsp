<%-- 
    Document   : admin-order-detail
    Created on : Sep 20, 2021, 11:55:19 AM
    Author     : SE140018
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Order Detail</title>
    </head>
    <body>
        
        <c:choose>
            <c:when test="${sessionScope.userdata.roleId.id == 2}">
                <div>
                    <div><a href="LoadCake">Index</a><a href="Order">Orders</a></div>
                    <div><a href="Logout">Logout</a></div>
                </div>
            </c:when>
            <c:otherwise>
                <div><a href="LoadCake">Index</a><a href="Order">Orders</a></div>
                <div><a href="Logout">Logout</a></div>
            </c:otherwise>

        </c:choose>
                <h1>Order Detail</h1>
        <div>Order : ${requestScope.order.id}</div>
        <div>Cake | Quantity</div>
        <c:forEach var="orderDetail" items="${requestScope.orderDetails}" >
            <div>${orderDetail.cakeID.cakeName} - ${orderDetail.cakeOrderQuantity}</div>
        </c:forEach>
        <div>
            <div>
                Total : ${requestScope.order.orderTotal}
            </div>
            <div>
                Date : ${requestScope.order.orderDate}
            </div>

            <div>
                Customer:
                Name: ${requestScope.order.fullName}
                Address : ${requestScope.order.address}
                Phone : ${requestScope.order.phoneNumber}
            </div>
        </div>
        <div>
            <div>Status : </div>
            <c:choose>
                <c:when test="${sessionScope.userdata.roleId.id eq 2}">
                    <div>
                        <c:choose>
                            <c:when test="${requestScope.order.orderStatus eq -1}">
                                Deny
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 0}">
                                <div>
                                    <a href="ChangeOrderStatus?orderID=${requestScope.order.id}&status=-1">Deny</a>
                                    <a href="ChangeOrderStatus?orderID=${requestScope.order.id}&status=1">Confirm</a>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 1}">
                                <div>                               
                                    <a href="ChangeOrderStatus?orderID=${requestScope.order.id}&status=2">Shipping</a>
                                    Cancel <a href="ChangeOrderStatus?orderID=${requestScope.order.id}&status=-1">Deny</a>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 2}">
                                <div>
                                    Shipping |
                                    Cancel : <a href="ChangeOrderStatus?orderID=${requestScope.order.id}&status=-1">Deny</a>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 3}">
                                Done
                            </c:when>
                        </c:choose>
                    </div>              
                </c:when>
                <c:when test="${sessionScope.userdata.roleId.id eq 1}">
                    <div>
                        <c:choose>
                            <c:when test="${requestScope.order.orderStatus eq 0}">
                                <div>
                                   Waiting for confirm
                                </div>
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 1}">
                                <div>                               
                                    Confirmed
                                </div>
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 2}">
                                <div>
                                   Shipping <a href="ChangeOrderStatus?orderID=${requestScope.order.id}&status=3">Get cake</a>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.order.orderStatus eq 3}">
                                Done
                            </c:when>
                        </c:choose>
                    </div>              
                </c:when>

            </c:choose>           
        </div>
    </body>
</html>
