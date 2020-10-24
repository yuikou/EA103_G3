<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.salon.model.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<%
	SalonVO salonVO = (SalonVO) session.getAttribute("salonVO");
	SalonService salSvc = new SalonService();
	salonVO = salSvc.getonesalon(salonVO.getSalNo());
	pageContext.setAttribute("salonVO", salonVO);
%>


<head>
<title>Groomer update</title>
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
<link rel="Shortcut Icon" type="image/x-icon" href="photo/favicon.ico">
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
					<input type="hidden" value="${salonVO.salNo}">
					<div class="wrap-input100 validate-input" data-validate="請輸入店名">
						<input class="input100" type="text" name="salName"
							value="${salonVO.salName}" placeholder="美容店店名"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100">
						<input class="input100" type="text" name="salOwner"
							value="${salonVO.salOwner}" placeholder="負責人姓名"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入電話">
						<input class="input100" type="tel" name="salPh"
							value="${salonVO.salPh}" placeholder="連絡電話"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入信箱">
						<input class="input100" type="email" name="salMail"
							value="${salonVO.salMail}" placeholder="信箱"> <span
							class="focus-input100"></span>
					</div>
					<div class="city-selector-set wrap-input100 "
						role="tw-city-selector">
						<div class="input100">
							<!-- 縣市選單 -->
							<div id="show"></div>
							<select class="county" id="country"
								data-value="${salonVO.salCity}"></select> <input type="hidden"
								name="salCity" id="putCountry">
							<!-- 區域選單 -->
							<select class="district" id="district"
								data-value="${salonVO.salDist}"></select> <input type="hidden"
								name="salDist" id="putDistrict">
							<div>
								<input type="text" name="salAdr" value="${salonVO.salAdr}"
									placeholder="地址">
							</div>
						</div>
					</div>

					<div class="wrap-input100 validate-input" data-validate="請輸入銀行代碼">
						<input class="input100" type="text" name="bankCode"
							value="${salonVO.bankCode}" placeholder="銀行代碼"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入匯款帳號">
						<input class="input100" type="text" name="salRemit"
							value="${salonVO.salRemit}" placeholder="匯款帳號"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 ">
						<select class="input100" name="salSTime" class="selectTime">
							<option value="" disabled selected>請選擇營業時間</option>
							<option value="0800" ${(salonVO.salSTime == 0800)?'selected':'' }>0800</option>
							<option value="0900" ${(salonVO.salSTime == 0900)?'selected':'' }>0900</option>
							<option value="1000" ${(salonVO.salSTime == 1000)?'selected':'' }>1000</option>
							<option value="1100" ${(salonVO.salSTime == 1100)?'selected':'' }>1100</option>
							<option value="1200" ${(salonVO.salSTime == 1200)?'selected':'' }>1200</option>
							<option value="1300" ${(salonVO.salSTime == 1300)?'selected':'' }>1300</option>
							<option value="1400" ${(salonVO.salSTime == 1400)?'selected':'' }>1400</option>
							<option value="1500" ${(salonVO.salSTime == 1500)?'selected':'' }>1500</option>
							<option value="1600" ${(salonVO.salSTime == 1600)?'selected':'' }>1600</option>
							<option value="1700" ${(salonVO.salSTime == 1700)?'selected':'' }>1700</option>
							<option value="1800" ${(salonVO.salSTime == 1800)?'selected':'' }>1800</option>
							<option value="1900" ${(salonVO.salSTime == 1900)?'selected':'' }>1900</option>
							<option value="2000" ${(salonVO.salSTime == 2000)?'selected':'' }>2000</option>
						</select>
					</div>
					<div class="wrap-input100 ">
						<select class="input100" name="salETime">
							<option value="" disabled selected>請選擇閉店時間</option>
							<option value="1200" ${(salonVO.salETime == 1200)?'selected':'' }>1200</option>
							<option value="1300" ${(salonVO.salETime == 1300)?'selected':'' }>1300</option>
							<option value="1400" ${(salonVO.salETime == 1400)?'selected':'' }>1400</option>
							<option value="1500" ${(salonVO.salETime == 1500)?'selected':'' }>1500</option>
							<option value="1600" ${(salonVO.salETime == 1600)?'selected':'' }>1600</option>
							<option value="1700" ${(salonVO.salETime == 1700)?'selected':'' }>1700</option>
							<option value="1800" ${(salonVO.salETime == 1800)?'selected':'' }>1800</option>
							<option value="1900" ${(salonVO.salETime == 1900)?'selected':'' }>1900</option>
							<option value="2000" ${(salonVO.salETime == 2000)?'selected':'' }>2000</option>
							<option value="2100" ${(salonVO.salETime == 2100)?'selected':'' }>2100</option>
							<option value="2200" ${(salonVO.salETime == 2200)?'selected':'' }>2200</option>
							<option value="2300" ${(salonVO.salETime == 2300)?'selected':'' }>2300</option>
							<option value="2400" ${(salonVO.salETime == 2400)?'selected':'' }>2400</option>
						</select>
					</div>
					<div class="wrap-input100 ">
						<select class="input100" name="salPetType" class="selectTime"
							id="salPetType">
							<option disabled>請選擇接受寵物</option>
							<option value="0" ${(salonVO.salPetType == 0)?'selected':'' }>狗和貓</option>
							<option value="1" ${(salonVO.salPetType == 1)?'selected':'' }>狗</option>
							<option value="2" ${(salonVO.salPetType == 2)?'selected':'' }>貓</option>
						</select>
					</div>
					<div class="wrap-input100 validate-input" data-validate="請輸入簡介">
						<textarea class="input100 txt1" name="salInfo" id="salInfo"
							maxlength="200" placeholder="美容店簡介">${salonVO.salInfo}</textarea>
						<span class="focus-input100"></span>

					</div>
					<!--  					上傳業者大頭貼 -->
					<div class="wrap-input100 " data-validate="請上傳大頭貼">
						<label class="input100">
							<p class="txt1 myP">請點擊此處上傳大頭貼</p> <input id="myFile2"
							type="file" style="display: none" name="salPic">
						</label>
					</div>
					<!-- 					大頭貼預覽 -->
					<div class="wrap-input100 validate-input input100 myP myBox"
						id="preview">
						<c:if test="${salonVO.salPic == null}">
							<img id="blah2" src="photo/noPic.jpg">
						</c:if>
						<c:if test="${salonVO.salPic != null}">
							<img id="blah2"
								src="${pageContext.request.contextPath}/front-end/salon/ShowPic.do?action=salPic&salNo=${salonVO.salNo}"
								alt="your image"> 圖片檔預覽
						<input type="button" class="login100-form-btn" id="remove"
								value="刪除">
						</c:if>

					</div>


					<div class="wrap-input100 " data-validate="請上傳營業執照">
						<label class="input100">
							<p class="txt1 myP">請點擊此處上傳營業執照</p> <input id="myFile"
							type="file" style="display: none" name="salCertif">
						</label>
					</div>
					<div class="wrap-input100 validate-input input100 myP myBox"
						id="preview">

						<c:if test="${salonVO.salCertif == null}">
							<img id="blah" src="photo/noPic.jpg">
						</c:if>
						<c:if test="${salonVO.salCertif != null}">
							<img
								src="${pageContext.request.contextPath}/front-end/salon/ShowPic.do?action=salCertif&salNo=${salonVO.salNo}"
								alt="" id="blah" /> 圖片檔預覽 <input type="button"
								class="login100-form-btn" id="remove" value="刪除">
						</c:if>


					</div>
					<div class="p-t-13 p-b-23" id="myTerm">
						<span class="txt1"> 註冊即同意PETfect Match的 </span> <a href="#"
							class="txt2"> 服務條款 </a> <span class="txt1"> & </span> <a href="#"
							class="txt2"> 隱私權條款 </a>
					</div>
					<div class="container-login100-form-btn p-b-40">
						<input type="hidden" name="action" value="updatesalon"> <input
							type="hidden" name="salNo" value="${salonVO.salNo}">
						<button class="login100-form-btn" id="send">確認修改</button>
						<!-- 						<button class="login100-form-btn" id="send">確認修改</button> -->
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
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/getCountry.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/showImg.js"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/showImg2.js"></script>


	<script>
		// 指定選單 element
		new TwCitySelector({
			el : '.city-selector-set',
			elCounty : '.county', // 在 el 裡查找 element
			elDistrict : '.district', // 在 el 裡查找 element
			elZipcode : '.zipcode' // 在 el 裡查找 element
		});
	</script>
	<%@ include file="/salonFooter.jsp"%>
</body>

</html>