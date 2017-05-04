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
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.google.inject.Inject;
import com.kp.persistance.types.Date;

public class FullFreightRouteCacheProvider implements ITTLCacheProvider<List<FreightRouteLineDto>> {

	@Inject
	private IFreightRouteService freightRouteService;

	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	@Override
	public List<FreightRouteLineDto> get(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		String[] keys = key.split("#");

		String origCode = keys[0];
		String destCode = keys[1];
		String productCode = keys[2];
		return freightRouteService.queryFreightRouteBySourceTarget(origCode, destCode, productCode, new Date());
	}

}