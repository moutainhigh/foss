package com.deppon.foss.module.settlement.common.api.shared.define;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.util.CollectionUtils;

/**
 * 结算数据字典常量定义
 * 
 * @author ibm-zhuwei
 * @date 2012-10-15 上午11:50:13
 */
public class SettlementDictionaryConstants {

	// -------------------- 结算通用 SETTLEMENT --------------------
	/**
	 * IS_RED_BACK 是否红单
	 */
	public static final String SETTLEMENT__IS_RED_BACK__YES = "Y"; // 红单

	public static final String SETTLEMENT__IS_RED_BACK__NO = "N"; // 非红单

	/**
	 * CREATE_TYPE 单据生成方式
	 */
	public static final String SETTLEMENT__CREATE_TYPE__AUTO = "A"; // 系统生成

	public static final String SETTLEMENT__CREATE_TYPE__MANUAL = "M"; // 手工输入

	/**
	 * CUSTOMER_TYPE 客户类型
	 */
	public static final String SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER = "LC"; // 客户

	public static final String SETTLEMENT__CUSTOMER_TYPE__PARTIAL_AGENCY = "PA"; // 偏线代理

	public static final String SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY = "AA"; // 空运代理

	public static final String SETTLEMENT__CUSTOMER_TYPE__AIR = "A"; // 航空公司

	public static final String SETTLEMENT__CUSTOMER_TYPE__DRIVER = "D"; // 司机

	public static final String SETTLEMENT__CUSTOMER_TYPE__LANDSTOWAGE = "LS"; // 快递代理

	public static final String SETTLEMENT__CUSTOMER_TYPE__PACKAGENCE = "PKA"; // 包装代理供应商
	
	/**
	 * CUSTOMER_TYPE 客户类型_合伙人
	 */	
	public static final String CUSTOMER_TYPE__PARTNER = "P"; 
	
	/**
	 * WITHDRAW_MIN_PARTNER 合伙人提现最低额度1000元
	 */	
	public static final BigDecimal WITHDRAW_MIN_PARTNER = BigDecimal.valueOf(1000); 
	
	
	/**
	 * WITHHOLD_STATUS 结算-应收单-扣款状态_未扣款
	 */
	public static final String STL__WITHHOLD_STATUS__UNWITHHOLDED = "UWH"; 	
	
	/**
	 * WITHHOLD_STATUS 结算-应收单-扣款状态_扣款成功
	 */
	public static final String STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS = "WHS"; 
	
	/**
	 * WITHHOLD_STATUS 结算-应收单-扣款状态_扣款失败
	 */
	public static final String STL__WITHHOLD_STATUS__WITHHOLD_FAILED = "WHF"; 

	/**
	 * PAYMENT_TYPE 支付方式
	 */
	/**
	 * 现金
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__CASH = "CH"; // 现金

	/**
	 * 银行卡
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__CARD = "CD"; // 银行卡

	/**
	 * 电汇
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER = "TT"; // 电汇

	/**
	 * 支票
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__NOTE = "NT"; // 支票

	/**
	 * 网上支付
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__ONLINE = "OL"; // 网上支付

	/**
	 * 月结
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__CREDIT = "CT"; // 月结

	/**
	 * 临时欠款
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__DEBT = "DT"; // 临时欠款

	/**
	 * 到付
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT = "FC"; // 到付
	
	/**
	 * 余额  add by 353654
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__YUE = "BE"; //余额
	
	/**
	 * 到付转预收
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT__US = "FCUS"; // 到付转预收
	
	/**
	 * 委托派费转预收
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__COMMISSION_FEE="CF"; // 委托派费转预收
	
	/**
	 * 奖励应付自动返
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__REWARD_RETURN="JTU"; // 奖励应付自动返
	
	/**
	 * 快递差错应付自动返
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE__EXPRESS_ERROR_RETURN="KTU"; // 快递差错应付自动返
	
	/**
	 * BILL_PARENT_TYPE 单据类型
	 */
	public static final String SETTLEMENT__BILL_PARENT_TYPE__XS = "XS"; // 现金收款单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__YS = "YS"; // 应收单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__YF = "YF"; // 应付单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__HK = "HK"; // 还款单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__US = "US"; // 预收单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__DZ = "DZ"; // 对账单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__FK = "FK"; // 付款单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__XP = "XP"; // 小票单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__HZ = "HZ"; // 坏账单

	public static final String SETTLEMENT__BILL_PARENT_TYPE__UF = "UF"; // 预付单

	/**
	 * 付款工作流对接系统
	 */
	public static final String SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL = "N";// 费控

	public static final String SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC = "Y";// 财务共享

	// -------------------- 应收单 BILL_RECEIVABLE --------------------
	/**
	 * BILL_TYPE 应收单单据子类型
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE = "OR"; // 始发应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE = "DR"; // 到达应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE = "CR"; // 代收货款应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE = "AR"; // 空运其他应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE = "RR"; // 小票应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE = "DP"; // 到达偏线代理应收单

	public static final String BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY = "AA"; // 空运到达代理应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD = "AAC"; // 空运代理代收货款应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE = "DL"; // 到达快递代理应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD = "DLC"; // 快递代理代收货款应收单

	public static final String BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE = "LR"; // 快递代理其他应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTIAL_LINE_RECEIVABLE = "PR"; // 偏线其他应收

	public static final String BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE = "WOR"; // 代打木架其它应收/包装其他应收
	
	public static final String BILL_RECEIVABLE__BILL_TYPE__HOME_IMPROVEMENT = "HIR";// 家装应收
	
//	/**
//	 * BILL_TYPE 应收单单据子类型_合伙人代收货款应收
//	 */
//	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__ACCOUNT_RECEIVABLE_COD = "PARC"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人代收货款应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__DESTINATION_RECEIVABLE = "PDR"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人始发提成应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE = "POR"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人到付运费应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE = "PFCR"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人委托派费应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE = "PDFR"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人罚款应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__PENALTY = "PP"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人培训会务应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER__TRAIN_FEE = "PTF"; 
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人差错应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER_ERROR_RECEIVABLE = "PER";
	
	/**
	 * BILL_TYPE 应收单单据子类型_合伙人其他应收
	 */
	public static final String BILL_RECEIVABLE__BILL_TYPE__PARTNER_OTHER_FEE_RECEIVABLE = "POFR"; 

	/**
	 * SOURCE_BILL_TYPE 应收单来源单据子类型
	 */
	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL = "W"; // 运单
	
	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE__ACCIDENT = "A"; // QMS差错

	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE__OTHER_REVENUE = "R"; // 小票

	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE__MANUAL = "M"; // 人工录入

	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE__EXCEPTION = "E"; // 异常出库

	/**
	 * APPROVE_STATUS 应收单审核状态
	 */
	public static final String BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT = "NA"; // 未审核

	public static final String BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE = "AA"; // 已审核
	
	/**
	 * RECEIVABLE_DETAIL__FEE_TYPE 应收明细费用类型——开单应收
	 */
	public static final String RECEIVABLE_FEE_TYPE__PUBLISH_FREIGHT_EXTRA = "PFE";// 公布价运费提成
	
	public static final String RECEIVABLE_FEE_TYPE__BILLING_OPERATE = "BO";// 开单操作费
	
	public static final String RECEIVABLE_FEE_TYPE__PACKAGING_EXTRA = "PE";// 包装费提成
	
	public static final String RECEIVABLE_FEE_TYPE__SUPPORT_VALUE_EXTRA = "SVE";// 保价费提成
	
	public static final String RECEIVABLE_FEE_TYPE__COD_HANDING_EXTRA = "CHE";// 代收货款手续费提成
	
	public static final String RECEIVABLE_FEE_TYPE__DELIVER_EXTRA_NO_UPSTAIRS = "DENU";// 送货费提成（不含上楼）
	
