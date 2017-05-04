<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="todoAction"/>
<script>
waybill.todoAction.handOverBillNo = "${param.handOverBillNo}";
</script>
<script type="text/javascript" src="${scripts}/todoAction.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<link rel="stylesheet" type="text/css" href="${styles}/toDoAction.css">