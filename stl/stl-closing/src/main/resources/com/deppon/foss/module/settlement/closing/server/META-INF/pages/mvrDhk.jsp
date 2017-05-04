<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp"%>
<%@ include file="../common/productType.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="mvrDhk"/>
<script type="text/javascript" src="${scripts}/mvrDhk.js"></script>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">

	//现金  非现金 
	closing.mvrDhk.SETTLEMENT__COLLECTION_TYPE__CASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@SETTLEMENT__COLLECTION_TYPE__CASH"/>';
	closing.mvrDhk.SETTLEMENT__COLLECTION_TYPE__NOCASH = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@SETTLEMENT__COLLECTION_TYPE__NOCASH"/>';
	
</script>	
</head>
