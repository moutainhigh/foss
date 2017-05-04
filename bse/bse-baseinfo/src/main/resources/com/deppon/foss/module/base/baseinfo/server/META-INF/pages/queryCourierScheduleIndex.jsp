<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/ext" prefix="ext"%>
<ext:module subModule="queryCourierSchedule" groups="queryCourierSchedule"/>
<script type="text/javascript">
	baseinfo.queryCourierSchedule.ymd =${param.ymd};
	var newExpressPartCodes =${param.expressPartCodes};
</script>
<link rel="stylesheet" type="text/css" href="${styles}/courierSchedule.css">