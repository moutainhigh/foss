<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="forecastQuantity"/>
<script type="text/javascript">
	platform.forecastQuantity.action = '${param.action}'
</script>
<script type="text/javascript" src="${scripts}/forecastQuantity.js"></script>
<script type="text/javascript" src="${scripts}/charts.js"></script>
<script type="text/javascript" src="${scripts}/joinCarAdjust.js"></script>