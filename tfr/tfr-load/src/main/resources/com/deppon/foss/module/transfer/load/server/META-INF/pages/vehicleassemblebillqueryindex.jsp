<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="vehicleassemblebillquery"/>
<script type="text/javascript">
	load.vehicleassemblebillquery.superOrgCode = '${requestScope.vehicleAssembleBillVo.superOrgCode}'
</script>
<script type="text/javascript" src="${scripts}/load.js"></script>
<script type="text/javascript" src="${scripts}/vehicleassemblebill_query.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>