<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../common/stlUtil.jsp" %>
<script type="text/javascript">
	function filePath(){
		var path = "${pageContext.request.contextPath}/download/空运其它应收应付模板.xls";
		window.open(path);
	}
</script>
<ext:module subModule="importPayAndRec" groups="airImportPayAndRec"/>