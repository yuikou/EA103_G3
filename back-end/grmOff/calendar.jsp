<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.grm.model.*, com.goffday.model.*,com.salon.model.* , java.util.*"%>
<%
	String salno = (session.getAttribute("salno")).toString();
SalonService saSvc = new SalonService();
SalonVO salonOne = saSvc.getonesalon(salno);
String sName = salonOne.getSalName();

GrmService grmSvc = new GrmService();
List<GrmVO> allGrm = grmSvc.getGrm(salno);
List<String> grms = new ArrayList<String>();
for(GrmVO i: allGrm){
	grms.add(i.getGroomerName());
}
pageContext.setAttribute("allGrm", allGrm);
pageContext.setAttribute("sName", sName);
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/groomer.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/liz/calendar/evo-calendar.min.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/liz/calendar/evo-calendar.orange-coral.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/liz/addHoliday.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<script> var PATH = "<%=request.getContextPath()%>"; var salName = "${sName}";</script>
<title>salon calendar</title>

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
							<h4>列出美容店❣️ ${sName }❣️的行事曆</h4></td>
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
			<div class="del col-md-12">
				<button id="delOff" class="btn btn-outline-danger fa fa-trash-o fa-lg" disabled="true">刪除此筆資料</button>
			</div>		
			<div class="add col-md-12">
				<button id="addOff" class="btn btn-outline-info fa fa-coffee fa-lg">新增休假日</button>
				<div id="offbody">
					<div class="offbodyA">
						<p>已選擇日期&nbsp;:&nbsp;</p><p id="daytext"></p>
					</div>
					<div class="offbodyB">	
						<p style="text-decoration:none;">請選擇美容師&nbsp;:&nbsp;</p>
						<select name="grmno" id="oneGrmOff">
							<option>請選擇</option>
							<c:forEach var="grm" items="${allGrm }">
								<option value="${grm.groomerNo }">${grm.groomerName }</option>
							</c:forEach>
						</select>
						<button id="sendbtn" class="btn btn-outline-success fa fa-check fa-lg">送出</button>
					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div id="evoCalendar"></div>
			</div>
		</div>
		<div class="layout"></div>
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>
	<!-- Add the evo-calendar.js for.. obviously, functionality! -->
	<script	src="https://cdn.jsdelivr.net/npm/evo-calendar@1.1.2/evo-calendar/js/evo-calendar.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/liz/addHoliday.js"></script>
	
</body>
</html>