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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/define/SealConstant.java
 *  
 *  FILE NAME          :SealConstant.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.define;
/**
 * 定义封签常量类
 * @author 038300-foss-pengzhen
 * @date 2013-1-29
 */
public class SealConstant {
	/**封签类型-已绑定 **/
	public static final String SEAL_TYPE_BIND = "BIND";

	/**封签类型-已校验 **/
	public static final String SEAL_TYPE_CHECK = "CHECK";
	
	/**封签类型-已删除 **/
	public static final String SEAL_TYPE_INVALID = "INVALID";

	/**后门封签 **/
	public static final String SEAL_TYPE_DETAIL_BACK = "BACK";

	/**侧门封签**/
	public static final String SEAL_TYPE_DETAIL_SIDE = "SIDE";
	
	/**封签**/
	public static final String SEAL_TYPE_DETAIL_NONE = "NONE";

	/**封签状态-未检查**/
	public static final String SEAL_STATE_UNCHECK = "UNCHECK";

	/**封签状态-正常**/
	public static final String SEAL_STATE_NORMAL = "NORMAL";

	/**封签状态-异常**/
	public static final String SEAL_STATE_EXCEPTION = "EXCEPTION";

	/**封签状态-破损**/
	public static final String SEAL_STATE_DAMAGED = "DAMAGED";
	
	/**车辆出发状态-未出发**/
	public static final String SEAL_TRUCK_STATUS_UNDEPART = "UNDEPART";

	/**车辆出发状态-在途**/
	public static final String SEAL_TRUCK_STATUS_ONTHEWAY = "ONTHEWAY";

	/**车辆出发状态-中途到达**/
	public static final String SEAL_TRUCK_STATUS_HALFWAY_ARRIVE = "HALFWAY_ARRIVE";

	/**车辆出发状态-已到达**/
	public static final String SEAL_TRUCK_STATUS_ARRIVED = "ARRIVED";
	
	/**车辆出发状态-已卸车**/
	public static final String SEAL_TRUCK_STATUS_UNLOADED = "UNLOADED";

	/**车辆出发状态-作废**/
	public static final String SEAL_TRUCK_STATUS_CANCLED = "CANCLED";
	
	/**逗号","**/
	public static final char SEAL_DTO = ',';
	
	/**封签绑定或校验的方式 FOSS**/
	public static final String SEAL_TYPE_FOSS = "FOSS";
	
	/**封签绑定或校验的方式 PDA手输**/
	public static final String SEAL_TYPE_PDA_HAND = "BY_HAND";
	
	/**封签绑定或校验的方式 PDA扫描**/
	public static final String SEAL_TYPE_PDA_SCAN = "SCANED";
}