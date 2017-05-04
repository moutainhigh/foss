<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="transferCenterLayoutShow" />
<script type="text/javascript">
	platform.transferCenterLayoutShow.orgCode = '${requestScope.transferCenterLayoutVo.orgCode}';
	if(platform.transferCenterLayoutShow.orgCode === ''
		|| platform.transferCenterLayoutShow.orgCode ===null
		|| platform.transferCenterLayoutShow.orgCode === undefined){
		platform.transferCenterLayoutShow.orgCode = '${param.orgCode}';
		platform.transferCenterLayoutShow.beComeFromQueryPage = '${param.beComeFromQueryPage}';
	}
</script>
<script type="text/javascript" src="${scripts}/transferCenterLayout_show.js"></script>
</head>
<body>
</body>
</html>