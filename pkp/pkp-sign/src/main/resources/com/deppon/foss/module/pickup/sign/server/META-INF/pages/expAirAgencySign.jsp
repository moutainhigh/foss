﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function filePath(){
		var path = "${pageContext.request.contextPath}/download/Template.xlsx";
		window.open(path);
	}
</script>
<ext:module subModule="expAirAgencySign" groups="expAirAgencySign"/>
<!-- //落地配货物签收 -->