	public static final String RECEIVABLE_FEE_TYPE__BASIC_DELIVER_EXTRA = "BDE";// 基础送货费提成
	
	public static final String RECEIVABLE_FEE_TYPE__MATTRESS_OPRATE_EXTRA = "MOE";// 床垫操作费提成
	
	public static final String RECEIVABLE_FEE_TYPE__AGENCY_CUSTOMERS_CHARGES_EXTRA = "ACCE";// 代理报关费提成
	
	public static final String RECEIVABLE_FEE_TYPE__DISMANTLE_PACKAGE_EXTRA = "DPE";// 拆包装费提成
	
	public static final String RECEIVABLE_FEE_TYPE__REGISTER_PARK_EXTRA = "RPE";// 登记费停车费提成
	
	public static final String RECEIVABLE_FEE_TYPE__OTHERS_EXTRA = "OE";// 其他费用提成
	
	public static final String RECEIVABLE_FEE_TYPE__GOODS_UPSTAIRS_EXTRA= "GUE";// 送货上楼费提成
	
	public static final String RECEIVABLE_FEE_TYPE__GOODS_ENTRY_EXTRA = "GEE";// 送货进仓费提成
	
	public static final String RECEIVABLE_FEE_TYPE__GOODS_BIG_UPSTAIRS_EXTRA = "GBUE";// 大件上楼费提成
	
	public static final String RECEIVABLE_FEE_TYPE__LONG_SEND_EXTRA = "LSE";// 超远派送费提成
	
	public static final String RECEIVABLE_FEE_TYPE__PROOF_DELIVERY_EXTRA = "PDE";// 签收单返回提成
	
	public static final String RECEIVABLE_FEE_TYPE__EXCEPTION_ACTION = "EA";// 异常操作费
	
	public static final String RECEIVABLE_FEE_TYPE__FAX_BACK_COST = "FBC";// 传真返单费成本
	
	public static final String RECEIVABLE_FEE_TYPE__MODIFY_LABEL_FEE = "MLF";// 更改标签费
	
	public static final String RECEIVABLE_FEE_TYPE__CHARGE_EXTRA_COST = "PCEC";//额外加收成本--二期新增
	
	public static final String RECEIVABLE_FEE_TYPE__CHARGE_EXTRA_FEE = "PCEF";//额外加收费用--二期新增
	
	/**
	 * RECEIVABLE_DETAIL__FEE_TYPE 应收明细费用类型——开单应收——到付运费
	 */
	public static final String RECEIVABLE_FEE_TYPE__TRANSPORT_FEE = "RFTTF"; //公布价运费
	
	public static final String RECEIVABLE_FEE_TYPE__PICKUP_FEE = "RFTPF"; //接送货费  
	
	public static final String RECEIVABLE_FEE_TYPE__DELIVER_GOODS_FEE = "RFTDGF"; //送货费 
	
	public static final String RECEIVABLE_FEE_TYPE__COD_FEE = "RFTCF"; //代收货款手续费
	
	public static final String RECEIVABLE_FEE_TYPE__PACKGE_FEE = "RFTPGF"; //包装费
	
	public static final String RECEIVABLE_FEE_TYPE__INSTRANCE_FEE = "RFTIF"; //保价费
	
	public static final String RECEIVABLE_FEE_TYPE__OTHER_FEE = "RFTOF"; //其它费用 
	
	public static final String RECEIVABLE_FEE_TYPE__COD = "RFTCOD"; //代收货款
	
	/**
	 * RECEIVABLE_DETAIL__FEE_TYPE 应收明细费用类型——合伙人罚款应收
	 */
	/*---------------------BILL_TYPE 应收单单据子类型_合伙人罚款应收 PARTNER__PENALTY 的 罚款类型-------------------*/
    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_GOODS_FAKE = "PPGF";//造假

    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_BUS_BODY_AD = "PPBBA";//车体广告

    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_FIRED_AND_SELL_GOODS = "PPFASG";//炒货卖货

    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_RECEIVABLE_OTHER = "PPRO";//其他

    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_QUALITY_INDICATORS = "PPQI";//品质指标

    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_VOLUME_INDICATORS = "PPVI";//货量指标
    
    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_DELIVER_INDICATORS = "PPDE";//快递指标
    
    public static final String RECEIVABLE_TYPE__PARTNER_PENALTY_FREIGHT_ERROR = "PPFE";//零担差错（L固定资产差错、L坐支营业款、L小票差错...标准化差错.）


    
    /**
	 * RECEIVABLE_DETAIL__FEE_TYPE 应收明细费用类型——合伙人培训费用应收
	 */
    public static final String RECEIVABLE_TYPE__PARTNER_TRAIN_TRAIN_COST_COLLECTION = "PTTCC";//培训费收款

    public static final String RECEIVABLE_TYPE__PARTNER_TRAIN_REGISTRATION_FEE_COLLECTION = "PTRFC";//会务费收款

    /**
	 * PAYABLE_DETAIL__FEE_TYPE 应付明细费用类型——合伙人差错应收
	 */
    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_EXCEPTIONAL_SIGN = "PEES";//异常签收

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_BARGAINS_GOODS = "PEBG";//抢货

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_SEND_LIMITATION = "PESL";//派送时效

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_SUBSTITUTE_ORIGIN = "PESO";//代打木架

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_OVER_WEIGHT_OVER_SQUARE_COST_EXTRACT = "PEOWOSCE";//超重超方成本提取

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_ONLINE_PAY_BILL_AMOUNT = "PEOPBA";//网上支付开单金额

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_RECEIVABLE_COMPLAIN = "PERC";//投诉

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_RECEIVABLE_SETTLEMENT = "PERS";//理赔

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_ONLINE_PAY_ERROR = "PEOPE";//网上支付差错

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_BARGAINS_ERROR = "PEBE";//抢货差错

    public static final String RECEIVABLE_TYPE__PARTNER_ERROR_TRANS_LTL_ERROR = "PELTLE";//快递差错（K开单快递超派、K开单补录超时、K线上补码差错...K开单差错）

	
	/**
	 * PAYABLE_DETAIL__FEE_TYPE 应付明细费用类型——开单费用类型
	 */
	public static final String PAYABLE_FEE_TYPE__PARTLINE_TRANSFER_EXTRA = "PTE";// 支线转运提成
	
	public static final String PAYABLE_FEE_TYPE__DELIVERY_NO_UPSTAIRS = "DNU";// 送货费（不含上楼）
	
	public static final String PAYABLE_FEE_TYPE___DELIVERY_EXTRA = "DE";// 派送提成
	
	public static final String PAYABLE_FEE_TYPE__GOODS_ENTRY = "PE";// 送货进仓费
	
	public static final String PAYABLE_FEE_TYPE__REVERT_BILL = "RB";// 签收单返单
	
	public static final String PAYABLE_FEE_TYPE__GOODS_UPSTAIRS_EXTRA_NO_BASE = "GUENB";// 送货上楼（不含基础送货费）
	
	public static final String PAYABLE_FEE_TYPE__GOODS_ENTRY_NO_BASE = "GENB";// 送货进仓（不含基础送货费）
	
	public static final String PAYABLE_FEE_TYPE__GOODS_BIG_UPSTAIRS_EXTRA_NO_BASE = "GBUNB";// 大件上楼（不含基础送货费）
	
	public static final String PAYABLE_FEE_TYPE__LONG_SEND = "LS";// 超远派送费
	
	public static final String PAYABLE_FEE_TYPE__PROOF_DELIVERY = "PD";// 签收单返单费     
	
	public static final String PAYABLE_FEE_TYPE__FREIGHT_COMMISSION_EXTRA= "PFC";//到付运费提成
	public static final String PAYABLE_FEE_TYPE__ACCEPT_CHANGE_SUBSIDIES = "ACS";// 更改单受理补贴
	
