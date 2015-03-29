<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me!</title>
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
							<div style="margin-top:50px;">
								<a href="https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=252473431161-0314f3l13do6kb34r7v0squmk604fjda.apps.googleusercontent.com&redirect_uri=http://localhost:8080/oauth2callback&scope=https://www.googleapis.com/auth/glass.timeline+https://www.googleapis.com/auth/userinfo.profile&state=amit.agrawal@sjsu.edu&approval_prompt=force&access_type=offline">Click here</a> to authorize your Glass here..
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