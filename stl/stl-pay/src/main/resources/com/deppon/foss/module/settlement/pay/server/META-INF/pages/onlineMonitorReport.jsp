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
<ext:module subModule="onlineMonitorReport" groups="onlineMonitorReport"/>
<script type="text/javascript">
	//按日期查询tab
	pay.onlineMonitorReport.TAB_QUERY_BY_DATE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';
	//按运单号查询tab
	pay.onlineMonitorReport.TAB_QUERY_BY_WAYBILL_NO ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_WAYBILL_NO"/>';
	//按对账单号查询tab
	pay.onlineMonitorReport.TAB_QUERY_BY_DZ_BILL_NO ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DZ_BILL_NO"/>';
</script>
<%-- <script type="text/javascript" src="${scripts}/onlineMonitorReport.js">
</script> --%>
</head>
<body>
</body>
</html>