/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/define/PartiallineConstants.java
 * 
 *  FILE NAME     :PartiallineConstants.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.define;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

public class PartiallineConstants {

	/**
	 * 待审核
	 */
	public static final String PARTIALLINE_AUDITSTATUS_WAITINGAUDIT = "WAITINGAUDIT";
	public static final String PARTIALLINE_AUDITSTATUS_WAITINGAUDIT_DESC = "待审核";
	/**
	 * 已审核
	 */
	public static final String PARTIALLINE_AUDITSTATUS_HASAUDITED = "HASAUDITED";
	public static final String PARTIALLINE_AUDITSTATUS_HASAUDITED_DESC = "已审核";
	/**
	 * 反审核
	 */
	public static final String PARTIALLINE_AUDITSTATUS_BACKAUDIT = "BACKAUDIT";
	public static final String PARTIALLINE_AUDITSTATUS_BACKAUDIT_DESC = "反审核";
	/**
	 * 作废
	 */
	public static final String PARTIALLINE_AUDITSTATUS_INVALID = "INVALID";
	public static final String PARTIALLINE_AUDITSTATUS_INVALID_DESC = "作废";
	/**
	 * 未知
	 */
	public static final String PARTIALLINE_AUDITSTATUS_UNKNOWN = "UNKNOWN";
	/**
	 * 全部
	 */
	public static final String PARTIALLINE_AUDITSTATUS_ALL = "ALL";
	/**
	 * 269701--lln
	 * 德邦家装送装明细及签收确认
	 * UNCOMMITTED--未操作
	 */
	public static final String PARTIALLINE_AUDITSTATUS_UNCOMMITTED = "UNCOMMITTED";
	public static final String PARTIALLINE_AUDITSTATUS_UNCOMMITTED_DESC = "未操作";
	/**
	 * 269701--lln
	 * 德邦家装送装明细及签收确认
	 * PASS--同意
	 */
	public static final String PARTIALLINE_AUDITSTATUS_PASS = "PASS";
	public static final String PARTIALLINE_AUDITSTATUS_PASS_DESC = "同意";
	/**
	 * 269701--lln
	 * 德邦家装送装明细及签收确认
	 * NOTPASS--不同意
	 */
	public static final String PARTIALLINE_AUDITSTATUS_NOTPASS = "NOTPASS";
	public static final String PARTIALLINE_AUDITSTATUS_NOTPASS_DESC = "不同意";
	/**
	 * 提货方式-自提
	 */
	public static final String RECEIVE_METHOD_SELF_PICKUP = "SELF_PICKUP";
	/**
	 * 提货方式-非自提
	 */
	public static final String RECEIVE_METHOD_NOT_SELF_PICKUP = "NOT_SELF_PICKUP";
	/**
	 * 异常-运单已被录入
	 */
	public static final String EXCEPTION_WAYBILL_HAS_INPUTED = "foss.partialline.exception.waybillHasInputed";
	/**
	 * 异常-外发单号已被录入
	 */
	public static final String EXCEPTION_EXTERNALBILLNO_HAS_USED = "foss.partialline.exception.externalbillnoUsed";
	/**
	 * 269701--lln
	 * 异常-流水号已被录入
	 */
	public static final String EXCEPTION_SERIALNO_HAS_INPUTED = "foss.partialline.exception.serialNoHasInputed";
	/**
	 * 异常-未被交接运单号
	 */
	public static final String EXCEPTION_WAYBILL_HAS_NOT_HANDED = "foss.partialline.exception.notParticllineWaybillNo";
	/**
	 * 异常-中转外发单对应的运单号已经被重新录入，不能更改此状态
	 */
	public static final String EXCEPTION_WAYBILL_TRANSFER_EXTERNAL_INPUTED_AGAIN = "foss.partialline.exception.transferExternalInputedAgain";
	/**
	 * 拷贝数据不合法
	 */
	public static final String EXCEPTION_WAYBILL_TRANSFER_EXTERNAL_ILLEGALACCESSEXCEPTION = "foss.partialline.exception.IllegalAccessException";
	/**
	 * 调用异常
	 */
	public static final String EXCEPTION_WAYBILL_TRANSFER_EXTERNAL_INVOCATIONTARGETEXCEPTION = "foss.partialline.exception.InvocationTargetException";
	/**
	 * 是否需要验证
	 */
	public static final String VALIDATE_YES = "yes";
	/**
	 * 结算系统不可用
	 */
	public static final String STL_SYS_UNUSEABLE = "foss.partialline.exception.stlUnuseable";
	/**
	 * 交接类型-偏线交接单
	 */
	public static final String HANDOVER_TYPE_PARTIALLINE_HANDOVER = "PARTIALLINE_HANDOVER";
	/**
	 * 是否中转外发-是
	 */
	public static final String IS_TRANSFER_EXTERNAL_YES = "Y";
	/**
	 * 是否中转外发-是
	 */
	public static final String IS_TRANSFER_EXTERNAL_YES_DESC = "是";
	/**
	 * 是否中转外发-否
	 */
	public static final String IS_TRANSFER_EXTERNAL_NO = "N";
	/**
	 * 是否中转外发-否
	 */
	public static final String IS_TRANSFER_EXTERNAL_NO_DESC = "否";
	/**
	 * 服务返回值
	 */
	public static final int SERVICE_RESULT_INT_VALUE = 0;
	/**
	 * 开单金额默认倍数,
	 */
	public static final Long MULTIPLER = 1L;
	/**
	 * 运单有效状态
	 */
	public static final String WAY_BILL_ACTIVE_CHAR_VALUE = "Y";
	/**
	 * 自动核销-是
	 */
	public static final String IS_WRITE_OFF_YES = "Y";
	/**
	 * 自动核销-否
	 */
	public static final String IS_WRITE_OFF_NO = "N";
	/**
	 * 新增操作
	 */
	public static final String PARTIALLINE_ACTION_INSERT = "INSERT";
	/**
	 * 运单可用
	 */
	public static final String WAY_BILL_ACTIVE_Y = "Y";

