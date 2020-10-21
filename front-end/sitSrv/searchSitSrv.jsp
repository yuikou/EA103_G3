<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>查詢托養服務</title>

<!-- 匯入外部CSS -->
<c:set var="cssPath" value="${pageContext.request.contextPath}/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css"> 
<link rel="stylesheet" type="text/css" href="${cssPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/jquery.datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvSearch.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>

<!-------------------- nav -------------------->
	<jsp:include page="/front-end/header.jsp"/>
    
<!------------------ 內文body ------------------>
	<div class="container-fluid">
	
		<div class="banner">
    		<div class="container-wrap">
    			<h1>幫您尋找最符合的寵物托養保姆</h1>
<!-- acpPetTyp-1 -->
		  		<div class="search-container">
		  			<form action="${pageContext.request.contextPath}/sitSrv/sitSrv.do" method="post">
		  			
		  				<div class="pet-type-select">
						    <div class="heading"> 我的寵物類型: </div>
						    <div>
						        <ul class="list">
						            <li class="list-item">
						                <label class="label-checkbox">
						                	<input type="checkbox" name="acpPetTypPart0" class="petTypCheckbox" value="cat">貓
						                </label>
						                <label class="label-checkbox">
						                	<input type="checkbox" name="acpPetTypPart1" class="petTypCheckbox" value="dog">狗
						                </label>
						            </li>
						        </ul>
						    </div>
						</div>
