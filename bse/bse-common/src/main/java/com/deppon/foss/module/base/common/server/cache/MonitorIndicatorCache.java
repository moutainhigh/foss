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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/cache/MonitorIndicatorCache.java
 * 
 * FILE NAME        	: MonitorIndicatorCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.cache
 * FILE    NAME: MonitorIndicatorCache.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.cache;

import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.util.common.FossTTLCache;


/**
 * 监控指标缓存
 * @author ibm-zhuwei
 * @date 2013-2-18 下午3:02:09
 */
public class MonitorIndicatorCache extends FossTTLCache<MonitorIndicatorEntity> {

	/** 
	 * 获取监控缓存UUID
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午3:03:03
	 * @return
	 * @see com.deppon.foss.framework.cache.ICache#getUUID()
	 */
	@Override
	public String getUUID() {
		return MONITOR_INDICATOR_CACHE_UUID;
	}

}
