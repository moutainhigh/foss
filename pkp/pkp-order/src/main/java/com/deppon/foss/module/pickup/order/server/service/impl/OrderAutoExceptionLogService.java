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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.order.api.server.dao.IOrderAutoExceptionLogEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;


/**
 * 
 * @ClassName: OrderAutoExceptionLogService 
 * @Description: 自动调度处理业务异常日志服务 
 * @author YANGBIN
 * @date 2014-5-10 上午9:07:25 
 *
 */
public class OrderAutoExceptionLogService implements IOrderAutoExceptionLogService{
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAutoExceptionLogService.class);
	private IOrderAutoExceptionLogEntityDao orderAutoExceptionLogEntityDao;
	@Override
	@Transactional(propagation =Propagation.REQUIRES_NEW)	
	public int insertAutoOrderExceptionLog(
			OrderAutoExceptionLogEntity orderAutoExceptionLogEntity) {
		return orderAutoExceptionLogEntityDao.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);
	}
	public void setOrderAutoExceptionLogEntityDao(
			IOrderAutoExceptionLogEntityDao orderAutoExceptionLogEntityDao) {
		this.orderAutoExceptionLogEntityDao = orderAutoExceptionLogEntityDao;
	}
	
}