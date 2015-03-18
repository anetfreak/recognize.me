<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me! - About</title>
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="resources/css/index.css">
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
							<h3>Recognize.me</h3>
							<hr>
					          <div><p style="font-family: 'Roboto', sans-serif; font-size:20px;">Team</p></div>
					          <div style="margin-top: 20px;">
					            <div class="col-md-4 text-center">
					              <img class="img-circle" src="resources/img/csoni.jpg" style="max-width: 40% !important; margin-bottom: 20px;" alt="Chitra Soni - Developer, Web Analyzer Team">
					              <h3>Chitra Soni</h3>
					              <p>Developer & Shopper </p>
					            </div>
					            <div class="col-md-4 text-center">
					              <img class="img-circle" src="resources/img/aagrawal.jpg" style="max-width: 40% !important; margin-bottom: 20px;" alt="Amit Agrawal - Developer, Web Analyzer Team">
					              <h3>Amit Agrawal</h3>
					              <p>Developer & C++ Programmer </p>
					            </div>
					            <div class="col-md-4 text-center">
					              <img class="img-circle" src="resources/img/ajoshi.jpg" style="max-width: 40% !important; margin-bottom: 20px;" alt="Ameya Joshi - Developer, Web Analyzer Team">
					              <h3>Ameya Joshi</h3>
					              <p>Coder & Tweeter </p>
					            </div>
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