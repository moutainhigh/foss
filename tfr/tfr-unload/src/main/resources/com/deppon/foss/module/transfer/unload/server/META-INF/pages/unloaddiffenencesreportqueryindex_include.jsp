<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"%>
<%@ include file="../login/common.jsp" %>
<ext:module subModule="unloaddiffenencesreportquery" />
<script type="text/javascript">
Ext.Ajax.request({
	url: unload.realPath('querySuperiorOrgByOrgCode.action'),
	async: false,
	success: function(response){
		var result = Ext.decode(response.responseText),
		unloadDiffReportVo = result.unloadDiffReportVo;
		unload.unloaddiffenencesreportquery.superOrgCode = unloadDiffReportVo.superOrgCode;
	},
	exception: function(response){
    	var result = Ext.decode(response.responseText);
    	Ext.ux.Toast.msg('提示', result.text);
    }
});
</script>
<script type="text/javascript" src="${scripts}/unload.js"></script>
<script type="text/javascript" src="${scripts}/unloaddiffenencesreport_query.js"></script>
<body>
	<div id="mainAreaPanel">
		<div id="T_unload-unloaddiffenencesreportqueryindex-body"></div>
	</div>
</body>
