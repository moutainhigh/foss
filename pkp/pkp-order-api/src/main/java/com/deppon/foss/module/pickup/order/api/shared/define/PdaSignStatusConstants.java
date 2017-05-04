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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/define/PdaSignStatusConstants.java
 * 
 * FILE NAME        	: PdaSignStatusConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.define;

/**
 * PDA签到状态常量
 * @author 038590-foss-wanghui
 * @date 2012-10-29 下午6:11:15
 */
public class PdaSignStatusConstants {

	// 已绑定
	public static final String BUNDLE = "BUNDLE";
	
	// 已解绑
	public static final String UNBUNDLE = "UNBUNDLE";
	
	/**
	 * PDA签到 用户类型 ：理货员
	 */
	public static final String USER_TYPE_TALLYPERSON = "TALLYPERSON";
	
	/**
	 * PDA签到 用户类型：司机
	 */
	public static final String USER_TYPE_DRIVER = "DRIVER";
	
	//liding add
	/**
	 * PDA签到 用户类型 ：快递员
	 */
	public static final String USER_TYPE_COURIER = "COURIER";
	/**
	 * PDA签到 用户类型 ：NCI
	 */
	public static final String USER_TYPE_NCI = "NCI_USER";
	
	/**
	 * 订单可视化 接收状态：OPEN
	 */
	public static final String DRIVER_RECEIVE_OPEN = "OPEN";
	/**
	 * 订单可视化 接收状态：STOP
	 */
	public static final String DRIVER_RECEIVE_STOP = "STOP";
	/**
	 * 订单可视化 接收状态：ALL
	 */
	public static final String DRIVER_RECEIVE_ALL = "ALL";
}