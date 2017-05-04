<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%-- <%String url = java.net.URLDecoder.decode(request.getParameter("path"),"UTF-8"); %> --%>
<!-- <script type="text/javascript">
	function filePath(){
		var path = "${pageContext.request.contextPath}/download/油耗标准模板.xls";
		window.open(path);
	}
</script> -->
<ext:module subModule="fuelConsumptionIndex"/>
<script type="text/javascript" src="${scripts}/fuelStandard.js"></script>
