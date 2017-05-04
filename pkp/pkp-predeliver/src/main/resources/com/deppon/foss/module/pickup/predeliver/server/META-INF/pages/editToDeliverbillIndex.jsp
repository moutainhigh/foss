<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@include file="../predeliver/productInfo.jsp"%>

<ext:module subModule="editToDeliverbill"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript">
	predeliver.editToDeliverbill.deliverbillId = '${deliverbillVo.deliverbill.id}';
	predeliver.editToDeliverbill.deliverbillNo = '${deliverbillVo.deliverbill.deliverbillNo}';
</script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
<script type="text/javascript" src="${scripts}/editToDeliverbillIndex.js"></script>