<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.grm.model.*, com.goffday.model.*, java.util.*"%>
<%
	String salno = (session.getAttribute("salno")).toString();
GrmService grmSvc = new GrmService();
List<GrmVO> allGrm = grmSvc.getGrm(salno);
pageContext.setAttribute("allGrm", allGrm);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- �פJ�~��CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/datetimepicker/jquery.datetimepicker.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<script>var PATH = "<%=request.getContextPath()%>";</script>
<title>�s�W���i�A�Ȯɶ�</title>
</head>
<body>
	<!-- ���Binclude header -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<td><span> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">�^����</a>
						</span>
							<h4>�����s�W���i�A�Ȯɶ�</h4></td>
					</tr>
				</table>

				<%-- ���~��C --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">�Эץ��H�U���~:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<div class="col-md-12">
				<div class="table-responsive">
					<span>�п�ܭn�]�w�����e�v</span> 
					<select name="grmno" id="oneGrmOff" class="btn btn-outline-info">
						<option>�п��</option>
						<c:forEach var="grm" items="${allGrm }">
							<option value="${grm.groomerNo }">${grm.groomerName }</option>
						</c:forEach>
					</select> <span>�п�ܤ��:</span> <input name="offdate" id="f_date1" type="text" required >
					<a class="btn btn-success" style="height: 30px; line-height: 5px; margin-bottom: 10px;" onclick="grmSetOff()"><i
						class="fa fa-coffee fa-lg"></i> Take a break!</a>
						<button id="back" class="btn btn-outline-secondary" style="height: 30px; margin-bottom: 10px; line-height: 5px;">����</button>
				</div>
			</div>
		</div>
	</div>
	<!-- ���Binclude footer  -->
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
		integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
		crossorigin="anonymous"></script>
	<script	src="<%=request.getContextPath()%>/js/liz/jquery.datetimepicker.full.js"></script>
	<script src="<%=request.getContextPath()%>/js/liz/addHoliday.js"></script>
</body>
</html>