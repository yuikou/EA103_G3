<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.sitSrv.model.*,com.sitFollow.model.*, com.petSitter.model.*, java.text.DecimalFormat" %>

<jsp:useBean id="sitSrvVOlist" scope="request" type="java.util.List<SitSrvVO2>" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�s�����i�A��</title>

<!-- �פJ�~��CSS -->
<c:set var="path" value="${pageContext.request.contextPath}/front-end" />
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/jquery.datetimepicker.css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/rateit.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rangeslider.js/2.3.0/rangeslider.min.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvBrowse.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>

	<!-------------------- nav -------------------->
	<jsp:include page="/front-end/header.jsp" />

	<!------------------ ����body ------------------>
	<div class="container container-width">

		<!-- ���~�C�� -->
		<div class="errorList">
			<c:if test="${not empty errorMsgs}">
				<font style="color: red;">�o�ͥH�U���~�G</font>
				<ul>
					<c:forEach var="msg" items="${errorMsgs}">
						<li style="color: red;">${msg}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>

		<form id="form1" action="${pageContext.request.contextPath}/sitSrv/sitSrv.do" method="post">
<!-- pages1 -->		
		<div class="row" style="display:none;">
			<%@ include file="pages/page1_ByCompositeQuery.file" %>
		</div>
		
		
		<div class="main-wrapper col-md-12 ">

