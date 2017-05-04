<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<spring:eval expression="@applicationProperties['network.js.path']" var="fosshost"/> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module/>

<script type="text/javascript">

Ext.onReady(function() {
	Ext.QuickTips.init();
		
	//window.open('http://192.168.10.133/bse-baseinfo-web/ssoURLRequest?app=network-web', 'newwindow', 'height=910, width=1200, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no')
	window.open('${fosshost}', 'newwindow', 'height=910, width=1200, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
	var tab = Ext.getCmp('mainAreaPanel').getActiveTab();
	tab.close();
});

</script>
</head>
<body>

</body>
</html>