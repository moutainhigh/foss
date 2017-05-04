<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="cargoArrived" />

<script type="text/javascript">
	platform.cargoArrived.transferCenterCode = '${requestScope.cargoArrivedVo.transferCenterCode}';
	platform.cargoArrived.transferCenterName = '${requestScope.cargoArrivedVo.transferCenterName}';
</script>

<script type="text/javascript" src="${scripts}/cargoArrived.js"></script>