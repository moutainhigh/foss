<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="noUnloadingProgressDetail"/>
<script type="text/javascript" src="${scripts}/noUnloadingProgressDetail.js"></script>
<script type="text/javascript">
	platform.noUnloadingProgressDetail.crurrentTaskId = '${requestScope.queryUnloadingProgressVo.taskId}';
</script>
</head>
<body>
	<div id="mainNoUnloadingPanel">
		<div id="T_platform-noUnloadingProgressDetail-body"></div>
	</div>
</body>
</html>