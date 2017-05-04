<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 查询库区密度信息界面 -->
<ext:module  subModule="densityInfo"/>
<script type="text/javascript">
	platform.densityInfo.outfieldCode = '${requestScope.densityVo.tfrCtrCode}';
	platform.densityInfo.outfield = '${requestScope.densityVo.tfrCtrName}';
	platform.densityInfo.hqCode = '${requestScope.densityVo.hqCode}';
	platform.densityInfo.hqName = '${requestScope.densityVo.hqName}';
</script>
<script type="text/javascript" src="${scripts}/densityInfo.js"></script>