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

import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderQueryHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.util.define.FossConstants;

public class PdaSignEntityDaoTest extends TestCase {

	private IPdaSignEntityDao pdaSignEntityDao;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] { "com/deppon/foss/module/pickup/order/test/META-INF/spring.xml", "com/deppon/foss/module/pickup/order/server/META-INF/spring.xml" };

	@Override
	protected void setUp() throws Exception {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		pdaSignEntityDao = (IPdaSignEntityDao) ctx.getBean("pdaSignEntityDao");

	}

	/**
	 * 查询可用快递员
	 */
	@Test
	public void testQueryUsedUser() {
		OwnTruckConditionDto dto = new OwnTruckConditionDto();
		dto.setActive(FossConstants.ACTIVE);
		// PDA绑定状态
		dto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		dto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		List<OwnTruckDto> dtos = pdaSignEntityDao.queryUsedUser(dto);
		assertNotNull(CollectionUtils.isEmpty(dtos));
	}

	/**
	 * 查询全部快递员
	 */
	@Test
	public void testQueryAllUser() {
		OwnTruckConditionDto dto = new OwnTruckConditionDto();
		// PDA绑定状态
		dto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		dto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		List<OwnTruckDto> dtos = pdaSignEntityDao.queryAllUser(dto, 0, 10);
		assertNotNull(CollectionUtils.isEmpty(dtos));
	}
	/**
	 * 查询全部快递员
	 */
	@Test
	public void testQueryAllUserCount() {
		OwnTruckConditionDto dto = new OwnTruckConditionDto();
		// PDA绑定状态
		dto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		dto.setPdaSignUserType(PdaSignStatusConstants.USER_TYPE_COURIER);
		Long count = pdaSignEntityDao.queryAllUserCount(dto);
		assertNotNull(count > 0);
	}
	/**
	 * 查询全部快递员
	 */
	@Test
	public void testQueryExpressSignByDVByPage() {
		PdaSignDto dto = new PdaSignDto();
		List<PdaSignDto> dtos = pdaSignEntityDao.queryExpressSignByDVByPage(dto, 0, 10);
		assertNotNull(CollectionUtils.isEmpty(dtos));
	}
	/**
	 * 查询全部快递员
	 */
	@Test
	public void testQueryExpressSignedInfoCount() {
		PdaSignDto dto = new PdaSignDto();
		Long count = pdaSignEntityDao.queryExpressSignedInfoCount(dto);
		assertNotNull(count > 0);
	}
}