<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
    import="com.petSitter.model.*, com.sitSrv.model.*, com.sitOrder.model.*,
    com.sitPhoto.model.*,com.member.model.*, java.util.*, java.text.DecimalFormat"%>
<%
    PetSitterVO petSitterVO = (PetSitterVO) request.getAttribute("petSitterVO");
%>
<%
    String sessionSitNo = (String) session.getAttribute("sessionSitNo");
    if(petSitterVO==null){
        PetSitterService petSitSrv = new PetSitterService();
        petSitterVO = petSitSrv.getByPK(sessionSitNo);
        pageContext.setAttribute("petSitterVO", petSitterVO);
    }
%>
<%
    SitSrvService sitSrvSrv = new SitSrvService();
    List<SitSrvVO> list = sitSrvSrv.get_OneSit_AllSrv(sessionSitNo);
    pageContext.setAttribute("list", list);
%>
<%
    SitPhotoService sitPSrv = new SitPhotoService();
    List<SitPhotoVO> sitPhotoList = sitPSrv.getAll(sessionSitNo);
    pageContext.setAttribute("sitPhotoList", sitPhotoList);
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- 瀏覽器版本相容性 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>保母頁面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
<%--     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/Petfect.css"> --%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/listOneSitter.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/rateit.css"  />
    <link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/css-ching/lightslider.css" />
</head>

<body>
    <!-- header -->
    <jsp:include page="/front-end/header.jsp" />
    <!-- 內文開始 -->
    <div class="container">
        <%-- 錯誤表列 --%>
        <c:if test="${not empty errorMsgs}">
            <font style="color: red">請修正以下錯誤:</font>
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li style="color: red">${message}</li>
                </c:forEach>
            </ul>
        </c:if>
        
        <jsp:useBean id="memSrv" scope="page" class="com.member.model.MemService" />
        <div>
            <div class="row head-info">
                <div class="col-lg-2 back-btn">
                  <a href="<%=request.getContextPath()%>/front-end/sitterFront.jsp"><img src="<%=request.getContextPath()%>/images/left-arrow.png" width="55"></a>
                </div>
                <div class="col-lg-3 left-info">
                    <c:forEach var="memVO" items="${memSrv.all}">
                        <c:if test="${petSitterVO.memNo==memVO.memNo}">
                            <h3>${memVO.memName}</h3>
                        </c:if>
                    </c:forEach>
                    <hr>
                    <div class="row">
                        <div class="col-5"><img id="userPhoto" src="<%=request.getContextPath()%>/images/pet.jpg"></div>
                        <div class="col-7">
                        <%
                          SitOrderService sitOSrv = new SitOrderService();
                          Double avgStar = sitOSrv.countAvgStar(sessionSitNo);
                          DecimalFormat df = new DecimalFormat("##.0");
                          avgStar = Double.parseDouble(df.format(avgStar));
                        %>
                            <div>
                               <div class="rateit" data-rateit-value="<%=avgStar%>" data-rateit-ispreset="true" data-rateit-readonly="true"></div>(<%=avgStar%>)
                            </div>
                            <div><img src="<%=request.getContextPath()%>/images/repeat.png" width="20"> <b>重複預訂</b>
                                <%=petSitterVO.getRepeatCus()%> 人</div>
                        </div>
                    </div>
                    <div class="srvPhoto">
                    <div><b>提供服務</b></div>
                    <c:forEach var="sitSrvVO" items="${list}">
							<c:if test="${sitSrvVO.sitSrvCode == 'Boarding'}">
							<img src="<%=request.getContextPath()%>/images/pet-house.svg" width="40">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'DayCare'}">
							<img src="<%=request.getContextPath()%>/images/pet-milk.svg" width="40">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'DropIn'}">
							<img src="<%=request.getContextPath()%>/images/pet-food.svg" width="40">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'DogWalking'}">
							<img src="<%=request.getContextPath()%>/images/walking-the-dog.svg" width="40">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'PetTaxi'}">
							<img src="<%=request.getContextPath()%>/images/pet-taxi.svg" width="40">
							</c:if>
					</c:forEach>
					<c:forEach var="sitSrvVO" items="${list}">
					        <c:if test="${sitSrvVO.sitSrvCode == 'Bathing'}">
							<img src="<%=request.getContextPath()%>/images/pet-bath.svg" width="40">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'Pickup'}">
							<img src="<%=request.getContextPath()%>/images/pet-google.svg" width="40">
							</c:if>
					</c:forEach>
					</div>
					<div class="btnRow">
                    <div class="row">
                        <div class="col-5" style="margin-bottom: 10px; margin-right: 10px;">
                            <form method="post" action="<%=request.getContextPath()%>/sitOrder/sitOrder.do">
                                <input type="hidden" name="action" value="select_order">
                                <input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
                                <input type="submit" class="btn btn-dark custom-btn" value="查看訂單">
                            </form>
                        </div>
                        <div class="col-5" style="margin-bottom: 10px; margin-left: 10px;">
                            <form method="post" action="<%=request.getContextPath()%>/petSitter/petSitter.do">
                                <input type="hidden" name="action" value="getSitter_forUpdate">
                                <input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
                                <input type="submit" class="btn btn-dark custom-btn" value="編輯資訊">
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-5" style="margin-bottom: 10px; margin-right: 10px;">
                            <form method="post" action="<%=request.getContextPath()%>/sitPhoto/sitPhoto.do">
                                <input type="hidden" name="action" value="display_sitPhoto">
                                <input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
                                <input type="submit" class="btn btn-dark custom-btn" value="上傳照片">
                            </form>
                        </div>
                        <div class="col-5" style="margin-bottom: 10px; margin-left: 10px;">
                            <a class="btn btn-dark custom-btn" href="<%=request.getContextPath()%>/sitLic/sitLic.do?action=getAll">管理證照</a>
                        </div>
                    </div>
                    </div>
                </div>
                
                <div class="col-lg-7 right-info">
                    <div class="demo">
                        <div class="item">
                            <div class="clearfix">
                                <ul id="image-gallery" class="gallery list-unstyled cS-hidden">
                                    <c:forEach var="sitPhotoVO" items="${sitPhotoList}">
                                        <li data-thumb="<%=request.getContextPath()%>/PicReader.do?action=sitPhoto&sitPNo=${sitPhotoVO.sitPNo}">
                                            <img src="<%=request.getContextPath()%>/PicReader.do?action=sitPhoto&sitPNo=${sitPhotoVO.sitPNo}" width="400" />
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <hr>
            <div>
                
                <div class="title">托養簡介</div>
                <div>
                    <%=petSitterVO.getSitInfo()%>
                </div>
                <div class="title">托養可服務時間</div>
                <div>
                    <%=petSitterVO.getSrvSTime().substring(0, 2) + ":" + petSitterVO.getSrvSTime().substring(2, 4)%> 到
                    <%=petSitterVO.getSrvETime().substring(0, 2) + ":" + petSitterVO.getSrvETime().substring(2, 4)%>
                </div>
