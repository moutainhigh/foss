<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="forkliftGoodsEfficiencyIndex"/>
<script type="text/javascript">
	platform.forkliftGoodsEfficiencyIndex.outfieldCode = '${requestScope.forkliftGoodsEfficiencyVo.transferCenterCode}';
	platform.forkliftGoodsEfficiencyIndex.outfieldName = '${requestScope.forkliftGoodsEfficiencyVo.transferCenterName}';
	platform.forkliftGoodsEfficiencyIndex.operationDeptCode = '${requestScope.forkliftGoodsEfficiencyVo.operationDeptCode}';
	platform.forkliftGoodsEfficiencyIndex.operationDeptName = '${requestScope.forkliftGoodsEfficiencyVo.operationDeptName}';
</script>
<script type="text/javascript" src="${scripts}/forkliftGoodsEfficiencyIndex.js"></script>