	/**
	 * 接送货接口查询运单信息出错
	 */
	public static final String WAY_BILL_EXCEPTION = "接送货接口查询运单信息出错";
	/**
	 * 偏线外发单
	 */
	public static final String EXCEPTION_WAYBILL_SHEET_NAME = "偏线外发单";
	/**
	 * 偏线外发单业务锁
	 */
	public static final String TFR_PARTITIALLINE_LOCK_DESC = "偏线外发单业务锁";
	/**
	 * 付款方式
	 */
	public static final String SETTLEMENT__PAYMENT_TYPE = "SETTLEMENT__PAYMENT_TYPE";
	/**
	 * 结果为1
	 */
	public static final Long LIST_SIZE_ONE = 1L;
	/**
	 * 等于
	 */
	public static final int EQUAL = 0;
	/**
	 * 小于
	 */
	public static final int LESS = -1;
	/**
	 * 大于
	 */
	public static final int GREATER = 1;
	/**
	 * 导出列头
	 */
	public static final String[] ROW_HEADS = { "运单号", "外发单号", "中转外发", "外发代理", "到付运费","外发代理费","代理送货费", "外发成本总额", "状态", "外发员", "录入时间",
			"币种" ,"其他费用","备注"};
	/**
	 * 表格每页行数
	 */
	public static final Integer EXCEL_DEFAULT_SHEET_SIZE = 5000;
	
