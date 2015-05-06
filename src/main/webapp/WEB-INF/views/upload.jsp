<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me! - Upload</title>
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<script type="text/javascript" src="resources/js/upload.js"></script>
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
						<div class="col-lg-11 col-lg-offset-1">
							<h3>Recognize.me</h3>
							<hr>
							<h4>Sample Upload Request Test Flow</h4>
							<p>Please select an image below to test a sample application flow</p>
<!-- 							<div id="upload-div" style="margin-top: 20px;"> -->
<%-- 								<form:form class="form-group" method="POST" action="uploadImage"  enctype="multipart/form-data"> --%>
<!-- 									<div class="col-sm-6"> -->
<!-- 							        	<input class="form-control" type="file" name="file" /> -->
<!-- 							        </div> -->
<!-- 							        <div class="col-sm-3"> -->
<!-- 							        	<input class="form-control btn btn-info" type="submit" value="Upload" /> -->
<!-- 							        </div> -->
<%-- 							        <form:errors path="file" cssStyle="color: #ff0000;" /> --%>
<%-- 							    </form:form> --%>
<!-- 							</div> -->
						</div>
					</div>
					<div class="row" id="input-div">
						<div class="col-lg-11 col-lg-offset-1">
							<div id="upload-image-div" style="margin-top: 20px;">
								<c:forEach var="myimg" items="${imageUrlList}">
									<div class="col-sm-2" style="margin: 20px;">
										<input id="${myimg}" class="test-image" type="image" src="/resources/test-images/${myimg}" name="image" width="200px" height="200px">
										<input style="display: none;" class="form-control" type="file" name="file" value="${myimg}" filename="abc.jpg"/>
										<input style="display: none;" class="form-control" type="file" name="email" filename="myemail@gmail.com"/>
										<input style="display: none;" class="form-control" value="678" type="file" name="latitude" />
										<input style="display: none;" class="form-control" value="352653" type="file" name="longitude" />
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					
					
					<div class="row" id="result-div" style="display:none;">
						<div class="col-lg-11 col-lg-offset-1">
							<div id="image-result-div" style="margin-top: 20px;">
								<div><p class="text-success"><strong>Image for uploaded successfully!</strong> Below are the results</p></div>
								<div>
									<div><span><strong>Image Uploaded - </strong></span><span id="filename"></span></div>
									<div><span><strong>Identified Brand - </strong></span><span id="brand"></span></div>
									<div><span><strong>Advertisement Found? - </strong></span> <span id="adFound"></span></div>
									<div><span><strong>Advertisement Content - </strong></span><span id="adText"></span></div>
								</div>
								<div style="margin-top: 20px;"><p>To try with another image, <a id="showImagesLink" href="#">Click here</a></p></div>
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