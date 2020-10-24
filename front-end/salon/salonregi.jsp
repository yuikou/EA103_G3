<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salon.model.*"%>

<%
	SalonVO salonVO = (SalonVO) request.getAttribute("salonVO");
%>


<head>
<title>Groomer Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/main.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Dave/groomerForm.css">
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
					enctype="multipart/form-data" name="form1"
					class="login100-form validate-form p-l-55 p-r-55 p-t-90">
					<span class="login100-form-title"> Meow ♥ </span>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
					<div class="wrap-input100 validate-input m-b-16"
						data-validate="請輸入帳號">
						<input class="input100" type="text" name="salAc" id="salAc"
							placeholder="帳號">
						<div id="msg"></div>
						<span class="focus-input100"></span>
					</div>
					<!-- 密碼方便測試先用明碼  -->
					<div class="wrap-input100 validate-input" data-validate="請輸入密碼">
						<input class="input100" type="text" name="salPw" placeholder="密碼">
						<span class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入一樣的密碼">
						<input class="input100" type="text" name="salPw2"
							placeholder="密碼確認"> <span class="focus-input100"></span>
					</div>
					<!--  -->
					<div class="wrap-input100 validate-input" data-validate="請輸入店名">
						<input class="input100" type="text" name="salName"
							placeholder="美容店店名"> <span class="focus-input100"></span>
					</div>
					<div class="wrap-input100">
						<input class="input100" type="text" name="salOwner"
							placeholder="負責人姓名"> <span class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入電話">
						<input class="input100" type="tel" name="salPh" placeholder="連絡電話">
						<span class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入信箱">
						<input class="input100" type="email" name="salMail"
							placeholder="信箱" id="salMail">
						<div id="mmsg"></div>
						<span class="focus-input100"></span>
					</div>
					<div class="city-selector-set wrap-input100 "
						role="tw-city-selector">
						<div class="input100">
							<!-- 縣市選單 -->
							<div id="show"></div>
							<select class="county" id="country"></select> <input
								type="hidden" name="salCity" id="putCountry"> <select
								class="district" id="district"></select> <input type="hidden"
								name="salDist" id="putDistrict">
							<div>
								<input type="text" name="salAdr" placeholder="地址">
							</div>
						</div>
					</div>

					<div class="wrap-input100 validate-input" data-validate="請輸入銀行代碼">
						<input class="input100" type="text" name="bankCode"
							placeholder="銀行代碼"> <span class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入匯款帳號">
						<input class="input100" type="text" name="salRemit"
							placeholder="匯款帳號"> <span class="focus-input100"></span>
					</div>
					<div class="wrap-input100 ">
						<select class="input100" name="salSTime" id="">
							<option value="" disabled selected>請選擇營業時間</option>
							<option value="0800">0800</option>
							<option value="0900">0900</option>
							<option value="1000">1000</option>
							<option value="1100">1100</option>
							<option value="1200">1200</option>
							<option value="1300">1300</option>
							<option value="1400">1400</option>
							<option value="1500">1500</option>
							<option value="1600">1600</option>
							<option value="1700">1700</option>
							<option value="1800">1800</option>
							<option value="1900">1900</option>
							<option value="2000">2000</option>
						</select>
					</div>
					<div class="wrap-input100 ">
						<select class="input100" name="salETime" id="">
							<option value="" disabled selected>請選擇閉店時間</option>
							<option value="1300">1300</option>
							<option value="1300">1300</option>
							<option value="1400">1400</option>
							<option value="1500">1500</option>
							<option value="1600">1600</option>
							<option value="1700">1700</option>
							<option value="1800">1800</option>
							<option value="1900">1900</option>
							<option value="2000">2000</option>
							<option value="2100">2100</option>
							<option value="2200">2200</option>
							<option value="2300">2300</option>
							<option value="2400">2400</option>
						</select>
					</div>
					<div class="wrap-input100 ">
						<select class="input100" name="salPetType" id="">
							<option value="">請選擇接受寵物類</option>
							<option value="0">狗和貓</option>
							<option value="1">狗</option>
							<option value="2">貓</option>
						</select>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入簡介">
						<textarea class="input100 txt1" name="salInfo" id="salInfo"
							maxlength="200" placeholder="美容店簡介"></textarea>
						<span class="focus-input100"></span>
					</div>
					<div>
						<input type="hidden" name="salStatus" id="salStatus">
						<!-- 						<input id="myFile" -->
						<!-- 							type="file"  name="salCertif"> -->

					</div>
