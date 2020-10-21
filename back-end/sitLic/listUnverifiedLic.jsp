<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>

<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>審核證照</TITLE>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY >

<!------------------ 內文body ------------------>
    <div class="container">
    
		<!-- 錯誤列表 -->
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

		<div class="backFromDB">
		<table class="vrfTable">
				<tr>
					<th style="min-width:auto;">NO.</th>
					<th>證照編號</th>
					<th>會員名稱</th>
					<th>證照名稱</th>
					<th>證照到期日</th>
					<th>證照狀態</th>
					<th>證照圖片</th>
					<th>審核結果</th>
				</tr>
				
				<!-- 先創建Svc -->
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
							<input class="vrfBtn" type="button" value="通過">
							<input type="hidden" name="licNo" value="${sitLic.licNo}">
							<input type="hidden" name="licStatus" value=1>
							<c:if test="${sitLic.licName == '特定寵物業許可證'}">
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
							<input class="vrfBtn" type="button" value="不通過">
							<input type="hidden" name="licNo" value="${sitLic.licNo}">
							<input type="hidden" name="licStatus" value=2>
							<c:if test="${sitLic.licName == '特定寵物業許可證'}">
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

<!-- 匯入js -->
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
	                	console.log("ajax失敗");
	                }
                });
			});
			
	        $(".pimg").click(function(){  
	            var _this = $(this);//將當前的pimg元素作為_this傳入函式  
// 	            console.log(_this);
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
</body>
</html>