<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>保姆的所有證照</TITLE>

<!-- 匯入外部CSS -->
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
    
<!------------------ 內文body ------------------>
    <div class="container">
		
		<% 
			String sitNo = (String) session.getAttribute("sitNo");
			SitLicService slSvc = new SitLicService();
			List<SitLicVO> list = slSvc.getSitAllLic(sitNo);
			pageContext.setAttribute("list", list);
		%>
		<div class="row" style="margin-top: 30px;">
			<!-- 錯誤列表 -->
		   	<div class="errorList" style="width:50%"> 
				<c:if test="${not empty errorMsgs}" >
					<font style="color:red;">發生以下錯誤：</font>
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
					</svg><span style="position: relative;top: 5px;">新增證書</span>
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
						    <p><span>到期日</span> <c:out value="${sitLic.licEXP}" default="-"/></p>
						    <c:set var="licStatus" value="${sitLic.licStatus}"/>
							<% 
								String[] statusArr = {"審核中", "審核通過", "審核未通過", "過期證照"};
								String status = statusArr[(Integer)pageContext.getAttribute("licStatus")];
								pageContext.setAttribute("status", status);
							%>
						    <p><span>證照狀態</span> ${status}</p>
						    
						    <div class='button'>
						    	<FORM method="post" action="${pageContext.request.contextPath}/sitLic/sitLic.do">
									<input class="updBtn" type="submit" value="修改">
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
	            var _this = $(this);//將當前的pimg元素作為_this傳入函式  
	            console.log(_this);
	            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);  
	        });  
	    });  

	    function imgShow(outerdiv, innerdiv, bigimg, _this){  
	        var src = _this.attr("src");//獲取當前點選的pimg元素中的src屬性  
	        $(bigimg).attr("src", src);//設定#bigimg元素的src屬性  
	      
	            /*獲取當前點選圖片的真實大小，並顯示彈出層及大圖*/  
	        $("<img>").attr("src", src).on("load",function(){
	            var windowW = $(window).width();//獲取當前視窗寬度  
	            var windowH = $(window).height();//獲取當前視窗高度  
	            var realWidth = this.width;//獲取圖片真實寬度  
	            var realHeight = this.height;//獲取圖片真實高度  
	            var imgWidth, imgHeight;  
	            var scale = 0.8;//縮放尺寸，當圖片真實寬度和高度大於視窗寬度和高度時進行縮放  
	              
	            if(realHeight>windowH*scale) {//判斷圖片高度  
	                imgHeight = windowH*scale;//如大於視窗高度，圖片高度進行縮放  
	                imgWidth = imgHeight/realHeight*realWidth;//等比例縮放寬度  
	                if(imgWidth>windowW*scale) {//如寬度扔大於視窗寬度  
	                    imgWidth = windowW*scale;//再對寬度進行縮放  
	                }  
	            } else if(realWidth>windowW*scale) {//如圖片高度合適，判斷圖片寬度  
	                imgWidth = windowW*scale;//如大於視窗寬度，圖片寬度進行縮放  
	                            imgHeight = imgWidth/realWidth*realHeight;//等比例縮放高度  
	            } else {//如果圖片真實高度和寬度都符合要求，高寬不變  
	                imgWidth = realWidth;  
	                imgHeight = realHeight;  
	            }  
	            $(bigimg).css("width",imgWidth);//以最終的寬度對圖片縮放  
	              
	            var w = (windowW-imgWidth)/2;//計算圖片與視窗左邊距  
	            var h = (windowH-imgHeight)/2;//計算圖片與視窗上邊距  
	            $(innerdiv).css({"top":h, "left":w});//設定#innerdiv的top和left屬性  
	            $(outerdiv).fadeIn("fast");//淡入顯示#outerdiv及.pimg  
	        });  
	          
	        $(outerdiv).click(function(){//再次點選淡出消失彈出層  
	            $(this).fadeOut("fast");  
	        });  
	    }  
	
	</script>
	
<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>
    
</body>
</html>