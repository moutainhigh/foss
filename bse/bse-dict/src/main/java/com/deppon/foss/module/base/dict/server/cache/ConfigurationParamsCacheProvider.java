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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/cache/ConfigurationParamsCacheProvider.java
 * 
 * FILE NAME        	: ConfigurationParamsCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * @Description: 系统配置参数 Provider
 * ProductItemCacheProvider.java Create on 2013-2-19 上午11:49:13
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ConfigurationParamsCacheProvider implements ITTLCacheProvider<ConfigurationParamsEntity> {

	//private static final Logger log = Logger.getLogger(ConfigurationParamsCacheProvider.class);

	@Inject
    private IConfigurationParamsDao configurationParamsDao;
	
	public void setConfigurationParamsDao(IConfigurationParamsDao configurationParamsDao) {
		this.configurationParamsDao = configurationParamsDao;
    }

	/**
     * @Description: 根据主键获取产品条目实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public ConfigurationParamsEntity get(String key) {
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	String[] keys = key.split("#");
    	ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setConfType(keys[0]);
		entity.setCode(keys[1]);
		entity.setOrgCode(keys[2]);
    	List<ConfigurationParamsEntity> paramsEntities = configurationParamsDao.queryConfigurationParamsExactByEntity(entity,0,1);
    	if(CollectionUtils.isNotEmpty(paramsEntities)) {
    		return paramsEntities.get(0);
    	}
    	return null;
    }

    

}
