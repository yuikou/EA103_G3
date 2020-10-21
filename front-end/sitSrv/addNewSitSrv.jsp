<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitSrv.model.*, java.util.*"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�s�W���i�A��</title>

<!-- �פJ�~��CSS -->
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
    
<!------------------ ����body ------------------>
    <div class="container">
    
    	<!-- ���~�C�� -->
	   	<div class="errorList"> 
			<c:if test="${not empty errorMsgs}" >
				<font style="color:red;">�o�ͥH�U���~�G</font>
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
			    <li class="active pgLi">�K�O�K�[�z���A��</li>
			    <li class="pgLi">�y�z�z���A�ȲӸ`</li>
			    <li class="pgLi">�M�w�z���A�Ȼ���</li>
			    <li class="pgLi">�W�Ǳz����~�ҷ�</li>
			  </ul>
			  <!-- fieldsets -->
			  <fieldset>
			    <h2 class="fs-title">�}�l�K�[�O�i�A�ȧa!</h2>
			    <h3 class="fs-subtitle">step 1</h3>
			    <span class="inputQ" style="width: 100%;">�п�ܱz�ҭn���Ѫ��A��</span>
<!-- sitSrvCode & sitSrvName -->
			    <div class="tabs">
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('Boarding')}"> sistSrvHide </c:if>" >
        				<input class="input1 sitSrv" type="radio" id="rd1" name='sitSrvCode' value="Boarding" <c:if test="${ssVO.sitSrvCode=='Boarding'}"> checked='checked'</c:if> >
        				<label class="tab-label" for="rd1">�d����J�H�i</label>
        				<div class="tab-content">
        					<h5>�d����z�a���L�]</h5>
        					<div class="tab-content-info"><span>�A�ȦW��</span><small> �z�i�H���X�����W�١B�A�������άO�z�ҪA�Ȫ��d�������A�@���z���A�ȦW�٧�e�����}�D�O���~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" maxlength="20" placeholder="�Ҧp�G���cTibame���p�ġi�H�i�j�A��" 
			    			<c:if test="${ssVO.sitSrvCode=='Boarding'}"> value="${ssVO.sitSrvName}" </c:if> />
			    			<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addBathing' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='Boarding' && ssVO.addBathing=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[���~��</span>
        						</label>
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addPickup' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='Boarding' && ssVO.addPickup=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[�����e</span>
        						</label>
      						</div>
        				</div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('DayCare')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd2" name='sitSrvCode' value="DayCare" <c:if test="${ssVO.sitSrvCode=='DayCare'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd2">�d���馫�w��</label>
				        <div class="tab-content">
				        	<h5>�d����z�a���ȹF</h5>
				        	<div class="tab-content-info"><span>�A�ȦW��</span><small> �z�i�H���X�����W�١B�A�������άO�z�ҪA�Ȫ��d�������A�@���z���A�ȦW�٧�e�����}�D�O���~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="�Ҧp�G���cTibame���p�ġi�馫�j�A��"
			    			<c:if test="${ssVO.sitSrvCode=='DayCare'}"> value="${ssVO.sitSrvName}" </c:if> />
			    			<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addBathing' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DayCare' && ssVO.addBathing=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[���~��</span>
        						</label>
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addPickup' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DayCare' && ssVO.addPickup=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[�����e</span>
        						</label>
      						</div>
				        </div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('DropIn')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd3" name='sitSrvCode' value="DropIn" <c:if test="${ssVO.sitSrvCode=='DropIn'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd3">�쩲�d���O�i</label>
				        <div class="tab-content">
				        	<h5>�h�d���a�������P�M��</h5>
				        	<div class="tab-content-info"><span>�A�ȦW��</span><small> �z�i�H���X�����W�١B�A�������άO�z�ҪA�Ȫ��d�������A�@���z���A�ȦW�٧�e�����}�D�O���~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="�Ҧp�G���cTibame���p�ġi�쩲�j�A��"
			    			<c:if test="${ssVO.sitSrvCode=='DropIn'}"> value="${ssVO.sitSrvName}" </c:if> />
			    			<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addBathing' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DropIn' && ssVO.addBathing=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[���~��</span>
        						</label>
      						</div>
				        </div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('DogWalking')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd4" name='sitSrvCode' value="DogWalking" <c:if test="${ssVO.sitSrvCode=='DogWalking'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd4">�����A��</label>
				        <div class="tab-content">
				        	<h5>�a�d���~�X�z��</h5>
				        	<div class="tab-content-info"><span>�A�ȦW��</span><small> �z�i�H���X�����W�١B�A�������άO�z�ҪA�Ȫ��d�������A�@���z���A�ȦW�٧�e�����}�D�O���~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="�Ҧp�G���cTibame���p�ġi�����j�A��" 
			    			<c:if test="${ssVO.sitSrvCode=='DogWalking'}"> value="${ssVO.sitSrvName}" </c:if> />
				        	<div class="btn-group" data-toggle="buttons">
        						<label class="btn">
          							<input class="input1" type="checkbox" name='addPickup' value="1" 
          							<c:if test="${ssVO.sitSrvCode=='DogWalking' && ssVO.addPickup=='1'}"> checked='checked'</c:if>
          							><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[�����e</span>
        						</label>
      						</div>
				        </div>
      				</div>
      				<div class="tab  <c:if test="${sitSrvCodeList.contains('PetTaxi')}"> sistSrvHide </c:if>">
        				<input class="input1 sitSrv" type="radio" id="rd5" name='sitSrvCode' value="PetTaxi" <c:if test="${ssVO.sitSrvCode=='PetTaxi'}"> checked='checked'</c:if> >
				        <label class="tab-label" for="rd5">�d���p�{��</label>
				        <div class="tab-content">
				        	<h5>���e�d���A��</h5>
				        	<div class="tab-content-info"><span>�A�ȦW��</span><small> �z�i�H���X�����W�١B�A�������άO�z�ҪA�Ȫ��d�������A�@���z���A�ȦW�٧�e�����}�D�O���~</small></div>
			    			<input class="input1" type="text" name="sitSrvName" placeholder="�Ҧp�G���cTibame���p�ġi�p�{���j�A��"
			    											 value="${ssVO.sitSrvName}" style="margin-bottom: 20px"/>
				        </div>
      				</div>
    			</div>
    			<input type="button" name="cancel" class="cancel action-button" value="�����s�W" onclick="location.href='${pageContext.request.contextPath}/front-end/petSitter/listOneSitter.jsp'"/>
			    <input type="button" name="next" class="next action-button" value="�U�@��" />
			  </fieldset>
			  <fieldset>
			    <h2 class="fs-title">�y�z�z���d���A��</h2>
			    <h3 class="fs-subtitle">step 2</h3>
<!-- srvArea -->
			    <div class="btn-group" data-toggle="buttons" style="margin-top: 10px;border-bottom: 1px solid #eee; padding: 5px 0 10px 0;">
			    	<span class="inputQ">�i�A�ȶZ��</span>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="0" 
	          			<c:if test="${ssVO.srvArea=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 5������</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="1" 
	          			<c:if test="${ssVO.srvArea=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 5~10����</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="2" 
	          			<c:if test="${ssVO.srvArea=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 10~20����</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='srvArea' value="3" 
	          			<c:if test="${ssVO.srvArea=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 20�����H�W</span>
	        		</label>
      			</div>
<!-- acpPetTyp-1 -->
      			<div class="btn-group" data-toggle="buttons">
        			<span class="inputQ">�������d������</span>
        			<c:if test="${ssVO.sitSrvCode!='DogWalking'}">
        			<label class="btn">
          				<input class="input1" type="checkbox" name='acpPetTypPart0' value="cat" 
          				<c:if test="${ssVO.acpPetTyp<=4}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> ��</span><small>one size</small>
        			</label>
        			</c:if>
        			<label class="btn">
          				<input class="input1 acpDog" type="checkbox" name='acpPetTypPart1' value="dog" 
          				<c:if test="${ssVO.acpPetTyp>0}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> ��</span>
        			</label>
      			</div>
<!-- acpPetTyp-2 -->
				<div class="dogSize">
					<span class="inputQ acpTyp">�i�����������̤j�髬</span>
	      			<div class="btn-group" data-toggle="buttons" style="padding: 5px 0;">
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="small" 
		          			<c:if test="${ssVO.acpPetTyp=='1'||ssVO.acpPetTyp=='5'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �p����(1~5kg)</span>
		        		</label>
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="medium" 
		          			<c:if test="${ssVO.acpPetTyp=='2'||ssVO.acpPetTyp=='6'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> ������(5~10kg)</span>
		        		</label>
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="large" 
		          			<c:if test="${ssVO.acpPetTyp=='3'||ssVO.acpPetTyp=='7'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �j����(10~20kg)</span>
		        		</label>
		        		<label class="btn smallBtn">
		          			<input type="radio" name='acpPetTypPart2' value="xlarge" 
		          			<c:if test="${ssVO.acpPetTyp=='4'||ssVO.acpPetTyp=='8'}"> checked='checked'</c:if>
		          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �S�j����(20kg�H�W)</span>
		        		</label>
	      			</div>
	      		</div>
<!-- acpPetNum -->
			    <div class="input-group" style="padding: 5px 0 10px 0; border-bottom: 1px solid #eee;">
			    	<span class="inputQ">�`�@�i�H���ǴX���d��</span>
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
			    	<span class="inputQ">���U�d�����{��</span>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="0" 
	          			<c:if test="${ssVO.careLevel=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �û����|�L�H�ݺ�</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="1" 
	          			<c:if test="${ssVO.careLevel=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �L�ݺޤ��W�L1�p��</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="2" 
	          			<c:if test="${ssVO.careLevel=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �L�ݺ�1-2�p��</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='careLevel' value="3" 
	          			<c:if test="${ssVO.careLevel=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �L�ݺ޶W�L2�p��</span>
	        		</label>
      			</div>
<!-- stayLoc -->
      			<div class="btn-group btn-group-vertical btn-row hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">�d���ݦb���a��</span>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="0" 
	          			<c:if test="${ssVO.stayLoc=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �ۥѺ��C</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="1" 
	          			<c:if test="${ssVO.stayLoc=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �p�H�|�l</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="2" 
	          			<c:if test="${ssVO.stayLoc=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> ���۩�Ţ�l</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='stayLoc' value="3" 
	          			<c:if test="${ssVO.stayLoc=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> ��W�ж�</span>
	        		</label>
      			</div>
<!-- overNightLoc -->
      			<div class="btn-group btn-group-vertical btn-row hide-show1" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">�d����ı���a��</span>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="0" 
	          			<c:if test="${ssVO.overnightLoc=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �H�N</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="1" 
	          			<c:if test="${ssVO.overnightLoc=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �b�O�i�ɤW</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="2" 
	          			<c:if test="${ssVO.overnightLoc=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �b���ɤW</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="3" 
	          			<c:if test="${ssVO.overnightLoc=='3'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> ���۩�Ţ�l</span>
	        		</label>
      			</div>
<!-- smkFree -->
			    <div class="btn-group hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">�z�a���O�L�����Ҷ�</span>
	        		<label class="btn">
	          			<input type="radio" name='smkFree' value="0" 
	          			<c:if test="${ssVO.smkFree=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> ���O�L������</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='smkFree' value="1" 
	          			<c:if test="${ssVO.smkFree=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �O�L������</span>
	        		</label>
      			</div>
<!-- hasChild -->		    
			    <div class="btn-group hide-show12" data-toggle="buttons" style="display:none;">
			    	<span class="inputQ">�z�a�����p�Ķ�</span>
	        		<label class="btn">
	          			<input type="radio" name='hasChild' value="0" 
	          			<c:if test="${ssVO.hasChild=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �a�̨S���p��</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='hasChild' value="1" 
	          			<c:if test="${ssVO.hasChild=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �a�̦��p��</span>
	        		</label>
      			</div>
<!-- eqpt -->      			
      			<div class="btn-group eqptDiv" data-toggle="buttons" style="display: none;">
			    	<span class="inputQ">���Ѫ��d���B��]��</span>
	        		<label class="btn">
	          			<input type="radio" name='eqpt' value="0" 
	          			<c:if test="${ssVO.eqpt=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> ���y�a</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='eqpt' value="1" 
	          			<c:if test="${ssVO.eqpt=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �d��Ţ�l</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='eqpt' value="2" 
	          			<c:if test="${ssVO.eqpt=='2'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �U�B�c</span>
	        		</label>
      			</div>
			    
			    <input type="button" name="previous" class="previous action-button" value="�W�@��" />
			    <input type="button" name="next" class="next action-button" value="�U�@��" />
			  </fieldset>
			  <fieldset>
			    <h2 class="fs-title">�]�w�A�Ȼ���P���e</h2>
			    <h3 class="fs-subtitle">step 3</h3>