	public static final String PAYABLE_FEE_TYPE__REACH_COLLECTION_COMMISSION_EXTRA= "PRCOD";//到达代收货款提成
	
	
	/**
     * PAYABLE_DETAIL__FEE_TYPE 应付明细费用类型——合伙人业务奖励应付
     */
	/*---------------------BILL_TYPE 应付单单据子类型_合伙人业务奖励应付 PARTNER__BONUS 的 奖励类型-------------------*/
	public static final String  PAYABLE__TYPE__PARTNER__BONUS_DELIVER_NDICATORS= "PBDE"; //快递指标
	
	/**
	 * PAYABLE_DETAIL__FEE_TYPE 应付明细费用类型——合伙人快递差错应付
	 */
	public static final String  PAYABLE_FEE_TYPE__LTL_ERROR = "LTLE"; //快递差错(K开单快递超派、K开单补录超时、K线上补码差错...K开单差错)
	
	public static final String PAYABLE_FEE_TYPE__BARGAINS_GOODS = "BG";//抢货（直营营业部抢合伙人）
	
	/**
	 * PAYABLE_DETAIL__FEE_TYPE 应付明细费用类型——合伙人奖励应付
	 */
	public static final String  PAYABLE_FEE_TYPE__FREIGHT_ERROR = "FE"; //零担差错(L固定资产差错、L坐支营业款、L小票差错...L考勤差错)

	public static final String  PAYABLE_FEE_TYPE__K_QUALITY_INDEX = "QI"; //品质指标

	public static final String  PAYABLE_FEE_TYPE__K_QUANTITY_INDEX = "	QNI"; //货量指标

	public static final String  PAYABLE_FEE_TYPE__OTHER_BONUS = "OB"; //其他（奖励）


	/**
	 * 收款类别
	 */

	/*---------------------------- 小票收款类别 -------------------------------------------------------*/
	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__WAREHOUSE = "W"; // 仓储费/保管费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__PICKUP_DELIVERY = "PD"; // 自提改派送

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__DELIVERY = "D"; // 加收送货费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__CARD = "C"; // 会员卡费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__PACKAGE = "P"; // 包装费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__FUEL = "F"; // 放空费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__RECYCLE = "R"; // 卖废品

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__EXPIRED = "E"; // 超期预收款

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__RFC = "RFC"; // 更改费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__DELIVERY_UPSTAIRS = "DU"; // 送货上楼费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__POS = "POS"; // POS机手续费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__OTHER = "O"; // 其他/租房违约金

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__ACCIDENT = "A"; // 事故赔偿

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__MORE_PAY = "B"; // 客户多夫运费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__CHECK = "G"; // 盘点长款金额

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__INTEREST = "H"; // 收银员卡利息

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__FC = "FC"; // 富余仓库转租收入

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__REPAY_DEBIT = "RB"; // 还借支

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__WAREHOUSE_ENTRY = "WE"; // 进仓费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__SHIELD_BACK = "SB"; // 网银盾返款

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__DEPOSIT_RECOVERY = "DR"; // 押金回收

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__ADDITIONAL_DELIVERY = "AD"; // 加收派送费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__EXTERNAL_COMPENSATION = "EC"; // 外部赔款

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__RESERVE_FUND = "RF"; // 备用金上缴

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__FORKLIFT = "FO"; // 叉车费

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__ANOMALOUS_COD = "AC"; // 异常代收货款

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__HANDING_CHARGE = "HC"; // 银票手续费
	
	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__EXCEPTIONAL_HANDLING = "EH";// 异常货处置

	/*---------------------------- 包装其它应收收款类别 ---------------------------------------------------*/
	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__DAMAGE_PAKING_FORFEIT = "DPF"; // 破损率处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__DAMAGE_CLAIM_FORFEIT = "DCF"; // 破损理赔处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__FORKLIFT_TICKET = "FT"; // 叉车票

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__AGING_FORFEIT = "AF"; // 时效处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__MIXING_FORFEIT = "MF"; // 混打处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__LOSING_FORFEIT = "LF"; // 丢货处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__COMPLAINT_FORFEIT = "CF"; // 投诉处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__BATTEN_CHECK_FORFEIT = "BCF"; // 木条验收处罚

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__RECEIVE_OTHER = "RO"; // 应收其他

	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__INCONSISTENTWAYBILL = "I"; // 系统与实际差异单号

	/**
	 * BILL_TYPE 应付单单据子类型
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD = "APC"; // 应付代收货款

	public static final String BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST = "TF1"; // 外请车首款

	public static final String BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST = "TL1"; // 外请车尾款

	public static final String BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST = "TF2"; // 整车首款

	public static final String BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST = "TL2"; // 整车尾款

	public static final String BILL_PAYABLE__BILL_TYPE__AIR = "A"; // 航空公司运费

	public static final String BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL = "AAO"; // 空运出发代理应付

	public static final String BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY = "AAD"; // 空运到达代理应付

	public static final String BILL_PAYABLE__BILL_TYPE__AIR_OTHER = "AO"; // 空运其他应付

	public static final String BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE = "PL"; // 偏线代理成本

	public static final String BILL_PAYABLE__BILL_TYPE__SERVICE_FEE = "SF"; // 装卸费应付

	public static final String BILL_PAYABLE__BILL_TYPE__CLAIM = "C"; // 理赔应付

	public static final String BILL_PAYABLE__BILL_TYPE__REFUND = "R"; // 退运费应付

	public static final String BILL_PAYABLE__BILL_TYPE__COMPENSATION = "CP"; // 服务补救应付

	public static final String BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE = "LD"; // 快递代理外发成本

	public static final String BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER = "LDO"; // 快递代理其他应付

	public static final String BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER = "PO"; // 偏线其他应付

	public static final String BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE = "WOP"; // 代打木架其它应付/包装其他应付

	public static final String BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE = "WP"; // 代打木架应付/包装应付

	public static final String BILL_PAYABLE__BILL_TYPE__RENT_CAR = "RC"; // 临时租车应付

	public static final String BILL_PAYABLE__BILL_TYPE__DISCOUNT = "DC";// 折扣应付

	public static final String BILL_PAYABLE__BILL_TYPE__FREIGHT_DISCOUNT = "FDC";// 零担折扣应付
	
	public static final String BILL_PAYABLE__BILL_TYPE__HOME_IMPROVEMENT = "HIP";// 家装应付
	
	/**
	 * BILL_TYPE 应付单据子类型_合伙人到付运费应付
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE = "PFCP"; 
	
	/**
	 * BILL_TYPE 应付单单据子类型_合伙人到达提成应付
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE = "PDFP"; 
	
	/**
	 * BILL_TYPE 应付单单据子类型_合伙人委托派费应付
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE = "PDDF"; 
	
	/**
	 * BILL_TYPE 应付单单据子类型_合伙人快递差错应付
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__PARTNER__LTL_ERROR = "PLE"; 
	
	/**
	 * BILL_TYPE 应付单单据子类型_合伙人业务奖励应付
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__PARTNER__BONUS = "PB";
	
	/**
	 * BILL_TYPE 应付单单据子类型_合伙人其他应付
	 */
	public static final String BILL_PAYABLE__BILL_TYPE__PARTNER_OTHER_PAYABLE = "POP"; 
	
	/**
	 * SOURCE_BILL_TYPE 应付单来源单据子类型
	 */
	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL = "W"; // 运单
	
	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__ACCIDENT = "A"; // QMS差错

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE = "RS"; // 汽运配置单

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL = "AW"; // 航空正单

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP = "TP"; // 中转提货清单

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_CHANGE = "AC"; // 变更清单

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP = "AP"; // 合大票清单

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__PARTIAL_LINE = "PL"; // 外发单

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__EXCEPTION = "E"; // 异常出库

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__RENTCAR = "RC"; // 临时租车

	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__DISCOUNT = "DC"; // 折扣应付
	
