<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 库存时长查询界面 -->
<ext:module  subModule="stockDuration"/>
<script type="text/javascript">
	platform.stockDuration.outfieldCode = '${requestScope.stockDurationVo.tfrCtrCode}';
	platform.stockDuration.outfield = '${requestScope.stockDurationVo.tfrCtrName}';
</script>
<script type="text/javascript" src="${scripts}/stockDuration.js"></script>