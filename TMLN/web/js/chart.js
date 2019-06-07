/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function () {
    var chart = new CanvasJS.Chart("chartContainer", {
        animationEnabled: true,
        theme: "light2",
        axisX: {
            valueFormatString: "DD MMM,YY",
        },
        axisY: {
            minimum:0.5,
            maximum:10,
            reversed: true,
            gridColor: "#F5F5F5",
            interval: 1,
        },
        legend: {
            cursor: "pointer",
            fontSize: 16,
            itemclick: toggleDataSeries
        },
        toolTip:{
            shared: true
        },
        data: [{
            name: "Hear Me Calling",
            type: "line",
            yValueFormatString: "#0.",
            showInLegend: true,
            dataPoints: [
                { x: new Date(2017,6,24), y: 1 },
                { x: new Date(2017,6,25), y: 2 },
                { x: new Date(2017,6,26), y: 1 },
                { x: new Date(2017,6,27), y: 2 },
                { x: new Date(2017,6,28), y: 1 },
                { x: new Date(2017,6,29), y: 2 },
                { x: new Date(2017,6,30), y: 5 }
            ]
        },
        {
            name: "Legends",
            type: "line",
            yValueFormatString: "#0.",
            showInLegend: true,
            dataPoints: [
                { x: new Date(2017,6,24), y: 5 },
                { x: new Date(2017,6,25), y: 1 },
                { x: new Date(2017,6,26), y: 2 },
                { x: new Date(2017,6,27), y: 3 },
                { x: new Date(2017,6,28), y: 2 },
                { x: new Date(2017,6,29), y: 1 },
                { x: new Date(2017,6,30), y: 2 }
            ]
        },
        {
            name: "Drain Me",
            type: "line",
            color: "lightgrey",
            yValueFormatString: "#0.",
            showInLegend: false,
            dataPoints: [
                { x: new Date(2017,6,24), y: 4 },
                { x: new Date(2017,6,25), y: 5 },
                { x: new Date(2017,6,26), y: 4 },
                { x: new Date(2017,6,27), y: 4 },
                { x: new Date(2017,6,28), y: 5 },
                { x: new Date(2017,6,29), y: 3 },
                { x: new Date(2017,6,30), y: 1 }
            ]
        },
        {
            name: "I've Been Waiting",
            type: "line",
            color: "lightgrey",
            yValueFormatString: "#0.",
            showInLegend: false,
            dataPoints: [
                { x: new Date(2017,6,24), y: 2 },
                { x: new Date(2017,6,25), y: 4 },
                { x: new Date(2017,6,26), y: 5 },
                { x: new Date(2017,6,27), y: 5 },
                { x: new Date(2017,6,28), y: 3 },
                { x: new Date(2017,6,29), y: 4 },
                { x: new Date(2017,6,30), y: 4 }
            ]
        },
        {
            name: "1 SIDED LOVE",
            type: "line",
            yValueFormatString: "#0.",
            showInLegend: true,
            dataPoints: [
                { x: new Date(2017,6,24), y: 3 },
                { x: new Date(2017,6,25), y: 3 },
                { x: new Date(2017,6,26), y: 3 },
                { x: new Date(2017,6,27), y: 1 },
                { x: new Date(2017,6,28), y: 4 },
                { x: new Date(2017,6,29), y: 5 },
                { x: new Date(2017,6,30), y: 3 }
            ]
        }]
    });
    chart.render();

    function toggleDataSeries(e){
        if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
            e.dataSeries.visible = false;
        } else{
            e.dataSeries.visible = true;
        }
        chart.render();
    }
};