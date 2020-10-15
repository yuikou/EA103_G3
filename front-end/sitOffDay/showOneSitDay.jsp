<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>保姆的所有證照</TITLE>

<!-- 匯入外部CSS -->
<c:set var="path" value="/EA103G3/front-end" />

<link rel="stylesheet" type="text/css" href="${path}/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<!-- Bootstrap datetimepicker & fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/css/tempusdominus-bootstrap-4.min.css" />
<link rel="stylesheet" type="text/css" href="${path}/css/sitOffDay.css">


</head>

<BODY>

<!-- 內文body -->
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
	
		<!-- 測試用 -->
		<div class="row">
            <div class='col-12 col-md-6'>
                <div class="form-group">
                    <div class="input-group date" id="datetimepicker7" data-target-input="nearest">
                        <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker7" />
                        <div class="input-group-append" data-target="#datetimepicker7" data-toggle="datetimepicker">
                            <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class='col-12 col-md-6'>
                <div class="form-group">
                    <div class="input-group date" id="datetimepicker8" data-target-input="nearest">
                        <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker8" />
                        <div class="input-group-append" data-target="#datetimepicker8" data-toggle="datetimepicker">
                            <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class='col-12 col-md-3' style="overflow:hidden;width:95%">
			    <div class="form-group">
			    	<div class="col-12" style="padding: 0; width:400px">
			            <div id="datetimepicker13"></div>
			        </div>
			    </div>
			    <div class="appointmentDates">
<!-- 		            <div class="appointmentDateSelect"> -->
<%-- 		              	<% 
// 		              		DateFormat ft = new SimpleDateFormat ("yyyy/MM/dd");
// 		              		Calendar cal = Calendar.getInstance();
// 		              		Date now = cal.getTime();
//    							out.print( "<div class=\"appointmentDate\">" +ft.format(now)+"</div>");
//    							cal.add(Calendar.DATE,-1);
// 		              		String yesterday = ft.format(cal.getTime());
// 		              		cal.add(Calendar.DATE,2);
// 		              		String tomorrow = ft.format(cal.getTime());
 						%> --%>
<!-- 						<div class="appointmentDayPicker dayPicker"> -->
<%-- 							<button type="button" class="appointmentDayPickerButton" id="prev" value="<%= yesterday%>">&lt;</button> --%>
<%-- 							<button type="button" class="appointmentDayPickerButton" id="next" value="<%= tomorrow%>">&gt;</button> --%>
<!-- 						</div> -->
		    
<!-- 		            </div> -->
		            <div class="appointmentSlots slots">
						<div class="appointmentSlotsContainer">
		                <div class="appointmentSlot slot" data-value="0900">09:00</div>
		                <div class="appointmentSlot slot" data-value="0930">09:30</div>
		                <div class="appointmentSlot slot" data-value="1000">10:00</div>
		                <div class="appointmentSlot slot" data-value="1030">10:30</div>
		                <div class="appointmentSlot slot" data-value="1100">11:00</div>
		                <div class="appointmentSlot slot" data-value="1500">15:00</div>
		                <div class="appointmentSlot slot" data-value="1530">15:30</div>
		                <div class="appointmentSlot slot" data-value="1600">16:00</div>
		                <div class="appointmentSlot slot" data-value="1630">16:30</div>
					</div>
		            </div>
				</div>
			</div>
        </div>
	</div>
	<!-- 內文end -->
	
	<!-- 匯入js -->
	<script src="${path}/js/jquery-3.2.1.min.js"></script>
	<script src="${path}/js/popper.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/locale/zh-tw.min.js"></script>
	<script src="${path}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/js/tempusdominus-bootstrap-4.min.js"></script>
    <script type="text/javascript">
        $(function() {
        	// 保姆頁面用
	        $('#datetimepicker13').datetimepicker({
	        	format: 'L', // 不顯示time
	        	inline: true,
// 	            sideBySide: true
				daysOfWeekDisabled: [0, 6],
				disabledDates: [ '2020-10-08', '2020-10-26'], // 休假或是已經預約額滿
				stepping: 30, // 30分鐘或1小時
	        });
        	
	        
	        
			// 預約用
            $('#datetimepicker7').datetimepicker({
                // inline: true,
                sideBySide: true,
                daysOfWeekDisabled: [0, 6],
                enabledHours: [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]
            });
            $('#datetimepicker8').datetimepicker({
                useCurrent: false,
                // inline: true,
                sideBySide: true,
                daysOfWeekDisabled: [0, 6],
                enabledHours: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
            });
            $("#datetimepicker7").on("change.datetimepicker", function(e) {
                $('#datetimepicker8').datetimepicker('minDate', e.date);
            });
            $("#datetimepicker8").on("change.datetimepicker", function(e) {
                $('#datetimepicker7').datetimepicker('maxDate', e.date);
            });
        });
	</script>
</body>
</html>