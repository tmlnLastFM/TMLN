<%-- 
    Document   : chartjsp
    Created on : 06.05.2019, 19:31:12
    Author     : michi
--%>"
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/chart.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <title>TMLN</title>
    </head>
    <body>
        <h1>TMLN</h1>
        
        <form action="TmlnServlet" method="POST">
            
            <c:forEach var="artist" items="${artists}" end="9">
                ${artist.getName()}<br>
            </c:forEach>
                
            <input type="submit" value="submit" />
                
            <div id="chartContainer" style="height: 370px; width: 60%;"></div>
            <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
            
        </form>
    </body>
</html>
