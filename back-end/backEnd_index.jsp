<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Petfect Match ==���e�~�̱M��==</title>
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
	<p>���e���~�̱M��</p>
	<h3> �w��:<font color=red> ${salno} </font>�z�n</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<h3>���e�v�޲z</h3>
	
	<ul>
		<li><a href="<%=request.getContextPath()%>/back-end/grm/listAllGrm.jsp">List</a>
			all Grms.<br></li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/grm/addGrm.jsp'>Add</a>
			a new Grm.</li>
	</ul>

	<h3>���e���Q�޲z</h3>
	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back-end/salsev/listAllSalSev.jsp'>List</a>
			�Ҧ��A�ȶ��� <br></li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/salsev/addSalSev.jsp'>�s�W</a>
			�A�ȶ���</li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/salalb/listAllbyAlb.jsp'>List</a>
			�Ӥ�</li>
	</ul>
	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/grmoff/listAllOff.jsp'>List</a>
			�������i�A�Ȯɶ�</li>
	</ul>

</body>
</html>