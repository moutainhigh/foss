<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="codPaid" groups="codPaid"/>
<script type="text/javascript">
	/**
	 * 代收货款状态--退款中RG
	 */
	consumer.codPaid.COD__STATUS__RETURNING = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__RETURNING"/>';
	/**
	 * 代收货款状态--已退款RD
	 */
	consumer.codPaid.COD__STATUS__RETURNED = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@COD__STATUS__RETURNED"/>';

</script>
</head>
