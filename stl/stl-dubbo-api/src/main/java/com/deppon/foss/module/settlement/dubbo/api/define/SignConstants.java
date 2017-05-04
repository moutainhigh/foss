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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/define/SignConstants.java
 * 
 * FILE NAME        	: SignConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.dubbo.api.define;

/**
 * 签收出库常量
 * 
 * @author foss-meiying
 * @date 2012-11-5 下午2:58:37
 * @since
 * @version
 */
public class SignConstants {
	/** 
	 * 签收情况--正常签收
	 */
	public static final String NORMAL_SIGN = "NORMAL_SIGN";
	/**
	 *  签收情况---异常签收
	 */
	public static final String UNNORMAL_SIGN = "UNNORMAL_SIGN";
	/** 
	 * 签收情况---部分签收
	 */
	public static final String PARTIAL_SIGN = "PARTIAL_SIGN";
	/** 
	 * 快递100---限制签收
	 */
	public static final String LIMIT_SIGN = "SD";
	/** 
	 *  签收备注---异常-弃货
	 */
	public static final String ABANDONGOODS_SIGN_NOTE = "异常-弃货";
	
	/** lizhiguo**/	
	/**变更签收结果--变更类型
	/**
	 * 运单签收结果
	 */
	public static final String SIGN_RFC_TYPE_WAYBILL = "SIGN_RFC_TYPE_WAYBILL";
	/**
	 * 付款
	 */
	public static final String SIGN_RFC_TYPE_REPAYMENT = "SIGN_RFC_TYPE_REPAYMENT";
	/**
	 * 到达联
	 */
	public static final String SIGN_RFC_TYPE_ARRIVESHEET = "SIGN_RFC_TYPE_ARRIVESHEET";
	/**
	 * 反签收专线
	 */
	public static final String SIGN_RFC_TYPE_REVERSESIGN_DEDICATED = "SIGN_RFC_TYPE_REVERSESIGN_DEDICATED";
	/**
	 * 反签收空运和偏线
	 */
	public static final String SIGN_RFC_TYPE_REVERSESIGN_AIR_PARTIAL = "SIGN_RFC_TYPE_REVERSESIGN_AIR_PARTIAL";
	
	/**
	 * 变更明细--变更类型--0:付款
	 */
	public static final String SIGN_RFC_CHANGEDETAIL_TYPE_REPAYMENT = "CHANGEDETAIL_TYPE_REPAYMENT";
	/**
	 * 变更明细--变更类型--1:到达联
	 */
	public static final String SIGN_RFC_CHANGEDETAIL_TYPE_ARRIVESHEET = "CHANGEDETAIL_TYPE_ARRIVESHEET";
	/**
	 * 变更明细--变更类型--2:运单签收结果
	 */
	public static final String SIGN_RFC_CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT = "CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT";
	
	/**
	 * 反签收明细--类型-付款
	 */
	public static final String REVERSE_SIGN_TYPE_REPAYMENT = "REVERSE_SIGN_TYPE_REPAYMENT";
	/**
	 * 反签收明细--类型-到达联
	 */
	public static final String REVERSE_SIGN_TYPE_ARRIVESHEET = "REVERSE_SIGN_TYPE_ARRIVESHEET";
	/**
	 * 反签收明细--类型-运单签收结果
	 */
	public static final String REVERSE_SIGN_TYPE_WAYBILLSIGNRESULT = "REVERSE_SIGN_TYPE_WAYBILLSIGNRESULT";
	
