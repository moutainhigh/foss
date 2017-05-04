<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 查询库区密度走势图界面 -->
<ext:module  subModule="densityTrend"/>
<script type="text/javascript">
	platform.densityTrend.outfieldCode = '${requestScope.densityVo.tfrCtrCode}';
	platform.densityTrend.outfield = '${requestScope.densityVo.tfrCtrName}';
	platform.densityTrend.hqCode = '${requestScope.densityVo.hqCode}';
	platform.densityTrend.hqName = '${requestScope.densityVo.hqName}';
</script>
<script type="text/javascript" src="${scripts}/densityTrend.js"></script>