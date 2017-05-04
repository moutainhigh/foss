<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="goodsAreaDensity" />
<script type="text/javascript" src="${scripts}/goodsAreaDensity.js"></script>
<script type="text/javascript">
	platform.goodsAreaDensity.outfieldCode = '${requestScope.goodsAreaDensityVo.parentTfrCtrCode}';
	platform.goodsAreaDensity.outfieldName = '${requestScope.goodsAreaDensityVo.parentTfrCtrName}';
</script>
</head>
<body>

</body>
</html>