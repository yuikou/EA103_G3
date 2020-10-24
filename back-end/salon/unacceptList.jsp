<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.salon.model.*"%>

<%
	SalonService salSvc = new SalonService();
	List<SalonVO> list = salSvc.getAllUnaccept();
	pageContext.setAttribute("list", list);
%>

<html>
<head>
<title>待審核美容店</title>

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
	width: 1000px;
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
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>待審核美容店</h3>
				<h4>
					<a href=""><img src="" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
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

	<table>
		<tr>
			<th>美容店編號</th>
			<th>店名</th>
			<th>負責人</th>
			<th>電話</th>
			<th>電子郵件</th>
			<th>地址</th>
			<th>大頭貼</th>
			<th>營業執照</th>
			<th>帳號狀態</th>
			<th>通過</th>
			<th>不通過</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="salonVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${salonVO.salNo}</td>
				<td>${salonVO.salName}</td>
				<td>${salonVO.salOwner}</td>
				<td>${salonVO.salPh}</td>
				<td>${salonVO.salMail}</td>
				<td>${salonVO.salCity}${salonVO.salDist}${salonVO.salAdr}</td>
				<td><img alt="" src="${pageContext.request.contextPath}/front-end/salon/ShowPic.do?action=salPic&salNo=${salonVO.salNo}"></td>
				<td><img alt="" src="${pageContext.request.contextPath}/front-end/salon/ShowPic.do?action=salCertif&salNo=${salonVO.salNo}"></td>
				<td>${salonVO.salStatus}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back-end/salon/salon.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="通過"> 
						<input type="hidden" name="salNo" value="${salonVO.salNo}"> 
						<input type="hidden" name="salStatus" value="${salonVO.salStatus}">
						<input type="hidden" name="action" value="approve">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back-end/salon/salon.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="不通過"> 
						<input type="hidden" name="salNo" value="${salonVO.salNo}"> 
						<input type="hidden" name="salStatus" value="${salonVO.salStatus}">
						<input type="hidden" name="action" value="notApprove">
					</FORM>
				</td>
			</tr>

		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>