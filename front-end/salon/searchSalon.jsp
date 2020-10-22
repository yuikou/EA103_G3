<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*, com.salon.model.*"%>
<%
	SalonService sSvc = new SalonService();
	List<SalonVO> allSalon = sSvc.getAll();
	pageContext.setAttribute("allSalon", allSalon);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" crossorigin="anonymous">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/rateit.css"  />
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<script>var PATH = "<%=request.getContextPath()%>";</script>
<style>
	a{
		text-decoration: none;
		line-height: inherit;
    	color: #00284D;
    	display:inline;
	}
	span{
		display:inline;
	}
	.county{
		padding: 5px;
		font-size:16px;
		margin-right: 2px;
	}
	.district, #petcat{
		padding: 5px;
		font-size:16px;
	}
	.mybtn{
		text-align: right;
	}
	.showCard{
		height: 180px;
		margin: 5px 0px;
		padding: .5rem;
  		border: 1px solid rgba(10,10,10,0.25);
    	border-radius: .3rem;
    	background-color: #fcfbf7;
	}
	a:hover{
		text-decoration: none;
		color: #FF7260;
	}
	#sName{
		font-size: 24px;
		color: #8ED2C9;
	}
	.salPicP{
		height: 150px;
	}
	.grmPic{
		width: 100%; 
		object-fit: cover;
	}
	.panel{
		margin-bottom: 15px;
		background-color: #F1FAEE;
		padding: 10px;
	}
	#searchbtn{
		background-color: #FF7260;
	}
	.myicon{
		width: 50px;
		margin: 10px 0px 0px 0px;
	}
	.grmPic{
		height: -webkit-fill-available;
	}
	.rateit .rateit-preset {
		color: #FFCC36;
   	 	background: url(https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/star.gif) left -32px !important;
	}
	#opentime, .openTime{
		color:#aaa;
	}
	.myText{
		display: inline-block;
        width: 100%;
        height:30px;
        box-sizing: border-box;
        text-align: center;
        background-color: #9BD7D5;
        border-radius: 5px;
        color: #505050;
        line-height: 2;
	}
	.myText:hover {
        color: #fff;
        background-color: #FFB85F;
        box-shadow: 2px 2px 7px 1px #aaa;
        text-shadow: 1px 2px 2px #CE5937;
        cursor: pointer;
    }
    .bookNow{
   		height:30px;
    	margin-top:10px;
    	background-color: #ea888f;
    	overflow: hidden;
    	padding-top: 1px;
    }
    .bookNow:hover{
    	background-color: #82c4f0;
    }
    #salno{
    	display:none;
    }

</style>
<script>var PICURL = "<%=request.getContextPath()%>/PicReader.do?action=salPic&salPic=";</script>
<title>搜尋寵物美容店</title>
</head>
<body>
<!-- 此處include header -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12 panel">
				<div class="row">
					<div class="col-md-2 col-xs-12">

					</div>
					<div class="col-md-5 col-xs-12">
						<span>您的位置</span>
						<div id="code" role="tw-city-selector"></div>
					</div>
					<div class="col-md-5 col-xs-12">
						<p style="margin:0px">您的寵物類型</p>
						<select name="petcat" id="petcat">
							<option value="0">貓跟狗</option>
							<option value="1">狗</option>
							<option value="2">貓</option>
						</select>
					</div>
					<div class="col-md-12 col-xs-12 mybtn">
						<span class="btn btn-danger" id="searchbtn"><i class="fa fa-search"></i> Search</span> 這裡用ajax
					</div>
				</div>
			</div>
			
				<div class="col-lg-7 col-md-7 col-sm-12" id="sbody">
<%-- 					<%@ include file="/back-end/pages/page1.file"%> --%>
					<c:forEach var="salVO" items="${allSalon}" begin="0" end="10">
						<div class="showCard">
							
								<div class="row">
									<div class="col-md-4 salPic">
										<img id="salPic" class="grmPic"
										src="<%=request.getContextPath()%>/PicReader.do?action=salPic&salPic=${salVO.salNo}"
										alt="salon Pic">
									</div>
									<div class="col-md-6 salbody">
										<p id="salno" name="salno"></p>
										<div id="sName">${salVO.salName }</div>
										<div class="rateit" data-rateit-value="${salVO.salTotalScore }" data-rateit-ispreset="true" data-rateit-readonly="true"></div>
										<div id="location">${salVO.salCity }${salVO.salDist }</div>
										<div class="openTime">營業時間</div>
										<c:set var="start" value="${salVO.salSTime}" />
										<c:set var="endTime" value="${salVO.salETime }" />
										<%
										String str1 = pageContext.getAttribute("start").toString().substring(0,2);
										String str2 = pageContext.getAttribute("start").toString().substring(2);
										String openTime = str1 + ":" + str2;
										
										String str3 = pageContext.getAttribute("endTime").toString().substring(0,2);
										String str4 = pageContext.getAttribute("endTime").toString().substring(2);
										String closeTime = str3 + ":" + str4;
										pageContext.setAttribute("openTime", openTime);
										pageContext.setAttribute("closeTime", closeTime);
										%>
										<div id="opentime">${openTime } ~ ${closeTime }</div>
									</div>	
									<div class="col-md-2 salPet">
										<a href="<%=request.getContextPath() %>/salon/sal.do?action=bookNow&salno=${salVO.salNo}" class="fa fa-paw myText">選我!</a>
										<a href="<%=request.getContextPath() %>/salsev/salsev.do?action=bookNow&salno=${salVO.salNo}" class="fa fa-shopping-cart myText bookNow ">預約</a>
										<c:set var="petcat" value="${salVO.salPetType }" />
										<% 
											String [] picArr = {"both2.svg", "dog(1).svg", "cat(1).svg"};
											String icon = "/Pet_Test/image/"+ picArr[(Integer) pageContext.getAttribute("petcat")];
											pageContext.setAttribute("icon", icon);
										%>
										<div class="salpet"><img id="petcat" src="${icon }" class="myicon"></div>
									</div>
								</div>
							
						</div>
					</c:forEach>
				</div>
				<div class="col-lg-5 col-md-5 col-sm-12">for map</div>
			
		</div>
	</div>
