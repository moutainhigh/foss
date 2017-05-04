<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >
/**
 * 该js是提供结算公共方法
 */
var settlementDict = {};

//--------------------------------------------FOSS-----------------------------------------------
/**
 * 是否有效
 */
settlementDict.FOSS_ACTIVE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@FOSS_ACTIVE"/>';

//--------------------------------------------结算公用-----------------------------------------------
/**
 * 是否红单
 */
settlementDict.SETTLEMENT__IS_RED_BACK = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@SETTLEMENT__IS_RED_BACK"/>';

/**
 * 生成方式
 */
settlementDict.SETTLEMENT__CREATE_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@SETTLEMENT__CREATE_TYPE"/>';

//--------------------------------------------外围系统-----------------------------------------------

/**
 * 提货方式(汽运)  --接送货
 */
settlementDict.PICKUP_GOODS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@PICKUP_GOODS"/>';

/**
 * 提货方式(空运)  --接送货
 */
settlementDict.PICKUP_GOODS_AIR = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@PICKUP_GOODS_AIR"/>';

/**
 * 更改单核销状态  --接送货
 */
settlementDict.BILL_WAYLLBAY_WRITEOFF_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_WAYLLBAY_WRITEOFF_STATUS"/>';


//---------------------------------------------应收单-------------------------------------------------
/**
 * 审核状态
 */
settlementDict.BILL_RECEIVABLE__APPROVE_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_RECEIVABLE__APPROVE_STATUS"/>';

/**
 * 单据子类型
 */
settlementDict.BILL_RECEIVABLE__BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_RECEIVABLE__BILL_TYPE"/>';

/**
 * 收款类别
 */
settlementDict.BILL_RECEIVABLE__COLLECTION_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_RECEIVABLE__COLLECTION_TYPE"/>';

/**
 * 来源单据类型
 */
settlementDict.BILL_RECEIVABLE__SOURCE_BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_RECEIVABLE__SOURCE_BILL_TYPE"/>';


//----------------------------------------------应付单-----------------------------------------------

/**
 *单据子类型
 */
settlementDict.BILL_PAYABLE__BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYABLE__BILL_TYPE"/>';

/**
 *生效状态
 */
settlementDict.BILL_PAYABLE__EFFECTIVE_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYABLE__EFFECTIVE_STATUS"/>';

/**
 *审核状态
 */
settlementDict.BILL_PAYABLE__APPROVE_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYABLE__APPROVE_STATUS"/>';

/**
 *冻结状态
 */
settlementDict.BILL_PAYABLE__FROZEN_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYABLE__FROZEN_STATUS"/>';

/**
 *支付状态
 */
settlementDict.BILL_PAYABLE__PAY_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYABLE__PAY_STATUS"/>';

/**
 *应付单应付类型
 */
settlementDict.BILL_PAYABLE__PAYABLE_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYABLE__PAYABLE_TYPE"/>';


//----------------------------------------------预收单----------------------------------------
/**
 * 单据子类型
 */
settlementDict.BILL_DEPOSIT_RECEIVED__BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_DEPOSIT_RECEIVED__BILL_TYPE"/>';

/**
 * 单据状态
 */
settlementDict.BILL_DEPOSIT_RECEIVED__STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_DEPOSIT_RECEIVED__STATUS"/>';

/**
 * 运输类型
 */
settlementDict.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE"/>';

//----------------------------------------------预付单-----------------------------------------
/**
 * 单据子类型
 */
settlementDict.BILL_ADVANCED_PAYMENT__BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_ADVANCED_PAYMENT__BILL_TYPE"/>';

/**
 * 审核状态
 */
settlementDict.BILL_ADVANCED_PAYMENT__AUDIT_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_ADVANCED_PAYMENT__AUDIT_STATUS"/>';


//---------------------------------------------现金收款单--------------------------------------
/**
 * 单据状态
 */
settlementDict.BILL_CASH_COLLECTION__STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_CASH_COLLECTION__STATUS"/>';

/**
 * 来源单据类型
 */
settlementDict.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_CASH_COLLECTION__SOURCE_BILL_TYPE"/>';

//---------------------------------------------还款单--------------------------------------
/**
 * 单据子类型
 */
settlementDict.BILL_REPAYMENT__BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_REPAYMENT__BILL_TYPE"/>';

//---------------------------------------------付款单--------------------------------------

/**
 * 付款单CRM账户类型
 */
settlementDict.CRM_ACCOUNT_NATURE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@CRM_ACCOUNT_NATURE"/>';

/**
 * 付款单FIN账户类型
 */
settlementDict.FIN_ACCOUNT_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@FIN_ACCOUNT_TYPE"/>';

