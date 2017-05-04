/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/PlatformDispatchConstants.java
 * 
 *  FILE NAME     :PlatformDispatchConstants.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.define
 * FILE    NAME: PlatformDispatchConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.define;

/**
 *  月台分配使常量容器
 * @author 104306-foss-wangLong
 * @date 2012-11-8 上午9:19:17
 */
public final class PlatformDispatchConstants {
	
	/** 月台任务状态    未使用， 计划停靠    */
	public static final String PLATFORMDISPATCH_STATUS_AVAILABLE = "AVAILABLE";
	
	/** 月台任务状态    结束使用    清空月台之后更新  */
	public static final String PLATFORMDISPATCH_STATUS_DISABLED  = "DISABLED";
	
	/** 月台任务状态   正在使用  */
	public static final String PLATFORMDISPATCH_STATUS_USING   = "USING";
	
	/** 月台停靠类型   计划 */
	public static final String PLATFORMDISPATCH_TYPE_PLAN = "PLAN";
	
	/** 月台停靠类型   当前停靠 */
	public static final String PLATFORMDISPATCH_TYPE_ACTUALUSE = "ACTUALUSE";
	
	/** 月台信息来源   发车计划 */
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE_STARTSCHEDULE = "STARTSCHEDULE";
	
	/** 月台信息来源   卸货 */
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE_UNLOAD = "UNLOAD";
	
	/** 月台信息来源   装货 */
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE_LOAD = "LOAD";
	
	/** 月台信息来源   手动新增 */
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE_HANDADD = "HANDADD";

	/** 月台信息来源   车辆到达 */
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE_ARRIVAL = "ARRIVAL";

	/**  参数错误. */
	public static final String PLATFORMDISPATCH_PARAMETERERROR_MESSAGE = "foss.scheduling.platformdispatch.parameterException";
	
	/** 默认 计算最优月台货物取值百分比 */
	public static final double DEFAULT_OPTIMAL_PLATFORM_GOODS_PERCENT = 0.8;
	
	/** 默认  最优月台个数 */
	public static final int DEFAULT_OPTIMAL_PLATFORM_RESULT_COUNT = 5;
	
	public static final String COMPANY = "Company";
	public static final String LEASED = "Leased";
	
	private PlatformDispatchConstants() {
		
	}
}