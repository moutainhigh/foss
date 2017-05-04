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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/MonitorIndicatorService.java
 * 
 * FILE NAME        	: MonitorIndicatorService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.service.impl
 * FILE    NAME: MonitorIndicatorService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.module.base.common.api.server.service.IMonitorIndicatorService;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;


/**
 * 监控指标信息服务
 * @author ibm-zhuwei
 * @date 2013-2-18 下午2:59:00
 */
public class MonitorIndicatorService implements IMonitorIndicatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorIndicatorService.class);

	/** 
	 * 通过指标编码查询指标信息
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午2:59:42
	 * @param indicatorCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorIndicatorService#queryByIndicatorCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MonitorIndicatorEntity queryByIndicatorCode(String indicatorCode) {
		
		MonitorIndicatorEntity entity = (MonitorIndicatorEntity) 
				CacheManager.getInstance().getCache(FossTTLCache.MONITOR_INDICATOR_CACHE_UUID).get(indicatorCode);
    	
		return entity;
	}

	/** 
	 * 判断指标是否需要监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午2:59:42
	 * @param indicatorCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorIndicatorService#isMonitorIndicator(java.lang.String)
	 */
	@Override
	public boolean isMonitorIndicator(String indicatorCode) {
		
		LOGGER.debug("获取监控指标开关信息，监控编码：" + indicatorCode);
		
		MonitorIndicatorEntity entity = this.queryByIndicatorCode(indicatorCode);
		
		return entity != null && FossConstants.ACTIVE.equals(entity.getMonitorFlag());
	}

}