	//运输合同：由于vts项目整车首尾款应付单是由打印运输合同生成的，不再是由FOSS配载单生成，同时来源单号也是取vts自于合同编码@218392 张永雪
	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSPORT_CONTRACTS = "TC";
	
	/**
	 * EFFECTIVE_STATUS 应付单生效状态
	 */
	public static final String BILL_PAYABLE__EFFECTIVE_STATUS__YES = "Y"; // 已生效

	public static final String BILL_PAYABLE__EFFECTIVE_STATUS__NO = "N"; // 未生效

	/**
	 * PAY_STATUS 应付单支付状态
	 */
	public static final String BILL_PAYABLE__PAY_STATUS__YES = "Y"; // 已支付

	public static final String BILL_PAYABLE__PAY_STATUS__NO = "N"; // 未支付
	
	/**
	 * STATUS 应收明细有效状态
	 */
	public static final String BILL_RECEIVABLE_D__STATUS__YES = "Y"; // 有效
	
	public static final String BILL_RECEIVABLE_D__STATUS__NO = "N"; // 无效
	
	/**
	 * STATUS 应付明细有效状态
	 */
	public static final String BILL_PAYABLE_D__STATUS__YES = "Y"; // 有效
	
	public static final String BILL_PAYABLE_D__STATUS__NO = "N"; // 无效


	/**
	 * FROZEN_STATUS 应付单冻结状态
	 */
	public static final String BILL_PAYABLE__FROZEN_STATUS__FROZEN = "F"; // 已冻结

	public static final String BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN = "N"; // //未冻结

	/**
	 * APPROVE_STATUS 应付单审核状态
	 */
	public static final String BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT = "NA"; // 未审核

	public static final String BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE = "AA"; // 已审核

	/**
	 * PAYER_TYPE 应付单付款方
	 */
	public static final String BILL_PAYABLE__PAYER_TYPE__ORIGIN = "O"; // 出发付款

	public static final String BILL_PAYABLE__PAYER_TYPE__DESTINATION = "D"; // 到达付款

	/**
	 * PAYABLE_TYPE 应付单应付类型
	 */
	public static final String BILL_PAYABLE__PAYABLE_TYPE__FIRST = "F"; // 首款

	public static final String BILL_PAYABLE__PAYABLE_TYPE__LAST = "L"; // 尾款

	public static final String BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK = "FB"; // 押回单首款

	public static final String BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK = "LB"; // 押回单尾款

	public static final String BILL_PAYABLE__PAYABLE_TYPE__MONTH = "MH"; // 月结

	public static final String BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING = "MAP"; // 主要包装

	public static final String BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING = "MNP"; // 次要包装/辅助包装

	public static final String BILL_PAYABLE__PAYABLE_TYPE__DAMAGE_PAKING_REWARD = "DPR";// 破损率奖励

	public static final String BILL_PAYABLE__PAYABLE_TYPE__AGING_REWARD = "AR";// 时效奖励

	public static final String BILL_PAYABLE__PAYABLE_TYPE__BOARD = "B";// 面板

	public static final String BILL_PAYABLE__PAYABLE_TYPE__WOODEN_STAND = "WS";// 木方

	public static final String BILL_PAYABLE__PAYABLE_TYPE__TRAY = "T";// 托盘

	public static final String BILL_PAYABLE__PAYABLE_TYPE__PACKING_TRAY = "PT";// 包装托盘

	public static final String BILL_PAYABLE__PAYABLE_TYPE__INCONSISTENT_WAYBILL = "I";// 系统与实际差异单号

	// 外租车使用应付类型，来判断是否多次标记
	public static final String BILL_PAYABLE__PAYABLE_TYPE__RENTCAR_REPET = "RPT"; // 多次标记

	/**
	 * PAYMENT_CATEGORIES 应付单支付类别
	 */
	public static final String BILL_PAYABLE__PAYMENT_CATEGORIES__CASH = "CH"; // 现金

	public static final String BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER = "TT"; // 电汇

	public static final String BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF = "W"; // 核销

	public static final String BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_CASH = "WCH"; // 核销后现金

	public static final String BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER = "WTT"; // 核销后电汇

	// -------------------- 现金收款单 BILL_CASH_COLLECTION --------------------
	/**
	 * BILL_TYPE 现金收款单单据类型
	 */
	public static final String BILL_CASH_COLLECTION__BILL_TYPE__CASH_COLLECTION = "C"; // 现金收款单

	/**
	 * SOURCE_BILL_TYPE 现金收款单来源单据子类型
	 */
	public static final String BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL = "W"; // 运单

	public static final String BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__REVENUE = "R"; // 小票

	/**
	 * STATUS 现金收款单单据状态
	 */
	public static final String BILL_CASH_COLLECTION__STATUS__SUBMIT = "S"; // 提交

	public static final String BILL_CASH_COLLECTION__STATUS__CONFIRM = "C"; // 确认收银

	// -------------------- 预收单 BILL_DEPOSIT_RECEIVED --------------------
	/**
	 * BILL_TYPE 预收单单据子类型
	 */
	public static final String BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED = "US"; // 预收单
	
	/**
	 * BILL_TYPE 预收单单据子类型_合伙人预收单
	 */
	public static final String BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_PARTNER = "DP";

	/**
	 * REFUND_STATUS 预收单退款状态
	 */
	public static final String BILL_DEPOSIT_RECEIVED__REFUND_STATUS__REFUNDED = "RD"; // 已退款

	public static final String BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND = "NR"; // 未退款

	public static final String BILL_DEPOSIT_RECEIVED__REFUND_STATUS__REFUNDING = "RG"; // 退款中

	/**
	 * STATUS 预收单单据状态
	 */
	public static final String BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT = "S"; // 已提交

	public static final String BILL_DEPOSIT_RECEIVED__STATUS__CONFIRM = "C"; // 确认收银

	/**
	 * TRANSPORT_TYPE 预收单运输类型
	 */
	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER = "LC"; // 专线客户

	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTIAL_AGENCY = "PA"; // 偏线代理

	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__AIR_AGENCY = "AA"; // 空运代理

	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LAND_STOWAGE = "LS"; // 快递代理

	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__PARTNER = "P"; // 事业合伙人

	// -------------------- 预付单 BILL_ADVANCED_PAYMENT --------------------
	/**
	 * BILL_TYPE 预付单单据子类型
	 */
	public static final String BILL_ADVANCED_PAYMENT__BILL_TYPE__AIR = "A"; // 空运
	
	/**
	 * BILL_TYPE 预付单单据子类型_合伙人保证金
	 */
	public static final String BILL_ADVANCED_PAYMENT__BILL_TYPE__PARTNER__MARGIN = "PM"; 

	/**
	 * AUDIT_STATUS 预付单审批状态
	 */
	public static final String BILL_ADVANCED_PAYMENT__AUDIT_STATUS__NOT_AUDIT = "NA"; // 未审批

	public static final String BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDITING = "AG"; // 审批中

	public static final String BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE = "AA"; // 审批通过

	public static final String BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_DISAGREE = "AD"; // 审批不通过

	// -------------------- 还款单 BILL_REPAYMENT --------------------

	/**
	 * BILL_TYPE 还款单单据类型
	 */
	public static final String BILL_REPAYMENT__BILL_TYPE__REPAYMENT = "HK";

	/**
	 * STATUS 还款单单据状态
	 */
	public static final String BILL_REPAYMENT__STATUS__SUBMIT = "S"; // 已提交

	public static final String BILL_REPAYMENT__STATUS__CONFIRM = "C"; // 已确认

	/**
	 * AUDIT_STATUS 还款单审核状态
	 */
	public static final String BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT = "NA"; // 未审核

	public static final String BILL_REPAYMENT__AUDIT_STATUS__AUDIT_AGREE = "AA"; // 已审核

	public static final String BILL_REPAYMENT_AUDIT_STATUS_AUDITING = "AG";// 审批中

	public static final String BILL_REPAYMENT_AUDIT_STATUS_DISAGREE = "AD";// 退回

