<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="statementCommon.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="partnerPayStatementEdit" groups="partnerPayStatementEdit"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
 </head>
<body>
</body>
<script type="text/javascript">
//外部对公账号、外部对私账户
writeoff.FIN_ACCOUNT_TYPE_PUBLIC = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PUBLIC"/>';
writeoff.FIN_ACCOUNT_TYPE_PRIVATE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@FIN_ACCOUNT_TYPE_PRIVATE"/>';
//来源单据编号 对账单
writeoff.SOURCE_BILL_TYPE__STATEMENT='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT"/>';
writeoff.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
</script>
</html>