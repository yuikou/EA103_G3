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

<TITLE>新增證書</TITLE>

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
<!-- 以下為首頁navigation bar -->
    <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light myNav">
        <div class="container-fluid">
            <!-- logo (click to home page)-->
            <a class="navbar-brand" href="#" style="padding-top: 0px;padding-bottom: 0px"><img class="myLogo"
                    src="https://dzmg8959fhe1k.cloudfront.net/all/logo.jpg"><img class="myLogoWord"
                    src="https://dzmg8959fhe1k.cloudfront.net/all/logoWord.jpg"></a>
            <!-- logo -->
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation"
                style="margin-right: 15px">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="myNavDiv collapse navbar-collapse" id="navbarText">
                <!-- 超連結連到各服務搜尋頁面 -->
                <ul class="navbar-nav ml-auto">
	                <div class="myUndeLine">
	                    <li class="nav-item">
                            <a class="nav-link accordion myFirst" href="#"><span class="ch-word">寵物服務</span><span
                                    class="en-word">Services</span></a>
                            <a class="nav-link morelink" href="#">托養</a>
                            <a class="nav-link morelink" href="#">美容</a>
                            <a class="nav-link morelink" href="#">領養</a>
                    	</li>
        	        </div>
                   	<div class="myUndeLine">
                    	<li class="nav-item">
                            <a class="nav-link accordion myFirst" href="#"><span class="ch-word">寵友互動</span><span
                                    class="en-word">Interaction</span></a>
                            <a class="nav-link morelink" href="#">討論區</a>
                            <a class="nav-link morelink" href="#">揪團</a>
                    	</li>
                    </div>
                    <div class="myUndeLine">
                    	<li class="nav-item">
                            <a class="nav-link accordion" href="#"><span class="ch-word">美容業者專區</span><span
                                    class="en-word">Salon store</span></a>
                    	</li>
                    </div>
                </ul>
                <!-- 以上超連結連到各服務搜尋頁面 -->
                <!-- 登入按鈕 -->
                <input type="button" class="myBtn" value="sign in"></input>
                <!-- 以上登入btn -->
            </div>
        </div>
    </nav>
    <!-- 首頁navigation bar end-->
    
	
	<!-- 內文body -->
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

	<!-- 測試先鎖定sitNo=S005 -->
	<% session.setAttribute("sitNo","S001");%>
		<!-- 測試用 -->
		<div class="test">
			<a href="listOneSitAllLic.jsp">會員查看所有證書</a>
			<a href="listUnverifiedLic.jsp">員工查看待審核證書</a>
		</div>
		<!-- 測試用 -->
	
<!-- 新增證書 -->
        <div class="limiter">
	        <div class="container-login100 cover">
	            <div class="wrap-login100 mybody">
	            
				
					<FORM class="login100-form validate-form p-l-55 p-r-55 p-t-90" 
							action="sitLic.do" method="post"
							enctype="multipart/form-data">
						<span class="login100-form-title">
	                        	新增保姆證照
	                    </span>
	                    
						<!-- 測試先鎖定sitNo=S005 -->
						<input type="hidden" name="sitNo" value="S005">
						
						<!-- 以下取得新增證書的參數 -->
						<div class="licBox">
				           <div class="wrap-input100 validate-input" data-validate="請輸入證照名稱">
				           		<input class="input100" type="text" name="licName" placeholder="證照名稱">
				           		<span class="focus-input100"></span>
				           </div>
				           <div class="info txt1 myP">
                                	證照失效日 <˙span>*</span>
                                <div class="add-note">
                                    <span>若證照有失效日期，請於下列日期欄位輸入</span>
                                </div>
                           </div>
				           <div class="wrap-input100">
				           		<input class="input100" type="date" name="licEXP" placeholder="證照到期日" style="margin-top:0">
				           		<span class="focus-input100"></span>
				           </div>
				           <div class="wrap-input100 hideDiv">
				           		<input class="input100" type="text" name="licStatus" placeholder="證照狀態">
				           		<span>新增時預設狀態0-待審核</span>
				           		<span class="focus-input100"></span>
				           </div>
					       <div class="wrap-input100 validate-input"  data-validate="請上傳執照圖片、檔案">
					           	<label class="myLabel">
					           		<span class="txt1 myP uploadTXT info">上傳寵物相關證照</span>
					           		<img src="${path}/img/upload.svg" class="myUpload">
					           		<input class="myFile" type="file" name="licPic" style="display:none">
					           	</label>
					       </div>
					       <div class="wrap-input100 validate-input input100 myP preview" style="margin-top:0;">
					                                圖片預覽<button class="login100-form-btn remove">刪除</button>
					       </div>
					       <div class="container-login100-form-btn p-b-40">
						   		<input type="hidden" name="action" value="add">
						   		<input class="login100-form-btn input100" id="send" type="submit" value="新增">
                        	</div>
						</div>
					</FORM>
				</div>
            </div>
        </div>

		<script src="${path}/js/jquery-3.2.1.min.js"></script>
		<script src="${path}/js/animsition.min.js"></script>
		<script src="${path}/js/popper.js"></script>
		<script src="${path}/js/bootstrap.min.js"></script>
		<script src="${path}/js/main.js"></script>
		<script src="${path}/js/checkSitPitter.js"></script>
		<!-- 上傳圖片預覽 -->
		<script type="text/javascript">
            function init() {
                var myFile = document.getElementsByClassName('myFile');
                var preview = document.getElementsByClassName('preview');
                var file;
                
                /*註冊檔案上傳事件*/
                for (let i = 0; i < myFile.length; i++) {
                    let index = i;
                    myFile[index].addEventListener('change', function (e) {
                        //取得檔案物件
                        var file = e.target.files;
                        console.log(e.target.value);
                        console.log(myFile[index].value);
                        
                        //判斷檔案是否存在
                        if (file !== null) {
                            for (var i = 0; i < file.length; i++) {
                                //判斷file.type的型別是否包含'image', 不是的話要彈出警告
                                if (file[i].type.indexOf('image') > -1) {
                                    //file物件存在:在FileReader物件上註冊load事件 - 載入檔案的意思
                                    var fr = new FileReader();
                                    fr.addEventListener('load', function (e) {
                                        //取得結果
                                        console.log(e.target.result);
    
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
    </div>
    <!-- 內文end -->
    <!-- 客服小按鈕 -->
    <div class="qaicon">
        <a href="#"><img src="https://dzmg8959fhe1k.cloudfront.net/all/unicorn.svg" /></a>
    </div>
    <!-- footer -->
    <div class="container-fluid main-footer text-center">
        <div class="row myFooterContainer">
            <div class="col-12 col-sm">
                <ul>About us
                    <li><a href="#" class="myFooterLink">服務條款</a></li>
                    <li><a href="#" class="myFooterLink">隱私權政策</a></li>
                </ul>
            </div>
            <div class="col-12 col-sm">
                <ul>FAQ
                    <li><a href="#" class="myFooterLink">常見問題</a></li>
                    <li><a href="#" class="myFooterLink">意見回饋</a></li>
                </ul>
            </div>
            <div class="col-12 col-sm">
                <ul>Contact us
                    <li><a href="#" class="myFooterLink">發送電子郵件</a></li>
                    <li><a href="#" class="myFooterLink">追蹤我們</a></li>
                </ul>
            </div>
        </div>
        <p class="copyright">&copy; copyright by <i>NiHaiZaiMa</i>
        <p>
    </div>
</body>
</html>