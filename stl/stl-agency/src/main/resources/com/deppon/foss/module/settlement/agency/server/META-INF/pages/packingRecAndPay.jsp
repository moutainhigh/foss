<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ include file="../common/stlUtil.jsp"%>
<%@ include file="../common/settlementConstants.jsp"%>
<ext:module subModule="packingRecAndPay" groups="packingRecAndPay" />
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	agency.packingRecAndPay.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT = '<s:property  value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT"/>';
	agency.packingRecAndPay.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE = '<s:property  value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE"/>';
</script>
