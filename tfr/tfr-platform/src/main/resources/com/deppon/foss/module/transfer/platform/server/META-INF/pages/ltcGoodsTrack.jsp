<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!-- 零担场内货物流动轨迹页面 -->
<ext:module  subModule="ltcGoodsTrack"/>
<script type="text/javascript">
	platform.ltcGoodsTrack.outfieldCodeOfLtc = '${requestScope.stockDurationVo.tfrCtrCode}';
	platform.ltcGoodsTrack.outfieldOfLtc = '${requestScope.stockDurationVo.tfrCtrName}';
</script>
<script type="text/javascript" src="${scripts}/ltcGoodsTrack.js"></script>