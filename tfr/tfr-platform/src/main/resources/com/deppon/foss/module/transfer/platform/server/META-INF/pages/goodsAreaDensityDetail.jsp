<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="goodsAreaDensityDetail" />
<script type="text/javascript" src="${scripts}/goodsAreaDensityDetail.js"></script>
<script type="text/javascript">
	platform.goodsAreaDensityDetail.outfieldCode = '${requestScope.goodsAreaDensityVo.parentTfrCtrCode}';
	platform.goodsAreaDensityDetail.outfieldName = '${requestScope.goodsAreaDensityVo.parentTfrCtrName}';
	platform.goodsAreaDensityDetail.lastPageOrgCode = '${param.parentTfrCtrCode}';
	platform.goodsAreaDensityDetail.lastPageOrgName = '${param.parentTfrCtrName}';
</script>
</head>
<body>

</body>
</html>