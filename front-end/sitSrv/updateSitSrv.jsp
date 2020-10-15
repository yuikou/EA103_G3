<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitSrv.model.*"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>修改托養服務</title>

<!-- 匯入外部CSS -->
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
	
	
		<div class="wrap updateBox">
			<h3>
			<c:out value="${ssVO.sitSrvCode=='Boarding'?'寵物住宿寄養':''}"/>
			<c:out value="${ssVO.sitSrvCode=='DayCare'?'寵物日托安親':''}"/>
			<c:out value="${ssVO.sitSrvCode=='DropIn'?'到府寵物保姆':''}"/>
			<c:out value="${ssVO.sitSrvCode=='DogWalking'?'遛狗服務':''}"/>
			<c:out value="${ssVO.sitSrvCode=='PetTaxi'?'寵物計程車':''}"/>
			</h3>
			<div class="tab-content">
				<span class="inputQ" style="width: 100%;">服務名稱</span>
				<div class="tab-content-info"><small> 您可以結合城市名稱、服務類型或是您所服務的寵物類型，作為您的服務名稱更容易讓飼主記住喔~</small></div>
			    <input class="input1 ssName" type="text" name="sitSrvName" maxlength="20" value="${ssVO.sitSrvName}"/>
			    <div class="btn-group" data-toggle="buttons">
        			<label class="btn">
          				<input class="input1" type="checkbox" name='addBathing' value="1" 
          				<c:if test="${ssVO.addBathing=='1'}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價洗澡</span>
        			</label>
        			<label class="btn">
          				<input class="input1" type="checkbox" name='addPickup' value="1" 
          				<c:if test="${ssVO.addPickup=='1'}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 加價接送</span>
        			</label>
      			</div>
        	</div>
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
        			<label class="btn">
          				<input class="input1" type="checkbox" name='acpPetTypPart0' value="cat" 
          				<c:if test="${ssVO.acpPetTyp<=4}"> checked='checked'</c:if>
          				><i class="fa fa-square-o fa-2x"></i><i class="fa fa-check-square-o fa-2x"></i><span> 貓</span><small>one size</small>
        			</label>
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
      			<div class="btn-group btn-group-vertical btn-row hide-show12" data-toggle="buttons">
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
      			<div class="btn-group btn-group-vertical btn-row hide-show1" data-toggle="buttons">
			    	<span class="inputQ">寵物睡覺的地方</span>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="0" 
	          			<c:if test="${ssVO.overnightLoc=='0'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 隨意</span>
	        		</label>
	        		<label class="btn">
	          			<input type="radio" name='overnightLoc' value="1" 
	          			<c:if test="${ssVO.overnightLoc=='1'}"> checked='checked'</c:if>
	          			><i class="fa fa-circle-o fa-2x"></i><i class="fa fa-check-circle-o fa-2x"></i><span> 在床上</span>
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
			    <div class="btn-group hide-show12" data-toggle="buttons">
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
			    <div class="btn-group hide-show12" data-toggle="buttons">
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
      			<div class="btn-group eqptDiv" data-toggle="buttons">
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
			    
<!-- srvFee -->
			    <div class="fee-group">
			    	<span class="fee-group-inputQ">服務費率</span>
			    	<input class="input1 fee-group-input" name="srvFee" placeholder="200" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${ssVO.srvFee}" ><span class="unit srvFeeUnit"> / 晚</span>
			    	<span class="fee-group-inputQ addB">加價洗澡價格</span>
			    	<input class="input1 fee-group-input addB" name="addBathingFee" placeholder="100" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${addBathingFee==null?'':addBathingFee}" ><span class="unit addBathingUnit addB">/次</span>
			    	<span class="fee-group-inputQ addP">加價接送價格</span>
			    	<input class="input1 fee-group-input addP" name="addPickupFee" placeholder="30" onkeyup="value=value.replace(/[^\d]/g,'')" 
			    		value="${addPickupFee==null?'':addPickupFee}" ><span class="unit addPickupUnit addP">/公里</span>
			    </div>
<!-- srvTime -->
			    <div class="quantity hide-srvTime">
			    	<span class="quantity-inputQ">服務時間</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min="0" max="8" step="1" name="srvTimeH" 
  						value="${ssVO.srvTime==null?'1':ssVO.srvTime.substring(0,1)}" ><span class="unit">小時</span>
  					<input class="input1 quantity-input" type="number" onfocus=this.blur() min="0" max="30" step="30" name="srvTimeM" 
  						value="${ssVO.srvTime==null?'0':ssVO.srvTime.substring(1,2)}" ><span class="unit">分</span>
				</div>
<!-- srvInfo -->	
			    <span class="inputQ" style="padding-top: 10px;">服務內容包含</span>
<!-- 			    <div id="toolbar-container"></div> -->
<!--     			<div id="editor1"></div> -->
<%-- 			    <input type="hidden" name="srvInfo" value="${param.srvInfo==null?'':param.srvInfo}"> --%>
				<textarea id="editor1" name="srvInfo" value="${ssVO.srvInfo==null?'':ssVO.srvInfo}"></textarea>
		</div>

		<div class="btnGp">
			<input type="button" name="previous" class="cancel action-button" value="取消修改" />
			<input type="hidden" name="action" value="update">
			<input type="button" class="submit action-button" value="送出修改" />
		</div>
		
	</div>

<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>


<!-- 匯入js -->
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
	            placeholder: '為了讓飼主感到安心，請您描述服務包含或不包含內容的簡單說明...',
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