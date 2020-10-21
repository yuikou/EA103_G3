<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitSrv.model.*, com.petSitter.model.*"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>xx�O�i���i�A��</title>

<!-- �פJ�~��CSS -->
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
			<!-- ���~�C�� -->
			<div class="errorList-allSrv"> 
			<c:if test="${not empty errorMsgs}" >
				<font style="color:red;">�o�ͥH�U���~�G</font>
				<ul>
				<c:forEach var="msg" items="${errorMsgs}">
					<li style="color:red;">${msg}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>
			<a class="addAct" href="${pageContext.request.contextPath}/sitSrv/sitSrv.do?action=transfer"><i class="material-icons">queue</i><span>�s�W�A��</span></a>
		</div>
	
		<div class="col-10 col-sm-12 wrap">
		
			<div class="col-sm-12 thead">
				<div class="col-5 col-sm-5 thead-th thName">�A�ȦW��</div>
				<div class="col-3 col-sm-2 thead-th thFee">�O�v<small>/���</small></div>
				<div class="col-2 col-sm-2 thead-th thStatus">�A�Ȫ��A</div>
				<div class="col-2 col-sm-3 thead-th thAction">�A�ȳ]�w</div>
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
								<c:if test="${ssVO.sitSrvCode=='Boarding'}">��</c:if>
			        			<c:if test="${ssVO.sitSrvCode=='DayCare'}">��</c:if>
			        			<c:if test="${ssVO.sitSrvCode=='PetTaxi'}">����</c:if>
			        			<c:if test="${ssVO.sitSrvCode!='Boarding' && ssVO.sitSrvCode!='DayCare' && ssVO.sitSrvCode!='PetTaxi'}">��</c:if>
							</small>
						</div>
					</div>
					<div class="col-2 col-sm-2 tbody-td ssStatusDiv">
						<c:if test="${ssVO.isDel==1}"><span>�פ�A��</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==0}"><span class="normalSrv">�A�Ȥ�</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==1}"><span class="pauseSrv">�w�Ȱ�</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==2}"><span class="verifying">�ҷӫݼf��</span></c:if>
						<c:if test="${ssVO.isDel==0 && ssVO.outOfSrv==3}"><span class="verifying">�Эק��ҷ�</span></c:if>
					</div>
					<div class="col-2 col-sm-3 tbody-td ssAct">
				<!-----�A�Ƚs��----->
						<input type="hidden" name="sitSrvNo" value="${ssVO.sitSrvNo}">
				<!-----����ק�----->
						<a class="modifyAct <c:out value="${ssVO.outOfSrv=='0'? '' : 'disabled'}" />" 
						   data-title="�ק�A��" href="${pageContext.request.contextPath}/sitSrv/sitSrv.do?action=getOne_For_Update&sitSrvNo=${ssVO.sitSrvNo}"><i class="table__icon material-icons">tune</i></a>
				
				<!-----�����----->		
						<a class="offDayAct <c:out value="${ssVO.outOfSrv=='0'? '' : 'disabled'}" />"
						   data-title="�w�ƥ�" href="${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=transfer&sitSrvNo=${ssVO.sitSrvNo}"><i class="table__icon material-icons">event_busy</i></a>
				
				<!-----����Ȱ�----->		
						<a class="pauseAct <c:out value="${ssVO.outOfSrv!='2'? '' : 'disabled'}" />"
						   data-title="<c:out value="${ssVO.outOfSrv=='0'?'�Ȱ��ȪA':'���ҪA��'}"/>" href=""><i class="table__icon material-icons"><c:out value="${ssVO.outOfSrv=='0'?'visibility_off':'visibility'}"/></i></a>
				
				<!-----����R��----->		
						<a class="delAct <c:out value="${ssVO.outOfSrv!='2'? '' : 'disabled'}" />"
						   data-title="�R���A��" href=""><i class="table__icon material-icons">delete</i></a>
						
					</div>
				</div>
				</c:if>
				</c:forEach>
			</div>
			
		</div>
	
	</div>


<!-- �פJjs -->
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
						// �Ȱ��A��
						swal({
				            title: "�Ȱ��A��",
				            text: "�T�w�n�Ȱ� "+name.text()+" ?",
				            type: "warning", // type can be "success", "error", "warning", "info", "question"
				            showCancelButton: true,
				            showCloseButton: true,
						}).then(function(isConfirm) {
		 		        	//-----------------------------�o�eajax---------------------------------------
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
		 			                    	swal("����!", "�A�Ȥw�Ȱ�", "success");
		 		                    		pauseLink.attr("data-title", "���ҪA��");
		 		                    		pauseIcon.text("visibility");
		 		                    		modifyAct.prop("disabled", true).addClass("disabled");
		 		                    		offDayAct.prop("disabled", true).addClass("disabled");
		 		                    		status.text("�w�Ȱ�").removeClass("normalSrv").addClass("pauseSrv");
		 		                    	} else {
		 		                    		swal("����", "�Ȱ��A�ȥ���", "error");
		 		                    	}
		 		                    },
		 		                    error: function (xhr, ajaxOptions, thrownError) {
		 		                    	swal("����", "ajax����", "error");
		 		                    }
		 		                });
		 		            }
						}, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
		 		        	if (dismiss == "cancel") {}
		 		        });
						
					} else {
						// ���ҪA��
						swal({
				            title: "���ҪA��",
				            text: "�T�w�n���� "+name.text()+" ?",
				            type: "warning", // type can be "success", "error", "warning", "info", "question"
				            showCancelButton: true,
				            showCloseButton: true,
						}).then(function(isConfirm) {
		 		        	//-----------------------------�o�eajax---------------------------------------
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
		 			                    	swal("����!", "�A�Ȥw����", "success");
		 		                    		pauseLink.attr("data-title", "�Ȱ��A��");
		 		                    		pauseIcon.text("visibility_off");
		 		                    		modifyAct.prop("disabled", true).removeClass("disabled");
		 		                    		offDayAct.prop("disabled", true).removeClass("disabled");
		 		                    		status.text("�A�Ȥ�").removeClass("pauseSrv").addClass("normalSrv");;
		 		                    	} else {
		 		                    		swal("����", "���ҪA�ȥ���", "error");
		 		                    	}
		 		                    },
		 		                    error: function (xhr, ajaxOptions, thrownError) {
		 		                    	swal("����", "ajax����", "error");
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
			            title: "�R���A��",
			            text: "�T�w�n�R�� "+name.text()+" ? �R����N�ݤ����o!!!",
			            type: "warning", // type can be "success", "error", "warning", "info", "question"
			            showCancelButton: true,
			            showCloseButton: true,
					}).then(function(isConfirm) {
	 		        	//-----------------------------�o�eajax---------------------------------------
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
	 			                    	swal("����!", "�A�Ȥw�R��", "success");
	 		                    		tbody_tr.remove();
	 		                    	} else {
	 		                    		swal("����", "�R���A�ȥ���", "error");
	 		                    	}
	 		                    },
	 		                    error: function (xhr, ajaxOptions, thrownError) {
	 		                    	swal("����", "ajax����", "error");
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