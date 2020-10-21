<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitOrder.model.*, java.util.*, com.sitODetail.model.*" %>
<% Set<SitOrderVO> sitOrderSet = (Set<SitOrderVO>) request.getAttribute("sitOrderSet"); %>
<% 
if(sitOrderSet==null){
    String memNo = (String)session.getAttribute("memNo");
    SitOrderService sitOrderSrv = new SitOrderService();
    sitOrderSet = sitOrderSrv.getByFK_memNo(memNo);
    pageContext.setAttribute("sitOrderSet", sitOrderSet);
}
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- 瀏覽器版本相容性 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>保母訂單</title>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/gsap/3.3.4/gsap.min.js'></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/sitOrder.css">
    <link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">

<body>
    <!-- header -->
    <jsp:include page="/front-end/header.jsp" />
    <!-- 內文開始 -->
    <%
      SitODetailVO sitODetailVO = (SitODetailVO) request.getAttribute("sitODetailVO");
      if (sitODetailVO != null) {
    %>
    <jsp:include page="/front-end/sitODetail/sitODetailPage.jsp" />
    <%} %>
    <div class="container">
        <%-- 錯誤表列 --%>
        <c:if test="${not empty errorMsgs}">
            <font style="color:red">請修正以下錯誤:</font>
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li style="color:red">${message}</li>
                </c:forEach>
            </ul>
        </c:if>
       <h3 class="display-5"><a href="<%=request.getContextPath()%>/front-end/sitterFront.jsp"><img src="<%=request.getContextPath()%>/images/left-arrow.png" width="50"></a> 進行中訂單</h3>
          <table class="tableBox">
            <thead>
                <tr class="thBox">
                    <th>訂單編號</th>
                    <th>保母編號</th>
                    <th>服務開始</th>
                    <th>服務結束</th>
                    <th>總金額</th>
                    <th>訂單狀態</th>
                    <th>退費</th>
                    <th>補償金額</th>
                    <th>評價分數</th>
                    <th>評價內容</th>
                    <th>接送起點</th>
                    <th>接送終點</th>
                    <th>訂單明細</th>
                    <th>我要評價</th>
                </tr>
            </thead>
            <tbody>
                <jsp:useBean id="petSitSrv" scope="page" class="com.petSitter.model.PetSitterService" />
                <jsp:useBean id="memSrv" scope="page" class="com.member.model.MemService" />
                <c:forEach var="sitOrderVO" items="${sitOrderSet}" varStatus="counter">
                    <c:if test="${sitOrderVO.orderStatus!=3}">
                    <tr>
                        <td>${sitOrderVO.sitOrderNo}</td>
                        <td>${sitOrderVO.sitNo}<br>
                            <c:forEach var="petSitVO" items="${petSitSrv.all}">
                                <c:if test="${sitOrderVO.sitNo == petSitVO.sitNo}">
                                   <c:forEach var="memVO" items="${memSrv.all}">
                                       <c:if test="${petSitVO.memNo == memVO.memNo}">
                                           ${memVO.memName}
                                       </c:if>
                                   </c:forEach> 
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${sitOrderVO.sitSDate}</td>
                        <td>${sitOrderVO.sitEDate}</td>
                        <td>$ ${sitOrderVO.totalPrice}</td>
                        <td>
                            <c:if test="${sitOrderVO.orderStatus == 0}">已下單<br>已付款</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 1}">保母<br>已確認</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 2}">服務結束<br>待評價</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 3}">此服務<br>已結案</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 4}">預約取消<br>待退款</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 5}">申訴檢舉<br>待處理</c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.refund == 0 || sitOrderVO.refund == null}">-</c:when>
                                <c:otherwise>$ ${sitOrderVO.refund}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.coupon == 0 || sitOrderVO.coupon == null}">-</c:when>
                                <c:otherwise>$ ${sitOrderVO.coupon}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.commStar == 0 || sitOrderVO.commStar == null}">尚未評價</c:when>
                                <c:otherwise>${sitOrderVO.commStar}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="#sitComm${counter.index}">內容</a>
                            <div class="lightbox" id="sitComm${counter.index}">
                                <figure>
                                    <a href="#" class="close"></a>
                                    <figcaption>
                                        <c:forEach var="memVO" items="${memSrv.all}">
                                            <div class="row">
                                                <c:if test="${memVO.memNo==sitOrderVO.memNo}">
                                                    <div class="col-sm-3">
                                                        <div>${memVO.memPhoto}</div>
                                                        <img src="<%=request.getContextPath()%>/images/user.png" width="60">
                                                    </div>
                                                </c:if>
                                                <c:if test="${memVO.memNo==sitOrderVO.memNo}">
                                                    <div class="col-sm-8">
                                                        <div style="font-size: 15px; font-weight: bold;">${memVO.memName}</div>
                                                        <div>
                                                            <c:choose>
                                                                <c:when test="${sitOrderVO.sitComm == null}">尚未評價</c:when>
                                                                <c:otherwise>${sitOrderVO.sitComm}</c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </figcaption>
                                </figure>
                            </div>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.pickupFrom == null}">-</c:when>
                                <c:otherwise>${sitOrderVO.pickupFrom}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.pickupTo == null}">-</c:when>
                                <c:otherwise>${sitOrderVO.pickupTo}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form method="post" action="<%=request.getContextPath()%>/sitODetail/sitODetail.do">
                                <input type="submit" class="btn btn-dark" value="查看">
                                <input type="hidden" name="sitOrderNo" value="${sitOrderVO.sitOrderNo}">
                                <input type="hidden" name="action" value="select_orderDetail_forMem">
                            </form>
                        </td>
                        <td>
                            <c:if test="${sitOrderVO.orderStatus==2}">
