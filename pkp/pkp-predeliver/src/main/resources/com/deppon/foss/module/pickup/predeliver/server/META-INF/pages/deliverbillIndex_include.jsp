<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<%@ include file="../predeliver/productInfo.jsp"%>
<ext:module subModule="deliverbill"/>
<link rel="stylesheet" type="text/css" href="${styles}/exceptionProcess.css">
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printDeliverIndex.js"></script>
<script type="text/javascript" src="${scripts}/printArriveSheet.js"></script>
<script type="text/javascript" src="${scripts}/deliverbillIndex.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_predeliver-deliverbillIndex-body"></div>
	</div>
</body>