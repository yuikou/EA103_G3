<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
<h6>�D���</h6>
<ul>
<form method="post" action="<%=request.getContextPath()%>/back-end/salon/salon.do" name="form1">
<li><a href="javascript:document.form1.submit();">�ݼf�ֲM��</a></li>
<input type="hidden"name="action" value="unAcceptList">
</form>

</ul>

</body>
</html>