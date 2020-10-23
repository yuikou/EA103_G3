<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitSrv.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>保姆的時間安排</TITLE>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">  
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.minty.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fullcalendar.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/sitOffDay.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY>

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
		
		
		<!-- 測試先鎖定sitNo=S005 -->
        <jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
        <jsp:useBean id="petSitterVO" scope="session" class="com.petSitter.model.PetSitterVO"/>
		<% 
			List<SitSrvVO> list = sitSrvSvc.get_OneSit_AllSrv(petSitterVO.getSitNo());
			// 為了全選設定的
			List<String> sitSrvArrList = new ArrayList<String>();
			for (SitSrvVO ssVO : list) {
				if (!"Bathing".equals(ssVO.getSitSrvCode()) && !"Pickup".equals(ssVO.getSitSrvCode())) {
					sitSrvArrList.add(ssVO.getSitSrvNo());
				}
			}
			session.setAttribute("sitSrvArrList", sitSrvArrList);
		%>
    
		<div class="row myRow">
            <h1 class="myH1">寵物保姆服務日程</h1>
            <div class="section-line">
            	<select id="mySrv1" name="sitSrvNo">
	            <c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.sitNo)}">
	            	<c:if test="${sitSrvVO.outOfSrv == 0 && sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup'}">
					<c:if test="${sitSrvVO.sitSrvNo == sitSrvNoRN}">
				    <option name="sitSrvNo" value="${sitSrvVO.sitSrvNo}" selected>${sitSrvVO.sitSrvName}</option>
				    </c:if>
				    <c:if test="${sitSrvVO.sitSrvNo != sitSrvNoRN}">
				    <option name="sitSrvNo" value="${sitSrvVO.sitSrvNo}">${sitSrvVO.sitSrvName}</option>
				    </c:if>
				    </c:if>
				</c:forEach>
	            </select>
	        </div>
        </div>		
		
		
		<!-- fullcalendar -->
		<div class="row cldrow">
		
			<!---------- 行事曆 ---------->
            <div class="myCld" id="calendar"></div>
            
            <!---------- 操作框 ---------->
            <div class="myCldCURD">
            
            	<div class="row">
		            <div class="myCldInfo">
		                <span class="cal-legend-off">　</span> 休假
		                <span class="cal-legend-busy">　</span> 預約
		                <p>請選取月曆/日曆的時間來安排休假~</p>
		            </div>
		        </div>
            	
            	<Form id="odForm" class="odForm" action="${pageContext.request.contextPath}/sitOffDay/sitOffDay.do" method="post">
					<span class="myform-title">
	                       	設定休假日
	                </span>            	
            		<!-- 以下取得新增休假的參數 -->
						<div class="odBox">
							<div class="input-info">
								服務項目<span>*</span>
                                <div class="add-note">
                                    <span>請選擇至少一種服務</span>
                                </div>
                           	</div>
                           	
                           	
				<!--  1-sitSrvNo  -->
				           	<div class="wrap-input100 validate-input" style="height:160px;" data-validate="請選取至少ㄧ項要安排休假的服務">
	                            <select class="input100 mySrv" id="mySrv2" name="sitSrvNo" multiple>
				           			<option value="all" selected>全選</option>
	                            
	                            <c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.sitNo)}">
	                            	<c:if test="${sitSrvVO.outOfSrv == 0 && sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup'}">
				           			<option value="${sitSrvVO.sitSrvNo}">${sitSrvVO.sitSrvName}</option>
				           			</c:if>
				           		</c:forEach>
	                            
	                            </select>
                        	</div>
                        	
                <!--  2-offDay  -->
				           	<div class="input-info">
				           		休假日(起)
                           	</div>
				           	<div class="wrap-input100">
				           		<input class="input100" type="date" name="offDayS">
				           	</div>
				           	<div class="input-info">
				           		休假日(訖)
                           	</div>
				           	<div class="wrap-input100">
				           		<input class="input100" type="date" name="offDayE">
				           	</div>
				           	
				<!--  3-offTime  -->
				           	<div class="input-info hideDiv_temp">
				           		開始時間
                           	</div>
				           	<div class="wrap-input100 hideDiv_temp">
				           		<input class="input100" type="time" name="offTimeS">
				           	</div>
				           	<div class="input-info hideDiv_temp">
				           		結束時間
                           	</div>
                           	<div class="wrap-input100 hideDiv_temp">
				           		<input class="input100" type="time" name="offTimeE">
				           	</div>
				           	
				<!--  4-offDayTyp  -->
				           	<div class="wrap-input100 hideDiv">
				           		<input class="input100" type="text" name="offDayTyp" value="0">
				           	</div>
				           	
				<!--  action  -->
					       	<div class="input-info myP">
				           		休假排程會影響您提供的服務，請確認資料無誤再送出
                           	</div>
					       	<div class="container-login100-form-btn">
					       		<input class="input100 hideDiv" type="text" name="groupId">
					       		<input class="input100 hideDiv" type="text" name="offDayNo">
						   		<input type="hidden" name="action" value="add">
						   		<input class="login100-form-btn input100 sendBtn halfBtn hideDiv" id="updateBtn" type="button" value="修改">
						   		<input class="login100-form-btn input100 sendBtn" id="setOffDay" type="submit" value="新增">
                        	</div>
						</div>
            	</Form>
            </div>
        </div>
	</div>
	
