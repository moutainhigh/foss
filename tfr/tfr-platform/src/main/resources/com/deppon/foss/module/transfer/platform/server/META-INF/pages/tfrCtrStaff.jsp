<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="tfrCtrStaff" />

<script type="text/javascript">
	platform.tfrCtrStaff.parentTfrCtrCode = '${requestScope.tfrCtrStaffVo.parentTfrCtrCode}';
	platform.tfrCtrStaff.parentTfrCtrName = '${requestScope.tfrCtrStaffVo.parentTfrCtrName}';
</script>

<script type="text/javascript" src="${scripts}/tfrCtrStaff.js"></script>