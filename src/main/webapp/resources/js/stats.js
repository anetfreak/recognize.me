$(document).ready(function () {
	$.ajax({
		url: 'http://localhost:8080/stats/getResults',
		method: 'GET'
	}).done(function(data){
		
		var successEntries = [];
		for(i = 0; i < data.length; i++) {
			var entry = [];
			entry.push(data[i].email);
			entry.push(data[i].success);
			successEntries.push(entry);
		}
		drawPieChart('#successChart', 'Audit Trail Success by User', 'Success rate per user', successEntries);
		
		var failureEntries = [];
		for(i = 0; i < data.length; i++) {
			var entry = [];
			entry.push(data[i].email);
			entry.push(data[i].failures);
			failureEntries.push(entry);
		}
		drawPieChart('#failureChart', 'Audit Trail Failures by User', 'Failure rate per user', failureEntries);
		
		var xAxisList = [];
		for(i = 0; i < data.length; i++) {
			var entry = [];
			entry.push(data[i].email);
			xAxisList.push(entry);
		}
		
		var yAxisList = [];
		
		var entries = [];
		var successArray = [];
		for(i = 0; i < data.length; i++) {
			successArray.push(data[i].success);
		}
		
		var entry = {
			name: 'Success',
			data: successArray
		};
		yAxisList.push(entry);
		
		var entries = [];
		var failureArray = [];
		for(i = 0; i < data.length; i++) {
			failureArray.push(data[i].failures);
		}
		
		var entry = {
			name: 'Failure',
			data: failureArray
		};
		yAxisList.push(entry);
		
		drawLineChart('#lineChart', 'Success/Failure Ratio per User', 'Shows the success/failure bifurcation for each user', 'Success/Failure Count', xAxisList, yAxisList);
		
	}).fail(function(err){
		console.log(err);
	});
	
});

function drawPieChart(container,titleText, seriesName,valueData) {
  $(container).highcharts({
    chart: {
      plotBackgroundColor: null,
      plotShadow: false
    },
    title: {
      text: titleText
    },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: true,
          format: '<b>{point.name}</b>: {point.percentage:.1f} %'
        }
      }
    },
    series: [{
      type: 'pie',
      name: seriesName,
      data: valueData
    }]
  });
}


function drawLineChart(container, titleText, subtitleText, yaxisText, list_xaxis,list_yaxis) {
  $(container).highcharts({
    chart: {
    	type: 'column'
    },
	title: {
      text: titleText
    },
    subtitle: {
      text: subtitleText
    },
    xAxis: {
      categories: list_xaxis,
      labels: {
        rotation: -45
      }
    },
    yAxis: {
      title: {
        text: yaxisText
      },
      stackLabels: {
          enabled: true,
          style: {
              fontWeight: 'bold',
              color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
          }
      }
    },
    tooltip: {
      valueSuffix: ''
    },
    legend: {
        align: 'right',
        x: -30,
        verticalAlign: 'top',
        y: 25,
        floating: true,
        backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
        borderColor: '#CCC',
        borderWidth: 1,
        shadow: false
    },
    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                style: {
                    textShadow: '0 0 3px black'
                }
            }
        }
    },
    series: list_yaxis
  });
}