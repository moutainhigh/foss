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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderPreprocessService.java
 * 
 * FILE NAME        	: OrderPreprocessService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.order.api.server.dao.IOrderPdaReturnRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;


/**
 * 
 * @ClassName: OrderAutoExceptionLogService 
 * @Description: 自动调度处理PDA退回记录服务 
 * @author YANGBIN
 * @date 2014-5-10 上午9:07:25 
 *
 */
public class OrderPdaReturnRecordService implements IOrderPdaReturnRecordService{
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPdaReturnRecordService.class);
	private IOrderPdaReturnRecordEntityDao orderPdaReturnRecordEntityDao;
	@Override
	public int insertPdaReturnRecord(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return orderPdaReturnRecordEntityDao.insertPdaReturnRecord(orderPdaReturnRecordEntity);
	}

	@Override
	public Long queryOrderPdaReturnByDriverCode(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return orderPdaReturnRecordEntityDao.queryOrderPdaReturnByDriverCode(orderPdaReturnRecordEntity);
	}
	
	@Override
	public int deleteOrderPdaReturn(
			OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return orderPdaReturnRecordEntityDao.deleteOrderPdaReturn(orderPdaReturnRecordEntity);
	}
	
	@Override
	public OrderPdaReturnRecordEntity queryOrderPdaReturnByCommon(
			OrderPdaReturnRecordEntity orderPdaReturnRecordEntity) {
		return orderPdaReturnRecordEntityDao.queryOrderPdaReturnByCommon(orderPdaReturnRecordEntity);
	}
	
	public void setOrderPdaReturnRecordEntityDao(
			IOrderPdaReturnRecordEntityDao orderPdaReturnRecordEntityDao) {
		this.orderPdaReturnRecordEntityDao = orderPdaReturnRecordEntityDao;
	}

}