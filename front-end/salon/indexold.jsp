<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salon.model.*,com.salonOrder.model.*"%>


<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
<title>Petfect Match</title>
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css" href="css/index.css">
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
	integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
	crossorigin="anonymous"></script>
<!-- AOS設定 -->
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
</head>

<body>
	<!-- 以下為首頁navigation bar -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light bg-light myNav">
		<div class="container-fluid">
			<!-- logo (click to home page)-->
			<a class="navbar-brand" href="#"
				style="padding-top: 0px; padding-bottom: 0px"><img
				class="mylogo" src="logo_v2.jpg"><img class="mylogoWord"
				src="logoWord.jpg"></a>
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
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion myFirst"
							href="#"><span class="ch-word">寵物服務</span><span
								class="en-word">Services</span></a> <a class="nav-link morelink"
							href="#">托養</a> <a class="nav-link morelink" href="#">美容</a> <a
							class="nav-link morelink" href="#">領養</a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion myFirst"
							href="#"><span class="ch-word">寵友互動</span><span
								class="en-word">Interaction</span></a> <a class="nav-link morelink"
							href="#">討論區</a> <a class="nav-link morelink" href="#">揪團</a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item">
							<form method="post"
								action="<%=request.getContextPath()%>/front-end/salon/salon.do"
								name="form1">
								<a class="nav-link accordion"
									href="javascript:document.form1.submit();"><span
									class="ch-word">美容業者專區</span><span class="en-word">Salon
										store</span></a> <input type="hidden" name="action" value="getALLSalon">
							</form>
						</li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salonOrder/salinOrderList.jsp">
								<span class="ch-word">美容訂單管理</span> <span class="en-word">OrderManager</span>
						</a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="updateSalon.jsp"> <span class="ch-word">會員資料修改</span> <span
								class="en-word">modifyInfo</span> <input type="hidden"
								name="salNo" id="salNo" value="${salonVO.salNo}">
						</a></li>
					</div>

				</ul>
				<!-- 以上超連結連到各服務搜尋頁面 -->
				<!-- 登入按鈕 -->
				<c:if test="${salonVO == null}">
					<a href="Glogin.jsp"> <input type="button" class="myBtn"
						id="login" value="登入"></a>
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

			</div>
		</div>
	</nav>
	<!-- 首頁navigation bar end-->
	<!-- 首頁輪播圖片,可放簡介跟優惠訊息跟廣告 -->
	<div class="banner myBanner">
		<div id="carouselExampleIndicators" class="carousel slide"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0"
					class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<div
						style='background: url("http://dzmg8959fhe1k.cloudfront.net/dog-unsplash.jpg") center fixed no-repeat;'
						class="d-block w-100 myImg"></div>
				</div>
				<div class="carousel-item">
					<div
						style='background: url("photo/dogGroomer.jpg") top fixed no-repeat;'
						class="d-block w-100 myImg"></div>
				</div>
				<div class="carousel-item">
					<div
						style='background: url("photo/waitCat.jpg") top fixed no-repeat;'
						class="d-block w-100 myImg"></div>
				</div>
			</div>
			<a class="carousel-control-prev myPrev"
				href="#carouselExampleIndicators" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="carousel-control-next myNext"
				href="#carouselExampleIndicators" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
		<!-- 腳印緩衝區 -->
		<div class="petfooter">
			<img class="pf1" src="footerprint.svg" width="50" height="50" /> <img
				class="pf2" src="footerprint.svg" width="50" height="50" /> <img
				class="pf3" src="footerprint.svg" width="50" height="50" />
		</div>
	</div>
	<!-- 首頁輪播圖片end -->
	<!-- 內文body -->
	<div class="container">
		<!-- 以下可link到各服務業面 -->
		<div class="row2" data-aos="fade-up" data-aos-delay="180">
			<div class="col-md-12">
				<h1 class="main-title myH1">寵物服務項目</h1>
				<div class="section-line"></div>
			</div>
			<div class="card" style="width: 19rem;">
				<img src="photo/dogCup.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">寵物托養</h5>
					<p class="card-text">Some quick example tex</p>
				</div>
			</div>
			<div class="card" style="width: 19rem;">
				<img src="photo/salonStore.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">寵物美容</h5>
					<p class="card-text">Some quick example tex</p>
				</div>
			</div>
			<div class="card" style="width: 19rem;">
				<img src="photo/shock.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">寵物領養</h5>
					<p class="card-text">Some quick example tex</p>
				</div>
			</div>
		</div>
		<!-- 以下可link到各互動區 -->
		<div class="row3">
			<div class="col-md-12">
				<h1 class="main-title myH1">寵友互動區</h1>
				<div class="section-line"></div>
			</div>
			<div class="row">
				<!-- .col-12 .col-md-6 = 手機滿版, 平板以上2欄  -->
				<div class="col-12 col-md-6 myBox" data-aos="fade-down-right"
					data-aos-delay="100">
					<figure class="item">
						<img src="photo/shy.jpg" class="img-fluid">
						<figcaption>討論區</figcaption>
					</figure>
				</div>
				<div class="col-12 col-md-6 myBlog" data-aos="fade-down-right"
					data-aos-delay="100">
					<div class="text-box">
						<div class="title">
							<h2>討論區</h2>
						</div>
						<div class="subtitle">
							<h5>寵物文章 & 知識家</h5>
						</div>
						<div class="underline"></div>
						<div class="text">
							<ul>
								<li><a href="#">要出遠門～又怕家裡的喵星人沒人照顧嗎？</a></li>
								<li><a href="#"> 貓肥家提供最專業最貼心的照護服務</a></li>
								<li><a href="#"> 空間舒適豐富、地點方便～</a></li>
								<li><a href="#">請您放心的把自己心愛的貓咪交給我們。</a></li>
							</ul>
						</div>
						<a href="#">...more</a>
					</div>
				</div>
				<div class="separate">
					<span style="display: none;">--------------------我是分隔線--------------------
					</span>
				</div>
				<!-- .col-12 .col-md-6 = 手機滿版, 平板以上2欄  -->
				<div class="col-12 col-md-6 myBox" data-aos="fade-down-right"
					data-aos-delay="100">
					<figure class="item">
						<img src="photo/catPlay.jpg" class="img-fluid">
						<figcaption>揪團</figcaption>
					</figure>
				</div>
				<div class="col-12 col-md-6 myEvent" data-aos="fade-down-right"
					data-aos-delay="150">
					<div class="text-box">
						<div class="title">
							<h2>揪團</h2>
						</div>
						<div class="subtitle">
							<h5>各種寵物活動</h5>
						</div>
						<div class="underline"></div>
						<div class="text">
							<ul>
								<li><a href="#">要出遠門～又怕家裡的喵星人沒人照顧嗎？</a></li>
								<li><a href="#"> 貓肥家提供最專業最貼心的照護服務</a></li>
								<li><a href="#"> 空間舒適豐富、地點方便～</a></li>
								<li><a href="#">請您放心的把自己心愛的貓咪交給我們。</a></li>
							</ul>
						</div>
						<a href="#">...more</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row4" data-aos="zoom-in" data-aos-delay="300">
			<div class="col-md-12">
				<h1 class="main-title myH1">合作廠商</h1>
				<div class="section-line"></div>
			</div>
			<div class="ad">
				<a href="#"><img src="http://picsum.photos/480/360?random=40"
					class="mr-3" alt="..."></a> <a href="#"><img
					src="http://picsum.photos/480/360?random=41" class="mr-3" alt="..."></a>
				<a href="#"><img src="http://picsum.photos/480/360?random=42"
					class="mr-3" alt="..."></a> <a href="#"><img
					src="http://picsum.photos/480/360?random=43" class="mr-3" alt="..."></a>
				<a href="#"><img src="http://picsum.photos/480/360?random=44"
					class="mr-3" alt="..."></a> <a href="#"><img
					src="http://picsum.photos/480/360?random=45" class="mr-3" alt="..."></a>
			</div>
			<div class="cutePage">
				<a href="#"><img src="cuteIcon/png/011-siberian husky.png"
					class="page" alt="..."></a> <a href="#"><img
					src="cuteIcon/png/005-golden retriever.png" class="page" alt="..."></a>
				<a href="#"><img src="cuteIcon/png/025-bull terrier.png"
					class="page" alt="..."></a> <a href="#"><img
					src="cuteIcon/png/043-pomeranian.png" class="page" alt="..."></a>
				<a href="#"><img src="cuteIcon/png/013-corgi.png" class="page"
					alt="..."></a>
			</div>
		</div>
	</div>
	<!-- 內文end -->
	<!-- 客服小按鈕 -->
	<div class="qaicon">
		<a href="#"><img src="unicorn.svg" /></a>
	</div>
	<!-- footer -->
	<div class="container-fluid main-footer text-center">
		<div class="row myFooterContainer">
			<div class="col-12 col-sm">
				<ul>
					About us
					<li><a href="#" class="myFooterLink">服務條款</a></li>
					<li><a href="#" class="myFooterLink">隱私權政策</a></li>
				</ul>
			</div>
			<div class="col-12 col-sm">
				<ul>
					FAQ
					<li><a href="#" class="myFooterLink">常見問題</a></li>
					<li><a href="#" class="myFooterLink">意見回饋</a></li>
				</ul>
			</div>
			<div class="col-12 col-sm">
				<ul>
					Contact us
					<li><a href="#" class="myFooterLink">發送電子郵件</a></li>
					<li><a href="#" class="myFooterLink">追蹤我們</a></li>
				</ul>
			</div>
		</div>
		<p class="copyright">
			&copy; copyright by <i>NiHaiZaiMa</i>
		<p>
	</div>
	<script>
		AOS.init();
	</script>
</body>

</html>