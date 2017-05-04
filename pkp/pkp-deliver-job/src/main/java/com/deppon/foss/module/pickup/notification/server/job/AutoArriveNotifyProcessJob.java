/**
 *  initial comments.
 */
/*******************************************************************************
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/notification/server/job/SyncSmsProcessJob.java
 * 
 * FILE NAME        	: SyncSmsProcessJob.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.notification.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybillArriveTempService;

/**
 * 
 * 自动通知JOB
 * 
 * @author ibm-wangfei
 * @date 2012-11-28 15:07:03
 */
public class AutoArriveNotifyProcessJob extends GridJob implements StatefulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoArriveNotifyProcessJob.class);

	/**
	 * 
	 * 调用短信同步方法
	 * @param arg0
	 * @throws JobExecutionException
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 4:41:05 PM
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			LOGGER.info("AutoArriveNotifyProcessJob begin");
			IWaybillArriveTempService waybillArriveTempService = getBean("waybillArriveTempService", IWaybillArriveTempService.class);
			// 调用短信同步方法
			waybillArriveTempService.autoNotify();
			LOGGER.info("AutoArriveNotifyProcessJob End");
		} catch (Exception e) {
			LOGGER.error("自动通知JOB error", e);
			throw new JobExecutionException("自动通知JOB error", e);
		}
	}
}