<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp"%>
<%@ include file="../common/productType.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="dvdDhk"/>
<script type="text/javascript" src="${scripts}/dvdDhk.js"></script>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">

	//获取还款方式 现金  银行卡 电汇 支票
	closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CASH"/>';
	closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__CARD = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__CARD"/>';
	closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER"/>';
	closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__NOTE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__NOTE"/>';
	closing.dvdDhk.SETTLEMENT__PAYMENT_TYPE__ONLINE= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__PAYMENT_TYPE__ONLINE"/>';
	
	//单据类型:还款单，现金收款单
	closing.dvdDhk.SETTLEMENT__BILL_PARENT_TYPE__HK= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HK"/>';
	closing.dvdDhk.SETTLEMENT__BILL_PARENT_TYPE__XS= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XS"/>';
	
</script>

</head>
