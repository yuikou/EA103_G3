<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitOffDay.model.*, com.sitSrv.model.*, com.petSitter.model.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>保姆的所有證照</TITLE>

<!-- 匯入外部CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<!-- Bootstrap datetimepicker & fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/css/tempusdominus-bootstrap-4.min.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/sitOffDay.css">


</head>

<BODY>

<!-- 內文body -->
    <div class="container">
    
    	<!-- 測試先鎖定sitNo=S001 -->
		<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
		<% PetSitterVO petSitterVO = (PetSitterVO) session.getAttribute("petSitterVO"); %>
		
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
		<!-------------------------------- 服務選單 -------------------------------->
		<div class="section-line">
			<select id="mySrv1" name="sitSrvNo">
				<c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.getSitNo())}">
					<option name="sitSrvNo ${sitSrvVO.sitSrvCode}" value="${sitSrvVO.sitSrvNo}">${sitSrvVO.sitSrvName}</option>
				</c:forEach>
			</select>
	   	</div>
	
		<div class="row">
            <div class='col-12 col-md-3'>
            	<!-------------------------------- 小月曆 -------------------------------->
			    <div class="form-group">
			    	<div class="col-12" style="padding: 0; width:400px">
			            <div id="datetimepicker13"></div>
			        </div>
			    </div>
			    <!------------------------------可預約時段 -------------------------------->
			    <div class="appointmentDates appointmentHide" id="appointment">
			    	<span id="spanDate">可預約時段</span>
		            <div class="appointmentSlots slots">
						<div class="appointmentSlotsContainer">
						<% 
							String startT = petSitterVO.getSrvSTime();
							String endT = petSitterVO.getSrvETime();
							List<String> ssTlist = new ArrayList<String>();
							
							int srvT = 100;// 先以一小時測試
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
			</div>
        </div>
	</div>
	<!-- 內文end -->
	
	<!-- 匯入js -->
    <c:set var="jsPath" value="/EA103G3/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/locale/zh-tw.min.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/js/tempusdominus-bootstrap-4.min.js"></script>
    <script type="text/javascript">
        $(function() {
        	// 判斷預約時間是否要顯示
        	var sitSrvCode = $("#mySrv1 option:selected").attr("name");
        	if (sitSrvCode.indexOf("DropIn") > -1 || sitSrvCode.indexOf("DogWalking") > -1 || sitSrvCode.indexOf("PetTaxi") > -1){
        		$("#appointment").removeClass("appointmentHide");
        	} else {
        		$("#appointment").addClass("appointmentHide");
        	}
        	
        	var sitOffDayObj = null;
        	var today = new Date();
        	// spanDate
        	var spanDate = $("#spanDate").text(dateFormat(today)+" 可預約時段");
        	// 首次建立月曆時(oneSrv)發送ajax取得資料
        	$.ajax({
		        type: "GET",
		       	url: "sitOffDay.do?action=getOneSitSrvOffDay",
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
                	console.log("ajax失敗");
                	console.log(xhr.responseText);
                }
    		
    		});
        	
        	
        	// 更換服務項目(anotherSrv)後發送ajax取得新資料，並重新建立新月曆
        	$("#mySrv1").on("change", function () {
        		console.log("送出sitSrvNo：" + $(this).val());
        		
    			$.ajax({
    		        type: "GET",
    		       	url: "sitOffDay.do?action=getOneSitSrvOffDay",
    		   		data: {sitSrvNo: $(this).val(),},
    		        dataType: "json",
    		        cache: false,
    		        success: function (result) {
    		        	// 預約時間
    		        	sitSrvCode = $("#mySrv1 option:selected").attr("name");
    		        	console.log(sitSrvCode);
    		        	if (sitSrvCode.indexOf("DropIn") > -1 || sitSrvCode.indexOf("DogWalking") > -1 || sitSrvCode.indexOf("PetTaxi") > -1){
    		        		$("#appointment").removeClass("appointmentHide");
    		        	} else {
    		        		$("#appointment").addClass("appointmentHide");
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
        	});
        	
        	// 點選月曆日期，更改可預約時間
        	$("#datetimepicker13").on("change.datetimepicker", function (e) {
        		if (e.oldDate == null) {
        			e.oldDate = dateFormat(new Date());
        		} else {
        			e.oldDate = dateFormat(new Date(e.oldDate));
        		}
        		e.date = dateFormat(new Date(e.date));
        		$("#spanDate").text(e.date+" 可預約時段");
        		
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