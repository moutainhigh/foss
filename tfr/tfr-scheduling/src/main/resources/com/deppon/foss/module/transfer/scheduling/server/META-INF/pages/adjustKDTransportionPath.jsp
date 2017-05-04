<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module  subModule="adjustKDTransportionPath"/>
<link rel="stylesheet" type="text/css" href="${styles}/adjustTransportPath.css">
<script type="text/javascript">
				Ext.Ajax.request({
				url:scheduling.realPath('findOutOrgCode.action'),
				async: false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					if(result.success){
						scheduling.adjustKDTransportionPath.outDeptCode=result.schedulingVO.outOrgCode;
					}
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					if(Ext.isEmpty(json)){
						Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportionPath.hint'),  result.message);
					}else{
						Ext.ux.Toast.msg(scheduling.adjustKDTransportionPath.i18n('foss.scheduling.adjustTransportionPath.hint'),  result.message);
					}
				}
			});
</script>
<script type="text/javascript" src="${scripts}/adjustKDTransportionPath.js"></script>
 <script type="text/javascript" src="${scripts}/adjustKDPath.js"></script>
</head>
<body>
</body>
</html>