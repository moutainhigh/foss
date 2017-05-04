<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module/>
<script type="text/javascript">
	stockchecking.tranferCode = '${requestScope.stockcheckingVO.transferCode}';
	stockchecking.isBasDept = '${requestScope.stockcheckingVO.isBasDept}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/stockchecking.css">
<script type="text/javascript" src="${scripts}/stTask.js"></script>
</head>
<body>
</body>
</html>