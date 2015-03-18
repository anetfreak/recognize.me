<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me! - Contact</title>
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="resources/css/index.css">
		<script type="text/javascript">
			$(document).ready(function(){
			  showPosition();
			});

			function showPosition() {
			  // var latlon='37.337166,-121.881329';
			  var coords = new google.maps.LatLng('37.337166', '-121.881329');

			  var mapOptions = {
			    zoom: 15,
			    center: coords,
			    mapTypeControl: true,
			    mapTypeId: google.maps.MapTypeId.ROADMAP
			  };

			  var map = new google.maps.Map(
			    document.getElementById("map"), mapOptions
			  );

			  var marker = new google.maps.Marker ({
			    position: coords,
			    map: map,
			    title: "Thunderbolts, Inc"
			  });
			}
		</script>
	</head>
	<body>
		<!-- #wrapper -->
		<div id="wrapper">
			<div class="overlay"></div>
			<%@include file="/WEB-INF/views/header.jsp"%>
			
			<!-- Page Content -->
			<div id="page-content-wrapper">
				<button type="button" class="hamburger is-closed" data-toggle="offcanvas">
					<span class="hamb-top"></span>
					<span class="hamb-middle"></span>
					<span class="hamb-bottom"></span>
				</button>
				
				<div class="container">
					<div class="row">
						<div class="col-lg-8 col-lg-offset-1">
							<div><h3>Recognize.me</h3></div>
							<hr>
					       	<div id="contact" style="margin:10px; !important">
							</div><!-- Main row -->
							<div class="col-md-10 text-center">
					    		<div><p>We are located right in the heart of San Jose</p></div>
					    		<div style="margin-top: 10px;"><code>Engineering Building, San Jose State University, San Jose, CA 95112</code></div>
					    		<div id='map' style="width: auto; height: 400px; padding: 0px; margin: 20px; 0px 20px 0px;"></div>
					    		<p><a href="mailto:info@recognize.me" title="Send Mail - Recognize.Me">We'd like to hear from you</a></p>
					  		</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /#page-content-wrapper -->
			<%@include file="/WEB-INF/views/footer.jsp"%>
		</div>
		<!-- /#wrapper -->
	</body>
</html>