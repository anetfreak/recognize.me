<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recognize.Me! - Audit Trails</title>
<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/index.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="resources/js/trails.js"></script>
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">
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
					<div class="col-lg-11 col-lg-offset-1">
						<h3>Recognize.me</h3>
						<hr>
						<div id="show-audit-trails" style="margin: 20px 0px 50px 0px;">
							<h4>Audit Trails</h4>
							<div  style="margin: 20px 0px 20px 0px;">
								<table id="ads-table" class="table table-responsive table-striped table-bordered table-condensed hover row-border" style="margin-top: 30px; width: 100%;">
									<thead>
										<tr>
											<th>User</th>
											<th>Latitude</th>
											<th>Longitude</th>
											<th>File Size (Kb)</th>
											<th>Matched?</th>
											<th>Brand</th>
											<th>Ad Found?</th>
											<th>Ad Text</th>
											<th>Authenticated?</th>
											<th>Ad Posted?</th>
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
												<td><c:out value="${trails.email}"/></td>
												<td><c:out value="${trails.latitude}"/></td>
												<td><c:out value="${trails.longitude}"/></td>
												<td><c:out value="${trails.fileSize}"/></td>
												<td><c:out value="${trails.matched}"/></td>
												<td><c:out value="${trails.brand}"/></td>
												<td><c:out value="${trails.adFound}"/></td>
												<td><c:out value="${trails.adText}"/></td>
												<td><c:out value="${trails.getCredential}"/></td>
												<td><c:out value="${trails.adPosted}"/></td>
												<td><fmt:formatDate pattern="MM-dd-yyyy hh:mm:ss" value="${trails.startTimeUpload}"/></td>
												<td><fmt:formatDate pattern="MM-dd-yyyy hh:mm:ss" value="${trails.endTimeUpload}"/></td>
												<td><fmt:formatDate pattern="MM-dd-yyyy hh:mm:ss" value="${trails.startTimeMatch}"/></td>
												<td><fmt:formatDate pattern="MM-dd-yyyy hh:mm:ss" value="${trails.endTimeMatch}"/></td>
												<td><fmt:formatDate pattern="MM-dd-yyyy hh:mm:ss" value="${trails.startTimeAd}"/></td>
												<td><fmt:formatDate pattern="MM-dd-yyyy hh:mm:ss" value="${trails.endtimeAd}" /></td>
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