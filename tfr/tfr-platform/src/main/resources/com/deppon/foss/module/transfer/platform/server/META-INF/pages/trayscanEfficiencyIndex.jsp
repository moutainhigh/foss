<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="trayscanEfficiencyIndex"/>
<script type="text/javascript">
	platform.trayscanEfficiencyIndex.outfieldCode = '${requestScope.trayscanEfficiencyVo.tfrCtrCode}';
	platform.trayscanEfficiencyIndex.outfieldName = '${requestScope.trayscanEfficiencyVo.tfrCtrName}';
	platform.trayscanEfficiencyIndex.operationDeptCode = '${requestScope.trayscanEfficiencyVo.hqCode}';
	platform.trayscanEfficiencyIndex.operationDeptName = '${requestScope.trayscanEfficiencyVo.hqName}';
</script>
<script type="text/javascript" src="${scripts}/trayscanEfficiencyIndex.js"></script>