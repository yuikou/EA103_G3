<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.salonAlbum.model.*"%>
<%
	SalonAlbService sbSvc = new SalonAlbService();
String salno = (session.getAttribute("salno")).toString();
List<SalonAlbVO> list = sbSvc.getOneSalAlb(salno);
pageContext.setAttribute("list", list);
%>
<%
	String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
pageContext.setAttribute("url", url);
%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/liz/blueimp-gallery.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/liz/blueimp-gallery-indicator.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/liz/gallery.css">
<title>Document</title>
</head>

<body>
	<div class="container">
		<div id="links" class="links pic-wrapper">
			<c:forEach var="pic" items="${list }" begin="0" end="20">
				<div class="item">
					<a
						href="${url }/PicReader.do?action=salAlbNo&salAlbNo=${pic.salAlbNo}"
						data-gallery=""> <img
						src="${url }/PicReader.do?action=salAlbNo&salAlbNo=${pic.salAlbNo}">
					</a>
				</div>
			</c:forEach>
		</div>
	</div>

	<div id="blueimp-gallery"
		class="blueimp-gallery blueimp-gallery-controls">
		<div class="slides"></div>
		<h3 class="title"></h3>
		<a class="prev"></a> <a class="next"></a> <a class="close"></a>
		<ol class="indicator"></ol>
	</div>
	<!-- boostrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
		integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
		integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
		crossorigin="anonymous"></script>
	<!-- blueimp gallery-->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/blueimp-gallery/2.33.0/js/blueimp-helper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/liz/js/blueimp-gallery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/liz/js/blueimp-gallery-fullscreen.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/liz/js/jquery.blueimp-gallery.min.js"></script>

</body>
</html>