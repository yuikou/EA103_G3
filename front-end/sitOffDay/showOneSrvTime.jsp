<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitOffDay.model.*, com.sitSrv.model.*, com.petSitter.model.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!-- Bootstrap datetimepicker & fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/css/tempusdominus-bootstrap-4.min.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/sitOffDay.css">


</head>

<BODY>

<!-- 內文body -->
    <div class="container sod-container">
    
		<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"/>
		<jsp:useBean id="petSitterSvc" class="com.petSitter.model.PetSitterService"/>
		<% 
		String sitNo = (String) request.getAttribute("sitNo");	
		PetSitterVO petSitterVO = petSitterSvc.getByPK(sitNo);
		pageContext.setAttribute("petSitterVO", petSitterVO);
		%>
		
		<!-------------------------------- 錯誤列表 -------------------------------->
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
		<!------------------------------可預約時段 -------------------------------->
		<div>
		    <c:forEach var="sitSrvVO" items="${sitSrvSvc.get_OneSit_AllSrv(sitNo)}">
			    <c:if test="${sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup' && sitSrvVO.sitSrvCode != 'Boarding' && sitSrvVO.sitSrvCode != 'DayCare'}">
			    <div class="appointmentDates appointmentHide" id="appointment_${sitSrvVO.sitSrvCode}">選擇時間
		            <div class="appointmentSlots slots">
						<div class="appointmentSlotsContainer">
						<% 
							String startT = petSitterVO.getSrvSTime();
							String endT = petSitterVO.getSrvETime();
							List<String> ssTlist = new ArrayList<String>();
							SitSrvVO sitSrvVO = (SitSrvVO) pageContext.getAttribute("sitSrvVO");
							
							String srvTstr = sitSrvSvc.get_OneSit_OneSrv(sitSrvVO.getSitSrvNo()).getSrvTime();
							int srvT = 100;// 通常服務時間一小時
							if (srvTstr!= null) {
								srvT = Integer.valueOf(srvTstr);// 如果服務項目有設定服務時間
							}
							if (startT != null){
								Integer startTint = Integer.valueOf(startT);
								Integer endTint = Integer.valueOf(endT);
								while(startTint<endTint){
									String temp = "0"+ startTint.toString();
									temp = temp.substring(temp.length()-4);
									if (temp.substring(2, 4).equals("50")){
										temp = temp.substring(0, 2) + ":30";
									} else {
										temp = temp.substring(0, 2) + ":00";
									}
									ssTlist.add(temp);
									startTint+=srvT;
								}
							}
							pageContext.setAttribute("ssTlist", ssTlist);
						%>
							<c:forEach var="ssT" items="${ssTlist}">
			                	<div class="appointmentSlot slot srvTimeSlot" data-value="${ssT}" >${ssT}</div>
			                </c:forEach>
						</div>
		            </div>
				</div>
				</c:if>
			</c:forEach>
        </div>
        
	</div>
	<!-- 內文end -->
	
	<!-- 匯入js -->
    <c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
</body>
</html>