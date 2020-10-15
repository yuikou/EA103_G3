<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Petfect Match ==美容業者專區==</title>
</head>

<body bgcolor="azure">

	<table id="table-1">
		<tr>
			<td>
				<h3>Salon Page</h3>
				<h4>(MVC)</h4>
			</td>
		</tr>
	</table>
	<p>美容店業者專區</p>
	<h3> 歡迎:<font color=red> ${salno} </font>您好</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<h3>美容師管理</h3>
	
	<ul>
		<li><a href="<%=request.getContextPath()%>/back-end/grm/listAllGrm.jsp">List</a>
			all Grms.<br></li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/grm/addGrm.jsp'>Add</a>
			a new Grm.</li>
	</ul>

	<h3>美容店鋪管理</h3>
	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back-end/salsev/listAllSalSev.jsp'>List</a>
			所有服務項目 <br></li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/salsev/addSalSev.jsp'>新增</a>
			服務項目</li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/salalb/listAllbyAlb.jsp'>List</a>
			照片</li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/grmoff/listAllOff.jsp'>List</a>
			本店不可服務時間</li>
	</ul>

</body>
</html>