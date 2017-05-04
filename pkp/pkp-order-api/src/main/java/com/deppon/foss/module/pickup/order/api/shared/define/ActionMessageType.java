/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/define/ActionMessageType.java
 * 
 * FILE NAME        	: ActionMessageType.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.define;


/**
 * i18n国际话key
 * @author 038590-foss-wanghui
 * @date 2012-10-30 下午2:26:50
 */
public class ActionMessageType {

	/**
	 * 受理成功.
	 */
	public static final String SAVE_SUCCESS = "pkp.order.orderHandle.saveSuccess";
	
	/**
	 * 退回订单成功.
	 */
	public static final String REJECT_SUCCESS = "pkp.order.orderHandle.rejectSuccess";
	
	/**
	 * 订单空.
	 */
	public static final String NONE_ORDER = "pkp.order.orderHandle.orderNull";
	
	/**
	 * 拒绝订单失败.
	 */
	public static final String REJECT_FAILURE = "pkp.order.orderHandle.rejectFailure";
	
	/**
	 * 受理订单失败.
	 */
	public static final String ACCEPT_FAILURE = "pkp.order.orderHandle.acceptFailure";
	
	/**
	 * 短信模板获取失败.
	 */
	public static final String SMS_NOTFOUND = "pkp.order.orderHandle.smsNotFound";
	
	/**
	 * 司机短信发送失败.
	 */
	public static final String DRIVER_SMS_SENDFAILURE = "pkp.order.orderHandle.driverSmsSendFailure";
	
	/**
	 * 客户短信发送失败.
	 */
	public static final String CUSTOMER_SMS_SENDFAILURE = "pkp.order.orderHandle.customerSmsSendFailure";
	
	/**
	 * 修改成功.
	 */
	public static final String MODIFY_SUCCESS = "pkp.order.orderHandle.modifySuccess";
	
	/**
	 * 在线通知失败.
	 */
	public static final String NOTICE_FAILURE = "pkp.order.orderHandle.noticeFailure";
	
	/**
	 * 订单不存在CRM中
	 */
	public static final String NOT_CRM_FAILURE = "pkp.order.orderHandle.notCrmFailure";
	
	/**
	 * 订单已锁定，请稍后再操作
	 */
	public static final String ORDER_LOCK = "pkp.order.orderHandle.orderLock";
	
	/**
	 * 退回订单失败！
	 */
	public static final String REJECT_ORDER_FAILURE = "pkp.order.orderHandle.rejectOrderFailure";
	
	/**
	 * 解绑成功！
	 */
	public static final String UNBUNDLE_SUCCESS = "pkp.order.queryAndResolveBind.unbundleSuccess";
	/**
	 * 无
	 */
	public static final String PDA_NO = "pkp.order.queryDispatchOrder.none";
	/**
	 * PDA异常
	 */
	public static final String PDA_EXCEPTION = "pkp.order.queryDispatchOrder.exception";
	/**
	 * PDA正常
	 */
	public static final String PDA_NORMAL = "pkp.order.queryDispatchOrder.normal";
}