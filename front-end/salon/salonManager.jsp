<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salon.model.*"%>
<%@ page import="java.util.*"%>

<%
	SalonVO salonVO = (SalonVO) request.getAttribute("salonVO"); //EmpServlet.java(Concroller)
%>

<html>
<head>
<meta charset="UTF-8">
<title>美容業者管理頁面</title>
<style>
#table1 {
	border: 1px;
	border-style: solid;
	hight: 100px;
}
</style>
</head>
<body>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

	<table id="table1">		
		<input type="hidden" value="${salonVO.salNo}">
		<tr>
			<th>美容店店名</th>
			<th>負責人姓名</th>
			<th>連絡電話</th>
			<th>信箱</th>
			<th>地址</th>
			<th>營業開始時間</th>
			<th>營業結束時間</th>
			<th>匯款帳號</th>
			<th>銀行代碼</th>
			<th>營業執照</th>
			<th>可養寵物類型</th>
			<th>美容店簡介</th>
		</tr>
		<tr>
			<td>${salonVO.salName}</td>
			<td>${salonVO.salOwner}</td>
			<td>${salonVO.salPh}</td>
			<td>${salonVO.salMail}</td>
			<td>${salonVO.salAdr}</td>
			<td>${salonVO.salSTime}</td>
			<td>${salonVO.salETime}</td>
			<td>${salonVO.salRemit}</td>
			<td>${salonVO.bankCode}</td>
			<td>${salonVO.salCertif}</td>
			<td>${salonVO.salPetType}</td>
			<td>${salonVO.salInfo}</td>
			<td>
			<FORM method="post" action="<%=request.getContextPath()%>/front-end/salon/salon.do" name="form1">	
			<input type="submit" value="修改">
			<input type="hidden" name="salNo"  value="${salonVO.salNo}">
			<input type="hidden" name="action"	value="getOne_For_Update">
			</FORM>
			
			</td>

		</tr>
	</table>

</body>
</html>