<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>
    
    <!-- 匯入js -->
    <c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<!-- moment.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/locale/zh-tw.min.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/fullcalendar.js"></script>
	<script src="${jsPath}/main.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
	    var calendarEl = document.getElementById('calendar');
	    
	    var calendar = new FullCalendar.Calendar(calendarEl, {
	    	schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
	    	
	    	dayMinWidth: 105,
	    	contentHeight: 'auto',
	    	aspectRatio: 1.05,
		    themeSystem: 'bootstrap',
	    	
	    	headerToolbar: {
		       left: 'prev,next',
		       center: 'title',
		       right: 'dayGridMonth,timeGridWeek,timeGridDay'
		    },
		    buttonText: {
		    	prev: '',
		    	next: '',
		    	month: '月',
		    	week: '周',
		    	day: '日'
		    },
		    locale: 'zh-tw',
		    initialView: 'dayGridMonth',
		    slotDuration: '00:30',
		    unselectAuto: false,
		    allDaySlot: false, // 在週曆不顯示all-day
		    selectOverlap : false, // 禁止選取已經有訂單的日期
		    navLinks: true, // can click day/week names to navigate views
		    editable: true, // can drag events and resize the end date
		    selectable: true,
		    dayMaxEvents: true, // allow "more" link when too many events
		    
		    // 保姆每日服務時間
		    slotMinTime: '08:00:00',
		    slotMaxTime: '20:00:00',
		    
		    // 設定可操作日期(一個月)
		    validRange: function(nowDate) {
		    	
		    	var startDate = new Date(nowDate.valueOf());
		        var endDate = new Date(nowDate.valueOf());
		        startDate.setDate(startDate.getDate()); // One day in the past
		        endDate.setDate(endDate.getDate() + 90); // Three months into the future
		        return {
    				start: startDate,
    				end: endDate
   		 		};
  			},
  			
  			// 註冊【日期選擇】OK
  			select: function(info) {
  				allDay: true,
  				selectDay(info);
  			},
  			
			// 註冊【事件點選】
  	        eventClick: function(info) {
				editOffDayInfo(info);
				if (info.event.extendedProps.offDayTyp == 1 && info.event.allDay == false) {
					calendar.changeView("timeGridDay");
					calendar.gotoDate(info.event.start);
				}
			},
			
			// 註冊【事件拖移】
			eventDrop: function(info) {
				//-----------------------------修改結束日期----------------------------------------
				var startDay = info.event.startStr;
				var endDay = info.event.end;
				endDay.setDate(endDay.getDate()-1);
				var endMonth = endDay.getMonth()+1;
				
				if (endDay.getDate() < 10) {
					endDay = endDay.getFullYear() + "-" + endMonth + "-" + "0"+endDay.getDate();
				} else {
					endDay = endDay.getFullYear() + "-" + endMonth + "-" + endDay.getDate();
				}
				//-----------------------------開始新增資料----------------------------------------
		     	editOffDayInfo(info);
				var arr = $("#mySrv2").val();
// 				console.log("groupId="+$("[name='groupId']").val());
				swal({
		            title: "修改"+info.event.title+"日期",
		            text: startDay +"~"+ endDay,
		            type: "warning", // type can be "success", "error", "warning", "info", "question"
		            showCancelButton: true,
		            showCloseButton: true,
		        }).then(function(isConfirm) {
		        	//-----------------------------發送ajax---------------------------------------
		        	if (isConfirm) {
		        		$.ajax({
		                    type: "POST",
		                    url: "${pageContext.request.contextPath}/sitOffDay/sitOffDay.do",
		                    data: {
		                    	action:"update",
		                    	groupId: $("[name='groupId']").val(),
		                    	sitSrvNo: arr[0],
		                    	offDayS: $("[name='offDayS']").val(),
		                    	offDayE: $("[name='offDayE']").val(),
		                    	offTimeS: null,
		                    	offTimeE: null,
		                    	offDayTyp: 0,
		                    },
		                    dataType: "html",
		                    success: function (result) {
		                    	if (result.indexOf("G") != -1 ){
			                    	swal("完成!", "日期/時間已經修改", "success");
		                    	} else {
		                    		info.revert();
		                    		if (result=="repeat") {
		                    			var errorMsgs = "日期/時間重複，請重新確認";
		                    		} else {
		                    			var errorMsgs = "發生錯誤";
		                    		}
		                    		swal({
		                                title: "修改失敗",
		                                text: errorMsgs,
		                                type: "error", // type can be "success", "error", "warning", "info", "question"
		                                showCloseButton: true,
		                            })
		                    	}
		                    },
		                    error: function (xhr, ajaxOptions, thrownError) {
		                    	console.log("ajax失敗");
		                    	info.revert();
		                    	swal("失敗", "日期/時間未被修改", "error");
		                    }
		                });
		            }
		        }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
		        	if (dismiss == "cancel") {
			        	info.revert();
		        	}
		        });
			},
			
			
			progressiveEventRendering: true,
			// 註冊【事件渲染】
			eventSources: [{
				events: function getEventSources(fetchInfo, successCallback, failureCallback) {
// 		           	var now = new Date();
// 		            var data = timeChange(now.toLocaleDateString());
					$("#mySrv2").val($("#mySrv1").val());
		            var events = [];
		            $.ajax({
		            	type: "GET",
		           		url: "${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=getAll",
		       			data: {sitSrvNo: $("#mySrv1").val(),},
		            	dataType: "json",
		            	eventLimit: true,
		                success: function (result) {
		                	$.each(result, function (i, j) {
		                		console.log("取出第"+i+"筆json物件："+j.offDayS+"~"+j.offDayE);
				            	var dayS = new Date(j.offDayS);
				            	var dayE = new Date(j.offDayE);
				            	var timeS = j.offTimeS;
				            	var timeE = j.offTimeE;
				            	
				            	if(timeS==null) {
					            	dayE.setDate(dayE.getDate()+1);
				            	} else {
				            		timeE = parseInt(timeE);
				            		timeE+=100;
				            		timeE = ('0'+ timeE.toString()).substr(-4);
				            	}
				            	
		                		events.push({
			                       	id: j.offDayNo,
			                       	groupId: j.groupID,
			                       	allDay: (j.offTimeS==null)? true : false,
			                       	title: (j.offDayTyp==0)? '休假' : '被預約',
			                       	start: (timeS==null)? dayS : timeFormat(dayS,timeS),
			            		    end: (timeE==null)? dayE : timeFormat(dayE,timeE),
			            		    extendedProps: {
			    		               	offDayTyp: j.offDayTyp,
			    		            },
			                       	editable: (j.offDayTyp==0)? true : false,
			                       	selectable: true,
			                        backgroundColor: (j.offDayTyp==0)? '#505050' : '#FAA307',
			      		 		  	borderColor: (j.offDayTyp==0)? '#505050' : '#FAA307',
			                    }); 
		                    })
		                    //回調渲染日曆
		                    successCallback(events);
		            	},
		        	})
				},
			}],
		});
		calendar.render();
		
		// 服務項目點選事件
		$("#mySrv1").on("change", function () {
			calendar.changeView("dayGridMonth");
			$("#mySrv2").val($("#mySrv1").val());
			var events = [];
			$.ajax({
		        type: "GET",
		       	url: "${pageContext.request.contextPath}/sitOffDay/sitOffDay.do?action=getAll",
		   		data: {sitSrvNo: $("#mySrv1").val(),},
		        dataType: "json",
		        eventLimit: true,
		        success: function (result) {
		            $.each(result, function (i, j) {
		            	
		            	var dayS = new Date(j.offDayS);
		            	var dayE = new Date(j.offDayE);
		            	var timeS = j.offTimeS;
		            	var timeE = j.offTimeE;
		            	
		            	if(timeS==null) {
			            	dayE.setDate(dayE.getDate()+1);
		            	} else {
		            		timeE = parseInt(timeE);
		            		timeE+=100;
		            		timeE = ('0'+ timeE.toString()).substr(-4);
		            	}
		            	
		            	events.push({
		                	id: j.offDayNo,
		                    groupId: j.groupID,
		                    allDay: (timeS==null)? true : false,
		                	title: (j.offDayTyp==0)? '休假' : '被預約',
		                  	start: (timeS==null)? dayS : timeFormat(dayS,timeS),
		               		end: (timeE==null)? dayE : timeFormat(dayE,timeE),
		               		extendedProps: {
		               			offDayTyp: j.offDayTyp,
		               		},
		                 	editable: (j.offDayTyp==0)? true : false,
		                  	selectable: true,
		                 	backgroundColor: (j.offDayTyp==0)? '#505050' : '#FAA307',
		      		 		borderColor: (j.offDayTyp==0)? '#505050' : '#FAA307',
		               	}); 
		          	})
		          	//回調渲染日曆
		         	calendar.removeAllEventSources();
		         	calendar.addEventSource(events);
		      	},
		  	})
		});
		
		// 送出修改	
		var updateBtn = document.getElementById("updateBtn");
		updateBtn.addEventListener("click", function(){
			$("[name='action']").val("update");
			$("#odForm").submit();
		});
		
		
	});
	
	// getAll資料函數~沒用到
    function timeChange(s) {
        var now = new Date(s);
        var now2 = new Date(s);
        now2.setDate(now.getDate() + 90);
        month = now.getMonth() + 1;
        month2 = now2.getMonth() + 1;
        var date = {
            start: now.getFullYear() + "-" + month + "-" + now.getDate(),
            end: now2.getFullYear() + "-" + month2 + "-" + now2.getDate(),
        }
        var data = {
            startDate: date.start,
            endDate: date.end,
            sitSrvNo: $("#mySrv1").val(),
        }
        return data;
    }
	// 修改日期格式
    function timeFormat(day, time) {
        return day.getFullYear() + '-' + (day.getMonth() + 1) + '-' + day.getDate() + 'T' + time.substr(0, 2) + ":" + time.substr(-2, 2);
    }
   	
	// 【日期選擇】
	function selectDay(info) {
		$("[name='action']").val("add");
		$("[id='setOffDay']").val("新增").removeClass("halfBtn");
		$("[id='updateBtn']").addClass("hideDiv");
		
		$("[name='offDayS']").val(info.startStr);
		var endDate = info.end;
		endDate.setDate(endDate.getDate()-1);
		var endMonth = endDate.getMonth()+1;
			
		if (endDate.getDate() < 10) {
			$("[name='offDayE']").val(endDate.getFullYear() + "-" + endMonth + "-" + "0"+endDate.getDate());
		} else {
			$("[name='offDayE']").val(endDate.getFullYear() + "-" + endMonth + "-" + endDate.getDate());
		}
	}
	
	// 【事件點選】
	function editOffDayInfo(info) {
// 		alert(info.event.extendedProps.offDayTyp);
		
		if (info.event.extendedProps.offDayTyp == 0) {
			$("[name='offDayNo']").val(info.event.id);
			$("[name='offDayS']").val(info.event.startStr);
			
			var endDate = info.event.end;
			endDate.setDate(endDate.getDate()-1);
			var endMonth = endDate.getMonth()+1;
			
			if (endDate.getDate() < 10) {
				$("[name='offDayE']").val(endDate.getFullYear() + "-" + endMonth + "-" + "0"+endDate.getDate());
			} else {
				$("[name='offDayE']").val(endDate.getFullYear() + "-" + endMonth + "-" + endDate.getDate());
			}
			
			$("[name='groupId']").val(info.event.groupId);
			$("[name='action']").val("del");
			$("[id='setOffDay']").val("刪除").addClass("halfBtn");
			$("[id='updateBtn']").removeClass("hideDiv");
			
			$("#mySrv2").val($("#mySrv1").val());
		} else {
			// do nothing
			$("[name='groupId']").val("");
			$("[name='offDayNo']").val("");
			$("[name='offDayS']").val("");
			$("[name='offDayE']").val("");
			$("[name='action']").val("add");
			$("[id='setOffDay']").val("新增").removeClass("halfBtn");
			$("[id='updateBtn']").addClass("hideDiv");
		}
	}
	
    </script>
	
</body>
</html>