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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/define/ReturnBillProcessConstants.java
 * 
 * FILE NAME        	: ReturnBillProcessConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.define;

/**
 * 签收单返单常量类
 * 
 * @date 2012-11-28 上午11:45:14
 *
 */
public class ReturnBillProcessConstants {

	/** 短信模版code-签收单返单 */ 
	public static final String SMS_CODE_PROCESS_BILL_RETURN 
		= "PKP_PROCESS_BILL_RETURN_NOTICE"; 
	
	/** 短信模版code-快递签收单返单 */ 
	public static final String SMS_CODE_EXPRESS_PROCESS_BILL_RETURN 
		= "EXPRESS_SIGN_RETURN"; 
	/***************************返单状态*******************************/
	//未返单
	public static final String NONE_RETURN_BILL = "NONE_RETURN_BILL";
	
	//已返单
	public static final String ALREADY_RETURN_BILL = "ALREADY_RETURN_BILL";
	
	/***************************返单类型*******************************/
	//原件返回
	public static final String ORIGINAL= "ORIGINAL";
	
	//扫描件
	public static final String SCANNED = "SCANNED";
	
	//传真件
	public static final String FAX = "FAX";
	
	//无需返单
	public static final String NO_RETURN_BILL = "NO_RETURN_BILL";
	
	/***************************其他参数*******************************/
	
	/** 短信摸板参数 运单号*/
	public static final String SMS_WAYBILL = "waybill";
	
	/** 短信摸板参数 发货人姓名*/
	public static final String SMS_NAME ="name";
	
	/** 短信摸板参数 联系电话*/
	public static final String SMS_TELPHONE="telephone";
	
	/** 短信摸板参数 客户地址*/
	public static final String SMS_ADDRESS="address";
	
	
}