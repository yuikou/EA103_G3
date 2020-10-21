<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.sitOffDay.model.*, com.sitSrv.model.*, com.member.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>���˰e�Xreq�d�߫O�i</title>

<!-- �פJ�~��CSS -->
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/SF.css">

</head>
<body>

    
<!------------------ ����body ------------------>
	<div class="container">
		
		<!-- ���շ|���w�n�J�æs�isession�imemNo=M001�j �A
		�I�i�Y�O�i�����ɥ�req(����session)���o�isitNo=S005(M010)�j -->
		
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
		
		<!-- ���ե� -->
		<div class="test">
			<a href="/EA103G3/front-end/sitFollow/oneSitFollow.jsp">�I��XX�O�i���ӤH����(sitFollow)</a>sitNo=${petSitterVO.sitNo} testMemNo=${memNo}<br>
			<a href="/EA103G3/front-end/sitOffDay/showOneSrvDay.jsp">�I��XX�O�i���ӤH����(sitOffDay)</a><br>
			<a href="/EA103G3/front-end/sitSrv/showOneSitAllSrv.jsp">�I��XX�O�i���O�i����(sitSrv)</a><br>
			<a href="/EA103G3/front-end/sitFollow/listSitFollow.jsp">�d�ݩҦ��l�ܫO�i</a>by DAO<br>
			<a href="/EA103G3/sitFollow/sitFollow.do?action=showAll">�d�ݩҦ��l�ܫO�i</a>by Session
		</div>
		<!-- ���ե� -->
	</div>
</body>
</html>