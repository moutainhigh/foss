<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<%@include file="productInfo.jsp"%>
<ext:module subModule="arriveNotice" groups="arriveNotice"/>
<link rel="stylesheet" type="text/css" href="${styles}/predeliver.css">
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/printArriveSheet.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_predeliver-arriveNotice-body"></div>
	</div>
</body>
