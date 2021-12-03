<%-- 
    Document   : cake-crud
    Created on : Sep 14, 2021, 12:52:50 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | MANAGER</title>
    </head>
    <body>
        <h1>Moon Cake Management</h1> 
        Welcome ${sessionScope.userdata.userName}
        <a href="UpdateUser">Profile</a>
        <a href ="CreateCake">Create New Cake</a>
        <a href="Order">View Order</a>
        <a href="Logout"> Log out</a>
        <form action="SearchCake" method="GET">
            Search:
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
        <div>Show list ${size} items of ${count} Product</div>
        <c:if test ="${not empty requestScope.cakes}">
            <c:set var="cakes" value="${requestScope.cakes}"></c:set>
            <c:forEach var="cake" items="${cakes}">               
                <div style="border: 1px solid black;width: 500px;height:120px; margin: 2px;">                   
                    <div style="float: left; width: 200px;height: 120px;"><img src="images/${cake.cakeImage}" alt="${cake.cakeName}" width="200" height="120"></div>                
                    <div>
                        <div>Cake Name: ${cake.cakeName}</div>
                        <div>Date Create: ${cake.createDate}</div>
                        <div>
                            Status : 
                            <c:choose>
                                <c:when test="${cake.cakeStatus eq false}">Not Buy</c:when>
                                <c:otherwise>Buy</c:otherwise>
                            </c:choose>
                        </div>
                        <div><a href="UpdateCake?cakeID=${cake.cakeID}">Edit</a></div>  
                    </div>
                </div> 
                    <div class="clear" style="clear:both"></div>
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
