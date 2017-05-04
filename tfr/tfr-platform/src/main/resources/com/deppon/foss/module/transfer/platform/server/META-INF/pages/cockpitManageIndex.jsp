<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
 <ext:module subModule="cockpitManageIndex"/>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
	var deptName = '${requestScope.cockpitVo.tfrCtrName}';
	var deptCode = '${requestScope.cockpitVo.tfrCtrCode}';
</script>
<script type="text/javascript" src="${scripts}/cockpitmanageIndex.js"></script>
</head>
<body>

</body>
</html>