	/**
	 * 签收结果--审批状态--1,审批中
	 */
	public static final String SIGN_RFC_SIGN_APPROVALIN = "CHANGE_SIGN_APPROVALIN";
	/**
	 * 审批状态--2,已通过
	 */
	public static final String SIGN_RFC_SIGN_PASSED = "CHANGE_SIGN_PASSED";
	/**
	 * 审批状态--2,已拒绝
	 */
	public static final String SIGN_RFC_SIGN_REFUSED = "CHANGE_SIGN_REFUSED";
	/**
	 * 申请次序数
	 */
	public static final String SIGN_RFC_SIGN_NO = "0000";
	/**
	 * 流水号补充位数
	 */
	public static final int SIGN_RFC_SEQ_NO = 4;
	/**
	 * 实付运费
	 */
	public static final String ACTUALFREIGHT = "actualFreight";
	/**
	 * 代收货款
	 */
	public static final String CODAMOUNT = "codAmount";
	/**
	 * 签收件数
	 */
	public static final String SIGNGOODSQTY = "signGoodsQty";
	/****lizhiguo*****/
	
	/***
	 * 常量','
	 */
	public static final String SPLIT_CHAR = ",";
	
	/***
	 * 默认值 N/A
	 */
	public static final String DEFAULT_VALUE = "N/A";
	
	
	/**0*/
	public static final int ZERO = 0;
	
	/**1*/
	public static final int ONE = 1;
	
	/**2*/
	public static final int TWO = 2;
	/**3*/
	public static final int THREE = 3;

	/**0001*/
	public static final String SERIAL_ZERO_ONE = "0001";
	
	/**
	 *  签收状态--全部签收
	 */
	public static final String SIGN_STATUS_ALL = "SIGN_STATUS_ALL";			//快递代理签收 - 1
	/** 
	 * 签收状态--部分签收
	 */
	public static final String SIGN_STATUS_PARTIAL = "SIGN_STATUS_PARTIAL";	//快递代理签收 - 3
	/**
	 *  签收状态--未签收
	 */
	public static final String SIGN_STATUS_NO = "SIGN_STATUS_NO";
	/**
	 * 签收状态--拒签
	 */
	public static final String SIGN_STATUS_REFUSE = "SIGN_STATUS_REFUSE"; // 快递代理签收 - 4
	/**
	 * 签收状态－－异常签收
	 */
	public static final String SIGN_STATUS_UNUSUAL = "SIGN_STATUS_UNUSUAL";
	
	/**
	 * 交接类型--接货
	 */
	public static final String HANDOVER_TYPE_RECEIVE = "RECEIVE";
	/**
	 * 交接类型--转货
	 */
	public static final String HANDOVER_TYPE_TRANSFER = "TRANSFER";
	/**
	 * 交接类型--拉回货物
	 */
	public static final String HANDOVER_TYPE_BACK = "GOODS_BACK";
	/**
	 * 给发货人正常签收模板-零担
	 */
	public static final String SMS_CODE_DELIVERY_NORMAL_SIGN_LD = "DELIVERY_NORMAL_SIGN_LD";
	/**
	 * 给发货人异常签收模板-零担
	 */
	public static final String SMS_CODE_DELIVERY_UNNORMAL_SIGN_LD = "DELIVERY_UNNORMAL_SIGN_LD";
	/**
	 * 给收货人正常签收模板-零担
	 */
	public static final String SMS_CODE_RECEIVE_NORMAL_SIGN_LD= "RECEIVE_NORMAL_SIGN_LD";
	
	/**
	 * 给收货人异常签收模板-零担 
	 */
	public static final String SMS_CODE_RECEIVE_UNNORMAL_SIGN_LD = "RECEIVE_UNNORMAL_SIGN_LD";
	
	/**
	 * 签收出库始发站在线通知模板
	 */
	public static final String PKP_SIGN_NOTICE = "PKP_SIGN_NOTICE";
	
	/**
	 * 快递收货人签收短信模板
	 */
	public static final String SMS_CODE_EXPRESS_SIGN_RECEIVE_CUSTOMER = "EXPRESS_SIGN_CONSIGNEE";
	/**
	 * 快递代理收货人签收短信模板
	 */
	public static final String SMS_CODE_EXPRESS_PROXY_SIGN_RECEIVE_CUSTOMER = "EXPRESS_SIGN_PROXY_CONSIGNEE";
	
