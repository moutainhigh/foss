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
	var workFlowNum = "${workFlowEntityVo.workFlowEntity.procInstId}";
 	var relatedKey = "${workFlowEntityVo.workFlowEntity.id}";
	var pageState = "${workFlowEntityVo.workFlowEntity.pageStatus}";
	var workFlowApprovalState = "${workFlowEntityVo.workFlowEntity.workFlowStatus}";
 	var approvalResult = "${workFlowEntityVo.workFlowEntity.approvalResult}";
	var applyType = "${workFlowEntityVo.workFlowEntity.applyType}";
	var addNewTempArrears = "${workFlowEntityVo.workFlowEntity.addNewTempArrears}";
	var note = "${workFlowEntityVo.workFlowEntity.note}";
	var currentAppName = "${workFlowEntityVo.workFlowEntity.currentApproverName}";
	var applyManName = "${workFlowEntityVo.workFlowEntity.applyManName}";
	var applyMan = "${workFlowEntityVo.workFlowEntity.applyMan}";
	var title = "${workFlowEntityVo.workFlowEntity.title}";
	var applyManDeptName = "${workFlowEntityVo.workFlowEntity.applyManDeptName}";
	var arrearsStandardUrl = "${arrearsStandardUrl}";
	var arrearsStandardExampleUrl = "${arrearsStandardExampleUrl}";
	var currentAppCode = "${workFlowEntityVo.workFlowEntity.currentApprover}";
	var applyManDeptCode = "${workFlowEntityVo.workFlowEntity.applyManDept}";
	var totalTempArrears = "${workFlowEntityVo.workFlowEntity.totalTempArrears}";
	var applyTempDept ="${workFlowEntityVo.workFlowEntity.applyTempDept}";
	var applyTempDeptName ="${workFlowEntityVo.workFlowEntity.applyTempDeptName}";
// var currentTempArreas = 50000;
// var workFlowNum = '13492802';
// var relatedKey = 'f7cbc5d5-bc8e-454b-badc-cba3da0dd74f';
// var pageState = 'APPROVAL';
// var pageState = 'VIEW';
// var workFlowApprovalState ='WF_STATUS_APPROVALING';
// var workFlowApprovalState ='WF_STATUS_APPROVAL_OVER';
// var approvalResult = 'Y';
// var applyType = 'WHOLE_VEHICLE';
// var addNewTempArrears = 4988;
// var note = '4564646465';
// var currentAppName = '陈阳';
// var applyManName = '王鹏';
// var	applyMan = '078816';
// var title = '高级软件开发工程师';
// var applyManDeptName ='FOSS综合开发组';

	
if(approvalResult === 'Y'){
	approvalResult = '同意';
}else if(approvalResult === 'N'){
	approvalResult = '不同意';
}
if(applyType=='SECTOR_REVENUE_GROWTH'){
	applyType = '部门收入增长';//部门收入增长
}else if(applyType=='WHOLE_VEHICLE'){
	applyType = '整车';//整车
}else{
	applyType = '其它';
}
</script>
<script src="${scripts}/baseinfo-deptTempArrearsUnapprovedWorkFlow.js"></script>
<script src="${scripts}/commomSelector.js"></script>