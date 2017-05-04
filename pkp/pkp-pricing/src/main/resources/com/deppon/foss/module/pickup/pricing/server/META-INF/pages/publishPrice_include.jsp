<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<style>
#mainAreaPanel-body {
	top: 0px !important;
}
</style>
<ext:module subModule="publishPrice" groups = "publishPrice"/>
<body>
	<script>
		Ext.create('Ext.container.Viewport', {
			items: [{
				id: 'mainAreaPanel',
				items: [{
					id : 'T_pricing-indexPublishPrice',
				}]
			}]
		});
	</script>
</body>
