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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/vo/ConfigurationParamsVo.java
 * 
 * FILE NAME        	: ConfigurationParamsVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;

/**
 * Vo
 * @author 078838-dp-zhangbin
 * @date 2012-11-16 下午8:43:48
 */
public class ConfigurationParamsVo implements Serializable{
    private static final long serialVersionUID = -3967231350132228718L;
    //配置参数实体
    private ConfigurationParamsEntity configurationParamsEntity;
    
    //配置参数实体List
    private List<ConfigurationParamsEntity> configurationParamsEntityList;
    
    //批量删除配置参数的虚拟编码
    private List<String> configurationParamsVirtualCodeList;

	public List<String> getConfigurationParamsVirtualCodeList() {
		return configurationParamsVirtualCodeList;
	}

	public void setConfigurationParamsVirtualCodeList(
			List<String> configurationParamsVirtualCodeList) {
		this.configurationParamsVirtualCodeList = configurationParamsVirtualCodeList;
	}

	public ConfigurationParamsEntity getConfigurationParamsEntity() {
		return configurationParamsEntity;
	}

	public void setConfigurationParamsEntity(
			ConfigurationParamsEntity configurationParamsEntity) {
		this.configurationParamsEntity = configurationParamsEntity;
	}

	public List<ConfigurationParamsEntity> getConfigurationParamsEntityList() {
		return configurationParamsEntityList;
	}

	public void setConfigurationParamsEntityList(
			List<ConfigurationParamsEntity> configurationParamsEntityList) {
		this.configurationParamsEntityList = configurationParamsEntityList;
	}
    
    
}
