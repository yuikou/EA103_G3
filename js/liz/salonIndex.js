$(document).ready(function() {
			var grmOffDay = null;
			var myEvents2 = [];
			var offno = '';
			var offtype = '';

			$.ajax({
				type : "GET",
				url : PATH + "/goday/goday.do?action=listGoff",
				success : function(data) {

					grmOffDay = JSON.parse(data);
					for (let i = 0; i < Object.keys(grmOffDay).length; i++) {
						var offTime = '';
						var type = '';
						var myStr = {};

						var offno = grmOffDay[i].offNo;
						var gName = grmOffDay[i].groomerName;
						var offDay = grmOffDay[i].offDay;
						var offDayType = grmOffDay[i].offDayType;

						if (offDayType === 0) {
							type = 'holiday';
						} else {
							type = 'event';
						}

						if (offDayType === 1) {
							offTime = grmOffDay[i].offTime + "Work!!";
						} else {
							offTime = '給放啦!!';
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
				format : 'yyyy-mm-dd',
				titleFormat : 'MM yyyy',
				eventHeaderFormat : 'MM d, yy',
				todayHighlight : 'true',
				eventDisplayDefault : false,
				theme : 'Orange Coral',
				calendarEvents : myEvents2
			});
		});