<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitSrv.model.*"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�ק嶺�i�A��</title>

<!-- �פJ�~��CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvAdd.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>

<!-------------------- nav -------------------->
	<jsp:include page="/front-end/nav.jsp"/>
    
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
	
	
		<div class="wrap updateBox">
			<h3>
			<c:out value="${ssVO.sitSrvCode=='Boarding'?'�d����J�H�i':''}"/>
			<c:out value="${ssVO.sitSrvCode=='DayCare'?'�d���馫�w��':''}"/>
			<c:out value="${ssVO.sitSrvCode=='DropIn'?'�쩲�d���O�i':''}"/>
			<c:out value="${ssVO.sitSrvCode=='DogWalking'?'�����A��':''}"/>
			<c:out value="${ssVO.sitSrvCode=='PetTaxi'?'�d���p�{��':''}"/>
			</h3>
			<div class="tab-content">
				<span class="inputQ" style="width: 100%;">�A�ȦW��</span>
				<div class="tab-content-info"><small> �z�i�H���X�����W�١B�A�������άO�z�ҪA�Ȫ��d�������A�@���z���A�ȦW�٧�e�����}�D�O���~</small></div>
			    <input class="input1 ssName" type="text" name="sitSrvName" maxlength="20" value="${ssVO.sitSrvName}"/>
			    <div class="btn-group" data-toggle="buttons">
        			<label class="btn">
          				<input class="input1" type="checkbox" name='addBathing' value="1" 
          				<c:if test="${ssVO.addBathing=='1'}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[���~��</span>
        			</label>
        			<label class="btn">
          				<input class="input1" type="checkbox" name='addPickup' value="1" 
          				<c:if test="${ssVO.addPickup=='1'}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> �[�����e</span>
        			</label>
      			</div>
        	</div>
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
        			<label class="btn">
          				<input class="input1" type="checkbox" name='acpPetTypPart0' value="cat" 
          				<c:if test="${ssVO.acpPetTyp<=4}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> ��</span><small>one size</small>
        			</label>
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
	              		<button type="button" class="btn btn-default btn-number" data-type="minus" data-field="acpPetNum">
	                  		<i class="fa fa-minus"></i>
	  					</button>
  					</span>
  					<input type="text" name="acpPetNum" class="input1 form-control input-number" value="${ssVO.acpPetNum}" min="1" max="8" >
  					<span class="input-group-btn">
              			<button type="button" class="btn btn-default btn-number" data-type="plus" data-field="acpPetNum">
                  			<i class="fa fa-plus"></i>
  						</button>
  					</span>
				</div>
<!-- careLevel -->
				<div class="btn-group btn-group-vertical btn-row hide-show12" data-toggle="buttons">
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
      			<div class="btn-group btn-group-vertical btn-row hide-show12" data-toggle="buttons">
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
      			<div class="btn-group btn-group-vertical btn-row hide-show1" data-toggle="buttons">
			    	<span class="inputQ">�d����ı���a��</span>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="0" 
	          			<c:if test="${ssVO.overnightLoc=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �H�N</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="1" 
	          			<c:if test="${ssVO.overnightLoc=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> �b�ɤW</span>
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
			    <div class="btn-group hide-show12" data-toggle="buttons">
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
			    <div class="btn-group hide-show12" data-toggle="buttons">
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
      			<div class="btn-group eqptDiv" data-toggle="buttons">
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
			    
<!-- srvFee -->
			    <div class="fee-group">
			    	<span class="fee-group-inputQ">�A�ȶO�v</span>
			    	<input class="input1 fee-group-input" name="srvFee" placeholder="200" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${ssVO.srvFee}" ><span class="unit srvFeeUnit"> / ��</span>
			    	<span class="fee-group-inputQ addB">�[���~������</span>
			    	<input class="input1 fee-group-input addB" name="addBathingFee" placeholder="100" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${addBathingFee==null?'':addBathingFee}" ><span class="unit addBathingUnit addB">/��</span>
			    	<span class="fee-group-inputQ addP">�[�����e����</span>
			    	<input class="input1 fee-group-input addP" name="addPickupFee" placeholder="30" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${addPickupFee==null?'':addPickupFee}" ><span class="unit addPickupUnit addP">/����</span>
			    </div>
<!-- srvTime -->
			    <div class="quantity hide-srvTime">
			    	<span class="quantity-inputQ">�A�Ȯɶ�</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min="0" max="8" step="1" name="srvTimeH" 
  						value="${ssVO.srvTime==null?'1':ssVO.srvTime.substring(0,1)}" ><span class="unit">�p��</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min="0" max="30" step="30" name="srvTimeM" 
  						value="${ssVO.srvTime==null?'0':ssVO.srvTime.substring(1,2)}" ><span class="unit">��</span>
				</div>
<!-- srvInfo -->	
			    <span class="inputQ" style="padding-top: 10px;">�A�Ȥ��e�]�t</span>
<!-- 			    <div id="toolbar-container"></div> -->
<!--     			<div id="editor1"></div> -->
<%-- 			    <input type="hidden" name="srvInfo" value="${param.srvInfo==null?'':param.srvInfo}"> --%>
				<textarea id="editor1" name="srvInfo" value="${ssVO.srvInfo==null?'':ssVO.srvInfo}"></textarea>
		</div>

		<div class="btnGp">
			<input type="button" name="previous" class="cancel action-button" value="�����ק�" />
			<input type="hidden" name="action" value="update">
			<input type="button" class="submit action-button" value="�e�X�ק�" />
		</div>
		
	</div>

<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>


<!-- �פJjs -->
	<script src="/EA103G3/js/euphy/jquery-3.2.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/classic/ckeditor.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/classic/translations/zh.js"></script>
	<script src="/EA103G3/js/euphy/acpPetNum.js"></script>
	<script>
	$(document).ready(function(){
		
		
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
		
		var tempInfo = `${ssVO.srvInfo}`;
		if (tempInfo.length >1){
// 			tempInfo = tempInfo.substring(3, tempInfo.length-4);
			editor.append(tempInfo);
		}
	});
	
	
	</script>
</body>
</html>