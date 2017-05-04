///**
// *  initial comments.
// */
///*******************************************************************************
// * Copyright 2013 PKP
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *    http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * 
// * PROJECT NAME	: pkp-job
// * 
// * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/job/DeliverRegionCodeProcessJob.java
// * 
// * FILE NAME        	: DeliverRegionCodeProcessJob.java
// * 
// * AUTHOR			: FOSS接送货系统开发组
// * 
// * HOME PAGE		: http://www.deppon.com
// * 
// * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
// ******************************************************************************/
//package com.deppon.foss.module.pickup.predeliver.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.StatefulJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.framework.server.components.logger.ILogBuffer;
//import com.deppon.foss.module.pickup.predeliver.api.server.service.IReceiveAddressRfcService;
//
///**
// * 据运单地址临时表，处理运单的集中送货小区Job
// * 
// * @date 2012-11-27 下午5:27:29
// */
//public class DeliverRegionCodeProcessJob extends GridJob implements StatefulJob {
//	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverRegionCodeProcessJob.class);
//
//	/**
//	 * 
//	 * 根据运单地址临时表，处理运单的集中送货小区
//	 * 
//	 * @author ibm-wangfei
//	 * @date Dec 3, 2012 5:06:10 PM
//	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
//	 */
//	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
//		try {
//			IReceiveAddressRfcService receiveAddressRfcService = getBean("receiveAddressRfcService", IReceiveAddressRfcService.class);
//
//			ILogBuffer logBuffer = getBean("performanceLog", ILogBuffer.class);
//			logBuffer.write("处理运单的集中送货小区  服务启动");
//			receiveAddressRfcService.updateDeliverRegionCode();
//			logBuffer.write("处理运单的集中送货小区  服务结束");
//		} catch (Exception e) {
//			LOGGER.error("据运单地址临时表，处理运单的集中送货小区Job error", e);
//			throw new JobExecutionException("据运单地址临时表，处理运单的集中送货小区Job error", e);
//		}
//	}
//}