CREATE OR REPLACE PACKAGE PKG_STL_CASH_COMMON IS
	-- Author  : ZHUWEI
	-- Created : 2012-11-19 11:06:22
	-- Purpose : 常量以及通用函数

  --========================== 通用常量定义 ================================
  MAX_PAGE_ROW_SIZE NUMBER := 1000; --最大页行数，用于批量插入，减少每次操作的行数
	DEFAULT_USER_CODE VARCHAR2(20) := 'STL'; --结算后台用户编码
  DEFAULT_BILL_NO VARCHAR2(20) := 'N/A'; -- 默认单号


	-- ==================== 结算通用 SETTLEMENT ====================
	/**
  * 布尔类型
  */
	YES VARCHAR2(20) := 'Y';
	NO  VARCHAR2(20) := 'N';
	/**
  * 生效/未生效
  */
	ACTIVE   VARCHAR2(20) := 'Y';
	INACTIVE VARCHAR2(20) := 'N';
	/**
  * IS_RED_BACK 是否红单
  */
	IS_RED_BACK_YES VARCHAR2(20) := 'Y'; -- 是
	IS_RED_BACK_NO  VARCHAR2(20) := 'N'; -- 否
	/**
  * CREATE_TYPE 单据生成方式
  */
	CREATE_TYPE_AUTO   VARCHAR2(20) := 'A'; -- 系统生成
	CREATE_TYPE_MANUAL VARCHAR2(20) := 'M'; -- 手工输入
	/**
  * PAYMENT_TYPE 支付方式
  */
	PAYMENT_TYPE_CASH            VARCHAR2(20) := 'CH'; -- 现金
	PAYMENT_TYPE_CARD            VARCHAR2(20) := 'CD'; -- 银行卡
	PAYMENT_TYPE_TELE_TRANSFER   VARCHAR2(20) := 'TT'; -- 电汇
	PAYMENT_TYPE_NOTE            VARCHAR2(20) := 'NT'; -- 支票
	PAYMENT_TYPE_ONLINE          VARCHAR2(20) := 'OL'; -- 网上支付
	PAYMENT_TYPE_CREDIT          VARCHAR2(20) := 'CT'; -- 月结
	PAYMENT_TYPE_DEBT            VARCHAR2(20) := 'DT'; -- 临时欠款
	PAYMENT_TYPE_FREIGHT_COLLECT VARCHAR2(20) := 'FC'; -- 到付

	-- ==================== 应收单 BILL_RECEIVABLE ====================
	/**
  * BILL_TYPE 单据子类型
  */
	RECEIVABLE_BILL_TYPE_ORIGIN  VARCHAR2(20) := 'OR'; -- 始发应收
	RECEIVABLE_BILL_TYPE_DEST    VARCHAR2(20) := 'DR'; -- 到达应收
	RECEIVABLE_BILL_TYPE_COD     VARCHAR2(20) := 'CR'; -- 代收货款应收
	RECEIVABLE_BILL_TYPE_AR      VARCHAR2(20) := 'AR'; -- 空运其他应收
	RECEIVABLE_BILL_TYPE_REVENUE VARCHAR2(20) := 'RR'; -- 小票应收
	RECEIVABLE_BILL_TYPE_PARTIAL VARCHAR2(20) := 'DP'; -- 到达偏线代理应收单
	RECEIVABLE_BILL_TYPE_AA      VARCHAR2(20) := 'AA'; -- 空运中转代理应收
	/**
  * SOURCE_BILL_TYPE 来源单据子类型
  */
	RECEIVABLE_SOURCE_BILL_TYPE_W VARCHAR2(20) := 'W'; -- 运单
	RECEIVABLE_SOURCE_BILL_TYPE_R VARCHAR2(20) := 'R'; -- 小票
	RECEIVABLE_SOURCE_BILL_TYPE_M VARCHAR2(20) := 'M'; -- 人工录入
	/**
  * PAYMENT_TYPE 付款方式（参考运单中对应的付款方式）
  */
	RECEIVABLE_PAYMENT_TYPE_DEBT VARCHAR2(20) := 'DT'; -- 临欠
	RECEIVABLE_PAYMENT_TYPE_CT   VARCHAR2(20) := 'CT'; -- 月结
	RECEIVABLE_PAYMENT_TYPE_FC   VARCHAR2(20) := 'FC'; -- 到付
	RECEIVABLE_PAYMENT_TYPE_OL   VARCHAR2(20) := 'OL'; -- 网上支付
	/**
  * COLLECTION_TYPE 收款类别（小票的收款类别）
  */
	RECEIVABLE_COLLECTION_TYPE_W  VARCHAR2(20) := 'W'; -- 仓储费
	RECEIVABLE_COLLECTION_TYPE_PD VARCHAR2(20) := 'PD'; -- 自提改派送
	RECEIVABLE_COLLECTION_TYPE_D  VARCHAR2(20) := 'D'; -- 加收送货费
	RECEIVABLE_COLLECTION_TYPE_C  VARCHAR2(20) := 'C'; -- 会员卡费
	RECEIVABLE_COLLECTION_TYPE_P  VARCHAR2(20) := 'P'; -- 包装费
	RECEIVABLE_COLLECTION_TYPE_F  VARCHAR2(20) := 'F'; -- 放空费
	RECEIVABLE_COLLECTION_TYPE_R  VARCHAR2(20) := 'R'; -- 卖废品
	RECEIVABLE_COLLECTION_TYPE_E  VARCHAR2(20) := 'E'; -- 超期预收款
	RECEIVABLE_COLLECTION_TYPE_O  VARCHAR2(20) := 'O'; -- 其他
	/**
  * APPROVE_STATUS 审核状态
  */
	RECEIVABLE_APPROVE_STATUS_NA VARCHAR2(20) := 'NA'; -- 未审核
	RECEIVABLE_APPROVE_STATUS_AA VARCHAR2(20) := 'AA'; -- 已审核

	-- ==================== 应付单 BILL_PAYABLE ====================
	/**
  * BILL_TYPE 单据子类型
  */
	PAYABLE_BILL_TYPE_APC          VARCHAR2(20) := 'APC'; -- 应付代收货款
	PAYABLE_BILL_TYPE_TRUCK1_FIRST VARCHAR2(20) := 'TF1'; -- 外请车首款
	PAYABLE_BILL_TYPE_TRUCK1_LAST  VARCHAR2(20) := 'TL1'; -- 外请车尾款
	PAYABLE_BILL_TYPE_TRUCK2_FIRST VARCHAR2(20) := 'TF2'; -- 整车首款
	PAYABLE_BILL_TYPE_TRUCK2_LAST  VARCHAR2(20) := 'TL2'; -- 整车尾款
	PAYABLE_BILL_TYPE_AIR          VARCHAR2(20) := 'A'; -- 航空公司运费
	PAYABLE_BILL_TYPE_AIR_OTHER    VARCHAR2(20) := 'AO'; -- 空运其他应付
	PAYABLE_BILL_TYPE_AAD          VARCHAR2(20) := 'AAD'; -- 空运其他应收应付
	PAYABLE_BILL_TYPE_PARTIAL_LINE VARCHAR2(20) := 'PL'; -- 偏线代理成本
	PAYABLE_BILL_TYPE_SERVICE_FEE  VARCHAR2(20) := 'SF'; -- 劳务费应付
	PAYABLE_BILL_TYPE_CLAIM        VARCHAR2(20) := 'C'; -- 理赔应付
	PAYABLE_BILL_TYPE_REFUND       VARCHAR2(20) := 'R'; -- 退运费应付
	PAYABLE_BILL_TYPE_COMPENSATION VARCHAR2(20) := 'CP'; -- 服务补救应付
	/**
  * SOURCE_BILL_TYPE 来源单据子类型
  */
	PAYABLE_SOURCE_BILL_TYPE_W  VARCHAR2(20) := 'W'; -- 运单
	PAYABLE_SOURCE_BILL_TYPE_RS VARCHAR2(20) := 'RS'; -- 汽运配置单
	PAYABLE_SOURCE_BILL_TYPE_AW VARCHAR2(20) := 'AW'; -- 航空正单
	PAYABLE_SOURCE_BILL_TYPE_TP VARCHAR2(20) := 'TP'; -- 中转提货清单
	PAYABLE_SOURCE_BILL_TYPE_AP VARCHAR2(20) := 'AP'; -- 合大票清单
	PAYABLE_SOURCE_BILL_TYPE_PL VARCHAR2(20) := 'PL'; -- 外发单
	/**
  * EFFECTIVE_STATUS 生效状态
  */
	PAYABLE_EFFECTIVE_STATUS_YES VARCHAR2(20) := 'Y'; -- 已生效
	PAYABLE_EFFECTIVE_STATUS_NO  VARCHAR2(20) := 'N'; -- 未生效
	/**
  * PAY_STATUS 支付状态
  */
	PAYABLE_PAY_STATUS_YES VARCHAR2(20) := 'Y'; -- 已支付
	PAYABLE_PAY_STATUS_NO  VARCHAR2(20) := 'N'; -- 未支付
	/**
  * PAYMENT_STATUS 付款状态
  */
	PAYABLE_PAYMENT_STATUS_NOT_PAY VARCHAR2(20) := 'NP'; -- 未付款
	PAYABLE_PAYMENT_STATUS_PAYING  VARCHAR2(20) := 'PG'; -- 付款中
	PAYABLE_PAYMENT_STATUS_PAID    VARCHAR2(20) := 'PD'; -- 已付款
	/**
  * PAYER_TYPE 付款方
  */
	PAYABLE_PAYER_TYPE_ORIGIN      VARCHAR2(20) := 'O'; -- 出发付款
	PAYABLE_PAYER_TYPE_DESTINATION VARCHAR2(20) := 'D'; -- 到达付款
	/**
  * PAYABLE_TYPE 应付类型
  */
	PAYABLE_PAYABLE_TYPE_FIRST VARCHAR2(20) := 'F'; -- 首款
	PAYABLE_PAYABLE_TYPE_LAST  VARCHAR2(20) := 'L'; -- 尾款
	/**
  * APPROVE_STATUS 审核状态
  */
	PAYABLE_APPROVE_STATUS_NA VARCHAR2(20) := 'NA'; -- 未审核
	PAYABLE_APPROVE_STATUS_AA VARCHAR2(20) := 'AA'; -- 已审核

	/**
  * FROZEN_STATUS 冻结状态
  */
	PAYABLE_FROZEN_STATUS_F VARCHAR2(20) := 'F'; -- 已冻结
	PAYABLE_FROZEN_STATUS_N VARCHAR2(20) := 'N'; -- --未冻结

	-- ==================== 现金收款单 BILL_CASH_COLLECTION ====================
	/**
  * BILL_TYPE 单据类型
  */
	CASH_COLL_BILL_TYPE_C VARCHAR2(20) := 'C'; -- 现金收款单
	/**
  * SOURCE_BILL_TYPE 来源单据子类型
  */
	CASH_COLL_SOURCE_BILL_TYPE_W VARCHAR2(20) := 'W'; -- 运单
	CASH_COLL_SOURCE_BILL_TYPE_R VARCHAR2(20) := 'R'; -- 小票
	/**
  * STATUS 单据状态
  */
	CASH_COLL_STATUS_SUBMIT  VARCHAR2(20) := 'S'; -- 提交
	CASH_COLL_STATUS_CONFIRM VARCHAR2(20) := 'C'; -- 确认收银

	-- ==================== 预收单 BILL_DEPOSIT_RECEIVED ====================
	/**
  * REFUND_STATUS 退款状态
  */
	DEPOSIT_RCVD_REFUND_STATUS_RD VARCHAR2(20) := 'RD'; -- 已退款
	DEPOSIT_RCVD_REFUND_STATUS_NR VARCHAR2(20) := 'NR'; -- 未退款
	DEPOSIT_RCVD_REFUND_STATUS_RG VARCHAR2(20) := 'RG'; -- 退款中
	/**
  * STATUS 单据状态
  */
	DEPOSIT_RCVD_STATUS_SS VARCHAR2(20) := 'SS'; -- 已提交
	DEPOSIT_RCVD_STATUS_CC VARCHAR2(20) := 'CC'; -- 收银确认
	DEPOSIT_RCVD_STATUS_SC VARCHAR2(20) := 'SC'; -- 客户对账单确认
	/**
  * 运输类型
  */
	DEPOSIT_RCVD__TRANS_TYPE__LC VARCHAR2(20) := 'LC'; -- 专线客户
	DEPOSIT_RCVD__TRANS_TYPE__PA VARCHAR2(20) := 'PA'; -- 偏线代理
	DEPOSIT_RCVD__TRANS_TYPE__AA VARCHAR2(20) := 'AA'; -- 空运代理

	-- ==================== 预付单 BILL_ADVANCED_PAYMENT ====================
	/**
  * AUDIT_STATUS 审批状态
  */
	ADVANCED_PAY_AUDIT_STATUS_NA VARCHAR2(20) := 'NA'; -- 未审批
	ADVANCED_PAY_AUDIT_STATUS_AA VARCHAR2(20) := 'AA'; -- 审批通过
	ADVANCED_PAY_AUDIT_STATUS_AD VARCHAR2(20) := 'AD'; -- 审批不通过

	-- ==================== 还款单 BILL_REPAYMENT ====================
	/**
  * STATUS 单据状态
  */
	REPAYMENT_STATUS_SUBMIT  VARCHAR2(20) := 'S'; -- 已提交
	REPAYMENT_STATUS_CONFIRM VARCHAR2(20) := 'C'; -- 已确认
	/**
  * AUDIT_STATUS 审核状态
  */
	REPAYMENT_AUDIT_STATUS_NA VARCHAR2(20) := 'NA'; -- 未审核
	REPAYMENT_AUDIT_STATUS_AA VARCHAR2(20) := 'AA'; -- 已审核
	/**
  * BILL_TYPE 单据类型
  */
	REPAYMENT_BILL_TYPE_WAYBILL VARCHAR2(20) := 'W'; -- 运费
	REPAYMENT_BILL_TYPE_FC      VARCHAR2(20) := 'FC'; -- 到付费
	REPAYMENT_BILL_TYPE_COD     VARCHAR2(20) := 'COD'; -- 代收货款
	REPAYMENT_BILL_TYPE_REVENUE VARCHAR2(20) := 'R'; -- 小票
	REPAYMENT_BILL_TYPE_PL      VARCHAR2(20) := 'PL'; -- 外发单

	-- ==================== 付款单 BILL_PAYMENT ====================
	/**
  * REMIT_STATUS 汇款状态
  */
	PAYMENT_REMIT_STATUS_NT VARCHAR2(20) := 'NT'; -- 未汇款
	PAYMENT_REMIT_STATUS_TG VARCHAR2(20) := 'TG'; -- 汇款中
	PAYMENT_REMIT_STATUS_TD VARCHAR2(20) := 'TD'; -- 已汇款
	/**
  * SOURCE_BILL_TYPE 来源单据类型
  */
	PAYMENT_SOURCE_BILL_TYPE_YF VARCHAR2(20) := 'YF'; -- 应付单
	PAYMENT_SOURCE_BILL_TYPE_YS VARCHAR2(20) := 'YS'; -- 预收单
	PAYMENT_SOURCE_BILL_TYPE_DZ VARCHAR2(20) := 'DZ'; -- 对账单
	PAYMENT_SOURCE_BILL_TYPE_PL VARCHAR2(20) := 'PL'; -- 外发单

	-- ==================== 对账单 STATEMENT_OF_ACCOUNT ====================
	/**
  * CONFIRM_STATUS 确认状态
  */
	STATEMENT_CONFIRM_STATUS_C VARCHAR2(20) := 'C'; -- 已确认
	STATEMENT_CONFIRM_STATUS_N VARCHAR2(20) := 'N'; -- 未确认
	/**
  * BILL_TYPE 对账单类型
  */
	STATEMENT_BILL_TYPE_CA VARCHAR2(20) := 'CA'; -- 客户对账单
	STATEMENT_BILL_TYPE_AA VARCHAR2(20) := 'AA'; -- 代理对账单
	STATEMENT_BILL_TYPE_AF VARCHAR2(20) := 'AF'; -- 空运对账单

	-- ==================== 对账单明细 STATEMENT_OF_ACCOUNT_D ====================
	/**
  * BILL_PARENT_TYPE 对账单明细单据大类型
  */
	STATEMENTD_BILL_PARENT_TYPE_YS VARCHAR2(20) := '10.YS'; -- 应收单
	STATEMENTD_BILL_PARENT_TYPE_YF VARCHAR2(20) := '20.YF'; -- 应付单
	STATEMENTD_BILL_PARENT_TYPE_US VARCHAR2(20) := '30.US'; -- 预收单
	STATEMENTD_BILL_PARENT_TYPE_UF VARCHAR2(20) := '40.UF'; -- 预付单

	-- ==================== 核销单 BILL_WRITEOFF ====================
	/**
  * WRITEOFF_TYPE 核销方式
  */
	WRITEOFF_WRITEOFF_TYPE_RP VARCHAR2(20) := 'RP'; -- 应收冲应付
	WRITEOFF_WRITEOFF_TYPE_DR VARCHAR2(20) := 'DR'; -- 预收冲应收
	WRITEOFF_WRITEOFF_TYPE_AP VARCHAR2(20) := 'AP'; -- 预付冲应付
	WRITEOFF_WRITEOFF_TYPE_RR VARCHAR2(20) := 'RR'; -- 还款冲应收
	WRITEOFF_WRITEOFF_TYPE_PP VARCHAR2(20) := 'PP'; -- 付款冲应付
	WRITEOFF_WRITEOFF_TYPE_BR VARCHAR2(20) := 'BR'; -- 坏账冲应收
	WRITEOFF_WRITEOFF_TYPE_PD VARCHAR2(20) := 'PD'; -- 付款冲预收

	-- ==================== 代收货款 COD ====================
	/**
  * REFUND_PATH 代收货款退款路径
  */
	COD_COD_REFUND_PATH_ONLINE  VARCHAR2(20) := 'ONL';
	COD_COD_REFUND_PATH_OFFLINE VARCHAR2(20) := 'OFFL';
	/**
  * COD_TYPE 代收货款类型（参考运单中代收货款退款类型）
  */
	COD_COD_TYPE_RETURN_1_DAY   VARCHAR2(20) := 'R1'; -- 即日退
	COD_COD_TYPE_RETURN_3_DAY   VARCHAR2(20) := 'R3'; -- 三日退
	COD_COD_TYPE_RETURN_APPROVE VARCHAR2(20) := 'RA'; -- 审核退
	/**
  * AIR_STATUS 空运代收货款状态
  */
	COD_AIR_STATUS_NOT_AUDIT VARCHAR2(20) := 'NA'; -- 未审核

	COD_AIR_STATUS_AUDIT_AGREE VARCHAR2(20) := 'AA'; -- 已审核

	/**
  * PUBLIC_PRIVATE_FLAG 对公对私标志
  */
	COD_PUBLIC_PRIVATE_FLAG_C VARCHAR2(20) := 'C'; -- 对公
	COD_PUBLIC_PRIVATE_FLAG_R VARCHAR2(20) := 'R'; -- 对私
	/**
  * STATUS 代收货款状态
  */
	COD_STATUS_NOT_RETURN          VARCHAR2(20) := 'NR'; -- 未退款
	COD_STATUS_APPROVING           VARCHAR2(20) := 'AG'; -- 待审核
	COD_STATUS_SHIPPER_FREEZE      VARCHAR2(20) := 'SF'; -- 营业部冻结
	COD_STATUS_CASHIER_APPROVE     VARCHAR2(20) := 'CA'; -- 收银员审核
	COD_STATUS_FUND_FREEZE         VARCHAR2(20) := 'FF'; -- 资金部冻结
	COD_STATUS_RETURNING           VARCHAR2(20) := 'RG'; -- 退款中
	COD_STATUS_RETURN_FAILURE_APPL VARCHAR2(20) := 'RFA'; -- 退款失败申请
	COD_STATUS_NEGATIVE_RTN_SUCC   VARCHAR2(20) := 'NRS'; -- 反汇款成功
	COD_STATUS_RETURN_FAILURE      VARCHAR2(20) := 'RF'; -- 退款失败
	COD_STATUS_RETURNED            VARCHAR2(20) := 'RD'; -- 已退款

	-- ==================== 代收货款历史状态 COD_LOG ====================
	/**
  * OPERATE_TYPE 操作类型
  */
	COD_LOG_OPERATE_TYPE_A   VARCHAR2(20) := 'A'; -- 审核
	COD_LOG_OPERATE_TYPE_NA  VARCHAR2(20) := 'NA'; -- 反审核
	COD_LOG_OPERATE_TYPE_F   VARCHAR2(20) := 'F'; -- 冻结
	COD_LOG_OPERATE_TYPE_D   VARCHAR2(20) := 'D'; -- 作废
	COD_LOG_OPERATE_TYPE_CA  VARCHAR2(20) := 'CA'; -- 更改账号
	COD_LOG_OPERATE_TYPE_ER  VARCHAR2(20) := 'ET'; -- 导出汇款
	COD_LOG_OPERATE_TYPE_RA  VARCHAR2(20) := 'RA'; -- 汇款申请
	COD_LOG_OPERATE_TYPE_RS  VARCHAR2(20) := 'RS'; -- 汇款成功
	COD_LOG_OPERATE_TYPE_NRS VARCHAR2(20) := 'NRS'; -- 反汇款成功
	COD_LOG_OPERATE_TYPE_RF  VARCHAR2(20) := 'RF'; -- 汇款失败
	COD_LOG_OPERATE_TYPE_FAP VARCHAR2(20) := 'FAP'; -- 汇款失败审核通过
	COD_LOG_OPERATE_TYPE_FAR VARCHAR2(20) := 'FAR'; -- 汇款失败审核退回
	COD_LOG_OPERATE_TYPE_FF  VARCHAR2(20) := 'FF'; -- 资金部冻结
	COD_LOG_OPERATE_TYPE_FRF VARCHAR2(20) := 'FRF'; -- 资金部反冻结

	-- ==================== 现金收入报表明细 CASH_COLLECTION_RPT_D ====================
	/**
  * SOURCE_BILL_TYPE 来源单据子类型
  */
	CASH_RPT_D_SOURCE_BILL_TYPE_XS VARCHAR2(20) := 'XS'; -- 现金收款单
	CASH_RPT_D_SOURCE_BILL_TYPE_US VARCHAR2(20) := 'US'; -- 预收单
	CASH_RPT_D_SOURCE_BILL_TYPE_HK VARCHAR2(20) := 'HK'; -- 还款单

	-- ==================== 小票单据申请记录 NOTE_APPL ====================
	/**
  * STATUS 单据状态
  */
	NOTE_APPL_STATUS_SUBMIT VARCHAR2(20) := 'S'; -- 已提交
	NOTE_APPL_STATUS_D      VARCHAR2(20) := 'D'; -- 已下发
	NOTE_APPL_STATUS_IN     VARCHAR2(20) := 'I'; -- 已入库
	/**
  * APPROVE_STATUS 审批状态
  */
	NOTE_APPL_APPROVE_STATUS_NA VARCHAR2(20) := 'NA'; -- 未审批
	NOTE_APPL_APPROVE_STATUS_RA VARCHAR2(20) := 'RA'; -- 审批通过
	NOTE_APPL_APPROVE_STATUS_RD VARCHAR2(20) := 'RD'; -- 审批不通过
	/**
  * WRITE_OFF_STATUS 核销状态
  */
	NOTE_APPL_WRITEOFF_STATUS_NW VARCHAR2(20) := 'NW'; -- 未核销
	NOTE_APPL_WRITEOFF_STATUS_AW VARCHAR2(20) := 'AW'; -- 申请核销
	NOTE_APPL_WRITEOFF_STATUS_WD VARCHAR2(20) := 'WD'; -- 已核销

	-- ==================== 小票单据发放入库 NOTE_STOCK_IN ====================
	/**
  * ISSUED_TYPE 下发方式
  */
	NOTE_STOCK_IN_ISSUED_TYPE_E VARCHAR2(20) := 'E'; -- 快递
	NOTE_STOCK_IN_ISSUED_TYPE_I VARCHAR2(20) := 'I'; -- 内部带货
	NOTE_STOCK_IN_ISSUED_TYPE_P VARCHAR2(20) := 'P'; -- 自领

	-- ==================== 小票单据明细 NOTE_DETAILS ====================
	/**
  * STATUS 单据状态
  */
	NOTE_DETAILS_STATUS_SUBMIT VARCHAR2(20) := 'S'; -- 已提交
	NOTE_DETAILS_STATUS_USED   VARCHAR2(20) := 'U'; -- 已使用

	-- ==================== 运单消息 POD_ENTITY ====================
	/**
  * POD_TYPE 签收/反签收类型
  */
	POD__POD_TYPE__POD VARCHAR2(20) := 'POD'; -- 签收
	POD__POD_TYPE__UPD VARCHAR2(20) := 'UPD'; -- 反签收

	--============================货币单位=====================================
	CURRENCY_CODE_RMB VARCHAR2(20) := 'RMB'; --人民币

	--=============================期末结账类型====================================
	--结账类型
	BALANCE_TYPE_RECEIVABLE VARCHAR(20) := 'R'; -- 应收期末结账
	BALANCE_TYPE_PAYABLE    VARCHAR(20) := 'P'; -- 应付期末结账
	BALANCE_TYPE_DEPOSIT    VARCHAR(20) := 'D'; -- 预收期末结账

	---==========================结账批次号==================================
	BALANCE_BATCH_INITED VARCHAR2(20) := 'INITED'; --初始化没有做任何操作
	BALANCE_BATCH_BEGIN  VARCHAR2(20) := 'BEGIN'; --正在结账
	BALANCE_BATCH_END    VARCHAR2(20) := 'END'; --结账结束

	---========================== 凭证业务场景 开单 ==================================
	DE_ORIG_CR_CASH   VARCHAR2(30) := 'DE_ORIG_CR_CASH'; -- 开单（现金）
	DE_ORIG_CR_CARD   VARCHAR2(30) := 'DE_ORIG_CR_CARD'; -- 开单（银行卡）
	DE_ORIG_AR        VARCHAR2(30) := 'DE_ORIG_AR'; -- 开单（月结/临欠）
	DE_AP_SERVICE_FEE VARCHAR2(30) := 'DE_AP_SERVICE_FEE'; -- 劳务费

	---========================== 凭证业务场景 签收还款 ==================================
	UR_ORIG_CR_CASH_NO_POD VARCHAR2(30) := 'UR_ORIG_CR_CASH_NO_POD'; -- 还款，未签收（开单为：月结/欠款，且以现金还款）
	UR_ORIG_CR_CARD_NO_POD VARCHAR2(30) := 'UR_ORIG_CR_CARD_NO_POD'; -- 还款，未签收（开单为：月结/欠款，且以银行卡还款）
	UR_ORIG_POD_CR         VARCHAR2(30) := 'UR_ORIG_POD_CR'; -- 签收，已还款或已核销（开单为现付/月结/临欠）
	UR_ORIG_POD_AR         VARCHAR2(30) := 'UR_ORIG_POD_AR'; -- 签收，未还款（开单为月结/临欠）
	UR_ORIG_CR_CASH_POD    VARCHAR2(30) := 'UR_ORIG_CR_CASH_POD'; -- 还款，已签收（月结/欠款签收且以现金还款）
	UR_ORIG_CR_BANK_POD    VARCHAR2(30) := 'UR_ORIG_CR_BANK_POD'; -- 还款，已签收（月结/欠款签收且以银行还款）

	---========================== 凭证业务场景 签收到付 ==================================
	UR_DEST_AR             VARCHAR2(30) := 'UR_DEST_AR'; -- 开单（到付）
	UR_DEST_CR_CASH_NO_POD VARCHAR2(30) := 'UR_DEST_CR_CASH_NO_POD'; -- 还款，未签收（到付且实收现金或还款现金）
	UR_DEST_POD_CR         VARCHAR2(30) := 'UR_DEST_POD_CR'; -- 签收，已还款或已核销（到付且签收前已收取现金/银行或还款现金/银行或已核销）
	UR_DEST_CR_CASH_AS_POD VARCHAR2(30) := 'UR_DEST_CR_CASH_AS_POD'; -- 签收（到付且同时收到现金）
	UR_DEST_CR_BANK_NO_POD VARCHAR2(30) := 'UR_DEST_CR_BANK_NO_POD'; -- 还款，未签收（到付且实收银行或还款银行）
	UR_DEST_CR_BANK_AS_POD VARCHAR2(30) := 'UR_DEST_CR_BANK_AS_POD'; -- 签收（到付同时收到银行）
	UR_DEST_AR_POD         VARCHAR2(30) := 'UR_DEST_AR_POD'; -- 签收时未收款（包含：专线到付结转月结/欠款或偏线签收时未收款或空运签收时未收款的情况）
	UR_DEST_CR_CASH_POD    VARCHAR2(30) := 'UR_DEST_CR_CASH_POD'; -- 签收后还款现金（包含：针对专线到付结转月结/欠款或偏线签收时未收款或空运签收时未收款3种情况做的还款现金）
	UR_DEST_CR_BANK_POD    VARCHAR2(30) := 'UR_DEST_CR_BANK_POD'; -- 签收后还款银行（包含：针对专线到付结转月结/欠款或偏线签收时未收款或空运签收时未收款3种情况做的还款银行）

	---========================== 凭证业务场景 理赔（包含专线、偏线、空运部门操作的理赔） ==================================
	CLAIM_ORIG_AP_WO_AR_WB VARCHAR2(30) := 'CLAIM_ORIG_AP_WO_AR_WB'; -- 同一单号：应付理赔运单对应的总运费或者理赔金额（两者取小），由始发部门申请
	CLAIM_ORIG_AP_COST     VARCHAR2(30) := 'CLAIM_ORIG_AP_COST'; -- 同一单号：理赔金额-运单总运费，由始发部门申请
	CLAIM_ORIG_AP_WO_AR    VARCHAR2(30) := 'CLAIM_ORIG_AP_WO_AR'; -- 理赔冲应收始发，由始发部门申请（适用于理赔单号和应收单号相同或不同的情况进行对冲）
	CLAIM_ORIG_CP          VARCHAR2(30) := 'CLAIM_ORIG_CP'; -- 理赔冲应收后，应付理赔仍存在余额，此时始发部门申请款项支付
	CLAIM_DEST_AP_WO_AR_WB VARCHAR2(30) := 'CLAIM_DEST_AP_WO_AR_WB'; -- 同一单号：应付理赔运单对应的总运费或者理赔金额（两者取小），由到达部门申请
	CLAIM_DEST_AP_COST     VARCHAR2(30) := 'CLAIM_DEST_AP_COST'; -- 同一单号：理赔金额-运单总运费，由到达部门申请
	CLAIM_DEST_AP_WO_AR    VARCHAR2(30) := 'CLAIM_DEST_AP_WO_AR'; -- 理赔冲应收到付，由到达部门申请（适用于理赔单号和应收单号相同或不同的情况进行对冲）
	CLAIM_DEST_CP          VARCHAR2(30) := 'CLAIM_DEST_CP'; -- 理赔冲应收后，应付理赔仍存在余额，此时到达部门申请款项支付

	---========================== 凭证业务场景 代收货款 ==================================
	COD_DE_AR_AP             VARCHAR2(30) := 'COD_DE_AR_AP'; -- 开单（代收货款）
	COD_DEST_CR_CASH         VARCHAR2(30) := 'COD_DEST_CR_CASH'; -- 签收前或签收时，代收货款收现金
	COD_DEST_CR_BANK         VARCHAR2(30) := 'COD_DEST_CR_BANK'; -- 签收前或签收时，代收货款收银行
	COD_DEST_AP_WO_AR_POD    VARCHAR2(30) := 'COD_DEST_AP_WO_AR_POD'; -- 应付(代收)冲应收（到付运费）（已签收）
	COD_DEST_AP_WO_AR_NO_POD VARCHAR2(30) := 'COD_DEST_AP_WO_AR_NO_POD'; -- 应付(代收)冲应收（到付运费）（未签收）
	COD_ORIG_AP_WO_AR_POD    VARCHAR2(30) := 'COD_ORIG_AP_WO_AR_POD'; -- 应付（代收）冲应收始发（已签收）
	COD_ORIG_AP_WO_AR_NO_POD VARCHAR2(30) := 'COD_ORIG_AP_WO_AR_NO_POD'; -- 应付（代收）冲应收始发（未签收）

	---========================== 凭证业务场景 预收客户 ==================================
	DR_CR_CASH              VARCHAR2(30) := 'DR_CR_CASH'; -- 预收客户现金
	DR_CR_BANK              VARCHAR2(30) := 'DR_CR_BANK'; -- 预收客户银行
	DR_DEST_CR_WO_AR_NO_POD VARCHAR2(30) := 'DR_DEST_CR_WO_AR_NO_POD'; -- 预收客户冲应收到付运费（未签收）
	DR_DEST_CR_WO_AR_POD    VARCHAR2(30) := 'DR_DEST_CR_WO_AR_POD'; -- 预收客户冲应收到付运费（已签收）
	DR_ORIG_CR_WO_AR_NO_POD VARCHAR2(30) := 'DR_ORIG_CR_WO_AR_NO_POD'; -- 预收客户冲应收始发（未签收）
	DR_ORIG_CR_WO_AR_POD    VARCHAR2(30) := 'DR_ORIG_CR_WO_AR_POD'; -- 预收客户冲应收始发（已签收）
	DR_ORIG_REFUND_CP       VARCHAR2(30) := 'DR_ORIG_REFUND_CP'; -- 始发退预收付款申请

	---========================== 凭证业务场景 坏账冲应收 ==================================
	BA_ORIG_AR_NO_POD VARCHAR2(30) := 'BA_ORIG_AR_NO_POD'; -- 坏账冲应收始发（未签收）
	BA_ORIG_AR_POD    VARCHAR2(30) := 'BA_ORIG_AR_POD'; -- 坏账冲应收始发（已签收）
	BA_DEST_AR_NO_POD VARCHAR2(30) := 'BA_DEST_AR_NO_POD'; -- 坏账冲应收到付运费（未签收）
	BA_DEST_AR_POD    VARCHAR2(30) := 'BA_DEST_AR_POD'; -- 坏账冲应收到付运费（已签收）

	---========================== 凭证业务场景 小票 ==================================
	OR_NOR_CR_CASH   VARCHAR2(30) := 'OR_NOR_CR_CASH'; -- 小票现金（营业外收入：会员卡费、卖废品、其他3种类型）
	OR_NOR_CR_BANK   VARCHAR2(30) := 'OR_NOR_CR_BANK'; -- 小票银行（营业外收入：会员卡费、卖废品、其他3种类型）
	OR_NOR_AR        VARCHAR2(30) := 'OR_NOR_AR'; -- 小票应收（营业外收入：会员卡费、卖废品、其他3种类型）
	OR_POR_CR_CASH   VARCHAR2(30) := 'OR_POR_CR_CASH'; -- 小票现金（主营业务收入：除会员卡费、卖废品、其他3种之外的类型）
	OR_POR_CR_BANK   VARCHAR2(30) := 'OR_POR_CR_BANK'; -- 小票银行（主营业务收入：除会员卡费、卖废品、其他3种之外的类型）
	OR_POR_AR        VARCHAR2(30) := 'OR_POR_AR'; -- 小票应收（主营业务收入：除会员卡费、卖废品、其他3种之外的类型）
	OR_CR_CASH_WO_AR VARCHAR2(30) := 'OR_CR_CASH_WO_AR'; -- 小票应收还款现金
	OR_CR_BANK_WO_AR VARCHAR2(30) := 'OR_CR_BANK_WO_AR'; -- 小票应收还款银行
	OR_COD_AP_WO_AR  VARCHAR2(30) := 'OR_COD_AP_WO_AR'; -- 应付（代收）冲小票应收
	OR_DR_WO_AR      VARCHAR2(30) := 'OR_DR_WO_AR'; -- 预收客户冲小票应收
	OR_BA_AR         VARCHAR2(30) := 'OR_BA_AR'; -- 坏账冲小票应收

	---========================== 凭证业务场景 弃货、违禁品、丢货 ==================================
	AC_ORIG_AR      VARCHAR2(30) := 'AC_ORIG_AR'; -- 应收始发（弃货或违禁品）
	AC_ORIG_CR      VARCHAR2(30) := 'AC_ORIG_CR'; -- 现付（现金/银行卡）（弃货或违禁品）
	AC_ORIG_AR_LOST VARCHAR2(30) := 'AC_ORIG_AR_LOST'; -- 应收始发（全部丢货）
	AC_ORIG_CR_LOST VARCHAR2(30) := 'AC_ORIG_CR_LOST'; -- 现付（现金/银行卡）（全部丢货）

	---========================== 凭证业务场景 退运费 ==================================
	RD_ORIG_AP_WO_AR_WB   VARCHAR2(30) := 'RD_ORIG_AP_WO_AR_WB'; -- 同一单号：退运费运单对应的总运费或者退运费金额（两者取小），由始发部门申请
	RD_ORIG_AP_WO_AR_COST VARCHAR2(30) := 'RD_ORIG_AP_WO_AR_COST'; -- 同一单号：退运费金额-运单总运费，由始发部门申请
	RD_ORIG_CP            VARCHAR2(30) := 'RD_ORIG_CP'; -- 始发退运费付款申请
	RD_DEST_AP_WO_AR_WB   VARCHAR2(30) := 'RD_DEST_AP_WO_AR_WB'; -- 同一单号：退运费运单对应的总运费或者退运费金额（两者取小），由到达部门申请
	RD_DEST_AP_WO_AR_COST VARCHAR2(30) := 'RD_DEST_AP_WO_AR_COST'; -- 同一单号：退运费金额-运单总运费，由到达部门申请
	RD_DEST_CP            VARCHAR2(30) := 'RD_DEST_CP'; -- 到达退运费付款申请

	---========================== 凭证业务场景 服务补救 ==================================
	CN_ORIG_CP VARCHAR2(30) := 'CN_ORIG_CP'; -- 始发服务补救付款申请
	CN_DEST_CP VARCHAR2(30) := 'CN_DEST_CP'; -- 到达服务补救付款申请

	---========================== 凭证业务场景 偏线代理成本 ==================================
	COST_AP_DE                VARCHAR2(30) := 'COST_AP_DE'; -- 外发反馈录入
	COST_AP_POD               VARCHAR2(30) := 'COST_AP_POD'; -- 含有应付代理成本的运单签收
	COST_AP_WO_DEST_AR_POD    VARCHAR2(30) := 'COST_AP_WO_DEST_AR_POD'; -- 应付(成本)冲应收（到付运费）（已签收）
	COST_AP_WO_DEST_AR_NO_POD VARCHAR2(30) := 'COST_AP_WO_DEST_AR_NO_POD'; -- 应付(成本)冲应收（到付运费）（未签收）
	COST_CP                   VARCHAR2(30) := 'COST_CP'; -- 实付偏线代理成本

	---========================== 凭证业务场景 还款签收 ==================================
	UR_CR_CASH_NO_POD VARCHAR2(30) := 'UR_CR_CASH_NO_POD'; -- 还款，未签收（到付且现金或还款现金）
	UR_CR_CASH_AS_POD VARCHAR2(30) := 'UR_CR_CASH_AS_POD'; -- 签收（同时到付且现金）
	UR_CR_BANK_NO_POD VARCHAR2(30) := 'UR_CR_BANK_NO_POD'; -- 还款，未签收，（到付且银行或还款银行）
	UR_CR_BANK_AS_POD VARCHAR2(30) := 'UR_CR_BANK_AS_POD'; -- 签收（同时到付且银行）
	UR_CR_CASH_POD    VARCHAR2(30) := 'UR_CR_CASH_POD'; -- 签收后还款现金（到付）
	UR_CR_BANK_POD    VARCHAR2(30) := 'UR_CR_BANK_POD'; -- 签收后还款银行（到付）

	---========================== 凭证业务场景 偏线理赔 ==================================
	CLAIM_AP_WO_AR_WB   VARCHAR2(30) := 'CLAIM_AP_WO_AR_WB'; -- 同一单号：应付理赔运单对应的总运费或者理赔金额（两者取小），由偏线部门申请
	CLAIM_COST_AP       VARCHAR2(30) := 'CLAIM_COST_AP'; -- 同一单号：理赔金额-运单总运费，由偏线部门申请
	CLAIM_AP_WO_DEST_AR VARCHAR2(30) := 'CLAIM_AP_WO_DEST_AR'; -- 理赔冲应收到付，由偏线部门申请（适用于理赔单号和应收单号相同或不同的情况进行对冲）
	CLAIM_CP            VARCHAR2(30) := 'CLAIM_CP'; -- 理赔冲应收后，应付理赔仍存在余额，此时偏线部门申请款项支付

	---========================== 凭证业务场景 预收偏线代理 ==================================
	DR_CASH              VARCHAR2(30) := 'DR_CASH'; -- 预收偏线代理款（现金）
	DR_BANK              VARCHAR2(30) := 'DR_BANK'; -- 预收偏线代理款（银行）
	DR_WO_DEST_AR_POD    VARCHAR2(30) := 'DR_WO_DEST_AR_POD'; -- 预收偏线代理冲应收到付运费（已签收）
	DR_WO_DEST_AR_NO_POD VARCHAR2(30) := 'DR_WO_DEST_AR_NO_POD'; -- 预收偏线代理冲应收到付运费（未签收）
	DR_CP                VARCHAR2(30) := 'DR_CP'; -- 偏线退预收付款申请

	---========================== 凭证业务场景 空运代理成本 ==================================
	COST_AIR_AP_DE  VARCHAR2(30) := 'COST_AP_DE'; -- 空运成本生成
	COST_AIR_AP_POD VARCHAR2(30) := 'COST_AP_POD'; -- 空运成本确认
	COST_AIR_CP     VARCHAR2(30) := 'COST_CP'; -- 空运成本付款申请

	---========================== 凭证业务场景 其它应收应付 ==================================
	OT_AP_DE        VARCHAR2(30) := 'OT_AP_DE'; -- 其它应付成本生成
	OT_CP           VARCHAR2(30) := 'OT_CP'; -- 其它应付付款申请
	OT_AR_COMM_CASH VARCHAR2(30) := 'OT_AR_COMM_CASH'; -- 其它应收-返点-现金
	OT_AR_COMM_BANK VARCHAR2(30) := 'OT_AR_COMM_BANK'; -- 其它应收-返点-银行
	OT_AR_COMM_DEBT VARCHAR2(30) := 'OT_AR_COMM_DEBT'; -- 其它应收-返点-月结/临欠

	---========================== 凭证业务场景 空运应付（其它应付）冲应收（其它应收） ==================================
	OT_DEST_AP_WO_DEST_AR_POD     VARCHAR2(30) := 'OT_DEST_AP_WO_DEST_AR_POD'; -- 应付到达代理成本冲应收到付运费（已签收）
	OT_DEST_AP_WO_DEST_AR_NO_POD  VARCHAR2(30) := 'OT_DEST_AP_WO_DEST_AR_NO_POD'; -- 应付到达代理成本冲应收到付运费（未签收）
	OT_DEST_OT_AP_WO_DEST_AR_POD  VARCHAR2(30) := 'OT_DEST_OT_AP_WO_DEST_AR_POD'; -- 其它应付到达代理冲应收到付运费（已签收）
	OT_DEST_OT_AP_WO_DEST_AR_NPOD VARCHAR2(30) := 'OT_DEST_OT_AP_WO_DEST_AR_NPOD'; -- 其它应付到达代理冲应收到付运费（未签收）
	OT_AP_WO_OT_AR                VARCHAR2(30) := 'OT_AP_WO_OT_AR'; -- 其它应付冲其它应收（包含：航空公司单据对冲、出发代理单据对冲、中转代理单据对冲、到达代理单据对冲）

	---========================== 凭证业务场景 预收空运代理 ==================================
	DR_AA_CASH              VARCHAR2(30) := 'DR_AA_CASH'; -- 预收空运代理现金
	DR_AA_BANK              VARCHAR2(30) := 'DR_AA_BANK'; -- 预收空运代理银行
	DR_AA_WO_DEST_CR_POD    VARCHAR2(30) := 'DR_AA_WO_DEST_CR_POD'; -- 预收空运代理冲应收到付运费（已签收）
	DR_AA_WO_DEST_CR_NO_POD VARCHAR2(30) := 'DR_AA_WO_DEST_CR_NO_POD'; -- 预收空运代理冲应收到付运费（未签收）
	DR_AA_WO_OT_AR          VARCHAR2(30) := 'DR_AA_WO_OT_AR'; -- 预收空运代理冲其他应收
	DR_AA_WO_COD_AR_POD     VARCHAR2(30) := 'DR_AA_WO_COD_AR_POD'; -- 预收空运代理冲应收代收货款（已签收）
	DR_AA_WO_COD_AR_NO_POD  VARCHAR2(30) := 'DR_AA_WO_COD_AR_NO_POD'; -- 预收空运代理冲应收代收货款（未签收）
	DR_AA_CP                VARCHAR2(30) := 'DR_AA_CP'; -- 空运退预收付款申请

	---========================== 凭证业务场景 空运理赔 ==================================
	CLAIM_AIR_AP_WO_AR_WB   VARCHAR2(30) := 'CLAIM_AIR_AP_WO_AR_WB'; -- 同一单号：应付理赔运单对应的总运费或者理赔金额（两者取小），由空运部门申请
	CLAIM_AIR_COST_AP       VARCHAR2(30) := 'CLAIM_AIR_COST_AP'; -- 同一单号：理赔金额-运单总运费，由空运部门申请
	CLAIM_AIR_AP_WO_DEST_AR VARCHAR2(30) := 'CLAIM_AIR_AP_WO_DEST_AR'; -- 理赔冲应收到付运费，由空运部门申请（适用于理赔单号和应收单号相同或不同的情况进行对冲）
	CLAIM_AIR_AP_WO_OT_AR   VARCHAR2(30) := 'CLAIM_AIR_AP_WO_OT_AR'; -- 理赔冲其它应收，由空运部门申请
	CLAIM_AIR_CP            VARCHAR2(30) := 'CLAIM_AIR_CP'; -- 理赔冲应收或其它应收后，应付理赔仍存在余额，此时空运部门申请款项支付

	---========================== 凭证业务场景 预付 ==================================
	ADP_AIR_CO   VARCHAR2(30) := 'ADP_AIR_CO'; -- 预付航空公司款
	ADP_WO_AP    VARCHAR2(30) := 'ADP_WO_AP'; -- 预付冲应付
	ADP_WO_OT_AP VARCHAR2(30) := 'ADP_WO_OT_AP'; -- 预付冲其他应付

	---========================== 凭证业务场景 坏账冲其它应收 ==================================
	BA_WO_OT_AR VARCHAR2(30) := 'BA_WO_OT_AR'; -- 坏账冲其它应收

	------------------------------------------------------------------
	-- 插入错误日志
	------------------------------------------------------------------
	PROCEDURE PROC_STL_ERROR_LOG(P_PERIOD           VARCHAR2, --账期
															 P_BEGIN_TIME       DATE, --开始时间
															 P_END_TIME         DATE, --截止时间
															 P_OPERATE_CONTENT  VARCHAR2, --操作内容
															 P_NOTES            VARCHAR2, --备注
															 P_CREATE_USER_CODE VARCHAR2 DEFAULT DEFAULT_USER_CODE, --创建人
															 P_SQL_ERR_CODE     VARCHAR2 DEFAULT SQLCODE, --错误码
															 P_SQL_ERR_MSG      VARCHAR2 DEFAULT SQLERRM, --错误消息
															 P_CREATE_TIME      DATE DEFAULT SYSDATE);

	---------------------------------------------------------------------
	--视图参数
	---------------------------------------------------------------------
	FUNCTION SET_POD_END_DATE(POD_DATE DATE) RETURN DATE;
	FUNCTION GET_POD_END_DATE RETURN DATE;

