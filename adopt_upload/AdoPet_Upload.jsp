<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.adoPet.model.*"%>
<%@ page import="java.util.*"%>


<%
AdoPetVO adoPetVO = (AdoPetVO) request.getAttribute("adoPetVO"); //一開始進來是空值，等使用者填寫資料但有誤時，AdoPetServlet #122會把方才填寫的資訊包裝起來。
%>

<% 
  java.sql.Date petBirth = null;
  try {
	  petBirth = adoPetVO.getPetBirth();
   } catch (Exception e) {
	   petBirth = new java.sql.Date(System.currentTimeMillis());
   }
%>

<html>
<head>
<title>AdoptPet Upload Test</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Cormorant+Garamond:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/animate.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/magnific-popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/aos.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/ionicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/flaticon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/icomoon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/adopt/adoPetCSS/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/adopt/datetimepicker/jquery.datetimepicker.css" />
		<script src="<%=request.getContextPath()%>/adopt/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/adopt/datetimepicker/jquery.datetimepicker.full.js"></script>	
<style>
/*取消按鈕一開始先消失*/
#removeButtonArea {
	display: none;
}

input.cxlBox {
	position: absolute;
	/*checkbox 調整大小*/
	-ms-transform: scale(1.5); /* IE */
	-moz-transform: scale(1.5); /* FF */
	-webkit-transform: scale(1.5); /* Safari and Chrome */
	-o-transform: scale(1.5); /* Opera */
	transform: scale(1.5);
}

.adoptImg {
	width: 210px;
	height: 200px;
}

.deleteArea {
	display: inline-block;
	margin: 5px;
}
</style>
<script>
	function init() {
		var preview = document.getElementById("preview");
		var removeButtonArea = document.getElementById("removeButtonArea");
		var removeButton = document.getElementById("removeButton");
		var adoPetPic = document.getElementById("adoPetPic");
		adoPetPic.addEventListener('change', function() {
			/*上傳檔案後，取消鍵就可以出現*/
			removeButtonArea.style.display = "inline";

			

			var files = adoPetPic.files;
			if (files !== null) {
				preview.innerHTML="";
				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					if (file.type.indexOf('image') > -1) {
						var reader = new FileReader();
						reader.addEventListener('load', function(e) {

							
							var divNew = document.createElement('div');
							divNew.setAttribute('class', 'deleteArea');
							preview.append(divNew);

							var adoptImg = document.createElement('img');
							adoptImg.setAttribute('src', e.target.result);
							adoptImg.setAttribute('class', 'adoptImg');
							divNew.append(adoptImg);

							var cxlBox = document.createElement('input');
							cxlBox.setAttribute('type', 'checkbox');
							cxlBox.setAttribute('class', 'cxlBox');
							adoptImg.before(cxlBox);
						});
						reader.readAsDataURL(file);
					} else {
						alert("請上傳圖片!");
					}
				}
			}

		});

		var cxlBox = document.getElementsByClassName('cxlBox');
		console.log(cxlBox);
		removeButton.addEventListener('click', function() {

			for (let i = (cxlBox.length - 1); i >= 0; i--) {
				var cxlBoxParent = cxlBox[i].closest('.deleteArea');

				if (cxlBox[i].checked) {
					cxlBoxParent.remove();
					delete document.getElementById("adoPetPic").files[i] ;
				}
			}
			console.log(cxlBox);
			console.log(document.getElementById("adoPetPic").files);
		});

		
		//尚未寫出選擇貓後，分類只跑貓的
// 		var petCat =document.getElementById("petCat");
// 		var petType = document.getElementsByClassName("petType");
// 		var option = document.getElementsByTagName("option");
// 		console.log(petType);
// 		petType.addEventListener('click', function() {

			
			
// 				if (petType[0].checked==true) {
					
// 					var span = document.createElement('span');
// 					span.setAttribute("style",'display:none;');
// 					span.append(option[1]);
// 					span.append(option[2]);
		

			
				
				
				
// 				}else{
// 					option[3].style.visibility="hidden";
// 					option[4].style.visibility="hidden";
// 					option[5].style.visibility="hidden";
// 					option[6].style.visibility="hidden";
// 				}
			
// 		});
		
	}
	window.onload = init;
	
	</script>
	<script>
	
	
	  $.datetimepicker.setLocale('zh');
	  
      $('#petBirth').datetimepicker({
         theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=petBirth%>',
         //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
         //startDate:	            '2017/07/10',  // 起始日
         //minDate:               '-1970-01-01', // 去除今日(不含)之前
         //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
      });
    
	
</script>
</head>

