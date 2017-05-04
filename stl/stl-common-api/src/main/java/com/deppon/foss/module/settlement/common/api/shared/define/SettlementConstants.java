package com.deppon.foss.module.settlement.common.api.shared.define;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.util.DateUtils;

/**
 * 
 * 结算公用常量类
 * 
 * @author dp-wujiangtao
 * @date 2012-10-15 上午9:27:03
 * @since
 * @version
 */
public class SettlementConstants {
	
	/**
	 * add by 353654
	 */
	public static final String TYPE_FOSS = "FOSS";
	public static final String TYPE_CUBC = "CUBC";
	public static final String TYPE_ALL = "ALL";
	public static final String OPERATION_TYPE_INSERT = "insert";
	public static final String OPERATION_TYPE_QUERY = "query";
	public static final String OPERATION_TYPE_DELETE = "delete";
	public static final String OPERATION_TYPE_UPDATE = "update";
	
	/**
	 * 结算客户最大临时欠款天数
	 */
	public static final int CUSTOMER_MAX_DEBIT_DAYS =30;
	/**
	 * 结算所有按单号查询时，单号输入最大个数
	 */
	public static final int BILL_NOS_MAX = 10;
		
	/**
	 * 结算单据最大金额
	 */
	public static final BigDecimal BILL_MAX_AMOUNT = new BigDecimal("1E10");
	
	/**
	 * 返回成功结果1
	 */
	public static final int RETURN_SUCESS=1;
	
	/**
	 * 返回失败结果 0 
	 */
	public static final int RETURN_FAILURE = 0;
	
	/**
	 * 英文逗号
	 */
	public static final String ENGLISH_COMMA=",";
	
	/**
	 * 结算单据前缀
	 */
	public static final String BILL_PREFIX_YS = "YS"; // 应收单

	public static final String BILL_PREFIX_YF = "YF"; // 应付单

	public static final String BILL_PREFIX_US = "US"; // 预收单

	public static final String BILL_PREFIX_UF = "UF"; // 预付单

	public static final String BILL_PREFIX_FK = "FK"; // 付款单
	
	public static final String BILL_PREFIX_DP = "DP"; //合伙人 预收单
	
	public static final String BILL_PREFIX_ZC = "ZC";//VTS打印运输合同，来源单号前缀ZC
	
	

	/**
	 * 页面按单号查询时，用于存放来源单号的集合，为了和应收单、应付、预收、预付单据集合命名保持一致
	 * 用途见：SettlementUtil.convertBillNos(String[] billNos)
	 */
	public static final String BILL_PREFIX_LY = "LY"; // 来源单据

	/**
	 * 默认单号
	 */
	public static final String DEFAULT_BILL_NO = "N/A";

	public static final int DATE_LIMIT_DAYS_MONTH = 31;// 日期相差最大天数一月31天
	
	public static final int DATE_LIMIT_DAYS_MAX_MONTH=90;//日期相差最大天数不能超过90天

	public static final int DATE_LIMIT_DAYS_WEEK = 7;// 日期相差最大天数一周 7天
	
	public static final int DATE_THREE_DAYS_WEEK = 3;//日期相差条数3天
	
	public static final int DATE_ELEVEN_DAYS_WEEK = 11; //日期相差天数11天
	
	public static final int DATE_LIMIT_SEX_MONTH = DATE_LIMIT_DAYS_MONTH*6;// 网厅查询应收单日期最大间隔

	/***
	 * 区分页面时间类型
	 */
	public static final String TAB_DATE_TYPE_FOR_BUSINESS = "BU";// 业务日期

	public static final String TAB_DATE_TYPE_FOR_ACCOUNT = "AC";// 记账日期

	public static final String TAB_DATE_TYPE_FOR_CONFIRM = "CO";// 确认收银日期

	/**
	 * tab 页查询常量设置
	 */
	public static final String TAB_QUERY_BY_DATE = "TD";// 按日期
	
	public static final String TAB_QUERY_BY_ACCOUNT_DATE = "TAD";// 按记账日期

