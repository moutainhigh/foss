<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext" %>
<ext:module subModule="queryloadtask" />
<script type="text/javascript">
	load.queryloadtask.outfieldCode = '${requestScope.loadTaskVo.outfieldCode}';
</script>
<script type="text/javascript" src="${scripts}/loadtask_query_gaprepwindow.js"></script>
<script type="text/javascript" src="${scripts}/loadtask_query.js"></script>