<!-- 					上傳業者大頭貼 -->
					<div class="wrap-input100 " data-validate="請上傳大頭貼">
						<label class="input100">
							<p class="txt1 myP">請點擊此處上傳大頭貼</p> <input id="myFile2"
							type="file" style="display: none" name="salPic">
						</label>
					</div>
<!-- 					大頭貼預覽 -->
					<div class="wrap-input100 validate-input input100 myP myBox"
						id="preview">
						<img id="blah2" src="photo/noPic.jpg" alt="your image"> 圖片檔預覽
						<input type="button" class="login100-form-btn" id="remove"
							value="刪除">
					</div>


<!-- 					上傳營業執照 -->
					<div class="wrap-input100 " data-validate="請上傳營業執照">
						<label class="input100">
							<p class="txt1 myP">請點擊此處上傳營業執照</p> <input id="myFile"
							type="file" style="display: none" name="salCertif">
						</label>
					</div>
<!-- 					營業執照預覽 -->
					<div class="wrap-input100 validate-input input100 myP myBox"
						id="preview">
						<img id="blah" src="photo/noPic.jpg" alt="your image"> 圖片檔預覽
						<input type="button" class="login100-form-btn" id="remove"
							value="刪除">
					</div>
					
					<div class="p-t-13 p-b-23" id="myTerm">
						<span class="txt1"> 註冊即同意PETfect Match的 </span> <a href="#"
							class="txt2"> 服務條款 </a> <span class="txt1"> & </span> <a href="#"
							class="txt2"> 隱私權條款 </a>
					</div>
					<div class="container-login100-form-btn p-b-40">
						<input type="hidden" name="action" value="insert">
						<button class="login100-form-btn" id="send">Submit</button>
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
	<script src="<%=request.getContextPath()%>/js/Dave/check.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/picUp.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/tw-city-selector.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/getCountry.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/showImg.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/showImg2.js"></script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script>
		// 指定選單 element
		new TwCitySelector({
			el : '.city-selector-set',
			elCounty : '.county', // 在 el 裡查找 element
			elDistrict : '.district', // 在 el 裡查找 element
			elZipcode : '.zipcode' // 在 el 裡查找 element
		});
	</script>
			
	<script>
	function getXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}

	window.onload = function() {
		var salAc = document.getElementsByName("salAc")[0];
		var salMail = document.getElementsByName("salMail")[0];
		// 为昵称选项注册onblur事件
		salAc.onblur = function() {
			var salAc = this.value;

			// 1.获取XMLHttpRequest对象
			var req = getXMLHttpRequest();
			// 4.处理响应结果
			req.onreadystatechange = function() {
				if (req.readyState == 4) { // XMLHttpRequest对象读取成功
					if (req.status == 200) { // 服务器相应正常
						var msg = document.getElementById("msg");
						var send = document.getElementById("send");
						// 根据返回的结果显示不同的信息
						if (req.responseText === "true") {
							msg.innerText = "此帳號已被註冊";
							send.disabled = true;							
						} else {
							msg.innerHTML = "<font color='green'>此帳號可以使用</font>";
							send.disabled = false;
						}
					}
				}
			}
			// 2.建立一个连接
			req.open("get",
					"${pageContext.request.contextPath}/front-end/salon/salon.do?salAc="+ salAc);
				// 3.发送get请求
				req.send(null);

			}
		}
	</script>
	<%@ include file="/salonFooter.jsp"%>


</body>

</html>