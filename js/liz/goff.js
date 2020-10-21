console.log(PATH);
    var nowDate = new Date();
    var curYear = nowDate.getFullYear();
    var curMonth = nowDate.getMonth();
    var curDay = nowDate.getDay(); //星期幾
    var curDate = nowDate.getDate();

    var days = document.getElementById("days");
    var year = document.getElementById("calendar-year");
    var month = document.getElementById("calendar-month");

    var monthName = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var leapYear = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var normalYear = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var nowMonth = null;

    setCalendar(curYear, curMonth);

    function setCalendar(curYear, curMonth) {
        var startday;
        var totaldays;
        var tempDate = new Date(curYear, curMonth, 1);
        startday = tempDate.getDay(); // setStartday
        if (curYear % 4 === 0) {
            totaldays = leapYear[curMonth]; //settotaldays
        } else {
            totaldays = normalYear[curMonth];
        }
        //         console.log(totaldays);
        //-----------------------------------------------

        var str = '';
        var d = new Date();
        for (var j = 0; j < startday; j++) {
            str += "<li>" + "</li>";
        }
        for (var i = 1; i <= totaldays; i++) {
            if (i < curDate && curYear === d.getFullYear() && curMonth === d.getMonth()) {
                str += "<li class=\"lightgrey\">" + i + "</li>";
            } else if (i === curDate && curYear === d.getFullYear() && curMonth === d.getMonth()) {
                str += "<li id=\"isToday\" class=\"green greenbox\">" + i + "</li>";
            } else {
                str += "<li class=\"darkgrey afterDays\">" + i + "</li>";
            }
        }

        days.innerHTML = str;
        year.innerHTML = curYear;
        month.innerHTML = monthName[curMonth];

        nowMonth = curMonth + 1;
    }


    //----------------------------------------------------------------
    var prev = document.getElementById("prev");
    var next = document.getElementById("next");

    next.addEventListener("click", function(e) {
        e.preventDefault(); //很重要!
        if (curMonth < 11) { //curMonth 0-11
            curMonth += 1;
        } else {
            curYear += 1;
            curMonth = 0;
        }
        setCalendar(curYear, curMonth);
    });

    prev.addEventListener("click", function(e) {
        e.preventDefault();
        if (curMonth > 0) {
            curMonth -= 1;
        } else {
            curYear -= 1;
            curMonth = 11;
        }
        setCalendar(curYear, curMonth);
    });

    year.addEventListener("click", function a() {
        var temp = prompt("請輸入年份", 2020);
        if (temp > 0) {
            curYear = temp;

        } else if (temp !== null) {
            a();
        }
        setCalendar(curYear, curMonth);
    });
    month.addEventListener("click", function a() {
        var temp = prompt("請輸入月份", 1);
        if (temp <= 12 && temp > 0) {
            curMonth = temp - 1;

        } else if (temp !== null) {
            a();
        }
        setCalendar(curYear, curMonth);
    });
    //----------------------------------------------

	var fullDate = "";
	var myTime = "";
	var str2 = '';
	

	//取得當日可預約時段
    $('.calendar').click(function() {
        var timeList = {};
        $('#offTimeList').empty();
        str1 = $('#calendar-year').text();
        str = $('#calendar-month').text();
        
        $('.afterDays').click(function(){
    		str2 = $(this).text();
    	});
       
        for (var i = 0; i < monthName.length; i++) {
            if (str === monthName[i]) {
                numMonth = i + 1;
            }
        }

        var myday = numMonth + '/' + str2;
        $('#myday').text(myday);
        
        fullDate = str1 + "-" + numMonth + "-" + str2;
        console.log(fullDate);
        $.ajax({
            type: "POST",
            url: PATH + "/goday/goday.do",
            data: {
                "action": "felistGoff",
                "myDate": fullDate
            },
            success: function(data) {
                timeList = JSON.parse(data);
                console.log(timeList);
                if(Object.keys(timeList).length === 0){
                	$('#offTimeList').append("<div class=\"panel\">Sorry 已約滿</div>");
                	
                }else{
                	for (var prop in timeList) {
                        $('#offTimeList').append("<div class=\"panel\"><a class=\"freeTime\">" + timeList[prop] + "</a></div>");
                        $(".panel").click(function(){
                        	myTime = $(this).text();
		                	$(".freeTime").attr("href", PATH + "/goday/goday.do?action=toOrder&offDay="+ fullDate +"&offTime=" + myTime + "&goffdaytype=1");
                        });
                	}
                }
               
                
            }
        });
        
    });
   