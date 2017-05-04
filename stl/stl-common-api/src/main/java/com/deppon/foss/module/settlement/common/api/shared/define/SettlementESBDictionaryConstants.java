package com.deppon.foss.module.settlement.common.api.shared.define;

/**
 * ESB对接数据字典常量 
 * @author foss-zhuwei
 * @date 2012-12-26 上午11:03:28
 */
public class SettlementESBDictionaryConstants {

	// -------------------- ESB 接口常量定义 --------------------

	/**
	 * ESB HEADER常量信息
	 */
	public static final String ESB_HEADER__SOURCE_SYSTEM = "FOSS"; // FOSS系统

    public static final String ESB_HEADER__MESSAGE_FORMAT = "SOAP"; // 消息格式:SOAP格式

	public static final Integer ESB_HEADER__EXCHANGE_PATTERN = 1; // 请求响应方式

	public static final String ESB_HEADER__VERSION = "1.0"; // 版本号

	/**
	 * ESB SERVICE CODE
	 */
	public static final String ESB_FOSS2ESB_OBTAIN_NUMBER = "ESB_FOSS2ESB_OBTAIN_NUMBER"; // 占用认领编号

	public static final String ESB_FOSS2ESB_RELEASE_NUMBER = "ESB_FOSS2ESB_RELEASE_NUMBER"; // 释放认领编号

	public static final String ESB_FOSS2ESB_QUERY_PAYINFO = "ESB_FOSS2ESB_QUERY_PAYINFO"; // 在线支付信息查询

	public static final String ESB_FOSS2ESB_BACK_FREIGHT_CHECK = "ESB_FOSS2ESB_BACK_FREIGHT_CHECK"; // 退运费状态校验

	public static final String ESB_FOSS2ESB_QUERY_TRANSFER = "ESB_FOSS2ESB_QUERY_TRANSFER"; // 查询汇款记录
	
	public static final String ESB_FOSS2ESB_BANK_PAY_REFUND = "ESB_FOSS2ESB_BANK_PAY_REFUND"; // 将代收货款数据发送给银企
	
	public static final String ESB_FOSS2ESB_SEND_MAIL = "ESB_FOSS2ESB_SEND_MAIL"; // 向官网发送客户联系人邮件信息

	public static final String ESB_FOSS2ESB_BANK_PAY_REFUND_DESC = "退代收货款接口，将代收货款数据发送到银企，在银企系统中退款"; // 退代收货款接口，将代收货款数据发送到银企，在银企系统中退款 
	
	public static final String FOSS_ESB2FOSS_GENERATE_CLAIMSAPBILL = "FOSS_ESB2FOSS_GENERATE_CLAIMSAPBILL"; // 生成理赔应付单接口
	
	public static final String FOSS_ESB2FOSS_GENERATE_CLAIMSAPBILL_DESC = "生成理赔应付单接口";
	
	public static final String ESB_FOSS2ESB_RECEIVE_CREDITUSED = "ESB_FOSS2ESB_RECEIVE_CREDITUSED"; // 发送月结客户已用信用额度接口
	
	public static final String ESB_FOSS2ESB_RECEIVE_CREDITUSED_DESC = "发送月结客户已欠款额度接口";

	public static final String ESB_FOSS2FOSS_CASH_DATA = "ESB_FOSS2FOSS_CASH_DATA"; // 发送现金缴款数据到财务自助接口
	
	public static final String ESB_FOSS2FOSS_CASH_DATA_DESC = "发送现金缴款数据到财务自助接口";
	
	public static final String ESB_FOSS2ESB_DELETE_POS_DATA = "ESB_FOSS2ESB_DELETE_POS_DATA";//对接财务自助,删除对应交易流水号信息借口

	/** 可开票余额接口-服务端 */
	public static final String ESB_FOSS2ESB_FIMS_SYN_REMAIN = "ESB_FOSS2ESB_FIMS_SYN_REMAIN";

	/** 定额发票登记信息接口-服务端 */
	public static final String ESB_FOSS2ESB_FIMS_SYN_REGISTER = "ESB_FOSS2ESB_FIMS_SYN_REGISTER";

