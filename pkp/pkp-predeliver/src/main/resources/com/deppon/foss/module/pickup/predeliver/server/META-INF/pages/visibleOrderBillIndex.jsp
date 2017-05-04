<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@include file="../predeliver/productInfo.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<ext:module subModule="visibleOrderBill"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<link rel="stylesheet" type="text/css" href="${styles}/exceptionProcess.css">
<script type="text/javascript">
	predeliver.visibleOrderBill.deliverbillId = '${vo.deliverbill.id}';
	predeliver.visibleOrderBill.deliverbillNo = '${vo.deliverbill.deliverbillNo}';
	predeliver.visibleOrderBill.status = '${vo.deliverbill.status}';
	
</script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
<script type="text/javascript" src="${scripts}/visibleAutoSort.js"></script>
<script type="text/javascript" src="${scripts}/visibleOrderBill.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yhG8AhZxIGy2K41SPUZ1x4fQ"></script>
<spring:eval expression="@applicationProperties['gis.js.map']" var="gisjs"/> 
<script type="text/javascript" src="${gisjs}"></script> 
<body>
	<div id="mainAreaPanel">
		<div id="T_predeliver-visibleOrderBillIndex-body"></div>
	</div>
</body>