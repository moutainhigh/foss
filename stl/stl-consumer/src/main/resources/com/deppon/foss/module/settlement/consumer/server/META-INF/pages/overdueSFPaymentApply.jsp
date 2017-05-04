<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="overdueSFApply" groups="overdueSFApply" />
<script type="text/javascript">
	/**
	 * 申请状态  -  未申请
	 */
	consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_NOT_APPLY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@OVERDUE_SERVICE_FEE_AUDIT_STATUS_NOT_APPLY"/>';
	/**
	 * 申请状态  -  审批中
	 */
	consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING"/>';
	/**
	 * 申请状态  -  审批同意
	 */
	consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED"/>';
	/**
	 *申请状态  -  审批不同意
	 */
	consumer.overdueSFApply.OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED"/>';
	
	
</script>
</head>
