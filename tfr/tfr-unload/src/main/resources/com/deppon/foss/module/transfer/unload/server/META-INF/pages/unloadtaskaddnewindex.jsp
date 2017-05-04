<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="unloadtaskaddnew" />
<script type="text/javascript">
	unload.unloadtaskaddnew.superOrgCode = '${requestScope.unloadTaskVo.superOrgCode}';
	unload.unloadtaskaddnew.beTransferCenter = '${requestScope.unloadTaskVo.beTransferCenter}';
</script>
<script type="text/javascript" src="${scripts}/unloadtask_addnew.js"></script>