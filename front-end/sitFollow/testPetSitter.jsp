<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, com.member.model.*, java.util.*" %>
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
		<% 
			session.setAttribute("memNo","M001");
			petSitterVO.setSitNo("S005");
			petSitterVO.setMemNo("M005");
		%>
		
		<!-- ���ե� -->
		<div class="test">
			<a href="oneSitFollow.jsp">�I��XX�O�i���ӤH����</a>sitNo=${petSitterVO.sitNo} testMemNo=${memNo}<br>
			<a href="listSitFollow.jsp">�d�ݩҦ��l�ܫO�i</a>by DAO<br>
			<a href="sitFollow.do?action=showAll">�d�ݩҦ��l�ܫO�i</a>by Session
		</div>
		<!-- ���ե� -->
	</div>
</body>
</html>