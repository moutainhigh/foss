/**
 *  initial comments
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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/platform/api/define/ForecastJOBConstants.java
 * 
 *  FILE NAME     :ForecastJOBConstants.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 2014-03-19 14:30:01
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/

package com.deppon.foss.module.transfer.platform.api.shared.define;

public interface ForecastJOBConstants {

	/**
	 * 常量
	 */
	static final int three = 3;

	/**
	 * 为了防止BigDecimal除出来的小数为无穷大 所以 在非汇总(为指定精度)的时候截取10位小数 PS : 截取高的精度是为了减小计算误差
	 */
	static final int ten = 10;
	/**
	 * 常量
	 */
	static final int ton = 1000;
	/**
	 * 出发部门
	 */
	static final String origOrg = "origOrgCode";
	/**
	 * 目的部门
	 */
	static final String objective = "objectiveOrgCode";
	/**
	 * countStartTime 小于等于 MODIFY_START_TIME(调整出发时间)
	 */
	static final String countStartTime = "countStartTime";
	/**
	 * countEndTime 大于 MODIFY_START_TIME(调整出发时间)
	 */
	static final String countEndTime = "countEndTime";
	/**
	 * 抵达/离开
	 */
	static final String arriveOrLeave = "arriveOrLeave";

}