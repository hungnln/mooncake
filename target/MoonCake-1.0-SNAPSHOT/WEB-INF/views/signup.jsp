<%-- 
    Document   : signup
    Created on : Sep 11, 2021, 3:46:43 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Moon Cake | Sign Up</title>
    </head>
    <body>
        <h1>SIGN UP</h1>
        <div><a href="LoadCake">Index </a> | <a href="Login"> Log In</a></div>
        
        <div>
            <form action="SignUp" method="POST">
                <div>
                    ID: <input type="text" name="userID" value="${param.userID}"/>           
                    <c:if test="${not empty requestScope.msgID}">                                            
                        <strong>${requestScope.msgID}</strong>
                    </c:if>                
                </div>
                <div>
                    Name:<input type="text" name="userName" value="${param.userName}"/>           
                    <c:if test="${not empty requestScope.msgName}">
                        <strong>${requestScope.msgName}</strong>
                    </c:if>  
                </div>
                <div>
                    Password:   <input type="password" name="userPassword" value=""/>           
                    <c:if test="${not empty requestScope.msgPassword}">                                            
                        <strong>${requestScope.msgPassword}</strong>
                    </c:if> 
                </div>
                <div>
                    Re-Password:<input type=password name="userRePassword" value=""/>
                    <c:if test="${not empty requestScope.msgRePassword}">                                            
                        <strong>${requestScope.msgRePassword}</strong>
                    </c:if>
                </div>
                <div>
                    Phone Number:<input type="text" name="userPhoneNumber" value="${param.userPhoneNumber}"/>
                    <c:if test="${not empty requestScope.msgPhoneNumber}">                                            
                        <strong>${requestScope.msgPhoneNumber}</strong>
                    </c:if>
                </div>
                <div>
                    Address:<input type="text" name="userAddress" value="${param.userAddress}"/>
                    <c:if test="${not empty requestScope.msgAddress}">                                            
                        <strong>${requestScope.msgAddress}</strong> 
                    </c:if>
                </div>
                <div>
                    <input type="submit" value="OK">
                    <input type="reset" value="Reset">
                    
                </div>
            </form>

        </div>
    </body>
</html>