<!--                 <div class="title">銀行代碼及匯款帳號</div> -->
<!--                 <div>( -->
<%--                     <%=petSitterVO.getBankCode()%>) --%>
<%--                     <%=petSitterVO.getBankAcc().substring(0, 4) + "-" + petSitterVO.getBankAcc().substring(4, 8) + "-" --%>
<%--                     + petSitterVO.getBankAcc().substring(8, 12) + "-" + petSitterVO.getBankAcc().substring(12, 16)%> --%>
<!--                 </div>  -->
         <%
           Set<SitOrderVO> sitOVOSet = sitOSrv.getByFK_sitNo(sessionSitNo);
           pageContext.setAttribute("sitOVOSet", sitOVOSet);
         %>
                <div class="title">托養評價</div>
                <c:forEach var="sitOrderVO" items="${sitOVOSet}">
                <c:if test="${sitOrderVO.sitComm!=null}">
                <c:forEach var="memVO" items="${memSrv.all}">
                
                  <div class="row">
                    <c:if test="${memVO.memNo==sitOrderVO.memNo && sitOrderVO.sitNo==petSitterVO.getSitNo()}">
                      <div class="col-sm-1"></div>
                      <div class="col-sm-2">
                          <div>${memVO.memPhoto}</div>
                          <img src="<%=request.getContextPath()%>/images/user.png" width="60">
                      </div>
                    </c:if>
                    <c:if test="${memVO.memNo==sitOrderVO.memNo && sitOrderVO.sitNo==petSitterVO.getSitNo()}">
                      <div class="col-sm-8">
                      <div style="font-size: 20px; font-weight: bold;">${memVO.memName}
                      <c:if test="${sitOrderVO.commStar==5}">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      </c:if>
                      <c:if test="${sitOrderVO.commStar==4}">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      </c:if>
                      <c:if test="${sitOrderVO.commStar==3}">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      </c:if>
                      <c:if test="${sitOrderVO.commStar==2}">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      </c:if>
                      <c:if test="${sitOrderVO.commStar==1}">
                      <img src="<%=request.getContextPath()%>/images/starred.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      <img src="<%=request.getContextPath()%>/images/star.png" width="20">
                      </c:if>
                      
                      </div>
                      <div>${sitOrderVO.sitComm}</div>
                      </div>
                    </c:if>
                  </div>
                  
                </c:forEach>
                <hr>
                </c:if>
                </c:forEach>               
            </div>
        <div class="row">
        	<jsp:include page="/front-end/sitSrv/showOneSitAllSrv.jsp" />
        </div>
    </div>
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.rateit/1.1.3/jquery.rateit.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/js-ching/lightslider.js"></script>
    <script>
        $(document).ready(function() {
            $("#content-slider").lightSlider({
                loop: true,
                keyPress: true
            });
            $('#image-gallery').lightSlider({
                gallery: true,
                item: 1,
                thumbItem: 9,
                slideMargin: 0,
                speed: 500,
                auto: true,
                loop: true,
                onSliderLoad: function() {
                    $('#image-gallery').removeClass('cS-hidden');
                }
            });
        });
    </script>
</body>

</html>