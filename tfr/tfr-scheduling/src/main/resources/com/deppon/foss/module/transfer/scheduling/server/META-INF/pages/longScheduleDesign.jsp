<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="longScheduleDesign"/>
<script type="text/javascript">
	scheduling.longScheduleDesign.planId=${param.planId};
	//��������
	scheduling.longScheduleDesign.origOrgCode=${param.origOrgCode};
	//���ﲿ��
	scheduling.longScheduleDesign.destOrgCode=${param.destOrgCode};
	//�ƻ�����
	scheduling.longScheduleDesign.planDate=${param.planDate};
</script>
<script type="text/javascript" src="${scripts}/longDesignPlan.js"></script>