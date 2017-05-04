<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="addPayment.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<ext:module subModule="depositReceived" groups="depositReceived"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	//收款方式
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__ONLINE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__ONLINE"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__DEST_ADVANCE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__DEST_FEE ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__JTU = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN"/>';
	pay.depositReceived.SETTLEMENT__PAYMENT_TYPE__KTU ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN"/>';
	//运输类型
	pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER"/>';
	pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY"/>';
	pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY"/>';
	pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE"/>';
	pay.depositReceived.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_PARTNER ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTNER"/>';
</script>