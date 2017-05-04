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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/define/PdaConstants.java
 * 
 * FILE NAME        	: PdaConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.define;

/***
 * 派送处理
 * @author foss-meiying
 * @date 2012-11-12 下午1:58:30
 * @since
 * @version
 */
public class PdaConstants {

	/** 清仓状态  **/
	/** 清仓中 */
	public static final String STOCK_CHECKING_DOING  = "DOING";
	/** 清仓完毕 */
	public static final String STOCK_CHECKING_DONE   = "DONE";
	/** 已撤销 */
	public static final String STOCK_CHECKING_CANCEL = "CANCEL";
	
	/**清仓任务中 PDA扫描结果*/
	/**正常*/
	public static final String GOODS_STATUS_OK = "OK";
	/**多货*/
	public static final String GOODS_STATUS_SURPLUS = "SURPLUS";
	
	/**
	 * 0
	 */
	public static final Integer ZERO = 0;
	
	/**
	 * 1
	 */
	public static final Integer ONE = 1;
	/**
	 * 默认值
	 */
	public static final  String DEFAULT_EMP_NAME_PDA = "pda";
	public static final  String OPERATOR_EMP_CODE = "000000";
	
	/**
	 * 默认值 签收
	 */
	public static final  String SITUATION_SIGN = "SIGN";
	/**
	 * 默认值 拒收
	 */
	public static final  String SITUATION_REFUSE = "REFUSE";
	/**
	 * 订单备注  其他
	 */
	public static final  String ORDER_NOTES_ELSE_REASON = "ELSE_REASON";
	
	/**
	 * 操作类型   零担   
	 */
	public static final  String OPERATE_TYPE_TYPE_LTL = "LTL";
	/**
	 * 操作类型   快递 
	 */
	public static final  String OPERATE_TYPE_EXPRESS = "EXPRESS";
	/**
	 * 派送拉回原因  联系不上客户
	 */
	public static final  String CUSTOMER_NOT_CONTACT = "CUSTOMER_NOT_CONTACT";
}