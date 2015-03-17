<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recognize.Me! - Add Brand</title>
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
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
							<div id="add-brand" style="margin-top: 20px;">
					            <h4>Add a new brand</h4>
			<%-- 		            <form:form method="POST" action="uploadImage"  enctype="multipart/form-data"> --%>
								<form class="form-horizontal" style="margin-top: 30px;" id="addBrandForm">
								  <div class="form-group">
								    <label for="brandName" class="col-sm-2 control-label">Brand Name</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" id="brandName" placeholder="Brand Name">
								    </div>
								  </div>
								  <div class="form-group">
								    <label for="url" class="col-sm-2 control-label">URL</label>
								    <div class="col-sm-6">
								      <input type="url" class="form-control" id="url" placeholder="URL">
								    </div>
								  </div>
								  <div class="form-group">
								    <label for="path" class="col-sm-2 control-label">Storage Path</label>
								    <div class="col-sm-6">
								      <input type="url" class="form-control" id="path" placeholder="Path" value="/tmp" disabled>
								    </div>
								  </div>
								  <div class="form-group">
								    <label for="category" class="col-sm-2 control-label">Category</label>
								    <div class="col-sm-4">
								      <select class="form-control" id="category">
								      	<option>Education</option>
								      	<option>Engineering</option>
								      	<option>E-Commerce</option>
								      	<option>Manufacturing</option>
								      	<option>IT/ Consulting</option>
								      	<option>Medical</option>
								      	<option>Finance</option>
								      	<option>Health Care</option>
								      	<option>Film/ Media</option>
								      </select>
								    </div>
								  </div>
								  <div class="form-group">
								    <label for="brandFiles" class="col-sm-2 control-label">Files</label>
								    <div class="col-sm-8">
								    	<input type="file" id="brandFiles">
								    	<p class="help-block">Choose the file to be used for learning the brand..</p>
								    </div>
								  </div>
								  <div class="form-group">
								    <div class="col-sm-offset-2 col-sm-5">
								      <button type="submit" class="btn btn-info">Add Brand</button>
								    </div>
								  </div>
								</form>
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