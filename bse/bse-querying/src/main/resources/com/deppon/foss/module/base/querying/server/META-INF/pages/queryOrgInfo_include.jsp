<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../login/common-gui.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<spring:eval expression="@applicationProperties['gis.page.query']" var="gisPpageQuery"/> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body> 
		<iframe src="${gisPpageQuery}" height="870px" width="100%"></iframe> 
</body>
</html>