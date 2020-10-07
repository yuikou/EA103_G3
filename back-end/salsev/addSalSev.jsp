<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salsev.model.*"%>

<%
	SalsevVO salvVO = (SalsevVO) request.getAttribute("salvVO");
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
	href="<%=request.getContextPath()%>/css/groomer.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>�s�W�A�ȶ���</title>
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
							<h4>�s�W�A�ȶ���</h4></td>
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
				<div class="table-responsive">
					<form method="post"
						action="<%=request.getContextPath()%>/salsev/salsev.do">
						<table class="table table-borderless table-sm">
							<tr>
								<th scope="row">���e���ئW��:</th>
								<td><select name="salsevname" required>
									<option value="��~�����e">��~�����e</option>
									<option value="��¦���e">��¦���e</option>
									<option value="��o���e">��o���e</option>
								</select></td>
							</tr>
							<tr>
								<th scope="row">���e���ؤ��e:</th>
								<td><input type="text" name="salsevinfo" size="100"
									class="gInfo" required /></td>
							</tr>
							<tr>
								<th scope="row">�i�A���d������:</th>
								<td><select name="petcat" required>
									<option value="0">��L</option>
									<option value="1">�u���</option>
									<option value="2">�����</option>
									<option value="3">�p����(1-5 kg)</option>
									<option value="4">������(5-10 kg)</option>
									<option value="5">�j����(10 kg�H�W)</option>
								</select></td>
							</tr>
							<tr>
								<th scope="row">���e���عw�p��O�ɶ�:</th>
								<td>
								<select name="salsevtime" required>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
								</select>�p��</td>
							</tr>
							<tr>
								<th scope="row">����:</th>
								<td><input type="number" name="salsevpr" class="salsevpr" required></td>
							</tr>
						</table>
						<div class="addSetting">
							<input type="hidden" name="status" value="0"> 
							<input type="submit" value="�s�W" class="btn btn-outline-success">
							<input type="hidden" name="action" value="insert">
						</div>
					</form>
					<div id="cancelBtn">
						<button id="back" class="btn btn-outline-secondary">����</button>
					</div>
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
	<script type="text/javascript">
		window.onload = init;
	</script>
</body>
</html>