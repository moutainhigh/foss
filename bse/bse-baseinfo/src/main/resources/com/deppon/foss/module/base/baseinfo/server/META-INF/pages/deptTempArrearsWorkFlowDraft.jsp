<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<spring:eval expression="@applicationProperties['workFlow.ArrearsStandard.url']" var="arrearsStandardUrl"/> 
<spring:eval expression="@applicationProperties['workFlow.ArrearsStandardExample.url']" var="arrearsStandardExampleUrl"/> 
<ext:module/>
<link rel="stylesheet" type="text/css"
	href="${resources}/styles/ext-foss-min.css">
<script type="text/javascript" src="${resources}/scripts/bootstrap.js"></script>
<script type="text/javascript" src="${resources}/scripts/ext-lang-${FRAMEWORK__KEY_LOCALE__}.js"></script>
<script type="text/javascript" src="${resources}/components/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var currentTempArreas = "${workFlowEntityVo.workFlowEntity.currentTempArrears}";
var arrearsStandardUrl = "${arrearsStandardUrl}";
var arrearsStandardExampleUrl = "${arrearsStandardExampleUrl}";
var currentName = "${workFlowEntityVo.workFlowEntity.applyManName}";
var currentEmpCode = "${workFlowEntityVo.workFlowEntity.applyMan}";
var currentTitle = "${workFlowEntityVo.workFlowEntity.title}";
var currentDeptName = "${workFlowEntityVo.workFlowEntity.applyManDeptName}";
//var currentTempArreas = 52000;
</script>


<script src="${scripts}/baseinfo-deptTempArrearsWorkFlowQuery.js"></script>
<script src="${scripts}/commomSelector.js"></script>