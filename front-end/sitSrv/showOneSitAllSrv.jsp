<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitSrv.model.*, com.petSitter.model.*"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>xx保姆托養服務</title>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>
<body>

	<div class="container-allSrv">
	
		<div class="addActDiv">
			<!-- 錯誤列表 -->
			<div class="errorList-allSrv"> 
			<c:if test="${not empty errorMsgs}" >
				<font style="color:red;">發生以下錯誤：</font>
				<ul>
				<c:forEach var="msg" items="${errorMsgs}">
					<li style="color:red;">${msg}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>
			<a class="addAct" href="${pageContext.request.contextPath}/sitSrv/sitSrv.do?action=transfer"><i class="material-icons">queue</i><span>新增服務</span></a>
		</div>
	
		<div class="col-10 col-sm-12 wrap">
		
			<div class="col-sm-12 thead">
				<div class="col-5 col-sm-5 thead-th thName">服務名稱</div>
				<div class="col-3 col-sm-2 thead-th thFee">費率<small>/單位</small></div>
				<div class="col-2 col-sm-2 thead-th thStatus">服務狀態</div>
				<div class="col-2 col-sm-3 thead-th thAction">服務設定</div>
			</div>
		
    		<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
			<jsp:useBean id="petSitterVO" scope="session" class="com.petSitter.model.PetSitterVO"/>
			
			<div class="col-sm-12 tbody">
				<c:forEach var="ssVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.sitNo)}">
				<c:if test="${ssVO.sitSrvCode != 'Bathing' && ssVO.sitSrvCode != 'Pickup'}">
				<div class="col-sm-12 tbody-tr">
					<div class="col-5 col-sm-5 tbody-td ssNameDiv">
						<i class="service-icon ${ssVO.sitSrvCode}-icon srvInfo-icon"></i>
						<span>${ssVO.sitSrvName}</span>
					</div>
					<div class="col-3 col-sm-2 tbody-td ssFeeDiv">
						<div>$ ${ssVO.srvFee}</div>
						<div>
							<small class="slash"> / </small>
							<small>
								<c:if test="${ssVO.sitSrvCode=='Boarding'}">晚</c:if>
			        			<c:if test="${ssVO.sitSrvCode=='DayCare'}">天</c:if>
			        			<c:if test="${ssVO.sitSrvCode=='PetTaxi'}">公里</c:if>
			        			<c:if test="${ssVO.sitSrvCode!='Boarding' && ssVO.sitSrvCode!='DayCare' && ssVO.sitSrvCode!='PetTaxi'}">次</c:if>
							</small>
						</div>
					</div>
					<div class="col-2 col-sm-2 tbody-td ssStatusDiv">
						<c:if test="${ssVO.isDel==1}"><span>終止服務</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==0}"><span class="normalSrv">服務中</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==1}"><span class="pauseSrv">已暫停</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==2}"><span class="verifying">證照待審核</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==3}"><span class="verifying">請修改證照</span></c:if>
					</div>
					<div class="col-2 col-sm-3 tbody-td ssAct">
				<!-----服務編號----->
						<input type="hidden" name="sitSrvNo" value="${ssVO.sitSrvNo}">
				<!-----跳轉修改----->
						<a class="modifyAct <c:out value="${ssVO.outOfSrv=='0'? '' : 'disabled'}" />" 
						   data-title="修改服務" href="${pageContext.request.contextPath}/sitSrv/sitSrv.do?action=getOne_For_Update&sitSrvNo=${ssVO.sitSrvNo}"><i class="table__icon material-icons">tune</i></a>
				
				<!-----跳轉休假----->		
						<a class="offDayAct <c:out value="${ssVO.outOfSrv=='0'? '' : 'disabled'}" />"
						   data-title="安排休假" href="${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=transfer&sitSrvNo=${ssVO.sitSrvNo}"><i class="table__icon material-icons">event_busy</i></a>
				
				<!-----執行暫停----->		
						<a class="pauseAct <c:out value="${ssVO.outOfSrv!='2'? '' : 'disabled'}" />"
						   data-title="<c:out value="${ssVO.outOfSrv=='0'?'暫停務服':'重啟服務'}"/>" href=""><i class="table__icon material-icons"><c:out value="${ssVO.outOfSrv=='0'?'visibility_off':'visibility'}"/></i></a>
				
				<!-----執行刪除----->		
						<a class="delAct <c:out value="${ssVO.outOfSrv!='2'? '' : 'disabled'}" />"
						   data-title="刪除服務" href=""><i class="table__icon material-icons">delete</i></a>
						
					</div>
				</div>
				</c:if>
				</c:forEach>
			</div>
			
		</div>
	
	</div>