	public static final String TAB_QUERY_BY_BILL_NO = "TB";// 按明细单号

	public static final String TAB_QUERY_BY_DZ_BILL_NO = "TDZ";// 按对账单号

	public static final String TAB_QUERY_BY_WAYBILL_NO = "WB";// 按运单号

	public static final String TAB_QUERY_BY_REPAYMENT_NO = "RT";// 按还款单号查询

	public static final String TAB_QUERY_BY_OTHERREVENUE_NO = "OR";// 小票单号
	
	public static final String TAB_QUERY_BY_DEPOIST_RECEIVED_NO = "DERE";// 按预收单号
	
	public static final String TAB_QUERY_BY_REC_PAY_NO="RP";//按应收单应付单号查询
	
	public static final String TAB_QUERY_BY_PAYABLE_NO = "TP"; //按应付单号
	
	public static final String TAB_QUERY_BY_RECEIVABLE_NO = "TR"; //按应收单号
	
	public static final String TAB_QUERY_BY_SOURCE_BILL_NO = "TSB"; //按来源单号
	
	public static final String TAB_QUERY_BY_DR_CC_RP_NO = "DCP"; //按预收单、现金收款单、还款单号
	
	public static final String TAB_QUERY_BY_AIR_WAYBILL_NO = "AWB";//航空正单号
	
	public static final String TAB_QUERY_BY_LANDSTOWAGE_NO= "BB";//银联交易流水号
	
	public static final String TAB_QUERY_BY_FAILING_INVOICE= "FI";//未开发票
	
	public static final String TAB_QUERY_BY_RENTCAR_NO = "RCB";//租车编号查询
	
	public static final String TAB_QUERY_BY_BUSINESS_NO = "BN";//按业务单号查询
	
	public static final String TAB_QUERY_BY_WORKFLOW_NO = "WO";//按业务单号查询
	
	
	/**
	 * RADIO 查询条件
	 */
	public static final String  RADIO_QUERY_BY_USE_TIME = "UT";//按用车日期查询
	
	public static final String  RADIO_QUERY_BY_RENT_TIME = "RT";//按租车日期查询
	
	public static final String  RADIO_QUERY_BY_CREATE_TIME = "CT";//按单据生成日期

	/**
	 * 签收类型
	 */
	public static final String LINE_SIGN = "LS";// 专线签收

	public static final String AIR_SIGN = "AS";// 空运签收

	public static final String PARTIAL_LINE_SIGN = "PLS";// 偏线签收
	
	public static final String LAND_STOWAGE_SIGN = "LDS";//快递代理签收
			
	/**
	 * 外部网点类型 KY:空运代理网点 PX：偏线代理网点 LD：快递代理网点
	 */
	public static final String EXTERNAL_NODE_TYPE_KY = "KY"; // 空运代理网点
	
	public static final String EXTERNAL_NODE_TYPE_PX = "PX"; // 偏线代理网点
	
	public static final String EXTERNAL_NODE_TYPE_LDP = "LD"; // 快递代理网点
	
	/**
	 * 最大查询集合数
	 */
	public static final int MAX_LIST_SIZE = 1000;

	/**
	 * 核销操作
	 */
	public static final String WRITE_OFF = "WO";

	/**
	 * 反核销操作
	 */
	public static final String BACK_WRITE_OFF = "BWO";

	/**
	 * 红冲操作
	 */
	public static final String RED_BACK = "RB";

	/**
	 * 单号最大查询条数
	 */
	public static final int MAX_BILL_NO_SIZE = 1000;

	/**
	 * 蓝单生成新的单据号
	 */
	public static final boolean BLUE_NEW_BILL_NO = true;
	
	/****************************应付单*****************************/
	/**
	 * 应付单的核销状态
	 */
	public static final String BILL_PAYABLE__WRITEOFF_STATUS__NOT_WRITEOFF = "NW";//未核销
	public static final String BILL_PAYABLE__WRITEOFF_STATUS__PART_WRITEOFF= "PW";//部分核销
	public static final String BILL_PAYABLE__WRITEOFF_STATUS__WRITEOFF_DONE = "WD";//已核销
	
