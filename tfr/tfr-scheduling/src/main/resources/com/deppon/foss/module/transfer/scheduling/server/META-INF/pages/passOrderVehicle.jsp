<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="passOrderVehicle" />
<script type="text/javascript">
	scheduling.passOrderVehicle.paramOrderIdList= '${param.paramOrderIdList}';
	scheduling.passOrderVehicle.orderVehicleIsLoadAll='${param.isLoadAll}';
</script>
<script type="text/javascript" src="${scripts}/passOrderVehicle.js"></script>