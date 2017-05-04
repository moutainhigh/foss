CREATE OR REPLACE PACKAGE PKG_STL_COMMON IS
	-- Author  : ZHUWEI
	-- Created : 2012-11-19 11:06:22
	-- Purpose : 常量以及通用函数

	--========================== 通用常量定义 ================================
	MAX_PAGE_ROW_SIZE NUMBER := 1000; --最大页行数，用于批量插入，减少每次操作的行数
	DEFAULT_USER_CODE VARCHAR2(20) := 'STL'; --结算后台用户编码
	DEFAULT_BILL_NO   VARCHAR2(20) := 'N/A'; -- 默认单号

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
  * CURRENCY_CODE 货币币种
  */
	CURRENCY_CODE_RMB VARCHAR2(20) := 'RMB'; --人民币

	/**
  * 空值，代替空格
  */
	NULL_VALUE VARCHAR2(20) := 'N/A';

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

	/**
  * BILL_PARENT_TYPE 单据类型
  */
	BILL_PARENT_TYPE__XS VARCHAR2(20) := 'XS'; -- 现金收款单
	BILL_PARENT_TYPE__YS VARCHAR2(20) := 'YS'; -- 应收单
	BILL_PARENT_TYPE__YF VARCHAR2(20) := 'YF'; -- 应付单
	BILL_PARENT_TYPE__HK VARCHAR2(20) := 'HK'; -- 还款单
	BILL_PARENT_TYPE__US VARCHAR2(20) := 'US'; -- 预收单
	BILL_PARENT_TYPE__UF VARCHAR2(20) := 'UF'; -- 预付单
	BILL_PARENT_TYPE__DZ VARCHAR2(20) := 'DZ'; -- 对账单
	BILL_PARENT_TYPE__FK VARCHAR2(20) := 'FK'; -- 付款单
	BILL_PARENT_TYPE__XP VARCHAR2(20) := 'XP'; -- 小票单
	BILL_PARENT_TYPE__HZ VARCHAR2(20) := 'HZ'; -- 坏账单

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
	RECEIVABLE_BILL_TYPE_AA      VARCHAR2(20) := 'AA'; -- 空运到达代理应收
	RECEIVABLE_BILL_TYPE_AAC     VARCHAR2(20) := 'AAC'; --  空运代理代收货款应收
	RECEIVABLE_BILL_TYPE_DL      VARCHAR2(20) := 'DL'; -- 到达落地配代理应收
	RECEIVABLE_BILL_TYPE_DLC     VARCHAR2(20) := 'DLC'; -- 落地配代理代收货款应收单
	RECEIVABLE_BILL_TYPE_LR      VARCHAR2(20) := 'LR'; -- 落地配其他应收
  RECEIVABLE_BILL_TYPE_PR VARCHAR2(20) := 'PR'; --偏线其它应收
	/**
  * SOURCE_BILL_TYPE 来源单据子类型
  */
	RECEIVABLE_SOURCE_BILL_TYPE_W VARCHAR2(20) := 'W'; -- 运单
	RECEIVABLE_SOURCE_BILL_TYPE_R VARCHAR2(20) := 'R'; -- 小票
	RECEIVABLE_SOURCE_BILL_TYPE_M VARCHAR2(20) := 'M'; -- 人工录入
	RECEIVABLE_SOURCE_BILL_TYPE_E VARCHAR2(20) := 'E'; -- 异常出库
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
	RECEIVABLE_COLLECTION_TYPE_W   VARCHAR2(20) := 'W'; -- 仓储费
	RECEIVABLE_COLLECTION_TYPE_PD  VARCHAR2(20) := 'PD'; -- 自提改派送
	RECEIVABLE_COLLECTION_TYPE_D   VARCHAR2(20) := 'D'; -- 加收送货费
	RECEIVABLE_COLLECTION_TYPE_C   VARCHAR2(20) := 'C'; -- 会员卡费
	RECEIVABLE_COLLECTION_TYPE_P   VARCHAR2(20) := 'P'; -- 包装费
	RECEIVABLE_COLLECTION_TYPE_F   VARCHAR2(20) := 'F'; -- 放空费
	RECEIVABLE_COLLECTION_TYPE_R   VARCHAR2(20) := 'R'; -- 卖废品
	RECEIVABLE_COLLECTION_TYPE_E   VARCHAR2(20) := 'E'; -- 超期预收款
	RECEIVABLE_COLLECTION_TYPE_RFC VARCHAR2(20) := 'RFC'; -- 更改费
	RECEIVABLE_COLLECTION_TYPE_DU  VARCHAR2(20) := 'DU'; -- 送货上楼费
	RECEIVABLE_COLLECTION_TYPE_POS VARCHAR2(20) := 'POS'; -- POS机手续费
	RECEIVABLE_COLLECTION_TYPE_O   VARCHAR2(20) := 'O'; -- 其他
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
	PAYABLE_BILL_TYPE_AIR_ORIGINAL VARCHAR2(20) := 'AAO'; -- 空运出发代理应付
	PAYABLE_BILL_TYPE_AIR_DELIVERY VARCHAR2(20) := 'AAD'; -- 空运到达代理应付
	PAYABLE_BILL_TYPE_AIR_OTHER    VARCHAR2(20) := 'AO'; -- 空运其他应付
	PAYABLE_BILL_TYPE_PARTIAL_LINE VARCHAR2(20) := 'PL'; -- 偏线代理成本
	PAYABLE_BILL_TYPE_SERVICE_FEE  VARCHAR2(20) := 'SF'; -- 劳务费应付
	PAYABLE_BILL_TYPE_CLAIM        VARCHAR2(20) := 'C'; -- 理赔应付
	PAYABLE_BILL_TYPE_REFUND       VARCHAR2(20) := 'R'; -- 退运费应付
	PAYABLE_BILL_TYPE_COMPENSATION VARCHAR2(20) := 'CP'; -- 服务补救应付
	PAYABLE_BILL_TYPE_LANDSTOWAGE  VARCHAR2(20) := 'LD'; -- 落地配外发成本
	PAYABLE_BILLTYPE_LAND_OTHER    VARCHAR2(20) := 'LDO'; -- 落地配其他应付
  PAYABLE_BILL_TYPE_PO VARCHAR2(20) := 'PO'; --偏线其它应付
	/**
  * SOURCE_BILL_TYPE 来源单据子类型
  */
	PAYABLE_SOURCE_BILL_TYPE_W  VARCHAR2(20) := 'W'; -- 运单
	PAYABLE_SOURCE_BILL_TYPE_RS VARCHAR2(20) := 'RS'; -- 汽运配置单
	PAYABLE_SOURCE_BILL_TYPE_AW VARCHAR2(20) := 'AW'; -- 航空正单
	PAYABLE_SOURCE_BILL_TYPE_TP VARCHAR2(20) := 'TP'; -- 中转提货清单
	PAYABLE_SOURCE_BILL_TYPE_AP VARCHAR2(20) := 'AP'; -- 合大票清单
	PAYABLE_SOURCE_BILL_TYPE_PL VARCHAR2(20) := 'PL'; -- 外发单
	PAYABLE_SOURCE_BILL_TYPE_E  VARCHAR2(20) := 'E'; -- 异常出库
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
	DEPOSIT_RCVD_STATUS_SUBMIT  VARCHAR2(20) := 'S'; -- 已提交
	DEPOSIT_RCVD_STATUS_CONFIRM VARCHAR2(20) := 'C'; -- 收银确认
	DEPOSIT_RCVD_STATUS_SC      VARCHAR2(20) := 'SC'; -- 客户对账单确认
	/**
  * 运输类型
  */
	DEPOSIT_RCVD__TRANS_TYPE__LC VARCHAR2(20) := 'LC'; -- 专线客户
	DEPOSIT_RCVD__TRANS_TYPE__PA VARCHAR2(20) := 'PA'; -- 偏线代理
	DEPOSIT_RCVD__TRANS_TYPE__AA VARCHAR2(20) := 'AA'; -- 空运代理
	DEPOSIT_RCVD__TRANS_TYPE__LS VARCHAR2(20) := 'LS'; -- 落地配代理

	-- ==================== 预付单 BILL_ADVANCED_PAYMENT ====================
	/**
  * BILL_TYPE 预付单单据子类型
  */
	ADVANCED_PAYMENT_BILL_TYPE_AIR VARCHAR2(20) := 'A'; --空运

	/**
  * AUDIT_STATUS 审批状态
  */
	ADVANCED_PAY_AUDIT_STATUS_NA VARCHAR2(20) := 'NA'; -- 未审批
	ADVANCED_PAY_AUDIT_STATUS_AG VARCHAR2(20) := 'AG'; -- 审批中
	ADVANCED_PAY_AUDIT_STATUS_AA VARCHAR2(20) := 'AA'; -- 审批通过
	ADVANCED_PAY_AUDIT_STATUS_AD VARCHAR2(20) := 'AD'; -- 审批不通过

	-- ==================== 还款单 BILL_REPAYMENT ====================
	/**
  * BILL_TYPE 单据类型
  */
	REPAYMENT_BILL_TYPE_REPAYMENT VARCHAR2(20) := 'HK'; -- 还款
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
  * SOURCE_BILL_TYPE 来源单据类型
  */
	REPAYMENT_SOURCE_BILL_TYPE_W   VARCHAR2(20) := 'W'; -- 运费
	REPAYMENT_SOURCE_BILL_TYPE_FC  VARCHAR2(20) := 'FC'; -- 到付费
	REPAYMENT_SOURCE_BILL_TYPE_COD VARCHAR2(20) := 'COD'; -- 代收货款
	REPAYMENT_SOURCE_BILL_TYPE_R   VARCHAR2(20) := 'R'; -- 小票
	REPAYMENT_SOURCE_BILL_TYPE_PL  VARCHAR2(20) := 'PL'; -- 外发单

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
	COD_COD_TYPE_RETURN_1_DAY     VARCHAR2(20) := 'R1'; -- 即日退
	COD_COD_TYPE_RETURN_3_DAY     VARCHAR2(20) := 'R3'; -- 三日退
	COD_COD_TYPE_RETURN_APPROVE   VARCHAR2(20) := 'RA'; -- 审核退
	COD_COD_TYPE_RETURN_3_APPROVE VARCHAR2(20) := 'R3RA'; -- 三日退和审核退
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
	--POD__POD_TYPE__BILLING VARCHAR2(20) := 'BILL'; -- 开单
	POD__POD_TYPE__POD VARCHAR2(20) := 'POD'; -- 签收
	POD__POD_TYPE__UPD VARCHAR2(20) := 'UPD'; -- 反签收

	-- ==================== 异常出库 OUT_STOCK_EXCEPTION ====================
	/**
  * EXCEPTION_TYPE 异常类型
  */
	OUT_STOCK_EXCEPTION_TYPE_LG VARCHAR2(20) := 'LG'; -- 丢货
	OUT_STOCK_EXCEPTION_TYPE_GG VARCHAR2(20) := 'GG'; -- 弃货
	OUT_STOCK_EXCEPTION_TYPE_CG VARCHAR2(20) := 'CG'; -- 违禁品

	/**
  * BILL_TYPE 坏账单单据类型
  */
	BAD_ACCOUNT_BILL_TYPE_BADDEDTS VARCHAR2(20) := 'BADDEBTS'; --坏账损失
	BAD_ACCOUNT_BILL_TYPE_INCOME   VARCHAR2(20) := 'INCOME'; --保险理赔

	/**
  * PRODUCT_CODE 产品类型
  */
	PRODUCT_CODE_FLF VARCHAR2(20) := 'FLF'; --精准卡航
	PRODUCT_CODE_FSF VARCHAR2(20) := 'FSF'; --精准城运
	PRODUCT_CODE_LRF VARCHAR2(20) := 'LRF'; --精准汽运(长途)
	PRODUCT_CODE_SRF VARCHAR2(20) := 'SRF'; --精准汽运(短途)
	PRODUCT_CODE_WVH VARCHAR2(20) := 'WVH'; --整车（三级）
	PRODUCT_CODE_AF  VARCHAR2(20) := 'AF'; --精准空运
	PRODUCT_CODE_PLF VARCHAR2(20) := 'PLF'; --汽运偏线
	PRODUCT_CODE_PKG VARCHAR2(20) := 'PACKAGE'; --快递包裹

	/**
  * INVOICE_MARK 发票标记
  */
	INVOICE_MARK_ONE VARCHAR2(20) := 'INVOICE_TYPE_01'; --11%运输专票_01
	INVOICE_MARK_TWO VARCHAR2(20) := 'INVOICE_TYPE_02'; --非运输专票_02

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

	------------------------------------------------------------------
	-- 通过组织编码获取组织标杆编码（缓存）
	------------------------------------------------------------------
	FUNCTION FUNC_GET_ORG_UNIFIED_CODE(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE;

	------------------------------------------------------------------
	-- 通过组织编码获取组织名称（缓存）
	------------------------------------------------------------------
	FUNCTION FUNC_GET_ORG_NAME(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE;

	------------------------------------------------------------------
	-- 通过客户编码获取客户名称（缓存）
	------------------------------------------------------------------
	FUNCTION FUNC_GET_CUSTOMER_NAME(P_CUST_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE;

	------------------------------------------------------------------
	-- 金额拆分取整(部分还款)
	------------------------------------------------------------------
	FUNCTION FUNC_AMOUNT_SPLIT_PART(P_FEE           NUMBER,
																	P_VERIFY_AMOUNT NUMBER,
																	P_AMOUNT        NUMBER) RETURN NUMBER;

	------------------------------------------------------------------
	-- 金额拆分取整(全部还完)
	------------------------------------------------------------------
	FUNCTION FUNC_AMOUNT_SPLIT_ALL(P_FEE           NUMBER,
																 P_VERIFY_AMOUNT NUMBER,
																 P_AMOUNT        NUMBER) RETURN NUMBER;

END PKG_STL_COMMON;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_COMMON IS

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
		PRAGMA AUTONOMOUS_TRANSACTION; -- 独立事务
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

	------------------------------------------------------------------
	-- 通过组织编码获取组织标杆编码（缓存）
	------------------------------------------------------------------
	FUNCTION FUNC_GET_ORG_UNIFIED_CODE(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE IS
		V_UNIFIED_CODE BSE.T_BAS_ORG.UNIFIED_CODE%TYPE;
		V_COUNT        NUMBER;
	BEGIN
		SELECT COUNT(T.ID)
			INTO V_COUNT
			FROM BSE.T_BAS_ORG T
		 WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
					 AND T.CODE = P_ORG_CODE;
	
		IF V_COUNT > 0 THEN
			SELECT T.UNIFIED_CODE
				INTO V_UNIFIED_CODE
				FROM BSE.T_BAS_ORG T
			 WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
						 AND T.CODE = P_ORG_CODE;
		ELSE
			V_UNIFIED_CODE := '';
		END IF;
	
		RETURN V_UNIFIED_CODE;
	END;
	------------------------------------------------------------------
	-- 通过组织编码获取组织名称（缓存）
	------------------------------------------------------------------
	FUNCTION FUNC_GET_ORG_NAME(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE IS
		V_ORG_NAME BSE.T_BAS_ORG.NAME%TYPE;
		V_COUNT    NUMBER;
	BEGIN
	
		SELECT COUNT(T.ID)
			INTO V_COUNT
			FROM BSE.T_BAS_ORG T
		 WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
					 AND T.CODE = P_ORG_CODE;
	
		IF V_COUNT > 0 THEN
			SELECT T.NAME
				INTO V_ORG_NAME
				FROM BSE.T_BAS_ORG T
			 WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
						 AND T.CODE = P_ORG_CODE;
		ELSE
			V_ORG_NAME := '';
		END IF;
	
		RETURN V_ORG_NAME;
	END;

	------------------------------------------------------------------
	-- 通过客户编码获取客户(散客)名称（缓存）
	------------------------------------------------------------------
	FUNCTION FUNC_GET_CUSTOMER_NAME(P_CUST_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE IS
		V_CUST_NAME BSE.T_BAS_CUSTOMER.NAME%TYPE;
		V_COUNT     NUMBER;
	BEGIN
		SELECT COUNT(T.ID)
			INTO V_COUNT
			FROM BSE.T_BAS_CUSTOMER T
		 WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
					 AND T.CODE = P_CUST_CODE;
	
		IF V_COUNT > 0 THEN
			SELECT T.NAME
				INTO V_CUST_NAME
				FROM BSE.T_BAS_CUSTOMER T
			 WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
						 AND T.CODE = P_CUST_CODE;
		ELSE
			V_CUST_NAME := '';
			/*SELECT COUNT(NT.ID)
        INTO V_COUNT
        FROM BSE.T_BAS_NONFIXED_CUSTOMER NT
       WHERE NT.ACTIVE = PKG_STL_COMMON.ACTIVE
             AND NT.CUSTCODE = P_CUST_CODE;
      IF V_COUNT > 0 THEN
        SELECT NT.CUSTNAME
          INTO V_CUST_NAME
          FROM BSE.T_BAS_NONFIXED_CUSTOMER NT
         WHERE NT.ACTIVE = PKG_STL_COMMON.ACTIVE
               AND NT.CUSTCODE = P_CUST_CODE;
      ELSE
        V_CUST_NAME := '';
      END IF;*/
		END IF;
	
		RETURN V_CUST_NAME;
	END;

	------------------------------------------------------------------
	-- 金额拆分取整(部分还款)
	------------------------------------------------------------------
	FUNCTION FUNC_AMOUNT_SPLIT_PART(P_FEE           NUMBER, --费用
																	P_VERIFY_AMOUNT NUMBER, --本次还款金额
																	P_AMOUNT        NUMBER --总金额
																	) RETURN NUMBER IS
		V_PRECISION NUMBER := 100; --精确到100
	BEGIN
		RETURN ROUND(P_FEE * P_VERIFY_AMOUNT / (P_AMOUNT * V_PRECISION)) * V_PRECISION;
	END;

	------------------------------------------------------------------
	-- 金额拆分取整(全部还完)
	------------------------------------------------------------------
	FUNCTION FUNC_AMOUNT_SPLIT_ALL(P_FEE           NUMBER, --费用
																 P_VERIFY_AMOUNT NUMBER, --累计已还金额
																 P_AMOUNT        NUMBER --总金额
																 ) RETURN NUMBER IS
		V_PRECISION NUMBER := 100; --精确到100
	BEGIN
		RETURN P_FEE - ROUND(P_VERIFY_AMOUNT * P_FEE /
												 (P_AMOUNT * V_PRECISION)) * V_PRECISION;
	END;

END PKG_STL_COMMON;
/
