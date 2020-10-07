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
<title>��s�A�ȶ���</title>
</head>
<body>
	<!-- ���Bincludeheader -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<td><span> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">�^����</a>
						</span>
							<h4>��s�A�ȶ���</h4></td>
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
			<div class="col-12 col-md-12">
				<div class="table-responsive">
					<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/salsev/salsev.do">
						<table class="table table-borderless">
							<tr>
								<th scope="col" class="w-150">���e���ؽs��:</th>
								<td><%=salvVO.getSalsevno()%></td>
							</tr>
							<tr>
								<th scope="col" class="w-150">���e���ئW��:</th>
								<td>
									<select name="salsevname" required>
									<option value="��~�����e" <%=("��~�����e".equals(salvVO.getSalsevname()))?"selected":"" %> >��~�����e</option>
									<option value="��¦���e" <%=("��¦���e".equals(salvVO.getSalsevname()))?"selected":"" %>>��¦���e</option>
									<option value="��o���e" <%=("��o���e".equals(salvVO.getSalsevname()))?"selected":"" %>>��o���e</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="col" class="w-150">���e���ؤ��e:</th>
								<td><input type="text" name="salsevinfo"
									value="<%=salvVO.getSalSevInfo()%>" class="gw" required></td>
							</tr>
							<tr>
								<th scope="col" class="w-150">�i�A���d������:</th>
								<td><select name="petcat" required>
									<option value="0" <%=(0 == salvVO.getPetcat())? "selected":"" %>>��L</option>
									<option value="1" <%=(1 == salvVO.getPetcat())? "selected":"" %>>�u���</option>
									<option value="2" <%=(2 == salvVO.getPetcat())? "selected":"" %>>�����</option>
									<option value="3" <%=(3 == salvVO.getPetcat())? "selected":"" %>>�p����(1-5 kg)</option>
									<option value="4" <%=(4 == salvVO.getPetcat())? "selected":"" %>>������(5-10 kg)</option>
									<option value="5" <%=(5 == salvVO.getPetcat())? "selected":"" %>>�j����(10 kg�H�W)</option>
								</select></td>
							</tr>
							<tr>
								<th scope="col" class="w-150">�w�p��O�ɶ�:</th>
								<td><select name="salsevtime" required>
									<option value="1" <%=(1 == salvVO.getSalsevtime())? "selected":"" %>>1</option>
									<option value="2" <%=(2 == salvVO.getSalsevtime())? "selected":"" %>>2</option>
									<option value="3" <%=(3 == salvVO.getSalsevtime())? "selected":"" %>>3</option>
									<option value="4" <%=(4 == salvVO.getSalsevtime())? "selected":"" %>>4</option>
									<option value="5" <%=(5 == salvVO.getSalsevtime())? "selected":"" %>>5</option>
								</select>�p��</td>
							</tr>
							<tr>
								<th scope="row">����:</th>
								<td><input type="number" name="salsevpr"
									value="<%=salvVO.getSalsevpr()%>" class="salsevpr" required></td>
							</tr>
						</table>
						<div class="addSetting">
							<input type="hidden" name="status" value="0">
							<input type="hidden" name="salsevno"
								value="<%=salvVO.getSalsevno()%>"> <input type="submit"
								value="�e�X��s" class="btn btn-outline-success"> <input
								type="hidden" name="action" value="update">
						</div>
					</FORM>
					<div id="cancelBtn">
						<button id="back" class="btn btn-outline-secondary">����</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ���Binclude footer  -->
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
	<script src="<%=request.getContextPath()%>/js/util.js"></script>
	<script type="text/javascript">
		window.onload = init;
	</script>
</body>
</html>