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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/notification/server/job/WaybillRfcTodoJob.java
 * 
 * FILE NAME        	: WaybillRfcTodoJob.java
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcJobService;

/**
 * 
 *1 1）系统生成待办事项时货物未入受理部门库存，若（当前时间-货物入库时间）>X，即为处理超时，系统记录处理超时次数；
 * 2）系统生成待办事项时货物已入受理部门库存，若（当前时间-生成待办事项时间）>X，即为处理超时，系统记录处理超时次数。
 *2.对于处理超时的运单变更，系统每隔Y时间发送一条催促信息给处理部门；
 *3.X与Y的值可在系统中进行配置；
 *4.待办事项处理后，系统不再提示催促信息。
 * 
 * @author 102246-foss-shaohongliang
 * @date 2013-2-22 下午3:27:41
 */
public class WaybillRfcTodoJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillRfcTodoJob.class);

	/**
	 * 
	 * 更改单处理代办超时提醒
	 * @author 102246-foss-shaohongliang
	 * @date 2013-2-22 下午3:26:18
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			LOGGER.info("WaybillRfcTodoJob begin");
			// 运单到达数量、到达时间临时表Service
			IWaybillRfcJobService waybillRfcJobService = getBean("waybillRfcJobService", IWaybillRfcJobService.class);
			// 执行计算
			waybillRfcJobService.prepareSendMsg();
			LOGGER.info("WaybillRfcTodoJob end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("更改单处理代办超时提醒异常", e);
		}
	}
	
}