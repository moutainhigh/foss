/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/define/NotifyCustomerConstants.java
 * 
 * FILE NAME        	: NotifyCustomerConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.define;

import java.math.BigDecimal;

/**
 * 
 * 客户通知模块常用常量
 * 
 * @author ibm_wangfei
 * @date Oct 22, 2012 11:48:32 AM
 */
public class NotifyCustomerConstants {

	/**
	 * 按库存查询
	 */
	public static final int SELECT_TYPE_STOCK = 0;
	/**
	 * 按交接单查询、预计到货时间
	 */
	public static final int SELECT_TYPE_HANDOVER = 1;
	/**
	 * 按车次查询
	 */
	public static final int SELECT_TYPE_VEHICLEASSEMBLENO = 2;

	public static final int SELECT_TYPE_STOCK_ERROR = 3;
	/**
	 * 按运单号查询
	 */
	public static final int SELECT_TYPE_WAYBILLNO = 4;
	public static final int EXPORT_NUMBER = 20000;

	/**
	 * add by 329757
	 * 按计划到达时间查询
	 */
	public static final int SELECT_TYPE_PLANARRIVE = 5;
	/**
	 * add by 329757
	 * 按实际到达时间查询
	 */
	public static final int SELECT_TYPE_ARRIVETIME = 6;
	/**
	 * add by 329757
	 * 按通知时间
	 */
	public static final int SELECT_TYPE_NOTICETIME = 7;
	/**
	 * 未通知_NONE_NOTICE；
	 */
	public static final String NONE_NOTICE = "NONE_NOTICE";
	/**
	 * 通知成功_SUCCESS；
	 */
	public static final String SUCCESS = "SUCCESS";
	/**
	 * 通知失败_FAILURE；
	 */
	public static final String FAILURE = "FAILURE";
	/**
	 * 语音通知中_VOICE_NOTICING
	 */
	public static final String VOICE_NOTICING = "VOICE_NOTICING";
	/**
	 * 短信通知中_SMS_NOTICING
	 */
	public static final String SMS_NOTICING = "SMS_NOTICING";
	/**
	 * 通知未果_NOTICING_UNSUCCESSFUL
	 */
	public static final String NOTICING_UNSUCCESSFUL = "NOTICING_UNSUCCESSFUL";

	/**
	 * 保管费计费最大天数
	 */
	public static final int MAX_WAREHOUSE_FREE_SAFE_DATA = 90;
	public static final String MAX_WAREHOUSE_FREE_SAFE_DATA_DESC = "MAX_WAREHOUSE_FREE_SAFE_DATA";

	/**
	 * 保管费收取标准
	 */
	public static final BigDecimal STORAGE_CHARGE_UNIT = BigDecimal.valueOf(10);
	public static final String STORAGE_CHARGE_UNIT_DESC = "STORAGE_CHARGE_UNIT";
	/**
	 * 最大保管费金额
	 */
	public static final BigDecimal STORAGE_CHARGE_MAX = BigDecimal
			.valueOf(1000);
	public static final String STORAGE_CHARGE_MAX_DESC = "STORAGE_CHARGE_MAX";
	/**
	 * 最小保管费金额
	 */
	public static final BigDecimal STORAGE_CHARGE_MIN = BigDecimal.valueOf(5);
	public static final String STORAGE_CHARGE_MIN_DESC = "STORAGE_CHARGE_MIN";

	/**
	 * 默认的免费库存天数
	 */
	public static final int WAREHOUSE_FREESAFE_DATA_NUM = 3;
	public static final String WAREHOUSE_FREESAFE_DATA_NUM_DESC = "WAREHOUSE_FREESAFE_DATA_NUM";

	/**
	 * 是否保管费初始化
	 */
	public static final String IS_STORAGE_CHARGE_INIT_DESC = "IS_STORAGE_CHARGE_INIT";

	/**
	 * 车辆到达后X分钟通知
	 */
	public static final String ARRIVE_NOTIFY_MINUTE = "ARRIVE_NOTIFY_MINUTE";
	/**
	 * 预计提前X小时提货
	 */
	public static final String PLAN_PICK_UP_GOODS_HOUR = "PLAN_PICK_UP_GOODS_HOUR";
	/**
	 * 超过X天，算仓储异常
	 */
	public static final int WAREHOUSE_TIMEOUT_DATA = 7;
	public static final String WAREHOUSE_TIMEOUT_DATA_DESC = "WAREHOUSE_TIMEOUT_DATA";
	/**
	 * 通知方式_语音
	 */
	public static final String NOTIFY_TYPE_VOICE_NOTICE = "VOICE_NOTICE";
	/**
	 * 通知方式_短信
	 */
	public static final String NOTIFY_TYPE_SMS_NOTICE = "SMS_NOTICE";
	/**
	 * 通知方式_电话
	 */
	public static final String NOTIFY_TYPE_TEL_NOTICE = "TEL_NOTICE";
	
