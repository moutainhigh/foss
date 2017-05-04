<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="deliverloadtaskconfirm"/>
<script type="text/javascript">
	load.deliverloadtaskconfirm.deliverBillNo = ${param.deliverBillNo};
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/deliverloadtask_confirm.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_load-deliverloadtaskconfirmindex-body"></div>
	</div>
</body>
