<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<ext:module subModule="querysortingscan"/>
<script type="text/javascript">
	backupquery.querysortingscan.superOrgCode = '${requestScope.vo.superOrgCode}';
</script>
<script type="text/javascript" src="${scripts}/query-sorting-scan.js"></script>