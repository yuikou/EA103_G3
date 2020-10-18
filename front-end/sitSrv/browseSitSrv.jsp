<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<!-- Required meta tags -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>�s�����i�A��</title>

<!-- �פJ�~��CSS -->
<c:set var="path" value="/EA103G3/front-end" />
<c:set var="cssPath" value="/EA103G3/css/euphy" />
<link rel="stylesheet" type="text/css" href="${cssPath}/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${path}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/Petfect.css">
<link rel="stylesheet" type="text/css" href="${cssPath}/sitSrvSearch.css">
<link rel="Shortcut Icon" type="image/x-icon" href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
</head>

<body>

<!-------------------- nav -------------------->
	<jsp:include page="/front-end/nav.jsp"/>
    
<!------------------ ����body ------------------>
	<div class="container-fluid">
	
		<div class="banner">
    		<div class="container-wrap">
    			<h1>���z�M��̲ŦX���d�����i�O�i</h1>
<!-- acpPetTyp-1 -->
		  		<div class="search-container">
		  			<form action="sitSrv.do" method="post">
		  			
		  				<div class="pet-type-select">
						    <div class="heading"> �ڪ��d������: </div>
						    <div>
						        <ul class="list">
						            <li class="list-item">
						                <label class="label-checkbox">
						                	<input type="checkbox" name="acpPetTypPart0" class="petTypCheckbox" value="cat">��
						                </label>
						                <label class="label-checkbox">
						                	<input type="checkbox" name="acpPetTypPart1" class="petTypCheckbox" value="dog">��
						                </label>
						            </li>
						        </ul>
						    </div>
						</div>
<!-- acpPetTyp-1 -->
						<div class="dog-size-select">
						    <div class="heading"> �ڪ������髬: </div>
						    <div>
						        <ul class="list">
						            <li class="list-item">
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="cat">�p����(1~5kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="dog">������(5~10kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="cat">�j����(10~20kg)
						                </label>
						                <label class="label-radio">
						                	<input type="radio" name="acpPetTypPart2" class="petTypCheckbox" value="dog">�S�j����(20kg�H�W)
						                </label>
						            </li>
						        </ul>
						    </div>
						</div>

						<div class="row">
        
				            <div class="col-sm-12" role="group" aria-labelledby="service_buttons">
				                <div class="heading service-select"> �M�䪺�A��: </div>
				                <div class="row service-select-group">
									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn" data-value="Boarding">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon boarding-icon"></i>
									            </div>
									            <div>
									                <div>�H�i</div>
									            </div>
									        </a>
									    </div>
									</div>
                            
									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn" data-value="DayCare">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon daycare-icon"></i>
									            </div>
									            <div>
									                <div>�馫</div>
									            </div>
									        </a>
									    </div>
									</div>

									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn" data-value="DropIn">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon dropin-icon"></i>
									            </div>
									            <div>
									                <div>�쩲</div>
									            </div>
									        </a>
									    </div>
									</div>

									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn" data-value="DogWalking">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon dogwalking-icon"></i>
									            </div>
									            <div>
									                <div>����</div>
									            </div>
									        </a>
									    </div>
									</div>

									<div class="col-xs-12 col-sm-2 service-select-div">
									    <div class="service-select-btn" data-value="PetTaxi">
									        <a class="focusMe">
									            <div class="hidden-xs">
									               <i class="service-icon pettaxi-icon"></i>
									            </div>
									            <div>
									                <div>�d���p�{��</div>
									            </div>
									        </a>
									    </div>
									</div>
                    
               					</div>
           					</div>
       						<input type="hidden" name="sitSrvCode" id="service_buttons" value="">
   						</div>
						
						<div class="row">
							<div class="col-sm-5 search-loaction">
								<div class="heading searchQ"> �ڪ���m: </div>
							</div>
							<div class="col-sm-7 search-time">
								<div class="heading searchQ"> ��ܤ��: </div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-5 search-loaction">
								<input class="search-loaction-input" type="text" name="" value="">
							</div>
							<div class="col-sm-7 search-time">
								<div class="search-time-div">
									<div class="col-sm-6 search-time-from"> 
										<input class="search-time-input" type="date" name="" value="">
									</div>
									<div class="betweenDiv"><i class="betweenIcon"></i></div>
									<div class="col-sm-6 search-time-end">
										<input class="search-time-input" type="date" name="" value="">
									</div>
									
								</div>
							</div>
						</div>
						<div class="row" style="clear:both;">
							<div class="col-sm-10 search-condition">
								<button class="search-condition-btn">�e�X�j�M</button>
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
		 	  			<h2>²��X�B���P�W��</h2>
		       		</div>
		      	</div>
    			<div class="row">
      				<div class="col-xs-12 col-sm-4 srvInfoDiv">
      					<div class="srvInfo">
      						<i class="service-icon boarding-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
	      						<h4>�d���H�i</h4>
	        					<span>�A���d����O�i�a���L�]</span>
	        				</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon daycare-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
      							<h4>�d���馫</h4>
        						<span>�A���d����O�i�a���ȹF(���L�])</span>
        					</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon dropin-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
	      						<h4>�쩲�O�i</h4>
	        					<span>�O�i�ӧA�a���d�������P�M��</span>
	        				</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon dogwalking-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
	      						<h4>����</h4>
	        					<span>�O�i�a�A���d���~�X�z��</span>
      						</div>
      					</div>
      					<div class="srvInfo">
      						<i class="service-icon pettaxi-icon srvInfo-icon"></i>
      						<div class="srvInfoDescript">
      							<h4>�d���p�{��</h4>
        						<span>���e�A���d������w�a�I</span>
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
						    		<h4>�D��O�i</h4>
							    	<span>�Q�α���B�����B����P�a�ϬD��߻����O�i</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/talking.svg">
					    		<div class="progress_step_descript">
						    		<h4>�u�W�p��</h4>
							    	<span>�w���e�i�H��O�i�u�W�p��, �T�{�O�i�X���X�A</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/schedule.svg">
					    		<div class="progress_step_descript">
						    		<h4>�ߧY�w��</h4>
							    	<span>�b���x�W�ֳt���w��, ���z�ɦ��w�ƭӤH��{</span>
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
						    		<h4>��I�e��</h4>
							    	<span>�H�H�Υd/�u�W�״ڤ覡�I��, �w����K�ֳt</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/lovePet.svg">
					    		<div class="progress_step_descript">
						    		<h4>���@�d��</h4>
							    	<span>���A���b�d�������, �O�i�|���Ѥ@�w�����U�P���`</span>
					    		</div>
					    	</div>
					    	<div class="col-sm-4 progress_step_info">
					    		<img class="progress_step_img" src="${pageContext.request.contextPath}/front-end/img/comment.svg">
					    		<div class="progress_step_descript">
						    		<h4>�������</h4>
							    	<span>�A�ȵ������ܽбz����, �إߤ��}�z�������s�H��</span>
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


<!-- �פJjs -->
	<c:set var="jsPath" value="/EA103G3/js/euphy" />
	<script src="${jsPath}/jquery-3.2.1.min.js"></script>
	<script src="${jsPath}/popper.js"></script>
	<script src="${jsPath}/bootstrap.min.js"></script>
	<script>
	$(document).ready(function(){
		
		var ssBtn = $(".service-select-btn");
		ssBtn.each(function(){
			$(this).click(function(){
				ssBtn.removeClass("line");
				$(this).toggleClass("line");
				// ��J��ܪ�sitSrvCode
				var sitSrvV = $(this).attr("data-value");
				$("#service_buttons").val(sitSrvV);
			});
		});

		
	});
	</script>
	
</body>
</html>