<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="codSalesPay" groups="codSalesPay"/>
<script type="text/javascript">
	/**
	 * 代收货款状态--营业部冻结SF
	 */
	consumer.codSalesPay.COD__STATUS__SHIPPER_FREEZE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__SHIPPER_FREEZE"/>';
	/**
	 * 代收货款状态--未退款NR
	 */
	consumer.codSalesPay.COD__STATUS__NOT_RETURN = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__NOT_RETURN"/>';
	/**
	 * 代收货款状态--退款失败RF
	 */
	consumer.codSalesPay.COD__STATUS__RETURN_FAILURE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__RETURN_FAILURE"/>';
	/**
	 * 代收货款状态--待审核AG
	 */
	consumer.codSalesPay.COD__STATUS__APPROVING = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__APPROVING"/>';
	/**
	 * 代收货款状态--待审核或退款失败AG,RF,SF
	 */
	consumer.codSalesPay.COD__STATUS__AG_RF_SF_CODE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__STATUS__AG_RF_SF_CODE"/>';
	/**
	 * 代收货款状态--待审核或退款失败AG,RF,SF
	 */
	consumer.codSalesPay.COD__STATUS__AG_RF_SF_NAME = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__STATUS__AG_RF_SF_NAME"/>';
	
	/**
	 * 对公对私标志--对公C
	 */
	consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__COMPANY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__PUBLIC_PRIVATE_FLAG__COMPANY"/>';
	/**
	 * 对公对私标志--对私R
	 */
	consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__RESIDENT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__PUBLIC_PRIVATE_FLAG__RESIDENT"/>';
	
	/**CRM
	 * 对公对私标志--对公"PUBLIC_ACCOUNT"
	 */
	consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__PUBLIC = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@CRM_ACCOUNT_NATURE_PUBLIC_ACCOUNT"/>';
	/**
	 * 对公对私标志--对私"PRIVATE_ACCOUNT"
	 */
	consumer.codSalesPay.COD__PUBLIC_PRIVATE_FLAG__PRIVATE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants@CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT"/>';
	
	
	/**
	 * 代收货款类型--即日退R1
	 */
	consumer.codSalesPay.COD__COD_TYPE__RETURN_1_DAY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__COD_TYPE__RETURN_1_DAY"/>';
	
	
</script>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">

</head>
<body>
<div id="mainAreaPanel">
		<div id="T_consumer-codSalesPay-body"></div>
	</div>
</body>