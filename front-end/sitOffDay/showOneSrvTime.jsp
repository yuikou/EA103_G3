<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitOffDay.model.*, com.sitSrv.model.*, com.petSitter.model.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>

<!-- �פJ�~��CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!-- Bootstrap datetimepicker & fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/css/tempusdominus-bootstrap-4.min.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/sitOffDay.css">


</head>

<BODY>

<!-- ����body -->
    <div class="container sod-container">
    
		<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
		<% PetSitterVO petSitterVO = (PetSitterVO) request.getAttribute("petSitterVO"); %>
		
		<!-------------------------------- ���~�C�� -------------------------------->
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
		<!------------------------------�i�w���ɬq -------------------------------->
		<div>
		    <c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.getSitNo())}">
			    <c:if test="${sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup' && sitSrvVO.sitSrvCode != 'Boarding' && sitSrvVO.sitSrvCode != 'DayCare'}">
			    <div class="appointmentDates appointmentHide" id="appointment_${sitSrvVO.sitSrvCode}">
		            <div class="appointmentSlots slots">
						<div class="appointmentSlotsContainer">
						<% 
							String startT = petSitterVO.getSrvSTime();
							String endT = petSitterVO.getSrvETime();
							List<String> ssTlist = new ArrayList<String>();
							SitSrvVO sitSrvVO = (SitSrvVO) pageContext.getAttribute("sitSrvVO");
							
							String srvTstr = sitSrvSvc.get_OneSit_OneSrv(sitSrvVO.getSitSrvNo()).getSrvTime();
							int srvT = 100;// �q�`�A�Ȯɶ��@�p��
							if (srvTstr!= null) {
								srvT = Integer.valueOf(srvTstr);// �p�G�A�ȶ��ئ��]�w�A�Ȯɶ�
							}
							if (startT != null){
								Integer startTint = Integer.valueOf(startT);
								Integer endTint = Integer.valueOf(endT);
								while(startTint<endTint){
									String temp = "0"+ startTint.toString();
									temp = temp.substring(temp.length()-4);
									if (temp.substring(2, 4).equals("50")){
										temp = temp.substring(0, 2) + ":30";
									} else {
										temp = temp.substring(0, 2) + ":00";
									}
									ssTlist.add(temp);
									startTint+=srvT;
								}
							}
							pageContext.setAttribute("ssTlist", ssTlist);
						%>
							<c:forEach var="ssT" items="${ssTlist}">
			                	<div class="appointmentSlot slot" data-value="${ssT}">${ssT}</div>
			                </c:forEach>
						</div>
		            </div>
				</div>
				</c:if>
			</c:forEach>
        </div>
        
	</div>
	<!-- ����end -->
	
	<!-- �פJjs -->
    <c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function() {
        	// �P�_�w���ɶ��O�_�n���
        	var sitSrvCode = $("#mySrv1 option:selected").attr("data-info");
        	if (sitSrvCode.indexOf("DropIn") > -1 || sitSrvCode.indexOf("DogWalking") > -1 || sitSrvCode.indexOf("PetTaxi") > -1){
        		$("#appointment_"+sitSrvCode).removeClass("appointmentHide");
        	} 
        	$("#srvInfo_"+sitSrvCode).removeClass("srvInfo-hide");
        	
        	var sitOffDayObj = null;
        	var today = new Date();
        	// �����إߤ���(oneSrv)�o�eajax���o���
        	$.ajax({
		        type: "GET",
		       	url: "${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=getOneSitSrvOffDay",
		   		data: {sitSrvNo: $("#mySrv1").val(),},
		        dataType: "json",
		        cache: false,
		        success: function (result) {
		        	var disabledDates = []; 
		        	sitOffDayObj = result;
		        	
		            $.each(result, function (i, j) {
		            	
		            	var offday = j.offDay;
		            	var offtime = j.offTime;
		            	
		            	if (offtime == null) {
		            		disabledDates.push(offday);
		            	} else if (offday == today){
		            		offtime = offtime.substr(0,2)+ ":" +offtime.substr(-2);
		            		$("[data-value='"+offtime+"']").addClass("hideOffTime");
		            	}
		        	}); 
		            console.log(disabledDates);
		            
		            $('#datetimepicker13').datetimepicker({
        	        	format: 'L',
        	        	inline: true,
        				disabledDates: disabledDates,
        				stepping: 30,
        				minDate: moment(),
        				maxDate: new Date(new Date().setDate(new Date().getDate() + 30)),
        	        });
		      	},
		      	error: function (xhr, ajaxOptions, thrownError) {
                	console.log("ajax����");
                	console.log(xhr.responseText);
                }
    		
    		});
        	
        	
        	// �󴫪A�ȶ���(anotherSrv)��o�eajax���o�s��ơA�í��s�إ߷s���
        	$("#mySrv1").on("change", function () {
//         		console.log("�e�XsitSrvNo�G" + $(this).val());
        		
    			$.ajax({
    		        type: "GET",
    		       	url: "${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=getOneSitSrvOffDay",
    		   		data: {sitSrvNo: $(this).val(),},
    		        dataType: "json",
    		        cache: false,
    		        success: function (result) {
    		        	// �w���ɶ�
    		        	sitSrvCode = $("#mySrv1 option:selected").attr("data-info");
    		        	console.log("sitSrvCode="+sitSrvCode);
    		        	if (sitSrvCode.indexOf("DropIn") > -1 || sitSrvCode.indexOf("DogWalking") > -1 || sitSrvCode.indexOf("PetTaxi") > -1){
    		        		$(".appointmentDates").addClass("appointmentHide");
    		        		$("#appointment_"+sitSrvCode).removeClass("appointmentHide");
    		        	} else {
    		        		$(".appointmentDates").addClass("appointmentHide");
    		        	}
    		        	
    		        	// ���i�w�����&�i�w���ɶ�
		        		var offDayArr = [];

		        		sitOffDayObj = result;
    		        	console.log(sitOffDayObj);
    		            
    		        	$.each(result, function (i, j) {
    		            	var offday = j.offDay;
    		            	var offtime = j.offTime;
    		            	
    		            	if (offtime == null) {
    		            		offDayArr.push(offday);
    		            	} else if (offday == dateFormat(today)){
    		            		offtime = offtime.substr(0,2)+ ":" +offtime.substr(-2);
    		            		$("[data-value='"+offtime+"']").addClass("hideOffTime");
    		            	}
    		        	}); 
    		            $('#datetimepicker13').datetimepicker("destroy");
    		            $('#datetimepicker13').datetimepicker({
	        	        	format: 'L',
	        	        	inline: true,
	        				disabledDates: offDayArr,
	        				stepping: 30,
	        				minDate: moment(),
	        				maxDate: new Date(new Date().setDate(new Date().getDate() + 30)),
	        	        });
    		      	},
    		      	error: function (xhr, ajaxOptions, thrownError) {
                    	console.log("ajax����");
                    }
        		
        		});
    			$(".srvInfo").each(function(){
    				$(this).addClass("srvInfo-hide");
    			});
    			var sitSrvCode_rn = $("#mySrv1 option:selected").attr("data-info");
    			$("#srvInfo_"+sitSrvCode_rn).removeClass("srvInfo-hide");
    			
        	});
        	
        	
        	// �I�������A���i�w���ɶ�
        	$("#datetimepicker13").on("change.datetimepicker", function (e) {
        		
        		if (e.oldDate == null) {
        			e.oldDate = dateFormat(new Date());
        		} else {
        			e.oldDate = dateFormat(new Date(e.oldDate));
        		}
        		e.date = dateFormat(new Date(e.date));
        		$(".spanDate").text(e.date+" �|���w���ɬq");
        		
        	    if (e.oldDate !== e.date) {
        	    	
            		var offDayArr = [];
            		
        		    $("[data-value]").removeClass("hideOffTime");
        		    $.each(sitOffDayObj, function (i, j) {
        		         var offday = j.offDay;
        		         var offtime = j.offTime;
        		            	
        		         if (offtime == null) {
        		            offDayArr.push(offday);
        		         } else if (offday == dateFormat(new Date(e.date))){
        		        	 offtime = offtime.substr(0,2)+ ":" +offtime.substr(-2);
        		             $("[data-value='"+offtime+"']").addClass("hideOffTime");
        		         }
        			}); 
        		}
        	});
        	
        });
        
        // �榡�Ƥ��
        function dateFormat(date) {
        	var yyyy = date.getFullYear();
        	var MM = (date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1));
        	var dd = date.getDate() < 10 ? ("0"+date.getDate()) : date.getDate();
        	var formatDate = yyyy + "-" + MM + "-" + dd;
        	return formatDate;
        }

	</script>
</body>
</html>