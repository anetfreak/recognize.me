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
							<h4>Upload File</h4>
							<div id="upload-div" style="margin-top: 20px;">
								<form:form class="form-group" method="POST" action="uploadImage"  enctype="multipart/form-data">
									<div class="col-sm-6">
							        	<input class="form-control" type="file" name="file" />
							        </div>
							        <div class="col-sm-3">
							        	<input class="form-control btn btn-info" type="submit" value="Upload" />
							        </div>
							        <form:errors path="file" cssStyle="color: #ff0000;" />
							    </form:form>
							</div>
							
						</div>
					</div>
					<div class="row">
						<div class="col-lg-8 col-lg-offset-1">
							<div id="upload-image-div" style="margin-top: 20px;">
								<c:forEach var="myimg" items="${imageUrlList}">
									<form:form class="form-group" method="POST" action="uploadImage"  enctype="multipart/form-data">
	<!-- 									<table>
										<tbody>
										<tr>
											<td> -->
											<div class="col-sm-3">
	 											<%-- <img src="/userimages/${myimg}" width="100%" height="100px"> --%>
	 											<input type="image" src="${myimg}" name="image" width="100%" height="100px">
	 											<input style="display: none;" class="form-control" type="file" name="file" value="${myimg}" filename="abc.jpg"/>
	 											<input style="display: none;" class="form-control" type="file" name="email" filename="myemail@gmail.com"/>
	 											<input style="display: none;" class="form-control" value="678" type="file" name="latitude" />
	 											<input style="display: none;" class="form-control" value="352653" type="file" name="longitude" />
	<!-- 									  	</td>
										  	<td> -->
	 											<input class="form-control btn btn-info" type="submit" value="Upload" width="10px" />
	 											</div>
	<!--  										</td>
	 									</tr>
	 									</tbody>
	 									</table>
	 									<hr> -->
	 									<form:errors path="file" cssStyle="color: #ff0000;" />
								    </form:form>
								</c:forEach>
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