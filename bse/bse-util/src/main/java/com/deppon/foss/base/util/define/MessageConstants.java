/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-util
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/define/MessageConstants.java
 * 
 * FILE NAME        	: MessageConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util.define;


/**
 * 消息共用常量定义
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-14 下午2:08:22
 */
public class MessageConstants {
	
	/**
	 * 受理状态:已受理
	 */
	public final static String ACCEPTSTATUS_HANDLED="HANDLED";
	/**
	 * 受理状态:未受理
	 */
	public final static String ACCEPTSTATUS_REFUSED="REFUSED";
	/**
	 * 返货受理结果:已补码
	 */
	public final static String HANDLE_RESULT_COMPLEMENT = "COMPLEMENT";
	/**
	 * 返货受理结果:未处理
	 */
	public final static String HANDLE_RESULT_UNHANDLE = "UNHANDLE";
	
	/**
	 * 查询时间范围
	 */
	public static final int DATE_LIMIT_DAYS_MONTH=31;
	/**
	 * CREATE_TYPE 站内消息生成方式
	 */
	public static final String MSG__CREATE_TYPE__AUTO = "A"; // 系统生成

	public static final String MSG__CREATE_TYPE__MANUAL = "M"; // 手工输入
	
	/**
	 *站内消息状态
	 */
	public static final String MSG__STATUS__PROCESSED = "D"; // 已处理
	
	public static final String MSG__STATUS__PROCESSING = "G"; // 未处理
	
	/**
	 *接收方类型
	 */
	public static final String MSG__RECEIVE_TYPE__ORG = "O"; // 组织 
	
	public static final String MSG__RECEIVE_TYPE__ORG_ROLE = "OR"; // 组织和角色
	
	/**
	 *业务单号类型
	 */
	public static final String MSG__BUSINESS_NO_TYPE__WAYBILL = "W"; // 运单类型
	
	public static final String MSG__BUSINESS_NO_TYPE__ORDER = "O"; // 订单类型
	
	/**
	 *  系统用户编码
	 */
	public static final String MSG__SYS_USER_CODE = "SYSTEM";  
	/**
	 *  系统网点编码
	 */
	public static final String MSG__SYS_ORG_CODE = "SYSORG";  
	/**
	 *  WEB待办
	 */
	public static final String MSG__URL_TYPE__WEB = "WEB";  
	/**
	 *  GUI待办
	 */
	public static final String MSG__URL_TYPE__GUI = "GUI"; 
	/**
	 *  代收货款在线提醒
	 */
	public static final String MSG_TYPE__COLLECTION = "COLLECTION";
	/**
	 * 代收货款退款失败提醒
	 */
	public static final String MSG_TYPE__CODPAYFAILD="CODPAYFAILD";
	/**
	 *  开单到付客户网上支付
	 */
	public static final String MSG_TYPE__ONLINEPAYMENT = "ONLINEPAYMENT";
	/**
	 *  供应商补录失败消息
	 */
	public static final String MSG_TYPE__SUPPLIER_FAIL_PATCH = "SUPPLIER_FAIL_PATCH";
	
	public static final String MSG_TYPE__FAILINGINVOICE = "FAILINGINVOICE";
	/**
	 *  未对账月结客户
	 */
	public static final String MSG_TYPE__UNRECONCILIATION = "UNRECONCILIATION";
	/**
	 *  距结款时间不足5日还未还款月结客户
	 */
	public static final String MSG_TYPE__NOREPAYMENT = "NOREPAYMENT";
	/**
	 * 结算天数不足10天提醒
	 */
	public static final String MSG_DT__DEBITWILLOVER="DEBITWILLOVER_DT";
	public static final String MSG_CT__DEBITWILLOVER="DEBITWILLOVER_CT";

	/**
	 * 派送退回提醒deliveryReturn
	 */
	public static final String MSG_SEND__DELIVERYRETURN="PKP_EXCEPTION_SEND_RETURN";//异常操作-派送拉回
	public static final String MSG_DISPATCH__DELIVERYRETURN="PKP_EXCEPTION_DISPATCH_RETURN";//异常操作-调度退回
	public static final String MSG_STATUS="HANDLING";//状态--处理中
	
	/**
	 * 快递派送地址库类型
	 */
	public static final String TOWN_ALL = "TOWN_ALL";//镇全部
	public static final String ROAD_ALL = "ROAD_ALL";//路全部
	public static final String NUMBER_ALL = "NUMBER_ALL";//号全部
	public static final String NUMBER_ODD = "NUMBER_ODD";//单号
	public static final String NUMBER_EVEN = "NUMBER_EVEN";//双号
	
	/**
	 * 快递派送地址库推送结果
	 */
	public static final String SUCCESS = "true";
	public static final String FAILED = "false";
}