	public static final int PAY_LIMIT_MAX = 100;//最大付款条数
	
	public static final BigDecimal SERVICE_FEE_PAY_CASH__MAX = new BigDecimal("1000");//单笔装卸费现金付款最大值
	
	/**
	 * 网上营业厅查询应收单的查询方式
	 * 查询方式：1-客户编码，精确的查询；2-运单号+手机号码，精确的查询，其中手机号码是收货人的或者发货人；
	 * 3-(收货人+货物)+日期+付款方式+客户编码，其中收货人和货物可以是模糊的查询; 4-运单号+客户编码，精确的查询
	 */
	public static final String BHO_QUERY_TYPE_BY_CUSTOMER="1";
	public static final String BHO_QUERY_TYPE_BY_WAYBILL="2";
	public static final String BHO_QUERY_TYPE_BY_DATE="3";
	public static final String BHO_QUERY_TYPE_BY_WAYBILL_CUSTOMER="4";
	
	/**
	 * 网厅按运单号和手机号码查询时允许最大个数
	 */
	public static final int BHO_MAX_LIST_SIZE = 10;
		
	/**
	 * 网上营业厅编码和名称
	 */
	public static final String BHO_CODE="WT";
	public static final String BHO_NAME="网上营业厅";
	
	/**
	 * @author 218392 张永雪
	 * @date 2016-02-19 12:06:30
	 * 裹裹APP编码和名称
	 */
	public static final String WRAP_CODE="GG";
	public static final String WRAP_NAME="裹裹";
	
	
	/**
	 * 费控
	 */
	public static final String COSTCONTROL_CODE = "FK";
	public static final String COSTCONTROL_NAME = "费控";
	public static final String COST_CONTROL__WORK_FLOW_TYPE__ADVANCED_AUDTI_RESULT="费控返回，审批预付款申请结果";
	
	/**
	 * 银企默认用户编码和名称
	 */
	public static final String BANK_CODE = "YQ";
	public static final String BANK_NAME = "银企";
	
	/**
	 * CRM默认用户编码和名称
	 */
	public static final String CRM_CODE = "CRM";
	public static final String CRM_NAME = "CRM";
	
	/**
	 * 财务自助默认用户编码和名称
	 */
	public static final String FINANCE_CODE = "FINANCE";
	public static final String FINANCE_NAME = "财务自助";
	
	/**
	 * 系统操作默认用户编码和名称
	 */
	public static final String SYSTEM_USER_CODE = "SYSTEM";
	public static final String SYSTEM_USER_NAME = "系统";
	
	/**
	 * 外围系统接口开关 (说明：开发阶段暂时关闭对外部系统的调用，集成测试后开发以下6个开关)
	 */
	public static final boolean EXTEND_SYSTEM_CRM_SWITCH = true;
	public static final boolean EXTEND_SYSTEM_OA_SWITCH = true;
	public static final boolean EXTEND_SYSTEM_FINANCE_SWITCH = true;
	public static final boolean EXTEND_SYSTEM_BHO_SWITCH = true;
	public static final boolean EXTEND_SYSTEM_COSTCONTROL_SWITCH = true;
	public static final boolean EXTEND_SYSTEM_BANK_SWITCH = true;

	/**
	 * 导出Excel的起始编号，默认为0
	 */
	public static final int EXPORT_EXCEL_START_NUMBER=0;
	
	
	/**
	 * 导出Excel的最大条数
	 */
	public static final int EXPORT_EXCEL_MAX_COUNTS=100000;
	
	/**
	 * 操作配载单方法类型
	 */
	public static final String  TRUCK_STOWAGE_OPER_TYPE_INSERT="ADD";//新增
	
	public static final String  TRUCK_STOWAGE_OPER_TYPE_UPDATE="UPDATE";//修改
	
