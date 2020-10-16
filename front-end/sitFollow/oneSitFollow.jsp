<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.member.model.*, com.petSitter.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>我的最愛保姆test</title>

<!-- 匯入外部CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="/EA103G3/front-end/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/SF.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<body>

<!-------------------- nav -------------------->
	<jsp:include page="../nav.jsp"/>
    
<!------------------ 內文body ------------------>
    <div class="container">
		
		<!-- 錯誤列表 -->
	   	<div class="errorList"> 
			<c:if test="${not empty errorMsgs}" >
				<font style="color:red;">發生以下錯誤：</font>
				<ul>
				<c:forEach var="msg" items="${errorMsgs}">
					<li style="color:red;">${msg}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>

		
		<!-- 取得追蹤的保姆的會員編號 -->
		<%
        	String memNo = (String) session.getAttribute("memNo");
			PetSitterVO petSitterVO = (PetSitterVO) session.getAttribute("petSitterVO");
			SitFollowService sfSrv = new SitFollowService();
			
			String attr = "fa fa-heart-o";
			if (sfSrv.getAllByMemNo(memNo).contains(petSitterVO.getMemNo())) { 
				attr = "fa fa-heart";
			}
			pageContext.setAttribute("attr", attr);
        %>
        
		<p style="text-align:center; padding-top:20px">測試：我是S005保姆</p>
		
		<input type="hidden" name="memNo" value="${memNo}">
		<input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
		<div class="myClick" onClick="getFavor()">
			<span class="${attr}" style="color:red;"></span>
			<div class="ring"></div>
			<div class="ring2"></div>
			<p class="info">加到最愛囉!</p>
		</div>
    </div>


<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>

<!-- 匯入js -->
	<c:set var="jsPath" value="/EA103G3/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/favor.js"></script>
</body>
</html>