<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitOrder.model.*, com.member.model.*, com.sitSrv.model.*, com.pet.model.*, java.util.*"%>
<% String memNo = (String) session.getAttribute("memNo"); %>
<%
    SitOrderVO sitOrderVO = (SitOrderVO) request.getAttribute("sitOrderVO");
    String city = (String) request.getAttribute("city");
    String district = (String) request.getAttribute("district");
    String address = (String) request.getAttribute("address");
    String sitNo = (String) request.getAttribute("sitNo");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- 瀏覽器版本相容性 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>預約保母</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/datetimepicker/jquery.datetimepicker.css" />
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="<%=request.getContextPath()%>/css/css-ching/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/css/css-ching/datetimepicker/jquery.datetimepicker.full.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.1/dist/tw-city-selector.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/Petfect.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css-ching/reservePetSitter.css">
    <link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>
    <!-- header -->
    <jsp:include page="/front-end/header.jsp" />
    <!-- 內文開始 -->
    <div class="container head">
        <%-- 錯誤列表 --%>
        <c:if test="${not empty errorMsgs}">
            <font style="color: red">請修正以下錯誤:</font>
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li style="color: red">${message}</li>
                </c:forEach>
            </ul>
        </c:if>
        <form method="post" action="<%=request.getContextPath()%>/sitOrder/sitOrder.do">
            <table class="table">
                <tr class="row1">
                    <th scope="row"><img src="<%=request.getContextPath()%>/images/calendar.png" width="40"> 請選擇需托養日期:</th>
                    <td>
                        <div class="row col-m-8">
                            <div class="col-lg-6">
                                開始日期<input type="text" id="from" class="form-control" name="sitSDate" value="${sitOrderVO.sitSDate}">
                            </div>
                            <div class="col-lg-6">
                                結束日期<input type="text" id="to" class="form-control" name="sitEDate" value="${sitOrderVO.sitEDate}">
                            </div>
                        </div>
                    </td>
                </tr>
                <%
           PetService petSrv = new PetService();
           List<PetVO> petList = petSrv.getAllPet(memNo);
           pageContext.setAttribute("petList", petList);
         %>
                <tr class="row2">
                    <th scope="row"><img src="<%=request.getContextPath()%>/images/treats.png" width="40"> 選擇你的毛小孩:</th>
                    <td>
                        <c:forEach var="petVO" items="${petList}" varStatus="counter">
                            <div>
                                <input type="radio" name="petNo" value="${petVO.petNo}" id="checked${counter.count}" <%=(sitOrderVO!=null) ? "checked" : "" %>required>
                                <label for="checked${counter.count}">${petVO.petName} ${petVO.petPhoto}</label>
                            </div>
                        </c:forEach>
                    </td>
                </tr>
                <%
           SitSrvService sitSrvSrv = new SitSrvService();
           List<SitSrvVO> list = sitSrvSrv.get_OneSit_AllSrv(sitNo);
           pageContext.setAttribute("list", list);
        %>
                <tr class="row1">
                    <th scope="row"><img src="<%=request.getContextPath()%>/images/pet.png" width="40"> 選擇服務:</th>
                    <td>
                        <div class="form-group">
                            <select class="form-control" size="1" name="sitSrvNo" id="mainSrv" onchange="toggleSvc();">
                                <option value="">選擇服務</option>
                                <c:forEach var="sitSrvVO" items="${list}">
