<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="handoverbillsalemodify"/>
<script type="text/javascript">
	load.handoverbillsalemodify.handOverBillNo = ${param.handOverBillNo}
	load.handoverbillsalemodify.beSalesDept = '${requestScope.handOverBillVo.beSalesDept}';
	load.handoverbillsalemodify.beDivision='${requestScope.handOverBillVo.beDivision}';
	load.handoverbillsalemodify.rate='${requestScope.handOverBillVo.expressConvertParameter}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbillsale_modify.js"></script>