	/**
	 * 实收货款
	 */
	public static final String  CONFIRM_PAYMENT_SUCCESS="CPSU";//实收货款成功
	
	public static final String  CONFIRM_PAYMENT_SUCCESS_NOT_SETTLE="CPSNS";//实收货款成功，未货款结清
	
	public static final String  CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE="CPSAPS";//实收货款成功，并且运单对应应收单已货款结清（应收单已核销或到付临欠转月结）
	
	
	/**
	 * JOB常量
	 */
	public static final int JOB_SELECT_SIZE = 10000; // 任务查询批次
	
	public static final int JOB_PROCESS_SIZE = 1000; // 任务处理批次
	
	public static final int JOB_MINUTES_INTERVAL = -1; // 任务处理时间差
	
	/**
	 * 预收会计、收银员常量
	 */
	public static final String DEPOSIT_RECEIVED_ACCOUNTING = "DEA";
	
	public static final String DEPOSIT_RECEIVED_CASHIER = "DEC";
	
	/*
	 * 退预收类型
	 */
	//声明退预收时方法最后一个参数，付款界面也调用该接口继续校验，但是不用封装对象
	public static final String OPERATETYPE_DEPOSITRECEIVED = "DEPOSIT";
	public static final String OPERATETYPE_PAYMENT = "PAYMENT";
	
	//还款来源界面类型
	public static final String REPAYMENT_BY_STATEMENTENTRY  = "statementEntry";// 来源自对账单明细界面
	public static final String REPAYMENT_BY_STATEMENTQUERY  = "statementQuery";// 来源自查询对账单界面
	
	/**
	 * 坏账查询类型：1（按客户查询），2（按运单号查询）
	 */
	public static final String BAD_ACCOUNT__QUERY_TYPE__CUSTOMER = "CUSTOMER";
	public static final String BAD_ACCOUNT__QUERY_TYPE__WAYBILL = "WAYBILLNUM";


	
	/**
	 *导出时，最大行数
	 */
	public static final int EXPORT_MAX_COUNT=65535;
	
	
	/**
	 * GBK编码
	 */
	public static final String UNICODE_GBK="GBK";

	/**
	 *ISO8859-1编码
	 */
	public static final String UNICODE_ISO="ISO8859_1";
	
	
	/**
	 * UTF-8编码
	 */
	public static final String UNICODE_UTF="UTF-8";
	
	/**
	 * 规定审核和反审核操作的单据类型
	 */
	public static final String[] AUDIT_OR_UNAUDIT_TYPES = {
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR, // 航空公司运费
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL, // 空运出发代理应付
		SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY // 空运到达代理应付
	};
	
	/**
	 * 是否需要验证运单是否存在参数
	 */
	public static final boolean EXTEND_SYSTEM_WAYBILL_IS_EXISTST=true;
	
	/**
	 * 验证是否存在相同的财务单据
	 */
	public static final boolean EXTEND_VALIDATE_SETTLEMENT_BILL_IS_EXISTS=true;
	
	
/**********************************************现金收入明细报表**************************************************/	
	/**
	 * 导出Excel 使用
	 */
    public static final String EXCEL_SHEET_NAME="sheet";//导出Excel  sheet
	
	/**
	 * 日期之间的合并符号
	 */
    public static final String EXCEL_DATE_TIME_MERGE_OPERATOR="~";//日期之间的合并符号
   
    /**
     * 现金收入明细报表
     */
    public static final String CASH_INCOM_STATEMENTS_EXCEL_NAME="-实时收入明细报表";//现金实时收入明细报表
    
    /**
     * 收款部门
     */
    public static final String CASH_COLLECTION_ORG_NAME="收款部门";//收款部门
    
    /**
     * 收入部门
     */
    public static final String GENERATION_ORG_NAME="收入部门";//收入部门
    
    /**
     * 中文冒号
     */
    public static final String CHINESE_COLON="：";//中文冒号
    
    /**
     * 日期拼接符号2
     */
    public static final String PRINT_DATE_TIME_MERGE_OPERATOR=" - ";//日期拼接符号2
    
