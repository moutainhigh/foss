<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="unloadtaskdetailquery" />

<script type="text/javascript">
	unload.unloadtaskdetailquery.unloadTaskNo = "${param.unloadTaskNo}";
	unload.unloadtaskdetailquery.stateValue="${param.state}";
</script>
<script type="text/javascript" src="${scripts}/unloadtaskdetail.js"></script>