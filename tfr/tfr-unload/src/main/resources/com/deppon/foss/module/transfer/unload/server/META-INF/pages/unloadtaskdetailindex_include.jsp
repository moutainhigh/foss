<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="unloadtaskdetailquery" />

<script type="text/javascript">
	unload.unloadtaskdetailquery.unloadTaskNo = "${param.unloadTaskNo}";
</script>
<script type="text/javascript" src="${scripts}/unloadtaskdetail.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_unload-unloadtaskdetailqueryIndex-body"></div>
	</div>
</body>