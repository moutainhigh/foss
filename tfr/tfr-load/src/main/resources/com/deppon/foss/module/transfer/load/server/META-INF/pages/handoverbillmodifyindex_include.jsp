<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="handoverbillmodify"/>
<script type="text/javascript">
	load.handoverbillmodify.handOverBillNo = ${param.handOverBillNo}
	load.handoverbillmodify.beSalesDept = '${requestScope.handOverBillVo.beSalesDept}';
	load.handoverbillmodify.rate='${requestScope.handOverBillVo.expressConvertParameter}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbill_modify.js"></script>
<body>
<div id="mainAreaPanel">
<div id="T_load-handoverbillmodifyindex-body"></div>
</div>
</body>
