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
<title>選擇服務項目</title>
</head>
<body>
	<!-- 此處include首頁navigation bar -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12" id="cancelBtn">
				<button id="back" class="btn btn-outline-secondary">回上一頁</button>
				<br>
				<h4>請選擇服務項目</h4>
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
									<p class="m-10">可服務寵物類型: </p>
									<p class="m-10">${salsevVO.petcat}</p>
									<p class="card-text m-10 salvtime">預計花費時間 ${salsevVO.salsevtime} 小時</p>
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
	<!-- 此處include footer -->
	<script src="<%=request.getContextPath()%>/js/util.js"></script>

</body>
</html>