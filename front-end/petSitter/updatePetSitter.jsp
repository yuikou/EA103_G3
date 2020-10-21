<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.petSitter.model.*"%>
<%
    PetSitterVO petSitterVO = (PetSitterVO) request.getAttribute("petSitterVO");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- 瀏覽器版本相容性 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>保母資料更新 - updatePetSitter.jsp</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/animate.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/animsition.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/util.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/updatePetSitter.css">
    <script src="<%=request.getContextPath()%>/js/js-ching/ckeditor/ckeditor.js"></script>
    <link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>
    <!-- header -->
    <jsp:include page="/front-end/header.jsp" />
    <!-- 內文開始 -->
    <div class="limiter">
        <div class="container-login100 cover">
            <div class="wrap-login100 mybody">
                <form method="post" action="<%=request.getContextPath()%>/petSitter/petSitter.do" name="form1" class="login100-form validate-form p-l-55 p-r-55 p-t-70">
                    <span class="login100-form-title">Update PetSitter Info</span>
                    <div class="updateBox">
                        <c:if test="${not empty errorMsgs}">
                            <font style="color: red">請修正以下錯誤:</font>
                            <ul>
                                <c:forEach var="message" items="${errorMsgs}">
                                    <li style="color: red">${message}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <div class="info txt1 myP">銀行代碼</div>
                        <div class="wrap-input100 validate-input" data-validate="請輸入銀行代碼">
                            <input class="input100" type="text" name="bankCode" size="2" value="<%=petSitterVO.getBankCode()%>" />
                            <span class="focus-input100"></span>
                        </div>
                        <div class="info txt1 myP">匯款帳號</div>
                        <div class="wrap-input100 validate-input" data-validate="請輸入匯款帳號">
                            <input class="input100" type="text" name="bankAcc" size="20" value="<%=petSitterVO.getBankAcc()%>" />
                            <span class="focus-input100"></span>
                        </div>
                        <div class="info txt1 myP">可服務開始時間</div>
                        <div class="wrap-input100 validate-input" data-validate="請可服務開始時間">
                            <select class="select_input input100" size="1" name="srvSTime">
                                <option value="${petSitterVO.srvSTime}">
                                    <%=petSitterVO.getSrvSTime().substring(0, 2) + ":" + petSitterVO.getSrvSTime().substring(2, 4)%>
                                </option>
                                <option value="0530">05:30</option>
                                <option value="0600">06:00</option>
                                <option value="0630">06:30</option>
                                <option value="0700">07:00</option>
                                <option value="0730">07:30</option>
                                <option value="0800">08:00</option>
                                <option value="0830">08:30</option>
                                <option value="0900">09:00</option>
                                <option value="0930">09:30</option>
                                <option value="1000">10:00</option>
                                <option value="1030">10:30</option>
                                <option value="1100">11:00</option>
                                <option value="1130">11:30</option>
                                <option value="1200">12:00</option>
                            </select><span class="focus-input100"></span>
                        </div>
                        <div class="info txt1 myP">可服務結束時間</div>
                        <div class="wrap-input100 validate-input" data-validate="請可服務結束時間">
                            <select class="select_input input100" size="1" name="srvETime">
                                <option value="${petSitterVO.srvETime}">
                                    <%=petSitterVO.getSrvETime().substring(0, 2) + ":" + petSitterVO.getSrvETime().substring(2, 4)%>
                                </option>
                                <option value="1530">15:30</option>
                                <option value="1600">16:00</option>
                                <option value="1630">16:30</option>
                                <option value="1700">17:00</option>
                                <option value="1730">17:30</option>
                                <option value="1800">18:00</option>
                                <option value="1830">18:30</option>
                                <option value="1900">19:00</option>
                                <option value="1930">19:30</option>
                                <option value="2000">20:00</option>
                                <option value="2030">20:30</option>
                                <option value="2100">21:00</option>
                                <option value="2130">21:30</option>
                                <option value="2200">22:00</option>
                            </select>
                            <span class="focus-input100"></span>
                        </div>
                        <div class="info txt1 myP">自我介紹-托養簡介</div>
                        <div class="wrap-input100 validate-input m-b-16" data-validate="請輸入托養簡介">
                            <textarea name="sitInfo" id="content" rows="10" cols="80"><%=petSitterVO.getSitInfo()%></textarea>
                            <span class="focus-input100"></span>
                        </div>
                        <div class="container-login100-form-btn p-b-40">
                            <input type="hidden" name="action" value="update">
                            <input class="login100-form-btn input100" id="send" type="submit" value="送出修改">
                        </div>
                    </div>
                    <input type="hidden" name="sitNo" value="<%=petSitterVO.getSitNo()%>">
                    <input type="hidden" name="memNo" value="<%=petSitterVO.getMemNo()%>">
                    <input type="hidden" name="sitAccStatus" value="<%=petSitterVO.getSitAccStatus()%>">
                    <input type="hidden" name="totalComm" value="<%=petSitterVO.getTotalComm()%>">
                    <input type="hidden" name="totalCus" value="<%=petSitterVO.getTotalCus()%>">
                    <input type="hidden" name="repeatCus" value="<%=petSitterVO.getRepeatCus()%>">
                </form>
            </div>
        </div>
    </div>
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
    <script src="<%=request.getContextPath()%>/js/js-ching/jquery-3.2.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/js-ching/animsition.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/js-ching/popper.js"></script>
    <script src="<%=request.getContextPath()%>/js/js-ching/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/js-ching/main.js"></script>
    <script>
        CKEDITOR.replace('sitInfo', {});
    </script>
</body>

</html>