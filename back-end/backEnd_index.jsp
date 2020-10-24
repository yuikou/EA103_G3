<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.grm.model.*, com.goffday.model.*,com.salon.model.* , java.util.*"%>
<%
	String salno = (session.getAttribute("salno")).toString();
SalonService saSvc = new SalonService();
SalonVO salonOne = saSvc.getonesalon(salno);
String sName = salonOne.getSalName();

pageContext.setAttribute("sName", sName);
%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/salonIndex.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/calendar/evo-calendar.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/liz/calendar/evo-calendar.orange-coral.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<title>Petfect Match ==美容業者專區==</title>
<script> var PATH = "<%=request.getContextPath()%>";</script>
</head>

<body>
	<div class="layout"></div>
	<div class="container-fluid">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-12">
					<h4>
						Welcome back<i class="fa fa-heart"></i>&nbsp;${sName}
					</h4>
				</div>
				<div class="col-md-12">
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
				<div class="col-md-12">這邊放業者簡介</div>
				<div class="col-md-3 col-sm-12">
					<div class="row">
						<div class="accordion" id="accordionExample">
							<div class="myCard col-sm-12">
								<div class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link" type="button"
											data-toggle="collapse" data-target="#collapseOne"
											aria-expanded="true" aria-controls="collapseOne">
											<i class="fa fa-user-circle-o headText">業者資料管理</i>
										</button>
									</h5>
								</div>

								<div id="collapseOne" class="collapse"
									aria-labelledby="headingOne" data-parent="#accordionExample">
									<div class="card-body">
										<ul>
											<li class="fa fa-cogs sList"><a href="">&nbsp;業者資料編輯</a></li>
											<li></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="myCard col-sm-12">
								<div class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" type="button"
											data-toggle="collapse" data-target="#collapseTwo"
											aria-expanded="false" aria-controls="collapseTwo">
											<i class="fa fa-bar-chart headText">訂單管理</i>
										</button>
									</h5>
								</div>
								<div id="collapseTwo" class="collapse"
									aria-labelledby="headingTwo" data-parent="#accordionExample">
									<div class="card-body">
										<ul>
											<li class="fa fa-th-list sList"><a href="">&nbsp;訂單列表</a></li>
											<li></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="myCard col-sm-12">
								<div class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" type="button"
											data-toggle="collapse" data-target="#collapseThree"
											aria-expanded="false" aria-controls="collapseThree">
											<i class="fa fa-fort-awesome headText">美容師管理</i>
										</button>
									</h5>
								</div>
								<div id="collapseThree" class="collapse"
									aria-labelledby="headingThree" data-parent="#accordionExample">
									<div class="card-body">
										<ul>
											<li class="fa fa-users sList"><a
												href="<%=request.getContextPath()%>/back-end/grm/listAllGrm.jsp">&nbsp;本店美容師列表</a></li>
											<br>
											<li class="fa fa-user-plus sList"><a
												href='<%=request.getContextPath()%>/back-end/grm/addGrm.jsp'>&nbsp;新增美容師</a></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="myCard col-sm-12">
								<div class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" type="button"
											data-toggle="collapse" data-target="#collapseFour"
											aria-expanded="false" aria-controls="collapseFour">
											<i class="fa fa-shopping-cart headText">服務項目管理</i>
										</button>
									</h5>
								</div>
								<div id="collapseFour" class="collapse"
									aria-labelledby="headingThree" data-parent="#accordionExample">
									<div class="card-body">
										<ul>
											<li class="fa fa-book sList"><a
												href="<%=request.getContextPath()%>/back-end/salsev/listAllSalSev.jsp">&nbsp;本店服務項目清單</a></li>
											<br>
											<li class="fa fa-hand-o-right sList"><a
												href='<%=request.getContextPath()%>/back-end/salsev/addSalSev.jsp'>&nbsp;新增服務項目</a></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="myCard col-sm-12">
								<div class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" type="button"
											data-toggle="collapse" data-target="#collapseFive"
											aria-expanded="false" aria-controls="collapseFive">
											<i class="fa fa-camera-retro headText">作品集管理</i>
										</button>
									</h5>
								</div>
								<div id="collapseFive" class="collapse"
									aria-labelledby="headingThree" data-parent="#accordionExample">
									<div class="card-body">
										<ul>
											<li class="fa fa-files-o sList"><a
												href="<%=request.getContextPath()%>/back-end/salalb/listAllbyAlb.jsp">&nbsp;編輯作品集相簿</a></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="myCard col-sm-12">
								<div class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" type="button"
											data-toggle="collapse" data-target="#collapseSix"
											aria-expanded="false" aria-controls="collapseSix">
											<i class="fa fa-calendar headText">行事曆</i>
										</button>
									</h5>
								</div>
								<div id="collapseSix" class="collapse"
									aria-labelledby="headingThree" data-parent="#accordionExample">
									<div class="card-body">
										<ul>
											<li class="fa fa-calendar-plus-o sList"><a
												href="<%=request.getContextPath()%>/back-end/grmOff/calendar.jsp">&nbsp;本店行程及排班管理</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-9 col-sm-12">
					<div id="evoCalendar"></div>
				</div>
			</div>
		</div>
	</div>
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
	<!-- Add the evo-calendar.js for.. obviously, functionality! -->
	<script	src="https://cdn.jsdelivr.net/npm/evo-calendar@1.1.2/evo-calendar/js/evo-calendar.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/liz/salonIndex.js"></script>
</body>
</html>