	/**
	 * 派送方式_家装送装
	 */
	public static final String DELIVER_EQUIP = "EQUIP";
	/**
	 * 自动推送到货通知配置时间(分钟)
	 */
	public static final String JZ_ARRIVALGOODS_NOTIFY_MINUTE = "JZ_ARRIVALGOODS_NOTIFY_MINUTE";
	/********************************************************* 短信模版code **************************************************/
	/**
	 * 送货
	 */
	public static final String SMS_CODE_DELIVER = "DELIVER";
	/**
	 * 查询派送单-通知客户短信模板
	 */
	public static final String SMS_CODE_PKP_NOTIFY_DRIVER_C = "SMS_CODE_PKP_NOTIFY_DRIVER_C";
	/**
	 * 特殊增值服务类运单到货通知模板
	 */
	public static final String SMS_CODE_JZ_WAYBILL_NOTICE = "JZ_WAYBILL_NOTICE";
	/******************************************************** 无到付款 *******************************************************/
	// 征收保管费
	/**
	 * 自提-无到付-预计提货-征收保管费模板
	 */
	public static final String SMS_CODE_PICKUP_NO_FC_FORECAST = "PICKUP_NO_FC_FORECAST";
	/**
	 * 自提-无到付-未产生保管费-征收保管费模板
	 */
	public static final String SMS_CODE_PICKUP_NO_FC_FREE = "PICKUP_NO_FC_FREE";
	/**
	 * 自提-无到付-已产生保管费-征收保管费模板
	 */
	public static final String SMS_CODE_PICKUP_NO_FC_FEE = "PICKUP_NO_FC_FEE";
	// 不征收保管费
	/**
	 * 自提-无到付-预计提货-不征收保管费
	 */
	public static final String SMS_CODE_PICKUP_NO_FC_FORECAST_NOT_SC = "PICKUP_NO_FC_FORECAST_NOT_SC";
	/**
	 * 自提-无到付-不征收保管费
	 */
	public static final String SMS_CODE_PICKUP_NO_FC_FREE_NOT_SC = "PICKUP_NO_FC_FEE_NOT_SC";

	/******************************************************** 有到付款 *******************************************************/
	/**
	 * 自提-有到付-预计提货-征收保管费模板
	 */
	public static final String SMS_CODE_PICKUP_FC_FORECAST = "PICKUP_FC_FORECAST";
	/**
	 * 自提-有到付-未产生保管费-征收保管费模板
	 */
	public static final String SMS_CODE_PICKUP_FC_FREE = "PICKUP_FC_FREE";
	/**
	 * 自提-有到付-已产生保管费-征收保管费模板
	 */
	public static final String SMS_CODE_PICKUP_FC_FEE = "PICKUP_FC_FEE";
	/**
	 * 自提-有到付-预计提货-不征收保管费
	 */
	public static final String SMS_CODE_PICKUP_FC_FORECAST_NOT_SC = "PICKUP_FC_FORECAST_NOT_SC";
	/**
	 * 自提-有到付-不征收保管费
	 */
	public static final String SMS_CODE_PICKUP_FC_FREE_NOT_SC = "PICKUP_FC_FREE_NOT_SC";

	/********************************************************* 语音模版code **************************************************/
	/**
	 * 语音模版后缀
	 */
	public static final String SMS_CODE_VOICE_SUFFIX = "_YY";

