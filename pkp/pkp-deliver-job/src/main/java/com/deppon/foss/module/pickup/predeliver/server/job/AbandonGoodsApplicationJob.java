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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/job/AbandonGoodsApplicationJob.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationJob.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.framework.server.components.logger.ILogBuffer;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;

/**
 * 预弃货信息Job
 * 
 * @date 2012-11-27 下午5:27:29
 */
public class AbandonGoodsApplicationJob extends GridJob implements StatefulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(AbandonGoodsApplicationJob.class);

	/**
	 * 1 系统每天晚上12点自动转换，将派送异常运单对应的运单号、始发部门、发货人、 发货人手机、体积、入库时间、仓储时长转换成预弃货信息，
	 * 同时操作人，操作人为系统管理员账户名。
	 * 
	 * 2 入库时间离当前时间如果超过“3”个月，则系统后台自动转成预弃货信息。 此处具体弃货时长可以进行人工配置。
	 */
	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
		try {
//			IAbandonGoodsApplicationService abandonGoodsApplicationService = getBean("abandonGoodsApplicationService", IAbandonGoodsApplicationService.class);
//
//			ILogBuffer logBuffer = getBean("performanceLog", ILogBuffer.class);
//			logBuffer.write("预弃货信息 服务启动");
//			// 调用预处理
//			abandonGoodsApplicationService.preprocess();
//			logBuffer.write("预弃货信息 服务结束");
		} catch (Exception e) {
			LOGGER.error("预弃货信息Job error", e);
//			throw new JobExecutionException("预弃货信息Job error", e);
		}
	}
}