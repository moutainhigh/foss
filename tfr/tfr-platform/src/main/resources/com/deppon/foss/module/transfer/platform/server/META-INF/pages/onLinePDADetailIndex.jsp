<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="onLinePDADetail" />
<script type="text/javascript">
platform.queryPDAOnlineUsing.transferCenterCode = ${param.transferCenterCode}
platform.queryPDAOnlineUsing.queryDate = ${param.queryDate}

</script>
<script type="text/javascript" src="${scripts}/common.js"></script>
<script type="text/javascript" src="${scripts}/onLinePDADetail.js"></script>
</head>
<body>

</body>
</html>