<body data-spy="scroll" data-target=".site-navbar-target"
	data-offset="300">
	<section>
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light site-navbar-target"
		id="ftco-navbar">
		<div class="container">
			<a class="navbar-brand" href="index.html">PetFect Match</a>
			<button class="navbar-toggler js-fh5co-nav-toggle fh5co-nav-toggle"
				type="button" data-toggle="collapse" data-target="#ftco-nav"
				aria-controls="ftco-nav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav nav ml-auto">
					<li class="nav-item"><a href="index.html#home-section"
						class="nav-link"><span>Home</span></a></li>
					<li class="nav-item"><a href="index.html#services-section"
						class="nav-link"><span>Services</span></a></li>
					<li class="nav-item"><a href="index.html#about-section"
						class="nav-link"><span>About</span></a></li>
					<li class="nav-item"><a href="index.html#destination-section"
						class="nav-link"><span>Destination</span></a></li>
					<li class="nav-item"><a href="index.html#hotel-section"
						class="nav-link"><span>Hotel</span></a></li>
					<li class="nav-item"><a href="index.html#restaurant-section"
						class="nav-link"><span>Restaurant</span></a></li>
					<li class="nav-item"><a href="index.html#blog-section"
						class="nav-link"><span>Blog</span></a></li>
					<li class="nav-item"><a href="index.html#contact-section"
						class="nav-link"><span>Contact</span></a></li>
				</ul>
			</div>
		</div>
	</nav>
	</section>
		<section class="ftco-section">
		
			<div class="container">
		
				<div class="row">
					<div class="col-lg-8 ftco-animate">
						<h1 class="mb-3 bread">新增待領養寵物</h1>
						<h4 class="mb-5">請填寫詳細資訊</h4>
						
						<c:if test="${ not empty erroMsgas}">
						<c:forEach var="message" items="${erroMsgas}">
							<ul>
							<li>  ${message}</li>
							</ul>
						</c:forEach>
						</c:if>
						
						<form action="/adoPet/adoPet/AdoPet.do" method=post class="px-4 bg-light"
							enctype="multipart/form-data">
							<div class="form-group">
								<label for="petName">寵物名稱*</label> <input type="text"
									class="form-control" name="petName" id="petName" value="<%= (adoPetVO==null)? "":adoPetVO.getPetName()%>" required>
							</div>
							<div class="form-group">
								<label for="petBreed">寵物品種*</label> <input type="text"
									name="petBreed" class="form-control" id="petBreed" value="<%= (adoPetVO==null)? "":adoPetVO.getPetBreed()%>" required>
							</div>
							<div class="form-group">
								<label for="petType">寵物類別*</label> 狗 <input type="radio"
									name="petType" id="petType" value="0" ${(adoPetVO.petType==0)? "checked":""} checked> 貓 <input
									type="radio" name="petType" value="1" ${(adoPetVO.petType==1)? "checked":""} >
							</div>
							
						
							<div class="form-group">
								<label for="petSex">寵物性別*</label> 公 <input type="radio"
									name="petSex" id="petSex" value="0" ${(adoPetVO.petSex == 0)? "checked":""} checked> 母 <input
									type="radio" name="petSex" value="1" ${(adoPetVO.petSex==1)? "checked":""} >
							</div>
							<div class="form-group">
								<label for="petBirth">寵物生日*</label> <input type="date"
									name="petBirth" id="petBirth" required class="form-control" value="<%= (adoPetVO ==null)? "":adoPetVO.getPetBirth()%>">
							</div>
							<div class="form-group">
								<label for="petWeight">寵物體重*</label> <input type="text"
									name="petWeight" id="petWeight" class="form-control" value="<%= (adoPetVO ==null)? "":adoPetVO.getPetWeight()%>" required>
							</div>
							<div class="form-group">
								<label for="petCat">寵物分類*</label> <select name="petCat" id="petCat"
									class="form-control" required>
									<option></option>
									<option value="1" ${(adoPetVO.petCat ==1)? "selected":""}>短毛貓</option>
									<option value="2" ${(adoPetVO.petCat ==2)? "selected":"" }>長毛貓</option>
									<option value="3" ${(adoPetVO.petCat ==3)? "selected":"" }>狗(小型1-5kg)</option>
									<option value="4" ${(adoPetVO.petCat ==4)? "selected":"" }>狗(中型10kg以下)</option>
									<option value="5" ${(adoPetVO.petCat ==5)? "selected":"" }>狗(大型20kg以下)</option>
									<option value="6" ${(adoPetVO.petCat ==6)? "selected":"" }>狗(特大型20kg以上)</option>
								</select>
							</div>
							<div class="form-group">
								<label for="location">探訪地區*</label> <select name="location"
									id="location" class="form-control" required>
									<option value=></option>
									<option value="320桃園市中壢區中大路300號" ${(adoPetVO.location =="320桃園市中壢區中大路300號")?"selected":"" }>桃園總部</option>
									<option value="404台中市北區健行路490號" ${(adoPetVO.location =="404台中市北區健行路490號")?"selected":"" }>台中分公司</option>
									<option value="812高雄市小港區松和路1號" ${(adoPetVO.location =="812高雄市小港區松和路1號")?"selected":""}>高雄分公司</option>
								</select>
							</div>
							<div class="form-group">
								<label for="petChar">特徵</label>
								<input type="text" name="petChar" id="petChar" cols="10" rows="10"
									class="form-control" value="${adoPetVO.petChar}">
							</div>
							<div class="form-group">
								<label for="adoPetPic">待領養寵物圖片*</label> <br> <input
									type="file" name="adoPetPic" id="adoPetPic" multiple
									style="margin-bottom: 20px" required>
							</div>
							
								<div class="form-group" style="margin-bottom: 20px">
 								圖片預覽 
								<div id="removeButtonArea"> 
									 <input type="button" value="刪除圖片" id="removeButton">
								</div> 
 								<div id="preview"></div>
 								</div>
							<div class="form-group" style="margin-left: 200px">
								<input type="hidden" name="action" value="insert">
								<input type="submit" value="確認新增"
									class="btn py-3 px-4 btn-primary"> 	
