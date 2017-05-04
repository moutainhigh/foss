<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 月台操作效率查询界面 -->
<ext:module  subModule="platformOpeEffi"/>
<script type="text/javascript">
	platform.platformOpeEffi.outfieldCode = '${requestScope.queryCondition.outfieldCode}';
	platform.platformOpeEffi.outfield = '${requestScope.queryCondition.outfieldName}';
	platform.platformOpeEffi.businessDeptCode = '${requestScope.queryCondition.businessDeptCode}';
	platform.platformOpeEffi.businessDept = '${requestScope.queryCondition.businessDeptName}';
</script>
<script type="text/javascript" src="${scripts}/platformOpeEffi.js"></script>