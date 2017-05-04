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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/cache/ConfigurationParamsCache.java
 * 
 * FILE NAME        	: ConfigurationParamsCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.cache;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * @Description: 系统配置参数 Cache
 * ConfigurationParamsCache.java Create on 2013-2-21 下午6:11:35
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ConfigurationParamsCache extends FossTTLCache<ConfigurationParamsEntity> {
 
    @Override
    public String getUUID() {
    	return CONFIGURATION_PARAMS_CACHE_UUID;
    }
}
