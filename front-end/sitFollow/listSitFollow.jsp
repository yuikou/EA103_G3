<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitFollow.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�ڪ��̷R�O�i</title>

<!-- �פJ�~��CSS -->
<c:set var="path" value="${pageContext.request.contextPath}/front-end" />
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/SF.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<body>

<!-------------------- nav -------------------->
	<jsp:include page="/front-end/header.jsp"/>
    
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
		
		<!-- ���o�l�ܪ��O�i���|���s�� -->
		<% String memNo = (String) session.getAttribute("memNo"); %>
        
		<!-- ���Ы�Svc -->
        <jsp:useBean id="memSvc" class="com.member.model.MemService"></jsp:useBean>
        <jsp:useBean id="petSitterSvc" class="com.petSitter.model.PetSitterService"></jsp:useBean>
        <jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"></jsp:useBean>
        <jsp:useBean id="sfSvc" class="com.sitFollow.model.SitFollowService"></jsp:useBean>
        
        <div class="row myRow">
        
        <c:forEach var="mem" items="${sfSvc.getAllByMemNo(memNo)}">
        
			<div class="col-6 col-md-3 col-sm-4 card">
            	<img src="${pageContext.request.contextPath}/PicReader.do?action=getMemPic&memNo=${mem}" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${memSvc.getOneMem(mem).memName}</h5>
                    <p class="card-text">
                        <small class="text-muted">
						<c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterSvc.getByFK(mem).sitNo)}">
							<c:if test="${sitSrvVO.sitSrvCode == 'Boarding'}">
							<img class="srvImg" src="${path}/img/pet-house.svg">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'DayCare'}">
							<img class="srvImg" src="${path}/img/pet-milk.svg">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'DropIn'}">
							<img class="srvImg" src="${path}/img/pet-food.svg">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'DogWalking'}">
							<img class="srvImg" src="${path}/img/walking-the-dog.svg">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'PetTaxi'}">
							<img class="srvImg" src="${path}/img/pet-taxi.svg">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'Bathing'}">
							<img class="srvImg" src="${path}/img/pet-bath.svg">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'Pickup'}">
							<img class="srvImg" src="${path}/img/pet-google.svg">
							</c:if>
						</c:forEach>                    
                        </small>
                        <a href="#" class="card-link"><img class="right-array" src="${path}/img/right-arrow.svg"></a>
                    </p>
                </div>
            </div>
        </c:forEach>
        </div>
        <div class="row" style="margin-top: 3%;">
	        <small class="srvImgInfo">���ѪA��  </small>
			<img class="srvImg" src="${path}/img/pet-house.svg">�d���H�i 
			<img class="srvImg" src="${path}/img/pet-milk.svg">�d���馫 
			<img class="srvImg" src="${path}/img/pet-food.svg">�쩲���@ 
			<img class="srvImg" src="${path}/img/walking-the-dog.svg">���� 
			<img class="srvImg" src="${path}/img/pet-taxi.svg">�d���p�{�� 
			<img class="srvImg" src="${path}/img/pet-bath.svg">�[�ʬ~�� 
			<img class="srvImg" src="${path}/img/pet-google.svg">�[�ʱ��e
		</div>
        
<!--         <div class="cutePage"> -->
<!--             <a href="#"><img src="025-bull terrier.png" class="page" alt="..."></a> -->
<!--             <a href="#"><img src="026-turkish pointer.png" class="page" alt="..."></a> -->
<!--             <a href="#"><img src="028-bichon frise.png" class="page" alt="..."></a> -->
<!--         </div> -->

    </div>

<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>

<!-- �פJjs -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/favor.js"></script>
</body>
</html>