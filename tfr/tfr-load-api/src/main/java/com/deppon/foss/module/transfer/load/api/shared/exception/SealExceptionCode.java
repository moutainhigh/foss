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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/exception/SealExceptionCode.java
 *  
 *  FILE NAME          :SealExceptionCode.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.exception;

/**
 * @author ibm-zhangyixin
 * @date 2012-10-24 上午8:58:23
 */
public class SealExceptionCode {
	/**
	 * 根据车牌号, 查询不到相应的记录
	 */
	public static final String DATA_NOTFIND_BY_VEHICLENO_MESSAGECODE = "foss.load.seal.errorCode.dataNotFindByVehicleNoMessage";

	/**
	 * 根据车牌号找不到任务车辆明细
	 */
	public static final String COULD_NOT_FIND_TRUCK_TASK_DETAIL_BY_VEHICLENO_MESSAGECODE = "foss.load.seal.errorCode.couldNotFindTruckTaskDetailByVehicleNoMessage";
}