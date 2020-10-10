<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>Nav</title>

</head>
<body>
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
</body>
</html>