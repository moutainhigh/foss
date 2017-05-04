<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="tfrCtrPersonnelBudget" />

<script type="text/javascript">
	platform.tfrCtrPersonnelBudget.parentTfrCtrCode = '${requestScope.tfrCtrPersonnelBudgetVo.parentTfrCtrCode}';
	platform.tfrCtrPersonnelBudget.parentTfrCtrName = '${requestScope.tfrCtrPersonnelBudgetVo.parentTfrCtrName}';

	platform.tfrCtrPersonnelBudget.downloadTemplate = function(){
		var path = "${pageContext.request.contextPath}/download/外场人员预算导入模版.xlsx";
		window.open(path);
	}
	
</script>

<script type="text/javascript" src="${scripts}/tfrCtrPersonnelBudget.js"></script>