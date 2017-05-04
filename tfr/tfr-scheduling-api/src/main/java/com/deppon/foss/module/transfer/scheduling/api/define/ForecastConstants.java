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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/ForecastConstants.java
 * 
 *  FILE NAME     :ForecastConstants.java
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
 * 走货路径常量容器
 * @author huyue
 * @date 2012-10-16 下午4:08:23
 */
public final class ForecastConstants {
	
	/** 货量预测状态  出发 */
	public static final String  FORECAST_DEPART = "DEPART";
	
	/** 货量预测状态  到达 */
	public static final String  FORECAST_ARRIVE = "ARRIVE";
	
	/** 货量预测合车生效状态  无效 */
	public static final String  CHANGE_QUANTITY_NOTEFFECT = "NOTEFFECT";
	
	/** 货量预测合车生效状态  有效 */
	public static final String  CHANGE_QUANTITY_EFFECT = "EFFECT";
	
	/** 国际化Exception*/
	public static final String FORECAST_TRANSFORTIME_ERROR = "foss.scheduling.forecast.transforTime";
	
	public static final String FORECAST_TIMEPOINT_MORETHANONE = "foss.scheduling.forecast.moreThanOne";
	
	public static final String FORECAST_TYPE_ERROR = "foss.scheduling.forecast.typeError";
	
	public static final String FORECAST_ACTION_ERROR = "foss.scheduling.forecast.actionError";
	
	public static final String FORECAST_ADVISEWORKNUMBER_CANTFIND = "foss.scheduling.forecast.cantFind";
	
	public static final String FORECAST_ADVISEWORKNUMBER_FORECASTTIME = "foss.scheduling.forecast.forecastTime";
	
	public static final String FORECAST_ADVISEWORKNUMBER_CANTFINDFORECASTDATA = "foss.scheduling.forecast.cantFindForecastData";
	
	/** 统计货量查询 开单未交接 */
	public static final String  FORECAST_NO_TRANSFER_BILLING = "NOTRANSFERBILLING"; 
	public static final String  FORECAST_NO_TRANSFER_BILLING_NAME = "开单未交接"; 
	/** 统计货量查询 交接未出发 */
	public static final String  FORECAST_NO_DEPARTURE_TRANSFER = "NODEPARTURETRANSFER"; 
	public static final String  FORECAST_NO_DEPARTURE_TRANSFER_NAME = "交接未出发"; 
	/** 统计货量查询 在途 */
	public static final String  FORECAST_IN_TRANSIT = "INTRANSIT"; 
	public static final String  FORECAST_IN_TRANSIT_NAME = "在途"; 
	/** 统计货量查询 到达未卸车 */
	public static final String  FORECAST_UNLOADING_NOT_REACHED = "UNLOADINGNOTREACHED"; 
	public static final String  FORECAST_UNLOADING_NOT_REACHED_NAME = "到达未卸车"; 
	/** 统计货量查询 在库 */
	public static final String  FORECAST_IN_LIBRARY = "INLIBRARY";
	public static final String  FORECAST_IN_LIBRARY_NAME = "在库";

	/** 统计货量查询 运输性质  汽运偏线 */
	//public static final String  FORECAST_PLF = "PLF";
	//public static final String  FORECAST_PLF_NAME = "汽运偏线"; 
	/** 统计货量查询 运输性质 精准卡航  */
	public static final String  FORECAST_FLF = "FLF"; 
	public static final String  FORECAST_FLF_NAME = "精准卡航"; 
	/** 统计货量查询 运输性质 精准城运 */
	public static final String  FORECAST_FSF = "FSF"; 
	public static final String  FORECAST_FSF_NAME = "精准城运"; 
	/** 统计货量查询 运输性质 精准汽运(长途) */
	public static final String  FORECAST_LRF = "LRF"; 
	public static final String  FORECAST_LRF_NAME = "精准汽运(长途)"; 
	/** 统计货量查询 运输性质 精准汽运(短途) */
	public static final String  FORECAST_SRF = "SRF"; 
	public static final String  FORECAST_SRF_NAME = "精准汽运(短途)"; 
	
}