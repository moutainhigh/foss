<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="manualInput" />

<script type="text/javascript">
	unload.manualInput.parentTfrCtrCode = '${requestScope.manualInputVo.parentTfrCtrCode}';
	unload.manualInput.parentTfrCtrName = '${requestScope.manualInputVo.parentTfrCtrName}';
</script>

<script type="text/javascript" src="${scripts}/manualInput.js"></script>