END PKG_STL_CASH_COMMON;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_CASH_COMMON IS

	V_POD_END_DATE DATE;

	------------------------------------------------------------------
	-- 插入错误日志
	------------------------------------------------------------------
	PROCEDURE PROC_STL_ERROR_LOG(P_PERIOD           VARCHAR2, --账期
															 P_BEGIN_TIME       DATE, --开始时间
															 P_END_TIME         DATE, --截止时间
															 P_OPERATE_CONTENT  VARCHAR2, --操作内容
															 P_NOTES            VARCHAR2, --备注
															 P_CREATE_USER_CODE VARCHAR2 DEFAULT DEFAULT_USER_CODE, --创建人
															 P_SQL_ERR_CODE     VARCHAR2 DEFAULT SQLCODE, --错误码
															 P_SQL_ERR_MSG      VARCHAR2 DEFAULT SQLERRM, --错误消息
															 P_CREATE_TIME      DATE DEFAULT SYSDATE) AS
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事物
		V_ERROR   VARCHAR2(500);
		V_CALLER  VARCHAR2(50);
		V_LINE_NO NUMBER(9);
		V_LPOS    NUMBER(9);
		V_RPOS    NUMBER(9);
	BEGIN
		V_ERROR  := DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
		V_LPOS   := INSTR(V_ERROR, '"') + 1;
		V_RPOS   := INSTR(V_ERROR, '"', V_LPOS);
		V_CALLER := SUBSTR(V_ERROR, V_LPOS, V_RPOS - V_LPOS);
	
		V_LPOS    := INSTR(V_ERROR, 'line ') + 5;
		V_RPOS    := INSTR(V_ERROR, CHR(10));
		V_LINE_NO := TO_NUMBER(TRIM(SUBSTR(V_ERROR, V_LPOS, V_RPOS - V_LPOS)));
	
		--DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK);
		--DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_CALL_STACK);
		--DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
	
		-- 插入日志
		INSERT INTO T_STL_ERROR_LOG
			(ID,
			 CREATE_TIME,
			 CREATE_USER_CODE,
			 OPERATE_TYPE,
			 OPERATE_CONTENT,
			 PERIOD,
			 BEGIN_TIME,
			 END_TIME,
			 SQL_ERR_CODE,
			 SQL_ERR_MSG,
			 SQL_LINE_NO,
			 NOTES)
		VALUES
			(SYS_GUID(),
			 P_CREATE_TIME,
			 P_CREATE_USER_CODE,
			 V_CALLER,
			 P_OPERATE_CONTENT,
			 P_PERIOD,
			 P_BEGIN_TIME,
			 P_END_TIME,
			 P_SQL_ERR_CODE,
			 P_SQL_ERR_MSG,
			 V_LINE_NO,
			 P_NOTES);
	
		COMMIT;
	END;

	FUNCTION SET_POD_END_DATE(POD_DATE DATE) RETURN DATE IS
	BEGIN
		V_POD_END_DATE := POD_DATE;
		RETURN V_POD_END_DATE;
	END;

	FUNCTION GET_POD_END_DATE RETURN DATE IS
	BEGIN
		RETURN V_POD_END_DATE;
	END;

END PKG_STL_CASH_COMMON;
/
