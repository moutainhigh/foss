<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="handoverbilladdnew"/>
<script type="text/javascript">
	load.handoverbilladdnew.handOverBillNo = '${requestScope.handOverBillVo.handOverBillNo}';
	load.handoverbilladdnew.departOrgName = '${requestScope.handOverBillVo.departOrgName}';
	load.handoverbilladdnew.beSalesDept = '${requestScope.handOverBillVo.beSalesDept}';
	load.handoverbilladdnew.superOrgCode = '${requestScope.handOverBillVo.superOrgCode}';
	load.handoverbilladdnew.rate='${requestScope.handOverBillVo.expressConvertParameter}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbill_addnew.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_load-handoverbilladdnewindex-body"></div>
	</div>
</body>