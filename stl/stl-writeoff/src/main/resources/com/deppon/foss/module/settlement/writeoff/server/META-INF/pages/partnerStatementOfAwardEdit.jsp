<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<%@ include file="../common/productType.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="partnerStatementOfAwardEdit" groups="partnerStatementOfAwardEdit"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	//tab 页查询常量设置--新增对账单明细，查询应收应付单
	writeoff.STATEMENTQUERYTAB_BYCUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
	writeoff.STATEMENTQUERYTAB_BYNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_BILL_NO"/>';//按单号查询
	
	//tab 页查询常量设置--对账单管理，查询对账单
	writeoff.STATEMENTQUERYTAB_BYPARTNER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
	writeoff.STATEMENTQUERYTAB_BYSTATEMENT_NO = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DZ_BILL_NO"/>';//按对账单查询
	writeoff.STATEMENTQUERYTAB_BYWAYBILL_NO = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_WAYBILL_NO"/>';//按运单号查询
	writeoff.STATEMENTQUERYTAB_BYDEPARTMENT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_FAILING_INVOICE"/>';//按部门查询
	
	//确认状态
	writeoff.STATEMENTCONFIRMSTATUS_Y = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM"/>';//确认
	writeoff.STATEMENTCONFIRMSTATUS_N = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM"/>';//未确认

	//结账状态
	writeoff.SETTLESTATUS_Y =  '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';//已结清
	writeoff.SETTLESTATUS_N =  '<s:property value="@com.deppon.foss.util.define.FossConstants@NO"/>';//未结清
	
	//付款方式
	writeoff.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	writeoff.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	
	//外部对公账号、外部对私账户
	writeoff.FIN_ACCOUNT_TYPE_PUBLIC = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PUBLIC"/>';
	writeoff.FIN_ACCOUNT_TYPE_PRIVATE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PRIVATE"/>';
	
	//来源单据编号 对账单
	writeoff.SOURCE_BILL_TYPE__STATEMENT='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT"/>';
</script>
</head>
<body>
<div id="partnerStatementOfAwardEditPanel">
    <div id="T_writeoff-partnerStatementOfAwardEdit-body"></div>
</div>
</body>
</html>