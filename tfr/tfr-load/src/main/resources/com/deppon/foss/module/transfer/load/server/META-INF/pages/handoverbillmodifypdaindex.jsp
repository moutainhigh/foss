<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="handoverbillmodifypda"/>
<script type="text/javascript">
	load.handoverbillmodifypda.handOverBillNo = ${param.handOverBillNo}
	load.handoverbillmodifypda.bePackage = ${param.bePackage}
	load.handoverbillmodifypda.beSalesDept = '${requestScope.handOverBillVo.beSalesDept}';
	load.handoverbillmodifypda.beDivision='${requestScope.handOverBillVo.beDivision}';
	load.handoverbillmodifypda.rate='${requestScope.handOverBillVo.expressConvertParameter}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbill_modify_pda.js"></script>