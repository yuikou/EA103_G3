<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head>
<title>Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/main.css">
<link rel="stylesheet" type="text/css"
	href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/animate.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/animsition.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/util.css">
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/photo/favicon.ico">
</head>

<body>
	<%@ include file="/salonHeader.jsp"%>
	<div class="limiter">

		<div class="container-login100 cover">
			<div class="wrap-login100 mybody">
				<form method="post"
					action="<%=request.getContextPath()%>/front-end/salon/salon.do"
					class="login100-form validate-form p-l-55 p-r-55 p-t-178">
					<span class="login100-form-title"> Hi Groomer ♥ Log in to
						continue </span>

					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red"></font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>

					<div class="wrap-input100 validate-input m-b-16"
						data-validate="請輸入帳號">
						<input class="input100" type="text" name="salAc" placeholder="帳號">
						<span class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入密碼">
						<input class="input100" type="password" name="salPw"
							placeholder="密碼"> <span class="focus-input100"></span>
					</div>
					<div class="text-right p-t-13 p-b-23">
						<span class="txt1"> 忘記 </span> <a href="#" class="txt2"> 帳號 /
							密碼? </a>
					</div>
					<div class="container-login100-form-btn">
						<button class="login100-form-btn">Sign in</button>
						<input type="hidden" name="action" value="login">
					</div>

					<div class="flex-col-c p-t-40 p-b-40">
						<span class="txt1 p-b-9"> 還沒有帳號? </span> <a href="salonregi.jsp"
							class="txt3"> 註冊新帳戶 </a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/Dave/jquery-3.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/animsition.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/popper.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/main.js"></script>
	<%@ include file="/salonFooter.jsp"%>
</body>

</html>