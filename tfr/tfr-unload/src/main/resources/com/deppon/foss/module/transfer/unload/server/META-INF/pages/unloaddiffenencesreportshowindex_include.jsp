<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="unloaddiffenencesreportshow" />
<script type="text/javascript">
	unload.unloaddiffenencesreportshow.diffReportNo = ${param.diffReportNo}
</script>
<script type="text/javascript" src="${scripts}/unloaddiffenencesreport_show.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_unload-unloaddiffenencesreportshowindex-body"></div>
	</div>
</body>