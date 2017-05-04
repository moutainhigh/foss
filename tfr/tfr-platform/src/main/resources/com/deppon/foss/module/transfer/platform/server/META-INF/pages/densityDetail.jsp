<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 查询库区密度明细页面 -->
<ext:module  subModule="densityDetail"/>
<script type="text/javascript">
	platform.densityDetail.outfieldCode = '${requestScope.densityVo.tfrCtrCode}';
	platform.densityDetail.outfield = '${requestScope.densityVo.tfrCtrName}';
	platform.densityDetail.hqCode = '${requestScope.densityVo.hqCode}';
	platform.densityDetail.hqName = '${requestScope.densityVo.hqName}';
</script>
<script type="text/javascript" src="${scripts}/densityDetail.js"></script>