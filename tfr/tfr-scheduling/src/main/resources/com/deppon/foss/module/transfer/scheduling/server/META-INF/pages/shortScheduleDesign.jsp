<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="shortScheduleDesign"/>
<script type="text/javascript">
	scheduling.shortScheduleDesign.planId=${param.planId};
	//��������
	scheduling.shortScheduleDesign.origOrgCode=${param.origOrgCode};
	//���ﲿ��
	scheduling.shortScheduleDesign.destOrgCode=${param.destOrgCode};
	//�ƻ�����
	scheduling.shortScheduleDesign.planDate=${param.planDate};
</script>
<script type="text/javascript" src="${scripts}/shortDesignPlan.js"></script>