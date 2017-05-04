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
<ext:module subModule="queryDisableRepayment" groups="queryDisableRepayment"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">

	//获取还款方式 现金  银行卡 电汇 支票
	pay.querydisablerepayment.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	pay.querydisablerepayment.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	pay.querydisablerepayment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	pay.querydisablerepayment.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';

	//付款单审核状态：未审核、已审核
	pay.querydisablerepayment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT"/>';
	pay.querydisablerepayment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE"/>';

	//是否红单：红单
	pay.querydisablerepayment.SETTLEMENT__IS_RED_BACK__YES = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__IS_RED_BACK__YES"/>';

	//是否有效：无效
	pay.querydisablerepayment.INACTIVE = '<s:property value="@com.deppon.foss.util.define.FossConstants@INACTIVE"/>';
</script>
</head>
<body>
<div id="mainAreaPanel">
		<div id="T_pay-queryDisableBillRepayment-body"></div>
	</div>
</body>
</html>