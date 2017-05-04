<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="salesDeptExpLost" />
<script type="text/javascript">
	var deptCode = '${requestScope.stockVO.stockOrgCode}';
	var deptName = '${requestScope.stockVO.stockOrgName}'
</script>
<script type="text/javascript" src="${scripts}/salesDeptExpLost.js"></script>