<!-- 匯入js -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>
	<script>
		$(document).ready(function(){
			var pauseAct = $(".pauseAct");
			
			pauseAct.each(function(){
				$(this).click(function(e){
					e.preventDefault();
					var pauseLink = $(this);
					var pauseIcon = $(this).children("i");
					var input = $(this).prevAll("input");
					var name = $(this).parents(".tbody-tr").find(".ssNameDiv span");
					var status = $(this).parents(".tbody-tr").find(".ssStatusDiv span");
					var modifyAct = $(this).prevAll(".modifyAct");
					var offDayAct = $(this).prevAll(".offDayAct");
// 					alert(offDayAct.text())
					
					if (pauseIcon.text()=='visibility_off') {
						// 暫停服務
						swal({
				            title: "暫停服務",
				            text: "確定要暫停 "+name.text()+" ?",
				            type: "warning", // type can be "success", "error", "warning", "info", "question"
				            showCancelButton: true,
				            showCloseButton: true,
						}).then(function(isConfirm) {
		 		        	//-----------------------------發送ajax---------------------------------------
		 		        	if (isConfirm) {
		 		        		$.ajax({
		 		                    type: "POST",
		 		                    url: "${pageContext.request.contextPath}/sitSrv/sitSrv.do",
		 		                    data: {
		 		                    	action:"pauseSrv",
		  		                    	sitSrvNo: input.val(),
		 		                    },
		 		                    dataType: "html",
		 		                    success: function (result) {
		 		                    	if (result==1){
		 			                    	swal("完成!", "服務已暫停", "success");
		 		                    		pauseLink.attr("data-title", "重啟服務");
		 		                    		pauseIcon.text("visibility");
		 		                    		modifyAct.prop("disabled", true).addClass("disabled");
		 		                    		offDayAct.prop("disabled", true).addClass("disabled");
		 		                    		status.text("已暫停").removeClass("normalSrv").addClass("pauseSrv");
		 		                    	} else {
		 		                    		swal("失敗", "暫停服務失敗", "error");
		 		                    	}
		 		                    },
		 		                    error: function (xhr, ajaxOptions, thrownError) {
		 		                    	swal("失敗", "ajax失敗", "error");
		 		                    }
		 		                });
		 		            }
						}, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
		 		        	if (dismiss == "cancel") {}
		 		        });
						
					} else {
						// 重啟服務
						swal({
				            title: "重啟服務",
				            text: "確定要重啟 "+name.text()+" ?",
				            type: "warning", // type can be "success", "error", "warning", "info", "question"
				            showCancelButton: true,
				            showCloseButton: true,
						}).then(function(isConfirm) {
		 		        	//-----------------------------發送ajax---------------------------------------
		 		        	if (isConfirm) {
		 		        		$.ajax({
		 		                    type: "POST",
		 		                    url: "${pageContext.request.contextPath}/sitSrv/sitSrv.do",
		 		                    data: {
		 		                    	action:"rebootSrv",
		  		                    	sitSrvNo: input.val(),
		 		                    },
		 		                    dataType: "html",
		 		                    success: function (result) {
		 		                    	if (result==1){
		 			                    	swal("完成!", "服務已重啟", "success");
		 		                    		pauseLink.attr("data-title", "暫停服務");
		 		                    		pauseIcon.text("visibility_off");
		 		                    		modifyAct.prop("disabled", true).removeClass("disabled");
		 		                    		offDayAct.prop("disabled", true).removeClass("disabled");
		 		                    		status.text("服務中").removeClass("pauseSrv").addClass("normalSrv");;
		 		                    	} else {
		 		                    		swal("失敗", "重啟服務失敗", "error");
		 		                    	}
		 		                    },
		 		                    error: function (xhr, ajaxOptions, thrownError) {
		 		                    	swal("失敗", "ajax失敗", "error");
		 		                    }
		 		                });
		 		            }
		 		        }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
		 		        	if (dismiss == "cancel") {}
		 		        });
					}
					
				});
			});
			
			var delAct = $(".delAct");
			
			delAct.each(function(){
				$(this).click(function(e){
					e.preventDefault();
					var input = $(this).prevAll("input");
					var tbody_tr = $(this).parents(".tbody-tr");
					var name = $(this).parents(".tbody-tr").find(".ssNameDiv span");
					
					swal({
			            title: "刪除服務",
			            text: "確定要刪除 "+name.text()+" ? 刪除後就看不到囉!!!",
			            type: "warning", // type can be "success", "error", "warning", "info", "question"
			            showCancelButton: true,
			            showCloseButton: true,
					}).then(function(isConfirm) {
	 		        	//-----------------------------發送ajax---------------------------------------
	 		        	if (isConfirm) {
	 		        		$.ajax({
	 		                    type: "POST",
	 		                    url: "${pageContext.request.contextPath}/sitSrv/sitSrv.do",
	 		                    data: {
	 		                    	action:"delSrv",
	  		                    	sitSrvNo: input.val(),
	 		                    },
	 		                    dataType: "html",
	 		                    success: function (result) {
	 		                    	if (result==1){
	 			                    	swal("完成!", "服務已刪除", "success");
	 		                    		tbody_tr.remove();
	 		                    	} else {
	 		                    		swal("失敗", "刪除服務失敗", "error");
	 		                    	}
	 		                    },
	 		                    error: function (xhr, ajaxOptions, thrownError) {
	 		                    	swal("失敗", "ajax失敗", "error");
	 		                    }
	 		                });
	 		            }
	 		        }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
	 		        	if (dismiss == "cancel") {
	 		        	}
	 		        });
					
				});
			});
				
		});
	</script>
</body>
</html>