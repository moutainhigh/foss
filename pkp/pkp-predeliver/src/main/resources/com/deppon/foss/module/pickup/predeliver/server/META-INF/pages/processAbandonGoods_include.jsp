<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<ext:module  subModule="processAbandonGoods"   />
<script type="text/javascript" >
predeliver.processAbandonGoods.byMsg = ${vo.isLoadByMsg};
</script>
<script type="text/javascript" src="${scripts}/processAbandonGoods.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_predeliver-processAbandonGoodsIndex-body"></div>
	</div>
</body>