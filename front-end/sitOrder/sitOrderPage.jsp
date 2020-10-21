<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitOrder.model.*, java.util.*, com.sitODetail.model.*" %>
<% Set<SitOrderVO> sitOrderSet = (Set<SitOrderVO>) request.getAttribute("sitOrderSet"); %>
<% 
if(sitOrderSet==null){
    String sessionSitNo = (String)session.getAttribute("sessionSitNo");
    SitOrderService sitOrderSrv = new SitOrderService();
    sitOrderSet = sitOrderSrv.getByFK_sitNo(sessionSitNo);
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/sitOrder.css">
    <link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

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
        <h3 class="display-5"><a href="<%=request.getContextPath()%>/front-end/petSitter/listOneSitter.jsp"><img src="<%=request.getContextPath()%>/images/left-arrow.png" width="50"></a> 進行中訂單</h3>
        <table class="tableBox">
            <thead>
                <tr class="thBox">
                    <th>訂單編號</th>
                    <th>會員編號</th>
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
                    <th>確認訂單</th>
                </tr>
            </thead>
            <tbody>
                <jsp:useBean id="memSrv" scope="page" class="com.member.model.MemService" />
                <c:forEach var="sitOrderVO" items="${sitOrderSet}" varStatus="counter">
                    <c:if test="${sitOrderVO.orderStatus!=3}">
                    <tr>
                        <td>${sitOrderVO.sitOrderNo}</td>
                        <td>${sitOrderVO.memNo}<br>
                            <c:forEach var="memVO" items="${memSrv.all}">
                                <c:if test="${sitOrderVO.memNo == memVO.memNo}">${memVO.memName}</c:if>
                            </c:forEach>
                        </td>
                        <td>${sitOrderVO.sitSDate}</td>
                        <td>${sitOrderVO.sitEDate}</td>
                        <td>$ ${sitOrderVO.totalPrice}</td>
                        <td id="status${counter.index}">
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
                                <input type="hidden" name="action" value="select_orderDetail">
                            </form>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.orderStatus == 0}">
                                    <button id="confirmBtn${counter.index}" class="btn btn-dark" name="sitOrderNo${counter.index}" value="${sitOrderVO.sitOrderNo}">確認</button>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-dark" value="已確認" disabled>
                                </c:otherwise>
                            </c:choose>
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
                    <th>會員編號</th>
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
                    <th>確認訂單</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sitOrderVO" items="${sitOrderSet}" varStatus="counter">
                    <c:if test="${sitOrderVO.orderStatus==3}">
                    <tr>
                        <td>${sitOrderVO.sitOrderNo}</td>
                        <td>${sitOrderVO.memNo}<br>
                            <c:forEach var="memVO" items="${memSrv.all}">
                                <c:if test="${sitOrderVO.memNo == memVO.memNo}">${memVO.memName}</c:if>
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
                                <input type="hidden" name="action" value="select_orderDetail">
                            </form>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sitOrderVO.orderStatus == 0}">
                                    <button id="confirmBtn${counter.index}" class="btn btn-dark" name="sitOrderNo${counter.index}" value="${sitOrderVO.sitOrderNo}">確認</button>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" class="btn btn-dark" value="已確認" disabled>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>        
    </div>

    <div class="empty"></div>
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {

            for (let i = 0; i <= `${sitOrderSet.size()}`; i++) {
                $("#confirmBtn" + i).click(function() {

                    $.ajax({
                        url: "<%=request.getContextPath()%>/sitOrder/sitOrder.do",
                        type: "POST",
                        data: {
                            action: "confirm_order",
                            sitOrderNo: $(this).val()
                        },
                        success: function(data) {
                            if (data == "confirm") {
                            	swal("恭喜!", "確認了一筆訂單!", "success");
                                $("#confirmBtn" + i).prop("disabled", true).text("已確認");
                                $("#status" + i).html("保母<br>已確認");
                            }
                        }
                    })
                })
            }

        });
    </script>
</body>

</html>