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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/define/ErrorConstant.java
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
package com.deppon.foss.module.transfer.departure.api.shared.define;


public class ErrorConstant{
	/********pda检查车辆放行时输入的条件不能为空********/
	public static final String PDA_DEPART_CONDITION_NOT_NULL = "输入的条件不能为空值";

	/********pda检查车辆放行时输入的条件不正确（未找到放行信息）********/
	public static final String PDA_DEPART_RESULT_NOT_FIND = "未查到放行记录，该车辆不能放行";
	
	/********pda检查车辆放行时输入的条件不正确（未找到放行信息）********/
	public static final String PDA_ARRIVAL_RESULT_NOT_FIND = "未查到放行记录，该车辆不能到达";
	/********未找到车牌号对应的司机********/
	public static final String SEARCH_DRIVER_FAIL = "未找到车牌号对应的司机";
	
	/********车牌号不能为空********/
	public static final String VEHICLE_NO_IS_NOT_NULL = "车牌号不能为空";
	
	/********调用接口时传入对象不能为空********/
	public static final String SEND_OBJECT_IS_NOT_NULL = "传入对象不能为空";
	
	/********请输入必填的条件********/
	public static final String INPUT_CENTAIN_CONDITION = "请输入必填的条件";

}