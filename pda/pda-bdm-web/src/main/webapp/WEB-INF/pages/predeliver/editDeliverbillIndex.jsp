<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="editDeliverbill"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript">
	predeliver.editDeliverbill.deliverbillId = '${deliverbillVo.deliverbill.id}';
	predeliver.editDeliverbill.deliverbillNo = '${deliverbillVo.deliverbill.deliverbillNo}';
</script>
<script type="text/javascript" src="${scripts}/editDeliverbillIndex.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
</head>
<body>
</body>
</html>