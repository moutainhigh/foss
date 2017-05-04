<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/productType.jsp" %> 
<ext:module subModule="dvdReturnCod"  groups="dvdReturnCod"/>
<script type="text/javascript">
	/**
	 * 代收货款类型--即日退R1
	 */
	closing.dvdReturnCod.COD__COD_TYPE__RETURN_1_DAY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__COD_TYPE__RETURN_1_DAY"/>';
	/**
	 * 代收货款类型--三日退（审核退）R3RA
	 */
	closing.dvdReturnCod.COD__COD_TYPE__RETURN_R3RA_DAY_CODE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@COD__COD_TYPE__RETURN_R3RA_DAY_CODE"/>';
</script>