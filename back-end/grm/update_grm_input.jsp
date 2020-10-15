<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.grm.model.*"%>

<%
	GrmVO grmVO = (GrmVO) request.getAttribute("grmVO");
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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Petfect Match ==���e�M��== ��s���e�v���</title>
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
							<h4>��s���e�v���</h4></td>
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
					<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/grm/grm.do" enctype="multipart/form-data">
						<table class="table table-borderless">
							<tr>
								<th scope="col" class="gw">���e�v�s��:</th>
								<td><%=grmVO.getGroomerNo()%></td>
							</tr>
							<tr>
								<th scope="col" class="gw">���e�v�Ӥ�:</th>
								<td id="preview"><img
									src="<%=request.getContextPath()%>/grm/picReader?grmPic=${grmVO.groomerNo}"
									id="gPic"></td>
								<td><input type="file" id="myFile" name="grmPic"
									value="�ק�Ӥ�" multiple></td>
							</tr>
							<tr>
								<th scope="col" class="gw">���e�v�ʺ�:</th>
								<td><input type="TEXT" name="gname" size="15"
									value="<%=grmVO.getGroomerName()%>" /></td>
							</tr>
							<tr>
								<th scope="col" class="gw">���e�v²��:</th>
								<td><input type="TEXT" name="ginfo" maxlength="100"
									value="<%=grmVO.getGroomerInfo()%>" /></td>
							</tr>
						</table>
						<div class="addSetting">
							<input type="hidden" name="isDelete" value="0"> <input
								type="hidden" name="grmno" value="<%=grmVO.getGroomerNo()%>">
							<input type="submit" value="�e�X��s" class="btn btn-outline-success">
							<input type="hidden" name="action" value="update">
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
	<script src="<%=request.getContextPath()%>/js/grmPicUp.js"></script>
	<script src="<%=request.getContextPath()%>/js/util.js"></script>
	<script type="text/javascript">
		window.onload = init;
	</script>
</body>
</html>