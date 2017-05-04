<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="quantitySta"/>
<script type="text/javascript">
	platform.quantitySta.transferCenterCode = '${requestScope.quantityStaVo.transferCenterCode}';
	platform.quantitySta.transferCenterName = '${requestScope.quantityStaVo.transferCenterName}';
</script>
<script type="text/javascript" src="${scripts}/quantitySta_main.js"></script>