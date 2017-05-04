<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<script type="text/javascript">
	function billListModeFilePath(){
		var path = "${pageContext.request.contextPath}/download/电子对账单.xls";
		window.open(path);
	}
</script>
<ext:module subModule="billListCheckIndex"/>
<script type="text/javascript" src="${scripts}/billListCheck.js"></script>