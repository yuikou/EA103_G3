<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.salonOrder.model.*,com.salon.model.*,java.util.*,com.member.model.*"%>

<%
	SalonOrderService salOrderSvc = new SalonOrderService();
	String salNo = ((SalonVO) session.getAttribute("salonVO")).getSalNo();
	List<SalonOrderVO> list = salOrderSvc.getAllBYSalNo(salNo); //必須匯入java.util.*才可以使用
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="memSvc" scope="page"
	class="com.member.model.MemService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>
</head>
<body>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<span><h1>美容訂單</h1></span>
	<div>
		<a href="<%=request.getContextPath()%>/front-end/salon/index.jsp">回首頁</a>
	</div>
	<table>
		<tr>
			<th>訂單編號</th>
			<th>會員編號</th>
			<th>會員名稱</th>
			<th>寵物編號</th>
			<th>訂單成立時間</th>
			<th>價錢</th>
			<th>明細<th>

		</tr>
		<%@ include file="/front-end/page1.file"%>
		<c:forEach var="salonOrderVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${salonOrderVO.salOrderNo}</td>
				<td>${salonOrderVO.memNo}</td>
				<td>
				<c:forEach var="memVO" items="${memSvc.all}">
						<c:if test="${salonOrderVO.memNo == memVO.memNo}">
							${memVO.memName}
						</c:if>
				</c:forEach>
				</td>
				<td>${salonOrderVO.petNo}</td>
				<td>${salonOrderVO.salOrderDate}</td>
				<td>${salonOrderVO.salTp}</td>
				<td>
				<form method ="post" action="<%=request.getContextPath()%>/front-end/salon/salonOrderDetail.do" name="form1">
				<input type="hidden" name = "action" value="getOrderDetail">
				<input type="hidden" name ="salOrderNo" value="${salonOrderVO.salOrderNo}">
				<button class="login100-form-btn" id="send">明細</button>
				</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/front-end/page2.file"%>

</body>
</html>