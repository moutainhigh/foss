<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="orderDifferReportHandle"/>
<script type="text/javascript">
	unload.orderDifferReportHandle.reportNo = ${param.reportNo}
	unload.orderDifferReportHandle.type = ${param.type}
</script>
<script type="text/javascript" src="${scripts}/orderDifferReport_handle.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>