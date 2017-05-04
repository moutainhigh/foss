<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<ext:module subModule="complement"/>
<link rel="stylesheet" type="text/css" href="${styles}/express.css">
<spring:eval expression="@applicationProperties['gis.page.query']" var="gisPageQuery"/>
<script type="text/javascript">
	load.complement.transferCenterCode = '${requestScope.complementVo.transferCenterCode}';
	load.complement.tfrCtrName = '${requestScope.complementVo.tfrCtrName}';
	load.complement.tfrCtrCode = '${requestScope.complementVo.tfrCtrCode}';
	load.complement.gisPageQuery = '${gisPageQuery}';
</script>
<script type="text/javascript" src="${scripts}/express_complement_query.js"></script>
<spring:eval expression="@applicationProperties['gis.js.map']" var="gishost"/> 
<script type="text/javascript" src="${gishost}"></script>  