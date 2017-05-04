<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="cockpit" />

<script type="text/javascript">
platform.cockpit.tfrCtrCode = '${requestScope.cockpitVo.tfrCtrCode}';
platform.cockpit.tfrCtrName = '${requestScope.cockpitVo.tfrCtrName}';
</script>

<script type="text/javascript" src="${scripts}/cockpit.js"></script>