<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/ext" prefix="ext"  %>
<script type="text/javascript">
	function downloadPkpExcelTemplatPath(){
		window.location.href="${pageContext.request.contextPath}/template/接送货排班模板.xls";
	}
</script>
<ext:module  subModule="pkpQueryShortSchedule"/>
<script type="text/javascript">
Ext.Ajax.request({
	url: scheduling.realPath('queryTopFleetOrgCode.action'),
	async: false,
	success: function(response){
		var result = Ext.decode(response.responseText),
			vo = result.vo;
		scheduling.pkpQueryShortSchedule.topFleetOrgCode = vo.topFleetOrgCode;
	},
	exception: function(response){
    	var result = Ext.decode(response.responseText);
    	Ext.ux.Toast.msg(scheduling.pkpQueryShortSchedule.i18n('foss.scheduling.ShortSchedule.tip.title'), result.message);
    }
});
</script>
<link rel="stylesheet" type="text/css" href="${styles}/shortSchedule.css">
<script type="text/javascript" src="${scripts}/pkpQueryShortSchedule.js"></script>
