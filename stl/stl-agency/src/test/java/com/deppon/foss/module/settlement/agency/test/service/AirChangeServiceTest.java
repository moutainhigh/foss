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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/agency/test/service/AirJointTicketServiceTest.java
 * 
 * FILE NAME        	: AirJointTicketServiceTest.java
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

import junit.framework.Assert;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IAirChangeService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.agency.api.shared.domain.AirChangeEntity;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;


/**
 * 变更清单测试类
 * @author 110485-foss-chenmingchun
 * @date 2013-4-16 下午7:14:07
 */
public class AirChangeServiceTest extends BaseTestCase {

	@Autowired
	protected IAirChangeService airChangeService;
	

//	protected static final String AIR_WAYBILL_NO = "115135176";
	protected static final String AIR_WAYBILL_NO = "AA5135176";
	protected static final String type = "W";
	protected static final String destOrgCode = "W011302020407";
	protected static final String destOrgName = "上海徐汇区营业部";
	protected static final String agentCompanyCode = "400450656";
	protected static final String agentCompanyName = "冯迪";
	
	
	
	@Before
	public void setUp() {
//		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", AIR_WAYBILL_NO);
//		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", WAYBILL_NO1);
//		this.simpleJdbcTemplate.update("delete from t_stl_bill_receivable where waybill_no = ?", WAYBILL_NO1);
//		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", WAYBILL_NO2);
//		this.simpleJdbcTemplate.update("delete from t_stl_bill_receivable where waybill_no = ?", WAYBILL_NO2);


	}
	
	@Test
	@Rollback(false)
	public void testAddAirJointTicket() {
		this.logger.info("...");
		
//		airChangeService.addAirJointTicket(getAirPickupbillEntity(), getAirPickupbillDetails()
//				, AgencyTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testModifyAirJointTicket() {
		this.logger.info("...");
		
		this.testAddAirJointTicket();
		airChangeService.modifyAirChangeDetail(getAirChangebillEntity(), getModifiedAirPickupbillDetails(), getRemoveAirChangebillDetails(), AgencyTestUtil.getCurrentInfo());
	
	}
	
	public List<AirChangeEntity> getAirChangebillEntity() {

		Date now = new Date();
		AirChangeEntity entity = new AirChangeEntity();

		entity.setDeliverFee(NumberUtils.createBigDecimal("1000.00"));
		entity.setWaybillNo(AIR_WAYBILL_NO);
		entity.setType(type);
		entity.setDestOrgCode(destOrgCode);
		entity.setDestOrgName(destOrgName);
		entity.setCreateTime(now);
		entity.setAgentCompanyCode(agentCompanyCode);
		entity.setAgentCompanyName(agentCompanyName);
		
		
		
		List<AirChangeEntity> list=new ArrayList<AirChangeEntity>();
		list.add(entity);

		return null;
	}
	
	public List<AirChangeEntity> getRemoveAirChangebillDetails() {
		
		List<AirChangeEntity> details = new ArrayList<AirChangeEntity>();
		
		Date now = new Date();
		AirChangeEntity entity = new AirChangeEntity();

		entity.setDeliverFee(NumberUtils.createBigDecimal("1000.00"));
		entity.setWaybillNo(AIR_WAYBILL_NO);
		entity.setType(type);
		entity.setDestOrgCode(destOrgCode);
		entity.setDestOrgName(destOrgName);
		entity.setCreateTime(now);
		entity.setAgentCompanyCode(agentCompanyCode);
		entity.setAgentCompanyName(agentCompanyName);
		
		details.add(entity);
		
		return null;
	}

	

	public List<AirChangeEntity> getModifiedAirPickupbillDetails() {
		
		List<AirChangeEntity> details = new ArrayList<AirChangeEntity>();
		
		Date now = new Date();
		AirChangeEntity entity = new AirChangeEntity();

		entity.setDeliverFee(NumberUtils.createBigDecimal("1000.00"));
		entity.setWaybillNo(AIR_WAYBILL_NO);
		entity.setType(type);
		entity.setDestOrgCode(destOrgCode);
		entity.setDestOrgName(destOrgName);
		entity.setCreateTime(now);
		entity.setAgentCompanyCode(agentCompanyCode);
		entity.setAgentCompanyName(agentCompanyName);
		
		details.add(entity);
		return details;
	}
	
}
