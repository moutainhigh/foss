<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<%@ include file="../login/common-gui.jsp" %>
<%@ include file="../common/productType.jsp" %>
<ext:module subModule="salesstatement"/>
<script type="text/javascript" src="${scripts}/../print/print.js"></script>
<script type="text/javascript" src="${scripts}/querying-util.js"></script>
<script type="text/javascript" src="${scripts}/querying-prtstatement.js"></script>
<script type="text/javascript" src="${scripts}/querying-salesstatement.js"></script>
<body> 
	<script type="text/javascript">
		createMainView('T_querying-salesStatementIndex');
	</script>
</body>