<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
	mvrDetail = {}
	
	//单据父类型
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__XS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XS"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__YS = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__YS"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__YF = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__YF"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__HK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HK"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__US = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__US"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__DZ = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__DZ"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__FK = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__FK"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__XP = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__XP"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__HZ = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__HZ"/>';
	mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__UF = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__BILL_PARENT_TYPE__UF"/>';
	
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER"/>';
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__AIR = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR"/>';
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY"/>';
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY"/>';
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE"/>';
	
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__DRIVER = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__DRIVER"/>';
	mvrDetail.SETTLEMENT__CUSTOMER_TYPE__PACKAGENCE = '<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@SETTLEMENT__CUSTOMER_TYPE__PACKAGENCE"/>';

	mvrDetail.rptType_RFO = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFO"/>';
	mvrDetail.rptType_PLI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_PLI"/>';
	mvrDetail.rptType_AFI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_AFI"/>';
	mvrDetail.rptType_RFI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFI"/>';
	mvrDetail.rptType_AFR = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_AFR"/>';
	mvrDetail.rptType_RFD = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_RFD"/>';
	mvrDetail.rptType_PLR = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_PLR"/>';
	mvrDetail.rptType_LDD = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LDD"/>';
	mvrDetail.rptType_LDI = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LDI"/>';
	mvrDetail.rptType_LWO = '<s:property value="@com.deppon.foss.module.settlement.closing.api.shared.define.VDRptTypes@TYPE_LWO"/>';

	/**
	 * 格式化分录的单据子类型
	 */
	mvrDetail.billTypeToConvert = function(value, record) {
		var displayField = value;
		// 单据子类型转化 --应收
		if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__XS) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_CASH_COLLECTION__BILL_TYPE");
		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__YS) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_RECEIVABLE__BILL_TYPE");
		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__YF) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_PAYABLE__BILL_TYPE");
		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__HK) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_REPAYMENT__BILL_TYPE");
		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__US) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_DEPOSIT_RECEIVED__BILL_TYPE");
			// 对账单没有单据子类型
		}else if(record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__UF){//预付单
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
			"BILL_ADVANCED_PAYMENT__BILL_TYPE");
		}else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__DZ) {

		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__FK) {
			displayField = FossDataDictionary.rendererSubmitToDisplay(value,
					"BILL_PAYMENT__BILL_TYPE");
			// 小票没有单据子类型
		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__XP) {
			// 坏账没有单据子类型
		} else if (record.get('billParentType') == mvrDetail.SETTLEMENT__BILL_PARENT_TYPE__HZ) {

		} else {
			displayField = value;
		}
		return displayField;
	}
</script>