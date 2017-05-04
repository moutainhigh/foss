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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/order/server/dao/DispatchOrderEntityDaoTest.java
 * 
 * FILE NAME        	: DispatchOrderEntityDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;

public class DispatchVehicleRecordEntityDaoTest extends TestCase {

	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	
	private static ApplicationContext ctx = null;
	
	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/order/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml" };
	
	@Override
	protected void setUp() throws Exception {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		dispatchVehicleRecordEntityDao = (IDispatchVehicleRecordEntityDao) ctx.getBean("dispatchVehicleRecordEntityDao");
		
	}
	/**
	 *  根据查询小件处理订单任务DTO查询调度订单派车记录
	 */
	@Test
	public void testGetExpressVehicleRecordByCount(){
		DispatchOrderVehicleDto dto = new DispatchOrderVehicleDto();
		dto.setOrderNo("WK130824727761");
		Long count = dispatchVehicleRecordEntityDao.getExpressVehicleRecordByCount(dto);
		assertNotNull(count > 0);
	}
	/**
	 *  根据查询小件处理订单任务DTO查询调度订单派车记录
	 */
	@Test
	public void testQueryExpressVehicleRecordBy(){
		DispatchOrderVehicleDto dto = new DispatchOrderVehicleDto();
		dto.setOrderNo("WK130824727761");
		List<DispatchOrderVehicleDto> dtos = dispatchVehicleRecordEntityDao.queryExpressVehicleRecordBy(dto, 0 , 10);
		assertNotNull(CollectionUtils.isEmpty(dtos));
	}
}