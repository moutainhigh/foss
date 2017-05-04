<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext" %>
<ext:module subModule="loaderworkloadmodify" />
<script type="text/javascript">
Ext.Ajax.request({
	url: load.realPath('querySuperiorOrgByOrgCode.action'),
	async: false,
	success: function(response){
		var result = Ext.decode(response.responseText),
			loaderWorkloadVo = result.loaderWorkloadVo;
		load.loaderworkloadmodify.superOrgCode = loaderWorkloadVo.superOrgCode;
	},
	exception: function(response){
    	var result = Ext.decode(response.responseText);
    	Ext.ux.Toast.msg(load.loaderworkloadmodify.i18n('foss.load.loaderworkload.prompt'), result.text);
    }
});
</script>
<script type="text/javascript" src="${scripts}/loaderworkloadmodify.js"></script>
<script type="text/javascript" src="${scripts}/loaderwork_addnew.js"></script>
<script type="text/javascript" src="${scripts}/loaderwork_modify.js"></script>
