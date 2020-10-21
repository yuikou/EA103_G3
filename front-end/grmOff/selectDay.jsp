<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.salsev.model.*, com.grm.model.*, com.goffday.model.*, java.util.*"%>
<%
	String salno = (session.getAttribute("salno")).toString();
SalsevVO mySalSev = (SalsevVO) session.getAttribute("mySalSev");
GrmVO myGrmVO = (GrmVO) session.getAttribute("myGrm");
String grmno = myGrmVO.getGroomerNo();
GodService gfSvc = new GodService();
List<GODayVO> list = gfSvc.getOneGod(grmno);
pageContext.setAttribute("myGrmVO", myGrmVO);
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" crossorigin="anonymous">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/goff.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<script>
	var PATH = "<%=request.getContextPath()%>";
</script>
<title>預約服務日期</title>
</head>
<body>
	<!-- 此處include header -->
	<div class="layout"></div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12" id="cancelBtn">
				<button id="back" class="btn btn-outline-secondary">回上一頁</button>
				<br>
				<h4>請選擇日期</h4>
			</div>
			<div class="col-md-3 col-sm-12">
				<div class="mySev">
					<div class="mySevBody">
						<p class="sevName">${myGrmVO.groomerName }</p>
						<p class="sevName">${mySalSev.salsevname }</p>
						<p class="sevInfo">${mySalSev.salSevInfo }</p>
						<p class="sevTime">${mySalSev.salsevtime }hr</p>
					</div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="row">
					<div class="col-sm-12">
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
					<div class="col-sm-12">
						<div id="result">
							<span id="title"><i class="fa fa-calendar-check-o"></i>Available times</span>
							<span id="myday"></span>
							<div id="offTimeList"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="layout"></div>
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
	<script src="<%=request.getContextPath()%>/js/liz/util.js"></script>
	<script src="<%=request.getContextPath()%>/js/liz/goff.js"></script>

</body>
</html>