<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="handoverbillsalequery"/>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript">
	load.handoverbillsalequery.superOrgCode = '${requestScope.handOverBillVo.superOrgCode}'
</script>
<script type="text/javascript" src="${scripts}/load.js"></script>
<script type="text/javascript" src="${scripts}/handoverbillsale_query.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>