<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Dave/index.css">


<title>Nav</title>

</head>
<body>
	<!-- 以下為首頁navigation bar -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light bg-light myNav">
		<div class="container-fluid">
			<!-- logo (click to home page)-->
			<a class="navbar-brand" href="#"
				style="padding-top: 0px; padding-bottom: 0px"><img
				class="myLogo"
				src="https://dzmg8959fhe1k.cloudfront.net/all/logo.jpg"><img
				class="myLogoWord"
				src="https://dzmg8959fhe1k.cloudfront.net/all/logoWord.jpg"></a>
			<!-- logo -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarText" aria-controls="navbarText"
				aria-expanded="false" aria-label="Toggle navigation"
				style="margin-right: 15px">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="myNavDiv collapse navbar-collapse" id="navbarText">
				<!-- 超連結連到各服務搜尋頁面 -->
				<ul class="navbar-nav ml-auto">
					<!-- 					之後要刪掉放在會員的footer -->
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salon/listAllSalon.jsp"><span
								class="ch-word">美容業者專區</span><span class="en-word">Salon
									store</span></a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salonOrder/salonOrderList.jsp">
								<span class="ch-word">美容訂單管理</span> <span class="en-word">OrderManager</span>
						</a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salon/updateSalon.jsp">
								<span class="ch-word">會員資料修改</span> <span class="en-word">modifyInfo</span>
								<input type="hidden" name="salNo" id="salNo"
								value="${salonVO.salNo}">
						</a></li>
					</div>

					<div class="myUndeLine">
						<c:if test="${salonVO == null}">
							<li class="nav-item"><a class="nav-link accordion"
								href="salonregi.jsp"> <span class="ch-word">註冊新帳戶</span> <span
									class="en-word">newmember</span>
							</a></li>
						</c:if>
						<c:if test="${salonVO != null}">
						</c:if>
					</div>


				</ul>
				<!-- 以上超連結連到各服務搜尋頁面 -->
				<!-- 登入按鈕 -->
				<c:if test="${salonVO == null}">
					<a href="<%=request.getContextPath()%>/front-end/salon/Glogin.jsp">
						<input type="button" class="myBtn" id="login" value="登入">
					</a>
					<!-- 以上登入btn -->
				</c:if>
				<c:if test="${salonVO != null}">
					<form method="post"
						action="<%=request.getContextPath()%>/front-end/salon/salon.do">
						<input type="submit" class="myBtn" id="login" value="登出">
						<input type="hidden" name="action" value="logout"> <span>${salonVO.salName}
							你好</span>
					</form>
				</c:if>
				<!-- 以上登入btn -->
			</div>
		</div>
	</nav>
	<!-- 首頁navigation bar end-->
</body>
</html>