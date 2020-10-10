<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>�O�i���Ҧ��ҷ�</TITLE>

<!-- �פJ�~��CSS -->
<c:set var="path" value="/EA103G3/front-end" />

<link rel="stylesheet" type="text/css" href="${path}/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${path}/css/Main.css">
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${path}/css/animate.css">
<link rel="stylesheet" type="text/css" href="${path}/css/animsition.min.css">
<link rel="stylesheet" type="text/css" href="${path}/css/util.css">
<link rel="stylesheet" type="text/css" href="${path}/css/Petfect.css">
<link rel="stylesheet" type="text/css" href="${path}/css/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${path}/css/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY>

<!-------------------- nav -------------------->
	<jsp:include page="../nav.jsp"/>
    
<!------------------ ����body ------------------>
    <div class="container">
		
		<!-- ���~�C�� -->
	   	<div class="errorList"> 
			<c:if test="${not empty errorMsgs}" >
				<font style="color:red;">�o�ͥH�U���~�G</font>
				<ul>
				<c:forEach var="msg" items="${errorMsgs}">
					<li style="color:red;">${msg}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>
	
		<!-- ���ե� -->
		<div class="test" style="margin:40px;left:400px;">
			<a href="addSitLic.jsp">�s�W�Ү�</a>
		</div>
		<!-- ���ե� -->
		
		<div class="backFromDB rwd-table">
			<table class="licTable">
				<tr>
					<th style="min-width:auto;">NO.</th>
					<th>�ҷӦW��</th>
					<th>�ҷӨ����</th>
					<th>�ҷӪ��A</th>
					<th>�ҷӹϤ�</th>
					<th>�ק�<span class="warrning">*</span></th>
				</tr>
				
				<c:forEach var="sitLic" items="${list}" varStatus="no">
				<tr>
					<td data-th="no">${no.count}</td>
					<td data-th="�ҷӦW��">${sitLic.licName}</td>
					<td data-th="�ҷӨ����"><c:out value="${sitLic.licEXP}" default="-"/></td>
					<c:set var="licStatus" value="${sitLic.licStatus}"/>
					<% 
						String[] statusArr = {"�f�֤�", "�f�ֳq�L", "�f�֥��q�L", "�L���ҷ�"};
						String status = statusArr[(Integer)pageContext.getAttribute("licStatus")];
						pageContext.setAttribute("status", status);
					%>
					<td data-th="�ҷӪ��A">${status}</td>
					<td data-th="�ҷӹϤ�" class="myLicPic"><img class="licPicImg" alt="" src="${pageContext.request.contextPath}/ShowImg?action=sitLic&licNo=${sitLic.licNo}"></td>
					<td data-th="�ק�">
						<FORM method="post" action="sitLic.do">
							<input class="updBtn" type="submit" value="�ק�">
							<input type="hidden" name="licNo" value="${sitLic.licNo}">
							<input type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>
    
</body>
</html>