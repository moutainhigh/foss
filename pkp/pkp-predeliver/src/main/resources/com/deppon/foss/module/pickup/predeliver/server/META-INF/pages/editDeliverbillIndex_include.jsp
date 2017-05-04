<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ include file="../login/common.jsp" %>
<%@include file="../predeliver/productInfo.jsp"%>
<ext:module subModule="editDeliverbill"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript">
	predeliver.editDeliverbill.deliverbillId = '${deliverbillVo.deliverbill.id}';
	predeliver.editDeliverbill.deliverbillNo = '${deliverbillVo.deliverbill.deliverbillNo}';
	predeliver.editDeliverbill.status = '${deliverbillVo.deliverbill.status}';
</script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
<script type="text/javascript" src="${scripts}/editDeliverbillIndex.js"></script>

<body>
	<div id="mainAreaPanel">
		<div id="T_predeliver-editDeliverbillIndex-body"></div>
	</div>
</body>