<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="rentCarForPayReport" groups="rentCarForPayReport"/>
<script type="text/javascript">
//获取后台数据字典的值  审核、反审核
pay.rentCarForPayReport.WITHHOLDING_STATUS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@WITHHOLDING_STATUS_NOT_TRANSFER"/>';
pay.rentCarForPayReport.WITHHOLDING_STATUS_NOT_TRANSFER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@WITHHOLDING_STATUS_NOT_TRANSFER"/>';
</script>
</head>
<body>
</body>
</html>