	/**
	 * 落地配外发单业务锁
	 */
	public static final String TFR_LDP_LOCK_DESC = "快递代理外发单业务锁";
	/**
	 * 落地配外发单发送状态  N 否
	 */
	public static final String IS_HASSEND_NO = "N";
	/**
	 * 交接类型-偏线交接单
	 */
	public static final String HANDOVER_TYPE_LDP_HANDOVER = "LDP_HANDOVER";
	/**
	 * 运单提货方式
	 */
	public static final String WAYBILL_PICKUP_TYPE = "PICKUPGOODSHIGHWAYS";
	/**
	 * 运单返单类型
	 */
	public static final String WAYBILL_RETURNBILL_TYPE = "RETURNBILLTYPE";
	/**
	 * 外发单审核状态编码-名称MAP
	 */
	public static Map<String,String> auditStatusMap=null;
	public static Map<String,String> getAuditStatusMap(){
		if(null==auditStatusMap){
			auditStatusMap=new HashMap<String,String>();
			auditStatusMap.put("WAITINGAUDIT", "待审核");
			auditStatusMap.put("HASAUDITED", "已审核");
			auditStatusMap.put("BACKAUDIT", "反审核");
			auditStatusMap.put("INVALID", "作废");
		}
		return auditStatusMap;
	}
	
	/**
	 * 定义运输性质编码-名称MAP
	 */
	public static Map<String,String> productCodeModelMap=null;
	
	public static Map<String,String> getProductCodeModelMap(){
		if(null==productCodeModelMap){
			productCodeModelMap=new HashMap<String,String>();
			productCodeModelMap.put("FLF", "精准卡航");
			productCodeModelMap.put("FSF", "精准城运");
			productCodeModelMap.put("LRF", "精准汽运(长途)");
			productCodeModelMap.put("SRF", "精准汽运(短途)");
			productCodeModelMap.put("PLF", "汽运偏线");
			productCodeModelMap.put("WVH", "整车");
			productCodeModelMap.put("AF", "精准空运");
		}
		return productCodeModelMap;
	}
	
	/**
	 * 定义落地配轨迹操作类型编码-名称MAP
	 */
	/**1*/
	public static String OPERATE_TYPE_LDP_TRACK_ARRIVE = "1";
	/**2*/
	public static String OPERATE_TYPE_LDP_TRACK_LEAVE = "2";
	/**3*/
	public static String OPERATE_TYPE_LDP_TRACK_DELIVER = "3";
	/**4*/
	public static String OPERATE_TYPE_LDP_TRACK_DELIVER_FAIL = "4";
	/**5*/
	public static String OPERATE_TYPE_LDP_TRACK_RETURN = "5";
	/**99*/
	public static String OPERATE_TYPE_LDP_TRACK_DEFUSE_SIGN = "99";
	/**88*/
	public static String OPERATE_TYPE_LDP_TRACK_DELIVERY = "8";
	
	public static String OPERATE_TYPE_INTER_CUSTOMS_ARRIVAL = "10";
	
	public static String OPERATE_TYPE_INTER_TRANSFER_ARRIVAL = "11";
	
	public static String OPERATE_TYPE_INTER_DEST_ARRIVAL = "12";
	
	public static String OPERATE_TYPE_INTER_DELIVER = "13";
	
	public static String OPERATE_TYPE_INTER_SIGN = "14";
	
	public static String OPERATE_TYPE_INTER_EXCEPTION_DELIVER = "20";
	
	public static String OPERATE_TYPE_INTER_RETURN = "21";
	
	public static String OPERATE_TYPE_INTER_ADDRESS_CHANGE = "22";
	
	public static String OPERATE_TYPE_INTER_UNDELIVER = "23";
	
	public static String OPERATE_TYPE_INTER_ADDRESS_ERROR = "24";
	
	
	public static Map<String,String> operationTypeMap = new HashMap<String, String>();
	
	static {
		operationTypeMap.put("1", "到达网点");
		operationTypeMap.put("2", "派件出仓");
		operationTypeMap.put("3", "派件成功");
		operationTypeMap.put("4", "派件失败");
		operationTypeMap.put("5", "货物拉回德邦");
		operationTypeMap.put("99", "拒签");
		operationTypeMap.put("8", "送达");
	}
	
