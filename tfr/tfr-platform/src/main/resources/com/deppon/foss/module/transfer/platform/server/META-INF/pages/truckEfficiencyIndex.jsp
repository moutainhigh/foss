<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="truckEfficiencyIndex"/>
<script type="text/javascript">
	platform.truckEfficiencyIndex.outfieldCode = '${requestScope.truckEfficiencyVo.transferCenterCode}';
	platform.truckEfficiencyIndex.outfieldName = '${requestScope.truckEfficiencyVo.transferCenterName}';
	platform.truckEfficiencyIndex.operationDeptCode = '${requestScope.truckEfficiencyVo.operationDeptCode}';
	platform.truckEfficiencyIndex.operationDeptName = '${requestScope.truckEfficiencyVo.operationDeptName}';
</script>
<script type="text/javascript" src="${scripts}/truckEfficiencyIndex.js"></script>