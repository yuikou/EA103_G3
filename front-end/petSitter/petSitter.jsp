<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>petSitter select page</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/petSitter.css">
    <link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>
<body>
    <!-- header -->
    <jsp:include page="/front-end/header.jsp" />
    <!-- 內文開始 -->
    <div class="container">
        <div id="headInfo">完成三個步驟提供服務</div>
        <div class="row">
          <div class="col-sm-4" style="padding-top:0px;">
            <img src="<%=request.getContextPath()%>/images/1.png">
            <div class="srvTitle">免費列出您的服務</div>
            <div class="srvContent">無需註冊費即可在家中提供寵物服務，<br>例如寵物到府寄宿，<br>寵物日托，寵物計程車及遛狗。</div>
          </div>
          <div class="col-sm-4">
            <img src="<%=request.getContextPath()%>/images/2.png">
            <div class="srvTitle">決定您的工作方式</div>
            <div class="srvContent">選擇您自己的時間表、價格及其他條件。我們將一路協助您。</div>
          </div>
          <div class="col-sm-4">
            <img src="<%=request.getContextPath()%>/images/3.png">
            <div class="srvTitle">歡迎您的第一隻寵物</div>
            <div class="srvContent">可以開始接受工作了。<br>您可以在預訂前與寵物父母提出任何問題。</div>
          </div>
        </div>
        <hr>
        <div class="info">為什麼要成為寵物保母?</div>
        <div id="first">
          <div class="row">
            <div class="col-sm-5"><img src="<%=request.getContextPath()%>/images/loupe.png" style="margin-top: 100px;" width="90"></div>
            <div class="col-sm-7">
            <div id="firstInfo">快樂工作、自由安排</div>
            <div class="firstContent">與毛小孩共度歡樂時光，還能賺取額外收入</div>
            <div class="firstContent">彈性的工作方式</div>
            <div class="firstContent">自由安排接案時間、價格以及服務內容</div>
            <div class="firstContent">您的服務將被分配給附近的寵物父母</div>
            </div>
          </div>
        </div>
        <div id="second">
          <div class="row">
            <div class="col-sm-7">
            <div id="secondInfo">簡單操作、用心經營</div>
            <div class="secondContent">網站操作簡單明瞭，內建即時通訊方便與家長溝通</div>
            <div class="secondContent">可隨時上傳照片讓家長放心</div>
            <div class="secondContent">自由安排接案時間、價格以及服務內容</div>
            <div class="secondContent">公開透明的雙方評價，累積自己的信用</div>
            </div>
            <div class="col-sm-5"><img src="<%=request.getContextPath()%>/images/team.png" style="margin-top: 100px;" width="90"></div>
          </div>
        </div>
        <div id="third">
          <div class="row">
            <div class="col-sm-5"><img src="<%=request.getContextPath()%>/images/credit-card.png" style="margin-top: 100px;" width="90"></div>
            <div class="col-sm-7">
            <div id="thirdInfo">安全交易、隱私保障</div>
            <div class="thirdContent">免費註冊檔案, 保姆皆須通過審核</div>
            <div class="thirdContent">個人隱私皆保護，確認預約後才提供地址、聯絡方式</div>
            <div class="thirdContent">我們只在交易後收取原價的一定比例的費用</div>
            <div class="thirdContent">若欲申請，您必須閱讀並同意我們的條款及條件</div>
            </div>
          </div>
        </div>
        <hr>
        <div class="info" style="margin-bottom: 10px;">成為保姆要準備什麼?</div>
        <div class="prepare1">
            <div class="prepareInfo">基本資料</div>
            <div>我們會對提出申請的用戶進行資料審核, 以維護保姆服務品質與家長權益!</div>
        </div>
        <div class="prepare2">
            <div class="prepareInfo">照片</div>
            <div>在保姆頁面放上最吸引人的環境、與家中寵物互動的照片，讓家長更了解您的服務</div>
        </div>
        <div class="prepare1">
            <div class="prepareInfo">收款帳號</div>
            <div>安全的金流服務, 完成服務簡單收到費用。</div>
        </div>
        <div class="prepare2">
            <div class="prepareInfo">還有...</div>
            <div>一顆對動物滿滿的愛心!</div>
        </div>
        <input type="checkbox" id="petfect">
        <label for="petfect">我年滿 20 歲並同意<a href="#"> 隱私權政策</a> 與 <a href="#"> 服務條款</a></label>
        <div>
            <a id="add" style="margin-top: 10px;" class="btn btn-dark btn-custom disabled-link" href="<%=request.getContextPath()%>/front-end/petSitter/addPetSitter.jsp" >馬上加入PETfect 寵物保母</a>
        </div>
    </div>
    
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
    <script type="text/javascript">
        var petfect = document.getElementById("petfect");
        var add = document.getElementById("add");
        petfect.addEventListener( 'change', function() {
        	add.classList.toggle("disabled-link");
        });
    
    </script>
</body>

</html>