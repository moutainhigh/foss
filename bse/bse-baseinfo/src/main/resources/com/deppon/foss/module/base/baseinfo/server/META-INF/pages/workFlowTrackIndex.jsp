<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://eos.primeton.com/tags/workflow" prefix="wf"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%-- <link rel="stylesheet" type="text/css" href="${resources}/styles/ext-all.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="${resources}/styles/fssc-all.css"> --%>
<script type="text/javascript" src="${resources}/scripts/bootstrap.js"></script>
<ext:module />
<script src="<%=request.getContextPath() %>/workflow/wfcomponent/web/js/Graphic.js"></script>
		<wf:processGraph processInstID ="${workFlowNum}" zoomQuotiety="1" onclick="show(this,'1')">
     		<wf:activityGraph activityType="start" onclick="show(this,'1')" />
           	<wf:activityGraph activityType="manual" onclick="show(this,'1')" />
           	<wf:activityGraph activityType="route" onclick="show(this,'1')" />
           	<wf:activityGraph activityType="subflow" onclick="show(this,'1')" />
           	<wf:activityGraph activityType="toolapp" onclick="show(this,'1')" />
           	<wf:activityGraph activityType="finish" onclick="show(this,'1')" />
	    </wf:processGraph>
<script>
	function show(activityDefId,processDefId){
			var activityDefId=activityDefId.attributes[4].nodeValue
			Ext.create('Fssc.Workflow.BackWindow').showAt(0,0);
			procStore.load({params:{
				'instanceId':"${workFlowNum}",
				'activityDefID':activityDefId
			}});
		}
	
</script>
