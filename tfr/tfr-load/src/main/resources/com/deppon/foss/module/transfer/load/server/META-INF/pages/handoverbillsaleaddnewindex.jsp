<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="handoverbillsaleaddnew"/>
<script type="text/javascript">
	load.handoverbillsaleaddnew.handOverBillNo = '${requestScope.handOverBillVo.handOverBillNo}';
	load.handoverbillsaleaddnew.departOrgName = '${requestScope.handOverBillVo.departOrgName}';
	load.handoverbillsaleaddnew.beSalesDept = '${requestScope.handOverBillVo.beSalesDept}';
	load.handoverbillsaleaddnew.superOrgCode = '${requestScope.handOverBillVo.superOrgCode}';
	load.handoverbillsaleaddnew.beDivision='${requestScope.handOverBillVo.beDivision}';
	load.handoverbillsaleaddnew.rate='${requestScope.handOverBillVo.expressConvertParameter}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbillsale_addnew.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