	/**
	 * 快递发货人短信模板
	 */
	public static final String SMS_CODE_EXPRESS_SIGN_DELIVERY_CUSTOMER = "EXPRESS_SIGN_CONSIGNOR";
	
	/**
	 * 快递签收收货人短信模板-PDA
	 */
	public static final String SMS_CODE_EXPRESS_SIGN_RECEIVE_CUSTOMER_PDA = "EXPRESS_SIGN_CONSIGNEE_PDA";
	/**
	 * 快递签收代理收货人短信模板-PDA
	 */
	public static final String SMS_CODE_EXPRESS_PROXY_SIGN_RECEIVE_CUSTOMER_PDA = "EXPRESS_SIGN_PROXY_CONSIGNEE_PDA";
	/**
	 * 快递签收发货人短信模板-PDA
	 */
	public static final String SMS_CODE_EXPRESS_SIGN_DELIVERY_CUSTOMER_PDA = "EXPRESS_SIGN_CONSIGNOR_PDA";
	/**
	 * 快递签收代理发货人短信模板-PDA
	 */
	public static final String SMS_CODE_EXPRESS_PROXY_SIGN_DELIVERY_CUSTOMER_PDA = "EXPRESS_SIGN_PROXY_CONSIGNOR_PDA";
	/**
	 * 派送拉回原因为“联系不上客户”，收货人短信模板
	 */
	public static final String SMS_CODE_EXPRESS_PKP_CONSIGNEE = "EXPRESS_DELIVERY_NOT_CONTACT";
	
	/**
	 * 取件拉回原因为“联系不上客户”，发货人短信模板
	 */
	public static final String SMS_CODE_EXPRESS_PKP_CONSIGNOR = "EXPRESS_ORDER_NOT_CONTACT";
	/**
	 * 派送拉回原因为“联系不上客户”，派件短信业务类型
	 */
	public static final String SMS_CODE_EXP_PKP_CONSIGNEE = "EXP_DEL_NOT_CONTACT";
	
	/**
	 * 取件拉回原因为“联系不上客户”，取件短信业务类型
	 */
	public static final String SMS_CODE_EXP_PKP_CONSIGNOR = "EXP_ORD_NOT_CONTACT";
	/**
	 * 常量值DELIVER
	 */
	public static final String RECEIVE_METHOD_DELIVER = "DELIVER";
	
	/**
	 * 常量值'有'
	 */
	public static final String IS_EXCEPTION_YES = "有";
	/**
	 * 常量值‘无'
	 */
	public static final String IS_EXCEPTION_NO = "无";
	/**
	 * 附件模型
	 */
	public static final String ATTACHEMENTMODE = "pkp-sign";
	
	/**
	 * CRM 订单状态   
	 * 		东方购物 客户编码
	 */
	public static final String CRM_ORDER_CUSTOMER_CODE = "401235548";
	/**
	 * CRM 定单状态   
	 * 		正常签收对应CRM订单的“SIGNSUCCESS”状态
	 */
	public static final String CRM_ORDER_STATUS_SIGNSUCCESS = "SIGNSUCCESS";
	/**
	 * CRM 定单状态    
	 * 		异常签收对应CRM订单的"SIGNFAIL"状态
	 */
	public static final String CRM_ORDER_STATUS_SIGNFAIL = "SIGNFAIL";
	/**
	 * CRM 订单状态    
	 * 		拒收签收对应CRM订单的"SIGNREFUSE"状态
	 */
	public static final String CRM_ORDER_STATUS_SIGNREFUSE = "SIGNREFUSE";
	
	/**
	 * 签收反签收时是否调用异常时的流水号限制数量
	 */
	public static final Integer DEFAULT_SERIALNOS_LIMIT_COUNT = 30;
	/**
	 * 签收反签收时是否调用异常接口每次处理条数
	 */
	public static final Integer SIGN_JOB_EACH__COUNT = 100;
	
	
	/**
	 * 付款类型
	 */
	public static final String PAYMENTTYPE = "paymentType";
	
