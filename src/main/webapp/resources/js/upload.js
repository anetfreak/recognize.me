$(document).ready(function () {
	$('#input-div').show();
	$('.test-image').on('click', function(e){
		e.stopPropagation();
		var filename = $(this).attr('id');
		$.post('http://localhost:8080/sampleUpload', { file: filename, email: 'amit.agrawal@sjsu.edu', latitude: '37.337166', longitude: '-121.881329' })
		.done(function(data) {
			console.log('Sample Test Request received..');
			$('#input-div').hide();
			$('#result-div').show();
		})
		.fail(function(err){
			console.log(err);
		})
		
	});
	
	$('#showImagesLink').on('click', function(){
		$('#input-div').show();
		$('#result-div').hide();
	});
});