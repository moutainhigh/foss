<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext" %>
<ext:module subModule="loaderworkloadmodifyExpress" />
<script type="text/javascript">
Ext.Ajax.request({
	url: load.realPath('querySuperiorOrgByOrgCode.action'),
	async: false,
	success: function(response){
		var result = Ext.decode(response.responseText),
			loaderWorkloadVo = result.loaderWorkloadVo;
		load.loaderworkloadmodifyExpress.superOrgCode = loaderWorkloadVo.superOrgCode;
	},
	exception: function(response){
    	var result = Ext.decode(response.responseText);
    	Ext.ux.Toast.msg(load.loaderworkloadmodifyExpress.i18n('foss.load.loaderworkload.prompt'), result.text);
    }
});
</script>
<script type="text/javascript" src="${scripts}/loaderworkloadmodifyExpress.js"></script>
<script type="text/javascript" src="${scripts}/loaderwork_addnewExpress.js"></script>
<script type="text/javascript" src="${scripts}/loaderwork_modifyExpress.js"></script>
 