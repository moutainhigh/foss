<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
	mvrEgAllDetails = {}
	
	//单据父类型
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XS"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__YS"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YF = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__YF"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HK"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__US = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__US"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__DZ = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__DZ"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__FK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__FK"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XP = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XP"/>';
	mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HZ = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HZ"/>';
	
	mvrEgAllDetails.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER"/>';
	mvrEgAllDetails.SETTLEMENT__CUSTOMER_TYPE__AIR = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR"/>';
	mvrEgAllDetails.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY"/>';
	mvrEgAllDetails.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY"/>';
	mvrEgAllDetails.SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE"/>';

	mvrEgAllDetails.rptType_RFO = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFO"/>';
	mvrEgAllDetails.rptType_PLI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_PLI"/>';
	mvrEgAllDetails.rptType_AFI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_AFI"/>';
	mvrEgAllDetails.rptType_RFI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFI"/>';
	mvrEgAllDetails.rptType_AFR = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_AFR"/>';
	mvrEgAllDetails.rptType_RFD = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFD"/>';
	mvrEgAllDetails.rptType_PLR = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_PLR"/>';
	mvrEgAllDetails.rptType_LDD = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LDD"/>';
	mvrEgAllDetails.rptType_LDI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LDI"/>';
	mvrEgAllDetails.rptType_LWO = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LWO"/>';

	/**
	 * 格式化分录的单据子类型
	 */
	mvrEgAllDetails.billTypeToConvert = function(value, record) {
		var displayField = value;
		// 单据子类型转化 --应收
		if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XS) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_CASH_COLLECTION__BILL_TYPE");
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YS) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_RECEIVABLE__BILL_TYPE");
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YF) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_PAYABLE__BILL_TYPE");
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HK) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_REPAYMENT__BILL_TYPE");
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__US) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_DEPOSIT_RECEIVED__BILL_TYPE");
			// 对账单没有单据子类型
		}else if(record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__UF){//预付单
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
			"BILL_ADVANCED_PAYMENT__BILL_TYPE");
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__DZ) {

		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__FK) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_PAYMENT__BILL_TYPE");
			// 小票没有单据子类型
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XP) {
			// 坏账没有单据子类型
		} else if (record.get('billParentType') == mvrEgAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HZ) {

		} else {
			displayField = value;
		}
		return displayField;
	}
</script>

<body>
<div id="mainAreaPanel">
		<div id="T_closing-mvrEgAllDetails-body"></div>
	</div>
</body>