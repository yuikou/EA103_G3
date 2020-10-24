
		$(document).ready(function(){
//list all offday
			var grmOffDay = null;
			var myEvents2 = [];
			var offno = '';
			var offtype = '';
			var myresult = '';
			var gname = '';
			var dayset = '';
			var clickDay = '';
			var text = '';
			
			$.ajax({
				type: "GET",
				url: PATH + "/goday/goday.do?action=listGoff",
				success : function(data){
					
					swal({
						title: "行事曆載入成功",
						html: '<i class="fa fa-trophy"> HI, ' + salName + ' 的管理者<br>請使用行事曆上方按鈕編輯美容師休假!',
						type: "info"
					});
					
					grmOffDay = JSON.parse(data);
					for(let i = 0; i < Object.keys(grmOffDay).length; i++){
						var offTime = '';
						var type = '';
						var myStr = {};
						
						var offno = grmOffDay[i].offNo;
						var gName = grmOffDay[i].groomerName;
						var offDay = grmOffDay[i].offDay;
						var offDayType = grmOffDay[i].offDayType;
						
						if(offDayType === 0){
							type = 'holiday';
						}else{
							type = 'event';
						}
						
						if(offDayType === 1){
							offTime = grmOffDay[i].offTime + "接客🤦";
						}else{
							offTime = '給放啦哪次不給放的🤗';
						}
						
						myStr.id = offno;
						myStr.name = gName;
						myStr.date = offDay;
						myStr.description = offTime;
						myStr.type = type;
						
						myEvents2.push(myStr);
					}
				}
			});
			
			$('#evoCalendar').evoCalendar({
				format:'yyyy-mm-dd',
				titleFormat:'MM yyyy',
				eventHeaderFormat:'MM d, yy',
				todayHighlight:'true',
				//theme:'Orange Coral',
				calendarEvents: myEvents2
			});
			
		
			//----以下處理刪除off day----
			// selectEvent and get offno
			$('#evoCalendar').on('selectEvent', function(event, activeEvent) {
				offno = activeEvent.id;
				offtype = activeEvent.type;
				gname = activeEvent.name;
				dayset = activeEvent.date;
				$('#delOff').attr('disabled', false);
			});
			
			//刪除處理
			$('#delOff').click(function(){
				console.log(offtype);
				console.log(offno);
				
				if(offtype === "event"){
					swal('錯誤!','不可刪除客人預約的時間','error');
				}else{
					swal({
		                title: "確定刪除？",
		                type: "warning", // type can be "success", "error", "warning", "info", "question"
		                html:
		                	'按下確定後就刪除休假了喔?<br>美容師:'+ gname+'<br>' + '選擇的日期:' + dayset,
		                showCancelButton: true,
		                showCloseButton: true,
		            }).then(function(result) {
		                if (result) {
		                	fireDel(offno);
		                }
		            }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
		                swal("取消", "休假未被刪除", "error");
		            }).catch(swal.noop);
					
				}
				
			});
			
			//----以下處理新增off day----
			//取得點擊的日期
			$("#evoCalendar").on('selectDate', function(dateNumber){
				clickDay = $("#evoCalendar").evoCalendar('getActiveDate');
				$('#daytext').text('');
				$('#daytext').append(clickDay);
			});
			
			$('#offbody').hide();
			$("#addOff").click(function(){
				$('#offbody').toggle();
			});
			//處理新增
			$('#sendbtn').click(function(e){
				e.preventDefault();
				var who = $("[name='grmno']").val();
				var doubleOff = false;
				
				console.log(clickDay);
				console.log(who);
				//這裡判斷日期是否重複
				for(let i = 0; i < Object.keys(grmOffDay).length; i++){
					var groomerNo = grmOffDay[i].groomerNo;
					var offDay = grmOffDay[i].offDay;
					var offDayType = grmOffDay[i].offDayType;
					
					if((who === groomerNo) && ( clickDay === offDay)){
						doubleOff = true;
						text = (offDayType === 0) ? '已經畫休啦':'要接客啦';
					}
				}
				
				if(doubleOff){
					swal({
						title:"Event covered!",
						html: "你還在嗎?&nbsp;" + text,
						type: "error"
					});
				}else{
					swal({
		                title: "確定送出休假？",
		                html: "日期:&nbsp;" + clickDay,
		                type: "question", 
		                showCancelButton: true,
		                showCloseButton: true,
		            }).then(function(result) {
		                if (result) {
		                	grmSetOff(who, clickDay);	
		                }
		            }, function(dismiss) { 
		                swal("取消", "乖乖上班QQ", "error");
		            }).catch(swal.noop);					
				}
					
				
			});
			
			function grmSetOff(who, when){
				$.ajax({
					type: "POST",
					url: PATH + "/goday/goday.do?action=grmSetOff",
					data:{
						"grmno":who,
						"offdate":when,
					},
					success : function(data){
						if (data == 1) {
							addEvent();
						}
					}
				});
			}
			
			function addEvent(){
				swal({
					title: "新增成功!",
					html:"還敢放假啊<br>點擊OK後</b>將重新載入行事曆",
					type:"success"
				}).then(function() {
					location.reload();
	            }).catch(swal.noop);
			}
			
			function fireDel(offno){
				$('#evoCalendar').evoCalendar('removeCalendarEvent', offno);
				
				$.ajax({
					type: "POST",
					url: PATH + "/goday/goday.do?action=delete",
					data:{
						"action": "delete",
						"offno" : offno
					},
					success : function(data){
						myresult = data;
						if (myresult == 1){
							swal("刪除完成!", "工作吧社畜", "success");
						}
					}
				});
			}
			
		});
		