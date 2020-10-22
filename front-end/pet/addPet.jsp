<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pet.model.*"%>
<%@ page import="com.member.model.*" %>
<%@ page import="java.util.*" %>

<% 
	PetVO petVO = (PetVO) request.getAttribute("petVO"); 
	Hashtable<String, String> errorMsgs = (Hashtable<String, String>) request.getAttribute("errorMsgs");
%>
<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Petfect Match</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    
    <style>
        * {
			/*�H�U�T��M���s�����w�]�ƪ�*/
			margin: 0px;
			padding: 0px;
			list-style: none;
			box-sizing: block;
			font-family: 'Montserrat', �L�n������, sans-serif;
		}

		body {
		/*��������-�̥զ�*/
			background-color: rgba(252, 244, 217, 0.4);
		}
		.container {
			position: relative;
			top: 100px;
			padding-bottom:150px;
		}
        
        
        
/*         .petphoto { */
/*         	margin-left:15px; */
/*         } */
    </style>
</head>

<body>
    <div class="container">
    <c:if test="${not empty errorMsgs }"><%=errorMsgs.get("memNo") %></c:if>
        <form action="<%=request.getContextPath()%>/pet/pet.do" method="post" enctype="multipart/form-data">
            <div class="row" id="row1">
                <div class="col-3 offset-2" id="div1">
                    <div class="form-group">
                        <label for="petPhoto">�ФW�Ǥj�Y�K</label>
                        <input type="file" class="form-control-file" name="petPhoto" onchange="loadImageFile(event)" id="petPhoto">
<%--                          <% --%>
<!--  							 final Base64.Encoder encoder = Base64.getEncoder(); -->
<!--  							 final String encodedText = encoder.encodeToString(petVO.getPetPhoto()); -->
<%-- 						%>  --%>
<%--                         <img src="data:image/png;base64,<%= encodedText %>" alt="" id="image" class="img-fluid img-thumbnail"> --%>
                        <img src="" alt="" id="image" class="img-fluid img-thumbnail">
                        <c:if test="${not empty errorMsgs.petPhoto }">${errorMsgs.petPhoto}</c:if>
                    </div>
                </div>
                <div class="col-4" id="div2">
                    <div class="form-group">
                        <label for="petName">�d���W�r&nbsp;:</label>
                        <input type="text" class="form-control" placeholder="�ж�J�d���W�r" name="petName" id="petName" value="<%=(petVO==null)?"":petVO.getPetName()%>">
                        <c:if test="${not empty errorMsgs.petName }">${errorMsgs.petName }</c:if>
                    </div>
                    <div class="form-group">
                        <label for="petSex">�d���ʧO&nbsp;:</label><br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="petSex" id="inlineRadio1" value=0 <c:if test="${petVO.petSex==0}">checked</c:if>>
                            <label class="form-check-label" for="inlineRadio1">��</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="petSex" id="inlineRadio2" value=1 <c:if test="${petVO.petSex==1}">checked</c:if>>
                            <label class="form-check-label" for="inlineRadio2">��</label>
                        </div>
                        <c:if test="${not empty errorMsgs.petSex }">${errorMsgs.petSex }</c:if>
                    </div>
                    <div class="form-group">
                        <label>�d���ͤ�&nbsp;:</label>
                        <input type="text" class="form-control" placeholder="�Ҧp:2004-02-28" name="petBirth" id="petBirth">
                        <c:if test="${not empty errorMsgs.petBirth }">${errorMsgs.petBirth }</c:if>
                    </div>
                    <div class="form-group">
                        <label for="petType">�d������&nbsp;:</label>
                        <select class="form-control" id="petType" name="petType">
                            <option>�п���d������</option>
                            <option value=0 <c:if test="${petVO.petType==0}">selected</c:if>>��</option>
                            <option value=1 <c:if test="${petVO.petType==1}">selected</c:if>>��</option>
                            <option value=2 <c:if test="${petVO.petType==2}">selected</c:if>>��L�d��</option>
                        </select>
                        <c:if test="${not empty errorMsgs.petType }">${errorMsgs.petType }</c:if>
                    </div>
                    <div class="form-group">
                        <label for="petCat">�d������&nbsp;:</label>
                        <select class="form-control" id="petCat" name="petCat">
                            <option>�п���d������</option>
                            <optgroup label="�߫}">
                                <option value=1 <c:if test="${petVO.petCat==1}">selected</c:if>>�����</option>
                                <option value=2 <c:if test="${petVO.petCat==2}">selected</c:if>>�u���</option>
                            </optgroup>
                            <optgroup label="����">
                                <option value=3 <c:if test="${petVO.petCat==3}">selected</c:if>>�p����(1-5kg)</option>
                                <option value=4 <c:if test="${petVO.petCat==4}">selected</c:if>>������(6-10kg)</option>
                                <option value=5 <c:if test="${petVO.petCat==5}">selected</c:if>>�j����(10-20kg)</option>
                                <option value=6 <c:if test="${petVO.petCat==6}">selected</c:if>>�S�j����(20kg�H�W)</option>
                            </optgroup>
                            <optgroup label="��L">
                                <option value=0 <c:if test="${petVO.petCat==0}">selected</c:if>>��L</option>
                            </optgroup>
                        </select>
                        <c:if test="${not empty errorMsgs.petCat }">${errorMsgs.petCat }</c:if>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">�d��²��&nbsp;:</label>
                        <textarea class="form-control" placeholder="��100�r�H��" name="petChar"></textarea>
                        
                    </div>
                    <div class="form-group">
                        <input type="submit" value="�T�{�s�W" class="btn btn-primary">
                        <input type="hidden" name="action" value="insert<c:if test="${not empty reserve}">_forReserve</c:if>">
                        <input type="hidden" name="memNo" value="${memVO.memNo }">
                    </div>
                </div>
            </div>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
</body>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<style>
        .xdsoft_datetimepicker .xdsoft_datepicker {
            width: 300px;
            /* width:  300px; */
        }

        .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
            height: 151px;
            /* height:  151px; */
        }
</style>

<%
	java.sql.Date petBirth = null;
	try {
		petBirth = petVO.getPetBirth();
	} catch (Exception e) {
		petBirth = new java.sql.Date(System.currentTimeMillis());
	}
%>
<script>
        $.datetimepicker.setLocale('zh');
        $('#petBirth').datetimepicker({
            theme: '', //theme: 'dark',
            timepicker: false, //timepicker:true,
            step: 1, //step: 60 (�o�Otimepicker���w�]���j60����)
            format: 'Y-m-d', //format:'Y-m-d H:i:s',
            // value: ,             
            value: '',
            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
            //startDate:	            '2017/07/10',  // �_�l��
            //minDate:               '-1970-01-01', // �h������(���t)���e
            maxDate: '+1970-01-01' // �h������(���t)����
        });
</script>
<script>
        function loadImageFile(event) {
            var image = document.getElementById('image');
            image.src = URL.createObjectURL(event.target.files[0]);
        };
</script>

</html>