<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="connectionbillquery"/>
<script> 
load.connectionbillquery.orgCode = '${requestScope.connectionBillVo.orgCode}';
load.connectionbillquery.orgName = '${requestScope.connectionBillVo.orgName}';
load.connectionbillquery.transferCenter = '${requestScope.connectionBillVo.transferCenter}';

</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/load.js"></script>
<script type="text/javascript" src="${scripts}/connectionbill_query.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>

