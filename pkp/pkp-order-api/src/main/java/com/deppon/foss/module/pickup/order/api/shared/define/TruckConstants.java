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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/define/TruckConstants.java
 * 
 * FILE NAME        	: TruckConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.define;

/**
 * 走货路径常量容器
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-16 下午4:08:23
 */
public final class TruckConstants {

	/**
	 * 计划状态-可用
	 */
	public static final String SCHEDULE_STATUS_ACTIVE = "1";
	/**
	 * 计划状态-可用
	 */
	public static final String SCHEDULE_STATUS_DELETED = "DELETED";
	/**
	 * 计划类型-接送货
	 */
	public static final String SCHEDULE_TYPE_DELIVERY = "PKP";
	/**
	 * 排班状态
	 */
	public static final String PLAN_TYPE_WORK = "WORK";
	
	/**
	 * 车辆可用状态
	 */
	public static final String VEHICLENO_ACTIVE = "Y";
	
	/**
	 * 物流班车状态
	 */
	public static final String DEPART_PLAN_TYPE = "SHORT";
	
	/** 约车状态  已受理 */
	public static final String ORDERVEHICLE_STATUS_ACCEPTED = "ACCEPTED";
}