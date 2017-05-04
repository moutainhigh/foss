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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/orderpreprocess/server/job/PdaSignUnbundleJob.java
 * 
 * FILE NAME        	: PdaSignUnbundleJob.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.orderpreprocess.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutService;

/**
 * PDA签到解除绑定job
 * @author 038590-foss-wanghui
 * @date 2013-3-15 上午9:57:43
 */
public class PdaSignUnbundleJob extends GridJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdaSignUnbundleJob.class);
	/**
	 * 
	 * PDA自动解除司机签到
	 * @author 038590-foss-wanghui
	 * @date 2013-3-15 上午10:04:38
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		ISignInAndLogOutService signInAndLogOutService = getBean("signInAndLogOutService", ISignInAndLogOutService.class);
		LOGGER.info("自动解绑启动");
		signInAndLogOutService.autoUnbundle();
		LOGGER.info("自动解绑成功结束");
	}

}