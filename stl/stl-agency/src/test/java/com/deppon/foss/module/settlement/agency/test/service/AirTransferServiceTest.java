/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-agency
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/agency/test/service/AirTransferServiceTest.java
 * 
 * FILE NAME        	: AirTransferServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.agency.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IAirTransferService;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;


/**
 * 空运中转提货测试类
 * @author ibm-zhuwei
 * @date 2012-12-3 下午7:13:50
 */
public class AirTransferServiceTest extends AirJointTicketServiceTest {

	@Autowired
	private IAirTransferService airTransferService;
	
	@Test
	@Rollback(false)
	public void testAddAirTransfer() {
		this.logger.info("...");
		
		// 先调用合票
		this.testAddAirJointTicket();
		
		airTransferService.addAirTransfer(getAirTransPickupbillEntity(), getAirTransPickupDetails()
				, AgencyTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(false)
	public void testModifyAirTransfer() {
		this.logger.info("...");

		// 中转提货
		this.testAddAirTransfer();
		
		airTransferService.modifyAirTransferDetail(getAirTransPickupbillEntity(), getAirTransPickupbillEntity(), 
				getAddedAirTransPickupDetails(), getModifiedAirTransPickupDetails(), null
				, AgencyTestUtil.getCurrentInfo());
		
		airTransferService.modifyAirTransferDetail(getAirTransPickupbillEntity(), getAirTransPickupbillEntity(), null, null, 
				Arrays.asList(WAYBILL_NO2), AgencyTestUtil.getCurrentInfo());
	}
	
	public AirTransPickupbillEntity getAirTransPickupbillEntity() {
		
		Date now = new Date();
		AirTransPickupbillEntity entity = new AirTransPickupbillEntity();
		
		entity.setAirTransferPickupbillNo("AIR_TRANSFER");
		entity.setDeliverFeeTotal(NumberUtils.createBigDecimal("1200.00"));
		entity.setAirWaybillNo(AIR_WAYBILL_NO);
		entity.setCreateOrgCode("ORIG_ORG_CODE");
		entity.setDestOrgCode("KYSH001");
		entity.setCreateTime(now);
		
		return entity;
	}
	
	public List<AirTransPickupDetailEntity> getAirTransPickupDetails() {
		
		List<AirTransPickupDetailEntity> details = new ArrayList<AirTransPickupDetailEntity>();
		
		AirTransPickupDetailEntity entity1 = new AirTransPickupDetailEntity();
		entity1.setDeliverFee(NumberUtils.createBigDecimal("450.00"));
		entity1.setWaybillNo(WAYBILL_NO1);
		
		details.add(entity1);
		
		return details;
	}

	public List<AirTransPickupDetailEntity> getAddedAirTransPickupDetails() {
		
		List<AirTransPickupDetailEntity> details = new ArrayList<AirTransPickupDetailEntity>();
		
		AirTransPickupDetailEntity entity1 = new AirTransPickupDetailEntity();
		entity1.setDeliverFee(NumberUtils.createBigDecimal("200.00"));
		entity1.setWaybillNo(WAYBILL_NO2);
		
		details.add(entity1);
		
		return details;
	}

	public List<AirTransPickupDetailEntity> getModifiedAirTransPickupDetails() {
		
		List<AirTransPickupDetailEntity> details = new ArrayList<AirTransPickupDetailEntity>();
		
		AirTransPickupDetailEntity entity1 = new AirTransPickupDetailEntity();
		entity1.setDeliverFee(NumberUtils.createBigDecimal("600.00"));
		entity1.setWaybillNo(WAYBILL_NO1);
		
		details.add(entity1);
		
		return details;
	}
	
}
