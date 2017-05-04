<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@taglib prefix="s" uri="/struts-tags"%>

<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<ext:module subModule="cashCashierConfirm" groups="cashCashierConfirm"/>
<script type="text/javascript">
	//来源单据编号  现金收款单、预收、还款单
	pay.cashCashierConfirm.BILL_TYPE__CASH_COLLECTION='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION"/>';
	pay.cashCashierConfirm.BILL_TYPE__DEPOSIT_RECEIVED='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED"/>';
	pay.cashCashierConfirm.BILL_TYPE__REPAYMENT='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT"/>';
	
	//收款方式
	pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	pay.cashCashierConfirm.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	
	pay.cashCashierConfirm.billingGroup = '<s:property value="billingGroup"/>'
	 
</script>

<body>
<div id="mainAreaPanel">
		<div id="T_pay-cashCashierConfirm-body"></div>
	</div>
</body>