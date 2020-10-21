<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   String sitOrderNo = (String) request.getAttribute("sitOrderNo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單評價</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/gsap/3.3.4/gsap.min.js'></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/sitterComment.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<style>
        h2 {
            /*   color: rgba(12, 12, 12, 0.6); */
            color: #555;
            font-weight: bold;
            text-align: center;
            /*   margin: 1em auto; */
            margin: 1em auto 0 auto;
        }

        p {
            text-align: center;
            padding: 1em;
            margin: 0;
            color: #555;
        }

        #rpt_field_wrapper {
            width: 650px;
            height:350px; 
            left: 50%;
            top: 50%;
            padding: 4em;
            border-radius: 16px;
            margin: 0 auto;
            margin-bottom: 30px;
            /*   border: 1px solid black; */
        }

        .report {
            width: 100%;
        }
        div input {
            cursor: pointer;
            line-height: 135%;
        }

        div label {
            line-height: 135%;
        }


</style>
</head>
<body>
    <!-- header -->
    <jsp:include page="/front-end/header.jsp" />
    <!-- 內文開始 -->
  <div class="container">
  <div class="comment__container">
        <h1 class="comment__title">請留下你這次的經驗</h1>
        <div>
            <ul class="rating">
        <li>
            <button id="b1" name="commStar" value="5" onClick="addClass(this);">
                <div class="star"></div>
            </button>
        </li>
        <li>
            <button id="b2" name="commStar" value="4" onClick="addClass(this);">
                <div class="star"></div>
            </button>
        </li>
        <li>
            <button id="b3" name="commStar" value="3" onClick="addClass(this);">
                <div class="star"></div>
            </button>
        </li>
        <li>
            <button id="b4" name="commStar" value="2" onClick="addClass(this);">
                <div class="star"></div>
            </button>
        </li>
        <li>
            <button id="b5" name="commStar" value="1" onClick="addClass(this);">
                <div class="star"></div>
            </button>
        </li>
    </ul>
        </div>
        <div class="comment__body">
            <textarea name="sitComm" id="sitComm" class="comment__textarea" placeholder="輸入你的評價 ..." rows="6"></textarea>
        </div>
        <div class="comment__post">
            <a href="#" style="color: #FFB85F;"><i>我想檢舉</i></a>
            <button class="comment__send" id="send_btn" onClick="comm();">送出評價</button>
            <input type="hidden" id="sitOrderNo" name="sitOrderNo" value="<%=sitOrderNo%>">
        </div>
    </div>  
    <div class="overlay">
        <div id="rpt_field_wrapper">
            <h2>請寫下檢舉內容</h2>
            <div class="">
            </div>
            <div>
                <textarea class="report" rows="8"></textarea>
            </div>
            <button id="close_rpt_settings" class="Vibrant Alert">Save Settings</button>
        </div>
    </div>
  
  
  </div>

    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />

<script type="text/javascript">
        document.querySelectorAll('.rating').forEach(rating => {

            let entries = rating.querySelectorAll('li')

            entries.forEach(function(entry, index) {

                entry.addEventListener('click', e => {

                    let active = [...entries].filter((e, i) => i >= index && !e.classList.contains('active')),
                        inactive = [...entries].filter((e, i) => i < index && e.classList.contains('active'))

                    if (active.length) {
                        gsap.to(active.reverse(), {
                            onStart() {
                                this._targets.forEach(e => e.classList.add('active', 'activeColor'))
                            },
                            keyframes: [{
                                '--star-scale': 0,
                                '--star-y': 0,
                                duration: 0
                            }, {
                                '--star-scale': 1,
                                '--dot-scale': 0,
                                duration: .9,
                                ease: 'elastic.out(1, .7)',
                                stagger: .03
                            }]
                        })
                    }

                    if (inactive.length) {
                        gsap.to(inactive, {
                            onStart() {
                                this._targets.forEach(e => e.classList.remove('activeColor'))
                            },
                            onComplete() {
                                this._targets.forEach(e => e.classList.remove('active'))
                            },
                            '--star-before-r': -20,
                            '--star-before-y': -8,
                            '--star-after-r': 20,
                            '--star-after-y': 8,
                            duration: .65,
                            clearProps: true
                        })
                        gsap.to(inactive, {
                            '--star-o': 0,
                            '--star-blur': 10,
                            '--star-y': 48,
                            '--dot-scale': .8,
                            duration: .5,
                            delay: .15,
                            clearProps: true
                        })
                    }

                })

            })

        })
        
        function addClass(button){
        	console.log("addthing");
        	for(let i=1; i<=5; i++){
            	if($("#b" + i).hasClass("toggle")){
            		$("#b" + i).removeClass("toggle")
            	}
            }
        	$(button).addClass("toggle");
        }
        
        function comm(){
        			$.ajax({
                        url: "<%=request.getContextPath()%>/sitOrder/sitOrder.do",
                        type: "POST",
                        data: {
                            action: "updateStarStatus",
                            commStar: $(".toggle").val(),
                            sitComm: $("#sitComm").val(),
                            sitOrderNo: $("#sitOrderNo").val()
                        },
                        success: function(data) {
                            if (data == "success") {
                            	console.log("able to Star");
                            }
                        }
                    })
        }
            
</script>
</body>
</html>