<!-- 空运月报表查询界面 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp"%>
<%@ include file="../common/productType.jsp" %>
<link rel="stylesheet" href="${styles }/style.css"/>
<ext:module subModule="mvrNAfr" groups="mvrNAfr"/>
<script type="text/javascript">
	closing.mvrNAfr.SETTLEMENT__CUSTOMER_TYPE__AIR = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR"/>';
	closing.mvrNAfr.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY"/>';
</script>

