package com.deppon.foss.module.settlement.common.api.shared.exception;

/**
 * 结算异常公用类 ---异常Code编码定义类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-wujiangtao,date:2012-10-18 下午5:28:10,content:
 * </p>
 * @author dp-wujiangtao
 * @date 2012-10-18 下午5:28:10
 */
public class SettlementExceptionType {

	/**
	 * DAO 返回对象为空
	 */
	public static final String DAO_RETURN_OBJECT_ISNULL_ERROR = "foss.stl.error.common.returnObject.isnull";

	/**
	 * 参数为空
	 */
	public static final String PARAMS_OBJECT_ISNULL_ERROR = "foss.stl.error.common.params.isnull";

	/**
	 * 内部错误，无效更新
	 */
	public static final String UPDATE_DAO_INVALID_ERROR = "foss.stl.error.common.update.invalid";

	/**
	 * 运单号为空异常
	 */
	public static final String WAYBILL_NO_IS_EMPTY_ERROR = "foss.stl.error.waybillNo.isEmpty";// 运单号为空异常

	/**
	 * 来源单号为空
	 */
	public static final String SOURCEBILLNO_IS_EMPTY_ERROR = "foss.stl.error.sourceBillNoIsEmpty";// 来源单号为空

	/**
	 * 运单信息不存在异常
	 */
	public static final String WAYBILL_IS_NOT_EXISTS_ERROR = "foss.stl.error.waybill.isNotExists";// 运单信息不存在异常

	/**
	 * 客户编码为空异常
	 */
	public static final String CUSTOMER_CODE_IS_EMPTY_ERROR = "foss.stl.error.customerCode.isEmpty";// 客户编码为空异常

	/**
	 * 客户名称为空异常
	 */
	public static final String CUSTOMER_NAME_IS_EMPTY_ERROR = "foss.stl.error.customerName.isEmpty";// 客户名称为空异常

	/**
	 * 客户编码不一致
	 */
	public static final String CUSTOMER_CODE_NOT_SAME_ERROR = "foss.stl.error.customerCode.notSame";// 客户编码不一致

	/**
	 * 客户信息不存在异常
	 */
	public static final String CUSTOMER_IS_NOT_EXISTS_ERROR = "foss.stl.error.customer.isNotExists";// 客户信息不存在异常

	/**
	 * 客户超期欠款异常
	 */
	public static final String CUSTOMER_OVERDUE_ARREARS_ERROR = "foss.stl.error.customer.overdueArrears";// 客户超期欠款异常

	/**
	 * 客户信用额度不够异常
	 */
	public static final String CUSTOMER_CREDIT_APPROVED_NOT_ENOUGH_ERROR = "foss.stl.error.customer.creditApprovedNotEnough";// 客户信用额度不够异常

	/**
	 * 申请客户信用额度小于等于0
	 */
	public static final String CUSTOMER_CREDIT_IS_LESS_OR_EQUAL_ZERO = "foss.stl.error.customer.creditIsLessOrEqualZero";

	/**
	 * 始发部门编码为空异常
	 */
	public static final String ORIG_ORG_CODE_IS_EMPTY_ERROR = "foss.stl.error.origOrgCode.isEmpty";// 始发部门编码为空异常

	/**
	 * 到达部门编码为空异常
	 */
	public static final String DEST_ORG_CODE_IS_EMPTY_ERROR = "foss.stl.error.destOrgCode.isEmpty";// 到达部门编码为空异常

	/**
	 * 部门编码为空异常
	 */
	public static final String ORG_CODE_IS_EMPTY_ERROR = "foss.stl.error.orgCode.isEmpty";// 部门编码为空异常

	/**
	 * 到达部门不存在异常
	 */
	public static final String DEST_ORG_IS_NOT_EXISTS_ERROR = "foss.stl.error.destOrg.isNotExists";// 到达部门不存在异常

	/**
	 * 始发部门不存在异常
	 */
	public static final String ROIG_ORG_IS_NOT_EXISTS_ERROR = "foss.stl.error.origOrg.isNotExists";// 始发部门不存在异常

	/**
	 * 到达部门非专线到达网点（部门）
	 */
	public static final String DEST_ORG_IS_NOT_LINE_DEST_ORG_ERROR = "foss.stl.error.destOrg.isNotLineDestOrg";// 到达部门非专线到达网点（部门）

