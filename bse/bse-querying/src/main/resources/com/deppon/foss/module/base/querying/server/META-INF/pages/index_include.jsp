<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common-gui.jsp" %>
<ext:module subModule="integrativeQuery"/>
<script type="text/javascript">
	var serviceId ='';
</script>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/querying-util.js"></script>
<script type="text/javascript" src="${scripts}/querying-prtwaybill.js"></script>
<script type="text/javascript" src="${scripts}/bse-querying.js"></script>
<body> 
	<script type="text/javascript">
		createMainView('T_querying-integrativeQueryIndex');
	</script>
</body>