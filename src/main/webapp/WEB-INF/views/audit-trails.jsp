<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recognize.Me! - Audit Trails</title>
<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript"
	src="resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/index.js"></script>
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="resources/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/index.css">
</head>
<body>
	<!-- #wrapper -->
	<div id="wrapper">
		<div class="overlay"></div>
		<%@include file="/WEB-INF/views/header.jsp"%>

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<button type="button" class="hamburger is-closed"
				data-toggle="offcanvas">
				<span class="hamb-top"></span> <span class="hamb-middle"></span> <span
					class="hamb-bottom"></span>
			</button>

			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-1">
						<h3>Recognize.me</h3>
						<hr>
						<div id="show-audit-trails" style="margin-top: 20px;">
							<h4>Get All Brands</h4>
							<div>
								<table id="ads-table" class="table table-striped" style="margin-top: 30px;">
									<thead>
										<tr>
											<th>Type</th>
											<th>User</th>
											<th>Latitude</th>
											<th>Longitude</th>
											<th>File Size</th>
											<th>Matched</th>
											<th>Brand</th>
											<th>Ad Found</th>
											<th>Ad Text</th>
											<th>Get Credential</th>
											<th>Ad Posted</th>
											<th>Upload Start Time</th>
											<th>Upload End Time</th>
											<th>Match Start Time</th>
											<th>Match End Time</th>
											<th>Ad Start Time</th>
											<th>Ad End Time</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="trails" items="${auditTrails}">
											<tr>
												<td><c:out value="${trails.type}"></c:out></td>
												<td><c:out value="${trails.email}"></c:out></td>
												<td><c:out value="${trails.latitude}"></c:out></td>
												<td><c:out value="${trails.longitude}"></c:out></td>
												<td><c:out value="${trails.fileSize}"></c:out></td>
												<td><c:out value="${trails.matched}"></c:out></td>
												<td><c:out value="${trails.brand}"></c:out></td>
												<td><c:out value="${trails.adFound}"></c:out></td>
												<td><c:out value="${trails.adText}"></c:out></td>
												<td><c:out value="${trails.getCredential}"></c:out></td>
												<td><c:out value="${trails.adPosted}"></c:out></td>
												<td><c:out value="${trails.startTimeUpload}"></c:out></td>
												<td><c:out value="${trails.endTimeUpload}"></c:out></td>
												<td><c:out value="${trails.startTimeMatch}"></c:out></td>
												<td><c:out value="${trails.endTimeMatch}"></c:out></td>
												<td><c:out value="${trails.startTimeAd}"></c:out></td>
												<td><c:out value="${trails.endtimeAd}"></c:out></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="wrapper">
				<!-- /#page-content-wrapper -->
				<%@include file="/WEB-INF/views/footer.jsp"%>
				<!-- /#wrapper -->
			</div>
</body>
</html>