	/**
	 * 存在未受理的更改单
	 */
	public static final String EXISTS_NOT_ACCP_CHANGE_BILL_ERROR = "foss.stl.error.waybill.NotAccpChangeBill";// 存在未受理的更改单

	/**
	 * 付款方式为空异常
	 */
	public static final String PAYMENT_TYPE_IS_EMPTY_ERROR = "foss.stl.error.paymentTypeIsEmpty";// 付款方式为空异常

	/**
	 * 汇款编号为空异常
	 */
	public static final String PAYMENT_NO_IS_EMPTY_ERROR = "foss.stl.error.paymentNoIsEmpty";// 汇款编号为空异常

	/************************************************** 应收单 ****************************************************/

	/**
	 * 保存应收单异常
	 */
	public static final String INSERT_BILL_RECEIVABLE_ERROR = "foss.stl.error.InsertBillReceivable";// 保存应收单异常

	/**
	 * 保存红单-应收单异常
	 */
	public static final String INSERT_RED_BACK_BILL_RECEIVABLE_ERROR = "foss.stl.error.InsertRedBackBillReceivable";// 保存红单-应收单异常

	/**
	 * 保存蓝单-应收单异常
	 */
	public static final String INSERT_BLUE_BILL_RECEIVABLE_ERROR = "foss.stl.error.InsertBlueBillReceivable";// 保存蓝单-应收单异常

	/**
	 * 修改应收单异常
	 */
	public static final String UPDATE_BILL_RECEIVABLE_ERROR = "foss.stl.error.UpdateBillReceivable";// 修改应收单异常

	/**
	 * 修改作废原应收单
	 */
	public static final String UPDATE_BILL_RECEIVABLE_ACTIVE_ERROR = "foss.stl.error.UpdateBillReceivableActive";// 修改作废原应收单

	/**
	 * 应收单被锁定异常
	 */
	public static final String BILL_RECEIVABLE_IS_LOCK_ERROR = "foss.stl.error.billReceivableIsLock";// 应收单被锁定异常

	/**
	 * 应收单已确认收入（已签收）异常
	 */
	public static final String BILL_RECEIVABLE_IS_SIGN_ERROR = "foss.stl.error.billReceivableIsSign";// 应收单已确认收入（已签收）异常

	/**
	 * 核销时应收单被锁定
	 */
	public static final String WRITEOFF_BILL_RECEIVABLE_LOCKED_ERROR = "foss.stl.error.WriteOffBillReceivableLocked";// 核销时应收单被锁定

	/**
	 * 核销时应收单存在更改单
	 */
	public static final String WRITEOFF_BILL_RECEIVABLE_EXISTS_WAYBILLRFC_ERROR = "foss.stl.error.WriteOffBillReceivableWayBillRfc";// 核销时应收单存在更改单

	/**
	 * 不存在****应收单
	 */
	public static final String NOT_EXISTS_BILL_RECEIVABLE_ERROR = "foss.stl.error.notExistsBillReceivable";// 不存在****应收单

	/**
	 * 存在多条****应收单
	 */
	public static final String EXISTS_MANY_BILL_RECEIVABLE_ERROR = "foss.stl.error.existsManyBillReceivable";// 存在多条****应收单

	/**************************************************** 还款单 *******************************************************/

	/**
	 * 新增还款单异常
	 */
	public static final String INSERT_BILL_REPAYMENT_ERROR = "foss.stl.error.insertBillRepayment";// 新增还款单异常

	/**
	 * 保存红单还款单异常
	 */
	public static final String INSERT_RED_BACK_BILL_REPAYMENT_ERROR = "foss.stl.error.insertRedBackBillRepayment";// 保存红单还款单异常

	/************************************************** 应付单 *******************************************************/
	/**
	 * 应付单付款中异常
	 */
	public static final String BILL_PAYABLE_PAYING_ERROR = "foss.stl.error.billPayablePaying";

	/**
	 * 应付单已付款异常
	 */
	public static final String BILL_PAYABLE_PAID_ERROR = "foss.stl.error.billPayablePaid";

	/************************************* 应收单到付运费结转临欠/月结 异常 **************************************************/

	/**
	 * 接口传入的数据为空
	 */
	public static final String INTERFACE_AFFERFENT_DATA_IS_EMPTY = "foss.stl.error.interfaceAfferfentDataIsEmpty";// 接口传入的数据为空