	/** 发货开单 */
	/**
	 * 上门接货发货人短信模板
	 */
	public static final String SMS_CODE_CONSIGNOR_TO_DOOR = "PICKUP_TO_DOOR";
	/**
	 * 客户自提收货人短信模板
	 */
	public static final String SMS_CODE_CONSIGNEE_SELF_PICKUP = "PICKUP_SELF";
	/**
	 * 收货客户_自提_有到付短信模板
	 */
	public static final String SMS_CODE_CONSIGNEE_SELF_PICKUP_FC = "PICKUP_SELF_FC";
	/**
	 * 收货客户_自提_无到付短信模板
	*/
	public static final String SMS_CODE_CONSIGNEE_NO_SELF_PICKUP = "PICKUP_SELF_NO_FC";
	/**
	 * 收货客户_送货_有到付短信模板
	*/
	public static final String SMS_CODE_CONSIGNEE_PICKUP_SENDGOODS_FC="PICKUP_SENDGOODS_FC";
	/**
	 * 收货客户_送货_无到付短信模板
	*/
	public static final String SMS_CODE_CONSIGNEE_PICKUP_SENDGOODS_NO_FC="PICKUP_SENDGOODS_NO_FC";
	/**
	 * 通知发货人不在自动受理时间范围短信模板(自动调度开启之前)
	 */
	public static final String SMS_CODE_AUTODISPATCH_OFF_EARLY= "ORDER_EXPRESS_AUTO_EARLY";
	/**
	 * 通知发货人不在自动受理时间范围短信模板(自动调度关闭之后)
	 */
	public static final String SMS_CODE_AUTODISPATCH_OFF_LATE= "ORDER_EXPRESS_AUTO_LATE";
	/**
	 * 快递PDA退回(联系不上客户)
	 */
	public static final String SMS_CODE_RETURN_CONTACT_NO_CUSTOMER_EXPRESS= "EXPRESS_RETURN_CONTACT_NO_CUSTOMER";
	/**
	 * 其它方式收货人短信模板
	 */
	public static final String SMS_CODE_CONSIGNEE_OTHER_PICKUP = "PICKUP_OTHER";
	/**
	 * 客户通知广告模版CODE
	 */
	public static final String PKP_NOTIFY_CUSTOMER_AD = "PKP_NOTIFY_CUSTOMER_AD";
	/**
	 * 更改单受理同意短信Code
	 */
	public static final String SMS_CODE_WAYBILL_RFC_ACCEPT = "WAYBILL_RFC_ACCECPT";
	/**
	 * 更改单受理同意短信Code
	 */
	public static final String SMS_CODE_WAYBILL_RFC_DENY = "PKP_WAYBILL_RFC_DENY";
	/**
	 * 快递开单收货人短信Code
	 */
	public static final String EXPRESS_WAYBILL_RECEIVE_CUSTOMER_SMS = "EXPRESS_WAYBILL_CONSIGNEE";
	/**
	 * 快递更改单受理同意Code
	 */
	public static final String EXPRESS_WAYBILL_RFC_SMS = "EXPRESS_WAYBILL_RFC";
	/**
	 * 零担受理更改受理同意-通知司机Code 
	 */
	public static final String PICKUP_FC_DELIVERY = "PICKUP_FC_DELIVERY";
	/**
	 * 系统来源
	 */
	public static final String SYS_SOURCE = "FOSS";
	/** 业务类型 */
	/**
	 * 客户通知
	 */
	public static final String BS_TYPE_PKP_NOTIFY = "PKP_NOTIFY";
	/**
	 * 快递自动调度
	 */
	public static final String BS_TYPE_PKP_EXPRESS_AUTO_DISPATCH= "PKP_EXPRESS_AUTO";
	/**
	 * 订单
	 */
	public static final String BS_TYPE_PKP_ORDER = "PKP_ORDER";
	/**
	 * 签收出库
	 */
	public static final String BS_TYPE_PKP_SIGN = "PKP_SIGN";
	/**
	 * 签收单返单
	 */
	public static final String BS_TYPE_PKP_SIGNRETURNPROCESS = "PKP_SIGNRETURN";
	/**
	 * 运单开单
	 */
	public static final String BS_TYPE_PKP_WAYBILL = "PKP_WAYBILL";
	/**
	 * 图片开单
	 */
	public static final String BS_TYPE_PKP_WAYBILL_PIC = "PKP_WAYBILL_PIC";


	/**
	 * 快递开单
	 */
	public static final String BS_TYPE_PKP_WAYBILLEXP = "PKP_WAYBILL_EXP";

	/**
	 * 更改单同意
	 */
	public static final String BS_TYPE_PKP_WAYBILLRFC = "PKP_WAYBILLRFC";

	/** 同步短信状态的时间配置 */
	/**
	 * 更新x天的通知未果的短信
	 */
	public static final int SYNC_SMS_MIN_DAY = 1;
	/**
	 * 短信大于3分钟
	 */
	public static final int SMS_NOTICE_MAX_MINUTE = 3;
	/**
	 * 语音大于5分钟
	 */
	public static final int VOICE_NOTICE_MAX_MINUTE = 5;