	/**
	 * OPERATE_TYPE 还款单申请作废审批类型
	 */
	public static final String BILL_REPAYMENT_AUDIT_OPERATE_TYPE_TG = "A";
	public static final String BILL_REPAYMENT_AUDIT_OPERATE_TYPE_TH = "B";

	/**
	 * SOURCE_BILL_TYPE 还款单来源单据类型
	 */
	public static final String BILL_REPAYMENT__SOURCE_BILL_TYPE__WAYBILL = "W"; // 运费

	public static final String BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT = "FC"; // 到付费

	public static final String BILL_REPAYMENT__SOURCE_BILL_TYPE__COD = "COD"; // 代收货款

	public static final String BILL_REPAYMENT__SOURCE_BILL_TYPE__REVENUE = "R"; // 小票

	public static final String BILL_REPAYMENT__SOURCE_BILL_TYPE__PARTIAL_LINE = "PL"; // 外发单

	// -------------------- 付款单 BILL_PAYMENT --------------------
	/**
	 * BILL_TYPE 付款单单据子类型
	 */
	public static final String BILL_PAYMENT__BILL_TYPE__PAYMENT = "FK"; // 付款单

	/**
	 * REMIT_STATUS 付款单汇款状态
	 */
	public static final String BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER = "NT"; // 未汇款

	public static final String BILL_PAYMENT__REMIT_STATUS__TRANSFERRING = "TG"; // 汇款中

	public static final String BILL_PAYMENT__REMIT_STATUS__TRANSFERRED = "TD"; // 已汇款

	/**
	 * SOURCE_BILL_TYPE 付款单来源单据类型
	 */
	public static final String BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT = "YF"; // 应付单

	public static final String BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED = "YS"; // 预收单

	public static final String BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT = "DZ"; // 对账单

	public static final String BILL_PAYMENT__SOURCE_BILL_TYPE__PARTIAL_LINE = "PL"; // 外发单

	/**
	 * AUDIT_STATUS 付款单审核状态
	 */
	public static final String BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT = "NA"; // 未审核

	public static final String BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE = "AA"; // 已审核

	// -------------------- 对账单 STATEMENT_OF_ACCOUNT --------------------
	/**
	 * CONFIRM_STATUS 对账单确认状态
	 */
	public static final String STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM = "C"; // 已确认

	public static final String STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM = "N"; // 未确认
	
	/**
	 * 合伙人奖罚对账单自动扣款的默认操作员工编码和名称
	 * @author 367752
	 * @date 2016-09-03
	 */
	public static final String PARTNER_STATEMENT_OF_AWARD_EMPCODE = "partnerStatementOfAward_sysJob"; //编码

	public static final String PARTNER_STATEMENT_OF_AWARD_EMPNAME = "partnerStatementOfAward_sysJob"; //名称 


	/**
	 * BILL_TYPE 对账单单据类型
	 */
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__CUSTOMER_ACCOUNT = "CA"; // 客户对账单

	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__AGENT_ACCOUNT = "AA"; // 代理对账单

	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__AIR_FREIGHT_ACCOUNT = "AF"; // 空运对账单

	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__LAND_STOWAGE_ACCOUNT = "LA"; // 快递代理账单

	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_WOODEN_ACCOUNT = "YFWA"; // 应付代打木架对账单

	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_WOODEN_ACCOUNT = "YSWA"; // 应收代打木架对账单
	
	public static final String STL__BILL_TYPE__CUSTOMER_ACCOUNT = "CA"; // 客户对账单
	
	public static final String STL__BILL_TYPE__AGENT_ACCOUNT = "AA"; // 代理对账单
	
	public static final String STL__BILL_TYPE__AIR_FREIGHT_ACCOUNT = "AF"; // 空运对账单
	
	/**
	 * BILL_TYPE 对账单单据类型_合伙人付款对帐单
	 */
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__PARTNER__PAY_ACCOUNT = "PPA"; 
	
	/**
	 * BILL_TYPE 对账单单据类型_合伙人奖罚对帐单
	 */
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__PARTNER__BONUS_PENALTY_ACCOUNT = "PBPA"; 
	
	/**
	 * BILL_TYPE 对账单单据类型_合伙人收款对帐单
	 */
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__PARTNER__RECEIVE_ACCOUNT = "PRA"; 
	
	//家装对账单类型
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__YF_HOME_ACCOUNT = "YFHA"; // 应付家装对账单

	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__YS_HOME_ACCOUNT = "YSHA"; // 应收家装对账单
	
	// -------------------- 对账单明细 STATEMENT_OF_ACCOUNT_D --------------------
	/**
	 * BILL_PARENT_TYPE 对账单明细单据父类型
	 */
	public static final String STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE = "10.YS"; // 应收单

	public static final String STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE = "20.YF"; // 应付单

	public static final String STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED = "30.US";// 预收单
	
	public static final String STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT = "40.UF";// 预付单

	// -------------------- 核销单 BILL_WRITEOFF --------------------
	/**
	 * WRITEOFF_TYPE 核销单核销方式
	 */
	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE = "RP"; // 应收冲应付

	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__DEPOSIT_RECEIVABLE = "DR"; // 预收冲应收

	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__ADVANCED_PAYABLE = "AP"; // 预付冲应付

	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE = "RR"; // 还款冲应收

	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE = "PP"; // 付款冲应付

	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__BAD_RECEIVABLE = "BR"; // 坏账冲应收

	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__PAYABLE_DEPOSIT = "PD"; // 付款冲预收

	// -------------------- 现金收入报表明细 CASH_COLLECTION_RPT_D --------------------
	/**
	 * SOURCE_BILL_TYPE 现金收入（缴款）报表明细来源单据子类型
	 */
	public static final String CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION = "XS"; // 现金收款单

	public static final String CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED = "US"; // 预收单

	public static final String CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT = "HK"; // 还款单

	// -------------------- 代收货款 COD --------------------

	/**
	 * REFUND_PATH 代收货款退款路径
	 */
	public static final String COD__COD_REFUND_PATH__ONLINE = "ONL";

	public static final String COD__COD_REFUND_PATH__OFFLINE = "OFFL";

	/**
	 * COD_TYPE 代收货款类型（参考运单中代收货款退款类型）
	 */
	public static final String COD__COD_TYPE__RETURN_1_DAY = "R1"; // 即日退

	public static final String COD__COD_TYPE__RETURN_3_DAY = "R3"; // 三日退

	public static final String COD__COD_TYPE__RETURN_APPROVE = "RA"; // 审核退

	// @author 218392 zhangyongxue 2015-08-06 10:25:12 打包退
	public static final String COD_COD_TYPE_RETURN_PACK = "PACK"; // 打包退

	/**
	 * AIR_STATUS 空运\快递代理 代收货款状态
	 */
	public static final String COD__AIR_STATUS__NOT_AUDIT = "NA"; // 未审核

	public static final String COD__AIR_STATUS__AUDIT_AGREE = "AA"; // 已审核

	/**
	 * PUBLIC_PRIVATE_FLAG 对公对私标志
	 */
	public static final String COD__PUBLIC_PRIVATE_FLAG__COMPANY = "C"; // 对公

	public static final String COD__PUBLIC_PRIVATE_FLAG__RESIDENT = "R"; // 对私

	/**
	 * STATUS 代收货款状态
	 */
	public static final String COD__STATUS__NOT_RETURN = "NR"; // 未退款

	public static final String COD__STATUS__APPROVING = "AG"; // 待审核

	public static final String COD__STATUS__SHIPPER_FREEZE = "SF"; // 营业部冻结

	public static final String COD__STATUS__CASHIER_APPROVE = "CA"; // 收银员审核

	public static final String COD__STATUS__FUND_FREEZE = "FF"; // 资金部冻结

	public static final String COD__STATUS__RETURNING = "RG"; // 退款中

