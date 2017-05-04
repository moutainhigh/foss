<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<ext:module subModule="billDepositReceivedAdd" groups="billDepositReceivedAdd"/>
<script type="text/javascript">
	//收款方式
	pay.billDepositReceivedAdd.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	pay.billDepositReceivedAdd.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	pay.billDepositReceivedAdd.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	pay.billDepositReceivedAdd.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	pay.billDepositReceivedAdd.SETTLEMENT__PAYMENT_TYPE__ONLINE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__ONLINE"/>';
	
	
	
	//运输类型
	pay.billDepositReceivedAdd.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER"/>';
	pay.billDepositReceivedAdd.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY"/>';
	pay.billDepositReceivedAdd.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY"/>';
</script>