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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/BorrowVehicleConstants.java
 * 
 *  FILE NAME     :BorrowVehicleConstants.java
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
 * FILE    NAME: OrderVehicleConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.define;

/**
 * 借车常量容器
 * @author 104306-foss-wangLong
 * @date 2012-10-16 下午4:08:23
 */
public final class BorrowVehicleConstants {
	
	/** 借车状态  暂存 */
	public static final String BORROWVEHICLE_STATUS_STAGING = "STAGING";
	
	/** 借车状态  未审核  */
	public static final String BORROWVEHICLE_STATUS_UNAPPROVED = "UNAPPROVED";
	
	/** 借车状态  已受理 */
	public static final String BORROWVEHICLE_STATUS_ACCEPTED = "ACCEPTED";
	
	/** 借车状态  已拒绝 */
	public static final String BORROWVEHICLE_STATUS_DISMISS = "DISMISS";
	
	/** 借车状态  已退回 */
	public static final String BORROWVEHICLE_STATUS_RETURN = "RETURN";
	
	/** 借车状态  已到达 */
	public static final String BORROWVEHICLE_STATUS_VERIFY_ARRIVE  = "VERIFY_ARRIVE";
	
	/** 借车状态  已撤销 */
	public static final String BORROWVEHICLE_STATUS_UNDO = "UNDO";
	
	/** 借车状态  车辆归还 */
	public static final String BORROWVEHICLE_STATUS_GIVE_BACK = "GIVE_BACK";

	/** 借车使用类型  班车 */
	public static final String BORROWVEHICLE_USE_TYPE_REGULAR = "REGULAR";
	
	/** 借车使用类型  接送货 */
	public static final String BORROWVEHICLE_USE_TYPE_PICK_GOODS = "PICK_GOODS";
	
	/** 异常  */
	public static final String BORROWVEHICLE_PARAMETERERROR_MESSAGE = "foss.scheduling.borrowVehicle.parameterException";
	
	private BorrowVehicleConstants() {
		
	}
}