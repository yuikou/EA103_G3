<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>

<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>�f���ҷ�</TITLE>

<!-- �פJ�~��CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY >

<!------------------ ����body ------------------>
    <div class="container">
    
		<!-- ���~�C�� -->
	   	<div class="errorList"> 
			<c:if test="${not empty errorMsgs}" >
				<font style="color:red;">�o�ͥH�U���~�G</font>
				<ul>
				<c:forEach var="msg" items="${errorMsgs}">
					<li style="color:red;">${msg}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>

		<div class="backFromDB">
		<table class="vrfTable">
				<tr>
					<th style="min-width:auto;">NO.</th>
					<th>�ҷӽs��</th>
					<th>�|���W��</th>
					<th>�ҷӦW��</th>
					<th>�ҷӨ����</th>
					<th>�ҷӪ��A</th>
					<th>�ҷӹϤ�</th>
					<th>�f�ֵ��G</th>
				</tr>
				
				<!-- ���Ы�Svc -->
				<jsp:useBean id="memSvc" class="com.member.model.MemService"></jsp:useBean>
				<jsp:useBean id="petSitterSvc" class="com.petSitter.model.PetSitterService"></jsp:useBean>
				<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"></jsp:useBean>
				<% 
					SitLicService slSvc = new SitLicService();
					List<SitLicVO> list = slSvc.getUnverifiedLic(0);
					pageContext.setAttribute("list", list);
				%>
			<c:forEach var="sitLic" items="${list}" varStatus="no">
				<tr id="${sitLic.licNo}">
					<td>${no.count}</td>
					<td>${memSvc.getOneMem(petSitterSvc.getByPK(sitLic.sitNo).memNo).memName}</td>
					<td>${sitLic.licNo}</td>
					<td>${sitLic.licName}</td>
					<td><c:out value="${sitLic.licEXP}" default="-"/></td>
					<td>${sitLic.licStatus}</td>
					<c:set var="licNo" value="${sitLic.licNo}"/>
					<td class="myLicPic"><img class="licPicImg pimg" alt="" src="${pageContext.request.contextPath}/PicReader.do?action=sitLic&licNo=${licNo}"></td>
					<td>
						<div id="successDiv">
<!-- 						<FORM method="post" action="sitLic.do"> -->
							<input class="vrfBtn" type="button" value="�q�L">
							<input type="hidden" name="licNo" value="${sitLic.licNo}">
							<input type="hidden" name="licStatus" value=1>
							<c:if test="${sitLic.licName == '�S�w�d���~�\�i��'}">
							<c:forEach var="ssVO" items="${sitSrvSvc.get_OneSit_AllSrv(sitLic.sitNo)}">
							<c:if test="${ssVO.sitSrvCode == 'Boarding'}">
							<input type="hidden" name="sitSrvNo" value="${ssVO.sitSrvNo}">
							</c:if>
							</c:forEach>
							</c:if>
							<input type="hidden" name="action" value="verify">
						</div>
						<div id="failDiv">
<!-- 						</FORM> -->
<!-- 						<FORM method="post" action="sitLic.do"> -->
							<input class="vrfBtn" type="button" value="���q�L">
							<input type="hidden" name="licNo" value="${sitLic.licNo}">
							<input type="hidden" name="licStatus" value=2>
							<c:if test="${sitLic.licName == '�S�w�d���~�\�i��'}">
							<c:forEach var="ssVO" items="${sitSrvSvc.get_OneSit_AllSrv(sitLic.sitNo)}">
							<c:if test="${ssVO.sitSrvCode == 'Boarding'}">
							<input type="hidden" name="sitSrvNo" value="${ssVO.sitSrvNo}">
							</c:if>
							</c:forEach>
							</c:if>
							<input type="hidden" name="action" value="verify">
<!-- 						</FORM> -->
						</div>
					</td>
				</tr>
			</c:forEach>
			</table>
		</div>
		<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
    		<div id="innerdiv" style="position:absolute;">
        		<img id="bigimg" style="border:5px solid #fff;" src="" />
    		</div>
		</div>
	</div>

