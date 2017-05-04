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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/order/server/dao/TruckResourceDaoTest.java
 * 
 * FILE NAME        	: TruckResourceDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.TruckConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.util.define.FossConstants;

public class TruckResourceDaoTest extends TestCase {

	private ITruckResourceDao truckResourceDao;
	private IPdaSignEntityDao pdaSignEntityDao;
	
	private static ApplicationContext ctx = null;
	
	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/order/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml" };
	
	OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto();
	
	@Override
	protected void setUp() throws Exception {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		truckResourceDao = (ITruckResourceDao) ctx.getBean("truckResourceDao");
		pdaSignEntityDao = (IPdaSignEntityDao) ctx.getBean("pdaSignEntityDao");
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		ownTruckConditionDto.setRegionNature(ComnConst.REGION_NATURE_SQ);
		ownTruckConditionDto.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
		ownTruckConditionDto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		ownTruckConditionDto.setSchedulingType(TruckConstants.SCHEDULE_TYPE_DELIVERY);
		ownTruckConditionDto.setSchedulingStatus(TruckConstants.SCHEDULE_STATUS_ACTIVE);
		ownTruckConditionDto.setSchedulingPlanType(TruckConstants.PLAN_TYPE_WORK);
		ownTruckConditionDto.setDepartPlanType(TruckConstants.DEPART_PLAN_TYPE);
	}
	
	@Test
	public void testQueryTruckDepartPlan(){
		truckResourceDao.queryTruckDepartPlan(ownTruckConditionDto);
	}
	
	@Test
	public void testQueryBorrowVehicle(){
		truckResourceDao.queryBorrowVehicle(ownTruckConditionDto);
	}
	
	@Test
	public void testQueryOrderVehicle(){
		truckResourceDao.queryOrderVehicle(ownTruckConditionDto);
	}
	
	@Test
	public void testA() {
		PdaSignEntity entity = new PdaSignEntity();
		entity.setVehicleNo("fdsaf");
		int i = pdaSignEntityDao.querySignCountByDV(entity);
		System.out.println(i);
	}
}