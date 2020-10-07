<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.grm.model.*"%>

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
	href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/groomer.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>選擇美容師</title>
</head>
<body>
	<!-- 此處include首頁navigation bar -->
	<%--<jsp:include page="/header.html" flush="true"></jsp:include> --%>
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12" id="cancelBtn">
				<button id="back" class="btn btn-outline-secondary">回上一頁</button>
				<br><h4>請選擇美容師</h4>
			</div>
			<div class="col-12 col-md-12">
				<c:forEach var="grmVO" items="${list}" begin="0" end="9">
					<div class="row">
						<div class="card" style="width: 100%;">
							<div class="row">
								<div class="col-md-4 grmPic">
									<img class="card-img-top"
										src="<%=request.getContextPath()%>/grm/picReader?grmPic=${grmVO.groomerNo}"
										alt="Card image cap">
								</div>
								<div class="col-md-6">
									<div class="card-body">
										<h5 class="card-title gname">${grmVO.groomerName}</h5>
										<p class="card-text">${grmVO.groomerInfo}</p>
									</div>
								</div>
								<div class="col-md-2">
									<FORM method="post"
										action="<%=request.getContextPath()%>/grm/grm.do">
										<input type="hidden" name="action" value="selectGrm">
										<input type="hidden" name="groomerNo" value="${grmVO.groomerNo }"> 
										<input type="submit" class="btn btn-outline-info grmbtn" value="Select">
									</FORM>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<!-- 此處include footer -->
	<script src="<%=request.getContextPath()%>/js/util.js"></script>
</body>
</html>