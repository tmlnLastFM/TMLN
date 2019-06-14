<%-- 
    Document   : chartjsp
    Created on : 06.05.2019, 19:31:12
    Author     : michi
--%>
<%@page import="java.util.Random"%>
<%@page import="Beans.TMLNArtist"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.Gson"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% List<String> dataList = null;
    String allData = "";
    Random r = new Random();
    Gson gson = new Gson();
    if (request.getAttribute("data") != null) {
        List<TMLNArtist> artistList = (LinkedList<TMLNArtist>) request.getAttribute("data");
        dataList = new LinkedList<>();
        for (TMLNArtist artist : artistList) {
            dataList.add("{ label: '" + artist.getName() + "',\n "
                    + "backgroundColor: 'rgba(0,0,0,0)',\n "
                    + "borderColor: 'rgb("+gson.toJson(artist.getColor()).replace('[', ' ').replace(']', ' ')+")',"
                    + "data: " + gson.toJson(artist.getCoordsList()) + "}");
        }
    }
    if (dataList != null) {
        for (int i = 0; i < dataList.size(); i++) {
            allData += i == dataList.size() ? dataList.get(i) : dataList.get(i) + ",";
        }
    }
%>    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/dropdown.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
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
            <input type="text" name="username" value="${param.username}" />
            <select name="type" value="1">
                <option value="1"<c:if test="${param.type==1}">selected</c:if>>Top Artists</option>
                <%-- <option value="2"<c:if test="${param.type==2}">selected</c:if>>Top Albums</option> --%>
                <option value="3"<c:if test="${param.type==3}">selected</c:if>>Top Tracks</option>
                </select>
                <div class="dropdown">
                    <input type="button" onclick="myFunction()" class="dropbtn" value="Period">
                    <div class="dropdown-content" id="myDropdown">
                        <a href="#" id="last">Last 7 days</a>
                        <a href="#" id="">Last 30 days</a>
                        <a href="#" onclick="">Last 90 days</a>
                        <div class="dropdown-divider"></div>
                        <div class="dropdown-header">From</div>
                        <a><input type="date" name="from"/></a>
                        <div class="dropdown-header">To</div>
                        <a><input type="date" name="to"/></a>                        
                    </div>
                </div>

                <select name="scale" value="2">
                    <option value="1"<c:if test="${param.type==1}">selected</c:if>>Weekly</option>
                <option value="2"<c:if test="${param.type==2}">selected</c:if>>Monthly</option>
                <option value="3"<c:if test="${param.type==3}">selected</c:if>>Yearly</option>
                </select>


                <input type="submit" value="Submit" class='dropbtn'/>
                <br><br><br>

            <c:if test="${data!=''}">
                <div id="chartContainer"><canvas id="myChart"></canvas></div>
                <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
                <script>
                        Chart.defaults.line.spanGaps = false;  
                        Chart.defaults.scale.ticks.reverse = true;
                        var ctx = document.getElementById('myChart').getContext('2d');
                        var chart = new Chart(ctx, {
                            type: 'line',

                            data: {
                                datasets: [<%=allData%>],
                                labels: <%=gson.toJson(request.getAttribute("yList"))%>
                            },
                        });
                </script>
            </c:if>

            <div id="top10">
                <c:forEach var="artist" items="${top10}">
                    ${artist.getPlace()}. ${artist.getName()} - ${artist.getPlaycount()} Plays<br>
                </c:forEach>
            </div>  
        </form>
    </body>
</html>