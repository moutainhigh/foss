<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="codFundBillPaid" groups="codFundBillPaid"/> 
<script type="text/javascript">
	/**
	 * 代收货款状态--资金部冻结FF
	 */
	consumer.codFundBillPaid.COD__STATUS__FUND_FREEZE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__FUND_FREEZE"/>';
	/**
	 * 代收货款状态--未退款NR
	 */
	consumer.codFundBillPaid.COD__STATUS__NOT_RETURN = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__NOT_RETURN"/>';

	/**
	 * 对公对私标志--对公C
	 */
	consumer.codFundBillPaid.COD__PUBLIC_PRIVATE_FLAG__COMPANY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__PUBLIC_PRIVATE_FLAG__COMPANY"/>';
	/**
	 * 对公对私标志--对私R
	 */
	consumer.codFundBillPaid.COD__PUBLIC_PRIVATE_FLAG__RESIDENT = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__PUBLIC_PRIVATE_FLAG__RESIDENT"/>';
	
	/**
	 * 代收货款类型--即日退R1
	 */
	consumer.codFundBillPaid.COD__COD_TYPE__RETURN_1_DAY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__COD_TYPE__RETURN_1_DAY"/>';
	/**
	 * 代收货款类型--三日退（审核退）R3,RA
	 */
	consumer.codFundBillPaid.COD__COD_TYPE__RETURN_3_A_DAY_CODE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__COD_TYPE__RETURN_3_A_DAY_CODE"/>';
	/**
	 * 代收货款类型--三日退（审核退）R3,RA
	 */
	consumer.codFundBillPaid.COD__COD_TYPE__RETURN_3_A_DAY_NAME = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__COD_TYPE__RETURN_3_A_DAY_NAME"/>';
	
	/**
	*@author 218392 zhangyongxue 2015-08-06 10:00:00
	* 代收货款类型--打包退(即日)
	*/
	consumer.codFundBillPaid.COD__COD_TYPE__RETURN_PACK_1_DAY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__COD_TYPE__RETURN_PACK_1_DAY"/>';
	
	/**
	*@author 218392 zhangyongxue 2015-08-06 10:18:09
	* 代收货款类型--打包退(三日) = 打包退+三日退+审核退
	*/
	consumer.codFundBillPaid.COD__COD_TYPE__RETURN_PACK_3_A_DAY_CODE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__COD_TYPE__RETURN_PACK_3_A_DAY_CODE"/>';
	
</script>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
</head>