    /**
     * 记账日期
     */
    public static final String ACCOUNT_DATE_DESCRIPTION_NAME="记账日期";//记账日期
    
    /**
     * 收银确认日期
     */
    public static final String CASH_CONFIRM_TIME_DESCRIPTION_NAME="收银日期";//收银确认日期

    
    
    /**
	 * 运单号最小长度
	 */
	public static final int WAYBILL_RULE_MIN_LENGTH = 8;
	
	/**
	 * 运单号最大长度
	 */
	public static final int WAYBILL_RULE_MAX_LENGTH = 10;
	
	/**
	 * 小票单号最小长度
	 */
	public static final int OTHERREVENUE_RULE_MIN_LENGTH = 8;
	
	/**
	 * 小票单号最大长度
	 */
	public static final int OTHERREVENUE_RULE_MAX_LENGTH = 9;
	/**
	 * 运单号最大长度14位
	 */
	public static final int WAYBILL_RULE_MAX_LENGTH14 = 14;
		
	/**
	 * 结算单据类型
	 */
	public static final String SETTLEMENT_BILL_TYPE_RECEIVABLE="RE"; //应收单
	
	public static final String SETTLEMENT_BILL_TYPE_PAYABLE="PAY";//应付单
	
	public static final String SETTLEMENT_BILL_TYPE_DEPOSIT_RECEIVED="DR";//预收单
	
	public static final String SETTLEMENT_BILL_TYPE_ADVANCED_PAYMENT="AP";//预付单
	
	/**
	 * 财务收支平衡消息表相差天数
	 */
	public static final int QUERY_CREDIT_DIFF_DAY=2;
	
	/**
	 * 即日退批次号类型:01
	 */
	public static final String COD_TYPE__BATCHNO_R1 = "01";
	
	/**
	 * 代收货款特殊合并类型-即日退
	 */
	public static final String COD_TYPE__BATCHNO_R1_NAME = "即日退"; // 即日退

	/**
	 * 三日退（审核退）批次号类型:02
	 */
	public static final String COD_TYPE__BATCHNO_R3_RA = "02";
	
	/**
	 * 代收货款特殊合并类型-R3,RA
	 */
	public static final String COD__COD_TYPE__RETURN_3_A_DAY_CODE = SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY 
																   + SettlementConstants.ENGLISH_COMMA
																   + SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE; // R3,RA
	
	/**
	 * @author 218392 张永雪  2015-08-06 10:27:19 打包退(即日)
	 */
	public static final String COD__COD_TYPE__RETURN_PACK_1_DAY = SettlementDictionaryConstants.COD_COD_TYPE_RETURN_PACK
																   + SettlementConstants.ENGLISH_COMMA
																   + SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY;
	/**
	 * @author 218392 张永雪 2015-08-06 10:31:20 打包退(三日)=打包退+三日退+审核退
	 */
	public static final String COD__COD_TYPE__RETURN_PACK_3_A_DAY_CODE = SettlementDictionaryConstants.COD_COD_TYPE_RETURN_PACK
																		+ SettlementConstants.ENGLISH_COMMA
																		+SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY 
																		+ SettlementConstants.ENGLISH_COMMA
																		+ SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE;
	
	/**
	 * 代收货款特殊合并类型-R3RA
	 */
	public static final String COD__COD_TYPE__RETURN_R3RA_DAY_CODE = SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY 
																   + SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE; // R3RA
	
	/**
	 * 代收货款类型-即日退
	 */
	public static final String COD__COD_TYPE__RETURN_1_DAY_NAME = "即日退"; // 即日退
	
	/**
	 * 代收货款特殊合并类型-三日退（审核退）
	 */
	public static final String COD__COD_TYPE__RETURN_3_A_DAY_NAME = "三日退（审核退）"; // 三日退（审核退）
	