<!-- �פJjs -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script>
		$(function(){  
			$(".vrfBtn").click(function(){
				var thisbtn = $(this);
				var licNo = thisbtn.nextAll("[name='licNo']");
				var licStatus = thisbtn.nextAll("[name='licStatus']");
				var sitSrvNo = thisbtn.nextAll("[name='sitSrvNo']");
				
				console.log(thisbtn.val());
				console.log(licNo.val());
				console.log(licStatus.val());
				console.log(sitSrvNo.val());
				
				$.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/sitLic/sitLic.do",
                    data: {
                    	action:"verify",
                    	licNo: licNo.val(),
                    	licStatus: licStatus.val(),
                    	sitSrvNo: sitSrvNo.val(),
                    },
                    dataType: "html",
                    success: function (result) {
                    	if (result.indexOf("error") == -1) {
	                    	$("#"+result).remove();
                    	} else {
                         	alert(result);
                        }
                    },
	                error: function (xhr, ajaxOptions, thrownError) {
	                	console.log("ajax����");
	                }
                });
			});
			
	        $(".pimg").click(function(){  
	            var _this = $(this);//�N��e��pimg�����@��_this�ǤJ�禡  
// 	            console.log(_this);
	            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);  
	        });  
	    });  

	    function imgShow(outerdiv, innerdiv, bigimg, _this){  
	        var src = _this.attr("src");//�����e�I�諸pimg��������src�ݩ�  
	        $(bigimg).attr("src", src);//�]�w#bigimg������src�ݩ�  
	      
	            /*�����e�I��Ϥ����u��j�p�A����ܼu�X�h�Τj��*/  
	        $("<img>").attr("src", src).on("load",function(){
	            var windowW = $(window).width();//�����e�����e��  
	            var windowH = $(window).height();//�����e��������  
	            var realWidth = this.width;//����Ϥ��u��e��  
	            var realHeight = this.height;//����Ϥ��u�갪��  
	            var imgWidth, imgHeight;  
	            var scale = 0.8;//�Y��ؤo�A��Ϥ��u��e�שM���פj������e�שM���׮ɶi���Y��  
	              
	            if(realHeight>windowH*scale) {//�P�_�Ϥ�����  
	                imgHeight = windowH*scale;//�p�j��������סA�Ϥ����׶i���Y��  
	                imgWidth = imgHeight/realHeight*realWidth;//������Y��e��  
	                if(imgWidth>windowW*scale) {//�p�e�ץ��j������e��  
	                    imgWidth = windowW*scale;//�A��e�׶i���Y��  
	                }  
	            } else if(realWidth>windowW*scale) {//�p�Ϥ����צX�A�A�P�_�Ϥ��e��  
	                imgWidth = windowW*scale;//�p�j������e�סA�Ϥ��e�׶i���Y��  
	                            imgHeight = imgWidth/realWidth*realHeight;//������Y�񰪫�  
	            } else {//�p�G�Ϥ��u�갪�שM�e�׳��ŦX�n�D�A���e����  
	                imgWidth = realWidth;  
	                imgHeight = realHeight;  
	            }  
	            $(bigimg).css("width",imgWidth);//�H�̲ת��e�׹�Ϥ��Y��  
	              
	            var w = (windowW-imgWidth)/2;//�p��Ϥ��P��������Z  
	            var h = (windowH-imgHeight)/2;//�p��Ϥ��P�����W��Z  
	            $(innerdiv).css({"top":h, "left":w});//�]�w#innerdiv��top�Mleft�ݩ�  
	            $(outerdiv).fadeIn("fast");//�H�J���#outerdiv��.pimg  
	        });  
	          
	        $(outerdiv).click(function(){//�A���I��H�X�����u�X�h  
	            $(this).fadeOut("fast");  
	        });  
	    }  
	    
	</script>
</body>
</html>