	/**
	 * 不存在到付运费应收单
	 */
	public static final String CFBR_IS_NOT_EXISTS_FC_BILL_RECEIVABLE = "foss.stl.error.isNotExistsFcBillReceivable";// 不存在到付运费应收单

	/**
	 * 付款方式不为临欠或月结异常
	 */
	public static final String CFBR_PAYMENT_TYPE_IS_NOT_DEBT_OR_CREDIT_ERROR = "foss.stl.error.paymentTypeIsNotDebtOrCredit";// 付款方式不为临欠或月结异常

	/***
	 * 存在多个到达应收单异常
	 */
	public static final String CFBR_EXISTS_MANY_DESTINATION_RECEIVABLE_ERROR = "foss.stl.error.existsManyDestinationReceivable";// 存在多个到达应收单异常

	/**
	 * 已存在付款方式为临欠到达运费应收单
	 */
	public static final String CFBR_EXISTS_PAYMENTTYPE_IS_DEBT_DR_RECEIVABLE_ERROR = "foss.stl.error.existsPaymentTypeIsDebtDrReceivable";// 已存在付款方式为临欠到达运费应收单

	/**
	 * 已存在付款方式为月结到达运费应收单
	 */
	public static final String CFBR_EXISTS_PAYMENTTYPE_IS_CREDIT_DR_RECEIVABLE_ERROR = "foss.stl.error.existsPaymentTypeIsCreditDrReceivable";// 已存在付款方式为月结到达运费应收单

	/**
	 * 已存在付款方式为到付的到达运费应收单
	 */
	public static final String CFBR_EXISTS_PAYMENTTYPE_IS_FREIGHT_COLLECT_DR_RECEIVABLE_ERROR = "foss.stl.error.ExistsPaymentTypeIsFreightCollectDrReceivable";// 已存在付款方式为到付的到达运费应收单

	/**
	 * 到付运费应收单的已核销金额大于0
	 */
	public static final String CFBR_FC_BILL_RECEIVABLE_WRIETE_OFF_AMOUNT_GTZERO = "foss.stl.error.fcBillReceivable.writeOffAmountGtZero";// 到付运费应收单的已核销金额大于0

	/**
	 * 查询到的应收单的应收部门，和传入的到达部门不一致,不能进行到付运费转临欠月结操作
	 */
	public static final String CFBR_BILL_RECEIVABLE_ORG_CODE_INCONSISTENT_ERROR = "foss.stl.error.billReceivableOrgCodeInconsistent";// 查询到的应收单的应收部门，和传入的到达部门不一致,不能进行到付运费转临欠月结操作

	/**
	 * 到付运费应收单挂账客户已经为具体客户，不能重复调用到付运费转临欠月结接口
	 */
	public static final String CFBR_FC_BILL_RECEIVABLE_IS_CUSTOMER_ERROR = "foss.stl.error.fcBillReceivableIsCustomer";// 到付运费应收单挂账客户已经为具体客户，不能重复调用到付运费转临欠月结接口

	/**
	 * 到付运费应收单挂已是非具体客户时,挂（各个部门所在的临时客户）
	 */
	public static final String CFBR_FC_BILL_RECEIVABLE_IS_TEMP_CUSTOMER_ERROR = "foss.stl.error.fcBillReceivableIsTempCustomer";// 到付运费应收单挂已是非具体客户时,挂（各个部门所在的临时客户）

	/*********************************************** 到达实收货款(包含：反) ***********************************************************************/

	/**
	 * 实收代收货款费用和实收到达运费金额小于等于0
	 */
	public static final String CTP_COD_FEE_AND_TOPAY_FEE_IS_LT_EQUAL_ZERO = "foss.stl.error.codFeeAndTopayFeeIsLtEqualZero";// 实收代收货款费用和实收到达运费金额小于等于0

	/**
	 * 实收代收货款费用小于等于0
	 */
	public static final String CTP_COD_FEE_IS_LT_EQUAL_ZERO = "foss.stl.error.codFeeIsLtEqualZero";// 实收代收货款费用小于等于0

	/**
	 * 实收到达运费金额小于等于0
	 */
	public static final String CTP_TOPAY_FEE_IS_LT_EQUAL_ZERO = "foss.stl.error.topayFeeIsLtEqualZero";// 实收到达运费金额小于等于0

