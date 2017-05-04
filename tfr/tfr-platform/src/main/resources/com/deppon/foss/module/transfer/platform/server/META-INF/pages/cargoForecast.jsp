<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 货量预测前端界面 -->
<ext:module  subModule="cargoForecast"/>
<script type="text/javascript">
	platform.cargoForecast.outfieldCode = '${requestScope.cargoFcstVo.tfrCtrCode}';
	platform.cargoForecast.outfield = '${requestScope.cargoFcstVo.tfrCtrName}';
</script>
<script type="text/javascript" src="${scripts}/cargoForecast.js"></script>