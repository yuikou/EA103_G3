// 	取消(回上一頁)
		$('#back').click(function() {
			window.history.back();
		});
		
		//ajax - 選擇日期
		$('#oneGrmOff').change(function(){
			$.ajax({
				type: "POST",
				url: PATH + "/goday/goday.do?action=oneGrmOff",
				data:{
					"grmno": $("[name='grmno']").val()
				},
				success:function(data){
					var myDay = data;
					$.datetimepicker.setLocale('zh')
					$('#f_date1').datetimepicker({
						theme : '',
						timepicker : false,
						format : 'Y-m-d',
						opened: true,
						value : new Date(),
						minDate: '+1970-02-02',
			 			maxDate: '+1970-03-02',
			 			disabledDates: myDay, formatDate:'Y-m-d'
					});
				}
			})
					
		});
		
		//ajax - 送出休息日
		function grmSetOff(){
			$.ajax({
				type: "POST",
				url: PATH + "/goday/goday.do?action=grmSetOff",
				data : {
					"grmno" : $("[name='grmno']").val(),
					"offdate" : $("[name='offdate']").val(),
					"goffdaytype": "0"
				},
				success : function(data) {
					if (data == 1) {
						alert("還敢放假啊"); 
					}
				}
			});
		}