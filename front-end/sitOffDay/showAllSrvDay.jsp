<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitSrv.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>�O�i���ɶ��w��</TITLE>

<!-- �פJ�~��CSS -->
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
		
		
		<!-- ���ե���wsitNo=S005 -->
        <jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
        <jsp:useBean id="petSitterVO" scope="session" class="com.petSitter.model.PetSitterVO"/>
		<% 
			List<SitSrvVO> list = sitSrvSvc.get_OneSit_AllSrv(petSitterVO.getSitNo());
			// ���F����]�w��
			List<String> sitSrvArrList = new ArrayList<String>();
			for (SitSrvVO ssVO : list) {
				if (!"Bathing".equals(ssVO.getSitSrvCode()) && !"Pickup".equals(ssVO.getSitSrvCode())) {
					sitSrvArrList.add(ssVO.getSitSrvNo());
				}
			}
			session.setAttribute("sitSrvArrList", sitSrvArrList);
		%>
    
		<div class="row myRow">
            <h1 class="myH1">�d���O�i�A�Ȥ�{</h1>
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
		
			<!---------- ��ƾ� ---------->
            <div class="myCld" id="calendar"></div>
            
            <!---------- �ާ@�� ---------->
            <div class="myCldCURD">
            
            	<div class="row">
		            <div class="myCldInfo">
		                <span class="cal-legend-off">�@</span> ��
		                <span class="cal-legend-busy">�@</span> �w��
		                <p>�п�����/��䪺�ɶ��Ӧw�ƥ�~</p>
		            </div>
		        </div>
            	
            	<Form id="odForm" class="odForm" action="${pageContext.request.contextPath}/sitOffDay/sitOffDay.do" method="post">
					<span class="myform-title">
	                       	�]�w�𰲤�
	                </span>            	
            		<!-- �H�U���o�s�W�𰲪��Ѽ� -->
						<div class="odBox">
							<div class="input-info">
								�A�ȶ���<span>*</span>
                                <div class="add-note">
                                    <span>�п�ܦܤ֤@�تA��</span>
                                </div>
                           	</div>
                           	
                           	
				<!--  1-sitSrvNo  -->
				           	<div class="wrap-input100 validate-input" style="height:160px;" data-validate="�п���ܤ֣����n�w�ƥ𰲪��A��">
	                            <select class="input100 mySrv" id="mySrv2" name="sitSrvNo" multiple>
				           			<option value="all" selected>����</option>
	                            
	                            <c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(petSitterVO.sitNo)}">
	                            	<c:if test="${sitSrvVO.outOfSrv == 0 && sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup'}">
				           			<option value="${sitSrvVO.sitSrvNo}">${sitSrvVO.sitSrvName}</option>
				           			</c:if>
				           		</c:forEach>
	                            
	                            </select>
                        	</div>
                        	
                <!--  2-offDay  -->
				           	<div class="input-info">
				           		�𰲤�(�_)
                           	</div>
				           	<div class="wrap-input100">
				           		<input class="input100" type="date" name="offDayS">
				           	</div>
				           	<div class="input-info">
				           		�𰲤�(�W)
                           	</div>
				           	<div class="wrap-input100">
				           		<input class="input100" type="date" name="offDayE">
				           	</div>
				           	
				<!--  3-offTime  -->
				           	<div class="input-info hideDiv_temp">
				           		�}�l�ɶ�
                           	</div>
				           	<div class="wrap-input100 hideDiv_temp">
				           		<input class="input100" type="time" name="offTimeS">
				           	</div>
				           	<div class="input-info hideDiv_temp">
				           		�����ɶ�
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
				           		�𰲱Ƶ{�|�v�T�z���Ѫ��A�ȡA�нT�{��ƵL�~�A�e�X
                           	</div>
					       	<div class="container-login100-form-btn">
					       		<input class="input100 hideDiv" type="text" name="groupId">
					       		<input class="input100 hideDiv" type="text" name="offDayNo">
						   		<input type="hidden" name="action" value="add">
						   		<input class="login100-form-btn input100 sendBtn halfBtn hideDiv" id="updateBtn" type="button" value="�ק�">
						   		<input class="login100-form-btn input100 sendBtn" id="setOffDay" type="submit" value="�s�W">
                        	</div>
						</div>
            	</Form>
            </div>
        </div>
	</div>
	
<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>
    
    <!-- �פJjs -->
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
		    	month: '��',
		    	week: '�P',
		    	day: '��'
		    },
		    locale: 'zh-tw',
		    initialView: 'dayGridMonth',
		    slotDuration: '00:30',
		    unselectAuto: false,
		    allDaySlot: false, // �b�g�䤣���all-day
		    selectOverlap : false, // �T�����w�g���q�檺���
		    navLinks: true, // can click day/week names to navigate views
		    editable: true, // can drag events and resize the end date
		    selectable: true,
		    dayMaxEvents: true, // allow "more" link when too many events
		    
		    // �O�i�C��A�Ȯɶ�
		    slotMinTime: '08:00:00',
		    slotMaxTime: '20:00:00',
		    
		    // �]�w�i�ާ@���(�@�Ӥ�)
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
  			
  			// ���U�i�����ܡjOK
  			select: function(info) {
  				allDay: true,
  				selectDay(info);
  			},
  			
			// ���U�i�ƥ��I��j
  	        eventClick: function(info) {
				editOffDayInfo(info);
				if (info.event.extendedProps.offDayTyp == 1 && info.event.allDay == false) {
					calendar.changeView("timeGridDay");
					calendar.gotoDate(info.event.start);
				}
			},
			
			// ���U�i�ƥ�첾�j
			eventDrop: function(info) {
				//-----------------------------�קﵲ�����----------------------------------------
				var startDay = info.event.startStr;
				var endDay = info.event.end;
				endDay.setDate(endDay.getDate()-1);
				var endMonth = endDay.getMonth()+1;
				
				if (endDay.getDate() < 10) {
					endDay = endDay.getFullYear() + "-" + endMonth + "-" + "0"+endDay.getDate();
				} else {
					endDay = endDay.getFullYear() + "-" + endMonth + "-" + endDay.getDate();
				}
				//-----------------------------�}�l�s�W���----------------------------------------
		     	editOffDayInfo(info);
				var arr = $("#mySrv2").val();
// 				console.log("groupId="+$("[name='groupId']").val());
				swal({
		            title: "�ק�"+info.event.title+"���",
		            text: startDay +"~"+ endDay,
		            type: "warning", // type can be "success", "error", "warning", "info", "question"
		            showCancelButton: true,
		            showCloseButton: true,
		        }).then(function(isConfirm) {
		        	//-----------------------------�o�eajax---------------------------------------
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
			                    	swal("����!", "���/�ɶ��w�g�ק�", "success");
		                    	} else {
		                    		info.revert();
		                    		if (result=="repeat") {
		                    			var errorMsgs = "���/�ɶ����ơA�Э��s�T�{";
		                    		} else {
		                    			var errorMsgs = "�o�Ϳ��~";
		                    		}
		                    		swal({
		                                title: "�ק異��",
		                                text: errorMsgs,
		                                type: "error", // type can be "success", "error", "warning", "info", "question"
		                                showCloseButton: true,
		                            })
		                    	}
		                    },
		                    error: function (xhr, ajaxOptions, thrownError) {
		                    	console.log("ajax����");
		                    	info.revert();
		                    	swal("����", "���/�ɶ����Q�ק�", "error");
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
			// ���U�i�ƥ��V�j
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
		                		console.log("���X��"+i+"��json����G"+j.offDayS+"~"+j.offDayE);
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
			                       	title: (j.offDayTyp==0)? '��' : '�Q�w��',
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
		                    //�^�մ�V���
		                    successCallback(events);
		            	},
		        	})
				},
			}],
		});
		calendar.render();
		
		// �A�ȶ����I��ƥ�
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
		                	title: (j.offDayTyp==0)? '��' : '�Q�w��',
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
		          	//�^�մ�V���
		         	calendar.removeAllEventSources();
		         	calendar.addEventSource(events);
		      	},
		  	})
		});
		
		// �e�X�ק�	
		var updateBtn = document.getElementById("updateBtn");
		updateBtn.addEventListener("click", function(){
			$("[name='action']").val("update");
			$("#odForm").submit();
		});
		
		
	});
	
	// getAll��ƨ��~�S�Ψ�
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
	// �ק����榡
    function timeFormat(day, time) {
        return day.getFullYear() + '-' + (day.getMonth() + 1) + '-' + day.getDate() + 'T' + time.substr(0, 2) + ":" + time.substr(-2, 2);
    }
   	
	// �i�����ܡj
	function selectDay(info) {
		$("[name='action']").val("add");
		$("[id='setOffDay']").val("�s�W").removeClass("halfBtn");
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
	
	// �i�ƥ��I��j
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
			$("[id='setOffDay']").val("�R��").addClass("halfBtn");
			$("[id='updateBtn']").removeClass("hideDiv");
			
			$("#mySrv2").val($("#mySrv1").val());
		} else {
			// do nothing
			$("[name='groupId']").val("");
			$("[name='offDayNo']").val("");
			$("[name='offDayS']").val("");
			$("[name='offDayE']").val("");
			$("[name='action']").val("add");
			$("[id='setOffDay']").val("�s�W").removeClass("halfBtn");
			$("[id='updateBtn']").addClass("hideDiv");
		}
	}
	
    </script>
	
</body>
</html>