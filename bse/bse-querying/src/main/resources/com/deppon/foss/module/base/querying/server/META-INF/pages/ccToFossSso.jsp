<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%-- <ext:module subModule="ccToFossSso"/>
 <link rel="stylesheet" type="text/css"
	href="${resources}/styles/ext-foss-min.css">
<script type="text/javascript" src="${resources}/scripts/bootstrap.js"></script>
<script type="text/javascript" src="${resources}/scripts/ext-lang-${FRAMEWORK__KEY_LOCALE__}.js"></script>
<script type="text/javascript" src="${resources}/components/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script> --%>
<%@ include file="../login/common-gui.jsp" %>
<ext:module subModule="integrativeQuery"/>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/querying-util.js"></script>
<script type="text/javascript" src="${scripts}/querying-prtwaybill.js"></script>
<script type="text/javascript" src="${scripts}/bse-querying.js"></script>
<body> 
<input name="billNo" type='hidden' value="${serviceId}" id='billNo'>
<script type="text/javascript">
  var serviceId=document.getElementById('billNo').value;
</script>
<script type="text/javascript">
  createMainView('T_querying-integrativeQueryIndex');
</script>
</body>