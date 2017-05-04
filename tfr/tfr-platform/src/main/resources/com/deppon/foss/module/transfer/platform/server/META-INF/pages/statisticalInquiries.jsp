<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="statisticalInquiries" />

<script type="text/javascript">
	platform.statisticalInquiries.parentTfrCtrCode = '${requestScope.forecastVO.parentTfrCtrCode}';
</script>

<script type="text/javascript" src="${scripts}/statisticalInquiries.js"></script>
