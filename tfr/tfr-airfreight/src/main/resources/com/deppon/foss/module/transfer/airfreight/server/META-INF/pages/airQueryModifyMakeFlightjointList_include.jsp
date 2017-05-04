<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<script type="text/javascript">
     airfreight.airWaybillNo= '${param.airWaybillNo}';
</script>
<ext:module groups="airQueryModifyMakeFlightjointList" subModule="airQueryModifyMakeFlightjointList" />
<%-- <script type="text/javascript" src="${scripts}/modifyFlightMakejoint.js"></script> --%>
<body>
	<div id="mainAreaPanel">
		<div id="T_airfreight-airQueryModifyMakeFlightjointList-body"></div>
	</div>
</body>

