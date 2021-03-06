<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitOffDay.model.*, com.sitSrv.model.*, com.petSitter.model.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!-- Bootstrap datetimepicker & fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/css/tempusdominus-bootstrap-4.min.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/sitOffDay.css">


</head>

<BODY>

<!-- 內文body -->
    <div class="container sod-container">
    
		<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
		<% PetSitterVO petSitterVO = (PetSitterVO) request.getAttribute("petSitterVO"); %>
		
		<!-------------------------------- 錯誤列表 -------------------------------->
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
		<div>
        	<div class="title-change title-info">服務說明</div>
        	<div class="row">
        		<c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.getSitNo())}">
        		<div class='srvInfo srvInfo-hide' id="srvInfo_${sitSrvVO.sitSrvCode}">${sitSrvVO.srvInfo}</div>
        		</c:forEach>
        	</div>
        </div>
		<div class="title-change title-offDay">尚未預約日期/時間</div>
		<div class="row">
            <div class='col-12'>
            	<!-------------------------------- 小月曆 -------------------------------->
			    <div class="form-group">
			    	<div class="col-12" style="padding: 0">
			            <div id="datetimepicker13"></div>
			        </div>
			    </div>
			    <!------------------------------可預約時段 -------------------------------->
			    <c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.getSitNo())}">
			    <c:if test="${sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup' && sitSrvVO.sitSrvCode != 'Boarding' && sitSrvVO.sitSrvCode != 'DayCare'}">
			    <div class="appointmentDates appointmentHide" id="appointment_${sitSrvVO.sitSrvCode}">
			    	<span class="spanDate">尚未預約時段</span>
		            <div class="appointmentSlots slots">
						<div class="appointmentSlotsContainer">
						<% 
							String startT = petSitterVO.getSrvSTime();
							String endT = petSitterVO.getSrvETime();
							List<String> ssTlist = new ArrayList<String>();
							SitSrvVO sitSrvVO = (SitSrvVO) pageContext.getAttribute("sitSrvVO");
							
							String srvTstr = sitSrvSvc.get_OneSit_OneSrv(sitSrvVO.getSitSrvNo()).getSrvTime();
							int srvT = 100;
							if (srvTstr!= null) {
								srvT = Integer.valueOf(srvTstr);// 先以一小時測試
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
        
	</div>
	<!-- 內文end -->
	
	<!-- 匯入js -->
    <c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/locale/zh-tw.min.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/js/tempusdominus-bootstrap-4.min.js"></script>
    <script type="text/javascript">
        $(function() {
        	// 判斷預約時間是否要顯示
        	var sitSrvCode = $("#mySrv1 option:selected").attr("data-info");
        	if (sitSrvCode.indexOf("DropIn") > -1 || sitSrvCode.indexOf("DogWalking") > -1 || sitSrvCode.indexOf("PetTaxi") > -1){
        		$("#appointment_"+sitSrvCode).removeClass("appointmentHide");
        	}
        	
        	// 顯示服務說明
        	$("#srvInfo_"+sitSrvCode).removeClass("srvInfo-hide");
        	
        	var sitOffDayObj = null;
        	var today = new Date();
        	// spanDate
        	var spanDate = $(".spanDate").text(dateFormat(today)+" 尚未預約時段");
        	// 首次建立月曆時(oneSrv)發送ajax取得資料
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
                	console.log("ajax失敗" + xhr.responseText);
                }
    		
    		});
        	
        	
        	// 更換服務項目(anotherSrv)後發送ajax取得新資料，並重新建立新月曆
        	$("#mySrv1").on("change", function () {
//         		console.log("送出sitSrvNo：" + $(this).val());
        		
    			$.ajax({
    		        type: "GET",
    		       	url: "${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=getOneSitSrvOffDay",
    		   		data: {sitSrvNo: $(this).val(),},
    		        dataType: "json",
    		        cache: false,
    		        success: function (result) {
    		        	// 預約時間
    		        	sitSrvCode = $("#mySrv1 option:selected").attr("data-info");

    		        	if (sitSrvCode.indexOf("DropIn") > -1 || sitSrvCode.indexOf("DogWalking") > -1 || sitSrvCode.indexOf("PetTaxi") > -1){
    		        		$(".appointmentDates").addClass("appointmentHide");
    		        		$("#appointment_"+sitSrvCode).removeClass("appointmentHide");
    		        	} else {
    		        		$(".appointmentDates").addClass("appointmentHide");
    		        	}
    		        	
    		        	// 不可預約日期&可預約時間
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
                    	console.log("ajax失敗");
                    }
        		
        		});
    			$(".srvInfo").each(function(){
    				$(this).addClass("srvInfo-hide");
    			});
    			var sitSrvCode_rn = $("#mySrv1 option:selected").attr("data-info");
    			$("#srvInfo_"+sitSrvCode_rn).removeClass("srvInfo-hide");
    			
        	});
        	
        	// 點選月曆日期，更改可預約時間
        	$("#datetimepicker13").on("change.datetimepicker", function (e) {
        		
        		if (e.oldDate == null) {
        			e.oldDate = dateFormat(new Date());
        		} else {
        			e.oldDate = dateFormat(new Date(e.oldDate));
        		}
        		e.date = dateFormat(new Date(e.date));
        		$(".spanDate").text(e.date+" 尚未預約時段");
        		
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
        	
        	
        	// 保姆頁面的小月曆(初始化)
// 	        $('#datetimepicker13').datetimepicker({
// 	        	format: 'L', // 不顯示time
// 	        	inline: true,
// 				daysOfWeekDisabled: [0, 6],
// 				disabledDates: newArr, // 休假或是已經預約額滿
// 				stepping: 30, // 30分鐘或1小時
// 				minDate: moment(),
// 				maxDate: new Date(new Date().setDate(new Date().getDate() + 30)),
// 	        });
        	
        	// ----------------------追蹤----------------------
			var myClick = $(".myClick");
			
			myClick.click(function(e){
				var myClick_rn = $(this);
				var myClick_span = myClick_rn.find("span");
				var myClick_sitNo = myClick_rn.prev();
				var myClick_memNo = myClick_sitNo.prev();
				
				e.preventDefault();
				var myClick_action = null;
				if (myClick_span.hasClass("fa-heart")) {
					myClick_action = "del";
				} else {
					myClick_action = "add";
				}
				
				$.ajax({
	            	type: "POST",
	            	url: "${pageContext.request.contextPath}/sitFollow/sitFollow.do",
	            	data: {
	                	action: myClick_action,
	                	memNo: myClick_memNo.val(),
	                	sitNo: myClick_sitNo.val(),
	              	},
	              	dataType: "html",
	              	success: function (result) {
	                	if (result==1){
	                		myClick_rn.addClass("active");
	                		myClick_rn.addClass("active-2");
	                		
	                		setTimeout(function () {
	                			myClick_span.addClass("fa-heart");
	                			myClick_span.removeClass("fa-heart-o");
	                		}, 150);
	                		  
	                		setTimeout(function () {
	                			  myClick_rn.addClass("active-3");
	                		}, 150);
	                		  
	                		  
	                   	} else if (result==0) {
	                   		myClick_rn.removeClass("active");
	                   	  	
	                   		setTimeout(function () {
	                   			myClick_rn.removeClass("active-2");
	                   	  	}, 30);
	                   	  	
	                   		myClick_rn.removeClass("active-3");
	                   	  	
	                   	  	setTimeout(function () {
	                   	  		myClick_span.removeClass("fa-heart");
	                   	 		myClick_span.addClass("fa-heart-o");
	                   	  	}, 15);
	                   	} else if (result == "error"){
	                        alert("唉呦~出錯了");
	                    } else {
	                   		console.log("追蹤失敗"+result);
	                  	}
	              	},
	              	error: function (xhr, ajaxOptions, thrownError) {
	               		console.log("ajax失敗");
	               	}
	         	});
			});
        	
        });
        
        // 格式化日期
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