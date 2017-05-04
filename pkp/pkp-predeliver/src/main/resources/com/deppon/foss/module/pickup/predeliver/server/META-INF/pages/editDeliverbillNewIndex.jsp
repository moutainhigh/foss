<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ include file="productInfo.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<ext:module subModule="editDeliverbillNew"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript">
    predeliver.editDeliverbillNew.waybillDetail_deliverbillId = '${vo.deliverbill.id}';
    predeliver.editDeliverbillNew.deliverbillNo = '${vo.deliverbill.deliverbillNo}';
    predeliver.editDeliverbillNew.status = '${vo.deliverbill.status}';

</script>
<spring:eval expression="@applicationProperties['gis.js.map']" var="gisjs"/> 
<script type="text/javascript" src="${gisjs}"></script> 
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/visibleAutoSort.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
<script type="text/javascript" src="${scripts}/editDeliverbillNewIndex.js"></script>
<html>
<body>
</body>
</html>