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
 * PROJECT NAME	: bse-dict
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/cache/ConfigurationParamsCacheDeal.java
 * 
 * FILE NAME        	: ConfigurationParamsCacheDeal.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.cache;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;

/**
 * 
 * @Description: 系统配置参数Cache处理
 * ProductItemCacheDeal.java Create on 2013-2-19 上午11:48:49
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ConfigurationParamsCacheDeal {
	
	private ConfigurationParamsCache configurationParamsCache;

	public void setConfigurationParamsCache(ConfigurationParamsCache configurationParamsCache) {
		this.configurationParamsCache = configurationParamsCache;
	}

	public ConfigurationParamsCache getConfigurationParamsCache() {
		return configurationParamsCache;
	}


	/**
	 * 
	 * @Description: 根据系统配置参数CODE从缓存获取实体
	 * @author FOSSDP-sz
	 * @date 2013-2-1 下午5:28:35
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public ConfigurationParamsEntity getConfigurationParamsEntityByCache(String code) {
		return configurationParamsCache.get(code);
	}

}
