<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitSrv.model.*, java.util.*"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>新增托養服務</title>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvAdd.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>

<!-------------------- nav -------------------->
	<jsp:include page="/front-end/header.jsp"/>
    
<!------------------ 內文body ------------------>
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
		
		<section>
			<!-- multistep form -->
			<form id="msform" action="${pageContext.request.contextPath}/sitSrv/sitSrv.do" method="post" enctype="multipart/form-data">
			  <!-- progressbar -->
			  <ul id="progressbar">
			    <li class="active pgLi">免費添加您的服務</li>
			    <li class="pgLi">描述您的服務細節</li>
			    <li class="pgLi">決定您的服務價格</li>
			    <li class="pgLi">上傳您的營業證照</li>
			  </ul>
			  <!-- fieldsets -->
			  <fieldset>
			    <h2 class="fs-title">開始添加保姆服務吧!</h2>
			    <h3 class="fs-subtitle">step 1</h3>
			    <span class="inputQ" style="width: 100%;">請選擇您所要提供的服務</span>
<!-- sitSrvCode & sitSrvName -->
			    <div class="tabs">
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('Boarding')}"> sistSrvHide </c:if>" >
        				<input class="input1 sitSrv" type="radio" id="rd1" name='sitSrvCode' value="Boarding" <c:if test="${ssVO.sitSrvCode=='Boarding'}"> checked='checked'</c:if> >
        				<label class="tab-label" for="rd1">寵物住宿寄養</label>
        				<div class="tab-content">
        					<h5>寵物到您家中過夜</h5>
        					<div class="tab-content-info"><span>服務名稱</span><small> 您可以結合城市名稱、服務類型或是您所服務的寵物類型，作為您的服務名稱更容易讓飼主記住喔~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" maxlength="20" placeholder="例如：中壢Tibame死小孩【寄養】服務" 
			    			<c:if test="${ssVO.sitSrvCode=='Boarding'}"> value="${ssVO.sitSrvName}" </c:if> />
			    			<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addBathing' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='Boarding' && ssVO.addBathing=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價洗澡</span>
        						</label>
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addPickup' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='Boarding' && ssVO.addPickup=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價接送</span>
        						</label>
      						</div>
        				</div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('DayCare')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd2" name='sitSrvCode' value="DayCare" <c:if test="${ssVO.sitSrvCode=='DayCare'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd2">寵物日托安親</label>
				        <div class="tab-content">
				        	<h5>寵物到您家中溜達</h5>
				        	<div class="tab-content-info"><span>服務名稱</span><small> 您可以結合城市名稱、服務類型或是您所服務的寵物類型，作為您的服務名稱更容易讓飼主記住喔~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="例如：中壢Tibame死小孩【日托】服務"
			    			<c:if test="${ssVO.sitSrvCode=='DayCare'}"> value="${ssVO.sitSrvName}" </c:if> />
			    			<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addBathing' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DayCare' && ssVO.addBathing=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價洗澡</span>
        						</label>
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addPickup' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DayCare' && ssVO.addPickup=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價接送</span>
        						</label>
      						</div>
				        </div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('DropIn')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd3" name='sitSrvCode' value="DropIn" <c:if test="${ssVO.sitSrvCode=='DropIn'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd3">到府寵物保姆</label>
				        <div class="tab-content">
				        	<h5>去寵物家裡餵食與清潔</h5>
				        	<div class="tab-content-info"><span>服務名稱</span><small> 您可以結合城市名稱、服務類型或是您所服務的寵物類型，作為您的服務名稱更容易讓飼主記住喔~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="例如：中壢Tibame死小孩【到府】服務"
			    			<c:if test="${ssVO.sitSrvCode=='DropIn'}"> value="${ssVO.sitSrvName}" </c:if> />
			    			<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addBathing' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DropIn' && ssVO.addBathing=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價洗澡</span>
        						</label>
      						</div>
				        </div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('DogWalking')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd4" name='sitSrvCode' value="DogWalking" <c:if test="${ssVO.sitSrvCode=='DogWalking'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd4">遛狗服務</label>
				        <div class="tab-content">
				        	<h5>帶寵物外出透氣</h5>
				        	<div class="tab-content-info"><span>服務名稱</span><small> 您可以結合城市名稱、服務類型或是您所服務的寵物類型，作為您的服務名稱更容易讓飼主記住喔~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="例如：中壢Tibame死小孩【遛狗】服務" 
			    			<c:if test="${ssVO.sitSrvCode=='DogWalking'}"> value="${ssVO.sitSrvName}" </c:if> />
				        	<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addPickup' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DogWalking' && ssVO.addPickup=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價接送</span>
        						</label>
      						</div>
				        </div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('PetTaxi')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd5" name='sitSrvCode' value="PetTaxi" <c:if test="${ssVO.sitSrvCode=='PetTaxi'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd5">寵物計程車</label>
				        <div class="tab-content">
				        	<h5>載送寵物服務</h5>
				        	<div class="tab-content-info"><span>服務名稱</span><small> 您可以結合城市名稱、服務類型或是您所服務的寵物類型，作為您的服務名稱更容易讓飼主記住喔~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="例如：中壢Tibame死小孩【計程車】服務"
			    											 value="${ssVO.sitSrvName}" style="margin-bottom: 20px"/>
				        </div>
      				</div>
    			</div>
    			<input type="button" name="cancel" class="cancel action-button" value="取消新增" onclick="location.href='${pageContext.request.contextPath}/front-end/petSitter/listOneSitter.jsp'"/>
			    <input type="button" name="next" class="next action-button" value="下一頁" />
			  </fieldset>
			  <fieldset>
			    <h2 class="fs-title">描述您的寵物服務</h2>
			    <h3 class="fs-subtitle">step 2</h3>
<!-- srvArea -->
			    <div class="btn-group" data-toggle="buttons" style="margin-top: 10px;border-bottom: 1px solid #eee; padding: 5px 0 10px 0;">
			    	<span class="inputQ">可服務距離</span>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="0" 
	          			<c:if test="${ssVO.srvArea=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 5公里內</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="1" 
	          			<c:if test="${ssVO.srvArea=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 5~10公里</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="2" 
	          			<c:if test="${ssVO.srvArea=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 10~20公里</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="3" 
	          			<c:if test="${ssVO.srvArea=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 20公里以上</span>
	        		</label>
      			</div>
<!-- acpPetTyp-1 -->
      			<div class="btn-group" data-toggle="buttons">
        			<span class="inputQ">接受的寵物類型</span>
        			<c:if test="${ssVO.sitSrvCode!='DogWalking'}">
        			<label class="btn">
          				<input class="input1" type="checkbox" name='acpPetTypPart0' value="cat" 
          				<c:if test="${ssVO.acpPetTyp<=4}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 貓</span><small>one size</small>
        			</label>
        			</c:if>
        			<label class="btn">
          				<input class="input1 acpDog" type="checkbox" name='acpPetTypPart1' value="dog" 
          				<c:if test="${ssVO.acpPetTyp>0}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 狗</span>
        			</label>
      			</div>
<!-- acpPetTyp-2 -->
				<div class="dogSize">
					<span class="inputQ acpTyp">可接受狗狗的最大體型</span>
	      			<div class="btn-group" data-toggle="buttons" style="padding: 5px 0;">
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="small" 
		          			<c:if test="${ssVO.acpPetTyp=='1'||ssVO.acpPetTyp=='5'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 小型犬(1~5kg)</span>
		        		</label>
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="medium" 
		          			<c:if test="${ssVO.acpPetTyp=='2'||ssVO.acpPetTyp=='6'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 中型犬(5~10kg)</span>
		        		</label>
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="large" 
		          			<c:if test="${ssVO.acpPetTyp=='3'||ssVO.acpPetTyp=='7'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 大型犬(10~20kg)</span>
		        		</label>
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="xlarge" 
		          			<c:if test="${ssVO.acpPetTyp=='4'||ssVO.acpPetTyp=='8'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 特大型犬(20kg以上)</span>
		        		</label>
	      			</div>
	      		</div>
<!-- acpPetNum -->
			    <div class="input-group" style="padding: 5px 0 10px 0; border-bottom: 1px solid #eee;">
			    	<span class="inputQ">總共可以接納幾隻寵物</span>
  					<span class="input-group-btn">
	              		<button type="button" class="btn btn-default btn-number" disabled="disabled" data-type="minus" data-field="acpPetNum">
	                  		<i class="fa fa-minus"></i>
	  					</button>
  					</span>
  					<input type="text" name="acpPetNum" class="input1 form-control input-number" value="${param.acpPetNum==null?1:param.acpPetNum}" min="1" max="8" style="top: -3px;">
  					<span class="input-group-btn">
              			<button type="button" class="btn btn-default btn-number" data-type="plus" data-field="acpPetNum">
                  			<i class="fa fa-plus"></i>
  						</button>
  					</span>
				</div>
<!-- careLevel -->
				<div class="btn-group btn-group-vertical btn-row hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">看顧寵物的程度</span>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="0" 
	          			<c:if test="${ssVO.careLevel=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 永遠不會無人看管</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="1" 
	          			<c:if test="${ssVO.careLevel=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 無看管不超過1小時</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="2" 
	          			<c:if test="${ssVO.careLevel=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 無看管1-2小時</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="3" 
	          			<c:if test="${ssVO.careLevel=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 無看管超過2小時</span>
	        		</label>
      			</div>
<!-- stayLoc -->
      			<div class="btn-group btn-group-vertical btn-row hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">寵物待在的地方</span>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="0" 
	          			<c:if test="${ssVO.stayLoc=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 自由漫遊</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="1" 
	          			<c:if test="${ssVO.stayLoc=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 私人院子</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="2" 
	          			<c:if test="${ssVO.stayLoc=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 狗窩或籠子</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="3" 
	          			<c:if test="${ssVO.stayLoc=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 單獨房間</span>
	        		</label>
      			</div>
<!-- overNightLoc -->
      			<div class="btn-group btn-group-vertical btn-row hide-show1" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">寵物睡覺的地方</span>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="0" 
	          			<c:if test="${ssVO.overnightLoc=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 隨意</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="1" 
	          			<c:if test="${ssVO.overnightLoc=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 在保姆床上</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="2" 
	          			<c:if test="${ssVO.overnightLoc=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 在狗床上</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="3" 
	          			<c:if test="${ssVO.overnightLoc=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 狗窩或籠子</span>
	        		</label>
      			</div>
<!-- smkFree -->
			    <div class="btn-group hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">您家中是無菸環境嗎</span>
	        		<label class="btn">
	          			<input type="radio" name='smkFree' value="0" 
	          			<c:if test="${ssVO.smkFree=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 不是無菸環境</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='smkFree' value="1" 
	          			<c:if test="${ssVO.smkFree=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 是無菸環境</span>
	        		</label>
      			</div>
<!-- hasChild -->		    
			    <div class="btn-group hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">您家中有小孩嗎</span>
	        		<label class="btn">
	          			<input type="radio" name='hasChild' value="0" 
	          			<c:if test="${ssVO.hasChild=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 家裡沒有小孩</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='hasChild' value="1" 
	          			<c:if test="${ssVO.hasChild=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 家裡有小孩</span>
	        		</label>
      			</div>
<!-- eqpt -->      			
      			<div class="btn-group eqptDiv" data-toggle="buttons" style="display: none;">
			    	<span class="inputQ">提供的寵物運輸設備</span>
	        		<label class="btn">
	          			<input type="radio" name='eqpt' value="0" 
	          			<c:if test="${ssVO.eqpt=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 狗座帶</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='eqpt' value="1" 
	          			<c:if test="${ssVO.eqpt=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 寵物籠子</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='eqpt' value="2" 
	          			<c:if test="${ssVO.eqpt=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 託運箱</span>
	        		</label>
      			</div>
			    
			    <input type="button" name="previous" class="previous action-button" value="上一頁" />
			    <input type="button" name="next" class="next action-button" value="下一頁" />
			  </fieldset>
			  <fieldset>
			    <h2 class="fs-title">設定服務價格與內容</h2>
			    <h3 class="fs-subtitle">step 3</h3>
<!-- srvFee -->
			    <div class="fee-group">
			    	<span class="fee-group-inputQ">服務費率</span>
			    	<input class="input1 fee-group-input" name="srvFee" placeholder="200" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${param.srvFee==null?'':param.srvFee}" ><span class="unit srvFeeUnit"> / 晚</span>
			    	<span class="fee-group-inputQ addB">加價洗澡價格</span>
			    	<input class="input1 fee-group-input addB" name="addBathingFee" placeholder="100" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${param.addBathingFee==null?'':param.addBathingFee}" ><span class="unit addBathingUnit addB">/次</span>
			    	<span class="fee-group-inputQ addP">加價接送價格</span>
			    	<input class="input1 fee-group-input addP" name="addPickupFee" placeholder="30" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${param.addPickupFee==null?'':param.addPickupFee}" ><span class="unit addPickupUnit addP">/公里</span>
			    </div>
<!-- srvTime -->
			    <div class="quantity hide-srvTime">
			    	<span class="quantity-inputQ">服務時間</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min="0" max="8" step="1" name="srvTimeH" 
  						value="${param.srvTimeH==null?'1':param.srvTimeH}" ><span class="unit">小時</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min=0 max=30 step="30" name="srvTimeM" 
  						value="${param.srvTimeM==null?'0':param.srvTimeM}" ><span class="unit">分</span>
				</div>
<!-- srvInfo -->	
			    <span class="inputQ" style="padding-top: 10px;">服務內容包含</span>
<!-- 			    <div id="toolbar-container"></div> -->
<!--     			<div id="editor1"></div> -->
<%-- 			    <input type="hidden" name="srvInfo" value="${param.srvInfo==null?'':param.srvInfo}"> --%>
				<textarea id="editor1" name="srvInfo" value="${param.srvInfo==null?'':param.srvInfo}"></textarea>
			    <input type="button" name="previous" class="previous action-button" value="上一頁" />
			    <input type="button" name="next" class="next action-button" value="下一頁" />
			  </fieldset>
			  <fieldset>
			    <h2 class="fs-title">上傳寵物營業證照</h2>
			    <h3 class="fs-subtitle">step 4</h3>
			    <h5 class="addInputQ"><b>提供寵物住宿寄養</b><font color=red>必須上傳</font>「特定寵物業許可證」，其他服務可略過此步驟</h5>
			    <jsp:include page="/front-end/sitLic/addSitLic2.jsp"/>
			    <input type="button" name="previous" class="previous action-button" value="上一頁" />
			    <input type="hidden" name="action" value="add">
			    <input type="button" class="submit action-button" value="送出" />
			  </fieldset>
			</form>
		</section>
	</div>

<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>


<!-- 匯入js -->
	<script src="${pageContext.request.contextPath}/js/euphy/jquery-3.2.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/classic/ckeditor.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/classic/translations/zh.js"></script>
<!-- 	<script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/decoupled-document/ckeditor.js"></script> -->
	<script src="${pageContext.request.contextPath}/js/euphy/acpPetNum.js"></script>
	<script>
	$(document).ready(function(){
		
		// -------------------------------page-----------------------------------
		
		var current_fs, next_fs, previous_fs; //fieldsets
		var left, opacity, scale; //fieldset properties which we will animate
		var animating; //flag to prevent quick multi-click glitches
	
		$(".next").click(function(){
			if(animating) return false;
			animating = true;
			
			current_fs = $(this).parent();
			next_fs = $(this).parent().next();
			
			//activate next step on progressbar using the index of next_fs
			$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
			
			//show the next fieldset
			next_fs.show(); 
			//hide the current fieldset with style
			current_fs.animate({opacity: 0}, {
				step: function(now, mx) {
					//as the opacity of current_fs reduces to 0 - stored in "now"
					//1. scale current_fs down to 80%
					scale = 1 - (1 - now) * 0.2;
					//2. bring next_fs from the right(50%)
					left = (now * 50)+"%";
					//3. increase opacity of next_fs to 1 as it moves in
					opacity = 1 - now;
					current_fs.css({
		        'transform': 'scale('+scale+')',
		        'position': 'absolute'
		      });
					next_fs.css({'left': left, 'opacity': opacity});
				}, 
				duration: 800, 
				complete: function(){
					current_fs.hide();
					animating = false;
				}, 
				//this comes from the custom easing plugin
				easing: 'easeInOutBack'
			});
		});
	
		$(".previous").click(function(){
			if(animating) return false;
			animating = true;
			
			current_fs = $(this).parent();
			previous_fs = $(this).parent().prev();
			
			//de-activate current step on progressbar
			$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
			
			//show the previous fieldset
			previous_fs.show(); 
			//hide the current fieldset with style
			current_fs.animate({opacity: 0}, {
				step: function(now, mx) {
					//as the opacity of current_fs reduces to 0 - stored in "now"
					//1. scale previous_fs from 80% to 100%
					scale = 0.8 + (1 - now) * 0.2;
					//2. take current_fs to the right(50%) - from 0%
					left = ((1-now) * 50)+"%";
					//3. increase opacity of previous_fs to 1 as it moves in
					opacity = 1 - now;
					current_fs.css({'left': left,});
					previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
				}, 
				duration: 800, 
				complete: function(){
					current_fs.hide();
					previous_fs.css({'position': 'static'});
					animating = false;
				}, 
				//this comes from the custom easing plugin
				easing: 'easeInOutBack'
			});
		});
		
		
		// ----------------------------------sitSrv----------------------------------
		
		$('.sitSrv').change(function() {
			var uncheck = $(".sitSrv").not("input:checked");
			// 清除沒有點選的服務項目的名稱跟加價購
			uncheck.each(function(){
				var uncheckParent = $(this).parent();
				var sitSrvName = uncheckParent.find('input[name="sitSrvName"]');
				sitSrvName.val("");
				var _addPickup = uncheckParent.find('input[name="addPickup"]');
				var _addBathing = uncheckParent.find('input[name="addBathing"]');
				
				if (_addPickup.val()) {
					_addPickup.prop("checked", false);
					$(".addP").hide();
				}
				if (_addBathing.val()) {
					_addBathing.prop("checked", false);
					$(".addB").hide();
				}
			});
			// 根據點選的類型決定下兩頁的選項
			var checkVal = $(".sitSrv:checked").val();
			switch (checkVal) {
			case 'Boarding':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show1").show();
				$(".hide-show12").show();
				$(".btn-row").css("width", "33.33%");
				$(".srvFeeUnit").text(" / 晚");
				$(".hide-srvTime").hide();
				break;
			case 'DayCare':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show12").show();
				$(".hide-show1").hide();
				$(".btn-row").css("width", "50%");
				$(".srvFeeUnit").text(" / 天");
				$(".hide-srvTime").hide();
				break;
			case 'DropIn':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show12").hide();
				$(".hide-show1").hide();
				$(".srvFeeUnit").text(" / 次");
				$(".hide-srvTime").show();
				break;
			case 'DogWalking':
				$("[name='acpPetTypPart0']").parent().hide();
				$(".hide-show12").hide();
				$(".hide-show1").hide();
				$(".eqptDiv").hide();
				$(".srvFeeUnit").text(" / 次");
				$(".hide-srvTime").show();
				break;
			case 'PetTaxi':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show12").hide();
				$(".hide-show1").hide();
				$(".eqptDiv").show();
				$(".srvFeeUnit").text(" / 公里");
				$(".hide-srvTime").show();
				break;
			}
			// ------------------------------addBathing--------------------------------
			
			addBathing.each(function(){
				var addBathLabel = $(this).parent();
				
				addBathLabel.click(function(){
					setTimeout(function () {
						if (addBathLabel.find('input[name="addBathing"]').prop("checked")==true){
							$(".addB").show();
						} else {
							$(".addB").hide();
						}
					},150);
				});
			});
			// ------------------------------addPickup---------------------------------
			
			addPickup.each(function(){
				var addPickLabel = $(this).parent();
				
				addPickLabel.click(function(){
					setTimeout(function () {
						if (addPickLabel.find('input[name="addPickup"]').prop("checked")==true){
							$(".addP").show();
						} else {
							$(".addP").hide();
						}
					},150);
				});
			});
		
		});
		
		// 根據點選的類型決定下兩頁的選項
		var checkVal = $(".sitSrv:checked").val();
		switch (checkVal) {
		case 'Boarding':
			$(".hide-show1").show();
			$(".hide-show12").show();
			$(".btn-row").css("width", "33.33%");
			$(".srvFeeUnit").text(" / 晚");
			$(".hide-srvTime").hide();
			break;
		case 'DayCare':
			$(".hide-show12").show();
			$(".hide-show1").hide();
			$(".btn-row").css("width", "50%");
			$(".srvFeeUnit").text(" / 天");
			$(".hide-srvTime").hide();
			break;
		case 'DropIn':
			$(".hide-show12").hide();
			$(".hide-show1").hide();
			$(".srvFeeUnit").text(" / 次");
			$(".hide-srvTime").show();
			break;
		case 'DogWalking':
			$(".hide-show12").hide();
			$(".hide-show1").hide();
			$(".eqptDiv").hide();
			$(".srvFeeUnit").text(" / 次");
			$(".hide-srvTime").show();
			break;
		case 'PetTaxi':
			$(".hide-show12").hide();
			$(".hide-show1").hide();
			$(".eqptDiv").show();
			$(".srvFeeUnit").text(" / 趟");
			$(".hide-srvTime").show();
			break;
		}
		
		var addBathing = $('input[name="addBathing"]');
		addBathing.each(function(){
			if ($(this).prop("checked")==true){
				$(".addB").show();
			}
		});
		var addPickup = $('input[name="addPickup"]');
		addPickup.each(function(){
			if ($(this).prop("checked")==true){
				$(".addP").show();
			}
		});
		
		
		// -------------------------------acpDog-----------------------------------
		var acpDog = $(".acpDog");
		acpDog.parent().click(function(){
			$(".dogSize").toggle(1000);
		});
		
		if (acpDog.prop("checked")==true){
			$(".dogSize").show(1000);
		}
		
		// ----------------------------------srvTime----------------------------------
		
		jQuery('<div class="quantity-nav"><div class="quantity-button quantity-up">+</div><div class="quantity-button quantity-down">-</div></div>').insertAfter('.quantity input');
	    jQuery('.quantity').each(function() {
	      var spinner = jQuery(this),
	        btnUp = spinner.find('.quantity-up'),
	        btnDown = spinner.find('.quantity-down');
	
	      btnUp.click(function() {
	    	var input =  $(this).parent().prev(),
	    		min = input.attr('min'),
	        	max = input.attr('max'),
	      		step = input.attr('step');
	      	var oldValue = parseFloat(input.val());
	        if (oldValue >= max) {
	          var newVal = oldValue;
	        } else {
	          var newVal = oldValue + parseFloat(step);
	        }
	        input.val(newVal);
	        input.trigger("change");
	      });
	
	      btnDown.click(function() {
	    	var input =  $(this).parent().prev(),
	  			min = input.attr('min'),
	      		max = input.attr('max'),
	    		step = input.attr('step');
	        var oldValue = parseFloat(input.val());
	        if (oldValue <= min) {
	          var newVal = oldValue;
	        } else {
	          var newVal = oldValue - parseFloat(step);
	        }
	        input.val(newVal);
	        input.trigger("change");
	        
	      });
	
	    });
	    
	    
	 	// -------------------------------CKeditor-----------------------------------
	 	
		var editor = document.querySelector('#editor1');
		
// 		DecoupledEditor
// 	            .create(editor, {
// 	            	removePlugins: [ 'Heading', 'ImageUpload', 'MediaEmbed', ],
// 	            })
// 	            .then(function(editor) {
// 	            	newEditor = editor;
// 	                const toolbarContainer = document.querySelector('#toolbar-container');
// 	                toolbarContainer.appendChild(editor.ui.view.toolbar.element);
// 	            })
// 	            .catch(function(error) {
// 	                console.error(error);
// 	            });
// 		var newEditor;
		

		ClassicEditor
    		.create( editor, {
    			toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', '|', 'blockQuote', 'insertTable', '|', 'undo', 'redo'],
	        	removePlugins: [ 'ImageUpload', 'MediaEmbed', 'Indent','|'],
	            language: 'zh',
	            placeholder: '為了讓飼主感到安心，請您描述服務包含或不包含內容的簡單說明...',
	        } )
   		 	.then(function(editor) {
    		} )
    		.catch(function(error) {
        		console.error( error );
    		} );
		
		var tempInfo = `${param.srvInfo}`;
		if (tempInfo.length >1){
// 			tempInfo = tempInfo.substring(3, tempInfo.length-4);
			editor.append(tempInfo);
		}
		
		
		$(".submit").click(function(e){
		// -------------------------CKeditor取得date後submit-----------------------------
// 			var editorData = newEditor.getData();
// 			$("[name='srvInfo']").val(editorData);
		    
		    $("form").submit();
		});
		
	});
	
	</script>
	
</body>
</html>