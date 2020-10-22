	  new TwCitySelector();
	  
	  var salonList;
 	  $('#searchbtn').click(function(){
		  $.ajax({
			  type: "POST",
			  url: PATH + "/salon/salon.do",
			  dataType:"JSON",
			  data: {
				"action": "findByCondition",
				"salCity": $("[name='county']").val(),
				"salDist": $("[name='district']").val(),
				"salPetType": $("[name='petcat']").val()
			  },
			  success: function(data) {
				
				  salonList = data;
				  console.log(salonList);
// 				  $('.salPic').empty();
// 				  $('.salbody').empty();
				  $('#sbody').empty();
				  for(let i = 0; i < Object.keys(salonList).length; i++){
					  
					 var salno = salonList[i].salNo;
					 var salPic = salonList[i].salPic;
					 var sName = salonList[i].salName;
					 var location = salonList[i].salCity + salonList[i].salDist;
					 
					 var str1 = salonList[i].salSTime.substring(0,2);
					 var str2 = salonList[i].salSTime.substring(2);
					 var sTime = str1 + ":" + str2;
					 
					 var str3 = salonList[i].salETime.substring(0,2);
					 var str4 = salonList[i].salETime.substring(2);
					 var eTime = str3 + ":" + str4;
					 
					 var openTime = sTime + " ~ " + eTime;
					 var star = salonList[i].salTotalScore;
					 var num = salonList[i].salPetType;
					 var petcat = '';
					 
					 if(num === 0){
						 petcat = `"/Pet_Test/image/both2.svg"`;
					 }else if(num === 1){
						 petcat = `"/Pet_Test/image/dog(1).svg"`;
					 }else{
						 petcat = `"/Pet_Test\/image\/cat(1).svg"`;
					 }
					  
					$('#sbody').html('<div class=\"showCard\"><div class=\"row\"><div class=\"col-md-4 salPic\"><img id=\"salPic\" class=\"grmPic\" src=\"'+ PICURL + salno + '\"></div><div class=\"col-md-6 salbody\"><p id=\"salno\"></p><div id=\"sName\"></div><div class=\"star\" data-rateit-value=\"'+ star +'\" data-rateit-ispreset=\"true\" data-rateit-readonly=\"true\"></div><div id=\"location\"></div><div class=\"openTime\">營業時間</div><div id=\"opentime\"></div></div></div>');
					//$('#salPic').attr('src', `data:image/png;base64,${salPic}`);
					$('#salno').attr('name', salno);
					$('#sName').text(sName);
					$('.star').addClass('rateit');
					$('.star').addClass('rateit-bg');
					$('#location').text(location);
					$('#opentime').text(openTime);
					
					
					$('.salbody').after('<div class="col-md-2 salPet"><a class=\"fa fa-paw myText\">選我!</a><a class=\"fa fa-shopping-cart myText bookNow\">預約</a><div class=\"salpet\"><img src=' + petcat + ' id=\"petcat\" class=\"myicon\"></div></div>');
//單一美容店頁面連結				  	//$('.myText').attr('href', PATH + '');
					$('.bookNow').attr('href', PATH + '/salsev/salsev.do?action=bookNow&salno=' + salno);
// 					$('#petcat').attr('src', getPetCat(num));
					$('.rateit').rateit();
				  }
			  }
		  });
 	  });
 	  
 	  
//  	  function getPetCat(num){
 		  
//  		 if(num === 0){
// 			 petcat = `"/Pet_Test/image/both2.svg"`;
// 		 }else if(num === 1){
// 			 petcat = `"/Pet_Test/image/dog(1).svg"`;
// 		 }else{
// 			 petcat = `"/Pet_Test\/image\/cat(1).svg"`;
// 		 }
//  		 return petcat;
//  	  }
 	  