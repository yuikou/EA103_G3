<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sitLic.model.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<TITLE>修改證書</TITLE>

<!-- 匯入外部CSS -->
<c:set var="path" value="/EA103G3/front-end" />

<link rel="stylesheet" type="text/css" href="${path}/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="${path}/css/Main.css">
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${path}/css/animate.css">
<link rel="stylesheet" type="text/css" href="${path}/css/animsition.min.css">
<link rel="stylesheet" type="text/css" href="${path}/css/util.css">
<link rel="stylesheet" type="text/css" href="${path}/css/Petfect.css">
<link rel="stylesheet" type="text/css" href="${path}/css/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${path}/css/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<BODY>

<!-------------------- nav -------------------->
	<jsp:include page="../nav.jsp"/>
    
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
	<% 
		SitLicVO sitLicVO = (SitLicVO) request.getAttribute("sitLicVO");
		pageContext.setAttribute("sitLicVO", sitLicVO);
	%>
	
		<!-- 測試用 -->
		<div class="prev">
			<a href="listOneSitAllLic.jsp">回到上一頁</a>
		</div>
		<!-- 測試用 -->
		
		<div class="limiter" style="margin:100px 0 40px 0;">
	        <div class="container-login100 cover">
	            <div class="wrap-login100 mybody">
					<FORM class="login100-form validate-form p-l-55 p-r-55 p-t-70" 
						action="sitLic.do" method="post"
						enctype="multipart/form-data">
						<span class="login100-form-title">
				                                        修改保姆證照
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
				           		<img class="licPicImg" alt="" src="${pageContext.request.contextPath}/ShowImg?action=sitLic&licNo=${sitLicVO.licNo}">
				           	</div>
					        <div class="wrap-input100">
					           	<label class="myLabel">
					           		<span class="txt1 myP uploadTXT info">如果要修改證照，請上傳圖片、檔案</span>
					           		<img src="${path}/img/upload.svg" class="myUpload">
					           		<input class="myFile" type="file" name="licPic" style="display:none">
					           	</label>
					        </div>
					        <div class="wrap-input100 validate-input input100 myP preview" style="margin-top:0;">
					                                圖片檔預覽<button class="login100-form-btn remove">刪除</button>
					        </div>
					        <div class="container-login100-form-btn p-b-40">
						   		<input type="hidden" name="action" value="update">
						   		<input class="login100-form-btn input100" id="send" type="submit" value="修改">
                        	</div>
						</div>
					</FORM>
				</div>
			</div>
		</div>
	</div>
	
<!------------------ footer ------------------>
    <jsp:include page="../footer.jsp"/>
    
    
<!-- 匯入js -->
	<script src="${path}/js/jquery-3.2.1.min.js"></script>
	<script src="${path}/js/animsition.min.js"></script>
	<script src="${path}/js/popper.js"></script>
	<script src="${path}/js/bootstrap.min.js"></script>
	<script src="${path}/js/main.js"></script>
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
       });

	}
    window.onload = init;
    </script>
</body>
</html>