	public static final String COD__STATUS__RETURN_FAILURE_APPLICATION = "RFA"; // 退款失败申请

	public static final String COD__STATUS__NEGATIVE_RETURN_SUCCESS = "NRS"; // 反汇款成功

	public static final String COD__STATUS__RETURN_FAILURE = "RF"; // 退款失败

	public static final String COD__STATUS__RETURNED = "RD"; // 已退款

	// -------------------- 代收批次 COD_BATCH --------------------
	/**
	 * STATUS 代收批次状态
	 */
	public static final String COD_BATCH__STATUS__SENDING = "SG"; // 发送中

	public static final String COD_BATCH__STATUS__SEND_SUCCESS = "SS"; // 发送成功

	public static final String COD_BATCH__STATUS__SEND_FAIL = "SF"; // 发送失败

	public static final String COD_BATCH__STATUS__BANK_AUDIT_PASS = "AP"; // 银企审核通过

	public static final String COD_BATCH__STATUS__BANK_AUDIT_FAIL = "AF"; // 银企审核不通过

	// -------------------- 代收货款操作日志 COD_LOG --------------------
	/**
	 * OPERATE_TYPE 代收货款操作日志操作类型
	 */
	public static final String COD_LOG__OPERATE_TYPE__APPROVE = "A"; // 审核

	public static final String COD_LOG__OPERATE_TYPE__BACK = "BK"; // 退回

	public static final String COD_LOG__OPERATE_TYPE__ADD = "ADD"; // 新增代收货款

	public static final String COD_LOG__OPERATE_TYPE__NEGATIVE_APPROVE = "NA"; // 反审核

	public static final String COD_LOG__OPERATE_TYPE__FREEZE = "F"; // 冻结

	public static final String COD_LOG__OPERATE_TYPE__DISABLE = "D"; // 作废

	public static final String COD_LOG__OPERATE_TYPE__CHANGE_ACCOUNT = "CA"; // 更改账号

	public static final String COD_LOG__OPERATE_TYPE__EXPORT_TRANSFER = "ET"; // 导出汇款

	public static final String COD_LOG__OPERATE_TYPE__RETURN_APPLICATION = "RA"; // 汇款申请

	public static final String COD_LOG__OPERATE_TYPE__RETURN_SUCCESS = "RS"; // 汇款成功

	public static final String COD_LOG__OPERATE_TYPE__NEGATIVE_REFUND_SUCCESS = "NRS"; // 反汇款成功

	public static final String COD_LOG__OPERATE_TYPE__RETURN_FAILURE = "RF"; // 汇款失败

	public static final String COD_LOG__OPERATE_TYPE__FAIL_APPLY_PASSED = "FAP";// 汇款失败审核通过

	public static final String COD_LOG__OPERATE_TYPE__FAIL_APPLY_RETURNED = "FAR";// 汇款失败审核退回

	public static final String COD_LOG__OPERATE_TYPE__FUND_FREEZE = "FF";// 资金部冻结

	public static final String COD_LOG__OPERATE_TYPE__FUND_RELEASE_FREEZE = "FRF";// 资金部反冻结

	public static final String COD_LOG__OPERATE_TYPE__BATCH_BACK = "BB";// 代收货款整批退回

	// -------------------- 小票单据申请记录 NOTE_APPLICATION --------------------
	/**
	 * STATUS 小票单据申请单据状态
	 */
	public static final String NOTE_APPLICATION__STATUS__SUBMIT = "S"; // 已提交

	public static final String NOTE_APPLICATION__STATUS__DISTRIBUTE = "D"; // 已下发

	public static final String NOTE_APPLICATION__STATUS__IN = "I"; // 已入库

	/**
	 * APPROVE_STATUS 小票单据申请审批状态
	 */
	public static final String NOTE_APPLICATION__APPROVE_STATUS__NOT_AUDIT = "NA"; // 未审批

	public static final String NOTE_APPLICATION__APPROVE_STATUS__AUDIT_AGREE = "AA"; // 审批通过

	public static final String NOTE_APPLICATION__APPROVE_STATUS__AUDIT_DISAGREE = "AD"; // 审批不通过

	/**
	 * WRITE_OFF_STATUS 小票单据申请核销状态
	 */
	public static final String NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF = "NW"; // 未核销

	public static final String NOTE_APPLICATION__WRITEOFF_STATUS__APPLY_WRITEOFF = "AW"; // 申请核销

	public static final String NOTE_APPLICATION__WRITEOFF_STATUS__WRITEOFF_DONE = "WD"; // 已核销

	// -------------------- 小票单据发放入库 NOTE_STOCK_IN --------------------
	/**
	 * ISSUED_TYPE 小票单据发放下发方式
	 */
	public static final String NOTE_STOCK_IN__ISSUED_TYPE__EXPRESS = "E"; // 快递代理

	public static final String NOTE_STOCK_IN__ISSUED_TYPE__INTERNAL = "I"; // 内部带货

	public static final String NOTE_STOCK_IN__ISSUED_TYPE__PICKUP = "P"; // 自领

	// -------------------- 小票单据明细 NOTE_DETAILS --------------------
	/**
	 * STATUS 小票单据明细单据状态
	 */
	public static final String NOTE_DETAILS__STATUS__SUBMIT = "S"; // 已提交

	public static final String NOTE_DETAILS__STATUS__IN = "I"; // 已入库

	public static final String NOTE_DETAILS__STATUS__USED = "U"; // 已使用

	// -------------------- 小票 OTHER_REVENUE --------------------
	/**
	 * PAYMENT_TYPE 小票收款方式
	 */
	public static final String OTHER_REVENUE__PAYMENT_TYPE__CASH = "CH"; // 现金

	public static final String OTHER_REVENUE__PAYMENT_TYPE__CARD = "CD"; // 银行卡

	public static final String OTHER_REVENUE__PAYMENT_TYPE__CREDIT = "CT"; // 月结

	public static final String OTHER_REVENUE__PAYMENT_TYPE__DEBT = "DT"; // 临时欠款

	// -------------------- 发票 INVOICE --------------------
	/**
	 * SOURCE_BILL_TYPE 发票来源单据子类型
	 */
	public static final String INVOICE__SOURCE_BILL_TYPE__WAYBILL = "W"; // 运单

	public static final String INVOICE__SOURCE_BILL_TYPE__OTHER_REVENUE = "R"; // 小票

	/**
	 * INVOICE_TYPE 发票类型
	 */
	public static final String INVOICE__INVOICE_TYPE__CUSTOMER = "C"; // 客户

	public static final String INVOICE__INVOICE_TYPE__AGENCY = "A"; // 代理

	// -------------------- 操作日志 OPERATING_LOG --------------------
	/**
	 * OPERATE_BILL_TYPE 操作日志操作单据类型
	 */
	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__AIR_RECEIVABLE = "AR"; // 空运其他应收

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__AIR_PAYABLE = "AP"; // 空运其他应付

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYABLE = "YF"; // 应付单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_REPAYMENT = "HK"; // 还款单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT = "DZ"; // 对账单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_ADVANCED_PAYMENT = "UF"; // 预付单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_DEPOSIT_RECEIVED = "US"; // 预收单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYMENT = "FK"; // 付款单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_OTHER_REVENUE = "XP"; // 小票单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__BILL_CLAIM = "LP"; // 理赔单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__WAYBILL_RFC = "GG"; // 更改单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__PARTIAL_LINE = "WF"; // 外发单

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_RECEIVABLE = "LDR"; // 快递代理其他应收

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_PAYABLE = "LDP"; // 快递代理其他应付

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__PARTIAL_AGENCY_RECEIVABLE = "PAR"; // 偏线其他应收

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__PARTIAL_AGENCY_PAYABLE = "PAP"; // 偏线其他应付

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_OTHER_RECEIVABLE = "PKR";// 包装其他应收

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_OTHER_PAYABLE = "PKP";// 包装其他应付

	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__PACKING_PAYABLE = "PP";// 包装应付

