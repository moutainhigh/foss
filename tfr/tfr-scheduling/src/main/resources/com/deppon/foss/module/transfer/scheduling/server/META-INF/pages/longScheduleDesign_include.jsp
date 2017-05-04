<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="longScheduleDesign"/>
<%@ include file="../login/common.jsp" %>
<script type="text/javascript">
	scheduling.longScheduleDesign.planId=${param.planId};
	//
	scheduling.longScheduleDesign.origOrgCode=${param.origOrgCode};
	//
	scheduling.longScheduleDesign.destOrgCode=${param.destOrgCode};
	//
	scheduling.longScheduleDesign.planDate=${param.planDate};
</script>
<script type="text/javascript" src="${scripts}/longDesignPlan.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_scheduling-longScheduleDesignIndex-body"></div>
	</div>
</body>