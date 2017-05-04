<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="statisticalInquiries" />
<script type="text/javascript">
				Ext.Ajax.request({
				url:scheduling.realPath('stowagePlansDefaultTempLength.action'),
				async: false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					scheduling.statisticalInquiries.stowagePlanDefault = result.vo.stowagePlanDefault;
				}
			});
</script>
<script type="text/javascript" src="${scripts}/statisticalInquiries.js"></script>
