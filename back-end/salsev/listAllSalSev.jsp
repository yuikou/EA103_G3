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
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- �פJ�~��CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/groomer.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>list �A�ȶ���</title>
</head>
<body>
	<!--�ݭninclude����navigation bar -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12">
				<table>
					<tr>
						<td><span class="myset"> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">�^����</a>
						</span>
							<h4>�Ҧ��A�ȶ���</h4></td>
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
			<div class="addgrm">
				<a
					href="<%=request.getContextPath()%>/back-end/salsev/addSalSev.jsp">�s�W�A�ȶ���</a>
				<!--�ݭninclude �s�W�A�ȶ��ح��� -->
			</div>
			<div class="col-12 col-md-12">
				<div class="table-responsive">
					<table class="table listAll">
						<thead>
							<tr>
								<th scope="col" class="gw">���e���ؽs��</th>
								<th scope="col" class="gw">���e���ئW��</th>
								<th scope="col" class="w-200">���e���ؤ��e</th>
								<th scope="col" class="w-150">�i�A���d������</th>
								<th scope="col" class="w-150">�w�p��O�ɶ�</th>
								<th scope="col" class="gw">����</th>
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
										String[] arr = { "��L", "�u���", "�����", "�p����(1-5 kg)", "������(5-10 kg)", "�j����(10 kg�H�W)" };
									String showText = arr[(Integer) pageContext.getAttribute("petcat")];
									pageContext.setAttribute("showText", showText);
									%>
									<td class="w-150" id="petcat">${showText}</td>
									<td class="w-150">${salvVO.salsevtime}</td>
									<td>${salvVO.salsevpr}</td>
									<td class="mySVBtn">
										<form method="post"
											action="<%=request.getContextPath()%>/salsev/salsev.do">
											<input type="submit" value="��s" class="btn btn-outline-info">
											<input type="hidden" name="salsevno"
												value="${salvVO.salsevno}"> <input type="hidden"
												name="action" value="getOne_For_Update">
										</form>
									</td>
									<td class="mySVBtn">
										<form method="post"
											action="<%=request.getContextPath()%>/salsev/salsev.do">
											<input type="submit" value="�R��"
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
	<!-- ���Binclude footer -->

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