	public static final String ESB_FOSS2ESB_GENERATE_ACCRUED_WORKFLOW = "ESB_FOSS2ESB_GENERATE_ACCRUED_WORKFLOW";//生成报账预提工作流

	/**
	 * 发送电子发票开票信息接口
	 */
	public static final String ESB_FOSS2ESB_SYNC_ORDER2FIMS_LTLANDEXPRESS = "ESB_FOSS2ESB_SYNC_ORDER2FIMS_LTLANDEXPRESS"; // 将电子发票发送给开票系统

	/**
	 *  发送电子发票开票信息接口
	 */
	public static final String ESB_FOSS2ESB_SYNC_ORDER2FIMS_LTLANDEXPRESS_DESC = "发送电子发票开票信息接口"; // 发送电子发票开票信息接口
	
	/**
     * 财务自助提供的占用/释放网上支付汇款接口
     */
    public static final String ESB_FOSS2ESB_OBTAIN_OL_REMIT_AMOUNT = "ESB_FOSS2ESB_OBTAIN_OL_REMIT_AMOUNT";

    public static final String ESB_FOSS2ESB_OBTAIN_OL_REMIT_AMOUNT_DESC = "财务自助提供的查询网上支付汇款接口";

    /**
     * 财务自助提供的查询网上支付汇款接口
     */
    public static final String ESB_FOSS2ESB_QUERY_OL_REMITINFO = "ESB_FOSS2ESB_QUERY_OL_REMITINFO";

    public static final String ESB_FOSS2ESB_QUERY_OL_REMITINFO_DESC = "财务自助提供的查询网上支付汇款接口";

    // -------------------- 官网接口数据字典 BHO --------------------

	/**
	 * 公用未知类型
	 */
	public static final int BHO__REFUND_TYPE__RETURN_UNKNOWN = -1; // 未知类型

	/**
	 * 接口：查询代收货款信息 字段：退款类型
	 */

	public static final int BHO__REFUND_TYPE__RETURN_1_DAY = 1; // 即日退

	public static final int BHO__REFUND_TYPE__RETURN_3_DAY = 2; // 三日退

	public static final int BHO__REFUND_TYPE__RETURN_APPROVE = 3; // 审核退

	/**
	 * 接口：查询代收货款信息 字段：退款状态
	 */
	public static final int BHO__REFUND_STATUS__NOT_RETURN = 1; // 未退款

	public static final int BHO__REFUND_STATUS__RETURNING = 2; // 退款中

	public static final int BHO__REFUND_STATUS__RETURNED = 3; // 已退款

	public static final int BHO__REFUND_STATUS__RETURN_FAILURE = 4; // 退款失败
	
	/**
	 * 接口：查询应收单信息 字段：支付方式
	 */
	public static final String BHO__PAYMENT_TYPE__FREIGHT_COLLECT = "1";// 到付
	
	public static final String BHO__PAYMENT_TYPE__CREDIT = "2";// 月结
	
	public static final String BHO__PAYMENT_TYPE__DEBT = "3";// 临欠
	
	public static final String BHO__PAYMENT_TYPE__ONLINE = "4";// 网上支付
	
	
	/**
	 * 接口： 官网在线监控查询方式
	 */
	public static final String BHO_ONLION_QUERYTYPE_PAYDATE = "1";//支付日期查询
	
	public static final String BHO_ONLION_QUERYTYPE_VERIFYDATE = "2";//核销日期查询
	
	public static final String BHO_ONLION_QUERYTYPE_WAYBILLNOS = "3";//运单号查询
	
	public static final String BHO_ONLION_QUERYTYPE_STATEMENTNOS = "4";//对账单号查询
	
	
	/**
	 * 接口 ：支付类型
	 */
	public static final String BHO_ONLION_PAYTYPE_RECEIVE = "0";//应收单
	
	public static final String BHO_ONLION_PAYTYPE_STATEMENT = "1";//对账单
	
	/**
	 * 支付状态
	 */
	public static final String BHO_ONLION_PAYSTATUS_SUCCESS = "2";//支付成功
	
	public static final String BHO_ONLION_PAYSTATUS_NO = "1";//未 支付
	
