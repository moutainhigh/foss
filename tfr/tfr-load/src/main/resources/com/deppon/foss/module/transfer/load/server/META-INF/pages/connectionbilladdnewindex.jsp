<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="connectionbilladdnew"/>
<script type="text/javascript">
load.connectionbilladdnew.connectionBillNo = '${requestScope.connectionBillVo.connectionBillNo}';
load.connectionbilladdnew.orgCode = '${requestScope.connectionBillVo.orgCode}';
load.connectionbilladdnew.orgName = '${requestScope.connectionBillVo.orgName}';
</script>
<link rel="stylesheet" type="text/css" href="${styles}/handoverbill.css">
<script type="text/javascript" src="${scripts}/connectionbill_addnew.js"></script>
<%-- <script type="text/javascript" src="${scripts}/../print/print.js"></script>
--%>