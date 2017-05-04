<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 快递场内货物流动轨迹页面 -->
<ext:module  subModule="expGoodsTrack"/>
<script type="text/javascript">
	platform.expGoodsTrack.outfieldCode = '${requestScope.stockDurationVo.tfrCtrCode}';
	platform.expGoodsTrack.outfield = '${requestScope.stockDurationVo.tfrCtrName}';
</script>
<script type="text/javascript" src="${scripts}/expGoodsTrack.js"></script>