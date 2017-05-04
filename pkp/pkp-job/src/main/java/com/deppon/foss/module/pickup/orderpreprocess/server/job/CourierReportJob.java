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
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.StatefulJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.order.api.server.service.ICourierReportProcessService;
//import com.deppon.foss.module.pickup.order.api.server.service.ICourierReportService;
//import com.deppon.foss.module.pickup.order.api.server.service.IOrderReportService;
//
//
//
///**
// * 预处理建议job
// * @author 038590-foss-wanghui
// * @date 2012-11-2 上午8:34:53
// */
//public class CourierReportJob extends GridJob implements StatefulJob{
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(CourierReportJob.class);
//	
//	/**
//	 * 
//	 * 每日3点删除前天凌晨3点至今当天凌晨3点，快递订单数据
//	 * @author YANGBIN
//	 * @date 2014-04-24 下午15:30:11
//	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
//	 */
//	@Override
//	protected void doExecute(JobExecutionContext arg0)
//			throws JobExecutionException {
//		try {
//			LOGGER.info("统计前天凌晨0点至今当天凌晨0点数据，并进行数据查询");
//			// 获得快递每日报表服务
//			ICourierReportProcessService courierReportProcessService = getBean("courierReportProcessService", ICourierReportProcessService.class);	
//			//zxy 20140714 AUTO-137 start 新增:
//			Calendar calendar = Calendar.getInstance();
//			Date curDate = calendar.getTime();
//			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-1);
//			Date preDate = calendar.getTime();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			//zxy 20140714 AUTO-137 end 新增:
//			// 进行统计处理
//			courierReportProcessService.process(curDate,preDate);
//			LOGGER.info("统计新增结束");
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(),e);
//			throw new JobExecutionException(e.getMessage(),e);
//		}
//	}
//	
//}