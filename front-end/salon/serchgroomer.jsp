<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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


	<jsp:useBean id="salSvc" scope="page"
		class="com.salon.model.SalonService" />


	<form method="post" action="<%=request.getContextPath()%>/front-end/salon/salon.do">
		<b>請選擇美容店編號</b> <select size="1" name="salNo">
			<c:forEach var="salonVO" items="${salSvc.all}">
				<option value="${salonVO.salNo}">${salonVO.salNo}
			</c:forEach>
		</select> <input type="hidden" name="action" value="getone_for_display">
		<input type="submit" value="送出">
	</form>

	<form method="post" action="<%=request.getContextPath()%>/front-end/salon/salon.do">
		<b>請選擇美容店店名</b> <select size="1" name="salNo">
			<option disabled selected>請選擇美容店</option>
			<c:forEach var="salonVO" items="${salSvc.all}">
				<option value="${salonVO.salName}">${salonVO.salName}
			</c:forEach>
		</select> <input type="hidden" name="action" value="update">	
		<input type="submit" value="送出">
	</form>


</body>
</html>