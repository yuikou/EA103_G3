<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.grm.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	GrmService grmSvc = new GrmService();
String salno = (session.getAttribute("salno")).toString();
List<GrmVO> list = grmSvc.getGrm(salno);
pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>Petfect Match ==美容專區== 美容師管理</title>
</head>
<body>
	<!-- 以下為首頁navigation bar -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light bg-light myNav">
		<div class="container-fluid">
			<!-- logo (click to home page)-->
			<a class="navbar-brand" href="#"
				style="padding-top: 0px; padding-bottom: 0px"><img
				class="myLogo"
				src="https://dzmg8959fhe1k.cloudfront.net/all/logo.jpg"><img
				class="myLogoWord"
				src="https://dzmg8959fhe1k.cloudfront.net/all/logoWord.jpg"></a>
			<!-- 漢堡選單 -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarText" aria-controls="navbarText"
				aria-expanded="false" aria-label="Toggle navigation"
				style="margin-right: 15px">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="myNavDiv collapse navbar-collapse" id="navbarText">
				<!-- 文字選單(超連結各服務搜尋頁面) -->
				<ul class="navbar-nav ml-auto">
					<div class="myUnderLine">
						<li class="nav-item"><a class="nav-link accordion myFirst"
							href="#"><span class="ch-word">寵物服務</span><span
								class="en-word">Services</span></a> <a class="nav-link moreLink"
							href="#">托養</a> <a class="nav-link moreLink" href="#">美容</a> <a
							class="nav-link moreLink" href="#">領養</a></li>
					</div>
					<div class="myUnderLine">
						<li class="nav-item"><a class="nav-link accordion myFirst"
							href="#"><span class="ch-word">寵友互動</span><span
								class="en-word">Interaction</span></a> <a class="nav-link moreLink"
							href="#">討論區</a> <a class="nav-link moreLink" href="#">揪團</a></li>
					</div>
					<div class="myUnderLine">
						<li class="nav-item"><a class="nav-link accordion" href="#"><span
								class="ch-word">美容業者專區</span><span class="en-word">Salon
									store</span></a></li>
					</div>
				</ul>
				<!-- 以上文字選單(超連結各服務搜尋頁面) -->
			</div>
		</div>
	</nav>
	<!-- 首頁navigation bar end-->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12">
				<table>
					<tr>
						<td><span class="myset"> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a>
						</span>
							<h4>所有美容師資料</h4></td>
					</tr>
				</table>
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<div class="addgrm">
				<a href="<%=request.getContextPath()%>/back-end/grm/addGrm.jsp">新增美容師</a>
			</div>
			<div class="col-12 col-md-12">
				<div class="table-responsive">
					<table class="table listAll">
						<thead>
							<tr>
								<th scope="col" class="gw">美容師編號</th>
								<th scope="col">美容師照片</th>
								<th scope="col" class="gw">美容師暱稱</th>
								<th scope="col">簡介</th>
								<th scope="col"></th>
							</tr>
						</thead>
						<%@ include file="/back-end/pages/page1.file"%>
						<c:forEach var="grmVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
							<tbody class="tbody">
								<tr>
									<th class="gno">${grmVO.groomerNo}</th>
									<td><img
										src="<%=request.getContextPath()%>//PicReader.do?action=grmPic&grmPic=${grmVO.groomerNo}"
										name="grmPic" class="showGPic"></td>
									<td>${grmVO.groomerName}</td>
									<td class="toLeft">${grmVO.groomerInfo}</td>
									<td>
										<form method="post"
											action="<%=request.getContextPath()%>/grm/grm.do">
											<input type="submit" value="修改" class="btn btn-outline-info">
											<input type="hidden" name="groomerNo"
												value="${grmVO.groomerNo}"> <input type="hidden"
												name="action" value="getOne_For_Update">
										</form>
									</td>
									<td><form method="post"
											action="<%=request.getContextPath()%>/grm/grm.do">
											<input type="submit" value="刪除"
												class="btn btn-outline-danger"> <input type="hidden"
												name="groomerNo" value="${grmVO.groomerNo}"> <input
												type="hidden" name="action" value="delete">
										</form></td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</div>
				<%@ include file="/back-end/pages/page2.file"%>
			</div>
		</div>
	</div>
	<!-- 客服小按鈕 -->
	<div class="qaicon">
		<a href="#"><img
			src="https://dzmg8959fhe1k.cloudfront.net/all/unicorn.svg" /></a>
	</div>
	<!-- footer -->
	<div class="container-fluid main-footer text-center">
		<div class="row myFooterContainer">
			<div class="col-12 col-sm">
				<ul>
					About us
					<li><a href="#" class="myFooterLink">服務條款</a></li>
					<li><a href="#" class="myFooterLink">隱私權政策</a></li>
				</ul>
			</div>
			<div class="col-12 col-sm">
				<ul>
					FAQ
					<li><a href="#" class="myFooterLink">常見問題</a></li>
					<li><a href="#" class="myFooterLink">意見回饋</a></li>
				</ul>
			</div>
			<div class="col-12 col-sm">
				<ul>
					Contact us
					<li><a href="#" class="myFooterLink">發送電子郵件</a></li>
					<li><a href="#" class="myFooterLink">追蹤我們</a></li>
				</ul>
			</div>
		</div>
		<p class="copyright">
			&copy; copyright by <i>NiHaiZaiMa</i>
		<p>
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
</body>
</html>