<%-- 
    Document   : chartjsp
    Created on : 06.05.2019, 19:31:12
    Author     : michi
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/chart.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="js/dropdown.js" type="text/javascript"></script>
        <title>TMLN</title>
    </head>
    <body>

        <div class="foo">
            <span class="letter" data-letter="T">T</span>
            <span class="letter" data-letter="M">M</span>
            <span class="letter" data-letter="L">L</span>
            <span class="letter" data-letter="N">N</span>
        </div>

        <form action="TmlnServlet" method="POST">


            <input type="text" name="username" value="${param.username} username" />
            <select name="type" value="1">
                <option value="1"<c:if test="${param.type==1}">selected</c:if>>Top Artists</option>
                <%-- <option value="2"<c:if test="${param.type==2}">selected</c:if>>Top Albums</option> --%>
                <option value="3"<c:if test="${param.type==3}">selected</c:if>>Top Tracks</option>
                </select>
            <%-- Range --%>
            

                
                
                
                    <div class="dropdown">
                        <input type="button" onclick="myFunction()" class="dropbtn" value="Period">
                        <div class="dropdown-content" id="myDropdown">
                            <a href="#" id="last">Last 7 days</a>
                            <a href="#" id="">Last 30 days</a>
                            <a href="#" onclick="">Last 90 days</a>
                            <div class="dropdown-divider"></div>
                            <div class="dropdown-header">From</div>
                            <a><input type="date" /></a>
                            <div class="dropdown-header">To</div>
                            <a><input type="date" /></a>                        
                        </div>
                    </div>
            
            <select name="scale" value="2">
                <option value="1"<c:if test="${param.type==1}">selected</c:if>>Weekly</option>
                <option value="2"<c:if test="${param.type==2}">selected</c:if>>Monthly</option>
                <option value="3"<c:if test="${param.type==3}">selected</c:if>>Yearly</option>
                </select>
            
            
               <input type="submit" value="Submit" class='dropbtn'/>
                <br><br><br>
                

                <div id="chartContainer"></div>
                <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

                <div id="top10">
                <c:forEach var="artist" items="${top10}">
                    ${artist.getPlace()}. ${artist.getName()} - ${artist.getPlaycount()} Plays<br>
                </c:forEach>
            </div>  

        </form>
    </body>
</html>