	/**
	 * 身份类型
	 */
	public static final String IDENTIFYTYPE = "identifyType";
	
	/**
	 * 签收情况
	 */
	public static final String SITUATION = "situation";
	/**
	 * 签收情况
	 */
	public static final String SIGN_SITUATION = "signSituation";
	/**
	 * 签收备注
	 */
	public static final String SIGN_NOTE = "signNote";
	
	/**
	 * 签收时间
	 */
	public static final String SIGN_TIME = "signTime";
	public static final String CONSIGNEE_CODE = "consigneeCode";
	
	/**
	 * 德邦快递
	 */
	public static final String PACKAGE_DEFAULT= "【德邦快递】";
	/**
	 * 是
	 */
	public static final String YES= "Y";
	
	/**
	 * 否
	 */
	public static final String NO= "N";
	
	/**
	 * 德邦快递
	 */
	public static final String SIGN_DELIVERY_MAN= "草签";
	/**
	 * 派送方式自提
	 */
	public static final String RECEIVE_METHOD= "PICKUP";

	
	/**
	 *  快递快递代理签收运输性质全部
	 */
	public static final String EXPRESS_SIGN_STATUS_ALL = "ALL";		
	/* 签收单提醒的默认值 To sign for the single back to the text */
	public static final String SIGN_BACK_FAIL_DEFAULE="不需要发送签收单短信";
	
	

	/** 批量发送短信相关模板常量 **/
	//发货#票，签单需返回的有#票  无签单返回  <1
	public static final String BATCH_SEND_BILL_NORETURN_QTY= "BATCH_SEND_BILL_NORETURN_QTY";
	//发货#票，签单需返回的有#票  有签单返回
	public static final String BATCH_SEND_BILL_PROCESS_BILL_RETURN_QTY= "BATCH_SEND_BILL_PROCESS_BILL_RETURN_QTY";
	
	
	
	//货物被成功签收#票，成功返回的签单共有#票  无成功返回的签单 <2
	public static final String BATCH_SEND_SIGN_QTY= "BATCH_SEND_SIGN_QTY";
	//货物被成功签收#票，成功返回的签单共有#票  有成功返回的签单
	public static final String BATCH_SEND_SIGN_PROCESS_BILL_RETURN_QTY= "BATCH_SEND_SIGN_PROCESS_BILL_RETURN_QTY";
	
	//货物被成功签收#票，成功返回的签单共有#票  无成功返回的签单 <3
	public static final String BATCH_SEND_LW_BILL_SIGN_QTY= "BATCH_SEND_LW_BILL_SIGN_QTY";
	//货物被成功签收#票，成功返回的签单共有#票  有成功返回的签单
	public static final String BATCH_SEND_LW_BILL_SIGN_PROCESS_BILL_RETURN_QTY= "BATCH_SEND_LW_BILL_SIGN_PROCESS_BILL_RETURN_QTY";
	
	public static final String EXIST= "EXIST";
	public static final String NOTEXIST= "NOTEXIST";
	

	// 发件人短信停发 
	public static final String STOP_MESSAGE_FOR_DELIVER="STOP_MESSAGE_FOR_DELIVER"; 
	// 发件人短信批量发送 
	public static final String BATCH_MESSAGE_FOR_DELIVER="BATCH_MESSAGE_FOR_DELIVER";
	
	// 收件人短信停发 
	public static final String STOP_MESSAGE_FOR_RECEIPT = "STOP_MESSAGE_FOR_RECEIPT"; 
	
	/***
	 * 客户的收件人短信停发
	 */
	public static final String SMS_STOP_RECIEVER = "STOP_MESSAGE_FOR_CUST_RECEIPT";
	/***
	 * 二者都停发
	 */
	public static final String SMS_STOP_RECIEVER_BOTH = "STOP_MESSAGE_FOR_DOUBLE";
	
