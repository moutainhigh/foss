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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/define/ExceptionProcessConstants.java
 * 
 * FILE NAME        	: ExceptionProcessConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.define;

/**
 * 
 * 异常模块常量
 * @author 043258-foss-zhaobin
 * @date 2012-11-7 上午11:07:39
 */
public class ExceptionProcessConstants 
{
	/************异常类型**************/
	//运单异常
	public static final String WAYBILL_EXCEPTION = "WAYBILL_EXCEPTION";
	//货物异常
	public static final String LABELEDGOOD_EXCEPTION = "LABELEDGOOD_EXCEPTION";
	
	
	
	/************异常环节**************/
	//送货通知
	public static final String CUSTOMER_NOTICE = "CUSTOMER_NOTICE";
	//客户自提
	public static final String CUSTOMER_PICKUP = "CUSTOMER_PICKUP";
	//司机送货
	public static final String DELIVER = "DELIVER";
	//外场卸车
	public static final String OUTFIELD_UNLOADING = "OUTFIELD_UNLOADING";
	//派送排单
	public static final String SEND_ROW_OF_SINGLE ="SEND_ROW_OF_SINGLE";
	
	
	
	/************异常状态**************/
	//处理中
	public static final String HANDLING = "HANDLING";
	//已处理
	public static final String HANDLED = "HANDLED";
	//已转弃货
	public static final String ALREADY_ABANDON_GOODS = "ALREADY_ABANDON_GOODS";
	

	/************异常操作 **************/
	//通知成功
	public static final String PKP_EXCEPTION_INFORM_SUCCESS = "PKP_EXCEPTION_INFORM_SUCCESS";
	//通知失败
	public static final String PKP_EXCEPTION_INFORM_FAIL = "PKP_EXCEPTION_INFORM_FAIL";
	//派送拉回
	public static final String PKP_EXCEPTION_SEND_RETURN = "PKP_EXCEPTION_SEND_RETURN";
	//卸车少货
	public static final String PKP_EXCEPTION_UNCARD_DELETION = "PKP_EXCEPTION_UNCARD_DELETION";
	//调度退回
	public static final String PKP_EXCEPTION_DISPATCH_RETURN = "PKP_EXCEPTION_DISPATCH_RETURN";
	
	
	/************排单状态**************/
	//未排单
	public static final String NO_DELIVERBILL = "NO_DELIVERBILL";
	//已排单
	public static final String DELIVERBILLED = "DELIVERBILLED";
	
	/***********异常模板CODE*************/
	//新增异常模板code
	public static final String NOTICE_CONTENT_CODE = "PKP_NOTICE_CONTENT";
	
	/**处理异常界面-签收记录常量**/
	//含有签收记录
	public static final String PKP_EXCETPION_HAS_SIGNRECORD = "hr";
	//不含有签收记录
	public static final String PKP_EXCETPION_NO_SIGNRECORD = "nr";
	
}