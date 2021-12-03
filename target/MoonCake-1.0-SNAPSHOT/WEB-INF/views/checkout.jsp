<%-- 
    Document   : checkout
    Created on : Sep 18, 2021, 2:43:53 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Check out</title>        
    </head>
    <body>       
        <div>
            Welcome ${sessionScope.userdata.userName}
            <a href="LoadCake">Index</a>
            <a href="Logout">Log out</a>
            <table border="1">
                <thead>
                    <tr>
                        <th> Items</th>
                        <th> Unit Price</th>
                        <th> Quantity</th>
                        <th> Amount of money </th>

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

                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3"></td>
                        <td>Total : ${pageScope.total} </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div>
            Customer Information               
            <form action="CheckOut" method="POST">
                <div>
                    Name:
                    <input type="text" name="customerName" value="${sessionScope.userdata.userName}" />
                    <div>
                        <c:if test="${not empty requestScope.msgCustomerName}">
                            ${requestScope.msgCustomerName}
                        </c:if>
                    </div>
                </div>
                <div>
                    Phone:
                    <input type="text" name="customerPhone" value="${sessionScope.userdata.userPhoneNumber}" />
                    <div>
                        <c:if test="${not empty requestScope.msgCustomerPhone}">
                            ${requestScope.msgCustomerPhone}
                        </c:if>
                    </div>
                </div>
                <div>
                    Address:
                    <input type="text" name="customerAddress" value="${sessionScope.userdata.userAddress}" />
                    <div>
                        <c:if test="${not empty requestScope.msgCustomerAddress}">
                            ${requestScope.msgCustomerAddress}
                        </c:if>
                    </div>
                </div> 
                <div>Payment: COD</div>
                <input type="hidden" name="cbPayment" value="cash"/>              
                <input type="submit" value="Checkout" />
                <input type="reset" value="Reset"/>
            </form>



        </div>


    </body>
</html>
