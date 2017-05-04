<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="pkpShortScheduleModify"/>
<script type="text/javascript">
	//����
	scheduling.pkpShortScheduleModify.ymd=${param.ymd};
	//��������
	scheduling.pkpShortScheduleModify.schedulingDepartCode=${param.schedulingDepartCode};
	//��������
	scheduling.pkpShortScheduleModify.actionType=${param.actionType};
	scheduling.pkpShortScheduleModify.driverOrgName=${param.driverOrgName};
</script>
<script type="text/javascript" src="${scripts}/pkpShortSchedule.js"></script>
<link rel="stylesheet" type="text/css" href="${styles}/shortSchedule.css">