	/**
	 * 代收货款特殊合并状态-AG,RF,SF
	 */
	public static final String COD__STATUS__AG_RF_SF_CODE = SettlementDictionaryConstants.COD__STATUS__APPROVING
														   + SettlementConstants.ENGLISH_COMMA
														   + SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE
														   + SettlementConstants.ENGLISH_COMMA
														   + SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE; // AG,RF,SF
	/**
	 * 代收货款特殊合并状态-待审核或退款失败
	 */
	public static final String COD__STATUS__AG_RF_SF_NAME = "待审核或退款失败"; // 待审核或退款失败
	
	/**
	 * 是否需要验证偏线外发单对应的运单已经签收
	 */
	public static final boolean EXTEND_PARTIAL_LINE_VEHICLE_WAYBILL_IS_SING=true;
	
	/**
	 * 部门临欠额度，统计部门最近（3）个月份的收入金额
	 */
	public static final int ORG_INCOME_MONTH_COUNT_THREE=3;
	
	/**
	 * 上个月，当前月减去1即可
	 */
	public static final int ORG_INCOME_MONTH_COUNT_ONE=1;
	
	/**
	 * 月的第一天
	 */
	public static final String MONTH_FIRST_DATE="-01";
	
	/**
	 * 格式化到月
	 */
	public static final String FORMATS_MONTH="yyyy-MM";
	
	/**
	 * 属性名称：记账日期
	 */
	public static final String ACCOUNT_DATE="accountDate";
	
	/**
	 * 单据类型
	 */
	public static final String BILL_TYPE="billType";
	
	/**
	 * 业务锁互斥逻辑锁定时间设置,时间单位：秒
	 */
	public static final int BUSINESS_LOCK_BATCH = 60;//批量锁定时间
	public static final int BUSINESS_LOCK_SINGLE = 10;//单个锁定时间
	
	
	/**
	 * 	设置缴款报表默认开始日期
	 */
	public static final int DATE_LIMIT_DAYS = 30;// 日期相差最大天数一月31天
	
	public static final Date CASH_BEGIN_DATE=DateUtils.addDayToDate(new Date(),-DATE_LIMIT_DAYS);
	
	/**
	 * 押回单到达付款单标志
	 */
	public static final String RETURN_BACK_BALANCE = "RBK";
	
	/**
	 * 月结标志
	 */
	public static final String PAYABLE_TYPE__MONTH = "MH"; 
	
	/**
	 * 联系人类型标记 - 财务联系人
	 */
	public static final String CONTACT_TYPE__FINANCE = "1";
	
	/**
	 * 凭证报表配置数据权限使用的部门状态
	 */
	public static final String VOUCHER_DETAILS_DESTORG_CODE_STATUS = "DEST";
	
	public static final String VOUCHER_DETAILS_ORIGORG_CODE_STATUS = "ORIG";
 
	
	/**
	 * 制作对账单时，根据开始正单号和结束正单号查询的最大个数
	 */
	public static final int MAX_AIR_WAYBILL_NO_SIZE = 1000;
	
	/**
	 * 制作对账单时，输入正单号的前缀
	 */
	public static final String AIR_WAYBILL_NO_PREFIX = "DP";
	
	/**
	 * FOSS上线日期
	 */
	public static final Date FOSS_ONLINE_DATE = DateUtils.convert("2013-06-01", DateUtils.DATE_FORMAT);
	
	/**
	 * 到付清查、  出发--到达
	 */
	public static final String DEPTTYPE_FROM = "FROM";//出发
	public static final String DEPTTYPE_TO = "TO";//到达
	
	/**
	 * 应收单查询 收入和催款部门
	 */
	public static final String GENERATING_ORG_CODE = "GENERATING_ORG_CODE";//出发
	public static final String DUNNING_ORG_CODE = "DUNNING_ORG_CODE";//到达
	
	/**
	 * 小票业务类型
	 */
	public static final String BREAKBULK = "BB";//零担
	public static final String EXPRESS = "EP";//快递代理
	