	/**
	 * 付款方式不能为临欠或月经
	 */
	public static final String CTP_PAYMENT_TYPE_IS_NOT_DEBT_OR_CREDIT_ERROR = "foss.stl.error.ctpPaymentTypeIsNotDebtOrCredit";// 付款方式不能为临欠或月经

	/**
	 * 存在多条代收货款应收单
	 */
	public static final String CTP_EXISTS_MANY_COD_BILL_RECEIVABLE_ERROR = "foss.stl.error.existsManyCodBillReceivable";// 存在多条代收货款应收单

	/**
	 * 存在多条到达运费应收单
	 */
	public static final String CTP_EXISTS_MANY_DR_BILL_RECEIVABLE_ERROR = "foss.stl.error.existsManyDrBillReceivable";// 存在多条到达运费应收单

	/***
	 * ****应收单已核销,未核销金额等于0
	 */
	public static final String CTP_BILL_RECEIVABLE_UNVERIFY_AMOUNT_ISZERO_ERROR = "foss.stl.error.billReceivableUnverifyAmountIsZero";// ****应收单已核销,未核销金额等于0

	/**
	 * 实收金额大于未核销金额
	 */
	public static final String CTP_PAID_IN_AMOUNT_GT_UNVERIFY_AMOUNT_ERROR = "foss.stl.error.paidInAmountGtUnverifyAmount";// 实收金额大于未核销金额

	/**
	 * 已存在相同批次号(实收单号 暂定)的有效的还款单
	 */
	public static final String CTP_EXISTS_SAME_BATCH_NO_BILL_REPAYMENT_ERROR = "foss.stl.error.existsSameBatchNoBillRepayment";// 已存在相同批次号(实收单号
																															   // 暂定)的有效的还款单

	/**
	 * 应付单的已核销金额大于0
	 */
	public static final String CTP_BILL_PAYABLE_VERIFY_AMOUNT_GT_ZERO_ERROR = "foss.stl.error.billPayableVerifyAmountGtZero";// 应付单的已核销金额大于0

	/**
	 * 实收货款金额为空
	 */
	public static final String CTP_PAID_IN_FEE_IS_EMPTY="foss.stl.error.paidInFeeIsEmpty";//实收货款金额为空
	
	/**
	 * 不存在还款单异常
	 */
	public static final String NOT_EXISTS_BILL_REPAYMENT_ERROR="foss.stl.error.notExistsBillRepayment";
	
	
	/************************************************ 对账单 *********************************************************************/
	/**
	 * 对账单明细单据已无效或发生核销
	 */
	public static final String STATEMENT_OF_ACCOUNT_DETAIL_CHANGE_ERROR = "foss.stl.error.statementOfAccount.changed";// 对账单明细单据已无效或发生核销

	/**
	 * 代收货款非资金部冻结
	 */
	public static final String COD_STATUS_NOT_FUND_FREEZE = "foss.stl.error.codStatusNotFundFreeze";
	
	/**
	 * 代收货款已经资金部冻结
	 */
	public static final String COD_STATUS_ALREADY_FUND_FREEZE = "foss.stl.error.codStatusAlreadyFundFreeze";

	/**
	 * 根据运单号找不到对应的代收货款应付单
	 */
	public static final String COD_PAYABLE_BILL_NOT_FOUND_BY_WAYBILL_NO = "foss.stl.error.payableBillNotFoundByWaybillNo";

	/**
	 * 根据运单号找到的代收货款应付单不唯一
	 */
	public static final String COD_PAYABLE_BILL_NOT_QNIQUE_BY_WAYBILL_NO = "foss.stl.error.payableBillNotQniqueByWaybillNo";
	
	
	
	/************************************************ 小票单据 *********************************************************************/
	
	/**
	 * 下发起止与终止编号不能重复
	 */
	public static final String NOTE_STOCKIN_BY_NO = "foss.stl.error.noteStockInByNo";
	
	
	/*****************************************确认签收******************************************************************/ 
	
	/**
	 * 接口传入的数据为空
	 */
	public static final String CONFIRMINCOME_INTERFACE_AFFERFENT_DATA_IS_EMPTY = "foss.stl.error.interfaceAfferfentDataIsEmpty";// 接口传入的数据为空
	
}
