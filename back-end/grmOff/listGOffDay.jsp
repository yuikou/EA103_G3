<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.grm.model.*,com.goffday.model.*, java.util.*"%>
<%
	String salno = (session.getAttribute("salno")).toString();
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/goff.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>Insert title here</title>
</head>
<body>
	<!-- 此處include header -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<td><span> <a
								href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a>
						</span>
							<h4>列出美容店${salno }所屬的美容師不可服務時間</h4></td>
					</tr>
				</table>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<div class="col-md-12">
				<div class="wrapper">
					<div class="calendar">
						<div class="title">
							<div class="a" style="float: left">
								<a href="" id="prev"> <</a>
							</div>
							<div class="header">
								<h1 class="green" id="calendar-month">Month</h1>
								<h2 class="green small" id="calendar-year">Year</h2>
							</div>
							<div class="a" style="float: right">
								<a href="" id="next"> ></a>
							</div>
						</div>
						<div class="body">
							<div class="ctitle body-list">
								<ul>
									<li>Sun</li>
									<li>Mon</li>
									<li>Tue</li>
									<li>Wed</li>
									<li>Thu</li>
									<li>Fri</li>
									<li>Sat</li>
								</ul>
							</div>
							<div class="body-list">
								<ul id="days">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12">
			<div class="row">
				
						<div class="title col-md-12">
							<h1 class="green salonName" style="padding-top: 10px">Salon</h1>
							<h2 class="green small des"></h2>
						</div>
						<div class="off-list col-md-12">
							<ul class="ctitle ">
								<li>No.</li>
								<li>Name</li>
								<li>Date</li>
								<li><i class="fa fa-trash-o"></i></li>
							</ul>
						</div>
					</div>
			</div>
		</div>
	</div>

	<!-- 此處include footer  -->
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
		integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
		crossorigin="anonymous"></script>
	<script>
    var nowDate = new Date();
    var curYear = nowDate.getFullYear();
    var curMonth = nowDate.getMonth();
    var curDay = nowDate.getDay(); //星期幾
    var curDate = nowDate.getDate();

    var days = document.getElementById("days");
    var year = document.getElementById("calendar-year");
    var month = document.getElementById("calendar-month");

    var monthName = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var leapYear = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var normalYear = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var nowMonth = null;

    setCalendar(curYear, curMonth);

    function setCalendar(curYear, curMonth) {
        var startday;
        var totaldays;
        var tempDate = new Date(curYear, curMonth, 1);
        startday = tempDate.getDay(); // setStartday
        if (curYear % 4 === 0) {
            totaldays = leapYear[curMonth]; //settotaldays
        } else {
            totaldays = normalYear[curMonth];
        }
//        console.log(totaldays);
        //-----------------------------------------------

        var str = '';
        var d = new Date();
        for (var j = 0; j < startday; j++) {
            str += "<li>" + "</li>";
        }
        for (var i = 1; i <= totaldays; i++) {
            if (i < curDate && curYear === d.getFullYear() && curMonth === d.getMonth()) {
                str += "<li class=\"lightgrey\">" + i + "</li>";
            } else if (i === curDate && curYear === d.getFullYear() && curMonth === d.getMonth()) {
                str += "<li id=\"isToday\" class=\"green greenbox\">" + i + "</li>";
            } else {
                str += "<li class=\"darkgrey afterDays\">" + i + "</li>";
            }
        }

        days.innerHTML = str;
        year.innerHTML = curYear;
        month.innerHTML = monthName[curMonth];

        nowMonth = curMonth + 1;
    }


    //----------------------------------------------------------------
    var prev = document.getElementById("prev");
    var next = document.getElementById("next");

    next.addEventListener("click", function(e) {
        e.preventDefault(); //很重要!
        if (curMonth < 11) { //curMonth 0-11
            curMonth += 1;
        } else {
            curYear += 1;
            curMonth = 0;
        }
        setCalendar(curYear, curMonth);
    });

    prev.addEventListener("click", function(e) {
        e.preventDefault();
        if (curMonth > 0) {
            curMonth -= 1;
        } else {
            curYear -= 1;
            curMonth = 11;
        }
        setCalendar(curYear, curMonth);
    });

    year.addEventListener("click", function a() {
        var temp = prompt("請輸入年份", 2020);
        if (temp > 0) {
            curYear = temp;

        } else if (temp !== null) {
            a();
        }
        setCalendar(curYear, curMonth);
    });
    month.addEventListener("click", function a() {
        var temp = prompt("請輸入月份", 1);
        if (temp <= 12 && temp > 0) {
            curMonth = temp - 1;

        } else if (temp !== null) {
            a();
        }
        setCalendar(curYear, curMonth);
    });
    //----------------------------------------------
	
		var myYear = $('#calendar-year').text()
		
		$('.afterDays').click(function() {
			var num = 0;
			var grmOffDay = null;
			var myDay = $(this).text();
	        
	        var month = $('#calendar-month').text();
	        for (var i = 0; i < monthName.length; i++) {
	        	if (month == monthName[i]) {
	            num = i + 1;
	            }
	        }
	        
	        $('#des').text(num);
	        str = myYear + '-' + num + '-' + myDay;
	        
			$.ajax({
				type: "GET",
				url: "<%=request.getContextPath()%>/goday/goday.do?action=listGoff",
				success : function(data){
					grmOffDay = data;
					console.log(typeof(grmOffDay));
// 					for(var prop in grmOffDay){
// 						console.log(grmOffDay[prop]);
// 					}
				}
			});
			
	    });
			
	</script>
</body>
</html>