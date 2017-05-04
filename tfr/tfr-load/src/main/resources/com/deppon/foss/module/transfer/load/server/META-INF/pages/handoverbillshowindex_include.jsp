<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="handoverbillshow"/>
<script type="text/javascript">
	load.handoverbillshow.handOverBillNo = ${param.handOverBillNo}
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/handoverbill_show.js"></script>
<body>
<div id="mainAreaPanel">
<div id="T_load-handoverbillshowindex-body"></div>
</div>
</body>
