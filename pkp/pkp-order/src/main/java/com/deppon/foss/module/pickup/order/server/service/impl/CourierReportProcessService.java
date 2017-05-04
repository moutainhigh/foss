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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderTaskQueryService.java
 * 
 * FILE NAME        	: OrderTaskQueryService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.pickup.order.api.server.service.ICourierReportProcessService;
import com.deppon.foss.module.pickup.order.api.server.service.ICourierReportService;
import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;

/**
 * 小件订单查询Service
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-20 上午10:17:18
 */
public class CourierReportProcessService implements ICourierReportProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CourierReportProcessService.class);
	
	private ICourierReportService courierReportService;
	/**
	 * 
	 * @Title: process
	 * @Description: job启动时，进行处理
	 * @param @param 
	 * @param @return 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@Transactional
	@Override
	public void process(Date curDate, Date preDate) {
		try{
			
			// 获取当天快递排班数据统计集合
			List<CourierReportEntity> courierList = courierReportService.queryCourierReportByDay(curDate, preDate);
			if(CollectionUtils.isEmpty(courierList)){
				LOGGER.info("快递员排班数据统计，未查到任何相关数据");
				return;
			}
			//zxy 20140714 AUTO-137 start 修改:如果当日已做过统计，则删除
			//如果当日已做过统计，则删除
			courierReportService.deleteCourierReportByDay(preDate);
			//循环插入数据
			for(CourierReportEntity queryEntity : courierList){
				courierReportService.insertCourierReport(queryEntity,preDate);
			}
			//zxy 20140714 AUTO-137 end 修改:如果当日已做过统计，则删除
		}catch(Exception e){
			LOGGER.info("快递员排班数据统计，抛出异常："+e.getMessage());
		}
		
	}
	public ICourierReportService getCourierReportService() {
		return courierReportService;
	}
	public void setCourierReportService(ICourierReportService courierReportService) {
		this.courierReportService = courierReportService;
	}
	
	
}