	/**
	 * 快递代理外发XX天未签收自动上报丢货查询 上线的历史数据开始时间
	 */
	public static final String NOT_SIGN_REPORTING_LOSTGOODS_BEGIN_TIME = "2014-06-01";
	/**
	 * 快递代理外发XX天未签收自动上报OA丢货 Job名称
	 */
	public static final String NOT_SIGN_REPORTING_LOSTGOODS_JOB = "LdpNotSignReportOaJob";
	
	/**
	 * 变更明细--变更类型--3:货签表变更
	 * */
	public static final String SIGN_RFC_CHANGEDETAIL_TYPE_LABELTABLEFLG="CHANGEDETAIL_TYPE_LABELTABLEFLG";
	
	
	/**
	 * 丢货差错自动上报
	 */
	public static final String LOST_CARGO_NOTIFY_PROCESS_JOB = "LostCargoNotifyProcessJob";
	
	/**
	 * 上报oa异常内物短少数据job
	 */
	public static final String EXCEPTION_SHORT_NOTIFY_PROCESS_JOB = "ShortGoodsNotifyProcessJob";

	/**
	 * 异常线上划责差错上报job
	 */
	public static final String UNNORMAL_SIGN_NOTIFY_PROCESS_JOB = "UnnormalSignNotifyProcessJob";
	
	/**
	 * 台湾空运
	 */
	public static final String TAIWANKONGYUN="LDP00174001";
	
	/**
	 * 台湾海运
	 */
	public static final String TAIWANHAIYUN="LDP00175001";
	
	/**
	 * 台湾-发货人-短信发送模板
	 */
	public static final String EXPRESS_WAYBILL_CONSIGNTW="EXPRESS_WAYBILL_CONSIGNTW";
	
	
	/**
	 * 快递返货单 发货人短信 PDA
	 */
	public static final String SMS_CODE_EXPRESS_RETURN_DELIVERY_CUSTOMER_PDA = "SMS_CODE_EXPRESS_RETURN_DELIVERY_CUSTOMER_PDA";
	
	/**
	 * 快递返货单 发货人短信
	 */
	public static final String SMS_CODE_EXPRESS_RETURN_DELIVERY_CUSTOMER = "SMS_CODE_EXPRESS_RETURN_DELIVERY_CUSTOMER";

	/**
	 * 快递返货单 收货人短信 PDA
	 */
	public static final String SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER_PDA = "SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER_PDA";

	/**
	 * 快递返货单 收货人短信
	 */
	public static final String SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER = "SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER";

	/**
	 * 判断子母件的查询条件:运单号
	 */
	public static final String WAYBILL_NO = "waybillNo";
	
	/**
	 * 判断子母件的查询条件:是否有效
	 */
	public static final String ACTIVE = "active";

	
	
	/**
	 * 合伙人-保证金按运单扣款-同步接口服务
	 */
	public  static final String PTP_DEDUCT_ESB_SYN_SERVER_CODE = "/ESB_FOSS2ESB_BOND_WITHHOLD";
	
	/**
	 * 合伙人-保证金反扣款-同步接口服务
	 */
	public static final  String PTP_REFUND_ESB_SYN_SERVER_CODE = "/ESB_FOSS2ESB_BOND_DEDUCT_ROLLBACK";
	
	/**
	 * 合伙人-签收时-更新派送流水状态-异步接口服务
	 */
	public static final String PTP_MODIFY_SALEFLOW__STATUS_ASYN_CODE = "ESB_FOSS2ESB_EFFECT_AP_FLOW";
	
	/**
	 * 合伙人-初始化数据-410上线，日期格式化
	 */
	public static final String PTP_INIT_DATE_410 = "yyyy-MM-dd HH:mm:ss";
	
}