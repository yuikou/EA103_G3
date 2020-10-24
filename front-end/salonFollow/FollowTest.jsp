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
//1. ���o�|���l�ܪ��Ҧ����e�~��list
//2. �ϥ�contains���ثe�������~�̬O�_�w�s�b

String fClass = "fa fa-heart-o"; //�w�]�Ť�
List<SalonFVO> list = safSvc.getAll(memNo);
for (SalonFVO i : list) {
	if (i.getSalNo().contains(salno)) {
		fClass = "fa fa-heart"; //�p�G�����쪺�ܫh��ܹ��
	}
}

pageContext.setAttribute("fClass", fClass);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- �s���������ۮe�� -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<!-- �פJ�~��CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/index.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/SF.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/liz/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="https://dzmg8959fhe1k.cloudfront.net/all/favicon.ico">
<script> var PATH = "<%=request.getContextPath()%>"; </script>
<title>�ڬO���e�~��---���ե[�J�ڪ��̷R</title>

</head>
<body>
	<p>�ڬO�|�� ${memNo }</p>
	<p>�o�̬O���e�� ${salno }</p>

	<p>�P�_���|���O�_�w�g�Q�l��, �p�G�O, �h�e�{��߷R��, �B�I���R�߫��ܬ��Ť߷R��(�����l��)</p>
	<div class="myClick">
		<form>
			<span class="${fClass }" id="followIcon" onclick="getAjax()"></span>
			<input type="hidden" name="memNo" value="${memNo }"> <input
				type="hidden" name="salno" value="${salno }">
		</form>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
	 /*���ë��s�Q�I����*/
    /*
     * action 1. �P�_�Ť�/��߫��ন�t�@��
     * action 2. �}��ajax�s�ucontroller, �i��insert/delete
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