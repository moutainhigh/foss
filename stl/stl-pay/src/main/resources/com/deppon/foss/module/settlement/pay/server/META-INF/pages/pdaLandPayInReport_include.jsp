<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="pdaPayInReport" groups="pdaPayInReport"/>
<%-- <script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/pdaPayInReport.js"> </script>--%>
<style type="text/css">
	.myRowRed .x-grid-cell{ 
		background-color:red;
	}
</style>
</head>
<body>
<div id="mainAreaPanel">
		<div id="T_pay-pdaLandPayInReport-body"></div>
	</div>
</body>
</html>