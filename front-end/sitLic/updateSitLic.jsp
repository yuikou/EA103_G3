<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>修改證書</TITLE>

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

		<!-- 修改證書 -->	
		<div class="limiter">
	        <div class="container-login100 cover">
	            <div class="wrap-login100 mybody">
					<FORM class="login100-form validate-form p-l-55 p-r-55 p-t-70" 
						action="${pageContext.request.contextPath}/sitLic/sitLic.do" method="post"
						enctype="multipart/form-data">
						<span class="login100-form-title">
							<a class="back" href="${pageContext.request.contextPath}/front-end/sitLic/listOneSitAllLic.jsp">
							<svg viewBox="0 0 16 16" class="bi bi-box-arrow-in-left" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  								<path fill-rule="evenodd" d="M10 3.5a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 1 1 0v2A1.5 1.5 0 0 1 9.5 14h-8A1.5 1.5 0 0 1 0 12.5v-9A1.5 1.5 0 0 1 1.5 2h8A1.5 1.5 0 0 1 11 3.5v2a.5.5 0 0 1-1 0v-2z"/>
  								<path fill-rule="evenodd" d="M4.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H14.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
							</svg>
							</a>
				                               修改保姆證照
				            <a class="search" href="${pageContext.request.contextPath}/front-end/sitLic/listOneSitAllLic.jsp" style="visibility: hidden;">
	                        	<svg viewBox="0 0 16 16" class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  									<path fill-rule="evenodd" d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"/>
									<path fill-rule="evenodd" d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"/>
								</svg>
							</a>
				        </span>
						<div class="licBox">
				           	<div class="wrap-input100 hideDiv">
				           		<input class="input100" type="text" name="licNo" value="${sitLicVO.licNo}">
				           		<span class="focus-input100"></span>
				           	</div>
				           	<div class="wrap-input100 hideDiv">
				           		<input class="input100" type="text" name="sitNo" value="${sitLicVO.sitNo}">
				           		<span class="focus-input100"></span>
				           	</div>
				           	<div class="info txt1 myP">
								證照名稱
                           	</div>
				           	<div class="wrap-input100 validate-input" data-validate="請輸入證照名稱">
				           		<input class="input100" type="text" name="licName" value="${sitLicVO.licName}">
				           		<span class="focus-input100"></span>
				           	</div>
				           	<div class="info txt1 myP">證照失效日 <span class="">*</span>
			                    <div class="add-note">
			                         <span>若證照有失效日期，請於下列日期欄位輸入</span>
			                    </div>
			               	</div>
				           	<div class="wrap-input100">
				           		<input class="input100" type="date" name="licEXP" value="${sitLicVO.licEXP}">
				           		<span class="focus-input100"></span>
				           	</div>
				           	<div class="wrap-input100 hideDiv">
				           		<input class="input100" type="text" name="licStatus" value="0">
				           		<span class="focus-input100"></span>
				           	</div>
				           	<div class="info txt1 myP">證照圖片 </div>
							<div class="wrap-input100">
								<div class="myOldPic">
				           			<img class="licPicImg" alt="" src="${pageContext.request.contextPath}/PicReader.do?action=sitLic&licNo=${sitLicVO.licNo}">
				           		</div>
				           	</div>
					        <div class="wrap-input100">
					           	<label class="myLabel">
					           		<span class="txt1 myP uploadTXT info">如果要修改證照，請上傳圖片</span>
					           		<img src="${pageContext.request.contextPath}/front-end/img/upload.svg" class="myUpload">
					           		<input class="myFile" type="file" name="licPic" style="display:none">
					           	</label>
					        </div>
					        <div class="wrap-input100 validate-input input100 myP preview" style="margin-top:0;padding-bottom:5px;">
					                                圖片檔預覽<button class="login100-form-btn remove hideDiv">刪除</button>
					        </div>
					        <!-- 先創建Svc -->
							<jsp:useBean id="sitSrvSvc" class="com.sitSrv.model.SitSrvService"></jsp:useBean>
					        <div class="container-login100-form-btn p-b-40">
						   		<input type="hidden" name="action" value="update">
						   		<c:if test="${sitLicVO.licName == '特定寵物業許可證'}">
						   		<c:forEach var="ssVO" items="${sitSrvSvc.get_OneSit_AllSrv(sitLicVO.sitNo)}">
								<c:if test="${ssVO.sitSrvCode == 'Boarding'}">
								<input type="hidden" name="sitSrvNo" value="${ssVO.sitSrvNo}">
								</c:if>
								</c:forEach>
						   		</c:if>
						   		<input class="login100-form-btn input100" id="send" type="submit" value="修改">
                        	</div>
						</div>
					</FORM>
				</div>
			</div>
		</div>
	</div>
	
<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>
    
    
<!-- 匯入js -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/animsition.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/main.js"></script>
	<!-- 上傳圖片預覽 -->
	<script type="text/javascript">
    	function init() {
        	var myFile = document.getElementsByClassName('myFile');
            var preview = document.getElementsByClassName('preview');
            var file;
                
            /*註冊檔案上傳事件*/
            for (let i = 0; i < myFile.length; i++) {
                let index = i;
                myFile[index].addEventListener('click', function(e){
                   e.target.value = ''
                })
                myFile[index].addEventListener('change', function (e) {
                   //取得檔案物件
                   var file = e.target.files;
                   console.log(myFile[index].value);
                        
                   //判斷檔案是否存在
                   if (file !== null) {
                       for (var i = 0; i < file.length; i++) {
                       //判斷file.type的型別是否包含'image', 不是的話要彈出警告
                       if (file[i].type.indexOf('image') > -1) {
                            //file物件存在:在FileReader物件上註冊load事件 - 載入檔案的意思
                            var fr = new FileReader();
                            fr.addEventListener('load', function (e) {
                            	// 打開刪除按鈕
                           	 	$(".remove").removeClass("hideDiv"); 
                            	//新增img元素
                                 var img = document.createElement('img');
                                 //賦予屬性
                                 img.setAttribute('src', e.target.result);
                                 img.classList.add("reviewImg");
    
                                 //新增一個div包住img
                                 var box = document.createElement('div');
                                 box.setAttribute('class', 'myReview');
                                 box.append(img);
                                 preview[index].append(box);
                             });
                             // 使用FileReader物件上的 readAsDataURL(file) 的方法，傳入要讀取的檔案，並開始進行讀取
                             fr.readAsDataURL(file[i]);
                        } else {
                             alert('請上傳圖片檔!');
                      	}
               		}
            	}
                        //e.target.value = ''
                        /*
                        	解決input type=file 同一個檔案二次上傳無效的問題!
                        	使用input[type=file]的檔案上傳功能，是通過onchange事件觸發js程式碼, 如果兩次檔案是重複的，所以這個時候onchange事件是沒有觸發到的。解決方法 :  把input的value設定為空
                        	!!! 這樣Part就取不到了
                        */
           });
       }
                
       // 註冊刪除事件
       $('.remove').on('click', function(e){
           e.preventDefault();
           $(this).next(".myReview").remove();
           myFile[0].value='';
        	// 關閉刪除按鈕
      	   $(".remove").addClass("hideDiv");
       });

	}
    window.onload = init;
    </script>
</body>
</html>