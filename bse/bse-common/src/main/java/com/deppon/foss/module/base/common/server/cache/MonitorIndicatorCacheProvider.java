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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/cache/MonitorIndicatorCacheProvider.java
 * 
 * FILE NAME        	: MonitorIndicatorCacheProvider.java
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
 * FILE    NAME: MonitorIndicatorCacheProvider.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorIndicatorEntityDao;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;


/**
 * 监控指标信息缓存
 * @author ibm-zhuwei
 * @date 2013-2-18 下午3:02:20
 */
public class MonitorIndicatorCacheProvider implements ITTLCacheProvider<MonitorIndicatorEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorIndicatorCacheProvider.class);

	/**
	 * 监控指标信息DAO
	 */
	private IMonitorIndicatorEntityDao monitorIndicatorEntityDao;

	/** 
	 * 监控指标信息数据库读取
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午3:11:57
	 * @param arg0
	 * @return
	 * @see com.deppon.foss.framework.cache.provider.ITTLCacheProvider#get(java.lang.String)
	 */
	@Override
	public MonitorIndicatorEntity get(String code) {
		
		LOGGER.debug("query database monitor indicator: " + code);
		
		MonitorIndicatorEntity  entity = monitorIndicatorEntityDao.queryByCode(code);
		
		return entity;
	}

	
	/**
	 * @param monitorIndicatorEntityDao
	 */
	public void setMonitorIndicatorEntityDao(
			IMonitorIndicatorEntityDao monitorIndicatorEntityDao) {
		this.monitorIndicatorEntityDao = monitorIndicatorEntityDao;
	}

}
