<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="loadtaskwaybilldetail" />
<script type="text/javascript">
	load.loadtaskwaybilldetail.taskNo = "${param.taskNo}";
	load.loadtaskwaybilldetail.source = "${param.source}";
</script>
<script type="text/javascript" src="${scripts}/loadtaskwaybilldetail.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_load-loadtaskwaybilldetailIndex-body"></div>
	</div>
</body>