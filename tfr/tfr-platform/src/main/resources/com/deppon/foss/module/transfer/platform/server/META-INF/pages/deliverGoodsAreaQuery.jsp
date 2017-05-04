<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="deliverGoodsAreaQuery" />
<script type="text/javascript" src="${scripts}/deliverGoodsAreaQuery.js"></script>
<script type="text/javascript">
	platform.deliverGoodsAreaQuery.serverTimeString = '${requestScope.deliverGoodsAreaQueryVo.serverTimeString}';
	platform.deliverGoodsAreaQuery.outfieldCode = '${requestScope.deliverGoodsAreaQueryVo.outfieldCode}';
	platform.deliverGoodsAreaQuery.outfieldName = '${requestScope.deliverGoodsAreaQueryVo.outfieldName}';
	platform.deliverGoodsAreaQuery.isAnalyst='${requestScope.deliverGoodsAreaQueryVo.isAnalyst}';
</script>
</head>
<body>

</body>
</html>