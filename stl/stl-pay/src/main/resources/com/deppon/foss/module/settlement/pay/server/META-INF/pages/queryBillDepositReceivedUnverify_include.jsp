<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<ext:module subModule="depositReceivedUnverify" groups="depositReceivedUnverify"/>
<script type="text/javascript">
	//运输类型
	pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER"/>';
	pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY"/>';
	pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY"/>';
	pay.depositReceivedUnverify.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE"/>';
</script>
<body>
	<div id="mainAreaPanel">
		<div id="T_pay-queryBillDepositReceivedUnverify-body"></div>
	</div>
</body>