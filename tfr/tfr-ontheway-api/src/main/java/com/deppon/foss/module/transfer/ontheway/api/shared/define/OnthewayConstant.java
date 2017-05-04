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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/define/OnthewayConstant.java
 *  
 *  FILE NAME          :OnthewayConstant.java
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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 在途常量定义
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午9:19:04
 */
public class OnthewayConstant
{

	/*********** 跟踪方式 *************/
	// 手动跟踪-HANDLE，GPS跟踪-GPS
	public static final String ONTHEWAY_TRACKING_TYPE_HANDLE = "HANDLE";
	public static final String ONTHEWAY_TRACKING_TYPE_GPS = "GPS";
	
	/*********** 是否最后一次跟踪 *************/
	// 是-Y，不是-N
	public static final String IS_THE_LATEST_TRACKING = "Y";
	public static final String IS_NOT_THE_LATEST_TRACKING = "N";
	
	/*********** 当前状态 *************/
	// 运行-RUN，停止-STOP，事故车辆-ACCIDENTS，离线-OFFLINE
	public static final String TRUCK_CURRENT_STATUS_RUN = "RUN";
	public static final String TRUCK_CURRENT_STATUS_STOP = "STOP";
	public static final String TRUCK_CURRENT_STATUS_ACCIDENTS = "ACCIDENTS";
	public static final String TRUCK_CURRENT_STATUS_OFFLINE = "OFFLINE";

	/*********** 运行的界面显示 *************/
	public static final String TRUCK_CURRENT_RUNNING = "运行";
	public static final String TRUCK_CURRENT_RUNNING_AND_SPEEN= "KM(运行)";
	
	public final static Map<String, String> CURRENT_STATUS_MAP = new HashMap<String, String>();
	static {
		CURRENT_STATUS_MAP.put("RUN", "运行");
		CURRENT_STATUS_MAP.put("STOP", "停止");
		CURRENT_STATUS_MAP.put("ACCIDENTS", "事故车辆");
		CURRENT_STATUS_MAP.put("OFFLINE", "离线");
		CURRENT_STATUS_MAP.put("TRAFFIC", "堵车");
		CURRENT_STATUS_MAP.put("SLOWRUN", "缓行");
		CURRENT_STATUS_MAP.put("DETAINING", "扣车");
		CURRENT_STATUS_MAP.put("OTHERS", "其他");
	}
}