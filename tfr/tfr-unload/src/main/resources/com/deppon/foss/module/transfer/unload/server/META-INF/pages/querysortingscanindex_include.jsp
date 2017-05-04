<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="querysortingscan"/>
<script type="text/javascript">
	unload.querysortingscan.superOrgCode = '${requestScope.vo.superOrgCode}';
</script>
<script type="text/javascript" src="${scripts}/query-sorting-scan.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_unload-querysortingscanindex-body"></div>
	</div>
</body>