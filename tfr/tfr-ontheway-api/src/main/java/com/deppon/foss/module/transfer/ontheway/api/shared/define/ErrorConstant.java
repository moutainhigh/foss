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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/define/ErrorConstant.java
 *  
 *  FILE NAME          :ErrorConstant.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.shared.define;

public class ErrorConstant
{
	
	/*********** 返回错误值 *************/
	//已到跟晚到的记录，不允许发车确认
	public static final String ARRIVAL_TASK_NOT_DEPART = "0";
	
	//只能对待出发的车辆进行发车确认
	public static final String ARRIVAL_ONLY_CONFIRM_WAIT = "1";
	
	//放行车辆的出发/到达部门不能为空
	public static final String DEPART_ORG_NOT_NULL="放行车辆的出发/到达部门不能为空";
	
	//只能操作目标部门是当前部门的任务
	public static final String THIS_ORG_HAS_NOT_OWN="只能操作目标部门是当前部门的任务";
	
}