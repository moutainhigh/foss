/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/exception/MessageType.java
 * 
 * FILE NAME        	: MessageType.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.exception;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:操作提示类型类</small></b> </br> 
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> 
 * <b style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%">
 * 1 2012-11-07 张斌    新增
 * </div>
 ******************************************** 
 */
public class MessageType {


	/**
	 * 保存成功
	 */
	public static final String SAVE_SUCCESS = "foss.dict.saveSuccess";
	/**
	 * 新增词条成功
	 */
	public static final String SAVE_DATADICTIONARY_SUCCESS = "foss.dict.saveDataDictionarySuccess";
	
	/**
	 * 新增值成功
	 */
	public static final String SAVE_DATADICTIONARYVALUE_SUCCESS = "foss.dict.saveDataDictionaryValueSuccess";
	
	/**
	 * 激活成功
	 */
	public static final String ACTIVE_SUCCESS = "foss.dict.activeSuccess";


	/**
	 * 作废成功
	 */
	public static final String DELETE_SUCCESS = "foss.dict.deleteSuccess";

	/**
	 * 作废词条成功
	 */
	public static final String DELETE_DATADICTIONARY_SUCCESS = "foss.dict.deleteDataDictionarySuccess";
	
	/**
	 * 作废值成功
	 */
	public static final String DELETE_DATADICTIONARYVALUE_SUCCESS = "foss.dict.deleteDataDictionaryValueSuccess";
	
	/**
	 * 作废参数配置成功ConfigurationParams
	 */
	public static final String DELETE_CONFIGURATIONPARAMS_SUCCESS = "foss.dict.deleteConfigurationParamsSuccess";
	
	/**
	 * 更新参数配置成功
	 */
	public static final String UPDATE_CONFIGURATIONPARAMS_SUCCESS = "foss.dict.updateConfigurationParamsSuccess";
	
	/**
	 * 新增参数配置成功foss.dict.saveConfigurationParamsValueSuccess
	 */
	public static final String SAVE_CONFIGURATIONPARAMS_SUCCESS = "foss.dict.saveConfigurationParamsSuccess";

	/**
	 * 更新成功
	 */
	public static final String UPDATE_SUCCESS = "foss.dict.updateSuccess";
	
	/**
	 * 更新词条成功
	 */
	public static final String UPDATE_DATADICTIONARY_SUCCESS = "foss.dict.updateDataDictionarySuccess";
	
	/**
	 * 更新值成功
	 */
	public static final String UPDATE_DATADICTIONARYVALUE_SUCCESS = "foss.dict.updateDataDictionaryValueSuccess";
}
