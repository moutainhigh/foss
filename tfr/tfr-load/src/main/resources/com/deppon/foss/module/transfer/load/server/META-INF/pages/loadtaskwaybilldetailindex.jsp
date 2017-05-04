<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="loadtaskwaybilldetail" />

<script type="text/javascript">
	load.loadtaskwaybilldetail.taskNo = "${param.taskNo}";
	load.loadtaskwaybilldetail.source = "${param.source}";
</script>
<script type="text/javascript" src="${scripts}/loadtaskwaybilldetail.js"></script>