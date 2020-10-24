<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "com.pet.model.*,com.member.model.*,com.salon.model.*,com.salsev.model.*, com.grm.model.*, com.goffday.model.*, java.util.*" %>

<%
String salNo = session.getAttribute("salNo").toString();
String memNo = session.getAttribute("memNo").toString();
String offDay = session.getAttribute("offDay").toString();
String offTime = session.getAttribute("offTime").toString();

SalsevVO mySalSev = (SalsevVO)session.getAttribute("mySalSev");
GrmVO myGrm = (GrmVO)session.getAttribute("myGrm");

MemService memSvc = new MemService();
MemVO memVO = memSvc.getOneMem(memNo);

SalonService salSvc = new SalonService();
SalonVO salonVO = salSvc.getonesalon(salNo);
PetService petSvc = new PetService();
List<PetVO> petList = petSvc.getAllPet(memNo);

pageContext.setAttribute("salonVO",salonVO);
pageContext.setAttribute("memVO",memVO);
pageContext.setAttribute("petList", petList);

%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Insert title here</title>
 <style type="text/css" media="screen">
        table {
            border: 1px solid black;
            width: 500px;
            height: 500px;
            margin-right: auto;
            margin-left: auto;
        }
        td.title {
            width: 100px;
        }
    </style>
</head>
<body>

<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
    <div id="container">
        <form method="post" action="<%=request.getContextPath()%>/front-end/salon/salonOrder.do">
        <table >
            <tr>
            
                <th colspan="2">
                    <h1>訂單確認</h1>
                </th>
            </tr>
            <tr>
                <td class="title"><label>美容店</label></td>
                <td>${salonVO.salName}</td>
            </tr>
            <tr>
                <td class="title"><label>美容師</label></td>
                <td>${myGrm.groomerName}</td>
            </tr>
            <tr>
                <td class="title"><label>寵物名稱</label></td>
                <td><select name="petNo">
							<c:forEach var="petVO" items="${petList}">
								<c:if test="${petVO.petCat == mySalSev.petcat}">
								<option value="${petVO.petNo}">${petVO.petName}</option>
								</c:if>
							</c:forEach>
					</select></td>
            </tr>
            <tr>
                <td class="title"><label>預約服務名稱</label></td>
                <td>${mySalSev.salsevname}</td>
            </tr>
            <tr>
                <td class="title"><label>預約服務內容</label></td>
                <td>${mySalSev.salSevInfo}</td>
            </tr>
            <tr>
				<td class="title"><label>預約時間</label></td>
				<td>${offDay} ${offTime}</td>
            </tr>
            <tr>
            	<td class="title"><label>價格</label></td>
				<td>${mySalSev.salsevpr}</td>
            </tr>
            <tr>
                <td colspan="2"><button id="btn">確認付款</button></td>              
            </tr>
        </table>
         <input type="hidden" name="action" value="insert">
         
         <input type="hidden" name="memNo" value="${memNo}">
<!--          <input type="hidden" name="petNo" value="P00001">  -->
         <input type="hidden" name="salNo" value="${salNo}">
         <input type="hidden" name="salTp" value="${mySalSev.salsevpr}"> 
         <input type="hidden" name="orderStatus" value="0">                  
         <input type="hidden" name="salSevNo" value="${mySalSev.salsevno}"> 
         <input type="hidden" name="groomerNo" value="${myGrm.groomerNo }">
         <input type="hidden" name="salSevPr" value="${mySalSev.salsevpr}">         
        </form>
    </div>
</body>
</html>