	/**
	 * 分隔符
	 */
	public static final String SPLIT_CHAR = ",";
	public static final String SPLIT_CHAR_DASH = "-";

	/** 临时表的状态 */
	/**
	 * 0-未处理
	 */
	public static final String HANDLE_NO = "0";
	/**
	 * 1-处理中
	 */
	public static final String HANDLE_ING = "1";
	/**
	 * 2-处理失败
	 */
	public static final String HANDLE_FAILURE = "2";
	/**
	 * 3-没有找到区域范围编码
	 */
	public static final String HANDLE_SUCCESS_NOTFOUNT_ID = "3";
	/**
	 * 4-根据gis的区域没有匹配到接货小区id及车辆车牌号
	 */
	public static final String HANDLE_SUCCESS_NOTFOUNT_ENTITY = "4";
	/**
	 * 5-综合接口调用GIS返回失败
	 */
	public static final String HANDLE_FAILURE_GIS = "5";
	/**
	 * 6-处理成功
	 */
	public static final String HANDLE_SUCCESS_OK = "6";
	/**
	 * 50个记录一次循环
	 */
	public static final int ROWNUM = 1000;

	/**
	 * 送货上楼
	 */
	public static final String DELIVERY_UPSTAIRS = "1";

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String DATE_TIME_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
	/**
	 * HH:mm
	 */
	public static final String DATE_TIME_FORMAT_HHMM = "HH:mm";
	/**
	 * 12:00
	 */
	public static final String DATE_TIME_AM_12 = "12:00";
	/**
	 * 库区默认值
	 */
	public static final String DEFAULT_GOODS_AREA_CODE = "N/A";

	/* 保管费计算job */
	public static final String STORAGE_COMPUTE_JOB_NAME = "storageChargeProcessJob";
	/* 自动通知JOB */
	public static final String AUTO_WAYBILL_NOTIFY = "autoWaybillNotify";

	/* 自动推送到货信息JOB */
	public static final String SEND_ARRIVALGOODS_INFORMATION_JOB_NAME = "sendArrivalGoodsInformationJob";
	/**
	 * 保管费日期执行表记录操作JOB
	 */
	public static final String STORAGE_DATE_JOB_NAME = "storageExecdateProcessJob";

	/**
	 * 批量新增待执行明细表记录
	 */
	public static final String STORAGE_DETAIL_BATCH_ADD_JOB_NAME = "storageExecDetailProcessJob";

	/**
	 * 短信发送目标-发货人
	 */
	public static final String SENDMAIL_TARGET_CONSIGNER = "CONSIGNER";
	/**
	 * 短信发送目标-收货人
	 */
	public static final String SENDMAIL_TARGET_CONSIGNEE = "CONSIGNEE";

	/**
	 * 短信业务类型-快递订单
	 */
	public static final String SMS_PKP_ORDER_EXP = "PKP_ORDER_EXP";

	/**
	 * 短信业务类型-快递运单
	 */
	public static final String SMS_PKP_WAYBILL_EXP = "PKP_WAYBILL_EXP  ";

	/**
	 * 短信业务类型-快递派送通知
	 */
	public static final String SMS_PKP_NOTIFY_EXP = "PKP_NOTIFY_EXP";

	/**
	 * 短信业务类型-快递签收
	 */
	public static final String SMS_PKP_SIGN_EXP = "PKP_SIGN_EXP";

	/**
	 * 短信业务类型-快递更改单
	 */
	public static final String SMS_WAYBILL_RFC_EXP = "WAYBILL_RFC_EXP";

	/**
	 * 短信业务类型-快递签收单返单
	 */
	public static final String SMS_PKP_SIGNRETURN_EXP = "PKP_SIGNRETURN_EXP";
	
	/**
	 * 短信业务类型-理赔付款
	 */
	public static final String SMS_STL_CLAIMS_PAYMENT = "STL_CLAIMS_PAYMENT";
	
