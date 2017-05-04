<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="tfrCtrOnDuty" />

<script type="text/javascript">
	platform.tfrCtrOnDuty.tfrCtrCode = '${requestScope.tfrCtrOnDutyVo.tfrCtrCode}';
	platform.tfrCtrOnDuty.tfrCtrName = '${requestScope.tfrCtrOnDutyVo.tfrCtrName}';
</script>

<script type="text/javascript" src="${scripts}/tfrCtrOnDuty.js"></script>