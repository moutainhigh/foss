<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="statementCommon.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="statementEdit" groups="statementEdit"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	if(!Ext.isEmpty(${param.ddw})){
		writeoff.statementEdit.ddw ='ddw';
	}else{
		writeoff.statementEdit.ddw = 'br';
	}
</script>
<%-- <script type="text/javascript" src="${scripts}/statementEdit.js"></script>--%>
 </head>
<body>
</body>
</html>