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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/AdministrativeRegionCache.java
 * 
 * FILE NAME        	: AdministrativeRegionCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.cache;

import com.deppon.foss.framework.cache.DefaultStrongCache;

/**   
 * <p>
 * Description:配置省份城市缓存<br />
 * </p>
 * @title CityCache.java
 * @package com.deppon.crm.module.common.server.cache 
 * @author 毛建强
 * @version 0.1 2012-3-16
 */

@SuppressWarnings("deprecation")
public class AdministrativeRegionCache extends DefaultStrongCache<String, AdministrativeRegionCacheProvider> {
	//给其一个唯一的UUID标志
	private static final String UUID = AdministrativeRegionCacheProvider.class.getName();
	@Override
	public String getUUID() {
		return UUID;
	}

}
