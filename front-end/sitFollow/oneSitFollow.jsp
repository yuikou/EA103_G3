<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.member.model.*, com.petSitter.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�ڪ��̷R�O�itest</title>

<!-- �פJ�~��CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/SF.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<body>
    
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

		
		<!-- ���o�l�ܪ��O�i���|���s�� -->
		<%
        	String memNo = (String) session.getAttribute("memNo");
			SitFollowService sfSrv = new SitFollowService();
			
			String attr = "fa fa-heart-o";
			if (sfSrv.getAllByMemNo(memNo).contains("M001")) { 
				attr = "fa fa-heart";
			}
			pageContext.setAttribute("attr", attr);
        %>
        
		<input type="hidden" name="memNo" value="${memNo}">
		<input type="hidden" name="sitNo" value="S001">
		<div class="myClick" onClick="getFavor()">
			<span class="${attr}" style="color:red;"></span>
			<div class="ring"></div>
			<div class="ring2"></div>
			<p class="info">�[��̷R�o!</p>
		</div>
    </div>


<!-- �פJjs -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/favor.js"></script>
</body>
</html>