	/**
	 * 代汇款月报表-收款类别
	 */
	public static final String SETTLEMENT__COLLECTION_TYPE__CASH = "CH";//现金
	public static final String SETTLEMENT__COLLECTION_TYPE__NOCASH = "NCH";//非现金
	
/**
	 * 
	 * 结清货款产生小票作废条件
	 */
	public static final String  CANCEL_OTHERREVENUE_CLASS= "WAYBILL";//现金
	public static final String CANCEL_OTHERREVENUE_DEPTCODE = "W0113010402";//非现金
	/**
	 * 包装其它应收应付录入类别
	 */
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGERATE="damageRate";//破损率奖罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__DAMAGECLAIM="damageClaim";//破损理赔处罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__FORKLIFTTICKET="forkliftTicket";//叉车票金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__AGING="aging";//时效奖罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__MIXING="mixing";//混打处罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__LOSING="losing";//丢货处罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__COMPLAINT="complaint";//投诉处罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__BATTENCHECK="battenCheck";//木条验收处罚金额
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__BOARD="board";//面板
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__WOODENSTAND="woodenStand";//木方
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__TRAY="tray";//托盘
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__PACKINGTRAY="packingTray";//包装托盘
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__RECOTHER="recOther";//应收其他
	public static final String PACKING_REC_AND_PAY_INPUT_TYPE__INCONSISTENTWAYBILL="inconsistentWaybill";//系统与实际差异单号

	/**
	 * 同步现金缴款接口调用时每批大小
	 */
	public static final int ESB_FOSS2FOSS_CASH_DATA_BATCH_SIZE = 50000;
	/**
	 * @author 310970
	 * 创建时间
	 * */
	public static final String CREATE_TIME="createTime";
	/**
	 * @author 325369  chenzhuang
	 * @date   2016-05-09 09:15:00
	 * 新增三级产品
	 */
	//三级产品  国际快递-标
	public static final String PRICING_PRODUCT_EXPRESS_INTERNATIONAL_BIAO = "ICES";
	//三级产品  国际快递-快
	public static final String PRICING_PRODUCT_EXPRESS_INTERNATIONAL_KUAI = "ICEC";
	//三级产品  快递报关通-标
	public static final String PRICING_PRODUCT_EXPRESS_CUSTOMS_CLEARANCE_BIAO = "GTSE";
	//三级产品  快递报关通-快
	public static final String PRICING_PRODUCT_EXPRESS_CUSTOMS_CLEARANCE_KUAI = new String("GTEC");
	/**
	 * @author 325369  chenzhuang  2016-05-03 08:31:20
	 * 保理公司名称
	 */
	public static final String FACTORING_COMPANY_NAME = "德易商业保理（深圳）有限公司";
	
	/**
	 * @author 357637  chenzhuang
	 * 删除交易流水号信息对接财务自助返回的结果
	 * 1代表成功.   
	 * 0代表FINS系统中没有此交易流水号的数据。
	 * 2代表FINS系统在进行删除操作时出现异常。
	 * 3代表FOSS系统传递过来的参数为空。
	 */
	public static final String DEL_SERIAL_STATUS_FROM_FINS_SUCCESS = "1";
	public static final String DEL_SERIAL_STATUS_FROM_FINS_BLANK = "0";
	public static final String DEL_SERIAL_STATUS_FROM_FINS_EXCEP = "2";
	public static final String DEL_SERIAL_STATUS_TO_FINS_NULL = "3";
	
	/**
	 * @author 357637  chenzhuang
	 * 交易流水号长度
	 */
	public static final int TRADESERIALNO_LENGTH = 12;
	/**
	 * POS刷卡数据冻结状态
	 * 0或空表示 未冻结
	 */
	public static final short POS_CARD_FROZEN_STATUS_0 = 0;
	/**
	 * POS刷卡数据冻结状态
	 * 1  表示全部冻结
	 */
	public static final short POS_CARD_FROZEN_STATUS_1 = 1;
	/**
	 * POS刷卡数据冻结状态
	 * 2 表示部分冻结
	 */
	public static final short POS_CARD_FROZEN_STATUS_2 = 2;
}