<!-- acpPetTyp-2 -->
						<div class="dog-size-select">
						    <div class="heading"> 我的狗狗體型: </div>
						    <div>
						        <ul class="list">
						            <li class="list-item">
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="small">小型犬(1~5kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="medium">中型犬(5~10kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="large">大型犬(10~20kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="xlarge">特大型犬(20kg以上)
						                </label>
						            </li>
						        </ul>
						    </div>
						</div>
<!-- sitSrvCode -->
						<div class="row">
        
				            <div class="col-sm-12" role="group" aria-labelledby="service_buttons">
				                <div class="heading service-select"> 尋找的服務: </div>
				                <div class="row service-select-group">
									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn"  data-value="Boarding">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon boarding-icon"></i>
									            </div>
									            <div>
									                <div>寄養</div>
									            </div>
									        </a>
									    </div>
									</div>
                            
									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn " data-value="DayCare">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon daycare-icon"></i>
									            </div>
									            <div>
									                <div>日托</div>
									            </div>
									        </a>
									    </div>
									</div>

									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn " data-value="DropIn">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon dropin-icon"></i>
									            </div>
									            <div>
									                <div>到府</div>
									            </div>
									        </a>
									    </div>
									</div>

									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn " data-value="DogWalking">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon dogwalking-icon"></i>
									            </div>
									            <div>
									                <div>遛狗</div>
									            </div>
									        </a>
									    </div>
									</div>

									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn " data-value="PetTaxi">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon pettaxi-icon"></i>
									            </div>
									            <div>
									                <div>寵物計程車</div>
									            </div>
									        </a>
									    </div>
									</div>
                    
               					</div>
           					</div>
       						<input type="hidden" name="sitSrvCode" id="service_buttons" value="${param.sitSrvCode}">
   						</div>
						
						<div class="row">
							<div class="col-sm-5 search-loaction">
								<div class="heading searchQ"> 我的位置: </div>
							</div>
							<div class="col-sm-7 search-time">
								<div class="heading searchQ"> 選擇日期: </div>
							</div>
						</div>
						<div class="row">
<!-- nearAddr -->
							<div class="col-sm-5 search-loaction">
								<span id="panel"><input type="text" id="keyword" class="search-loaction-input" name="nearAddr"></span>
<!-- 								<input class="search-loaction-input" type="text" id="keyword" name="nearAddr" value=""> -->
							</div>
<!-- dateFrom & dateTo -->						
							<div class="col-sm-7 search-time">
								<div class="search-time-div">
									<div class="col-sm-6 search-time-from"> 
										<input class="search-time-input" type="text" id="start_dateTime" name="dateFrom" value="" placeholder="開始日期">
									</div>
									<div class="betweenDiv"><i class="betweenIcon"></i></div>
									<div class="col-sm-6 search-time-end">
										<input class="search-time-input" type="text" id="end_dateTime" name="dateTo" value="" placeholder="結束日期">
									</div>
									
								</div>
							</div>
						</div>
						<div class="row" style="clear:both;">
							<div class="col-sm-10 search-condition">
								<input type="hidden" name="action" value="search">
								<button class="search-condition-btn">送出搜尋</button>
							</div>
						</div>
		  			</form>
		  		</div>
    		
    		</div>
  		</div>
	
	
		<section class="about" id="about">
		 	<div class="container">
		   		<div class="row">
		     		<div class="col-xs-12 col-sm-12">
		 	  			<h2>簡單幾步輕鬆上手</h2>
		       		</div>
		      	</div>
    			<div class="row">
      				<div class="col-xs-12 col-sm-4 srvInfoDiv">
      					<div class="srvInfo">
      						<i class="service-icon boarding-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
	      						<h4>寵物寄養</h4>
	        					<span>你的寵物到保姆家中過夜</span>
	        				</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon daycare-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
      							<h4>寵物日托</h4>
        						<span>你的寵物到保姆家中溜達(不過夜)</span>
        					</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon dropin-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
	      						<h4>到府保姆</h4>
	        					<span>保姆來你家幫寵物餵食與清潔</span>
	        				</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon dogwalking-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
	      						<h4>遛狗</h4>
	        					<span>保姆帶你的寵物外出透氣</span>
      						</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon pettaxi-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
      							<h4>寵物計程車</h4>
        						<span>載送你的寵物到指定地點</span>
        					</div>
      					</div>
      				</div>
      				<div class="col-xs-12 col-sm-8 progressDiv">
      					<div class="col-xs-12">
					      <div class="progress_step_wrap">
					        <div class="progress_step first_step">
					          <div class="step_line"><span class="step_number">1</span>
					          </div>
					        </div>
					        <div class="progress_step">
					          <div class="step_line"><span class="step_number">2</span>
					          </div>
					        </div>
					        <div class="progress_step last_step">
					          <div class="step_line"><span class="step_number">3</span>
					          </div>
					        </div>
					      </div>
					    </div>
					    <div class="col-xs-12 col-sm-12" style="display: flex;">
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/search.svg">
					    		<div class="progress_step_descript">
						    		<h4>挑選保姆</h4>
							    	<span>利用條件、評價、日期與地圖挑選心儀的保姆</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/talking.svg">
					    		<div class="progress_step_descript">
						    		<h4>線上私聊</h4>
							    	<span>預約前可以跟保姆線上私聊, 確認保姆合不合適</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/schedule.svg">
					    		<div class="progress_step_descript">
						    		<h4>立即預約</h4>
							    	<span>在平台上快速填單預約, 讓您盡早安排個人行程</span>
					    		</div>
					    	</div>
					    </div>
					    
					    <div class="col-xs-12">
					      <div class="progress_step_wrap">
					        <div class="progress_step first_step">
					          <div class="step_line"><span class="step_number">4</span>
					          </div>
					        </div>
					        <div class="progress_step">
					          <div class="step_line"><span class="step_number">5</span>
					          </div>
					        </div>
					        <div class="progress_step last_step">
					          <div class="step_line"><span class="step_number">6</span>
					          </div>
					        </div>
					      </div>
					    </div>
					    <div class="col-xs-12 col-sm-12" style="display: flex;">
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/onlinePay.svg">
					    		<div class="progress_step_descript">
						    		<h4>支付容易</h4>
							    	<span>以信用卡/線上匯款方式付款, 安全方便快速</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/lovePet.svg">
					    		<div class="progress_step_descript">
						    		<h4>呵護寵物</h4>
							    	<span>當你不在寵物身邊時, 保姆會提供一定的照顧與關注</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/comment.svg">
					    		<div class="progress_step_descript">
						    		<h4>後續評價</h4>
							    	<span>服務結束後邀請您評價, 建立公開透明的社群信用</span>
					    		</div>
					    	</div>
					    </div>
				    </div>
				</div>
			</div>	
 		</section> <!-- end of about section-->
	</div>

<!------------------ footer ------------------>
    <jsp:include page="/front-end/footer.jsp"/>


<!-- 匯入js -->
	<c:set var="jsPath" value="${pageContext.request.contextPath}/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/jquery.datetimepicker.full.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script src="${jsPath}/google-map.js"></script>
	<script>
	$.datetimepicker.setLocale('zh');
	$(document).ready(function(){
		
		
		// --------------------狗狗size--------------------	
		var acpPetTypPart1 = $("[name='acpPetTypPart1']");
		var acpPetTypPart2 = $("[name='acpPetTypPart2']");
		var acpPetTyp2_div = $(".dog-size-select");
			
		if (acpPetTypPart1.prop("checked")) {
			acpPetTyp2_div.show();
		} else {
			acpPetTyp2_div.hide();
		}
		acpPetTypPart1.click(function(){
			if (acpPetTypPart1.prop("checked")) {
				acpPetTyp2_div.show();
			} else {
				acpPetTyp2_div.hide();
				acpPetTypPart2.each(function(){
					$(this).prop("checked", false);
				});
			}
		});
		
		
		// --------------------選擇服務--------------------
		var ssBtn = $(".service-select-btn");
		ssBtn.each(function(){
			$(this).click(function(){
				ssBtn.removeClass("line");
				$(this).toggleClass("line");
				// 放入選擇的sitSrvCode
				var sitSrvV = $(this).attr("data-value");
				$("#service_buttons").val(sitSrvV);
			});
		});
		
		// --------------------可選日期--------------------
		var today = new Date();
		var ninetyDays = new Date();
		ninetyDays.setDate(today.getDate()+90);
		$('#start_dateTime').datetimepicker({
			theme: 'light-bootstrap',
			format:'Y-m-d',
			onShow:function(){
				this.setOptions({
				maxDate:$('#end_dateTime').val()?$('#end_dateTime').val():false
				})
			},
			timepicker:false,
			beforeShowDay: function(date) {
				if ( date.getYear() <  today.getYear() || 
				   ( date.getYear() == today.getYear() && date.getMonth() <  today.getMonth()) || 
				   ( date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDate() < today.getDate())
				      ||
				     date.getYear() >  ninetyDays.getYear() || 
				   ( date.getYear() == ninetyDays.getYear() && date.getMonth() >  ninetyDays.getMonth()) || 
				   ( date.getYear() == ninetyDays.getYear() && date.getMonth() == ninetyDays.getMonth() && date.getDate() > ninetyDays.getDate())
				 ) {
				 	 return [false, ""]
				 }
				return [true, ""];
			}
		});
			 
		$('#end_dateTime').datetimepicker({
			theme: '',
			format:'Y-m-d',
			onShow:function(){
				this.setOptions({
				minDate:$('#start_dateTime').val()?$('#start_dateTime').val():false
				})
			},
			timepicker:false,
			beforeShowDay: function(date) {
				if ( date.getYear() <  today.getYear() || 
				   ( date.getYear() == today.getYear() && date.getMonth() <  today.getMonth()) || 
				   ( date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDate() < today.getDate())
				      ||
				     date.getYear() >  ninetyDays.getYear() || 
				   ( date.getYear() == ninetyDays.getYear() && date.getMonth() >  ninetyDays.getMonth()) || 
				   ( date.getYear() == ninetyDays.getYear() && date.getMonth() == ninetyDays.getMonth() && date.getDate() > ninetyDays.getDate())
				 ) {
				 	 return [false, ""]
				 }
				return [true, ""];
			}
		});

		
	});
	
	</script>
	
</body>
</html>