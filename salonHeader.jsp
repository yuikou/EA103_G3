<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- �פJ�~��CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Dave/index.css">


<title>Nav</title>

</head>
<body>
	<!-- �H�U������navigation bar -->
	<nav
		class="navbar fixed-top navbar-expand-lg navbar-light bg-light myNav">
		<div class="container-fluid">
			<!-- logo (click to home page)-->
			<a class="navbar-brand" href="#"
				style="padding-top: 0px; padding-bottom: 0px"><img
				class="myLogo"
				src="https://dzmg8959fhe1k.cloudfront.net/all/logo.jpg"><img
				class="myLogoWord"
				src="https://dzmg8959fhe1k.cloudfront.net/all/logoWord.jpg"></a>
			<!-- logo -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarText" aria-controls="navbarText"
				aria-expanded="false" aria-label="Toggle navigation"
				style="margin-right: 15px">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="myNavDiv collapse navbar-collapse" id="navbarText">
				<!-- �W�s���s��U�A�ȷj�M���� -->
				<ul class="navbar-nav ml-auto">
					<!-- 					����n�R����b�|����footer -->
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salon/listAllSalon.jsp"><span
								class="ch-word">���e�~�̱M��</span><span class="en-word">Salon
									store</span></a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salonOrder/salonOrderList.jsp">
								<span class="ch-word">���e�q��޲z</span> <span class="en-word">OrderManager</span>
						</a></li>
					</div>
					<div class="myUndeLine">
						<li class="nav-item"><a class="nav-link accordion"
							href="<%=request.getContextPath()%>/front-end/salon/updateSalon.jsp">
								<span class="ch-word">�|����ƭק�</span> <span class="en-word">modifyInfo</span>
								<input type="hidden" name="salNo" id="salNo"
								value="${salonVO.salNo}">
						</a></li>
					</div>

					<div class="myUndeLine">
						<c:if test="${salonVO == null}">
							<li class="nav-item"><a class="nav-link accordion"
								href="salonregi.jsp"> <span class="ch-word">���U�s�b��</span> <span
									class="en-word">newmember</span>
							</a></li>
						</c:if>
						<c:if test="${salonVO != null}">
						</c:if>
					</div>


				</ul>
				<!-- �H�W�W�s���s��U�A�ȷj�M���� -->
				<!-- �n�J���s -->
				<c:if test="${salonVO == null}">
					<a href="<%=request.getContextPath()%>/front-end/salon/Glogin.jsp">
						<input type="button" class="myBtn" id="login" value="�n�J">
					</a>
					<!-- �H�W�n�Jbtn -->
				</c:if>
				<c:if test="${salonVO != null}">
					<form method="post"
						action="<%=request.getContextPath()%>/front-end/salon/salon.do">
						<input type="submit" class="myBtn" id="login" value="�n�X">
						<input type="hidden" name="action" value="logout"> <span>${salonVO.salName}
							�A�n</span>
					</form>
				</c:if>
				<!-- �H�W�n�Jbtn -->
			</div>
		</div>
	</nav>
	<!-- ����navigation bar end-->
</body>
</html>