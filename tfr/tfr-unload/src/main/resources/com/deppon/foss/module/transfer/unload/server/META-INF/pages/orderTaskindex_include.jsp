<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module/>
<script type="text/javascript">
        unload.superOrgCode = '${requestScope.orderTaskVo.superOrgCode}';
	unload.empCode = '${requestScope.orderTaskVo.empCode}';
</script>
<script type="text/javascript" src="${scripts}/orderTask.js"></script>
<script type="text/javascript" src="${scripts}/order.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_unload-orderTaskindex-body"></div>
	</div>
</body>