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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/CrmOrderImportException.java
 * 
 * FILE NAME        	: CrmOrderImportException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 订单导入开单异常
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-14 下午2:55:05,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-14 下午2:55:05
 * @since
 * @version
 */
public class CrmOrderImportException extends BusinessException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8508645843507717365L;
	
	//调用CRM系统查询订单失败：
	public static final String CRM_ORDER_SERVICE_FAIL="foss.pkp.waybill.crmOrderService.exception.failUseCrmQueryOrder";
	
	//查询CRM订单失败：
	public static final String CRM_ORDER_QUERY_FAIL="foss.pkp.waybill.crmOrderService.exception.failQueryCrmOrder";
		
	//导入CRM系统订单失败：
	public static final String CRM_ORDER_IMPORT_FAIL="foss.pkp.waybill.crmOrderService.exception.failImportCrmOrder";
		
	//导入CRM系统订单失败，从CRM接口未获取到有效的订单数据，请向CRM系统管理员核实
	public static final String VALID_CRM_ORDER_DATA_NULL="foss.pkp.waybill.crmOrderService.exception.nullvalidOrderDataGet";
		
	//此订单编号已经导入开单，请核对或重新选择其他订单
	public static final String VALID_CRM_ORDER_NO_NULL="foss.pkp.waybill.crmOrderService.exception.nullvalidOrderNo";
		
	//调用优惠劵验证接口失败：
	public static final String VALID_ATE_COUPON_FAIL="foss.pkp.waybill.crmOrderService.exception.failValidateCouponRequest";
		
	//调用优惠劵验证接口失败-数据对象转换错误：
	public static final String CONVERT_VALID_ATE_ERROR="foss.pkp.waybill.crmOrderService.exception.errorConvertValidate";
		
	//调用优惠劵使用状态退回接口失败：
	public static final String COUPON_STATE_FAIL="foss.pkp.waybill.crmOrderService.exception.failEffectCouponState";
	
	//调用锁屏信息接口失败：
	public static final String LOCK_STATE_FAIL="foss.pkp.waybill.crmOrderService.exception.LockInfoFall";
	//调用CRM系统查询订单失败：
	public static final String QUERY_CRM_ORDER_PARAMS_IS_NULL="foss.pkp.waybill.crmOrderService.exception.failUseCrmQueryOrder";
	
	/**
	 * 
	 * 创建一个新的实例CrmOrderImportExveption
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:44:45
	 */
	public CrmOrderImportException() {
		super();
	}

	/**
	 * CRM订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:44:53
	 */
	public CrmOrderImportException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * 
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:45:23
	 */
	public CrmOrderImportException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public CrmOrderImportException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public CrmOrderImportException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public CrmOrderImportException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 * CMR订单导入异常类
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 上午9:46:09
	 */
	public CrmOrderImportException(String msg) {
		super(msg);
		this.errCode = msg;
	}

}