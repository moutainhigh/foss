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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/agency/test/service/AirStowageServiceTest.java
 * 
 * FILE NAME        	: AirStowageServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.agency.test.service;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IAirStowageService;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.util.UUIDUtils;


/**
 * 空运配载测试类
 * @author ibm-zhuwei
 * @date 2012-10-25 下午6:08:12
 */
public class AirStowageServiceTest extends BaseTestCase {

	@Autowired
	private IAirStowageService airStowageService;
	
	private static final String AIR_WAYBILL_NO = "AA2345123";
	
	@Before
	public void setUp() {
		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", AIR_WAYBILL_NO);
		
	}
	
	/**
	 * 新增空运配载单
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test
	@Rollback(false)
	public void testAddAirWaybill1() {
		this.logger.info("...");

		AirWaybillEntity item = this.getAirWaybillEntity();

		airStowageService.addAirStowage(item, AgencyTestUtil.getCurrentInfo());
	}
	
	@Test
	@Rollback(false)
	public void testCancelAirWaybill() {
		this.logger.info("...");

		AirWaybillEntity item = this.getAirWaybillEntity();

		airStowageService.addAirStowage(item, AgencyTestUtil.getCurrentInfo());
		airStowageService.cancelAirStowage(item.getAirWaybillNo(), AgencyTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(false)
	public void testModifyAirWaybill() {
		this.logger.info("...");

		AirWaybillEntity item = this.getAirWaybillEntity();
		AirWaybillEntity newItem = this.getAirWaybillEntity();

		airStowageService.addAirStowage(item, AgencyTestUtil.getCurrentInfo());
		BeanUtils.copyProperties(item, newItem);
		
		item.setFeeTotal(NumberUtils.createBigDecimal("1900.50"));
		airStowageService.modifyAirStowage(item, newItem, AgencyTestUtil.getCurrentInfo());
	}

	private AirWaybillEntity getAirWaybillEntity() {
		
		Date now = new Date();
		AirWaybillEntity item = new AirWaybillEntity();
		
		item.setId(UUIDUtils.getUUID());
		
		item.setAirLineTwoletter(null);
		item.setAirWaybillNo(AIR_WAYBILL_NO);
		item.setDeptRegionCode("shanghai");
		item.setDeptRegionName("上海");
		item.setArrvRegionCode("beijing");
		item.setArrvRegionName("北京");
		item.setAirAssembleType("DDKD");
		item.setDestOrgCode("org_code");
		item.setDedtOrgName("部门");
		item.setReceiverCode(null);
		item.setReceiverName(null);
		item.setReceiverContactPhone(null);
		item.setAccountItem(null);
		item.setBillingAgency(null);
		item.setReceiverAddress(null);
		item.setStorageItem(null);
		item.setFlightNo(null);
		item.setFlightDate(null);
		item.setTakeOffTime(null);
		item.setArriveTime(null);
		item.setRateClass(null);
		item.setPaymentType(null);
		item.setGrossWeight(null);
		item.setBillingWeight(null);
		item.setFee(null);
		item.setAgenctCode("agency_code");
		item.setAgencyName("承运人");
		item.setDeclareValue(null);
		item.setItemCode(null);
		item.setGoodsQty(null);
		item.setVolume(null);
		item.setAirFee(null);
		item.setExtraFee(null);
		item.setGoodsName(null);
		item.setPackageStruction(null);
		item.setGroundFee(null);
		item.setFuelSurcharge(null);
		item.setTransportInsurance(null);
		item.setInseranceFee(null);
		item.setFeeTotal(NumberUtils.createBigDecimal("1200.50"));
		item.setFeePlain(null);
		item.setBillingFee(null);
		item.setShipperCode(null);
		item.setShipperName(null);
		item.setShipperAddress(null);
		item.setShipperContactPhone(null);
		item.setPickupType(null);
		item.setCreateOrgCode("create_org_code");
		item.setCreateOrgName("create_org_name");
		item.setCreateUserCode("create_user_code");
		item.setCreateUserName("create_user_name");
		item.setCreateTime(now);
		item.setModifyUserCode(null);
		item.setModifyUserName(null);
		item.setModifyTime(null);
		item.setHandoverState(null);
		item.setCurrencyCode(null);
		item.setIsNotPayment(null);
		item.setJointTicketNo(null);
		item.setActualTakeOffTime(null);
		item.setActualArriveTime(null);
		item.setTrackState(null);

		return item;
	}
	
}
