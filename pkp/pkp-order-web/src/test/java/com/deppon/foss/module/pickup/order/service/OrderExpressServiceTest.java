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
 * PROJECT NAME	: pkp-deliver-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/deliver/server/service/NotifyCustomerServiceTest.java
 * 
 * FILE NAME        	: NotifyCustomerServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.pickup.order.BaseTestCase;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderExpressService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * NotifyCustomerServiceTest
 * @author ibm-wangfei
 * @date Dec 25, 2012 3:17:41 PM
 */
public class OrderExpressServiceTest extends BaseTestCase {

	@Autowired
	private IOrderExpressService orderExpressService;

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#queryExpressDispatchOrders(com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto, int, int)}.
	 */
	@Test
	public void testQueryExpressDispatchOrders() {
		DispatchOrderConditionDto dispatchOrderConditionDto = new DispatchOrderConditionDto();
		// 设置运输性质为包裹
		dispatchOrderConditionDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		// 设置默认的订单状态查询条件
		List<String> defaultOrderStatus = new ArrayList<String>();
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		dispatchOrderConditionDto.setDefaultOrderStatus(defaultOrderStatus);
		dispatchOrderConditionDto.setActive(FossConstants.ACTIVE);
		int start = 0; 
		int limit = 10;
		orderExpressService.queryExpressDispatchOrders(dispatchOrderConditionDto, start, limit);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#queryUsedUser(com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto)}.
	 */
	@Test
	public void testQueryUsedUser() {
		OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto(); 
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		// PDA绑定状态
		ownTruckConditionDto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		ownTruckConditionDto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		orderExpressService.queryUsedUser(ownTruckConditionDto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#queryAllUserCount(com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto)}.
	 */
	@Test
	public void testQueryAllUserCount() {
		OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto();
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		// PDA绑定状态
		ownTruckConditionDto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		ownTruckConditionDto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		orderExpressService.queryAllUserCount(ownTruckConditionDto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#queryAllUser(com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto, int, int)}.
	 */
	@Test
	public void testQueryAllUser() {
		OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto();
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		// PDA绑定状态
		ownTruckConditionDto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		ownTruckConditionDto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		orderExpressService.queryAllUserCount(ownTruckConditionDto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#queryVehicleRecordBy(com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto, int, int)}.
	 */
	@Test
	public void testQueryVehicleRecordBy() {
		DispatchOrderVehicleDto dto = new DispatchOrderVehicleDto();
		// 设置运输性质为包裹
		dto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		orderExpressService.queryVehicleRecordBy(dto, 0, 10);
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#getVehicleRecordByCount(com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto)}.
	 */
	@Test
	public void testGetVehicleRecordByCount() {
		DispatchOrderVehicleDto dto = new DispatchOrderVehicleDto();
		// 设置运输性质为包裹
		dto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		orderExpressService.getVehicleRecordByCount(dto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.server.service.impl.OrderExpressService#queryVehicleRecord(com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto)}.
	 */
	@Test
	public void testQueryVehicleRecord() {
		DispatchOrderVehicleDto dto = new DispatchOrderVehicleDto();
		// 设置运输性质为包裹
		dto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		orderExpressService.queryVehicleRecord(dto);
	}
}