	/**
	 * OPERATE_TYPE 操作日志操作类型
	 */
	public static final String OPERATING_LOG__OPERATE_TYPE__AUDIT = "A"; // 审核

	public static final String OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT = "RA"; // 反审核

	public static final String OPERATING_LOG__OPERATE_TYPE__FREEZE = "F"; // 冻结

	public static final String OPERATING_LOG__OPERATE_TYPE__DISABLE = "D"; // 作废

	public static final String OPERATING_LOG__OPERATE_TYPE__CONFIRM = "C"; // 确认

	public static final String OPERATING_LOG__OPERATE_TYPE__UNCONFIRM = "UC"; // 反确认

	public static final String OPERATING_LOG__OPERATE_TYPE__RETREAT = "RE"; // 退回

	public static final String OPERATING_LOG__OPERATE_TYPE__APPROVE = "AP"; // 审批

	public static final String OPERATING_LOG__OPERATE_TYPE__WRITEOFF = "WO"; // 核销

	public static final String OPERATING_LOG__OPERATE_TYPE__BACK_WRITEOFF = "BWO"; // 反核销

	public static final String OPERATING_LOG__OPERATE_TYPE__MODIFY = "M"; // 修改

	public static final String OPERATING_LOG__OPERATE_TYPE__RED_BACK = "RB"; // 红冲

	// -------------------- 理赔单 BILL_CLAIM --------------------
	/**
	 * PAYMENT_CATEGORIES 理赔单支付类别
	 */
	public static final String BILL_CLAIM__PAYMENT_CATEGORIES__CASH = "CH"; // 现金

	public static final String BILL_CLAIM__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER = "TT"; // 电汇

	public static final String BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF = "W"; // 核销

	public static final String BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF_CASH = "WCH"; // 核销后现金

	public static final String BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER = "WTT"; // 核销后电汇

	/**
	 * TYPE 理赔类型
	 */
	public static final String BILL_CLAIM__TYPE__CLAIM = "C"; // 理赔

	public static final String BILL_CLAIM__TYPE__REFUND = "R"; // 退运费

	public static final String BILL_CLAIM__TYPE__COMPENSATION = "CP"; // 服务补救

	/**
	 * RETURN_STATUS CRM退回发送状态
	 */
	public static final String BILL_CLAIM__RETURN__STATUS__NOT_RETURN = "NR"; // 未退回

	public static final String BILL_CLAIM__RETURN__STATUS_RETURNING = "RG"; // 退回中

	public static final String BILL_CLAIM__RETURN__STATUS_RETURNED = "RD"; // 已退回

	// -------------------- 理赔 CLAIM_STATUS_MSG --------------------
	/**
	 * MSG_ACTION 消息动作
	 */
	public static final String CLAIM_STATUS_MSG__MSG_ACTION__PASS = "P"; // 成功

	public static final String CLAIM_STATUS_MSG__MSG_ACTION__FAIL = "F"; // 失败

	/**
	 * MSG_STATUS 消息状态
	 */
	public static final String CLAIM_STATUS_MSG__MSG_STATUS__NEW = "N"; // 新增

	public static final String CLAIM_STATUS_MSG__MSG_STATUS__PROCESSED = "F"; // 已处理

	public static final String CLAIM_STATUS_MSG__MSG_STATUS__EXCEPTIONAL = "E"; // 异常

	// -------------------- 运单消息 WAYBILL_CHANGE_MSG --------------------
	/**
	 * MSG_ACTION 消息动作
	 */
	public static final String WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING = "PG"; // 新增/反操作

	public static final String WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE = "PD"; // 完结

	/**
	 * SOURCE_BILL_TYPE 来源单据类型
	 */
	public static final String WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE__RECEIVABLE = "R"; // 应收

	public static final String WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE__PAYABLE = "P"; // 应付

	public static final String WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE_CASHCOLLECTION = "C"; // 现金收款单

	// -------------------- 运单消息 POD_ENTITY --------------------
	/**
	 * POD_TYPE 签收/反签收类型
	 */
	public static final String POD_ENTITY__POD_TYPE__BILLING = "BILL";// 开单

	public static final String POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY = "POD"; // 签收

	public static final String POD_ENTITY__POD_TYPE__UN_PROOF_DELIVERY = "UPD"; // 反签收

	/**
	 * 车辆到达确认、车辆反到达确认（凭证使用）
	 */
	public static final String TRUCK_ARRIVE_CONFIRM = "TAC"; // 车辆到达

	public static final String TRUCK_ARRIVE_UNCONFIRM = "UAC";// 反车辆到达

	// -------------------- 异常出库 OUT_STOCK_EXCEPTION --------------------
	/**
	 * EXCEPTION_TYPE 异常出库异常类型
	 */
	public static final String OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS = "LG"; // 丢货

	public static final String OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS = "GG"; // 弃货

	public static final String OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS = "CG"; // 违禁品

	// -------------------- 司机收款报表明细 DRIVER_COLLECTION_RPT_D
	/**
	 * TYPE 司机收款报表明细类型
	 */
	public static final String DRIVER_COLLECTION_RPT_D__TYPE__PICKUP = "P"; // 接货

	public static final String DRIVER_COLLECTION_RPT_D__TYPE__DELIVERY = "D"; // 送货

	// -----------------------------财务收支平衡消息表T_STL_CREDIT_MSG-------------------

	/**
	 * 消息操作类型
	 */
	public static final String CREDIT_MSG_TYPE__WRITEOFF = "WF";// 核销

	public static final String CREDIT_MSG_TYPE_REVERS__WRITEOFF = "RWF";// 反核销

	public static final String CREDIT_MSG_TYPE__WRITEBACK = "WB";// 红冲

	public static final String CREDIT_MSG_TYPE__STAMP = "STAMP"; // 标记

	/**
	 * 财务平衡表类型
	 */
	public static final String CREDIT_MSG_CREDIT_TYPE__CUSTOMER = "C";// 客户收支平衡表

	public static final String CREDIT_MSG_CREDIT_TYPE__ORG = "O";// 部门收支平衡表

	/**
	 * 状态
	 */
	public static final String CREDIT_MSG_STATUS_NOT_EXECUTE = "NE";// 未执行

	public static final String CREDIT_MSG_STATUS_HAN_EXECUTE = "HE";// 已执行

	// --------------------------------付款单明细****************************************

	/**
	 * 来源单据类型
	 */
	public static final String BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE = "P";// 应付单

	public static final String BILL_PAYMENT_D_SOURCE_BILL_TYPE_DEPOSIT_RECEIVED = "DR";// 预收单

	/**
	 * 凭证部门类型
	 */
	public static final String VOUCHER_ORG_TYPE_ORIG = "O";// 出发
	public static final String VOUCHER_ORG_TYPE_DEST = "D";// 到达
	public static final String VOUCHER_ORG_TYPE_AIR = "A";// 空运
	public static final String VOUCHER_ORG_TYPE_PL = "P";// 偏线
	public static final String VOUCHER_ORG_TYPE_PKG = "G";// 快递代理
	/***
	 * 发票产生类别
	 * 
	 */
	public static final String SETTLEMENT_INVOICE_CATEGORY = "WAYBILL";// 结清货款生成的小票

	/***
	 * 发票标记
	 */
	public static final String SETTLEMENT_INVOICE_MARK_ONE = "INVOICE_TYPE_01"; // 01—运输专票11%
	public static final String SETTLEMENT_INVOICE_MARK_TWO = "INVOICE_TYPE_02";// 02—非运输专票

	/**
	 * 发票申请状态
	 */
	public static final String SETTLEMENT_INVOICE_STATUS_APPLIED = "AP";// 已申请
	public static final String SETTLEMENT_INVOICE_STATUS_NO_APPLY = "NP";// 未申请

