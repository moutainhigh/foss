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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/define/TaskTruckConstant.java
 *  
 *  FILE NAME          :TaskTruckConstant.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.constant
 * FILE    NAME: TaskTruckConstant.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.define;

/**
 * 任务车辆常量
 * @author dp-duyi
 * @date 2012-11-7 上午9:57:22
 */
public class TaskTruckConstant {
	
	/**车辆业务类型*/
	//派送-DELIVER
	public static final String BUSINESS_TYPE_DELIVER = "DELIVER";
	//偏线-PARTIALLINE
	public static final String BUSINESS_TYPE_PARTIALLINE = "PARTIALLINE";
	//长途装车-LONG_DISTANCE
	public static final String BUSINESS_TYPE_LONG_DISTANCE = "LONG_DISTANCE";
	//短途装车-SHORT_DISTANCE
	public static final String BUSINESS_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE";
	
	/**车辆状态*/
	//未出发-UNDEPART
	public static final String TASK_TRUCK_STATE_UNDEPART = "UNDEPART";
	//在途-ONTHEWAY
	public static final String TASK_TRUCK_STATE_ONTHEWAY = "ONTHEWAY";
	//已到达-ARRIVED
	public static final String TASK_TRUCK_STATE_ARRIVED = "ARRIVED";
	//作废-CANCLED
	public static final String TASK_TRUCK_STATE_CANCLED = "CANCLED";
	//已卸车-UNLOADED
	public static final String TASK_TRUCK_STATE_UNLOADED = "UNLOADED";
	
	/**单据级别*/
	//1-有效
	public static final String BILL_LEVEL_VALID = "1";
	//0-无效
	public static final String BILL_LEVEL_UNVALID = "0";
	
	/**单据类型*/
	//HANDOVER-交接单
	public static final String BILL_TYPE_HANDOVER = "HANDOVER";
	//VEHICLEASSEMBLE-汽运配载单
	public static final String BILL_TYPE_VEHICLEASSEMBLE = "VEHICLEASSEMBLE";
	//VEHICLEMPBILL-车辆空驶单
	public static final String BILL_TYPE_VEHICLEMPBILL = "VEHICLEMPBILL";
	
	/**单据分配状态*/
	//已分配-ASSIGNED 
	public static final String BILL_ASSIGN_STATE_ASSIGNED = "ASSIGNED";
	//未分配-UNASSIGN
	public static final String BILL_ASSIGN_STATE_UNASSIGN = "UNASSIGN";
	//卸车中-UNLOADING
	public static final String BILL_ASSIGN_STATE_UNLOADING = "UNLOADING";
	//已卸车-UNLOADED
	public static final String BILL_ASSIGN_STATE_UNLOADED = "UNLOADED";
	
	
	/**同步到gps操作类型*/
	//新增-0
	public static final int GPS_OPERATE_TYPE_NEW = 0;
	//删除-1
	public static final int GSP_OPERATE_TYPE_DELETE = 1;
	//同步同步失败的待跟踪至gps的同步条数
	public static final int GPS_SYN_COUNT = 5000;
	
	//AIR_HANDOVERTYPE-商务专递快递交接单272681
	public static final String BILL_TYPE_PACKAGE_HANDOVER = "PACKAGE_HANDOVER";
}