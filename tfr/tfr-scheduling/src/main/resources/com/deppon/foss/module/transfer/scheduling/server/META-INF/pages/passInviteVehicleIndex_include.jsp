<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module  subModule="passInviteVehicle"/>
<script type="text/javascript">
	scheduling.paramInviteNoList= '${param.paramInviteNoList}';
	scheduling.inviteVehicleIsLoadAll='${param.inviteVehicleIsLoadAll}';
</script>
<script type="text/javascript" src="${scripts}/passInviteVehicleIndex.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_scheduling-passInviteVehicleIndex-body"></div>
	</div>
</body>
