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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/order/server/service/OrderTaskHandleServiceTest.java
 * 
 * FILE NAME        	: OrderTaskHandleServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.esb.crm.server.order.CancelOrderRequest;
import com.deppon.foss.esb.crm.server.order.CancelOrderResponse;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.shared.dto.TransferOrderDto;

public class OrderTaskHandleServiceTest extends TestCase {
	
	private IOrderTaskHandleService orderTaskHandleService;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/order/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml"};

	@Override
	protected void setUp() throws Exception {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		orderTaskHandleService = (IOrderTaskHandleService) ctx
				.getBean("orderTaskHandleService");
	}

	@Test
	public void testAddTransferOrder(){
		TransferOrderDto transferOrderDto = new TransferOrderDto();
		transferOrderDto.setCreateUserCode("0000");
		transferOrderDto.setCreateUserName("张三");
		transferOrderDto.setCustomerName("李四");
		transferOrderDto.setFleetCode("上海车队");
		transferOrderDto.setGoodsName("电脑");
		transferOrderDto.setGoodsQty(12);
		transferOrderDto.setGoodsType("A");
		transferOrderDto.setLatestPickupTime(new Date());
		transferOrderDto.setMobile("13263639388");
		transferOrderDto.setOrderNo("YC00001");
		transferOrderDto.setOrderNotes("此货");
		transferOrderDto.setOrderVehicleOrgCode("CSSSS1111");
		transferOrderDto.setOrderVehicleOrgName("约车部门名称");
		transferOrderDto.setOrderVehicleTime(new Date());
		transferOrderDto.setPickupElseAddress("青浦区徐泾营业部");
		transferOrderDto.setSalesDepartmentCode("CS0001");
		transferOrderDto.setSalesDepartmentName("营业部");
		transferOrderDto.setTel("11111111");
		transferOrderDto.setVehicleType("7米6");
		transferOrderDto.setWeight(BigDecimal.TEN);
		transferOrderDto.setVolume(BigDecimal.TEN);
		orderTaskHandleService.addTransferOrder(transferOrderDto);
	}
	
	@Test
	public void testCancelOrder() {
		CancelOrderRequest payload = new CancelOrderRequest();
		payload.setOrderNumber("K130629769856");
		CancelOrderResponse response = new CancelOrderResponse();
		
		orderTaskHandleService.cancelOrder(payload, response);
	}
}