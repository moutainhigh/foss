<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext" %>
<ext:module subModule="unloadtaskquery" />
<script type="text/javascript">
	unload.unloadtaskquery.outfieldCode = '${requestScope.unloadTaskVo.outfieldCode}';
</script>
<script type="text/javascript" src="${scripts}/unload.js"></script>
<script type="text/javascript" src="${scripts}/unloadtask_query.js"></script>
