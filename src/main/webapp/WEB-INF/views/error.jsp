<%-- 
    Document   : error
    Created on : Sep 23, 2021, 9:01:54 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Error</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty sessionScope.userdata}">
                <h1>Hello ${sessionScope.userdata} . Some thing went wrong !!!</h1>
                <a href="LoadCake">Come back to index page</a>
            </c:when>
            <c:otherwise>
                <h1>Hello Guest . Some thing went wrong !!!</h1>
                <a href="LoadCake">Come back to index page</a>
            </c:otherwise>
        </c:choose>
        
    </body>
</html>
