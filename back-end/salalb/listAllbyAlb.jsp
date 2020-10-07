<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.salonAlbum.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	SalonAlbService salb = new SalonAlbService();
String salno = (session.getAttribute("salno")).toString();
List<SalonAlbVO> list = salb.getOneSalAlb(salno);
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-12">
				<table>
					<tr>
						<td><span class="myset"> <a	href="<%=request.getContextPath()%>/back-end/backEnd_index.jsp">回首頁</a>
						</span>
							<h4>美容店作品集管理</h4></td>
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
			<div class="addgrm">
				<a
					href="<%=request.getContextPath()%>/back-end/salalb/addAlbPic.jsp">新增相片</a>
			</div>
			<div class="col-12 col-md-12">
				<div class="row">
					<c:forEach var="picVO" items="${list}" begin="0" end="20">
						<div class="card" style="width: 18rem;">
							<img
								src="<%=request.getContextPath()%>/salalb/picReader?salAlbNo=${picVO.salAlbNo}"
								alt="picture for salon" name="salAlbNo"
								class="card-img-top showPic">
							<div class="card-body">
								<p class="card-text">${picVO.salPortInfo}</p>
								<form method="post"
									action="<%=request.getContextPath()%>/salalb/salalb.do">
									<input type="submit" value="刪除" class="btn btn-outline-danger">
									<input type="hidden" name="salAlbNo" value="${picVO.salAlbNo}">
									<input type="hidden" name="action" value="delete">
								</form>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>