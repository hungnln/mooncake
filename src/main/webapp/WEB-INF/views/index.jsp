<%-- 
    Document   : index
    Created on : Sep 12, 2021, 9:35:49 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake Shopping</title> 
    </head>
    <body>        
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <div><a href="LoadCake">Index </a> | <a href="Order"> View Order </a> | <a href="UpdateUser"> User </a> | <a href="Cart"> Cart</a></div>
                <div>Welcome ${sessionScope.userdata.userName}<a href="Logout">Log out</a></div>
            </c:when>              
            <c:otherwise>
                <div><a href="LoadCake">Index </a> | <a href="Cart"> View Cart</a></div>
                <div><a href="Login">Log In</a>  | <a href="SignUp">Sign Up</a></div>
            </c:otherwise>
        </c:choose>
                <h1>Moon Cake Shop</h1>
        <form action="SearchCake" method="GET">
            Search
            <input type="text" name="searchCakeName" value="${param.searchCakeName}" placeholder="Enter Cake Name">
            <div>Price range :
                <select name="searchPriceRange">
                    <option value="0,1000000">All</option>
                    <option value="0,200000">Below 200.000</option>
                    <option value="200000,500000">200.000 - 500.000</option>
                    <option value="500000,1000000">Under 500.000</option>
                    <option value="500000,700000">500.000 - 700000</option>
                    <option value="700000,1000000">700.000 - 1000000</option>
                </select>
            </div>
            <div>Category:
                <select name="categoryID">
                    <option value="0">Choose Category</option>
                    <c:if test="${not empty requestScope.categories}">
                        <c:set var="categories" value="${requestScope.categories}"></c:set>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryID}">${category.categoryName}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <input type="submit" value="OK">
        </form>
        <c:if test="${not empty requestScope.msg}">
            ${requestScope.msg}
        </c:if>
        <c:if test="${not empty requestScope.cakes}">
            <div>Show list ${requestScope.size} items of ${requestScope.count} Product</div>
            <c:set var="cakes" value="${requestScope.cakes}"></c:set>
            <c:forEach var="cake" items="${cakes}">
                <div class="item" style="border: 1px solid black;width: 160px; height: 220px;float:left; margin: 2px; text-align: center;">
                    <div title="${cake.cakeDescription}">
                        <div style="width: 160px;height: 140px;"><img src="images/${cake.cakeImage}" alt="${cake.cakeName}" width="160" height="140"></div>
                        <div>${cake.cakeName}</div>
                        <div>${cakePrice}</div>                    
                    </div>
                    <div title="${cake.expirationDate}">
                        <c:choose>
                            <c:when test="${cake.cakeStatus eq true}">
                                <a href="StoreCake?cakeID=${cake.cakeID}">Buy</a> 
                            </c:when>
                            <c:otherwise>
                                Contact 
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div><a href="ViewCake?cakeID=${cake.cakeID}">View Detail</a></div>
                </div>               
            </c:forEach>
            <div class="clear" style="clear:both"></div>
        </c:if>
        <c:choose>
            <c:when test="${(not empty param.searchCakeName) or (not empty param.categoryID) or (not empty param.searchPriceRange)}">
                <div class="paging">
                    <c:forEach begin="1" end="${requestScope.end}" var="i">
                        <a href="SearchCake?index=${i}&searchPriceRange=${param.searchPriceRange}&searchCakeName=${param.searchCakeName}&categoryID=${param.categoryID}">${i}</a>
                    </c:forEach>          
                </div>
            </c:when>
            <c:otherwise>
                <div class="paging">
                    <c:forEach begin="1" end="${requestScope.end}" var="i">
                        <a href="LoadCake?index=${i}">${i}</a>
                    </c:forEach>          
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>
