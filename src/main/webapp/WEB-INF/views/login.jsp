<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me! - Upload</title>
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
							
							<a href="https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=252473431161-0314f3l13do6kb34r7v0squmk604fjda.apps.googleusercontent.com&redirect_uri=http://localhost:8080/oauth2callback&scope=https://www.googleapis.com/auth/glass.timeline+profile+email&state=amit.agrawal@sjsu.edu&approval_prompt=force&access_type=offline" id="signInButton"><img src="/resources/img/google_signin.png" width="200px" height="50px"></img></a>
							
							<!-- Login -->
<!-- 							<div id="loginbox" class="mainbox col-lg-8 col-sm-10"> -->
<!-- 								<div class="panel panel-info"> -->
<!-- 									<div class="panel-heading"> -->
<!-- 										<div class="panel-title">Sign In</div> -->
<!-- 										<div style="float: right; font-size: 80%; position: relative; top: -10px"> -->
<!-- 											<a href="#">Forgot password?</a> -->
<!-- 										</div> -->
<!-- 									</div> -->
						
<!-- 									<div style="padding-top: 30px" class="panel-body"> -->
<!-- 										<div style="display: none" id="login-alert" class="alert alert-danger col-sm-12"></div> -->
<%-- 										<form id="loginform" class="form-horizontal" role="form"> --%>
<!-- 											<div style="margin-bottom: 25px" class="input-group"> -->
<!-- 												<span class="input-group-addon"> <i class="fa fa-envelope"></i></span> -->
<!-- 												<input id="login-username" type="text" class="form-control" name="username" value="" placeholder="email"> -->
<!-- 											</div> -->
						
<!-- 											<div style="margin-bottom: 25px" class="input-group"> -->
<!-- 												<span class="input-group-addon"><i class="fa fa-lock"></i></span> -->
<!-- 												<input id="login-password" type="password" class="form-control" name="password" placeholder="password"> -->
<!-- 											</div> -->
<!-- 											<div style="margin-top: 10px" class="form-group"> -->
<!-- 												<div class="col-sm-12 controls"> -->
<!-- 													<a id="btn-login" href="#" class="btn btn-success">Login </a> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 											<div class="form-group"> -->
<!-- 												<div class="col-md-12 control"> -->
<!-- 													<div style="border-top: 1px solid #888; padding-top: 15px; font-size: 85%"> -->
<!-- 														Don't have an account!  -->
<!-- 														<a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()"> Sign Up Here </a> -->
<!-- 													</div> -->
<!-- 												</div> -->
<!-- 											</div> -->
<%-- 										</form> --%>
<!-- 										<div class="input-group"> -->
<!-- 		                                	<a href="https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=252473431161-0314f3l13do6kb34r7v0squmk604fjda.apps.googleusercontent.com&redirect_uri=http://localhost:8080/oauth2callback&scope=https://www.googleapis.com/auth/glass.timeline+https://www.googleapis.com/auth/userinfo.profile&state=amit.agrawal@sjsu.edu&approval_prompt=force&access_type=offline">Click here</a> to authorize your Glass here..                         -->
<!-- 		                               	</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							
<!-- 							Sign up -->
<!-- 							<div id="signupbox" style="display: none;" class="mainbox col-lg-8 col-sm-10""> -->
<!-- 								<div class="panel panel-info"> -->
<!-- 									<div class="panel-heading"> -->
<!-- 										<div class="panel-title">Sign Up</div> -->
<!-- 										<div style="float: right; font-size: 85%; position: relative; top: -10px"> -->
<!-- 											<a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign In</a> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="panel-body"> -->
<%-- 										<form id="signupform" class="form-horizontal" role="form"> --%>
<!-- 											<div id="signupalert" style="display: none" class="alert alert-danger"> -->
<!-- 												<p>Error:</p> -->
<!-- 												<span></span> -->
<!-- 											</div> -->
<!-- 											<div class="form-group"> -->
<!-- 												<label for="email" class="col-md-3 control-label">Email</label> -->
<!-- 												<div class="col-md-9"> -->
<!-- 													<input type="text" class="form-control" name="email" placeholder="Email Address"> -->
<!-- 												</div> -->
<!-- 											</div> -->
						
<!-- 											<div class="form-group"> -->
<!-- 												<label for="firstname" class="col-md-3 control-label">First Name</label> -->
<!-- 												<div class="col-md-9"> -->
<!-- 													<input type="text" class="form-control" name="firstname" placeholder="First Name"> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 											<div class="form-group"> -->
<!-- 												<label for="lastname" class="col-md-3 control-label">Last Name</label> -->
<!-- 												<div class="col-md-9"> -->
<!-- 													<input type="text" class="form-control" name="lastname" placeholder="Last Name"> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 											<div class="form-group"> -->
<!-- 												<label for="password" class="col-md-3 control-label">Password</label> -->
<!-- 												<div class="col-md-9"> -->
<!-- 													<input type="password" class="form-control" name="passwd" placeholder="Password"> -->
<!-- 												</div> -->
<!-- 											</div> -->
						
<!-- 											<div class="form-group"> -->
<!-- 												<div class="col-md-offset-3 col-md-9"> -->
<!-- 													<button id="btn-signup" type="button" class="btn btn-info"> -->
<!-- 														<i class="icon-hand-right"></i> &nbsp Sign Up -->
<!-- 													</button> -->
<!-- 												</div> -->
<!-- 											</div> -->
<%-- 										</form> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
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