<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>保姆的所有證照</TITLE>

<!-- 匯入外部CSS -->
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

<!-- 以下為首頁navigation bar -->
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
                <!-- 超連結連到各服務搜尋頁面 -->
                <ul class="navbar-nav ml-auto">
	                <div class="myUndeLine">
	                    <li class="nav-item">
                            <a class="nav-link accordion myFirst" href="#"><span class="ch-word">寵物服務</span><span
                                    class="en-word">Services</span></a>
                            <a class="nav-link morelink" href="#">托養</a>
                            <a class="nav-link morelink" href="#">美容</a>
                            <a class="nav-link morelink" href="#">領養</a>
                    	</li>
        	        </div>
                   	<div class="myUndeLine">
                    	<li class="nav-item">
                            <a class="nav-link accordion myFirst" href="#"><span class="ch-word">寵友互動</span><span
                                    class="en-word">Interaction</span></a>
                            <a class="nav-link morelink" href="#">討論區</a>
                            <a class="nav-link morelink" href="#">揪團</a>
                    	</li>
                    </div>
                    <div class="myUndeLine">
                    	<li class="nav-item">
                            <a class="nav-link accordion" href="#"><span class="ch-word">美容業者專區</span><span
                                    class="en-word">Salon store</span></a>
                    	</li>
                    </div>
                </ul>
                <!-- 以上超連結連到各服務搜尋頁面 -->
                <!-- 登入按鈕 -->
                <input type="button" class="myBtn" value="sign in"></input>
                <!-- 以上登入btn -->
            </div>
        </div>
    </nav>
    <!-- 首頁navigation bar end-->
    
    
<!-- 內文body -->
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
	
		<!-- 測試用 -->
		<div class="test" style="margin:40px;left:400px;">
			<a href="addSitLic.jsp">新增證書</a>
		</div>
		<!-- 測試用 -->
		
		<div class="backFromDB rwd-table">
			<table class="licTable">
				<tr>
					<th style="min-width:auto;">NO.</th>
					<th>證照名稱</th>
					<th>證照到期日</th>
					<th>證照狀態</th>
					<th>證照圖片</th>
					<th>修改<span class="warrning">*</span></th>
				</tr>
				<% 
					String sitNo = (String) session.getAttribute("sitNo");
					SitLicService slSvc = new SitLicService();
					List<SitLicVO> list = slSvc.getSitAllLic(sitNo);
					pageContext.setAttribute("list", list);
				%>
				<c:forEach var="sitLic" items="${list}" varStatus="no">
				<tr>
					<td data-th="no">${no.count}</td>
					<td data-th="證照名稱">${sitLic.licName}</td>
					<td data-th="證照到期日"><c:out value="${sitLic.licEXP}" default="-"/></td>
					<c:set var="licStatus" value="${sitLic.licStatus}"/>
					<% 
						String[] statusArr = {"審核中", "審核通過", "審核未通過", "過期證照"};
						String status = statusArr[(Integer)pageContext.getAttribute("licStatus")];
						pageContext.setAttribute("status", status);
					%>
					<td data-th="證照狀態">${status}</td>
					<td data-th="證照圖片" class="myLicPic"><img class="licPicImg" alt="" src="${pageContext.request.contextPath}/ShowImg?action=sitLic&licNo=${sitLic.licNo}"></td>
					<td data-th="修改">
						<FORM method="post" action="sitLic.do">
							<input class="updBtn" type="submit" value="修改">
							<input type="hidden" name="licNo" value="${sitLic.licNo}">
							<input type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<!-- 內文end -->
    <!-- 客服小按鈕 -->
    <div class="qaicon">
        <a href="#"><img src="https://dzmg8959fhe1k.cloudfront.net/all/unicorn.svg" /></a>
    </div>
    <!-- footer -->
    <div class="container-fluid main-footer text-center">
        <div class="row myFooterContainer">
            <div class="col-12 col-sm">
                <ul>About us
                    <li><a href="#" class="myFooterLink">服務條款</a></li>
                    <li><a href="#" class="myFooterLink">隱私權政策</a></li>
                </ul>
            </div>
            <div class="col-12 col-sm">
                <ul>FAQ
                    <li><a href="#" class="myFooterLink">常見問題</a></li>
                    <li><a href="#" class="myFooterLink">意見回饋</a></li>
                </ul>
            </div>
            <div class="col-12 col-sm">
                <ul>Contact us
                    <li><a href="#" class="myFooterLink">發送電子郵件</a></li>
                    <li><a href="#" class="myFooterLink">追蹤我們</a></li>
                </ul>
            </div>
        </div>
        <p class="copyright">&copy; copyright by <i>NiHaiZaiMa</i>
    </div>
</body>
</html>