<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="shortScheduleDesign"/>
<%@ include file="../login/common.jsp" %>
<script type="text/javascript">
	scheduling.shortScheduleDesign.planId=${param.planId};
	//
	scheduling.shortScheduleDesign.origOrgCode=${param.origOrgCode};
	//
	scheduling.shortScheduleDesign.destOrgCode=${param.destOrgCode};
	//
	scheduling.shortScheduleDesign.planDate=${param.planDate};
</script>
<script type="text/javascript" src="${scripts}/shortDesignPlan.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_scheduling-shortScheduleDesignIndex-body"></div>
	</div>
</body>