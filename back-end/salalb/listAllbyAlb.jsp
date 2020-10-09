<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.salonAlbum.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	SalonAlbService salb = new SalonAlbService();
String salno = (session.getAttribute("salno")).toString();
List<SalonAlbVO> list = salb.getOneSalAlb(salno);
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="zh-Hant">
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
	href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/groomer.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>Document</title>
</head>
<body>
<!-- 此處include header -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12">
				<table>
					<tr>
						<td><span class="myset"> <a	href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a>
						</span>
							<h4>美容店作品集管理</h4></td>
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
				<a href="<%=request.getContextPath()%>/back-end/salalb/addAlbPic.jsp">新增相片</a>
			</div>
			<div class="col-12 col-md-12">
				<div class="row">
					<c:forEach var="picVO" items="${list}" begin="0" end="20">
						<div class="card sabCard" style="width: 18rem;">
							<img
								src="<%=request.getContextPath()%>/grm/PicReader?action=salAlbNo&salAlbNo=${picVO.salAlbNo}"
								alt="picture for salon" name="salAlbNo" class="card-img-top">
							<div class="card-body mydBtn">
								<form method="post"
									action="<%=request.getContextPath()%>/salalb/salalb.do">
									<input type="submit" value="刪除" class="btn btn-outline-danger">
									<input type="hidden" name="salAlbNo" value="${picVO.salAlbNo}">
									<input type="hidden" name="action" value="delete">
								</form>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
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
</body>
</html>