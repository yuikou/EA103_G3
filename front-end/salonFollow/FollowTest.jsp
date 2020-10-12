<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.member.model.*, com.salonF.model.*, com.salon.model.*, java.util.*"%>

<%
	session.setAttribute("memNo", "M003");
session.setAttribute("salno", "B002");
SalFService safSvc = new SalFService();
String memNo = (session.getAttribute("memNo")).toString();
String salno = (session.getAttribute("salno")).toString();

pageContext.setAttribute("memNo", memNo);
pageContext.setAttribute("salno", salno);
//1. 取得會員追蹤的所有美容業者list
//2. 使用contains比對目前頁面的業者是否已存在

String fClass = "fa fa-heart-o"; //預設空心
List<SalonFVO> list = safSvc.getAll(memNo);
for (SalonFVO i : list) {
	if (i.getSalNo().contains(salno)) {
		fClass = "fa fa-heart"; //如果有比對到的話則顯示實心
	}
}

pageContext.setAttribute("fClass", fClass);
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
	href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/SF.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

<title>我是美容業者---測試加入我的最愛</title>

</head>
<body>
	<p>我是會員 ${memNo }</p>
	<p>這裡是美容店 ${salno }</p>

	<p>判斷此會員是否已經被追蹤, 如果是, 則呈現實心愛心, 且點擊愛心後變為空心愛心(取消追蹤)</p>
	<div class="myClick">
		<span class="${fClass }" id="followIcon" onclick="getAjax()"></span> <input
			type="hidden" name="memNo" value="${memNo }"> <input
			type="hidden" name="salno" value="${salno }">
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/salonF.js"></script>
</body>
</html>