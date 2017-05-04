<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="tfrCtrAbsenteeInfo" />

<script type="text/javascript">
	platform.tfrCtrAbsenteeInfo.parentTfrCtrCode = '${requestScope.tfrCtrAbsenteeInfoVo.parentTfrCtrCode}';
	platform.tfrCtrAbsenteeInfo.parentTfrCtrName = '${requestScope.tfrCtrAbsenteeInfoVo.parentTfrCtrName}';

	platform.tfrCtrAbsenteeInfo.downloadTemplate = function(){
		var path = "${pageContext.request.contextPath}/download/外场异常人员模板.xls";
		window.open(path);
	}
	
</script>

<script type="text/javascript" src="${scripts}/tfrCtrAbsenteeInfo.js"></script>