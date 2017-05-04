<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<ext:module subModule="printagentwaybill" />
<%-- <link rel="stylesheet" type="text/css" href="${scripts}/../../styles/partialline/style.css"> --%>
<script type="text/javascript" src="${scripts}/printAgentWaybill.js"></script>
<script type="text/javascript">
	function filePath(){
		var path = "${pageContext.request.contextPath}/download/代理单号绑定.xls";
		window.open(path);
}
</script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>