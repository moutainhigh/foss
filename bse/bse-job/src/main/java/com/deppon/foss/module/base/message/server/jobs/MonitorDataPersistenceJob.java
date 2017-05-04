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
 * PROJECT NAME	: bse-job
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/message/server/jobs/MonitorDataPersistenceJob.java
 * 
 * FILE NAME        	: MonitorDataPersistenceJob.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-job
 * PACKAGE NAME: com.deppon.foss.module.base.message.server.jobs
 * FILE    NAME: MonitorDataPersistenceJob.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.message.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorProcessService;


/**
 * 监控数据持久化任务
 * @author ibm-zhuwei
 * @date 2013-2-18 下午4:31:31
 */
public class MonitorDataPersistenceJob extends GridJob implements StatefulJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorDataPersistenceJob.class);
	
	/** 
	 * 监控数据持久化任务执行入口
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午4:32:23
	 * @param context
	 * @throws JobExecutionException
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		try {
			LOGGER.info("监控数据持久化处理开始...");
			
			IBusinessMonitorProcessService businessMonitorProcessService = getBean("businessMonitorProcessService", IBusinessMonitorProcessService.class);
			businessMonitorProcessService.processBusinessMonitor();
			
			LOGGER.info("监控数据持久化处理结束...");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
		
		
	}

}
