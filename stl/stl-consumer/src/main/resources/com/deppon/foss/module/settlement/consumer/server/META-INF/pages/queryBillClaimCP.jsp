<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="billClaimCP" groups="billClaimCP"/>
<script type="text/javascript">
	//理赔单是否退回：未退回
	consumer.billClaimCP.BILL_CLAIM__RETURN__STATUS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_CLAIM__RETURN__STATUS__NOT_RETURN"/>';
</script>
<%-- <script type="text/javascript" src="${scripts}/queryBillClaim.js"> --%>
</script>

</head>
<body>
</body>
</html>