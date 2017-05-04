<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<ext:module subModule="inputWeightVolumn"/>
	<link rel="stylesheet" type="text/css" href="${styles}/../../styles/partialline/style.css">
	<script type="text/javascript" src="${scripts}/inputWeightVolumn.js"></script>
<script type="text/javascript">
	function filePath(fileName){
		var path = "${pageContext.request.contextPath}/download/"+fileName;
		window.open(path);
	}
</script>