<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<%@ include file="../common/productType.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="trunkedDiscountManagement" groups="trunkedDiscountManagement"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<style type="text/css">

.x-window .x-grid .x-toolbar-docked-bottom {
	background-color: #F5F5F5
}
</style>
<script type="text/javascript">
//tab 页查询常量设置
	pay.STATEMENTQUERYTAB_BYCUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
	pay.STATEMENTQUERYTAB_BYNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_BILL_NO"/>';//按单号查询 
</script>
</head>
<body>
	<div id="mainAreaPanel">
		<div id="T_pay-trunkedDiscountManagement-body"></div>
	</div>
</body>
</html>