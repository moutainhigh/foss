<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="homeStatements" groups="homeStatements"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	//确认、反确认
	writeoff.STATEMENTCONFIRMSTATUS_Y = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM"/>';
	writeoff.STATEMENTCONFIRMSTATUS_N = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM"/>';
	//tab 页查询常量设置
	writeoff.STATEMENTQUERYTAB_BYCUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
	writeoff.STATEMENTQUERYTAB_BYNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_BILL_NO"/>';//按单号查询
	//付款方式
	writeoff.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	writeoff.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	writeoff.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	//单据类型
	writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_HOME_ACCOUNT"/>';
	writeoff.STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_HOME_ACCOUNT"/>';
	//外部对公账号、外部对私账户
	writeoff.FIN_ACCOUNT_TYPE_PUBLIC = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PUBLIC"/>';
	writeoff.FIN_ACCOUNT_TYPE_PRIVATE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PRIVATE"/>';
	//来源单据编号 对账单
	writeoff.SOURCE_BILL_TYPE__STATEMENT='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT"/>';
	//结账状态
	writeoff.SETTLESTATUS_Y =  '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';//已结清
	writeoff.SETTLESTATUS_N =  '<s:property value="@com.deppon.foss.util.define.FossConstants@NO"/>';//未结清
</script>
</head>
<body>
<div id="mainAreaPanel">
    <div id="T_writeoff-homeStatements-body"></div>
</div>
</body>
</html>