	public static final String BHO_ONLION_PAYSTATUS_FAILURE = "0";//支付失败、
	
	/**
	 * 核销状态
	 */
	public static final String BHO_ONLION_VERIFYSTATUS_NO = "0";//未核销
	
	public static final String BHO_ONLION_VERIFYSTATUS_FAILURE = "2";//核销失败
	
	public static final String BHO_ONLION_VERIFYSTATUS_SUCCESS = "1";//已核销
	
	/**
	 * 费控接口数据字典 COSTCONTROL 审批状态
	 */
	
	public static final  String COST_CONTROL__RESULT__SUCESS = "1";//预付款状态审批通过常量

	public static final  String COST_CONTROL__RESULT__FAIL = "2";//预付单状态审批失败常量

	public static final String COST_CONTROL__RESULT__SUCCESS_TO_FAIL = "3";//预付款审批成功转失败常量

	
	// -------------------- 财务自助接口数据字典 FINANCE --------------------
	public static final String FINANCE_QUERYBADDEBT_TRANTYP_AIR = "空运";//运输方式 --查询坏账
	public static final String FINANCE_QUERYBADDEBT_TRANTYP_MOTOR = "汽运";//运输方式 --查询坏账
	
	/**
	 * 坏账核销接口冲账方式
	 */
	public static final String FINANCE__BAD_ACCOUNT__BILL_TYPE__INCOME = "INCOME";//冲账方式--保险理赔
	public static final String FINANCE__BAD_ACCOUNT__BILL_TYPE__BADDEDTS = "BADDEBTS";//冲账方式--坏账损失
	
	// -------------------- 费控接口数据字典  --------------------
	/**
	 * 规定FOSS日常工作流明细
	 */
	public static final String[] WORKFLOW_DETAIL_DAY = {
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE, // 装卸费应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM, // 理赔应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND,// 退运费应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION, // 服务补救应付
		SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED // 退预收
	};
		
	/**
	 * 规定FOSS运作成本工作流明细
	 */
	public static final String[] WORKFLOW_DETAIL_COST = {
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST, // 外请车首款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST, // 外请车尾款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,// 整车首款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST, // 整车尾款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR, // 航空公司运费
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL, // 空运出发代理应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY,// 空运到达代理应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER,// 空运其他应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE,// 偏线代理成本
		SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE__AIR,//预付款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE,// 快递代理外发成本
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER, // 快递代理其他应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER
	};
	
	/**
	 * 包装工作流明细
	 */
	public static final String[] WORKFLOW_DETAIL_PACK = {
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE,// 代打木架其它应付/包装其他应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE// 代打木架应付/包装应付
	};
	
	/**
	 * 规定FOSS外请车工作流明细
	 */
	public static final String[] WORKFLOW_DETAIL_DRIVER = {
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST, // 外请车首款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST, // 外请车尾款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,// 整车首款
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST // 整车尾款
	};
	
	/**
	 * 合伙人工作流明细
	 */
	public static final String[] PARTNER_DETAIL_PACK = {
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE,// 合伙人到付运费应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE,// 合伙人到达提成应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE// 合伙人委托派费应付
	};
	
	/**
	 * 费用明细值 
	 * 1-FOSS装卸费； 
	 * 2-FOSS理赔； 3-FOSS退运费；4-FOSS减免运费；5-FOSS退预收；
	 * 6-FOSS预付款、7-FOSS偏线外发成本；8- FOSS航空代理成本；
	 * 9-FOSS外请车整车运费</documentation>
	 */
	public static final String COST_CONTROL_PAY_DETAIL_SERVICE_FEE= "1";// 装卸费应付
	public static final String COST_CONTROL_PAY_DETAIL_CLAIM= "2"; // 理赔应付
	public static final String COST_CONTROL_PAY_DETAIL_REFUND= "3";// 退运费应付
	public static final String COST_CONTROL_PAY_DETAIL_COMPENSATION= "4";//减免运费
	public static final String COST_CONTROL_PAY_DETAIL_DEPOSIT_RECEIVED= "5";//退预收
	public static final String COST_CONTROL_PAY_DETAIL_ADVANCE= "6";//预付
	public static final String COST_CONTROL_PAY_DETAIL_PARTIAL_LINE= "7";//偏线成本
	public static final String COST_CONTROL_PAY_DETAIL_AIR= "8";//空运代理成本
	public static final String COST_CONTROL_PAY_DETAIL_TRUCK_FIRST= "9";//首款
	public static final String COST_CONTROL_PAY_DETAIL_TRUCK_LAST= "10";//尾款
	public static final String COST_CONTROL_PAY_DETAIL_LAND_STOWAGE= "11";//快递代理外发成本
	public static final String COST_CONTROL_PAY_DETAIL_WOODEN= "12";//代打木架应付
	
