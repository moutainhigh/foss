<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="confirmDeliverbill"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript">
	predeliver.confirmDeliverbill.deliverbillId = '${deliverbillVo.deliverbill.id}';
	predeliver.confirmDeliverbill.createType = '${deliverbillVo.deliverbill.createType}';
</script>
<script type="text/javascript" src="${scripts}/confirmDeliverbillIndex.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