<!-- 此處include footer  -->
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
		integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
		crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.1/dist/tw-city-selector.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/jquery.rateit.min.js"></script>
	<script>
	  new TwCitySelector();
	  
	  var salonList;
 	  $('#searchbtn').click(function(){
		  $.ajax({
			  type: "POST",
			  url: PATH + "/salon/salon.do",
			  dataType:"JSON",
			  data: {
				"action": "findByCondition",
				"salCity": $("[name='county']").val(),
				"salDist": $("[name='district']").val(),
				"salPetType": $("[name='petcat']").val()
			  },
			  success: function(data) {
				
				  salonList = data;
				  console.log(salonList);
// 				  $('.salPic').empty();
// 				  $('.salbody').empty();
				  $('#sbody').empty();
				  for(let i = 0; i < Object.keys(salonList).length; i++){
					  
					 var salno = salonList[i].salNo;
					 var salPic = salonList[i].salPic;
					 var sName = salonList[i].salName;
					 var location = salonList[i].salCity + salonList[i].salDist;
					 
					 var str1 = salonList[i].salSTime.substring(0,2);
					 var str2 = salonList[i].salSTime.substring(2);
					 var sTime = str1 + ":" + str2;
					 
					 var str3 = salonList[i].salETime.substring(0,2);
					 var str4 = salonList[i].salETime.substring(2);
					 var eTime = str3 + ":" + str4;
					 
					 var openTime = sTime + " ~ " + eTime;
					 var star = salonList[i].salTotalScore;
					 var num = salonList[i].salPetType;
					 var petcat = '';
					 
					 if(num === 0){
						 petcat = `"/Pet_Test/image/both2.svg"`;
					 }else if(num === 1){
						 petcat = `"/Pet_Test/image/dog(1).svg"`;
					 }else{
						 petcat = `"/Pet_Test\/image\/cat(1).svg"`;
					 }
					  
					$('#sbody').html('<div class=\"showCard\"><div class=\"row\"><div class=\"col-md-4 salPic\"><img id=\"salPic\" class=\"grmPic\" src=\"'+ PICURL + salno + '\"></div><div class=\"col-md-6 salbody\"><p id=\"salno\"></p><div id=\"sName\"></div><div class=\"star\" data-rateit-value=\"'+ star +'\" data-rateit-ispreset=\"true\" data-rateit-readonly=\"true\"></div><div id=\"location\"></div><div class=\"openTime\">營業時間</div><div id=\"opentime\"></div></div></div>');
					//$('#salPic').attr('src', `data:image/png;base64,${salPic}`);
					$('#salno').attr('name', salno);
					$('#sName').text(sName);
					$('.star').addClass('rateit');
					$('.star').addClass('rateit-bg');
					$('#location').text(location);
					$('#opentime').text(openTime);
					
					
					$('.salbody').after('<div class="col-md-2 salPet"><a class=\"fa fa-paw myText\">選我!</a><a class=\"fa fa-shopping-cart myText bookNow\">預約</a><div class=\"salpet\"><img src=' + petcat + ' id=\"petcat\" class=\"myicon\"></div></div>');
//單一美容店頁面連結				  	//$('.myText').attr('href', PATH + '');
					$('.bookNow').attr('href', PATH + '/salsev/salsev.do?action=bookNow&salno=' + salno);
// 					$('#petcat').attr('src', getPetCat(num));
					$('.rateit').rateit();
				  }
			  }
		  });
 	  });
 	  
 	  
//  	  function getPetCat(num){
 		  
//  		 if(num === 0){
// 			 petcat = `"/Pet_Test/image/both2.svg"`;
// 		 }else if(num === 1){
// 			 petcat = `"/Pet_Test/image/dog(1).svg"`;
// 		 }else{
// 			 petcat = `"/Pet_Test\/image\/cat(1).svg"`;
// 		 }
//  		 return petcat;
//  	  }
 	  
	</script>
</body>
</html>