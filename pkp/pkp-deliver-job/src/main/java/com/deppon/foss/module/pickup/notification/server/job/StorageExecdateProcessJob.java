/**
 *  initial comments.
 */
/**
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-job
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/notification/server/job/StorageExecdateProcessJob.java
 * 
 * FILE NAME        	: StorageExecdateProcessJob.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.notification.server.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IShareJobService;

/**
 * 
 * 仓储费日期执行表记录操作JOB
 * 
 * @author ibm-wangfei
 * @date Nov 20, 2012 3:55:43 PM
 */
public class StorageExecdateProcessJob extends GridJob implements StatefulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageExecdateProcessJob.class);

	/**
	 * 仓储费日期执行表记录操作
	 * 
	 * @param arg0
	 * @throws JobExecutionException
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 4:40:52 PM
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			IShareJobService shareJobService = getBean("shareJobService", IShareJobService.class);
			// 仓储费日期执行表记录操作生成服务
			LOGGER.info("StorageExecdateProcessJob begin");
			// 仓储费日期执行表记录操作入口
			shareJobService.executeStorageExecdate();
			LOGGER.info("StorageExecdateProcessJob End");
		} catch (Exception e) {
			LOGGER.error("仓储费日期执行表记录操作JOB error", e);
			throw new JobExecutionException("仓储费日期执行表记录操作JOB error", e);
		}
	}
}