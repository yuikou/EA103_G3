<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*, com.salonF.model.*, com.salon.model.*, java.util.*"%>

<%
	session.setAttribute("memNo", "M003");
session.setAttribute("salno", "B002");
SalFService safSvc = new SalFService();
String memNo = (session.getAttribute("memNo")).toString();
String salno = (session.getAttribute("salno")).toString();

pageContext.setAttribute("memNo", memNo);
pageContext.setAttribute("salno", salno);
//1. 取得會員追蹤的所有美容業者list
//2. 使用contains比對目前頁面的業者是否已存在

String fClass = "fa fa-heart-o"; //預設空心
List<SalonFVO> list = safSvc.getAll(memNo);
for (SalonFVO i : list) {
	if (i.getSalNo().contains(salno)) {
		fClass = "fa fa-heart"; //如果有比對到的話則顯示實心
	}
}

pageContext.setAttribute("fClass", fClass);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 瀏覽器版本相容性 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- 匯入外部CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/SF.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<script> var PATH = "<%=request.getContextPath()%>"; </script>
<title>我是美容業者---測試加入我的最愛</title>

</head>
<body>
	<p>我是會員 ${memNo }</p>
	<p>這裡是美容店 ${salno }</p>

	<p>判斷此會員是否已經被追蹤, 如果是, 則呈現實心愛心, 且點擊愛心後變為空心愛心(取消追蹤)</p>
	<div class="myClick">
		<form>
			<span class="${fClass }" id="followIcon" onclick="getAjax()"></span>
			<input type="hidden" name="memNo" value="${memNo }"> <input
				type="hidden" name="salno" value="${salno }">
		</form>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
	 /*收藏按鈕被點擊時*/
    /*
     * action 1. 判斷空心/實心後轉成另一個
     * action 2. 開啟ajax連線controller, 進行insert/delete
     */
    function getAjax() {
        if ($("#followIcon").hasClass("fa-heart-o")) {
            $.ajax({
                type: "POST",
                url: "<%=request.getContextPath()%>/salf/salf.do?action=insert",
                data: {
                    "memNo": $("[name='memNo']").val(),
                    "salno=": $("[name='salno']").val()
                },
                success: function(data) {
                    if (data == 1) {
                        $("#followIcon").removeClass("fa-heart-o");
                        $("#followIcon").addClass("fa-heart");
                    }
                }
            })

        } else { 
            $.ajax({
                type: "POST",
                url:  PATH + "/salf/salf.do?action=delete",
                data: {
                    "memNo": $("[name='memNo']").val(),
                    "salno=": $("[name='salno']").val()
                },
                success: function(data) {
                    if (data == 0) {
                        $("#followIcon").removeClass("fa-heart");
                        $("#followIcon").addClass("fa-heart-o");
                    }
                }
            })

        }
    }
	</script>
</body>
</html>