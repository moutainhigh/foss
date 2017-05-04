<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>

<ext:module subModule="unloadtaskbindtrayDetail"/>
<script type="text/javascript" src="${scripts}/unload.js"></script>
<script type="text/javascript">
   backupquery.traybindmanager.unloadTaskNo = ${param.unloadTaskNo};
   backupquery.traybindmanager.bindPieces = ${param.bindPieces};
   backupquery.traybindmanager.bindRate = ${param.bindRate};
   backupquery.traybindmanager.createTime = ${param.createTime};
   backupquery.traybindmanager.createrName = decodeURI(${param.createrName});
   backupquery.traybindmanager.scanPieces = ${param.scanPieces};
   backupquery.traybindmanager.unLoadScanPieces = ${param.unLoadScanPieces};
</script>
<script type="text/javascript" src="${scripts}/unloadtaskbindtrayDetailExpress_show.js"></script>
