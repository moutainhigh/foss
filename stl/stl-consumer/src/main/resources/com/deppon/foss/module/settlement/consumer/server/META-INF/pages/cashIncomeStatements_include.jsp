<%@ page language="java" pageEncoding="UTF-8"%> 
<%@taglib uri="/ext" prefix="ext" %>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<%@ include file="../common/productType.jsp" %>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<ext:module subModule="cashIncomeStatements" groups="cashIncomeStatements"/> 
<!-- script type="text/javascript" src="${scripts}/cashIncomeStatements.js"></script-->
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">

	//获取还款方式 现金  银行卡 电汇 支票
	consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	consumer.cashIncomeStatements.SETTLEMENT__PAYMENT_TYPE__ONLINE= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__ONLINE"/>';
</script>

<body>
	<div id="mainAreaPanel">
		<div id="T_consumer-queryCashIncomeStatements-body"></div>
	</div>
</body>
