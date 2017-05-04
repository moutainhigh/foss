<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="unloadtaskconfirm" />
<script type="text/javascript">
	unload.unloadtaskconfirm.unloadTaskId = ${param.unloadTaskId}
</script>
<link rel="stylesheet" type="text/css" href="${styles}/plugingridgap.css">
<script type="text/javascript" src="${scripts}/unloadtask_confirm.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_unload-unloadtaskconfirmindex-body"></div>
	</div>
</body>
