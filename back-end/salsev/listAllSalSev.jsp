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
<title>list 服務項目</title>
</head>
<body>
	<!--需要include首頁navigation bar -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12">
				<table>
					<tr>
						<td><span class="myset"> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a>
						</span>
							<h4>所有服務項目</h4></td>
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
				<a
					href="<%=request.getContextPath()%>/back-end/salsev/addSalSev.jsp">新增服務項目</a>
				<!--需要include 新增服務項目頁面 -->
			</div>
			<div class="col-12 col-md-12">
				<div class="table-responsive">
					<table class="table listAll">
						<thead>
							<tr>
								<th scope="col" class="gw">美容項目編號</th>
								<th scope="col" class="gw">美容項目名稱</th>
								<th scope="col" class="w-200">美容項目內容</th>
								<th scope="col" class="w-150">可服務寵物類型</th>
								<th scope="col" class="w-150">預計花費時間</th>
								<th scope="col" class="gw">價格</th>
							</tr>
						</thead>
						<%@ include file="/back-end/pages/page1.file"%>
						<c:forEach var="salvVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
							<tbody class="tbody">
								<tr>
									<th>${salvVO.salsevno}</th>
									<td>${salvVO.salsevname}</td>
									<td class="w-200">${salvVO.salSevInfo}</td>
									<c:set var="petcat" value="${salvVO.petcat}" />
									<%
										String[] arr = { "其他", "短毛貓", "長毛貓", "小型犬(1-5 kg)", "中型犬(5-10 kg)", "大型犬(10 kg以上)" };
									String showText = arr[(Integer) pageContext.getAttribute("petcat")];
									pageContext.setAttribute("showText", showText);
									%>
									<td class="w-150" id="petcat">${showText}</td>
									<td class="w-150">${salvVO.salsevtime}</td>
									<td>${salvVO.salsevpr}</td>
									<td class="mySVBtn">
										<form method="post"
											action="<%=request.getContextPath()%>/salsev/salsev.do">
											<input type="submit" value="更新" class="btn btn-outline-info">
											<input type="hidden" name="salsevno"
												value="${salvVO.salsevno}"> <input type="hidden"
												name="action" value="getOne_For_Update">
										</form>
									</td>
									<td class="mySVBtn">
										<form method="post"
											action="<%=request.getContextPath()%>/salsev/salsev.do">
											<input type="submit" value="刪除"
												class="btn btn-outline-danger"> <input type="hidden"
												name="salsevno" value="${salvVO.salsevno}"> <input
												type="hidden" name="action" value="delete">
										</form>
									</td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</div>
				<%@ include file="/back-end/pages/page2.file"%>
			</div>
		</div>
	</div>
	<!-- 此處include footer -->

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