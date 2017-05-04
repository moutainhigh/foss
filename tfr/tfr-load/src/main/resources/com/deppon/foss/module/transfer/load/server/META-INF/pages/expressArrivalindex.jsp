<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<ext:module subModule="expressArrivalindex" />
<link rel="stylesheet" type="text/css" href="${styles}/express.css">
<spring:eval expression="@applicationProperties['gis.page.query']" var="gisPageQuery"/>
<script type="text/javascript">
	load.expressArrivalindex.gisPageQuery = '${gisPageQuery}';
	
</script>
<script type="text/javascript" src="${scripts}/expressArrivalindex.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_load-expressArrivalindex-body"></div>
	</div>
</body>
