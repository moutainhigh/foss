<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<%@ include file="../common/productType.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="partnerStatementOfAwardAdd" groups="partnerStatementOfAwardAdd"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	//tab 页查询常量设置
	writeoff.STATEMENTQUERYTAB_BYCUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
	writeoff.STATEMENTQUERYTAB_BYNUMBER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_BILL_NO"/>';//按单号查询
	//是否统一结算
    writeoff.RECEIVABLEUNIFORM_Y = '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';
    writeoff.RECEIVABLEUNIFORM_N = '<s:property value="@com.deppon.foss.util.define.FossConstants@NO"/>';
    //单据类型
    writeoff.STATEMENTDETAIL_RECEIVABLE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE"/>';
    writeoff.STATEMENTDETAIL_PAYABLE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE"/>';
    writeoff.STATEMENT_OPERATE_DELETE = 'delete';//删除明细
</script>
</head>
<body>
<div id="mainAreaPanel">
    <div id="T_writeoff-partnerStatementOfAwardAdd-body"></div>
</div>
</body>
</html>