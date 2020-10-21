<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.sitOffDay.model.*, com.sitSrv.model.*, com.member.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>假裝送出req查詢保姆</title>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/SF.css">

</head>
<body>

    
<!------------------ 內文body ------------------>
	<div class="container">
		
		<!-- 測試會員已登入並存進session【memNo=M001】 ，
		點進某保姆頁面時用req(先用session)取得【sitNo=S005(M010)】 -->
		
		<jsp:useBean id="petSitterVO" scope="session" class="com.petSitter.model.PetSitterVO"/>
		<jsp:useBean id="sitSrvVO" scope="session" class="com.sitSrv.model.SitSrvVO"/>
		<jsp:useBean id="petSitterSvc" class="com.petSitter.model.PetSitterService"></jsp:useBean>
		<% 
			session.setAttribute("memNo","M014");
			petSitterVO = petSitterSvc.getByFK("M014");
			session.setAttribute("petSitterVO", petSitterVO);
// 			sitSrvVO.setSitSrvNo("SS024");
// 			sitSrvVO.setSitSrvCode("PetTaxi");
		%>
		
		<!-- 測試用 -->
		<div class="test">
			<a href="/EA103G3/front-end/sitFollow/oneSitFollow.jsp">點擊XX保姆的個人頁面(sitFollow)</a>sitNo=${petSitterVO.sitNo} testMemNo=${memNo}<br>
			<a href="/EA103G3/front-end/sitOffDay/showOneSrvDay.jsp">點擊XX保姆的個人頁面(sitOffDay)</a><br>
			<a href="/EA103G3/front-end/sitSrv/showOneSitAllSrv.jsp">點擊XX保姆的保姆頁面(sitSrv)</a><br>
			<a href="/EA103G3/front-end/sitFollow/listSitFollow.jsp">查看所有追蹤保姆</a>by DAO<br>
			<a href="/EA103G3/sitFollow/sitFollow.do?action=showAll">查看所有追蹤保姆</a>by Session
		</div>
		<!-- 測試用 -->
	</div>
</body>
</html>