<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="forkliftStayDurationIndex"/>
<script type="text/javascript">
	platform.forkliftStayDurationIndex.outfieldCode = '${requestScope.forkliftGoodsEfficiencyVo.transferCenterCode}';
	platform.forkliftStayDurationIndex.outfieldName = '${requestScope.forkliftGoodsEfficiencyVo.transferCenterName}';
	platform.forkliftStayDurationIndex.operationDeptCode = '${requestScope.forkliftGoodsEfficiencyVo.operationDeptCode}';
	platform.forkliftStayDurationIndex.operationDeptName = '${requestScope.forkliftGoodsEfficiencyVo.operationDeptName}';
</script>
<script type="text/javascript" src="${scripts}/forkliftStayDurationIndex.js"></script>