<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<%@ include file="../login/common-gui.jsp" %>
<ext:module subModule="toDoMsg" />  
<script type="text/javascript" src="${scripts}/toDoMsg.js"></script>
<body> 
	<script type="text/javascript">
		createMainView('T_common-toDoMsgInit');
	</script>
</body>