<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="handoverbillquery"/>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript">
	load.handoverbillquery.superOrgCode = '${requestScope.handOverBillVo.superOrgCode}'
</script>
<script type="text/javascript" src="${scripts}/load.js"></script>
<script type="text/javascript" src="${scripts}/handoverbill_query.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>