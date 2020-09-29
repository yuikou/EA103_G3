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

<TITLE>審核證照</TITLE>

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

<BODY style="background-color:#fff !important">

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

		<div class="backFromDB rwd-table">
		<table class="licTable vrfTable">
				<tr>
					<th style="min-width:auto;">NO.</th>
					<th>證照編號</th>
					<th>證照名稱</th>
					<th>證照到期日</th>
					<th>證照狀態</th>
					<th>證照圖片</th>
					<th>審核結果</th>
				</tr>
				<% 
					SitLicService slSvc = new SitLicService();
					List<SitLicVO> list = slSvc.getUnverifiedLic(0);
					pageContext.setAttribute("list", list);
				%>
				<c:forEach var="sitLic" items="${list}" varStatus="no">
					<tr>
						<td>${no.count}</td>
						<td>${sitLic.licNo}</td>
						<td>${sitLic.licName}</td>
						<td><c:out value="${sitLic.licEXP}" default="-"/></td>
						<td>${sitLic.licStatus}</td>
						<c:set var="licNo" value="${sitLic.licNo}"/>
						<td class="myLicPic"><img class="licPicImg" alt="" src="${pageContext.request.contextPath}/ShowImg?action=sitLic&licNo=${licNo}"></td>
						<td>
							<FORM method="post" action="sitLic.do">
									<input class="vrfBtn" type="submit" value="通過">
									<input type="hidden" name="licNo" value="${sitLic.licNo}">
									<input type="hidden" name="licStatus" value=1>
									<input type="hidden" name="action" value="verify">
							</FORM>
							<FORM method="post" action="sitLic.do">
									<input class="vrfBtn" type="submit" value="不通過">
									<input type="hidden" name="licNo" value="${sitLic.licNo}">
									<input type="hidden" name="licStatus" value=2>
									<input type="hidden" name="action" value="verify">
							</FORM>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>