<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.member.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>假裝送出req查詢保姆</title>

<!-- 匯入外部CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<link rel="stylesheet" type="text/css" href="${path}/css/SF.css">

</head>
<body>

    
<!------------------ 內文body ------------------>
	<div class="container">
		
		<!-- 測試會員已登入並存進session【memNo=M001】 ，
		點進某保姆頁面時用req(先用session)取得【sitNo=S005(M010)】 -->
		
		<jsp:useBean id="petSitterVO" scope="session" class="com.petSitter.model.PetSitterVO"/>
		<% 
			session.setAttribute("memNo","M001");
			petSitterVO.setSitNo("S005");
			petSitterVO.setMemNo("M005");
		%>
		
		<!-- 測試用 -->
		<div class="test">
			<a href="oneSitFollow.jsp">點擊XX保姆的個人頁面</a>sitNo=${petSitterVO.sitNo} testMemNo=${memNo}<br>
			<a href="listSitFollow.jsp">查看所有追蹤保姆</a>by DAO<br>
			<a href="sitFollow.do?action=showAll">查看所有追蹤保姆</a>by Session
		</div>
		<!-- 測試用 -->
	</div>
</body>
</html>