<!-- 這裡我改掉了 -->					
									<c:if test="${sitSrvVO.sitSrvCode != 'Bathing' && sitSrvVO.sitSrvCode != 'Pickup'}">
                                    <option value="${sitSrvVO.sitSrvNo}" data-type="${sitSrvVO.sitSrvCode}" data-bathing="${sitSrvVO.addBathing}" data-pickup="${sitSrvVO.addPickup}" >${sitSrvVO.sitSrvName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <c:forEach var="sitSrvVO" items="${list}">
                            <c:if test="${sitSrvVO.sitSrvCode == 'Boarding'}">
                                <table name="mainSrvHide" id="nightSrvPrice" style="display:none;" class="table table-striped" style="width:500px;margin:0 auto;">
                                    <thead>
                                        <tr>
                                            <th style="width:28%" class="text-center">服務類型</th>
                                            <th style="width:18%" class="text-center">${sitSrvVO.acpPetTyp}</th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-center radio-group">
                                        <tr>
                                            <td style="width:28%">住宿/每晚</td>
                                            <td style="width:18%">${sitSrvVO.srvFee}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="sitSrvVO" items="${list}">
                            <c:if test="${sitSrvVO.sitSrvCode == 'DayCare'}">
                                <table name="mainSrvHide" id="daySrvPrice" style="display:none;" class="table table-striped" style="width:500px;margin:0 auto;">
                                    <thead>
                                        <tr>
                                            <th style="width:28%" class="text-center">服務類型</th>
                                            <th style="width:18%" class="text-center">${sitSrvVO.acpPetTyp}</th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-center radio-group">
                                        <tr>
                                            <td style="width:28%">日托/每天</td>
                                            <td style="width:18%">${sitSrvVO.srvFee}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="sitSrvVO" items="${list}">
                            <c:if test="${sitSrvVO.sitSrvCode == 'DropIn'}">
                                <table name="mainSrvHide" id="homeSrvPrice" style="display:none;" class="table table-striped" style="width:500px;margin:0 auto;">
                                    <thead>
                                        <tr>
                                            <th style="width:28%" class="text-center">服務類型</th>
                                            <th style="width:18%" class="text-center">${sitSrvVO.acpPetTyp}</th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-center radio-group">
                                        <tr>
                                            <td style="width:28%">到府/每次</td>
                                            <td style="width:18%">${sitSrvVO.srvFee}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="sitSrvVO" items="${list}">
                            <c:if test="${sitSrvVO.sitSrvCode == 'DogWalking'}">
                                <table name="mainSrvHide" id="walkSrvPrice" style="display:none;" class="table table-striped" style="width:500px;margin:0 auto;">
                                    <thead>
                                        <tr>
                                            <th style="width:28%" class="text-center">服務類型</th>
                                            <th style="width:18%" class="text-center">${sitSrvVO.acpPetTyp}</th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-center radio-group">
                                        <tr>
                                            <td style="width:28%">毛小孩放風/每趟</td>
                                            <td style="width:18%">${sitSrvVO.srvFee}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="sitSrvVO" items="${list}">
                            <c:if test="${sitSrvVO.sitSrvCode == 'PetTaxi'}">
                                <table name="mainSrvHide" id="pickupPrice" style="display:none;" class="table table-striped" style="width:500px;margin:0 auto;">
                                    <thead>
                                        <tr>
                                            <th style="width:28%" class="text-center">服務類型</th>
                                            <th style="width:18%" class="text-center">${sitSrvVO.acpPetTyp}</th>
                                        </tr>
                                    </thead>
                                    <tbody class="text-center radio-group">
                                        <tr>
                                            <td style="width:28%">寵物計程車/每公里</td>
                                            <td style="width:18%">${sitSrvVO.srvFee}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                
                <tr class="row1" id="extraSrv" name="mainSrvHide" style="display: none;">
                    <th scope="row"><img src="<%=request.getContextPath()%>/images/veterinary.png" width="40"> 附加服務:</th>
    	            
    	            <c:forEach var="sitSrvVO" items="${list}">
    	            <c:if test="${sitSrvVO.sitSrvName.indexOf('加價洗澡') != -1}">
	                    <td id="extraBathing_${sitSrvVO.sitSrvName.substring(4)}" style="display: none;" name="mainSrvHide">
	                        <select class="form-control">
	                            <option value="">不洗澡</option>
	                            <option value="${sitSrvVO.sitSrvNo}">洗澡</option>
	                        </select>
	                    </td>
                    </c:if>
                    <c:if test="${sitSrvVO.sitSrvName.indexOf('加價接送') != -1}">
	                    <td id="extraPickup_${sitSrvVO.sitSrvName.substring(4)}" style="display: none;" name="mainSrvHide" >
	                        <select onchange="extraSrv();" class="form-control">
	                            <option>不需要接送</option>
	                            <option value="${sitSrvVO.sitSrvNo}">只要單程接送</option>
	                            <option value="${sitSrvVO.sitSrvNo}">需要來回接送</option>
	                        </select>
	                    </td>
                    </c:if>
                	</c:forEach>
                	
                </tr>
                <%
                  MemService memSrv = new MemService();
                  MemVO memVO = memSrv.getOneMem(memNo);
                  pageContext.setAttribute("memVO", memVO);
                %>
                <tr class="row2">
                    <th scope="row">
                        <img src="<%=request.getContextPath()%>/images/car.png" width="40"> 接送地址:
                        <p style="display: none; color: #E63946;" id="addressInfo" name="mainSrvHide"><i>* 請填寫此欄 *</i></p>
                    </th>
                    <td>
                        <div class="row">
                            <div class="col-5">
                                起點：<br><input type="checkbox" id="pickupFrom" name="pickupFrom" value="${memVO.memAddress}"> ${memVO.memAddress} <br><i>同會員資料地址，如需更改請至<a href="#">個人資料</a>更改</i>
                            </div>
                            <div class="col-7">
                                終點：<div class="col-4"><select class="form-control" name="city" id="city" onchange="changeCity()">
                                        <option>
                                            <%= (sitOrderVO == null) ? "城市": city%>
                                        </option>
                                    </select></div>
                                <div class="col-4">
                                    <select class="form-control" name="district" id="district">
                                        <option>
                                            <%= (sitOrderVO == null) ? "區域": district%>
                                        </option>
                                    </select>
                                </div>
                                <div class="col-8">
                                    <input name="address" type="text" class="form-control" value="${address}">
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr class="row1">
                    <th scope="row"><img src="<%=request.getContextPath()%>/images/coin.png" width="40"> 總金額:</th>
                    <td>$1,500
                        <%
                        
                    %>
                    </td>
                </tr>
            </table>
            <!-- 訂單假資料 -->
            <input type="hidden" name="sitNo" value="<%=(sitOrderVO == null) ? sitNo : sitOrderVO.getSitNo()%>">
            <input type="hidden" name="memNo" value="M001">
            <input type="hidden" name="totalPrice" value="1500">
            <input type="hidden" name="sitOTime" value="">
            <!-- 訂單明細假資料-->
            <input type="hidden" name="sitSrvNo" value="SS006">
            <input type="hidden" name="petNo" value="P00003">
            <input type="hidden" name="sitOpPrice" value="500">
            <input type="hidden" name="sitSrvTimes" value="3">
            <input type="hidden" name="sitSrvUnit" value="晚">
            <div class="submitBtn">
              <input type="submit" value="預約" name="success" id="success" class="btn btn-dark">
              <input type="hidden" name="action" value="reserve">
            </div>
        </form>
    </div>
    <!-- 內文end -->
    <!-- footer -->
    <jsp:include page="/front-end/footer.jsp" />
    <script src="<%=request.getContextPath()%>/js/js-ching/popper.js"></script>
    <script src="<%=request.getContextPath()%>/js/js-ching/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/js-ching/tw-districts.js"></script>
    <script>
    
        $.datetimepicker.setLocale('zh'); // kr ko ja en
        $(function() {
        	
//             $('#from').datetimepicker({
//                 format: 'Y-m-d',
//                 minDate: new Date(),
//                 onShow: function() {
//                     this.setOptions({
//                         maxDate: $('#to').val() ? $('#to').val() : false
//                     })
//                 },
//                 disabledDates:    ['2020/10/22','2020/10/25','2020/10/28'],
//                 timepicker: false
//             });

//             $('#to').datetimepicker({
//                 format: 'Y-m-d',
//                 onShow: function() {
//                     this.setOptions({
//                         minDate: $('#from').val() ? $('#from').val() : false
//                     })
//                 },
//                 disabledDates:    ['2020/10/22','2020/10/25','2020/10/28'],
//                 timepicker: false
//             });
        });

        function toggleSvc() {

            var n = $("#mainSrv").find("option:selected").attr("data-type");
            var sitSrvNo =  $("#mainSrv").find("option:selected").val();
            var data_bathing = $("#mainSrv").find("option:selected").attr("data-bathing");
            var data_pickup = $("#mainSrv").find("option:selected").attr("data-pickup");
            
            var today = new Date();
        	var sitSrvNo =  $("#mainSrv").find("option:selected").val();
        	// 首次建立月曆時(oneSrv)發送ajax取得資料
        	$.ajax({
		        type: "GET",
		       	url: "/EA103G3/front-end/sitOffDay/sitOffDay.do?action=getOneSitSrvOffDay",
		   		data: {sitSrvNo: sitSrvNo,},
		        dataType: "json",
		        cache: false,
		        success: function (result) {
		        	var disabledDates = []; 
		        	
		            $.each(result, function (i, j) {
		            	
		            	var offday = j.offDay;
		            	var offtime = j.offTime;
		            	
		            	if (offtime == null) {
		            		disabledDates.push(offday);
		            	} else if (offday == today){
		            		offtime = offtime.substr(0,2)+ ":" +offtime.substr(-2);
		            		$("[data-value='"+offtime+"']").addClass("hideOffTime");
		            	}
		        	}); 
		            console.log(disabledDates)
		            
		            $('#from').datetimepicker({
		            	format: 'Y-m-d',
		            	timepicker: false,
// 		            	onShow: function() {
// 		                    this.setOptions({
// 		                        maxDate: $('#to').val() ? $('#to').val() : false
// 		                    })
// 		                },
        				disabledDates: disabledDates,
        				minDate: new Date(),
        				maxDate: new Date(new Date().setDate(new Date().getDate() + 30)),
        	        });
		            $('#to').datetimepicker({
		                format: 'Y-m-d',
		                timepicker: false,
// 		                onShow: function() {
// 		                    this.setOptions({
// 		                        minDate: $('#from').val() ? $('#from').val() : false
// 		                    })
// 		                },
		                disabledDates: disabledDates,
        				minDate: new Date(),
        				maxDate: new Date(new Date().setDate(new Date().getDate() + 30)),
		            });
		      	},
		      	error: function (xhr, ajaxOptions, thrownError) {
                	console.log("ajax失敗");
                	console.log(xhr.responseText);
                }
    		
    		});
            
            $("[name='mainSrvHide']").hide();

            if (data_bathing == 1) {
            	$("#extraSrv").show("slow");
            	$("#extraBathing_"+sitSrvNo).show();
            }
            if (data_pickup == 1) {
            	$("#extraSrv").show("slow");
            	$("#extraPickup_"+sitSrvNo).show();
            }
            
            switch (n) {
                case 'Boarding':
//                     $("#extraSrv").show("slow");
//                     $("#extraBathing").show();
//                     $("#extraPickup").show();
                    $("#nightSrvPrice").fadeIn("slow");
                    break;
                case 'DayCare':
//                     $("#extraSrv").show("slow");
//                     $("#extraBathing").show();
//                     $("#extraPickup").show();
                    $("#daySrvPrice").fadeIn("slow");
                    break;
                case 'DropIn':
//                     $("#extraSrv").show("slow");
//                     $("#extraBathing").show();
                    $("#homeSrvPrice").fadeIn("slow");
                    break;
                case 'DogWalking':
//                     $("#extraSrv").show("slow");
//                     $("#extraPickup").show();
                    $("#walkSrvPrice").fadeIn("slow");
                    break;
                case 'PetTaxi':
                    $("#pickupPrice").fadeIn("slow");
                    $("#addressInfo").fadeIn("slow");
                    $("#pickupFrom").attr("checked", true);
                    break;
                default:
                    break;
            }


        }

        function extraSrv() {
            var n = $("#extraPickup").find("option:selected").val();

            switch (n) {
                case 'Pickup':
                    $("#addressInfo").fadeIn("slow");
                    $("#pickupFrom").attr("checked", true);
                    break;
                default:
                    break;
            }
        }
        
    </script>
</body>

</html>