<%--                               <form method="post" action="<%=request.getContextPath()%>/sitOrder/sitOrder.do"> --%>

<!--                                 <input type="submit" class="btn btn-dark" value="評價"> -->
                                <button class="btn btn-dark" name="sitOrderNo" value="${sitOrderVO.sitOrderNo}" onClick="getSitOrder(this); openResTime();" >評價</button>
<%--                                 <input type="hidden" name="sitOrderNo" value="${sitOrderVO.sitOrderNo}"> --%>
<!--                                 <input type="hidden" name="action" value="display_comment"> -->
<!--                               </form> -->
                            </c:if>
                            <c:if test="${sitOrderVO.orderStatus!=2}">
                                <input type="submit" class="btn btn-dark" value="評價" disabled>
                            </c:if>
<!--                             <input type="submit" class="btn btn-dark" value="評價" onclick="openResTime();"> -->
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
       <h3 class="display-5">歷史訂單</h3>
        <table class="tableBox">
            <thead>
                <tr class="thBox">
                    <th>訂單編號</th>
                    <th>保母編號</th>
                    <th>服務開始</th>
                    <th>服務結束</th>
                    <th>總金額</th>
                    <th>訂單狀態</th>
                    <th>退費</th>
                    <th>補償金額</th>
                    <th>評價分數</th>
                    <th>評價內容</th>
                    <th>接送起點</th>
                    <th>接送終點</th>
                    <th>訂單明細</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sitOrderVO" items="${sitOrderSet}" varStatus="counter">
                    <c:if test="${sitOrderVO.orderStatus==3}">
                    <tr>
                        <td>${sitOrderVO.sitOrderNo}</td>
                        <td>${sitOrderVO.sitNo}<br>
                            <c:forEach var="petSitVO" items="${petSitSrv.all}">
                                <c:if test="${sitOrderVO.sitNo == petSitVO.sitNo}">
                                   <c:forEach var="memVO" items="${memSrv.all}">
                                       <c:if test="${petSitVO.memNo == memVO.memNo}">
                                           ${memVO.memName}
                                       </c:if>
                                   </c:forEach> 
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${sitOrderVO.sitSDate}</td>
                        <td>${sitOrderVO.sitEDate}</td>
                        <td>$ ${sitOrderVO.totalPrice}</td>
                        <td>
                            <c:if test="${sitOrderVO.orderStatus == 0}">已下單<br>已付款</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 1}">保母<br>已確認</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 2}">服務結束<br>待評價</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 3}">此服務<br>已結案</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 4}">預約取消<br>待退款</c:if>
                            <c:if test="${sitOrderVO.orderStatus == 5}">申訴檢舉<br>待處理</c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.refund == 0 || sitOrderVO.refund == null}">-</c:when>
                                <c:otherwise>$ ${sitOrderVO.refund}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.coupon == 0 || sitOrderVO.coupon == null}">-</c:when>
                                <c:otherwise>$ ${sitOrderVO.coupon}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.commStar == 0 || sitOrderVO.commStar == null}">尚未評價</c:when>
                                <c:otherwise>${sitOrderVO.commStar}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="#sitComm${counter.index}">內容</a>
                            <div class="lightbox" id="sitComm${counter.index}">
                                <figure>
                                    <a href="#" class="close"></a>
                                    <figcaption>
                                        <c:forEach var="memVO" items="${memSrv.all}">
                                            <div class="row">
                                                <c:if test="${memVO.memNo==sitOrderVO.memNo}">
                                                    <div class="col-sm-3">
                                                        <div>${memVO.memPhoto}</div>
                                                        <img src="<%=request.getContextPath()%>/images/user.png" width="60">
                                                    </div>
                                                </c:if>
                                                <c:if test="${memVO.memNo==sitOrderVO.memNo}">
                                                    <div class="col-sm-8">
                                                        <div style="font-size: 15px; font-weight: bold;">${memVO.memName}</div>
                                                        <div>
                                                            <c:choose>
                                                                <c:when test="${sitOrderVO.sitComm == null}">尚未評價</c:when>
                                                                <c:otherwise>${sitOrderVO.sitComm}</c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </figcaption>
                                </figure>
                            </div>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.pickupFrom == null}">-</c:when>
                                <c:otherwise>${sitOrderVO.pickupFrom}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.pickupTo == null}">-</c:when>
                                <c:otherwise>${sitOrderVO.pickupTo}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form method="post" action="<%=request.getContextPath()%>/sitODetail/sitODetail.do">
                                <input type="submit" class="btn btn-dark" value="查看">
                                <input type="hidden" name="sitOrderNo" value="${sitOrderVO.sitOrderNo}">
                                <input type="hidden" name="action" value="select_orderDetail_forMem">
                            </form>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>        
    </div>
    <div class="empty"></div>
    
    <div id="resTime-modal" >
	    <button title="Close" type="button" class="closeBtn" onclick="closeResTime()">x</button>
	        
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
            <button class="comment__send" onClick="comm();" id="sitOrderNo">送出評價</button>