	/**
	 * 落地配外发单
	 */
	public static final String EXPORT_LDP_EXTERNALBILL_SHEET_NAME = "快递代理外发单";
	/**
	 * 落地配导出列头
	 */
	public static final String[] LDP_ROW_HEADS = { "运单号","流水号","代理单号", "快递代理公司","快递代理公司编码", "到付金额", "代收货款", "应收费用", "外发运费","返单类型", 
					"外发员", "外发员工号","外发部门名称","外发部门编码", "生成时间", "币种","付款方式","提货方式","收货联系人姓名","收货客户地址","收货客户(公司)",
					"收货联系人电话","货物声明价值","件数","重量","体积","备注","外发单状态","修改人","修改时间","收货地址" };
	/**
	 * 返回实体属性json格式数据给落地配公司
	 */
	public static final String[] LDP_JSON_RESULT_HEADS = {"waybillNo","payType","externalUserName","externalUserCode","modifyTime",
			"externalOrgName","externalOrgCode","agentCompanyName","agentCompanyCode","pickupType","returnType","auditStatus",
			"receiveName","receiveAddr","receiveCompany","receivePhone","freightFee","codAmount","payDpFee","paymentCollectionFee","insuranceFee",
			"declarationValue","goodsNum","weight","volume","notes","createdTime","message","detailedInfo"};
	
	/**
	 * 打印代理面单类别定义
	 */
	public static final String AGENT_WAYBILL_STO = "STO";
	public static final String AGENT_WAYBILL_YTO = "YTO";
	public static final String AGENT_WAYBILL_ZTO = "ZTO";
	public static final String AGENT_WAYBILL_YUNDA = "YUNDA";
	public static final String AGENT_WAYBILL_TTK = "TTK";
	public static final String AGENT_WAYBILL_UC = "UC";
	public static final String AGENT_WAYBILL_SF = "SF";
	public static final String AGENT_WAYBILL_BEST = "BEST";
	public static final String AGENT_WAYBILL_EMS = "EMS";
	public static final String AGENT_WAYBILL_ZJS = "ZJS";
	public static final String AGENT_WAYBILL_QF = "QF";
	public static final String AGENT_WAYBILL_GTO = "GTO";
	 
	public static Map<String,String> agentWaybillMap = new HashMap<String,String>();
	static {
	agentWaybillMap.put("STO", "申通快递");
	agentWaybillMap.put("YTO", "圆通快递");
	agentWaybillMap.put("ZTO", "中通快递");
	agentWaybillMap.put("YUNDA", "韵达快递");
	agentWaybillMap.put("TTK", "天天快递");
	agentWaybillMap.put("UC", "优速快递");
	agentWaybillMap.put("SF", "顺丰速运");
	agentWaybillMap.put("BEST", "百世汇通");
	agentWaybillMap.put("EMS", "EMS");
	agentWaybillMap.put("ZJS", "宅急送");
	agentWaybillMap.put("QF", "全峰快递");
	agentWaybillMap.put("GTO", "国通快递");
	//modified by 257220 新增代理公司
	agentWaybillMap.put("UNITOP", "全一快递");
	agentWaybillMap.put("ANYTIME", "全日通快递");
	agentWaybillMap.put("QC", "全晨快递");
	agentWaybillMap.put("SUER", "速尔快递");
	agentWaybillMap.put("HMJ", "黄马甲快递");
	agentWaybillMap.put("RUFENGDA", "如风达快递");
	agentWaybillMap.put("YUTONG", "运通快递");
	}