<!-- search-sideBar -->
			<div class="col-xl-3 col-lg-5 col-md-5 col-sm-12 search-sideBar">
				<div class="search-sideBar-wrapper">
					<!-- sitSrvCode -->
					<div class="search-sideBar-filter sitSrvCode-div">
						<div class="row sitSrvCode-row">
							<div class="heading">�M�䪺�A��:</div>
							<input type="hidden" name="sitSrvCode" value="${param.sitSrvCode==''? 'Boarding': param.sitSrvCode}">
							<button class="service-seleced-btn">
								<div class="col-2 service-icon-div"><i class="service-icon <c:out value="${param.sitSrvCode==''? 'Boarding': param.sitSrvCode}"/>-icon"></i></div>
								<div class="col-8 service-seleced-name"></div>
								<div class="col-2 select-icon"><i class="material-icons">keyboard_arrow_down</i></div>
							</button>
							<ul class="sitSrvCode-select" style="display: none;">
								<li class="selectMe <c:out value=" ${(param.sitSrvCode=='Boarding'|| param.sitSrvCode=='')?'is-selected':''}"/>" data-value="Boarding">
									<div class="hidden-xs">
										<i class="service-icon Boarding-icon"></i>
									</div>
									<div>
										<div class="service-name">�H�i<small>�d����z�a���L�]</small></div>
									</div>
								</li>
								<li class="selectMe <c:out value=" ${param.sitSrvCode=='DayCare' ? 'is-selected':''}"/>" data-value="DayCare">
									<div class="hidden-xs">
										<i class="service-icon DayCare-icon"></i>
									</div>
									<div>
										<div class="service-name">�馫<small>�d����z�a���ȹF</small></div>
									</div></li>
								<li class="selectMe <c:out value=" ${param.sitSrvCode=='DropIn' ? 'is-selected':''}"/>" data-value="DropIn">
									<div class="hidden-xs">
										<i class="service-icon DropIn-icon"></i>
									</div>
									<div>
										<div class="service-name">�쩲<small>�h�d���a�����P�M��</small></div>
									</div></li>
								<li class="selectMe <c:out value=" ${param.sitSrvCode=='DogWalking' ? 'is-selected':''}"/>" data-value="DogWalking">
									<div class="hidden-xs">
										<i class="service-icon DogWalking-icon"></i>
									</div>
									<div>
										<div class="service-name">����<small>�a�d���~�X�z��</small></div>
									</div></li>
								<li class="selectMe <c:out value=" ${param.sitSrvCode=='PetTaxi' ? 'is-selected':''}"/>" data-value="PetTaxi">
									<div class="hidden-xs">
										<i class="service-icon PetTaxi-icon"></i>
									</div>
									<div>
										<div class="service-name">�d��Taxi<small>���e�d���A��</small></div>
									</div></li>
							</ul>
						</div>
					</div>

					<!-- nearAddr -->
					<div class="search-sideBar-filter nearAddr-div">
						<div class="row nearAddr-row">
							<div class="heading">�ڪ���m:</div>
							<span id="panel"><input type="text" id="keyword" class="search-loaction-input" name="nearAddr" value="${param.nearAddr}"></span>
						</div>
					</div>

					<!-- dateFrom & dateTo -->
					<div class="search-sideBar-filter date-div">
						<div class="row date-row">
							<div class="heading">��ܤ��:</div>
							<div class="col-sm-6 search-time-from"> 
								<input class="search-input search-time-input" type="text" id="start_dateTime" name="dateFrom" value="${dateFrom}" placeholder="�}�l���" autocomplete="off">
							</div>
							<div class="betweenDiv"><i class="betweenIcon"></i></div>
							<div class="col-sm-6 search-time-end">
								<input class="search-input search-time-input" type="text" id="end_dateTime" name="dateTo" value="${dateTo}" placeholder="�������" autocomplete="off">
							</div>
						</div>
					</div>

					<!-- acpPetTyp-1 -->
					<div class="search-sideBar-filter acpPetTyp1-div">
						<div class="row acpPetTyp-row">
							<div class="heading">�ڪ��d������:</div>
							<div>
						        <ul class="list">
						            <li class="list-item">
						                <label class="label-checkbox">
						                	<input type="checkbox" name="acpPetTypPart0" class="petTypCheckbox" value="cat" <c:out value="${param.acpPetTypPart0!=null ?'checked':''}"/> >��
						                </label>
						                <label class="label-checkbox">
						                	<input type="checkbox" name="acpPetTypPart1" class="petTypCheckbox" value="dog" <c:out value="${param.acpPetTypPart1!=null ?'checked':''}"/>>��
						                </label>
						            </li>
						        </ul>
						    </div>
						</div>
					</div>

					<!-- acpPetTyp-2 -->
					<div class="search-sideBar-filter acpPetTyp2-div">
						<div class="row acpPetTyp2-row">
							<div class="heading">�ڪ������髬:</div>
							<div>
						        <ul class="list">
						            <li class="list-item">
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="small" <c:out value="${param.acpPetTypPart2=='small' ?'checked':''}"/>>�p����(1~5kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="medium" <c:out value="${param.acpPetTypPart2=='medium' ?'checked':''}"/>>������(5~10kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="large" <c:out value="${param.acpPetTypPart2=='large' ?'checked':''}"/>>�j����(10~20kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="xlarge" <c:out value="${param.acpPetTypPart2=='xlarge' ?'checked':''}"/>>�S�j����(20kg�H�W)
						                </label>
						            </li>
						        </ul>
						    </div>
						</div>
					</div>

					<!-- acpPetNum -->
					<div class="search-sideBar-filter acpPetNum-div">
						<div class="row acpPetNum-row">
							<div class="heading">�ڪ��d���ƶq:</div>
							<div class="button-item">
						    	<label>
						        	<input type="radio" name="acpPetNum" value="1" <c:out value="${param.acpPetNum=='1' ?'checked':''}"/>>1
						       	</label>
						       	<label>
						        	<input type="radio" name="acpPetNum" value="2" <c:out value="${param.acpPetNum=='2' ?'checked':''}"/>>2
						      	</label>
						     	<label>
						          	<input type="radio" name="acpPetNum" value="3" <c:out value="${param.acpPetNum=='3' ?'checked':''}"/>>3+
						     	</label>
							</div>
						</div>
					</div>

					<!-- acpSrvFee -->
					<div class="search-sideBar-filter acpSrvFee-div">
						<div class="row acpSrvFee-row">
							<div class="heading"><span>�i�����C&nbsp;</span><span class="unit">��</span><span>&nbsp;���̤j����</span></div>
							<div class="range-slider">
						    	<div class="wrapper_Ranger">
						        	<input id="rs-range-line" class="rs-range" type="range" name="srvFee" value="${param.srvFee==null ?1000:param.srvFee}" min="0" max="1000">
						        </div>
						   	</div>
						</div>
					</div>

					<div class="row" style="clear:both;">
						<div class="col-sm-10 search-condition">
							<input type="hidden" name="action" value="browse">
							<button class="search-condition-btn">�j�M�O�i</button>
						</div>
					</div>

					<!-- otherFilters -->
					<div class="otherFilters-div">
						<div>
							<div class="otherFilters-more">��h�z�����:<i class="material-icons">keyboard_arrow_down</i></div>
							<button class="otherFilters-reset">�M�ũҦ�����</button>
						</div>
						<div class="otherFilters-more-choices">
							<div class="heading otherFilters-heading">�[���~��</div>
							<div class="otherFilters-choices"><label><input type="radio" name='addBathing' value='1' <c:out value="${param.addBathing=='1' ?'checked':''}"/>><span>������</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='addBathing' value='0' <c:out value="${param.addBathing=='0' ?'checked':''}"/>><span>�S������</span></label></div>
							<div class="heading otherFilters-heading">�[�����e</div>
							<div class="otherFilters-choices"><label><input type="radio" name='addPickup' value='1' <c:out value="${param.addPickup=='1' ?'checked':''}"/>><span>������</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='addPickup' value='0' <c:out value="${param.addPickup=='0' ?'checked':''}"/>><span>�S������</span></label></div>
							<div class="heading otherFilters-heading">���U�d�����{��</div>
							<div class="otherFilters-choices"><label><input type="radio" name='careLevel' value='0' <c:out value="${param.careLevel=='0' ?'checked':''}"/>><span>�û����|�L�H�ݺ�</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='careLevel' value='1' <c:out value="${param.careLevel=='1' ?'checked':''}"/>><span>�L�ݺޤ��W�L1�p��</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='careLevel' value='2' <c:out value="${param.careLevel=='2' ?'checked':''}"/>><span>�L�ݺ�1-2�p��</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='careLevel' value='3' <c:out value="${param.careLevel=='3' ?'checked':''}"/>><span>�L�ݺ޶W�L2�p��</span></label></div>
							<div class="heading otherFilters-heading">�d���ݦb���a��</div>
							<div class="otherFilters-choices"><label><input type="radio" name='stayLoc' value='0' <c:out value="${param.stayLoc=='0' ?'checked':''}"/>><span>�ۥѺ��C</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='stayLoc' value='1' <c:out value="${param.stayLoc=='1' ?'checked':''}"/>><span>�p�H�|�l</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='stayLoc' value='2' <c:out value="${param.stayLoc=='2' ?'checked':''}"/>><span>���۩�Ţ�l</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='stayLoc' value='3' <c:out value="${param.stayLoc=='3' ?'checked':''}"/>><span>��W�ж�</span></label></div>
							<div class="heading otherFilters-heading">�d����ı���a��</div>
							<div class="otherFilters-choices"><label><input type="radio" name='overnightLoc' value='0' <c:out value="${param.overnightLoc=='0' ?'checked':''}"/>><span>�H�N</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='overnightLoc' value='1' <c:out value="${param.overnightLoc=='1' ?'checked':''}"/>><span>�b�O�i�ɤW</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='overnightLoc' value='2' <c:out value="${param.overnightLoc=='2' ?'checked':''}"/>><span>�b���ɤW</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='overnightLoc' value='3' <c:out value="${param.overnightLoc=='3' ?'checked':''}"/>><span>���۩�Ţ�l</span></label></div>
							<div class="heading otherFilters-heading">�L������</div>
							<div class="otherFilters-choices"><label><input type="radio" name='smkFree' value='1' <c:out value="${param.smkFree=='0' ?'checked':''}"/>><span>������</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='smkFree' value='0' <c:out value="${param.smkFree=='1' ?'checked':''}"/>><span>�S������</span></label></div>
							<div class="heading otherFilters-heading">�O�i�a�̦��p��</div>
							<div class="otherFilters-choices"><label><input type="radio" name='hasChild' value='1' <c:out value="${param.hasChild=='0' ?'checked':''}"/>><span>�a�̦��p��</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='hasChild' value='0' <c:out value="${param.hasChild=='1' ?'checked':''}"/>><span>�a�̨S���p��</span></label></div>
							<div class="heading otherFilters-heading">�O�i���Ѫ��d���B��]��</div>
							<div class="otherFilters-choices"><label><input type="radio" name='eqpt' value='0' <c:out value="${param.eqpt=='0' ?'checked':''}"/>><span>���y�a</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='eqpt' value='1' <c:out value="${param.eqpt=='1' ?'checked':''}"/>><span>�d��Ţ�l</span></label></div>
							<div class="otherFilters-choices"><label><input type="radio" name='eqpt' value='2' <c:out value="${param.eqpt=='2' ?'checked':''}"/>><span>�U�B�c</span></label></div>
						</div>
					</div>
				</div>
			</div>

<!-- search-results -->
			<div class="col-xl-4 col-lg-7 col-md-7 col-sm-12 search-results">
				<div class="search-results-wrapper">
				
				<!-- ���Ы�Svc -->
				<jsp:useBean id="memSvc" class="com.member.model.MemService"/>
        		<jsp:useBean id="petSitterSvc" class="com.petSitter.model.PetSitterService"/>
        		<jsp:useBean id="sfSrv" class="com.sitFollow.model.SitFollowService"/>
					
					<c:forEach var="sitSrvVO" items="${sitSrvVOlist}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<div class="panel" id="${sitSrvVO.sitSrvNo}">
        
			            <a href="${pageContext.request.contextPath}/petSitter/petSitter.do?action=getOneSitter_DisplayForMem&sitNo=${sitSrvVO.sitNo}&sitSrvNo=${sitSrvVO.sitSrvNo}">
			            <div class="row align-middle" >
			                <div class="col-md-3 col-sm-3 column hl-selfie" 
			                	style="background-image: url('${pageContext.request.contextPath}/PicReader.do?action=getMemPic&memNo=${sitSrvVO.memNo}');"></div>
			                <div class="col-md-7 col-sm-7 column align-top">
			                    <div class="hl-name">${sitSrvVO.memName}</div>
<%-- 			                    	<img src="${path}/img/verify_mobile.svg" class="hl-phoneveri" alt="phone"> --%>
			                    <div class="hl-title">${sitSrvVO.sitSrvName}</div>
			                    <div class="hl-stars">
			                        <div class="rateit" data-rateit-value=<c:if test="${sitSrvVO.totalComm != 0}">${sitSrvVO.totalComm / sitSrvVO.totalCus}</c:if> data-rateit-ispreset="true" data-rateit-readonly="true"></div>
			                    </div>
			                    <div class="hl-svc">
			                    	${sitSrvVO.memAddress.substring(0,6)}</div>
			                    <div class="hl-about">
			                    	<div class="hl-about-div repeat-clients">${sitSrvVO.repeatCus} �ӭ��ƹw�q</div>
			                    	<div class="hl-about-div reviews"><fmt:formatNumber value="${sitSrvVO.totalComm}"  pattern="0"/> �ӵ���</div>
			                    </div>
			                    <div style="display: none" class="memAddress">${sitSrvVO.memAddress}</div>
			                    <div style="display: none" class="memPicPath">${pageContext.request.contextPath}/PicReader.do?action=getMemPic&memNo=${sitSrvVO.memNo}</div>
			                </div>
			                <div class="col-md-2 col-sm-2 column" style="position:relative;height:inherit;padding-right: 0;">
			                    <div class="hl-best-svc text-center">
			                        <div class="hl-best-price">$${sitSrvVO.srvFee}</div>
			                        <div class="hl-unit"> /&nbsp;
			                        <c:out value="${sitSrvVO.sitSrvCode=='Boarding'? '��': ''} "/>
			                        <c:out value="${sitSrvVO.sitSrvCode=='DayCare'? '��': ''} "/>
			                        <c:out value="${sitSrvVO.sitSrvCode=='DropIn'? '��': ''} "/>
			                       	<c:out value="${sitSrvVO.sitSrvCode=='DogWalking'? '��': ''} "/>
			                        <c:out value="${sitSrvVO.sitSrvCode=='PetTaxi'? '����': ''} "/>
			                        </div>
			                    </div>
			                    <div class="align-center">
			                    	<c:if test="${sitSrvVO.acpPetTyp!=0}">
				                        <img src="${path}/img/mdog.svg" alt="pet-type" class="hl-svctarget">
			                    	</c:if>
			                    	<c:if test="${sitSrvVO.acpPetTyp<=4}">
			                        	<img src="${path}/img/catLong.svg" alt="pet-type" class="hl-svctarget">
			                        </c:if>
			                    </div>
			                </div>
			            </div>
			            </a>
            
        			</div>
					</c:forEach>
					
				</div>
				<div class="row align-center">
	        		<%@ include file="pages/page2_ByCompositeQuery.file" %>
	        		<input id="whichPage" type="hidden" name="whichPage" value="<%=whichPage%>">
				</div>
			</div>

<!-- search-map -->
			<div class="col-xl-5 hide-md search-map">
				<div class="search-map-wrapper">
					<div id="map"></div>
				</div>
			</div>
		</div>
		</form>
	</div>

	<!------------------ footer ------------------>
	<jsp:include page="/front-end/footer.jsp" />


	<!-- �פJjs -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/jquery.datetimepicker.full.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/rangeslider.js/2.3.0/rangeslider.min.js"></script>
	<script src="${jsPath}/sitFeeRange.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/jquery.rateit.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB4icTXRSh83NvMt7E3vY3ZrF4NGTb5AIs&libraries=places&callback=initMap" async defer></script>
	<script src="${jsPath}/google-map.js"></script>
	<script>
		$.datetimepicker.setLocale('zh');
		
		function search(p) {
			// �o�eajax���s�d��
			var data = $( "form" ).serialize();
			var dataFinal = data.substr(0, data.length-1) + p;
			
			if (p==0) {
				// ���M�Ť��e��marker
        		DeleteMarkers();
        		// ���s�j�M����ܷj�M���G��Marker
                addSearchData();
        		//
                changeCenter($("#keyword").val());
			}
			
			$.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/sitSrv/sitSrv.do",
                data: dataFinal,             	
                dataType: "json",
                success: function (result) {
                	var wrapper = $(".search-results-wrapper");
                	wrapper.empty();
                	
                	$.each(result, function (i, j) {
                		if (p==0) p+=1;
                		if (i<p*5 && i>=(p-1)*5){
// 	                		console.log("���X��"+i+"��json����G"+j.memName);
							var unit='��';
							if (j.sitSrvCode == 'Boarding') {
								unit = '��';
							} else if (j.sitSrvCode == 'DayCare') {
								unit = '��';
							} else if (j.sitSrvCode == 'PetTaxi') {
								unit = '����';
							}
							var dogPic = '';
							if (j.acpPetTyp!=0) {
								dogPic=`<img src="${path}/img/mdog.svg" alt="pet-type" class="hl-svctarget">`;
							}
							var catPic = '';
							if (j.acpPetTyp<=4) {
								catPic=`<img src="${path}/img/catLong.svg" alt="pet-type" class="hl-svctarget">`;
							}
							
	                		var newCard = 
	                		`<div class="panel" id=` + j.sitSrvNo +`>
	                			
	                			<a href="${pageContext.request.contextPath}/petSitter/petSitter.do?action=getOneSitter_DisplayForMem&sitNo=`+j.sitNo+`&sitSrvNo=`+ j.sitSrvNo+`">
	                			<div class="row align-middle" >
	 			                	<div class="col-md-3 col-sm-3 column hl-selfie" 
	 			                	style="background-image: url('${pageContext.request.contextPath}/PicReader.do?action=getMemPic&memNo=`+j.memNo+`');"></div>
	 			                	<div class="col-md-7 col-sm-7 column align-top">
				                    	<div class="hl-name">`+j.memName+`
				                    	</div>
		                				<div class="hl-title">`+j.sitSrvName+`</div>
					                    <div class="hl-stars">
					                        <div class="rateit" data-rateit-value=`+ j.totalComm / j.totalCus+` data-rateit-ispreset="true" data-rateit-readonly="true"></div>
					                    </div>
					                    <div class="hl-svc">`+
					                    	j.memAddress.substring(0,6)+`</div>
					                    <div class="hl-about">
					                    	<div class="hl-about-div repeat-clients">`+ j.repeatCus +` �ӭ��ƹw�q</div>
					                    	<div class="hl-about-div reviews">`+ j.totalComm +` �ӵ���</div>
					                    </div>
					                    <div style="display: none" class="memAddress">`+ j.memAddress + `</div>
					                    <div style="display: none" class="memPicPath">${pageContext.request.contextPath}/PicReader.do?action=getMemPic&memNo=`+j.memNo+`</div>
	                				</div>
	                				<div class="col-md-2 col-sm-2 column" style="position:relative;height:inherit;padding-right: 0;">
					                    <div class="hl-best-svc text-center">
					                        <div class="hl-best-price">$`+j.srvFee+`</div>
					                        <div class="hl-unit"> /&nbsp;`+unit+`</div>
					                    </div>
					                    <div class="align-center">`+
					                    	dogPic + catPic +`
					                    </div>
					            	</div>
	                			</div>
	                			</a>
	                		</div>`;
	                		
	                		wrapper.append(newCard);
	                		$(".rateit").rateit();
	                		
	                	}
                	});
                	$(".current").text(p);
                	$("#whichPage").val(p);//���input�ثe����
                	if (result.length <= 5) {
                		$(".pagination").hide();
                	} else {
                		$(".pagination").show();
                		changePage();
                	}
                	$('html, body').scrollTop(0);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                	console.log("ajax����"+xhr.responseText);
                }
            });
		}
		
		function changePage() {
			var pagination_first = $(".pagination-first").children("div");
			var pagination_prev = $(".pagination-prev").children("div");
			var pagination_next = $(".pagination-next").children("div");
			var pagination_final = $(".pagination-final").children("div");
			
			if ($(".current").text()==1) {
				pagination_first.fadeTo("normal",0).css("cursor", "default");
				pagination_prev.fadeTo("normal",0).css("cursor", "default");
				pagination_next.fadeTo("normal",1).css("cursor", "pointer");
				pagination_final.fadeTo("normal",1).css("cursor", "pointer");
			} else if ($(".current").text()==<%=pageNumber%>) {
				pagination_first.fadeTo("normal",1).css("cursor", "pointer");
				pagination_prev.fadeTo("normal",1).css("cursor", "pointer");
				pagination_next.fadeTo("normal",0).css("cursor", "default");;
				pagination_final.fadeTo("normal",0).css("cursor", "default");;
			} else if ($(".current").text()!=1 ) {
				pagination_first.fadeTo("normal",1).css("cursor", "pointer");
				pagination_prev.fadeTo("normal",1).css("cursor", "pointer");
				pagination_next.fadeTo("normal",1).css("cursor", "pointer");
				pagination_final.fadeTo("normal",1).css("cursor", "pointer");
			}
		}
		
		$(document).ready(function() {
			
			var service_seleced_btn 	= $(".service-seleced-btn");
			var sitSrvCode 				= service_seleced_btn.prev();
			var service_icon_rn			= service_seleced_btn.find(".service-icon");
			var service_seleced_name 	= service_seleced_btn.find(".service-seleced-name");
			var sitSrvCode_select 		= $(".sitSrvCode-select");
			var is_selected 			= $(".is-selected");
			var search_condition_btn 	= $(".search-condition-btn");
			
			search_condition_btn.click(function(e){
				e.preventDefault();
				search(0);
				
			})
			
			;
			// --------------------����--------------------
			var pagination_first = $(".pagination-first").children("div");
			var pagination_prev = $(".pagination-prev").children("div");
			var pagination_next = $(".pagination-next").children("div");
			var pagination_final = $(".pagination-final").children("div");
			var whichPage = $("#whichPage");
			
			if ($(".current").text()==1) {
				pagination_first.hide();
				pagination_prev.hide();
			}
			// �I�����ܲĤ@��
			pagination_first.click(function(){
				search(1);
			});
			// �I���W�@��
			pagination_prev.click(function(){
				var newPage = parseInt(whichPage.val());
				search(newPage-1);
			});
			// �I���U�@��
			pagination_next.click(function(){
				var newPage = parseInt(whichPage.val());
				search(newPage+1);
			});
			// �I���̫�@��
			pagination_final.click(function(){
				search(<%=pageNumber%>);
			});
			
			// --------------------��ܪA�ȿﶵ--------------------
			
			service_seleced_name.html(is_selected.find(".service-name").html());
			service_seleced_btn.click(function(e){
				e.preventDefault();
				
				if (sitSrvCode_select.css("display") == "block") {
					sitSrvCode_select.css("display", "none");
				} else {
					sitSrvCode_select.css("display", "block");
				}
				
			});

			// --------------------���A�ȿﶵ--------------------
				
			$(".selectMe").click(function(){
				
				var selectMe_rn = $(this);
				var service_icon = selectMe_rn.find(".service-icon");
				var service_name = selectMe_rn.find(".service-name");
				// ���M�����e�Q�襤�A�Ȫ�����class
				$(".selectMe").each(function(){
					$(".selectMe").removeClass("is-selected");
				})
				
				// ���btn�����e�A��ܥثe�襤�A��
				service_icon_rn.attr("class",service_icon.attr("class"));
				service_seleced_name.html(service_name.html());
				
				// �N�襤���A��value�m�Jinput
				sitSrvCode.val(selectMe_rn.attr("data-value"));
				
				// ���óQ�襤���A�ȫ��������
				selectMe_rn.addClass("is-selected");
				sitSrvCode_select.css("display", "none");
				
			});
				
			// --------------------����size--------------------
			
			var acpPetTypPart1 = $("[name='acpPetTypPart1']");
			var acpPetTyp2_div = $(".acpPetTyp2-div");
			var acpPetTypPart2 = $("[name='acpPetTypPart2']");
			
			if (acpPetTypPart1.prop("checked")) {
				acpPetTyp2_div.show();
			} else {
				acpPetTyp2_div.hide();
			}
			acpPetTypPart1.click(function(){
				if (acpPetTypPart1.prop("checked")) {
					acpPetTyp2_div.show();
				} else {
					acpPetTyp2_div.hide();
					acpPetTypPart2.each(function(){
						$(this).prop("checked", false);
					});
				}
			});
			
			// --------------------�d���ƶq--------------------
				
			var button_item_label = $(".button-item").find("label");
			
			button_item_label.click(function(){
				
				var button_item_label_rn = $(this);
				button_item_label.removeClass("checked");
				button_item_label_rn.addClass("checked");
				
			});
			
			var acpPetNum_input = $("[name='acpPetNum']");
			
			acpPetNum_input.each(function(){
				var label_rn = $(this).parent();
				
				if ($(this).prop("checked")) {
					label_rn.addClass("checked");
				} else {
					label_rn.removeClass("checked");
				}
				
			});
			
			
			// --------------------��h�z�����--------------------
			var otherFilters_more = $(".otherFilters-more");
			var choices = $(".otherFilters-more-choices");

			otherFilters_more.click(function(){
				
				if (choices.css("display") == "none") {
					$(this).find("i").css("transform", "scaleY(-1)");
					choices.css("display", "block")
				} else {
					$(this).find("i").css("transform", "scaleY(1)");
					choices.css("display", "none")
				}
			});
			
			var otherFilters_reset = $(".otherFilters-reset");
			otherFilters_reset.click(function(e){
				e.preventDefault();
				var choices_inputs = choices.find("input");
				choices_inputs.each(function(){
					$(this).prop("checked", false);
				});
			});
			
			// ----------------------���----------------------
			var today = new Date();
			var ninetyDays = new Date();
			ninetyDays.setDate(today.getDate()+90);
			$('#start_dateTime').datetimepicker({
				theme: 'light-bootstrap',
				format:'Y-m-d',
				onShow:function(){
					this.setOptions({
					maxDate:$('#end_dateTime').val()?$('#end_dateTime').val():false
					})
				},
				timepicker:false,
				beforeShowDay: function(date) {
					if ( date.getYear() <  today.getYear() || 
					   ( date.getYear() == today.getYear() && date.getMonth() <  today.getMonth()) || 
					   ( date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDate() < today.getDate())
					      ||
					     date.getYear() >  ninetyDays.getYear() || 
					   ( date.getYear() == ninetyDays.getYear() && date.getMonth() >  ninetyDays.getMonth()) || 
					   ( date.getYear() == ninetyDays.getYear() && date.getMonth() == ninetyDays.getMonth() && date.getDate() > ninetyDays.getDate())
					 ) {
					 	 return [false, ""]
					 }
					return [true, ""];
				}
			});
				 
			$('#end_dateTime').datetimepicker({
				theme: '',
				format:'Y-m-d',
				onShow:function(){
					this.setOptions({
					minDate:$('#start_dateTime').val()?$('#start_dateTime').val():false
					})
				},
				timepicker:false,
				beforeShowDay: function(date) {
					if ( date.getYear() <  today.getYear() || 
					   ( date.getYear() == today.getYear() && date.getMonth() <  today.getMonth()) || 
					   ( date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDate() < today.getDate())
					      ||
					     date.getYear() >  ninetyDays.getYear() || 
					   ( date.getYear() == ninetyDays.getYear() && date.getMonth() >  ninetyDays.getMonth()) || 
					   ( date.getYear() == ninetyDays.getYear() && date.getMonth() == ninetyDays.getMonth() && date.getDate() > ninetyDays.getDate())
					 ) {
					 	 return [false, ""]
					 }
					return [true, ""];
				}
			});	
			
		});

	</script>
	

</body>
</html>