/**
 * 付款单对公对私标志--代收货款
 */
settlementDict.COD__PUBLIC_PRIVATE_FLAG = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@COD__PUBLIC_PRIVATE_FLAG"/>';

/**
 * 付款单汇款状态
 */
settlementDict.BILL_PAYMENT__REMIT_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYMENT__REMIT_STATUS"/>';

/**
 * 来源单据类型
 */
settlementDict.BILL_PAYMENT__SOURCE_BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYMENT__SOURCE_BILL_TYPE"/>';

/**
 * 审核状态
 */
settlementDict.BILL_PAYMENT__AUDIT_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_PAYMENT__AUDIT_STATUS"/>';

//----------------------------------------------对账单-----------------------------------------
/**
 * 对账单类型
 */
settlementDict.STATEMENT_OF_ACCOUNT__BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@STATEMENT_OF_ACCOUNT__BILL_TYPE"/>';

/**
 * 确认状态
 */
settlementDict.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@STATEMENT_OF_ACCOUNT__CONFIRM_STATUS"/>';

/**
 * 支付方式
 */
settlementDict.SETTLEMENT__PAYMENT_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@SETTLEMENT__PAYMENT_TYPE"/>';

/**
 * 单据父类型
 */
settlementDict.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE"/>';
//------------------------------------------------代收货款-----------------------------------------
/**
 * 代收货款状态
 */
settlementDict.COD__STATUS = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@COD__STATUS"/>';

/**
 * 代收货款类型
 */
settlementDict.COD__COD_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@COD__COD_TYPE"/>';

/**
 * 对公对私标志
 */
settlementDict.COD__PUBLIC_PRIVATE_FLAG = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@COD__PUBLIC_PRIVATE_FLAG"/>';

/**
 * 代收货款退款路径
 */
settlementDict.COD__REFUND_PATH = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@COD__REFUND_PATH"/>';

/**
 * 代收货款操作类型
 */
settlementDict.COD_LOG__OPERATE_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@COD_LOG__OPERATE_TYPE"/>';


//-------------------------------------------------理赔单-----------------------------------------
/**
 * 理赔单类型
 */
settlementDict.BILL_CLAIM__TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_CLAIM__TYPE"/>';

/**
 * 理赔单支付类别
 */
settlementDict.BILL_CLAIM__PAYMENT_CATEGORIES = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_CLAIM__PAYMENT_CATEGORIES"/>';

//-------------------------------------------------核销单-----------------------------------------
/**
 * 核销类型
 */
settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@BILL_WRITEOFF__WRITEOFF_TYPE"/>';


//-------------------------------------------------确认收银-----------------------------------------
/**
 * 单据子类型
 */
settlementDict.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE"/>';

//-------------------------------------------------司机缴款报表----------------------------------------
/**
 * 明细类型（接货/送货）
 */
settlementDict.DRIVER_COLLECTION_RPT_D__TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@DRIVER_COLLECTION_RPT_D__TYPE"/>';


//-------------------------------------------------操作日志-----------------------------------------
/**
 * 操作类型
 */
settlementDict.OPERATING_LOG__OPERATE_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@OPERATING_LOG__OPERATE_TYPE"/>';

/**
 * 单据类型
 */
settlementDict.OPERATING_LOG__OPERATE_BILL_TYPE = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@OPERATING_LOG__OPERATE_BILL_TYPE"/>';

/**
 * 是否初始化
 */
settlementDict.FOSS_BOOLEAN = '<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@FOSS_BOOLEAN"/>';
 
 /**
 	客户类型
 */
settlementDict.SETTLEMENT__CUSTOMER_TYPE='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.DictionaryConstants@SETTLEMENT__CUSTOMER_TYPE"/>';
 
 /**状态
 */
settlementDict.BILL_CASH_COLLECTION__STATUS='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.DictionaryConstants@BILL_CASH_COLLECTION__STATUS"/>';


/**
 * 币种
 */
settlementDict.SETTLEMENT__CURRENCY_CODE='<s:property value="@com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants@SETTLEMENT__CURRENCY_CODE"/>';

/**
 * 还款单的来源单据类型
 */
 settlementDict.BILL_REPAYMENT__SOURCE_BILL_TYPE='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_REPAYMENT__SOURCE_BILL_TYPE"/>';
 
 /**
 还款单状态
 */
 settlementDict.BILL_REPAYMENT__STATUS='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_REPAYMENT__STATUS"/>';
 
 /**
 还款单审核状态
 */
 settlementDict.BILL_REPAYMENT__AUDIT_STATUS='<s:property value="@com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants@BILL_REPAYMENT__AUDIT_STATUS"/>';
 
</script>