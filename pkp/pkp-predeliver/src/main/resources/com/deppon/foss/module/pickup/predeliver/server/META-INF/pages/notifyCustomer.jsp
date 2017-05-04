<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@include file="productInfo.jsp"%>
<ext:module subModule="notifyCustomer"/>
<script type="text/javascript">
	predeliver.notifyCustomer.warehouseFreeSafeDataNum = ${vo.warehouseFreeSafeDataNum};
</script>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript" src="${scripts}/notifyCustomer.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printArriveSheet.js"></script>