<!-- 									<input type="reset" -->
<!-- 									value="清除表單" class="btn py-3 px-4 btn-secondary"> -->
							</div></form>

				</div>
				<!-- .col-md-8 -->
				<div class="col-lg-4 sidebar ftco-animate">
					<div class="sidebar-box">
						<form action="#" class="search-form">
							<div class="form-group">
								<span class="icon icon-search"></span> <input type="text"
									class="form-control" placeholder="請輸入待領養寵物編號">
							</div>
						</form>
					</div>
					<div class="sidebar-box ftco-animate">
						<h3 class="heading-sidebar">Categories</h3>
<!-- 						<ul class="categories"> -->
<!-- 							<li><a href="#">Fitness Gym <span>(12)</span></a></li> -->
<!-- 							<li><a href="#">Crossfit <span>(22)</span></a></li> -->
<!-- 							<li><a href="#">Yoga <span>(37)</span></a></li> -->
<!-- 							<li><a href="#">aerobics <span>(42)</span></a></li> -->
<!-- 						</ul> -->
					</div>
				</div>
			</div>
		</section>
		<!-- .section -->
		<footer class="ftco-footer ftco-section">
			<div class="container">
				<div class="row mb-5">
					<div class="col-md">
						<div class="ftco-footer-widget mb-4">
							<h2 class="ftco-heading-2">
								About <span><a href="index.html">Ecoland</a></span>
							</h2>
							<p>Far far away, behind the word mountains, far from the
								countries Vokalia and Consonantia, there live the blind texts.</p>
							<ul
								class="ftco-footer-social list-unstyled float-md-left float-lft mt-5">
								<li class="ftco-animate"><a href="#"><span
										class="icon-twitter"></span></a></li>
								<li class="ftco-animate"><a href="#"><span
										class="icon-facebook"></span></a></li>
								<li class="ftco-animate"><a href="#"><span
										class="icon-instagram"></span></a></li>
							</ul>
						</div>
					</div>
					<div class="col-md">
						<div class="ftco-footer-widget mb-4 ml-md-4">
							<h2 class="ftco-heading-2">Information</h2>
							<ul class="list-unstyled">
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Online Enquiry</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>General Enquiry</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Booking</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Privacy</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Refund Policy</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Call Us</a></li>
							</ul>
						</div>
					</div>
					<div class="col-md">
						<div class="ftco-footer-widget mb-4">
							<h2 class="ftco-heading-2">Experience</h2>
							<ul class="list-unstyled">
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Adventure</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Hotel and Restaurant</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Beach</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Nature</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Camping</a></li>
								<li><a href="#"><span
										class="icon-long-arrow-right mr-2"></span>Party</a></li>
							</ul>
						</div>
					</div>
					<div class="col-md">
						<div class="ftco-footer-widget mb-4">
							<h2 class="ftco-heading-2">Have a Questions?</h2>
							<div class="block-23 mb-3">
								<ul>
									<li><span class="icon icon-map-marker"></span><span
										class="text">203 Fake St. Mountain View, San Francisco,
											California, USA</span></li>
									<li><a href="#"><span class="icon icon-phone"></span><span
											class="text">+2 392 3929 210</span></a></li>
									<li><a href="#"><span class="icon icon-envelope"></span><span
											class="text">info@yourdomain.com</span></a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 text-center">
						<p>
							Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0.
							Copyright &copy;
							<script>
<!-- // 								document.write(new Date().getFullYear()); -->
<!-- 							</script> -->
							All rights reserved | <i class="icon-heart color-danger"
								aria-hidden="true"></i> by <a href="https://colorlib.com"
								target="_blank">Colorlib</a>
							Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0.
						</p>
					</div>
				</div>
			</div>
		</footer>
		<!-- loader -->
		<div id="ftco-loader" class="show fullscreen">
			<svg class="circular" width="48px" height="48px">
                <circle class="path-bg" cx="24" cy="24" r="22"
					fill="none" stroke-width="4" stroke="#eeeeee" />
                <circle class="path" cx="24" cy="24" r="22" fill="none"
					stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
		</div>

		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery-migrate-3.0.1.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/popper.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery.easing.1.3.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery.waypoints.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery.stellar.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/owl.carousel.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery.magnific-popup.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/aos.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/jquery.animateNumber.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/scrollax.min.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/google-map.js"></script>
		<script src="<%=request.getContextPath()%>/adopt/adoPetCSS/js/main.js"></script>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
		<!-- Template Javascript -->
</body>

</html>