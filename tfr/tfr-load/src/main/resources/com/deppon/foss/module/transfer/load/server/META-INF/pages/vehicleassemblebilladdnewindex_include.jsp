<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="vehicleassemblebilladdnew"/>
<script type="text/javascript">
	load.vehicleassemblebilladdnew.comeFromHandOverBillQuery = '${param.comeFromHandOverBillQuery}';
</script>
<script type="text/javascript" src="${scripts}/load.js"></script>
<script type="text/javascript" src="${scripts}/vehicleassemblebill_addnew.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_load-vehicleassemblebilladdnewindex"></div>
	</div>
</body>

