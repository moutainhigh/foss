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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/define/TaskProgressConstants.java
 *  
 *  FILE NAME          :TaskProgressConstants.java
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
 * FILE    NAME: TaskProgressConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.define;

/**
 * 任务进度常量类
 * @author 046130-foss-xuduowei
 * @date 2012-11-26 下午5:14:30
 */
public class TaskProgressConstants {
	//派送装车
	public final static String DELIVER_LOAD = "派送装车";
	//偏线装车
	public final static String PARTIALLINE_LOAD = "偏线装车";
	//长途装车
	public final static String LONG_DISTANCE_LOAD = "长途装车";
	//短途装车
	public final static String SHORT_DISTANCE_LOAD = "短途装车";
	//装车
	public final static String LOAD = "装车";
	//卸车
	public final static String UNLOAD = "卸车";
	//装车 进行中
	public final static String LOADING = "LOADING";
	//卸车 进行中
	public final static String UNLOADING = "UNLOADING";
	//已完成
	public final static String FINISHED = "FINISHED";
	//已提交
	public final static String SUBMITED = "SUBMITED";
	//已取消
	public final static String CANCELED = "CANCELED";
	
	/**装车进度常量*/
	//按建立任务时间降序
	public static final String ORDER_BY_CREATETASKDATE_DESC = "ORDER_BY_CREATETASKDATE_DESC";
	//按建立任务时间升序
	public static final String ORDER_BY_CREATETASKDATE_ASC = "ORDER_BY_CREATETASKDATE_ASC";
	//按计划发车时间降序
	public static final String ORDER_BY_PLANDEPARTDATE_DESC = "ORDER_BY_PLANDEPARTDATE_DESC";
	//按计划发车时间升序
	public static final String ORDER_BY_PLANDEPARTDATE_ASC = "ORDER_BY_PLANDEPARTDATE_ASC";
	//按已装重量降序
	public static final String ORDER_BY_LOADEDWEIGHT_DESC = "ORDER_BY_LOADEDWEIGHT_DESC";
	//按已装重量升序
	public static final String ORDER_BY_LOADEDWEIGHT_ASC = "ORDER_BY_LOADEDWEIGHT_ASC";
	//按已装体积降序
	public static final String ORDER_BY_LOADVOLUME_DESC = "ORDER_BY_LOADVOLUME_DESC";
	//按已装体积升序
	public static final String ORDER_BY_LOADVOLUME_ASC = "ORDER_BY_LOADVOLUME_ASC";
	//百分比模式
	public static final String QUERY_LOAD_PROGRESS_PERCENT = "##%";

}