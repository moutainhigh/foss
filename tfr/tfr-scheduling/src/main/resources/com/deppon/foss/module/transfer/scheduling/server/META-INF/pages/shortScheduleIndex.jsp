<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="shortScheduleModify"/>
<script type="text/javascript">
	//����
	scheduling.shortScheduleModify.ymd=${param.ymd};
	//��������
	scheduling.shortScheduleModify.schedulingDepartCode=${param.schedulingDepartCode};
	//��������
	scheduling.shortScheduleModify.actionType=${param.actionType};
</script>
<script type="text/javascript" src="${scripts}/shortSchedule.js"></script>
<link rel="stylesheet" type="text/css" href="${styles}/shortSchedule.css">