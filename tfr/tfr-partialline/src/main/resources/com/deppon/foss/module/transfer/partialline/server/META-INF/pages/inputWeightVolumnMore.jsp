<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<ext:module subModule="inputWeightVolumnMore"/>
	<link rel="stylesheet" type="text/css" href="${styles}/../../styles/partialline/style.css">
	<script type="text/javascript" src="${scripts}/inputWeightVolumnMore.js"></script>
<script type="text/javascript">
	function filePath(fileName){
		var path = "${pageContext.request.contextPath}/download/"+fileName;
		window.open(path);
	}
</script>