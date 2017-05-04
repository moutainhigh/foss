<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${scripts}/../print/print.js"></script>

<ext:module subModule="queryCashPaymentReport" groups="queryCashPaymentReport"/>
<script type="text/javascript">
	//获取后台数据字典的值
	//付款单来源单据类型:应付、预收
	consumer.queryCashPaymentReport.YF = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT"/>';
	consumer.queryCashPaymentReport.YS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED"/>';
	
</script>
</head>
<body>
</body>
</html>