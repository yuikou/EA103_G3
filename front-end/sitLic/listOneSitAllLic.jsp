<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>�O�i���Ҧ��ҷ�</TITLE>

<!-- �פJ�~��CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/animate.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/animsition.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/util.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY>

<!-------------------- nav -------------------->
	<jsp:include page="/front-end/header.jsp"/>
    
<!------------------ ����body ------------------>
    <div class="container">
		
		<% 
			String sitNo = (String) session.getAttribute("sitNo");
			SitLicService slSvc = new SitLicService();
			List<SitLicVO> list = slSvc.getSitAllLic(sitNo);
			pageContext.setAttribute("list", list);
		%>
		<div class="row" style="margin-top: 30px;">
			<!-- ���~�C�� -->
		   	<div class="errorList" style="width:50%"> 
				<c:if test="${not empty errorMsgs}" >
					<font style="color:red;">�o�ͥH�U���~�G</font>
					<ul>
					<c:forEach var="msg" items="${errorMsgs}">
						<li style="color:red;">${msg}</li>
					</c:forEach>
					</ul>
				</c:if>
			</div>
			<div style="width:50%">
				<a class="addLic" href="addSitLic.jsp">
					<svg viewBox="0 0 16 16" class="bi bi-file-earmark-plus" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
		  				<path d="M4 0h5.5v1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h1V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2z"/>
		  				<path d="M9.5 3V0L14 4.5h-3A1.5 1.5 0 0 1 9.5 3z"/>
		  				<path fill-rule="evenodd" d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>
					</svg><span style="position: relative;top: 5px;">�s�W�Ү�</span>
				</a>
			</div>
		</div>
		
		<div class="wrap">
			<div class="table">
				<ul>
  					<c:forEach var="sitLic" items="${list}" varStatus="no">
					<li class="licLi">
						<div class="top">
						    <h1 class="licNameH1">${sitLic.licName}</h1>
						    <div class="circle"><img class="pimg" alt="" src="${pageContext.request.contextPath}/PicReader.do?action=sitLic&licNo=${sitLic.licNo}"></img></div>
						</div>
						<div class="bottom">
						    <p><span>�����</span> <c:out value="${sitLic.licEXP}" default="-"/></p>
						    <c:set var="licStatus" value="${sitLic.licStatus}"/>
							<% 
								String[] statusArr = {"�f�֤�", "�f�ֳq�L", "�f�֥��q�L", "�L���ҷ�"};
								String status = statusArr[(Integer)pageContext.getAttribute("licStatus")];
								pageContext.setAttribute("status", status);
							%>
						    <p><span>�ҷӪ��A</span> ${status}</p>
						    
						    <div class='button'>
						    	<FORM method="post" action="${pageContext.request.contextPath}/sitLic/sitLic.do">
									<input class="updBtn" type="submit" value="�ק�">
									<input type="hidden" name="licNo" value="${sitLic.licNo}">
									<input type="hidden" name="action" value="getOne_For_Update">
								</FORM>
						    </div>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
    		<div id="innerdiv" style="position:absolute;">
        		<img id="bigimg" style="border:5px solid #fff;" src="" />
    		</div>
		</div>
	</div>
	
	<script src="${pageContext.request.contextPath}/js/euphy/jquery-3.2.1.min.js"></script>
	<script>
		$(".licLi").each(function(){
			$(this).hover(function(){
				$(this).children(".top").css("background-color", "#5D4660");
				$(this).children(".top").css("color", "#FFF");
			},function(){
				$(this).children(".top").css("background-color", "#EAE9E4");
				$(this).children(".top").css("color", "#989A8F");
			});
		});
		
		$(function(){  
	        $(".pimg").click(function(){  
	            var _this = $(this);//�N��e��pimg�����@��_this�ǤJ�禡  
	            console.log(_this);
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
	
<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>
    
</body>
</html>