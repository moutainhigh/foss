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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/exception/AssignLoadTaskExceptionCode.java
 *  
 *  FILE NAME          :AssignLoadTaskExceptionCode.java
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
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.exception
 * FILE    NAME: AssignLoadTaskExceptionCode.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.exception;

/**
 * AssignLoadTaskExceptionCode
 * @author dp-duyi
 * @date 2012-10-15 上午8:58:23
 */
public class AssignLoadTaskExceptionCode {
	/**没有查询到记录*/
	public static final String DATA_NOTFIND_MESSAGECODE = "foss.load.assignLoadTask.errorCode.dataNotFindMessage";
	/**参数错误*/
	public static final String PARAMETERERROR_MESSAGECODE = "foss.load.assignLoadTask.errorCode.parameterErrorMessage";
	/**该任务已被取消*/
	public static final String TASKBECENCELED_MESSAGECODE = "foss.load.assignLoadTask.errorCode.taskBeCanceled";
	/**该任务已执行不能取消*/
	public static final String TASKBESTARTED_MESSAGECODE = "foss.load.assignLoadTask.errorCode.taskBeStarted";
	/**派送单已分配*/
	public static final String DELIVERBILLBEASSIGNED_MESSAGECODE = "foss.load.assignLoadTask.errorCode.deliverBillBeAssigned";
	/**派送单状态有误，请刷新派送单列表后重新操作！*/
	public static final String DELIVERBILLSTATEWRONG_MESSAGECODE = "foss.load.assignLoadTask.errorCode.deliverBillStateWrong";
	private AssignLoadTaskExceptionCode() {
	}
}