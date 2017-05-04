<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp"%>
<script type="text/javascript">
	function filePath(){
		var path = "${pageContext.request.contextPath}/download/偏线其它应收应付模板.xls";
		window.open(path);
	}
</script>
<ext:module subModule="PAImportPayAndRec" groups="PAImportPayAndRec"/>
<body>
	<div id="mainAreaPanel">
		<div id="T_agency-PAImportPayAndRec-body"></div>
	</div>
</body>