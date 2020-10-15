<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salonF.model.*, com.salon.model.*, java.util.*"%>

<!-- �ϥ�memNo=M001 ����-->
<%
	session.setAttribute("memNo", "M003");
%>

<%
	String memNo = (session.getAttribute("memNo")).toString().trim();
SalFService salfSvc = new SalFService();
List<SalonFVO> mySalon = salfSvc.getAll(memNo);
pageContext.setAttribute("mySalon", mySalon);
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
	href="<%=request.getContextPath()%>/css/SF.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>�ڪ�����</title>

</head>

<body>
	<div class="container">
		<!-- ���~�C�� -->
		<div class="errorList">
			<c:if test="${not empty errorMsgs}">
				<font style="color: red;">�o�ͥH�U���~�G</font>
				<ul>
					<c:forEach var="msg" items="${errorMsgs}">
						<li style="color: red;">${msg}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
		<div class="row" style="clear: both;">
			<h1 class="main-title myH1">�l��/���òM��</h1>
			<div class="section-line"></div>
		</div>
		<ul class="nav nav-tabs">
			<!-- <li class="nav-item">
                <a class="nav-link active" href="#">�̷R���O�i</a>
            </li> -->
			<li class="nav-item"><a class="nav-link myFavorites" href="#">�̷R���O�i</a>
			</li>
			<li class="nav-item"><a class="nav-link myFavorites" href="#">�̷R�����e��</a>
			</li>
			<li class="nav-item"><a class="nav-link myFavorites" href="#">�Q��i���d��</a>
			</li>
			<li class="nav-item"><a class="nav-link myFavorites" href="#">���w���K��</a>
			</li>
			<!-- <li class="nav-item">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li> -->
		</ul>

		<!-- �o��nuseBean���Jsalon�W�r��ʺA���osalon page -->
		<jsp:useBean id="salonSvc" class="com.salon.model.SalonService"></jsp:useBean>
		<div class="row myRow">
			<c:forEach var="sal" items="${mySalon}">
				<div class="col-6 col-md-3 col-sm-4 card">
					<img
						src="<%=request.getContextPath()%>/grm/PicReader?action=salPic&salPic=${sal.salNo}"
						class="card-img-top" alt="my salon" style="width:100%; height:100%;">
					<div class="card-body">
						<c:set var="salNo" value="${sal.salNo }" />
						<h5 class="card-title">${salonSvc.getOneSalon(salNo).getSalName() }</h5>
						<p class="card-text">${salonSvc.getOneSalon(salNo).getSalCity() }</p>
						<small class="text-muted">${salonSvc.getOneSalon(salNo).getSalPetType() }</small>
						<a href="#" class="card-link"><i class="fa fa-arrow-right"></i>link
							to salon</a>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>
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
	<script src="<%=request.getContextPath()%>/js/favor.js"></script>
</body>
</html>