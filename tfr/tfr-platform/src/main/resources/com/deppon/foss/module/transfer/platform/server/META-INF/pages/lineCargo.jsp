<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="lineCargo" />

<script type="text/javascript">
	platform.lineCargo.transferCenterCode = '${requestScope.lineCargoVo.transferCenterCode}';
	platform.lineCargo.transferCenterName = '${requestScope.lineCargoVo.transferCenterName}';
</script>

<script type="text/javascript" src="${scripts}/lineCargo.js"></script>