<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/settlementConstants.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="reverseWriteoff" groups="reverseWriteoff"/>
<link rel="stylesheet" type="text/css" href="${styles}/style.css">
<script type="text/javascript">
	//获取后台数据字典的值
	
	//是否红单：红单
	writeoff.reverseWriteoff.SETTLEMENT__IS_RED_BACK__YES = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__IS_RED_BACK__YES"/>';
	
	//是否有效：无效
	writeoff.reverseWriteoff.INACTIVE = '<s:property value="@com.deppon.foss.util.define.FossConstants@INACTIVE"/>';
	
	//获取结算是否有效字段
	writeoff.reverseWriteoff.YES = '<s:property value="@com.deppon.foss.util.define.FossConstants@YES"/>';
	//核销单生成方式 手动
	writeoff.reverseWriteoff.SETTLEMENT__CREATE_TYPE__MANUAL = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CREATE_TYPE__MANUAL"/>';
	
</script>
<%-- <script type="text/javascript" src="${scripts}/reverseWriteoff.js"></script> --%>
</head>
<body>
</body>
</html>