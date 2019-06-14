<%-- 
    Document   : chartjsp
    Created on : 06.05.2019, 19:31:12
    Author     : michi
--%>
<%@page import="java.util.Random"%>
<%@page import="Beans.TMLNEntry"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.Gson"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% List<String> dataList = null;
    String allData = "";
    Random r = new Random();
    Gson gson = new Gson();
    if (request.getAttribute("data") != "") {
        List<TMLNEntry> entryList = (LinkedList<TMLNEntry>) request.getAttribute("data");
        dataList = new LinkedList<>();
        for (TMLNEntry entry : entryList) {
            dataList.add("{ label: '" + entry.getArtist() + "',\n "
                    + "backgroundColor: 'rgba(0,0,0,0)',\n "
                    + "borderColor: 'rgb(" + entry.getColorString() + ")',"
                    + "data: " + gson.toJson(entry.getCoordsList()) + "}");
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
        <div id="input"> 
            Username:  
            <input type="text" name="username" value="${param.username}" id="abstand"/>
            <select name="type" value="1" id="abstand">
                <option value="1"<c:if test="${param.type==1}">selected</c:if>>Top Artists</option>
                <%-- <option value="2"<c:if test="${param.type==2}">selected</c:if>>Top Albums</option> --%>
                <option value="3"<c:if test="${param.type==3}">selected</c:if>>Top Tracks</option>
            </select>

            <select name="scale"value="2" id="abstand">
                <option value="1"<c:if test="${param.scale==1}">selected</c:if>>Weekly</option>
                <option value="2"<c:if test="${param.scale==2}">selected</c:if>>Monthly</option>
                <option value="3"<c:if test="${param.scale==3}">selected</c:if>>Yearly</option>
            </select>

            <div class="dropdown">
                <input type="button" onclick="myFunction()" class="dropbtn" value="Period" id="abstand">
                    <div class="dropdown-content" id="myDropdown">
<!--                        <a onclick="">last 90 days</a>
                        <a href="#" id="">last year</a>
                        <a href="#" onclick="">all time</a>
                        <div class="dropdown-divider"></div>-->
                        <div class="dropdown-header">from</div>
                        <a><input type="date" name="from" value="${dateFrom}"/></a>
                        <div class="dropdown-header">to</div>
                        <a><input type="date" name="to" value="${dateTo}"/></a>                        
                    </div>
            </div>

            <input type="submit" value="Submit" class='dropbtn' id="abstand"/>
        </div><br>
    </form>
        <c:if test="${data!=''}">
            <div id="chartContainer"><canvas id="myChart"></canvas></div>
            <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
            <script>
                    var ctx = document.getElementById('myChart').getContext('2d');
                    let chart = new Chart(ctx, {
                        type: 'line',

                        data: {
                            datasets: [<%=allData%>],
                            labels: <%=gson.toJson(request.getAttribute("xList"))%>
                        },
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        reverse: true
                                    }
                                }]
                            },
                            tooltips: {
                                enabled: false,
                                mode: 'dataset',
                                position: 'nearest'
                            },
                            legend: {
                                display: false
                            },
                            point: {
                                radius: 20,
                                hoverRadius: 6,
                                backgroundColor: 'rgba(0, 0, 0, 0.8)'
                            }
                        }
                    });
            </script>
        </c:if>
       
        <c:if test="${top10!=''}"> 
            <div id="top10">
                <center><header id="h">TOP 10 Artists</header> <br></center>
                    <c:forEach var="entry" items="${top10}">
                        <font style="color: rgb(${entry.getColorString()})">▬</font>
                        ${entry.getPlace()}. ${entry.getArtist()} - ${entry.getPlaycount()} Plays <br>
                    </c:forEach>
            </div>  
        </c:if> 
<!--        <div class="footer">
            © 2019 TMLN - Michael Ulz & Nicola Kolenz
        </div>-->
</body>
</html>
