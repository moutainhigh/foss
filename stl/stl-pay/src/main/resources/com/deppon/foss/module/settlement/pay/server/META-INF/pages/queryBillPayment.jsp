<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="payment" groups="payment"/>
<script type="text/javascript">
	//获取后台数据字典的值
	
	//付款单生成方式：系统生成
	pay.payment.SETTLEMENT__CREATE_TYPE__AUTO = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CREATE_TYPE__AUTO"/>';
	
	//付款单汇款状态：未汇款、汇款中、已汇款
	pay.payment.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER"/>';
	pay.payment.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__REMIT_STATUS__TRANSFERRING"/>';
	pay.payment.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__REMIT_STATUS__TRANSFERRED"/>';
	
	//付款单审核状态：未审核、已审核
	pay.payment.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT"/>';
	pay.payment.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE"/>';
	
	//是否红单：红单
	pay.payment.SETTLEMENT__IS_RED_BACK__YES = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__IS_RED_BACK__YES"/>';
	
	//是否有效：无效
	pay.payment.INACTIVE = '<s:property value="@com.deppon.foss.util.define.FossConstants@INACTIVE"/>';
	
	//付款单汇款方式：现金、电汇、到付转预收、委托派费转预收、奖励应付自动返、快递差错应付自动返
	pay.payment.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	pay.payment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	pay.payment.SETTLEMENT__PAYMENT_TYPE__DEST_ADVANCE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US"/>';
	pay.payment.SETTLEMENT__PAYMENT_TYPE__DEST_FEE ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE"/>';
	pay.payment.SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN"/>';
	pay.payment.SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN"/>';

	
	//默认单号
	pay.payment.DEFAULT_BILL_NO = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DEFAULT_BILL_NO"/>';
	
	//付款单来源单据类型:应付、预收
	pay.payment.BILL_PREFIX_YF = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@BILL_PREFIX_YF"/>';
	pay.payment.BILL_PREFIX_US = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@BILL_PREFIX_US"/>';
	
	//外请车报销
	pay.payment.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants@COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY"/>';
	//是否自动冲借支
	pay.payment.AUTOABATEMENTLOAN_Y = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants@AUTOABATEMENTLOAN_Y"/>';
	pay.payment.AUTOABATEMENTLOAN_N = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants@AUTOABATEMENTLOAN_N"/>';
	//对接系统 --费控
	pay.payment.PAYTOSYSTEM_CONSCONTROL = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL"/>';
	pay.payment.PAYTOSYSTEM_TYPE_FSSC = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC"/>';

</script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<%-- <script type="text/javascript" src="${scripts}/queryBillPayment.js"> --%>
</script>
</head>
<body>
</body>
</html>