	/**
	 * 是否申请发票
	 */
	public static final String SETTLEMENT_INVOICE_APPLY_INVOICE_YES = "Y";// 是
	public static final String SETTLEMENT_INVOICE_APPLY_INVOICE_NO = "N";// 否

	/**
	 * 超期装卸费审核状态
	 */
	public static final String OVERDUE_SERVICE_FEE_AUDIT_STATUS_NOT_APPLY = "NOT_APPLY"; // 未申请
	public static final String OVERDUE_SERVICE_FEE_AUDIT_STATUS_PROCESSING = "PROCESSING"; // 审批中
	public static final String OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED = "PASSED"; // 审批同意
	public static final String OVERDUE_SERVICE_FEE_AUDIT_STATUS_REJECTED = "REJECTED"; // 审批不同意

	// --------------------------------临时租车****************************************
	/**
	 * 预提状态
	 */
	public static final String WITHHOLDING_STATUS_NOT_TRANSFER = "NT";// 未预提

	public static final String WITHHOLDING_STATUS_TRANSFERRING = "TG";// 预提中

	public static final String WITHHOLDING_STATUS_TRANSFERED = "TD";// 已预提

	/**
	 * 费用类型
	 */
	public static final String RENTCAR_COST_TYPE_OIL_FEE_LONG = "14";// 油费（长途）

	public static final String RENTCAR_COST_TYPE_OIL_FEE_SHORT = "15";// 油费（短途）

	public static final String RENTCAR_COST_TYPE_ROAD_FEE_LONG = "16";// 路桥费（长途）

	public static final String RENTCAR_COST_TYPE_ROAD_FEE_SHORT = "17";// 路桥费（短途）

	public static final String RENTCAR_COST_TYPE_RENT_CAR_FEE = "13";// 临时租车费

	public static final String RENTCAR_COST_TYPE_TRANS_FEE = "19";// 运费

	public static final String RENTCAR_COST_TYPE_TRAFFIC_FEE = "20";// 交通费

	public static final String RENTCAR_COST_TYPE_OIL_RECHARGE_FEE = "18";// 油卡充值

	/**
	 * 租车用途
	 */
	public static final String RENTCAR_USE_TYPE_RECEIVE = "JH";// 接货

	public static final String RENTCAR_USE_TYPE_DELIVER = "SH";// 送货

	public static final String RENTCAR_USE_TYPE_TRANSLATE = "ZH";// 转货

	public static final String RENTCAR_USE_TYPE_DELIVER_RECEIVE = "JSH";// 接送货

	/**
	 * 理赔、退运费、服务补救申请部门类型
	 */
	public static final String CLAIM_APPLY_ORG_TYPE_ORIG = "ORIG";// 始发部门申请
	public static final String CLAIM_APPLY_ORG_TYPE_DEST = "DEST";// 到达部门申请

	/**
	 * 折扣单单据状态
	 */
	public static final String DISCOUNT_BILL_STATUS_CONFIRM = "C";// 已确认
	public static final String DISCOUNT_BILL_STATUS_NOT_CONFIRM = "N";// 未确认
	public static final String DISCOUNT_BILL_STATUS_DISABLE = "D";// 已作废

	/**

	 * 结清方式
	 */
	public static final String SETTLE_APPROACH_PC = "PC"; // PC端结清
	public static final String SETTLE_APPROACH_MOBILE = "MOBILE"; // 移动端结清（PDA）

	/** 
	 * 理赔付款-全现金理赔短信模板
	 */
	public static final String CLAIMS_PAYMENT_CH = "CLAIMS_PAYMENT_CH";
	
	/**
	 * 理赔付款-全电汇理赔短信模板
	 */
	public static final String CLAIMS_PAYMENT_TT = "CLAIMS_PAYMENT_TT";
	
	/**
	 * 资金代收货款审批状态
	 */
	public static final String SETTLE_CODAUDIT_FUNDAUDIT="FA";//资金部待审核
	public static final String SETTLE_CODAUDIT_FUNDLOCK="FL";//资金部锁定
	public static final String SETTLE_CODAUDIT_FUNDCANCELLOCK="FCL";//资金部取消锁定
	public static final String SETTLE_CODAUDIT_REVIEWAUDIT="RA";//复核会计待审核
	public static final String SETTLE_CODAUDIT_REVIEWLOCK="RL";//复核会计锁定
	public static final String SETTLE_CODAUDIT_REVIEWCANLELLOCK="RCL";//复核会计取消锁定
	public static final String SETTLE_CODAUDIT_REVIEWOVER="RO";//复合组审核通过
	public static final String SETTLE_SHORT_LOCK = "SSL";//短期锁定:针对签收时间在90天到730天未退款
	public static final String SETTLE_LONG_LOCK = "SLL";//长期锁定:针对签收时间730天以上未退款
	
	/** 理赔付款-冲销后电汇理赔短信模板
	 */
	public static final String CLAIMS_PAYMENT_WTT = "CLAIMS_PAYMENT_WTT";
	
	/**
	 * 理赔付款-冲销后现金理赔短信模板
	 */
	public static final String CLAIMS_PAYMENT_WCH = "CLAIMS_PAYMENT_WCH";
	
	/**
	 * 理赔付款-全部冲销理赔短信模板
	 */
	public static final String CLAIMS_PAYMENT_W = "CLAIMS_PAYMENT_W";
	
	/**
	 * 理赔付款-在线理赔 
	 */
	public static final String CLAIMSWAY_ONLINE = "3";
	

	/**
	 * 268217
	 * 理赔出库
	 */
	public static final String CLAIMSWAY_CLAIMS_OUT = "CLAIMS_OUT";


	/*
	 * 非现金盘点---所属模块
	 */
	/**
	 * 对账单
	 */
	public static String NCI_STATEMENT = "对账单";
	
	/**
	 * 结清货款
	 */
    public static String NCI_SETTLE="结清货款";
    
    /**
     * 预存款
     */
    public static String NCI_DEPOSIT="预存款";
    
    /**
     * 待刷卡单据
     */
    public static String NCI_WAYBILL="待刷卡单据";
    
    /**
     * 快递
     */
    public static String NCI_KD="快递";

    //start:add by 269044-zhurongrong-灰名单
	/**
	 * 灰名单--进入灰名单
	 */
	public static final String GRAYCUSTOMER_IN = "0";
	
	/**
	 * 灰名单--移除灰名单
	 */
	public static final String GRAYCUSTOMER_OUT = "1";
	
	/**
	 * 灰名单-通知快递是否全表删除--是
	 */
	public static final String ISALLDELETE_Y = "Y";
	
	/**
	 * 灰名单-通知快递是否全表删除--否
	 */
	public static final String ISALLDELETE_N = "N";
	
	/**
	 * 运单的来源系统
	 */
	public static final String SOURCE_SYSTEM_ECS = "ECS";
	
	/**
	 * 开单
	 */
	public static final String FOSS_ESB2FOSS_ECS_WAYBILL_BILLING="FOSS_ESB2FOSS_ECS_WAYBILL_BILLING";
	/**
	 * 补码
	 */
	public static final String FOSS_ESB2FOSS_COMPLEMENT_FUNCTION="FOSS_ESB2FOSS_COMPLEMENT_FUNCTION";
	/**
	 * 同步运单
	 */
	public static final String FOSS_ESB2FOSS_ECS_SYNC_WAYBILL="FOSS_ESB2FOSS_ECS_SYNC_WAYBILL";
	/**
	 * 同步签收
	 */
	public static final String FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT="FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT";
	//end

	
	/**
	 * 通知ptp扣款的场景 ONLINE--网上支付  DISABLE--作废还款单
	 */
	public static final String FOSS_PTP_SEND_WITHHOLD_STATUS_ONLINE = "ONLINE";
	
	public static final String FOSS_PTP_SEND_WITHHOLD_STATUS_DISABLE = "DISABLE";

}


