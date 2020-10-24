<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.salonOrder.model.*,java.util.*,com.member.model.*,com.salonOrderDetail.model.*,com.salsev.model.*"%>

<%
	SalonOrderDetailService salOrderDetailSvc = new SalonOrderDetailService();
	String salOrderNo =  (String)(request.getAttribute("salOrderNo"));
	List<SalonOrderDetailVO> list = salOrderDetailSvc.getAllBySalOrderNo(salOrderNo); //必須匯入java.util.*才可以使用
	pageContext.setAttribute("list", list);	
%>

<jsp:useBean id="salsevSvc" scope="page"
	class="com.salsev.model.SalVService" />



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
<%@ include file="/front-end/header.jsp"%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<span><h1>美容明細</h1></span>
	<div>
		<a href="<%=request.getContextPath()%>/front-end/salon/index.jsp">回首頁</a>
	</div>
	<table>
		<tr>
			<th>訂單編號</th>
			<th>美容項目編號</th>
			<th>美容項目</th>
			<th>美容師編號</th>
			<th>美容師姓名</th>
			<th>價格</th>
			<th>預約服務時間</th>
			

		</tr>
		<%@ include file="/front-end/page1.file"%>
		<c:forEach var="salonOrderDetailVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${salonOrderDetailVO.salOrderNo}</td>
				<td>${salonOrderDetailVO.salSevNo}</td>
				<td></td>
				<td>${salonOrderDetailVO.groomerNo}</td>
				<td></td>
				<td>${salonOrderDetailVO.salSevPr}</td>
				<td></td>


			</tr>
		</c:forEach>		
	</table>
	<%@ include file="/front-end/page2.file"%>
<%@ include file="/front-end/footer.jsp"%>
</body>
</html>