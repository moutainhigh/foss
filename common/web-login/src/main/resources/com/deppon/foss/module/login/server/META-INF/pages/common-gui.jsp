<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="common.jsp"%>
<style>
#mainAreaPanel-body {
	top: 0px !important;
}
</style>
<script>
	function createMainView(id){
		Ext.create('Ext.container.Viewport', {
			items: [{
				id: 'mainAreaPanel',
				items: [{
					id : id,
				}]
			}]
		});	
	}
</script>
