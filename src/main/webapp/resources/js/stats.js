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
//		console.log(JSON.stringify(successEntries));
		drawPieChart('#successChart', 'Audit Trail Success by User', 'Success rate per user', successEntries);
		
		var failureEntries = [];
		for(i = 0; i < data.length; i++) {
			var entry = [];
			entry.push(data[i].email);
			entry.push(data[i].failures);
			failureEntries.push(entry);
		}
//		console.log(JSON.stringify(failureEntries));
		drawPieChart('#failureChart', 'Audit Trail Failures by User', 'Failure rate per user', failureEntries);
		
	}).fail(function(err){
		console.log(err);
	});
	
	//drawPieChart('#pieChart', 'Audit Trail Success by User', 'Success rate per user', [{name:'A', y:25, sliced: true, selected: true}, ['A', 30], ['E', 15], ['D', 20], ['C', 10]]);
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