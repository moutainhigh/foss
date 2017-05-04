<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module  subModule="passOrderVehicle"/>
<script type="text/javascript">
	scheduling.paramOrderIdList= '${param.paramOrderIdList}';
	scheduling.orderVehicleIsLoadAll='${param.isLoadAll}';
</script>
<script type="text/javascript" src="${scripts}/passOrderVehicle.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_scheduling-passOrderVehicleIndex-body"></div>
	</div>
</body>