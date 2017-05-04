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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/exception/AssignUnloadTaskExceptionCode.java
 *  
 *  FILE NAME          :AssignUnloadTaskExceptionCode.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.exception
 * FILE    NAME: AssignUnloadTaskExceptionCode.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.exception;

/**
 * AssignUnloadTaskExceptionCode
 * @author dp-duyi
 * @date 2012-10-24 下午4:54:25
 */
public class AssignUnloadTaskExceptionCode {
	//没有查询到记录
		public static final String DATA_NOTFIND_MESSAGECODE = "foss.unload.assignunloadtask.dataNotFind";
		//参数错误
		public static final String PARAMETERERROR_MESSAGECODE = "foss.unload.assignunloadtask.PARAMETERERROR_MESSAGECODE";
		//非法月台
		public static final String INVALID_PLATEFORM_MESSAGECODE = "foss.unload.assignunloadtask.invalidPlatform";
		//该任务已被取消
		public static final String TASKBECENCELED_MESSAGECODE = "foss.unload.assignunloadtask.TASKBECENCELED_MESSAGECODE";
		//该任务已执行不能取消
		public static final String TASKBESTARTED_MESSAGECODE = "foss.unload.assignunloadtask.TASKBESTARTED_MESSAGECODE";
		//该任务已被分配
		public static final String BILLBEASSIGNED_MESSAGECODE = "foss.unload.assignunloadtask.BILLBEASSIGNED_MESSAGECODE";
		//该车辆有未完成任务
		public static final String VEHICLE_MESSAGECODE = "foss.unload.assignunloadtask.VEHICLE_MESSAGECODE";
		private AssignUnloadTaskExceptionCode(){
			
		}
}