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

<TITLE>新增證書</TITLE>

<!-- 匯入外部CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">    
<link rel="stylesheet" type="text/css" href="${cssPath}/Main.css">
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/animate.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/animsition.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/util.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/pitSitterForm.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitLicAll.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

</head>

<BODY>
    
<!------------------ 內文body ------------------>
    <div class="sub-container">
		
<!-- 新增證書 -->
	    <div class="container-login100 cover" style="padding: 0;">
	    	<div class="wrap-login100 mybody" style="padding: 0 20px;box-shadow: none;">
	            
				<!-- 以下取得新增證書的參數 -->
				<div class="licBox">
					<div class="info txt1 myP">
						證照名稱
                    </div>
				    <div class="wrap-input100 validate-input">
				       	<input class="input100" type="text" name="licName" value="特定寵物業許可證" readonly="readonly" style="margin-top:0">
				        <span class="focus-input100"></span>
				    </div>
				    <div class="info txt1 myP">
                    	證照失效日 <span>*</span>
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
				       	<span class="focus-input100"></span>
				    </div>
					<div class="wrap-input100 myP">
					   	<label class="myLabel">
					   		<span class="txt1 myP uploadTXT info">上傳寵物相關證照</span>
					       	<img src="${path}/img/upload.svg" class="myUpload">
					       	<input class="myFile" type="file" name="licPic" style="display:none">
					   	</label>
					</div>
					<div class="wrap-input100 validate-input input100 myP preview" style="margin-top:0;padding-bottom:5px;" data-validate="請上傳證照圖片">
					   	<input class="input100 hideDiv" type="text" id="licPic">
					   	<span class="focus-input100"></span>
					                         圖片預覽<button class="login100-form-btn remove hideDiv">刪除</button>
					</div>
				</div>
			</div>
      	</div>
    </div>


<!-- 匯入js -->
	<c:set var="jsPath" value="/EA103G3/js/euphy" />
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
                
            /*註冊檔案上傳事件*/
            for (let i = 0; i < myFile.length; i++) {
            	let index = i;
                myFile[index].addEventListener('change', function (e) {
                	//取得檔案物件
                    var file = e.target.files;
                    //判斷檔案是否存在
                   	//console.log(myFile[index].value);
                    // 每次重新上傳就清空預覽圖片(只上傳一張)
                   	$('.myReview').remove();
                    
                   	//判斷檔案是否存在
                   	if (file !== null) {
                   		$("#licPic").val("有檔案囉");
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
           $("#licPic").val("");
           // 關閉刪除按鈕
      	   $(".remove").addClass("hideDiv");
       });

	}
    window.onload = init;
    </script>
    
</body>
</html>