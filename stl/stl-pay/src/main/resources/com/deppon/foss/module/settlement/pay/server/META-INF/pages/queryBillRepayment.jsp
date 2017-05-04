<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="repayment" groups="queryRepayment"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">

	//获取还款方式 现金  银行卡 电汇 支票
	pay.repayment.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	pay.repayment.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	pay.repayment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	pay.repayment.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	pay.repayment.SETTLEMENT__PAYMENT_TYPE__ONLINE= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__ONLINE"/>';
	pay.repayment.CLAIMSWAY_CLAIMS_OUT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@CLAIMSWAY_CLAIMS_OUT"/>';

	//付款单审核状态：未审核、已审核
	pay.repayment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT"/>';
	pay.repayment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE"/>';

	//是否红单：红单
	pay.repayment.SETTLEMENT__IS_RED_BACK__YES = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__IS_RED_BACK__YES"/>';

	//是否有效：无效
	pay.repayment.INACTIVE = '<s:property value="@com.deppon.foss.util.define.FossConstants@INACTIVE"/>';
</script>
<!--script type="text/javascript" src="${scripts}/queryBillRepayment.js">
</script-->
</head>
<body>
</body>
</html>