<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salonAlbum.model.*"%>
<%
	SalonAlbVO salbvo = (SalonAlbVO) request.getAttribute("salbvo");
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- �פJ�~��CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/salalb.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>Insert title here</title>
</head>
<body>

	<!-- ���Binclude header -->

	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<td><span> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">�^����</a>
						</span>
							<button id="back" class="btn btn-outline-secondary">�^�W�@��</button>
							<h4>�s�W���e���ۤ�</h4></td>
					</tr>
				</table>

				<%-- ���~��C --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">�Эץ��H�U���~:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<div class="col-md-12">
				<form method="post"
					action="<%=request.getContextPath()%>/salalb/salalb.do"
					enctype="multipart/form-data">
					<div id="main">
						<label>�п�ܹϤ���:</label> 
						<input type="file" id="myFile" name="salpic" multiple>
					</div>
					<input type="hidden" name="action" value="insert"> 
					<input type="submit" value="�W��" class="btn btn-outline-success">
				</form>
				<div class="btnset">
					<label>�w���ۤ�: </label>
					<div id="preview" class="preview"></div>
				</div>

			</div>
			<div class="col-md-12">
				<div class="btnset">
					<button id="remove" class="btn btn-outline-secondary">�R��</button>
					<button id="removeAll" class="btn btn-outline-secondary">�R������</button>
				</div>
			</div>
		</div>
	</div>
	<!-- ���Binclude footer  -->
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
		integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/js/util.js"></script>
	<script src="<%=request.getContextPath()%>/js/salPicUp.js"></script>
	<script type="text/javascript">
		window.onload = init;
	</script>
</body>
</html>