<!--             <input type="hidden"  name="sitOrderNo" value=""> -->
        </div>
    </div>
	    
	</div>
    
    
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    
    function openResTime(){
    	document.getElementById("resTime-modal").style.display = "block";
    } 
    function closeResTime() {
		document.getElementById("resTime-modal").style.display = "none";
	}
    
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
                        	swal({
                      		  title: "謝謝你!",
                      		  text: "成功完成評價",
                      		  icon: "success",
                      		  buttons: true,
                      		  dangerMode: false,
                      		})
                      		.then((willDelete) => {
                      		  if (willDelete) {
                      			  document.location.href="<%=request.getContextPath()%>/front-end/sitterFront.jsp";
                      		  }
                      		});
                        }
                    }
                })
    }
    
    
    function getSitOrder(button) {
    	console.log($(button).val());
        var xhr = new XMLHttpRequest();
        //設定好回呼函數   
        xhr.onload = function() {
            if (xhr.status == 200) {
                // document.getElementById("showPanel").innerHTML = xhr.responseText;1
                showSitOrder(xhr.responseText);
            } else {
                alert(xhr.status);
            } //xhr.status == 200
        }; //onload 

        //建立好Get連接
        var url = "<%=request.getContextPath()%>/sitOrder/sitOrder.do?action=comment&sitOrderNo=" + $(button).val();
        xhr.open("Get", url, true);
        //送出請求 
        xhr.send(null);
        
    }
    
    function showSitOrder(jsonStr) {
    	console.log("manage to show");
        //剖析json字串,將其轉成jsob物件
        let value;
        console.log("json=" + jsonStr);
        let sitOrder = JSON.parse(jsonStr);
        console.log("String=" + sitOrder);
        

        value = sitOrder.sitOrderNo;
        console.log(value);
        $("#sitOrderNo").val(sitOrder.sitOrderNo);
    }
    
    </script>
</body>

</html>