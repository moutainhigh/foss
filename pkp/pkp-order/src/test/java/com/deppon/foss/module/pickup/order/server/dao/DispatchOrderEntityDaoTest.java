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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.define.FossConstants;

public class DispatchOrderEntityDaoTest extends TestCase {

	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	
	private static ApplicationContext ctx = null;
	
	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/order/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml" };
	
	@Override
	protected void setUp() throws Exception {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		dispatchOrderEntityDao = (IDispatchOrderEntityDao) ctx.getBean("dispatchOrderEntityDao");
		
	}
	
	@Transactional
	public void testInsertOrder(){
		DispatchOrderEntity dispatchOrderEntity = new DispatchOrderEntity();
		String id = UUID.randomUUID().toString();
		dispatchOrderEntity.setId(id);
		dispatchOrderEntity.setConsigneeAddress("上海青浦徐泾沪清平");
		dispatchOrderEntity.setCustomerName("张三");
		dispatchOrderEntity.setDeviceNo("P000001");
		dispatchOrderEntity.setDriverCode("033333");
		dispatchOrderEntity.setDriverName("司机A");
		dispatchOrderEntity.setGoodsQty(10);
		dispatchOrderEntity.setMobile("133333333333");
		dispatchOrderEntity.setOperateNotes("受理情况");
		dispatchOrderEntity.setOperateTime(new Date());
		dispatchOrderEntity.setOperator("MM");
		dispatchOrderEntity.setOperatorCode("033345");
		dispatchOrderEntity.setOrderNo("111111111");
		dispatchOrderEntity.setOrderNotes("订单备注");
		dispatchOrderEntity.setOrderStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		dispatchOrderEntity.setOrderType("接货");
		dispatchOrderEntity.setOrderVehicleOrgCode("001");
		dispatchOrderEntity.setOrderVehicleOrgName("上海徐泾营业部");
		dispatchOrderEntity.setPickupProvince("北京");
		dispatchOrderEntity.setPickupCity("北京");
		dispatchOrderEntity.setPickupCounty("朝阳区");
		dispatchOrderEntity.setPickupElseAddress("XXXX胡同XXX号");
		dispatchOrderEntity.setTel("021555555");
		dispatchOrderEntity.setVehicleNo("皖11140");
		dispatchOrderEntity.setVehicleType("车型");
		dispatchOrderEntity.setWeight(new BigDecimal("21"));
		dispatchOrderEntity.setVolume(new BigDecimal("0.5"));
		dispatchOrderEntity.setSalesDepartmentName("上海徐泾营业部");
		dispatchOrderEntity.setSalesDepartmentCode("00001");
		dispatchOrderEntity.setVehicleNoSuggested("沪B0011");
		dispatchOrderEntity.setDriverNameSuggested("周周");
		dispatchOrderEntity.setDriverCodeSuggested("005511");
		dispatchOrderEntity.setLatestPickupTime(new Date());
		int count = dispatchOrderEntityDao.insertSelective(dispatchOrderEntity);
		assertEquals(1, count);
	}
	
	public void testQueryOrderByCommon1(){
		DispatchOrderConditionDto conditionDto = new DispatchOrderConditionDto();
		conditionDto.setOrderNo("111111111");
		DispatchOrderEntity entities = dispatchOrderEntityDao.queryOrderByOrderNo("Y21");
		assertNotNull(entities);
	}
//	
	@Test
	public void testQueryOrderByCommon2(){
		DispatchOrderConditionDto conditionDto = new DispatchOrderConditionDto();
		List<String> deptList = new ArrayList<String>();
		deptList.add("W0000001");
		conditionDto.setSalesDepartmentCodes(deptList);
		List<String> status = new ArrayList<String>();
		status.add("NONE_HANDLE");
		conditionDto.setDefaultOrderStatus(status);
		conditionDto.setStatus("122");
		List<DispatchOrderEntity> entities = dispatchOrderEntityDao.queryOrderByDefault(conditionDto, 0, 10);
		assertNotNull(entities);
	}
//	
//	public void testQueryOrderByCommon3(){
//		DispatchOrderConditionDto conditionDto = new DispatchOrderConditionDto();
//		conditionDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
//		List<DispatchOrderEntity> entities = dispatchOrderEntityDao.queryOrderByCommon(conditionDto, 0, 10);
//		assertNotNull(entities);
//	}
//	
//	public void testQueryOrderByCommon4(){
//		DispatchOrderConditionDto conditionDto = new DispatchOrderConditionDto();
//		List<String> salesDepartments = new ArrayList<String>();
//		salesDepartments.add("00001");
//		conditionDto.setSalesDepartmentCodes(salesDepartments);
//		List<DispatchOrderEntity> entities = dispatchOrderEntityDao.queryOrderByCommon(conditionDto, 0, 10);
//		assertNotNull(entities);
//	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	/**
	 * 根据调度订单查询条件查询订单
	 */
	@Test
	public void testQueryExpressOrderByCommon(){
		DispatchOrderConditionDto dispatchOrderConditionDto = new DispatchOrderConditionDto();
		// 设置运输性质为包裹
		dispatchOrderConditionDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		// 设置默认的订单状态查询条件
		List<String> defaultOrderStatus = new ArrayList<String>();
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		dispatchOrderConditionDto.setDefaultOrderStatus(defaultOrderStatus);
		dispatchOrderConditionDto.setActive(FossConstants.ACTIVE);
		List<DispatchOrderEntity> entities = dispatchOrderEntityDao.queryExpressOrderByCommon(dispatchOrderConditionDto, 0, 10);
		assertNotNull(entities);
	}
	/**
	 *  根据调度订单查询条件查询订单总数
	 */
	@Test
	public void testQueryExpressOrderCountByCommon(){
		DispatchOrderConditionDto dispatchOrderConditionDto = new DispatchOrderConditionDto();
		// 设置运输性质为包裹
		dispatchOrderConditionDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		// 设置默认的订单状态查询条件
		List<String> defaultOrderStatus = new ArrayList<String>();
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP);
		dispatchOrderConditionDto.setDefaultOrderStatus(defaultOrderStatus);
		dispatchOrderConditionDto.setActive(FossConstants.ACTIVE);
		Long count = dispatchOrderEntityDao.queryExpressOrderCountByCommon(dispatchOrderConditionDto);
		assertNotNull(count > 0);
	}
	/**
	 *  更新小件订单
	 */
	@Test
	public void testUpdateByExpressOrderHandle(){
		OrderHandleDto record = new OrderHandleDto();
		record.setOrderNo("WK130824727764");
		record.setOrderStatus(DispatchOrderStatusConstants.STATUS_CANCEL);
		List<String> orderStatusList = new ArrayList<String>();
		orderStatusList.add(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		orderStatusList.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		record.setOrderStatusList(orderStatusList);
		int count = dispatchOrderEntityDao.updateByExpressOrderHandle(record);
		assertNotNull(count > 0);
	}
	/**
	 *  更新小件订单
	 */
	@Test
	public void testDeleteDispatchOrderForNo(){
		DispatchOrderConditionDto record = new DispatchOrderConditionDto();
		record.setOrderNo("WK130824727764");
		int count = dispatchOrderEntityDao.deleteDispatchOrderForNo(record);
		assertNotNull(count > 0);
	}
}