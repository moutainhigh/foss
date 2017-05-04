<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="platformDistribute" groups="platformDistribute" />
<link rel="stylesheet" type="text/css" href="${styles}/platformDistribute.css">
<script type="text/javascript">
	scheduling.platformDistribute.parentTfrCtrCode = '${requestScope.platformDistributeVo.parentTfrCtrCode}';
	scheduling.platformDistribute.parentTfrCtrName = '${requestScope.platformDistributeVo.parentTfrCtrName}';
</script>