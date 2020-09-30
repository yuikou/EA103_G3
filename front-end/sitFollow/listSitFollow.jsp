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

<title>�ڪ��̷R�O�i</title>

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

<!-- �H�U������navigation bar -->
    <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light myNav">
        <div class="container-fluid">
            <!-- logo (click to home page)-->
            <a class="navbar-brand" href="#" style="padding-top: 0px;padding-bottom: 0px"><img class="myLogo"
                    src="https://dzmg8959fhe1k.cloudfront.net/all/logo.jpg"><img class="myLogoWord"
                    src="https://dzmg8959fhe1k.cloudfront.net/all/logoWord.jpg"></a>
            <!-- logo -->
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation"
                style="margin-right: 15px">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="myNavDiv collapse navbar-collapse" id="navbarText">
                <!-- �W�s���s��U�A�ȷj�M���� -->
                <ul class="navbar-nav ml-auto">
	                <div class="myUndeLine">
	                    <li class="nav-item">
                            <a class="nav-link accordion myFirst" href="#"><span class="ch-word">�d���A��</span><span
                                    class="en-word">Services</span></a>
                            <a class="nav-link morelink" href="#">���i</a>
                            <a class="nav-link morelink" href="#">���e</a>
                            <a class="nav-link morelink" href="#">��i</a>
                    	</li>
        	        </div>
                   	<div class="myUndeLine">
                    	<li class="nav-item">
                            <a class="nav-link accordion myFirst" href="#"><span class="ch-word">�d�ͤ���</span><span
                                    class="en-word">Interaction</span></a>
                            <a class="nav-14link morelink" href="#">�Q�װ�</a>
                            <a class="nav-link morelink" href="#">����</a>
                    	</li>
                    </div>
                    <div class="myUndeLine">
                    	<li class="nav-item">
                            <a class="nav-link accordion" href="#"><span class="ch-word">���e�~�̱M��</span><span
                                    class="en-word">Salon store</span></a>
                    	</li>
                    </div>
                </ul>
                <!-- �H�W�W�s���s��U�A�ȷj�M���� -->
                <!-- �n�J���s -->
                <input type="button" class="myBtn" value="sign in"></input>
                <!-- �H�W�n�Jbtn -->
            </div>
        </div>
    </nav>
    <!-- ����navigation bar end-->

<!-- ����body -->
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
		
		<!-- ���ե���wmemNo=M001 -->
		<% session.setAttribute("memNo","M001");%>
		<!-- ���o�l�ܪ��O�i���|���s�� -->
		<%
        	String memNo = (String) session.getAttribute("memNo");
        	
			SitFollowService sfSvc = new  SitFollowService();
        	List<String> memlist = sfSvc.getAllByMemNo(memNo);
        	pageContext.setAttribute("memlist", memlist);
        %>
        
        <div class="row" style="clear:both;">
            <h1 class="main-title myH1">�l��/���òM��</h1>
            <div class="section-line"></div>
        </div>
        <ul class="nav nav-tabs">
            <!-- <li class="nav-item">
                <a class="nav-link active" href="#">�̷R���O�i</a>
            </li> -->
            <li class="nav-item">
                <a class="nav-link myFavorites" href="#">�̷R���O�i</a>
            </li>
            <li class="nav-item">
                <a class="nav-link myFavorites" href="#">�̷R�����e��</a>
            </li>
            <li class="nav-item">
                <a class="nav-link myFavorites" href="#">�Q��i���d��</a>
            </li>
            <li class="nav-item">
                <a class="nav-link myFavorites" href="#">���w���K��</a>
            </li>
            <!-- <li class="nav-item">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li> -->
        </ul>
        
		<!-- ���Ы�Svc -->
        <jsp:useBean id="memSvc" class="com.member.model.MemService"></jsp:useBean>
<%--         <jsp:useBean id="sitSvc" class="com.petSitter.model.petSitService"></jsp:useBean> --%>
        <jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"></jsp:useBean>
        
        
        <div class="row myRow">
        
        <c:forEach var="mem" items="${memlist}">
        
			<div class="col-6 col-md-3 col-sm-4 card">
            	<img src="${pageContext.request.contextPath}/ShowImg?action=sitFollow&memNo=${mem}" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${memSvc.getOneMem(mem).memName}</h5>
                    <p class="card-text">
                        <small class="text-muted">
						<c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(sitSvc.getOneByFK(mem).sitNo)}">
							���ѡG<c:out value="${sitSrvVO.sitSrvCode} "/>
						</c:forEach>                    
                        </small>
                        <a href="#" class="card-link"><img class="right-array" src="${path}/img/right-arrow.svg"></a>
                    </p>
                </div>
            </div>
            
        </c:forEach>
        </div>
        
<!--         <div class="cutePage"> -->
<!--             <a href="#"><img src="025-bull terrier.png" class="page" alt="..."></a> -->
<!--             <a href="#"><img src="026-turkish pointer.png" class="page" alt="..."></a> -->
<!--             <a href="#"><img src="028-bichon frise.png" class="page" alt="..."></a> -->
<!--         </div> -->
    </div>
    <!-- ����end -->

<!-- �ȪA�p���s -->
    <div class="qaicon">
        <a href="#"><img src="https://dzmg8959fhe1k.cloudfront.net/all/unicorn.svg" /></a>
    </div>
    <!-- footer -->
    <div class="container-fluid main-footer text-center">
        <div class="row myFooterContainer">
            <div class="col-12 col-sm">
                <ul>About us
                    <li><a href="#" class="myFooterLink">�A�ȱ���</a></li>
                    <li><a href="#" class="myFooterLink">���p�v�F��</a></li>
                </ul>
            </div>
            <div class="col-12 col-sm">
                <ul>FAQ
                    <li><a href="#" class="myFooterLink">�`�����D</a></li>
                    <li><a href="#" class="myFooterLink">�N���^�X</a></li>
                </ul>
            </div>
            <div class="col-12 col-sm">
                <ul>Contact us
                    <li><a href="#" class="myFooterLink">�o�e�q�l�l��</a></li>
                    <li><a href="#" class="myFooterLink">�l�ܧڭ�</a></li>
                </ul>
            </div>
        </div>
        <p class="copyright">&copy; copyright by <i>NiHaiZaiMa</i>
    </div>
	<script src="${path}/js/jquery-3.2.1.min.js"></script>
	<script src="${path}/js/popper.js"></script>
	<script src="${path}/js/bootstrap.min.js"></script>
	<script src="${path}/js/favor.js"></script>
</body>
</html>