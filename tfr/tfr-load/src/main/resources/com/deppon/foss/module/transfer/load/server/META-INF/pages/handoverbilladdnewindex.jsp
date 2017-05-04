<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="handoverbilladdnew"/>
<script type="text/javascript">
	load.handoverbilladdnew.handOverBillNo = '${requestScope.handOverBillVo.handOverBillNo}';
	load.handoverbilladdnew.departOrgName = '${requestScope.handOverBillVo.departOrgName}';
	load.handoverbilladdnew.beSalesDept = '${requestScope.handOverBillVo.beSalesDept}';
	load.handoverbilladdnew.superOrgCode = '${requestScope.handOverBillVo.superOrgCode}';
	load.handoverbilladdnew.beDivision='${requestScope.handOverBillVo.beDivision}';
	load.handoverbilladdnew.rate='${requestScope.handOverBillVo.expressConvertParameter}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbill_addnew.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
