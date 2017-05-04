<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="unloadtaskmodify" />
<script type="text/javascript">
	unload.unloadtaskmodify.unloadTaskId = ${param.unloadTaskId};
	unload.unloadtaskmodify.superOrgCode = '${requestScope.unloadTaskVo.superOrgCode}';
	unload.unloadtaskmodify.beTransferCenter = '${requestScope.unloadTaskVo.beTransferCenter}';
</script>
<script type="text/javascript" src="${scripts}/unloadtask_modify.js"></script>