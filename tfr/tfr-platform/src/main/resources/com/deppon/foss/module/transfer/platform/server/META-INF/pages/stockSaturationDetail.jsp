<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="stockSaturationDetail" />
<script type="text/javascript" src="${scripts}/stockSaturationDetail.js"></script>
<script type="text/javascript">
	platform.stockSaturationDetail.outfieldCode = '${requestScope.stockSaturationVo.outfieldCode}';
	platform.stockSaturationDetail.outfieldName = '${requestScope.stockSaturationVo.outfieldName}';
	platform.stockSaturationDetail.currentCode = '${requestScope.currentCode}';
</script>
</head>
<body>
	<div id="mainStocksaturationPanel">
		<div id="T_platform-stockSaturationDetailQueryIndex-body"></div>
	</div>
</body>
</html>