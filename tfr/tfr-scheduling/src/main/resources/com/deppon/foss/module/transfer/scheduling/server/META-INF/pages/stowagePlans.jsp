<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module  subModule="stowagePlan"/>
<script type="text/javascript">
				Ext.Ajax.request({
				url:scheduling.realPath('stowagePlansDefaultLength.action'),
				async: false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					scheduling.stowagePlan.stowagePlanDefault = result.vo.stowagePlanDefault;
				}
			});
</script>
<script type="text/javascript" src="${scripts}/stowagePlan.js"></script>