	public static final String COST_CONTROL_PAY_DETAIL_HOME= "13";//家装应付
	
	public static final String COST_CONTROL_PAY_DETAIL_DELIVERY= "14";//到达提成应付
	public static final String COST_CONTROL_PAY_DETAIL_DELEGATION= "15";//委托到达提成应付
	public static final String COST_CONTROL_PAY_DETAIL_FREIGHT= "16";//到付运费应付
	public static final String COST_CONTROL_PAY_DETAIL_WITHDRAW= "18";//合伙人预存款提现应付
	
	public static final String COST_CONTROL_PAY_DETAIL_PARTNER_AWARD= "17";//合伙人奖罚应付
	/**
	 * 支付类型
	 */
	public static final String COST_CONTROL_PAYMENT_PAY= "1";//付款
	public static final String COST_CONTROL_PAYMENT_APPLY= "2";//报销
	
	/**
	 * 工作流类型  --此处划分这么细主要是为了费控返回处理结果时继续处理。
	 */
	public static final String COST_CONTROL_WORKFLOW_DAY_PAY= "1";//FOSS日常工作流--付款 
	public static final String COST_CONTROL_WORKFLOW_DAY_APPLY = "2";//FOSS日常工作流--报销 
	public static final String COST_CONTROL_WORKFLOW_COST_PAY= "3";//FOSS运作成本工作流--付款
	public static final String COST_CONTROL_WORKFLOW_COST_PAY_ADV= "4";//FOSS运作成本工作流--付款(预付)
	public static final String COST_CONTROL_WORKFLOW_COST_APPLY= "5";//FOSS运作成本工作流--报销
	//外请车两个字段是给报账系统额外添加
	public static final String COST_CONTROL_WORKFLOW_COST_DRIVER_PAY= "6";//FOSS外请车--付款
	public static final String COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY= "7";//FOSS外请车--报销
	//代打木架
	public static final String COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY= "8";//FOSS包装费--付款
	//临时租车
	public static final String COST_CONTROL_WORKFLOW_RENT_CAR_PAY= "9";//FOSS临时租车-付款
	public static final String COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_PAY= "10";//FOSS临时租车（交通费）—付款
	public static final String COST_CONTROL_WORKFLOW_RENT_CAR_APPLY= "11";//FOSS临时租车-报销
	public static final String COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY= "12";//FOSS临时租车（交通费）-报销
	//家装
	public static final String COST_CONTROL_WORKFLOW_PACK_HOME_PAY= "13";//家装--付款
	//理赔
	public static final String COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM= "17";//FOSS理赔--付款
	public static final String COST_CONTROL_WORKFLOW_PARTNER_PAY= "14";//合伙人--付款
	
	public static final String COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY= "16";//合伙人提现--付款（电汇）
	
	public static final String COST_CONTROL_WORKFLOW_PARTNER_AWARD_PAY= "15";//合伙人奖罚--付款
	
	//预提工作流类型
	public static final String COST_CONTROL_WORKFLOW_WITHHOLDING= "1";//预提工作流
	
	/**
	 * 币种 
	 */
	public static final String CURRENCY_CODE_RMB = "1";//人民币
	
	public static final String CURRENCY_CODE_HKD = "2";//港币
	
	/**
	 * 是否冲借支
	 */
	public static final String AUTOABATEMENTLOAN_Y = "Y";//自动冲借支
	
