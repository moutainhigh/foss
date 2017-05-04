<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>

<ext:module subModule="unloadtaskbindtrayDetail"/>
<script type="text/javascript" src="${scripts}/unload.js"></script>
<script type="text/javascript">
   unload.traybindmanager.unloadTaskNo = ${param.unloadTaskNo};
   unload.traybindmanager.bindPieces = ${param.bindPieces};
   unload.traybindmanager.bindRate = ${param.bindRate};
   unload.traybindmanager.createTime = ${param.createTime};
   unload.traybindmanager.createrName = decodeURI(${param.createrName});
   unload.traybindmanager.scanPieces = ${param.scanPieces};
   unload.traybindmanager.unLoadScanPieces = ${param.unLoadScanPieces};
</script>
<script type="text/javascript" src="${scripts}/unloadtaskbindtrayDetailExpress_show.js"></script>
