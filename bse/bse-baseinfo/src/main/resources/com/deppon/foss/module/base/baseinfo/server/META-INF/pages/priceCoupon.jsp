<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/ext" prefix="ext"  %>
<script type="text/javascript">
	function templateDownload(){
			var path ="${pageContext.request.contextPath}/download/couponDetail.xls";
			window.open(path);
	}
</script>
<ext:module subModule="priceCoupon" groups="priceCoupon"/>
