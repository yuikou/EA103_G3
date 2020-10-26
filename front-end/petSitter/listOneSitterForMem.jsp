<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
    import="com.petSitter.model.*, com.sitSrv.model.*, com.sitOrder.model.*,
    com.sitPhoto.model.*,com.member.model.*, java.util.*, java.text.DecimalFormat"%>
<%
    PetSitterVO petSitterVO = (PetSitterVO) request.getAttribute("petSitterVO");
    if (request.getAttribute("petSitterVO")==null) {
    	String sitNo = (String) request.getAttribute("sitNo");
    }
%>
<%
    SitSrvService sitSrvSrv = new SitSrvService();
    List<SitSrvVO> list = sitSrvSrv.get_OneSit_AllSrv(petSitterVO.getSitNo());
    pageContext.setAttribute("list", list);
%>
<%
    SitPhotoService sitPSrv = new SitPhotoService();
    List<SitPhotoVO> sitPhotoList = sitPSrv.getAll(petSitterVO.getSitNo());
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
    <title>會員保母頁面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
<%--     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/Petfect.css"> --%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/listOneSitter.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400&display=swap" rel="stylesheet">
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
                          Double avgStar = sitOSrv.countAvgStar(petSitterVO.getSitNo());
                          DecimalFormat df = new DecimalFormat("##.0");
                          avgStar = Double.parseDouble(df.format(avgStar));
                        %>
                            <div>
                            <div class="rateit" data-rateit-value="<%=avgStar %>" data-rateit-ispreset="true" data-rateit-readonly="true"></div>(<%=avgStar %>)    
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
							<img src="<%=request.getContextPath()%>/images/pet-bath.svg" width="50">
							</c:if>
							<c:if test="${sitSrvVO.sitSrvCode == 'Pickup'}">
							<img src="<%=request.getContextPath()%>/images/pet-google.svg" width="50">
							</c:if>
					</c:forEach>
					</div>
                    <div class="row btnRow">
                        <div class="col-sm-5">
                            <a href="#" class="btn btn-dark custom-btn">聯繫保母</a>
                        </div>
                        <div class="col-sm-2">
                            <form method="post" action="<%=request.getContextPath()%>/sitOrder/sitOrder.do">
                                <input type="hidden" name="action" value="display_for_reserve">
                                <input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
                                <input type="submit" class="btn btn-dark custom-btn" value="預約">
                            </form>
                        </div>
<!------------- 追蹤愛心 ------------->
                        <div class="col-sm-1 heart-div" style="margin-left: 55px">
                        <jsp:useBean id="petSitterSvc" class="com.petSitter.model.PetSitterService"></jsp:useBean>
        				<jsp:useBean id="sfSrv" class="com.sitFollow.model.SitFollowService"></jsp:useBean>
                        	<input type="hidden" name="memNo" value="${memNo}">
							<input type="hidden" name="sitNo" value="${petSitterVO.sitNo}">
							<div class="myClick">
								<span class="${sfSrv.getAllByMemNo(memNo).contains(petSitterVO.memNo)? 'fa fa-heart':'fa fa-heart-o'}" style="color:red; font-size: 40px;"></span>
								<div class="ring"></div>
								<div class="ring2"></div>
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
        <div class="row">
            <div class="col-lg-4 col-sm-8 section-line">
                <div class="title">托養證照</div>
                <jsp:useBean id="sitLicSvc" class="com.sitLic.model.SitLicService"/>
                <c:if test="${sitLicSvc.getSitAllLic(petSitterVO.sitNo).size()<1}">尚無證照</c:if>
                <c:forEach var="sitLicVO" items="${sitLicSvc.getSitAllLic(petSitterVO.sitNo)}">
                <c:if test="${sitLicVO.licStatus==1}">
                <div class="sitlic-div">
                	<img class="sitlicImg" src="${pageContext.request.contextPath}/front-end/img/certificate.svg">
                	<span class="sitlicSapn">${sitLicVO.licName}</span>
                </div>
                </c:if>
                </c:forEach>
                <div class="title">有提供的服務項目</div> <select id="mySrv1" class="form-control" size="1" name="sitSrvNo">
                    <c:forEach var="sitSrvVO" items="${list}">
                    	<c:if test="${sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup'}">
                        <option name="sitSrvNo" data-info="${sitSrvVO.sitSrvCode}" value="${sitSrvVO.sitSrvNo}" <c:if test="${sitSrvVO.sitSrvNo==param.sitSrvNo}">selected</c:if> >${sitSrvVO.sitSrvName}</option>
                        </c:if>
                    </c:forEach>
                </select>
                
                <jsp:include page="/front-end/sitOffDay/showOneSrvDay.jsp" />
            </div>
            <div class="col-lg-8">
                <div class="title">托養簡介</div>
                <div>
                    <%=petSitterVO.getSitInfo()%>
                </div>
                <div class="title">托養可服務時間範圍</div>
                <div>非休假日
                    <%=petSitterVO.getSrvSTime().substring(0, 2) + ":" + petSitterVO.getSrvSTime().substring(2, 4)%> 到
                    <%=petSitterVO.getSrvETime().substring(0, 2) + ":" + petSitterVO.getSrvETime().substring(2, 4)%>
                </div>
        <%
           Set<SitOrderVO> sitOVOSet = sitOSrv.getByFK_sitNo(petSitterVO.getSitNo());
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
        </div>
    </div>
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
<!--     <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
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