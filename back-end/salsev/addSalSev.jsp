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
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>新增服務項目</title>
</head>
<body>
	<!-- 此處include header -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<td><span> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a>
						</span>
							<h4>新增服務項目</h4></td>
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
			<div class="col-md-12">
				<div class="table-responsive">
					<form method="post"
						action="<%=request.getContextPath()%>/salsev/salsev.do">
						<table class="table table-borderless table-sm">
							<tr>
								<th scope="row">美容項目名稱:</th>
								<td><select name="salsevname" id="salsevname" required>
									<option value="單品項美容">單品項美容</option>
									<option value="基礎美容">基礎美容</option>
									<option value="精緻美容">精緻美容</option>
								</select></td>
							</tr>
							<tr>
								<th scope="row">美容項目內容:</th>
								<td><input type="text" name="salsevinfo" size="100"
									class="gInfo" id="salsevInfo" placeholder="剪指甲/特殊處理(拆結、除蟲除蚤、皮膚調理...)等..." required /></td>
							</tr>
							<tr>
								<th scope="row">可服務寵物類型:</th>
								<td><select name="petcat" required>
									<option value="0">其他</option>
									<option value="1">短毛貓</option>
									<option value="2">長毛貓</option>
									<option value="3">小型犬(1-5 kg)</option>
									<option value="4">中型犬(5-10 kg)</option>
									<option value="5">大型犬(10 kg以上)</option>
								</select></td>
							</tr>
							<tr>
								<th scope="row">美容項目預計花費時間:</th>
								<td>
								<select name="salsevtime" required>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
								</select>小時</td>
							</tr>
							<tr>
								<th scope="row">價格:</th>
								<td><input type="number" name="salsevpr" class="salsevpr" required></td>
							</tr>
						</table>
						<div class="addSetting">
							<input type="hidden" name="status" value="0"> 
							<input type="submit" value="新增" class="btn btn-outline-success">
							<input type="hidden" name="action" value="insert">
						</div>
					</form>
					<div id="cancelBtn">
						<button id="back" class="btn btn-outline-secondary">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 此處include footer  -->
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
	<script src="<%=request.getContextPath()%>/js/liz/util.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/liz/salsev.js"></script>
	

</body>
</html>