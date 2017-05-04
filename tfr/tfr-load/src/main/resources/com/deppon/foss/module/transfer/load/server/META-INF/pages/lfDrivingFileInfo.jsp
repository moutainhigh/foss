<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<ext:module subModule="lfDrivingFileInfo"/>
<script type="text/javascript">
	load.lfDrivingFileInfo.drivingNo = '${param.drivingNo}';
</script>
<script type="text/javascript" src="${scripts}/load.js"></script>
<script type="text/javascript" src="${scripts}/lfDrivingFile_Info.js"></script>