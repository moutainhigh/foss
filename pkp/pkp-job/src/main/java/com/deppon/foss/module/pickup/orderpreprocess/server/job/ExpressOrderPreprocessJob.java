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
// * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/orderpreprocess/server/job/OrderPreprocessJob.java
// * 
// * FILE NAME        	: OrderPreprocessJob.java
// * 
// * AUTHOR			: FOSS接送货系统开发组
// * 
// * HOME PAGE		: http://www.deppon.com
// * 
// * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
// ******************************************************************************/
//package com.deppon.foss.module.pickup.orderpreprocess.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.StatefulJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.order.api.server.service.IOrderPreprocessExpressService;
//import com.deppon.foss.module.pickup.order.api.server.service.IOrderPreprocessService;
//
//
//
///**
// * 预处理建议job
// * @author 038590-foss-wanghui
// * @date 2012-11-2 上午8:34:53
// */
//public class ExpressOrderPreprocessJob extends GridJob implements StatefulJob{
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressOrderPreprocessJob.class);
//	
//	/**
//	 * 
//	 * 快递订单预处理建议Job主方法体
//	 * @author YANGBIN
//	 * @date 2014-04-24 下午15:30:11
//	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
//	 */
//	@Override
//	protected void doExecute(JobExecutionContext arg0)
//			throws JobExecutionException {
//		try {
//			LOGGER.info("快递订单预处理生成服务启动");
//			// 获得快递预处理服务
//			IOrderPreprocessExpressService orderPreprocessExpressService = getBean("orderPreprocessExpressService", IOrderPreprocessExpressService.class);	
//			// 调用快递预处理接口
//			orderPreprocessExpressService.preprocess();
//			LOGGER.info("快递订单预处理生成服务结束");
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(),e);
//			throw new JobExecutionException(e.getMessage(),e);
//		}
//	}
//	
//}