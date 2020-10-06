<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.member.model.*, com.petSitter.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�ڪ��̷R�O�itest</title>

<!-- �פJ�~��CSS -->
<c:set var="path" value="/EA103G3/front-end" />

<link rel="stylesheet" type="text/css" href="${path}/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${path}/css/Main.css">
<link rel="stylesheet" type="text/css" href="${path}/css/Petfect.css">
<link rel="stylesheet" type="text/css" href="${path}/css/SF.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<body>

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

		
		<!-- ���o�l�ܪ��O�i���|���s�� -->
		<%
        	String memNo = (String) session.getAttribute("memNo");
			PetSitterVO petSitterVO = (PetSitterVO) session.getAttribute("petSitterVO");
			SitFollowService sfSrv = new SitFollowService();
			
			System.out.println(petSitterVO.getMemNo());
			String attr = "fa fa-heart-o";
			if (sfSrv.getAllByMemNo(memNo).contains(petSitterVO.getMemNo())) { 
				attr = "fa fa-heart";
			}
			pageContext.setAttribute("attr", attr);
        %>
        
		<p style="text-align:center; padding-top:20px">���աG�ڬOS005�O�i</p>
		
		<input type="hidden" name="memNo" value="${memNo}">
		<input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
		<div class="myClick" onClick="getFavor()">
			<span class="${attr}" style="color:red;"></span>
			<div class="ring"></div>
			<div class="ring2"></div>
			<p class="info">�[��̷R�o!</p>
		</div>
    </div>


<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>

<!-- �פJjs -->
	<script src="${path}/js/jquery-3.2.1.min.js"></script>
	<script src="${path}/js/popper.js"></script>
	<script src="${path}/js/bootstrap.min.js"></script>
	<script src="${path}/js/favor.js"></script>
</body>
</html>