<!-- srvFee -->
			    <div class="fee-group">
			    	<span class="fee-group-inputQ">�A�ȶO�v</span>
			    	<input class="input1 fee-group-input" name="srvFee" placeholder="200" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${param.srvFee==null?'':param.srvFee}" ><span class="unit srvFeeUnit"> / ��</span>
			    	<span class="fee-group-inputQ addB">�[���~������</span>
			    	<input class="input1 fee-group-input addB" name="addBathingFee" placeholder="100" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${param.addBathingFee==null?'':param.addBathingFee}" ><span class="unit addBathingUnit addB">/��</span>
			    	<span class="fee-group-inputQ addP">�[�����e����</span>
			    	<input class="input1 fee-group-input addP" name="addPickupFee" placeholder="30" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${param.addPickupFee==null?'':param.addPickupFee}" ><span class="unit addPickupUnit addP">/����</span>
			    </div>
<!-- srvTime -->
			    <div class="quantity hide-srvTime">
			    	<span class="quantity-inputQ">�A�Ȯɶ�</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min="0" max="8" step="1" name="srvTimeH" 
  						value="${param.srvTimeH==null?'1':param.srvTimeH}" ><span class="unit">�p��</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min=0 max=30 step="30" name="srvTimeM" 
  						value="${param.srvTimeM==null?'0':param.srvTimeM}" ><span class="unit">��</span>
				</div>
<!-- srvInfo -->	
			    <span class="inputQ" style="padding-top: 10px;">�A�Ȥ��e�]�t</span>
<!-- 			    <div id="toolbar-container"></div> -->
<!--     			<div id="editor1"></div> -->
<%-- 			    <input type="hidden" name="srvInfo" value="${param.srvInfo==null?'':param.srvInfo}"> --%>
				<textarea id="editor1" name="srvInfo" value="${param.srvInfo==null?'':param.srvInfo}"></textarea>
			    <input type="button" name="previous" class="previous action-button" value="�W�@��" />
			    <input type="button" name="next" class="next action-button" value="�U�@��" />
			  </fieldset>
			  <fieldset>
			    <h2 class="fs-title">�W���d����~�ҷ�</h2>
			    <h3 class="fs-subtitle">step 4</h3>
			    <h5 class="addInputQ"><b>�����d����J�H�i</b><font color=red>�����W��</font>�u�S�w�d���~�\�i�ҡv�A��L�A�ȥi���L���B�J</h5>
			    <jsp:include page="/front-end/sitLic/addSitLic2.jsp"/>
			    <input type="button" name="previous" class="previous action-button" value="�W�@��" />
			    <input type="hidden" name="action" value="add">
			    <input type="button" class="submit action-button" value="�e�X" />
			  </fieldset>
			</form>
		</section>
	</div>

<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>


<!-- �פJjs -->
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
			// �M���S���I�諸�A�ȶ��ت��W�ٸ�[����
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
			// �ھ��I�諸�����M�w�U�⭶���ﶵ
			var checkVal = $(".sitSrv:checked").val();
			switch (checkVal) {
			case 'Boarding':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show1").show();
				$(".hide-show12").show();
				$(".btn-row").css("width", "33.33%");
				$(".srvFeeUnit").text(" / ��");
				$(".hide-srvTime").hide();
				break;
			case 'DayCare':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show12").show();
				$(".hide-show1").hide();
				$(".btn-row").css("width", "50%");
				$(".srvFeeUnit").text(" / ��");
				$(".hide-srvTime").hide();
				break;
			case 'DropIn':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show12").hide();
				$(".hide-show1").hide();
				$(".srvFeeUnit").text(" / ��");
				$(".hide-srvTime").show();
				break;
			case 'DogWalking':
				$("[name='acpPetTypPart0']").parent().hide();
				$(".hide-show12").hide();
				$(".hide-show1").hide();
				$(".eqptDiv").hide();
				$(".srvFeeUnit").text(" / ��");
				$(".hide-srvTime").show();
				break;
			case 'PetTaxi':
				$("[name='acpPetTypPart0']").parent().show();
				$(".hide-show12").hide();
				$(".hide-show1").hide();
				$(".eqptDiv").show();
				$(".srvFeeUnit").text(" / ����");
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
		
		// �ھ��I�諸�����M�w�U�⭶���ﶵ
		var checkVal = $(".sitSrv:checked").val();
		switch (checkVal) {
		case 'Boarding':
			$(".hide-show1").show();
			$(".hide-show12").show();
			$(".btn-row").css("width", "33.33%");
			$(".srvFeeUnit").text(" / ��");
			$(".hide-srvTime").hide();
			break;
		case 'DayCare':
			$(".hide-show12").show();
			$(".hide-show1").hide();
			$(".btn-row").css("width", "50%");
			$(".srvFeeUnit").text(" / ��");
			$(".hide-srvTime").hide();
			break;
		case 'DropIn':
			$(".hide-show12").hide();
			$(".hide-show1").hide();
			$(".srvFeeUnit").text(" / ��");
			$(".hide-srvTime").show();
			break;
		case 'DogWalking':
			$(".hide-show12").hide();
			$(".hide-show1").hide();
			$(".eqptDiv").hide();
			$(".srvFeeUnit").text(" / ��");
			$(".hide-srvTime").show();
			break;
		case 'PetTaxi':
			$(".hide-show12").hide();
			$(".hide-show1").hide();
			$(".eqptDiv").show();
			$(".srvFeeUnit").text(" / ��");
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
	            placeholder: '���F���}�D�P��w�ߡA�бz�y�z�A�ȥ]�t�Τ��]�t���e��²�满��...',
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
		// -------------------------CKeditor���odate��submit-----------------------------
// 			var editorData = newEditor.getData();
// 			$("[name='srvInfo']").val(editorData);
		    
		    $("form").submit();
		});
		
	});
	
	</script>
	
</body>
</html>