<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="goodsDistributionIndex"/>
<script type="text/javascript">
	platform.goodsDistributionIndex.outfieldCode = '${requestScope.goodsDistributionVo.transferCenterCode}';
	platform.goodsDistributionIndex.outfieldName = '${requestScope.goodsDistributionVo.transferCenterName}';
	platform.goodsDistributionIndex.operationDeptCode = '${requestScope.goodsDistributionVo.operationDeptCode}';
	platform.goodsDistributionIndex.operationDeptName = '${requestScope.goodsDistributionVo.operationDeptName}';
</script>
<script type="text/javascript" src="${scripts}/goodsDistributionIndex.js"></script>