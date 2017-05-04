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
// * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/job/RepaymentJob.java
// * 
// * FILE NAME        	: RepaymentJob.java
// * 
// * AUTHOR			: FOSS接送货系统开发组
// * 
// * HOME PAGE		: http://www.deppon.com
// * 
// * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
// ******************************************************************************/
//package com.deppon.foss.module.pickup.sign.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
//
///**
// * 
// * 后台job查询未结清未生成单据的数据进行财务结清
// * @author 043258-foss-zhaobin
// * @date 2012-11-30 上午11:16:58
// */
//public class RepaymentJob extends GridJob{
//	private static final Logger LOGGER = LoggerFactory.getLogger(RepaymentJob.class);
//	@Override
//	protected void doExecute(JobExecutionContext context) throws JobExecutionException
//	{
//		try {
//			LOGGER.info("RepaymentJob begin");
//			// 付款service
//			IRepaymentService repaymentService = getBean("repaymentService", IRepaymentService.class);
//			// 执行轮询
//			repaymentService.batchjobs();
//			LOGGER.info("RepaymentJob end");
//		} catch (Exception e) {
//			LOGGER.error("error", e);
//			throw new JobExecutionException("结清货款异常",e);
//		}
//	}
//}