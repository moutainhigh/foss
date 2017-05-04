<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
	mvrAllDetails = {}
	
	//单据父类型
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XS"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__YS"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YF = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__YF"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HK"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__US = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__US"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__DZ = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__DZ"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__FK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__FK"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XP = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XP"/>';
	mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HZ = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HZ"/>';
	
	mvrAllDetails.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER"/>';
	mvrAllDetails.SETTLEMENT__CUSTOMER_TYPE__AIR = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR"/>';
	mvrAllDetails.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY"/>';
	mvrAllDetails.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY"/>';
	mvrAllDetails.SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE"/>';

	mvrAllDetails.rptType_RFO = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFO"/>';
	mvrAllDetails.rptType_PLI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_PLI"/>';
	mvrAllDetails.rptType_AFI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_AFI"/>';
	mvrAllDetails.rptType_RFI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFI"/>';
	mvrAllDetails.rptType_AFR = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_AFR"/>';
	mvrAllDetails.rptType_RFD = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFD"/>';
	mvrAllDetails.rptType_PLR = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_PLR"/>';
	mvrAllDetails.rptType_LDD = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LDD"/>';
	mvrAllDetails.rptType_LDI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LDI"/>';
	mvrAllDetails.rptType_LWO = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LWO"/>';

	/**
	 * 格式化分录的单据子类型
	 */
	mvrAllDetails.billTypeToConvert = function(value, record) {
		var displayField = value;
		// 单据子类型转化 --应收
		if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XS) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_CASH_COLLECTION__BILL_TYPE");
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YS) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_RECEIVABLE__BILL_TYPE");
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__YF) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_PAYABLE__BILL_TYPE");
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HK) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_REPAYMENT__BILL_TYPE");
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__US) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_DEPOSIT_RECEIVED__BILL_TYPE");
			// 对账单没有单据子类型
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__DZ) {

		}else if(record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__UF){//预付单
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
			"BILL_ADVANCED_PAYMENT__BILL_TYPE");
		}  else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__FK) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_PAYMENT__BILL_TYPE");
			// 小票没有单据子类型
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__XP) {
			// 坏账没有单据子类型
		} else if (record.get('billParentType') == mvrAllDetails.SETTLEMENT__BILL_PARENT_TYPE__HZ) {

		} else {
			displayField = value;
		}
		return displayField;
	}
</script>
<body>
<div id="mainAreaPanel">
		<div id="T_closing-mvrAllDetails-body"></div>
	</div>
</body>