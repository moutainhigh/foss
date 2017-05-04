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
//import java.util.Calendar;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.StatefulJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.order.api.server.service.IOrderPdaReturnRecordService;
//import com.deppon.foss.module.pickup.order.api.server.service.IOrderReportService;
//import com.deppon.foss.module.pickup.order.api.server.service.IRegionCourierReportService;
//
//
//
///**
// * 预处理建议job
// * @author 038590-foss-wanghui
// * @date 2012-11-2 上午8:34:53
// */
//public class ExpressOrderReportJob extends GridJob implements StatefulJob{
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressOrderReportJob.class);
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
//			LOGGER.info("删除前天凌晨3点至今当天凌晨3点，快递订单数据服务启动[ExpressOrderReportJob start...]");
//			// 获得快递每日报表服务
//			IOrderReportService orderReportService = getBean("orderReportService", IOrderReportService.class);
//			IRegionCourierReportService regionCourierReportService = getBean("regionCourierReportService", IRegionCourierReportService.class);
////			//zxy 20140703 内部优化 start 新增:删除Pda退单
////			IOrderPdaReturnRecordService orderPdaReturnRecordService = getBean("orderPdaReturnRecordService", IOrderPdaReturnRecordService.class);
////			orderPdaReturnRecordService.deleteOrderPdaReturn(null);
////			//zxy 20140703 内部优化 end 新增:删除Pda退单
//			
////			OrderReportEntity orderReportEntity = new OrderReportEntity();
////			if(Calendar.){
////
////				Calendar calendar = Calendar.getInstance(); 		
////				calendar.
////				calendar.add(Calendar.MINUTE, endMinNum);
////				calendar.add(Calendar.SECOND, 0);
////			}
//			// 调用快递预处理接口
//			orderReportService.deleteOrderReport(null);
//			//14.8.9 gcl 
//			regionCourierReportService.delete(null);
//			LOGGER.info("快递订单预处理生成服务结束[ExpressOrderReportJob end.]");
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(),e);
//			throw new JobExecutionException(e.getMessage(),e);
//		}
//	}
//	
//}