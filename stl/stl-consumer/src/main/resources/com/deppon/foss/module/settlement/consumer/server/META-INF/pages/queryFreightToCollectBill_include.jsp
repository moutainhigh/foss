<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ include file="../login/common.jsp" %>
<%@ include file="../common/stlUtil.jsp" %>
<%@ include file="../common/productType.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<ext:module subModule="queryFreightToCollectBill"  groups="queryFreightToCollectBill"/>
<script type="text/javascript">
//****************到付清查报表******************
consumer.queryFreightToCollectBill.COD_RECEIVABLE= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE"/>';
consumer.queryFreightToCollectBill.DESTINATION_RECEIVABLE='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE"/>';
consumer.queryFreightToCollectBill.AIR_RECEIVABLE='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE"/>';
consumer.queryFreightToCollectBill.DESTINATION_PARTIAL_LINE='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE"/>';
consumer.queryFreightToCollectBill.AIR_AGENCY='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY"/>';
consumer.queryFreightToCollectBill.AIR_AGENCY_COD='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD"/>';
//ISSUE-3389 小件业务
consumer.queryFreightToCollectBill.DESTINATION_LAND_STOWAGE='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE"/>';//快递代理应收
consumer.queryFreightToCollectBill.LAND_STOWAGE_AGENCY_COD='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD"/>';//快递代理代收货款应收单

//****************到付清查报表-库存状态******************
/** 库存少货*/
consumer.queryFreightToCollectBill.STOCKSTATUS_LOSE_STOCK_STATUS='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_STOCKSTATUS_LOSE_STOCK_STATUS"/>';
/** 正常签收*/
consumer.queryFreightToCollectBill.STOCKSTATUS_NORMAL_SIGN_STOCK_STATUS='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_STOCKSTATUS_NORMAL_SIGN_STOCK"/>';
/** 异常签收*/
consumer.queryFreightToCollectBill.STOCKSTATUS_UNNORMAL_SIGN_STOCK_STATUS= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_STOCKSTATUS_UNNORMAL_SIGN"/>';;
/** 未入库*/
consumer.queryFreightToCollectBill.STOCKSTATUS_NO_STOCK_STOCK_STATUS= '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_STOCKSTATUS_NO_STOCK"/>';
/** 库存中*/
consumer.queryFreightToCollectBill.STOCKSTATUS_IN_STOCK_STOCK_STATUS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_STOCKSTATUS_IN_STOCK"/>';
/** 库存异常*/
consumer.queryFreightToCollectBill.STOCKSTATUS_EXCEPTION_STOCK_STOCK_STATUS='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_STOCKSTATUS_EXCEPTION_STOCK"/>';
//********************到付清查报表  查询部门类型***********************
consumer.queryFreightToCollectBill.DEPTTYPE_FROM='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DEPTTYPE_FROM"/>';
consumer.queryFreightToCollectBill.DEPTTYPE_TO='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants@DEPTTYPE_TO"/>';

</script>
</head>
<body>
	<div id="mainAreaPanel">
		<div id="T_consumer-queryFreightToCollectBill-body"></div>
	</div>
</body>
</html>