	/**
	 * 通知结果-通知成功
	 */
	public static final String NOTICE_CONTENT_SUCCESS = "通知成功";
	/**
	 * 通知结果-通知失败
	 */
	public static final String NOTICE_CONTENT_FAILURE = "通知失败";
	/**
	 * 自提-有到付-未产生保管费-征收(新)
	 */
	public static final String SELF_FC_LEVY = "PICKUP_FC_FREE";
	/**
	 * 自提-有到付-已产生保管费-征收(新)
	 */
	public static final String SELF_FC_STORE_FEE_LEVY = "PICKUP_FC_FEE";
	/**
	 * 自提-有到付-预计提货-征收(新)
	 */
	public static final String SELF_FC_PREDELIVER_LEVY = "PICKUP_FC_FORECAST";
	/**
	 * 自提-有到付-不征收(新)
	 */
	public static final String SELF_FC = "PICKUP_FC_FREE_NOT_SC";
	/**
	 * 自提-有到付-预计提货-不征收(新)
	 */
	public static final String SELF_FC_PREDELIVER = "PICKUP_FC_FORECAST_NOT_SC";
	/**
	 * 自提-无到付-未产生保管费-征收(新)
	 */
	public static final String SELF_NOFC_LEVY = "PICKUP_NO_FC_FREE";
	/**
	 * 自提-无到付-已产生保管费-征收(新)
	 */
	public static final String SELF_NOFC_STORE_FEE_LEVY = "PICKUP_NO_FC_FEE";
	/**
	 * 自提-无到付-预计提货-征收(新)
	 */
	public static final String SELF_NOFC_PREDELIVER_LEVY = "PICKUP_NO_FC_FORECAST";
	/**
	 * 自提-无到付-不征收(新)
	 */
	public static final String SELF_NOFC = "PICKUP_NO_FC_FEE_NOT_SC";
	/**
	 * 自提-无到付-预计提货-不征收(新)
	 */
	public static final String SELF_NOFC_PREDELIVER = "PICKUP_NO_FC_FORECAST_NOT_SC";

	//通知取消语音试点部门
	public static final String NOTICE_CUSTOMER_ORG_CODE = "NOTICE_CUSTOMER_ORG_CODE";
	//通知取消语音试点部门编码
	public static final String NOTICE_CUSTOMER_ORG_CODES = "W011305080404,W0113080403,W03050339";
	//通知取消语音试点部门编码 (全国)
	public static final String NOTICE_CUSTOMER_ORG_DIP = "DIP";
	
	//FOSS-快递批量整合1天量
	public static final String BATCH_SMS_DAY_EXP = "BATCH_SMS_DAY_EXP";
	
	//FOSS-快递批量整合7天量
	public static final String BATCH_SMS_WEEK_EXP = "BATCH_SMS_WEEK_EXP";
	
	//###################
	/**
	 * 自提-有到付-不征收(新)
	 */
	public static final String SELF_FC_WV = "PICKUP_FC_FREE_NOT_SC_WV";
	/**
	 * 自提-无到付-不征收(新)
	 */
	public static final String SELF_NOFC_WV = "PICKUP_NO_FC_FEE_NOT_SC_WV";
	
	
	
	/***
	 * 自提-无到付-预计提货-不征收-零担
	 */
	public static final String PICKUP_NO_FC_FORECAST_NOT_SC_WV = "PICKUP_NO_FC_FORECAST_NOT_SC_WV";
	/***
	 * 自提-有到付-预计提货-不征收-零担
	 */
	public static final String PICKUP_FC_FORECAST_NOT_SC_WV = "PICKUP_FC_FORECAST_NOT_SC_WV";
	/***
	 * 短地址常量
	 */
	public static final String GIS_SHORTURL_HTTP_DEFAULT = "http://Deppon.com/";
	/***
	 * GIS短链接请求链接头
	 */
	public static final String GIS_SHORTURL_HTTP = "GIS_SHORTURL_HTTP";
	/***
	 * 客户作为收件人短信停发
	 */
	public static final String SMS_STOP_CUSTOMER_AS_RECIEVER = "STOP_MESSAGE_FOR_RECEIPT";
	/***
	 * 客户的收件人短信停发
	 */
	public static final String SMS_STOP_RECIEVER = "STOP_MESSAGE_FOR_CUST_RECEIPT";
	/***
	 * 二者都停发
	 */
	public static final String SMS_STOP_RECIEVER_BOTH = "STOP_MESSAGE_FOR_DOUBLE";
	
	/**
	 * 返货单发货人model
	 */
	public static final String EXPRESS_RETURN_TO_CONSIGNOR_SMS="EXPRESS_RETURN_TO_CONSIGNOR";
	
	/**
	 * 原单收货人model
	 */
	public static final String EXPRESS_RETURN_TO_RECEIVER_SMS="EXPRESS_RETURN_TO_RECEIVER";
	
}