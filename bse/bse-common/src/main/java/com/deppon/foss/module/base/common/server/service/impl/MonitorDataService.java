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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/MonitorDataService.java
 * 
 * FILE NAME        	: MonitorDataService.java
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
 * FILE    NAME: MonitorDataService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.base.common.api.server.dao.IMonitorDataEntityDao;
import com.deppon.foss.module.base.common.api.server.service.IMonitorDataService;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorDataEntity;
import com.deppon.foss.module.base.common.api.shared.exception.BusinessMonitorException;


/**
 * 监控数据服务
 * @author ibm-zhuwei
 * @date 2013-2-2 上午9:15:55
 */
public class MonitorDataService implements IMonitorDataService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorDataService.class);
	
	private IMonitorDataEntityDao monitorDataEntityDao;

	/** 
	 * 批量新增监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-2 上午9:17:43
	 * @param list
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorDataService#batchAddMonitorData(java.util.List)
	 */
	@Override
	public void batchAddMonitorData(List<MonitorDataEntity> list) {
		
		LOGGER.info("add monitor data");
		for (MonitorDataEntity entity : list) {
			LOGGER.info(ToStringBuilder.reflectionToString(entity));
		}
		
		// 输入参数校验
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessMonitorException("输入参数为空");
		}
		
		monitorDataEntityDao.batchAddMonitorData(list);
	}

	/** 
	 * 批量删除监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-2 上午9:17:43
	 * @param monitorDate
	 * @param orgCode
	 * @param indicatorCodes
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorDataService#batchDeleteMonitorData(java.util.Date, java.lang.String)
	 */
	@Override
	public void batchDeleteMonitorData(Date monitorDate, String monitorTimeRange, String orgCode, List<String> indicatorCodes) {
		
		// 输入参数校验
		if (monitorDate == null || StringUtils.isEmpty(orgCode)) {
			throw new BusinessMonitorException("输入参数为空");
		}
		
		LOGGER.info("begin delete monitor data, monitorDate: " + monitorDate + " , orgCode: " + orgCode);
		
		monitorDataEntityDao.batchDeleteMonitorData(monitorDate, monitorTimeRange, orgCode, indicatorCodes);
		
	}

	
	/**
	 * @param monitorDataEntityDao
	 */
	public void setMonitorDataEntityDao(IMonitorDataEntityDao monitorDataEntityDao) {
		this.monitorDataEntityDao = monitorDataEntityDao;
	}

}
