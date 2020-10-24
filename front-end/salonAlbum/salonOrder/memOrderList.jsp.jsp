<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.salonOrder.model.*,com.salon.model.*,java.util.*,com.member.model.*,com.pet.model.*"%>

<%
	String memNo = "M001";//測試用
	SalonOrderService salOrderSvc = new SalonOrderService();
	// 	String memNo = ((MemVO) session.getAttribute("memVO")).getMemNo(); //連接會員網頁要打開  
	List<SalonOrderVO> list = salOrderSvc.getALLBYMemNo(memNo); //必須匯入java.util.*才可以使用
	pageContext.setAttribute("list", list);

	PetService petSvc = new PetService();
	List<PetVO> petList = petSvc.getAllPet(memNo);
	pageContext.setAttribute("petList", petList);
%>
<jsp:useBean id="memSvc" scope="page"
	class="com.member.model.MemService" />


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Tables - SB Admin</title>
<link href="<%=request.getContextPath()%>/css/Dave/stylesTable.css"
	rel="stylesheet" />
<link
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" crossorigin="anonymous" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"
	crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">


	<div id="layoutSidenav">
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">訂單管理</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="index.html">首頁</a></li>
					</ol>
					<div class="card mb-4">
						<div class="card-header">
							<i class="fas fa-table mr-1"></i> DataTable Example
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>訂單編號</th>
											<th>會員編號</th>
											<th>會員名稱</th>
											<th>寵物編號</th>
											<th>寵物名稱</th>
											<th>訂單成立時間</th>
											<th>價錢</th>
											<th>明細
											<th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="salonOrderVO" items="${list}">
											<tr>
												<td>${salonOrderVO.salOrderNo}</td>
												<td>${salonOrderVO.memNo}</td>
												<td><c:forEach var="memVO" items="${memSvc.all}">
														<c:if test="${salonOrderVO.memNo == memVO.memNo}">
															${memVO.memName}
														</c:if>
													</c:forEach></td>
												<td><select>
														<c:forEach var="petVO" items="${petList}">
															<option value="${petVO.petName}">${petVO.petName}</option>
														</c:forEach>
												</select></td>
												<td>${petVO.petName}</td>
												<td>${salonOrderVO.salOrderDate}</td>
												<td>${salonOrderVO.salTp}</td>
												<td>
													<form method="post"
														action="<%=request.getContextPath()%>/front-end/salon/salonOrderDetail.do"
														name="form1">
														<input type="hidden" name="action"
															value="getMemOrderDetail"> <input type="hidden"
															name="salOrderNo" value="${salonOrderVO.salOrderNo}">
														<button class="btn btn-primary" id="send">明細</button>
													</form>
												</td>
												<td></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/scriptsTable.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/js/Dave/datatables-demo.js"></script>


</body>

</html>