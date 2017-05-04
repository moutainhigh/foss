<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/productType.jsp" %> 
<%@ include file="mvrDetail_include.jsp" %>
<ext:module subModule="mvrPtpAllDetails" groups="mvrPtpAllDetails"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script  type="text/javascript" >
mvrPtpAllDetails = {}
mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER"/>';
mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__AIR = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR"/>';
mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY"/>';
mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY"/>';
mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE"/>';
mvrPtpAllDetails.CUSTOMER_TYPE__PARTNER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@CUSTOMER_TYPE__PARTNER"/>';//合伙人

mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__DRIVER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__DRIVER"/>';
mvrPtpAllDetails.SETTLEMENT__CUSTOMER_TYPE__PACKAGENCE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__PACKAGENCE"/>';
</script>
