<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../common/stlUtil.jsp" %>
<script type="text/javascript">
	function filePath(){
		var path = "${pageContext.request.contextPath}/download/ƫ������Ӧ��Ӧ��ģ��.xls";
		window.open(path);
	}
</script>
<ext:module subModule="PAImportPayAndRec" groups="PAImportPayAndRec"/>