<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.salsev.model.*"%>

<%
	SalVService svSvc = new SalVService();
String salno = (session.getAttribute("salno")).toString();
List<SalsevVO> list = svSvc.getAll(salno);
pageContext.setAttribute("list", list);
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
<title>��ܪA�ȶ���</title>
</head>
<body>
	<!-- ���Binclude����navigation bar -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12" id="cancelBtn">
				<button id="back" class="btn btn-outline-secondary">�^�W�@��</button>
				<br>
				<h4>�п�ܪA�ȶ���</h4>
			</div>

			<c:forEach var="salsevVO" items="${list }" begin="0" end="9">
				<div class="col-12 col-md-12">
					<div class="row">
						<div class="card" style="width: 100%;">
							<div class="row">
								<div class="col-md-6">
									<div class="card-body">
										<h5 class="card-title gname">${salsevVO.salsevname }</h5>
										<p class="card-text">${salsevVO.salSevInfo }</p>
									</div>
								</div>
								<div class="col-md-4">
									<p class="m-10">�i�A���d������: </p>
									<p class="m-10">${salsevVO.petcat}</p>
									<p class="card-text m-10 salvtime">�w�p��O�ɶ� ${salsevVO.salsevtime} �p��</p>
								</div>
								<div class="col-md-2">
									<p class="sevpr">$ ${salsevVO.salsevpr}</p>
									<form method="post" action="<%=request.getContextPath()%>/salsev/salsev.do">
										<input type="hidden" name="action" value="selectSalV" /> 
										<input type="hidden" name="salsevNo" value="${salsevVO.salsevno }" />
										<input type="submit" class="btn btn-outline-info grmbtn" value="Select" />
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- ���Binclude footer -->
	<script src="<%=request.getContextPath()%>/js/util.js"></script>

</body>
</html>