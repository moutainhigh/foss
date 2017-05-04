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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/define/ArrivalConstant.java
 *  
 *  FILE NAME          :ArrivalConstant.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.define;
/**
 * 
 * 到达的常量定义
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:53:04
 */
public class ArrivalConstant{

	/*********** 到达状态 *************/
	// 已到
	public static final String DEPART_ARRIVALED = "ARRIVED";
	// 未到
	public static final String DEPART_NOT_ARRIVALED = "NOT_ARRIVED";
	// 晚到
	public static final String DEPART_LATE_ARRIVALED = "LATE_ARRIVED";
	// 取消
	public static final String DEPART_LATE_CANCLED = "4";
	/**全部**/
	public static final String ALL = "ALL";

	/*********** 车辆状态 *************/
	// 待出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED
	public static final String ARRIVAL_VEHICLE_UNDEPART = "UNDEPART";
	public static final String ARRIVAL_VEHICLE_ONTHEWAY = "ONTHEWAY";
	public static final String ARRIVAL_VEHICLE_ARRIVED = "ARRIVED";
	public static final String ARRIVAL_VEHICLE_CANCLED = "CANCLED";
	/**
	 * 已卸车-UNLOADED
	 */
	public static final String ARRIVAL_VEHICLE_UNLOADED = "UNLOADED";
	
	/************分配情况*********/
	//已分配，未分配
	public static final String ALLOCATIONED = "已分配";
	public static final String NOT_ALLOCATIONED = "未分配";
	public static final String ALLOCATIONED_BAG = "已上交";
	public static final String NOT_ALLOCATIONED_BAG = "未上交";
	
	/*************放行类型名称**********/
	public static final String DEPART_TYPE_NAME_GPS = "(GPS)";
	public static final String DEPART_TYPE_NAME_PDA = "(PDA)";
	public static final String DEPART_TYPE_NAME_MANUAL = "(手工放行)";
	public static final String DEPART_TYPE_NAME_DEPART_CONFIRM = "(出发确认)";
	public static final String ARRIVE_TYPE_NAME_MANUAL = "(到达确认)";
	

	/*********** 车型*************/
	public static final String VEHICLE_LENGTH_FOUR_POINT_TWO = "4.2";
	public static final String VEHICLE_LENGTH_SIX_POINT_FIVE = "6.5";
	public static final String VEHICLE_LENGTH_SEVEN_POINT_SIX = "7.6";
	public static final String VEHICLE_LENGTH_NINE_POINT_SIX = "9.6";
	public static final String VEHICLE_LENGTH_SEVENTEEN_POINT_FIVE = "17.5";
	public static final String VEHICLE_LENGTH_COMMA = "/";

	/*********** 停用车型是否有效*************/
	public static final String VEHICLE_IS_ACTIVE = "Y";
	
	/*********** 显示空月台*************/
	public static final String VEHICLE_SHOW_EMPTY_PLATFORM = "1";
	
}