	/**
	 * 打印代理面单的保价金额统一为0
	 */
	public static final int INSURANCE_VALUE = 0;
/**
	 * .XLS
	 */
	public static final String EXCEL_FILE_TAIL_XLS_UP = ".XLS";
	/**
	 * .xls
	 */
	public static final String EXCEL_FILE_TAIL_XLS_DOWN = ".xls";
	/**
	 * .xlsx
	 */
	public static final String EXCEL_FILE_TAIL_XLSX_DOWN = ".xlsx";
	/**
	 * .XLSX
	 */
	public static final String EXCEL_FILE_TAIL_XLSX_UP = ".XLSX";
	
	/**
	 * 第一
	 */
	public static final Integer EXCEL_DEFAULT_SHEET_ONE = 0;
	
	
	/**
	 * 第一列
	 */
	public static final Integer EXCEL_COLUMN_FIRST = 0;
	/**
	 * 第二列
	 */
	public static final Integer EXCEL_COLUMN_SECOND = 1;
	/**
	 * 第三列
	 */
	public static final Integer EXCEL_COLUMN_THREE = 2;
	
	/**
	 * 第四列
	 */
	public static final Integer EXCEL_COLUMN_FOUR = 3;
	/**
	 * 第五列
	 */
	public static final int EXCEL_COLUMN_FIVE = 4;
	/**
	 * 导入excel最大列数
	 */
	public static final Integer EXCEL_COLUMN_MAX_SIZE = 3;
	/**
	 * 导入重量体积最大列数
	 */
	public static final Integer EXCEL_COLUMN_WV_MAX_SIZE = 5;
	/**
	 * 导入一票多件重量体积最大列数
	 */
	public static final Integer EXCEL_COLUMN_WVM_MAX_SIZE = 4;
	/**
	 * NAN
	 */
	public static final String NAN = "NaN";
	
	//快递一百订阅状态
	/**
	 * 订阅状态-未订阅
	 */
	public static final String ISPUSH_NO  = "N";
	
	/**
	 * 订阅状态-已订阅
	 */
	public static final String ISPUSH_YES  = "Y";
	
	/**
	 * 订阅状态-已退订
	 */
	public static final String ISPUSH_BACK  = "B";
	
	/*
	 * 接收快递100推送消息原因编码
	 * 1.代理单号是否存在有效的绑定关系或者对应单号是否存在
	 * 2.存在敏感词汇
	 */
	/**代理单号是否存在有效的绑定关系**/
	public static final String IS_EXIST_VALID_BIND="555";
	
	/**对应单号是否存在**/
	public static final String WAYBILL_IS_VALID="666";
	
	/**代理单号是否存在有效的绑定关系*/
	public static final String IS_EXIST_VALID_BIND_REASON="代理单号不存在有效的绑定关系";
	
	/**对应单号是否存在原因***/
	public static final String WAYBILL_IS_VALID_REASON="对应运单无效";
	
	/**存在敏感词汇**/
	public static final String EXIST_SENSITIVE_WORD="777";
	
	/**快递代理外发签收信息包含敏感信息原因**/
	public static final String EXIST_SENSITIVE_WORD_EXPRESS_REASON="快递代理外发签收信息包含敏感信息";
	
	/**营业部外发外发签收信息包含敏感信息原因**/
	public static final String EXIST_SENSITIVE_WORD_SALES_REASON="营业部外发外发签收信息包含敏感信息";
	/** 到付手续费 **/
	public static final String TO_PAY_FEE = "FEE";
	
	/** 应收费用 **/
	public static final String PRICING_CODE_YS = "YS";
	
	/**311396 map轨迹枚举TrackingEventTypeEnum中的所有常量*/
	public static Map<String, TrackingEventTypeEnum> enumMap = new HashMap<String, TrackingEventTypeEnum>();
	static{
		enumMap = EnumUtils.getEnumMap(TrackingEventTypeEnum.class);
	}
	public final static String TRACK_PREFIX = "TRACKING_";
	public final static String TARGET_TYPE_FOSS = "FOSS";
	public final static String TARGET_TYPE_WQS = "WQS";
	}