<%-- 
    Document   : chartjsp
    Created on : 06.05.2019, 19:31:12
    Author     : michi
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TMLN</title>
    </head>
    <body>
        <h1>TMLN</h1>
        <form action="TmlnServlet" method="POST">
            <c:forEach var="artist" items="${artists}" end="9">
                ${artist.getName()}<br>
            </c:forEach>
            
            <jsp:include page="chartjsp.jsp"/>
            
            <input type="submit" value="submit" />
        </form>
    </body>
</html>
