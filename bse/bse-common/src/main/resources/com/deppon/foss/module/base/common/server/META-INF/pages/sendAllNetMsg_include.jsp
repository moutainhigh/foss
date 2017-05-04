<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<%@ include file="../login/common-gui.jsp" %>
<ext:module subModule="sendAllNetMsg" />  
<script type="text/javascript" src="${scripts}/sendAllNetMsg.js"></script>
<body> 
	<script type="text/javascript">
		createMainView('T_common-allNetMsgSendInit');
	</script>
</body>