	public static final String AUTOABATEMENTLOAN_N = "N";//非自动冲借支
	
	/**
	 * 是否月结
	 */
	public static final String ISMONTH_Y = "Y";//月结
	
	public static final String ISMONTH_N = "N";//非月结
	/**
	 * 押回单到达
	 */
	public static final String BEMIDWAYLOAD_Y = "Y";//押回单
	
	public static final String BEMIDWAYLOAD_N = "N";//非押回单
	
	/**
	 * 奖罚类型
	 */
	public static final String AWARDTYPE_REWARD = "奖励";
	
	public static final String AWARDTYPE__FINE = "惩罚";
	
	/**
	 * 奖罚类型
	 */
	public static final String PAY_TYPE_START = "1";//出发
	
	public static final String PAY_TYPE_ARRIVE = "2";//到达
	
	/**
	 * 支付类型
	 */
	public static final String PAYMENT_TYPE_START_ALL = "1";//出发全额付
	
	public static final String PAYMENT_TYPE_ARRIV_EALL = "2";//到达全额付
	
	public static final String PAYMENT_TYPE_START_PART = "3";//出发部分付
	// -------------------- CRM口数据字典 CRM --------------------
	/**
	 * 理赔
	 */
	public static final String CRM__BUSINESS_TYPE__CLAIM = "1";
	
	/**
	 * CRM账户类型
	 */
	public static final String CRM_ACCOUNT_TYPE_PUBLIC ="PUBLIC_ACCOUNT";//对公账户
	public static final String CRM_ACCOUNT_TYPE_PRIVATE="PRIVATE_ACCOUNT";//对私账户
	public static final String CRM_ACCOUNT_TYPE_CASHIER="BACKUP_ACCOUNT";//收银员账户

	/**
	 * 退运费
	 */
	public static final String CRM__BUSINESS_TYPE__REFUND = "2";

	/**
	 * 服务补救
	 */
	public static final String CRM__BUSINESS_TYPE__COMPENSATION = "3";
	

	/**
	 * 同步现金缴款接口调用时每批大小
	 */
	public static final int ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE = 10000;
	/**
	 * 预提的费用类型
	 */
	public static final String RENTCAR_COST_TYPE_RENT_CAR_FEE = "1";//临时租车费
	
	public static final String RENTCAR_COST_TYPE_OIL_FEE_LONG = "2";//油费（长途）

	public static final String RENTCAR_COST_TYPE_OIL_FEE_SHORT = "3";//油费（短途）

	public static final String RENTCAR_COST_TYPE_ROAD_FEE_SHORT = "4";//路桥费（短途）

	public static final String RENTCAR_COST_TYPE_TRANS_FEE = "5";//运费
	
	/**
	 *接口响应成功标志 
	 */
	public static final int INTERFACE_RESPONSE_SUCCESS = 1;//调用接口方法成功
	
	/**
	 *接口响应失败标志 
	 */
	public static final int INTERFACE_RESPONSE_FAILED = 0;//调用接口方法失败
	
	
	// -------------------- PTP接口数据字典常量定义  --------------------
	
	/**
	 * 合伙人系统代码PTP
	 */
	public static final String SOURCE_SYSTEM_PTP = "PTP"; // PTP系统
	
	/**
	 * 新增合伙人应收单service code
	 */
	public static final String FOSS_ESB2FOSS_ADD_RECEIVABLEBILL = "FOSS_ESB2FOSS_ADD_RECEIVABLEBILL";
	
	/**
	 * 新增合伙人应付单service code
	 */
	public static final String FOSS_ESB2FOSS_ADD_PAYABLE_BILL = "FOSS_ESB2FOSS_ADD_PAYABLE_BILL";
	
	/**
	 * 更改合伙人应收单service code
	 */
	public static final String FOSS_ESB2FOSS_UPDATE_RECEIVABLE = "FOSS_ESB2FOSS_UPDATE_RECEIVABLE";
	
	/**
	 * 更改合伙人应付单service code
	 */
	public static final String FOSS_ESB2FOSS_UPDATE_PAYABLE_BILL = "FOSS_ESB2FOSS_UPDATE_PAYABLE_BILL";
}
