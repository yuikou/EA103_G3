<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sitOrder.model.*, java.util.*, com.sitODetail.model.*, com.sitSrv.model.*, com.pet.model.*" %>
<% SitODetailVO sitODetailVO = (SitODetailVO) request.getAttribute("sitODetailVO"); %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- 瀏覽器版本相容性 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>訂單詳情頁面</title>
</head>

<body>
    <!-- 內文開始 -->
    <div class="container">
        <h3 class="display-5">訂單細項</h3>
        <table class="tableBox_D">
            <thead>
                <tr class="thBox_D">
                    <th>訂單編號</th>
                    <th>服務項目</th>
                    <th>毛小孩</th>
                    <th>服務項目價格</th>
                    <th>服務項目(數目)</th>
                    <th>服務項目(單位)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <%=sitODetailVO.getSitOrderNo()%>
                    </td>
                 <% 
                   SitSrvService sitSrvSrv = new SitSrvService();
                   SitSrvVO sitSrvVO = sitSrvSrv.get_OneSit_OneSrv(sitODetailVO.getSitSrvNo());
                   pageContext.setAttribute("sitSrvVO", sitSrvVO);
                 %>   
                    <td>
                        ${sitSrvVO.sitSrvName}
                    </td>
                 <%
                   PetService petSrv = new PetService();
                   List<PetVO> petList = petSrv.getAll();
                   pageContext.setAttribute("petList", petList);
                 %>   
                    <td>
                        <c:forEach var="petVO" items="${petList}">
                          <c:if test="${sitODetailVO.petNo==petVO.petNo}">
                            ${petVO.petName}
                          </c:if>
                        </c:forEach>
                    
                    </td>
                    <td>
                        $ <%=sitODetailVO.getSitOpPrice()%>
                    </td>
                    <td>
                        <%=sitODetailVO.getSitSrvTimes()%>
                    </td>
                    <td>
                        <%=sitODetailVO.getSitSrvUnit()%>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- 內文end -->
    <!-- footer -->
</body>

</html>