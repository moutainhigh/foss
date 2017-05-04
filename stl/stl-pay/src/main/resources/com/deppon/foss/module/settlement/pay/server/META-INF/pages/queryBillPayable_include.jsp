<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/productType.jsp" %>
<%@ include file="addPayment.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="payable" groups="payable"/>
<script type="text/javascript">
	//获取后台数据字典的值  审核、反审核
	pay.payable.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE"/>';
	pay.payable.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT"/>';
	//应付单付款状态
	pay.payable.BILL_PAYABLE__PAY_STATUS__YES = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__PAY_STATUS__YES"/>';
	//应付代收货款
	pay.payable.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD"/>';
	pay.payable.AUDIT_OR_UNAUDIT_TYPES =[
		'<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST"/>',                             
		'<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST"/>',
		'<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST"/>',
		'<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST"/>'
		]; 
	//不合法的审核状态
	pay.payable.BILL_PAYABLE__BILL_TYPE__AIR_OTHER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__AIR_OTHER"/>',                             
	pay.payable.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE"/>',    
	pay.payable.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__SERVICE_FEE"/>',    
	pay.payable.BILL_PAYABLE__BILL_TYPE__CLAIM = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__CLAIM"/>',    
	pay.payable.BILL_PAYABLE__BILL_TYPE__REFUND = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__REFUND"/>',    
	pay.payable.BILL_PAYABLE__BILL_TYPE__COMPENSATION = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__COMPENSATION"/>',    
	pay.payable.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD"/>',    
	pay.payable.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER"/>';
	pay.payable.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE =  '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE"/>';

	//
	pay.payable.QUERY_BYDATE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_DATE"/>';//按客户查询
	pay.payable.QUERY_BYNUMBER ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_BILL_NO"/>';//按单号查询
	pay.payable.QUERY_BYSOURCENUMBER ='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@TAB_QUERY_BY_SOURCE_BILL_NO"/>';//按来源单号查询
</script>
</head>
<body>
<div id="mainAreaPanel">
		<div id="T_pay-manageBillPayable-body"></div>
	</div>
</body>
</html>