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

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;


/**
 * 空运中转合大票测试类
 * @author ibm-zhuwei
 * @date 2012-12-3 下午7:14:07
 */
public class AirJointTicketServiceTest extends BaseTestCase {

	@Autowired
	protected IAirJointTicketService airJointTicketService;

	protected static final String AIR_WAYBILL_NO = "AA111222";
	protected static final String WAYBILL_NO1 = "111222";
	protected static final String WAYBILL_NO2 = "111333";
	
	@Before
	public void setUp() {
		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", AIR_WAYBILL_NO);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", WAYBILL_NO1);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_receivable where waybill_no = ?", WAYBILL_NO1);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", WAYBILL_NO2);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_receivable where waybill_no = ?", WAYBILL_NO2);

		this.simpleJdbcTemplate.update(
		"insert into stl.t_stl_bill_receivable (ID, RECEIVABLE_NO, WAYBILL_NO, WAYBILL_ID, CREATE_TYPE, SOURCE_BILL_NO, SOURCE_BILL_TYPE, BILL_TYPE, RECEIVABLE_ORG_CODE, RECEIVABLE_ORG_NAME, GENERATING_ORG_CODE, GENERATING_ORG_NAME, GENERATING_COM_CODE, GENERATING_COM_NAME, DUNNING_ORG_CODE, DUNNING_ORG_NAME, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME, CUSTOMER_CODE, CUSTOMER_NAME, DELIVERY_CUSTOMER_CODE, DELIVERY_CUSTOMER_NAME, RECEIVE_CUSTOMER_CODE, RECEIVE_CUSTOMER_NAME, AMOUNT, VERIFY_AMOUNT, UNVERIFY_AMOUNT, CURRENCY_CODE, BUSINESS_DATE, ACCOUNT_DATE, CONREVEN_DATE, PAYMENT_TYPE, PRODUCT_CODE, PRODUCT_ID,  TRANSPORT_FEE, PICKUP_FEE, DELIVERY_GOODS_FEE, PACKAGING_FEE, COD_FEE, INSURANCE_FEE, OTHER_FEE, VALUE_ADD_FEE, PROMOTIONS_FEE, GOODS_NAME, GOODS_VOLUME_TOTAL, BILL_WEIGHT, RECEIVE_METHOD, CUSTOMER_PICKUP_ORG_CODE, GOODS_QTY_TOTAL, TARGET_ORG_CODE, VERSION_NO, ACTIVE, IS_RED_BACK, IS_INIT, CREATE_USER_CODE, CREATE_USER_NAME, CREATE_ORG_CODE, CREATE_ORG_NAME, CREATE_TIME, MODIFY_TIME, MODIFY_USER_CODE, MODIFY_USER_NAME, STATEMENT_BILL_NO, UNLOCK_DATE_TIME, LOCK_CUSTOMER_CODE, LOCK_CUSTOMER_NAME, COLLECTION_TYPE, COLLECTION_NAME, AUDIT_USER_CODE, AUDIT_USER_NAME, APPROVE_STATUS, AUDIT_DATE, IS_DISABLE, DISABLE_USER_CODE, DISABLE_USER_NAME, DISABLE_TIME, NOTES)" +
		" values (sys_guid(), 'YS2' || SQ_YS2.Nextval, ?, '111', '1', ?, 'W', 'DR', 'UW01060304', '应收上海营业部', '1352512067750', '收入上海营业部', 'SRCOM1', '收入公司上海德邦物流', 'CK1', '催款上海青浦营业部', 'CFORG1', '出发上海徐泾营业部', 'DDORG1', '到达北京营业部', 'CUST1', '客户1', '', '', '', '', 100000, 0, 100000, '1', SYSDATE, SYSDATE, null, 'FC', '1', '',  40000, 10000, 10000, 10000, 10000, 10000, 10000, 60000, 0, '', null, null, '1', '', null, '', 1, 'Y', 'N', 'N', '', '', '', '', null, null, '', '', 'N/A', null, '', '', '', '', '', '', '', null, '', '', '', null, '')",
		WAYBILL_NO1, WAYBILL_NO1);
	}
	
	@Test
	@Rollback(false)
	public void testAddAirJointTicket() {
		this.logger.info("...");
		
		airJointTicketService.addAirJointTicket(getAirPickupbillEntity(), getAirPickupbillDetails()
				, AgencyTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(false)
	public void testModifyAirJointTicket() {
		this.logger.info("...");
		
		this.testAddAirJointTicket();
		airJointTicketService.modifyAirJointTicketDetail(getAirPickupbillEntity(), getAirPickupbillEntity(), 
				getAddedAirPickupbillDetails(), getModifiedAirPickupbillDetails(), null
				, AgencyTestUtil.getCurrentInfo());
		
		airJointTicketService.modifyAirJointTicketDetail(getAirPickupbillEntity(), getAirPickupbillEntity(), null, null, 
				Arrays.asList(WAYBILL_NO2), AgencyTestUtil.getCurrentInfo());
	}
	
	public AirPickupbillEntity getAirPickupbillEntity() {

		Date now = new Date();
		AirPickupbillEntity entity = new AirPickupbillEntity();

		entity.setDeliverFeeTotal(NumberUtils.createBigDecimal("1000.00"));
		entity.setAirWaybillNo(AIR_WAYBILL_NO);
		entity.setOrigOrgCode("ORIG_ORG_CODE");
		entity.setDestOrgCode("KYNJFY");
		entity.setCreateTime(now);

		return entity;
	}
	
	public List<AirPickupbillDetailEntity> getAirPickupbillDetails() {
		
		List<AirPickupbillDetailEntity> details = new ArrayList<AirPickupbillDetailEntity>();
		
		AirPickupbillDetailEntity entity1 = new AirPickupbillDetailEntity();
		entity1.setDeliverFee(NumberUtils.createBigDecimal("350.00"));
		entity1.setWaybillNo(WAYBILL_NO1);
		
		details.add(entity1);
		
		return details;
	}

	public List<AirPickupbillDetailEntity> getAddedAirPickupbillDetails() {
		
		List<AirPickupbillDetailEntity> details = new ArrayList<AirPickupbillDetailEntity>();
		
		AirPickupbillDetailEntity entity1 = new AirPickupbillDetailEntity();
		entity1.setDeliverFee(NumberUtils.createBigDecimal("200.00"));
		entity1.setWaybillNo(WAYBILL_NO2);
		
		details.add(entity1);
		
		return details;
	}

	public List<AirPickupbillDetailEntity> getModifiedAirPickupbillDetails() {
		
		List<AirPickupbillDetailEntity> details = new ArrayList<AirPickupbillDetailEntity>();
		
		AirPickupbillDetailEntity entity1 = new AirPickupbillDetailEntity();
		entity1.setDeliverFee(NumberUtils.createBigDecimal("500.00"));
		entity1.setWaybillNo(WAYBILL_NO1);
		
		details.add(entity1);
		
		return details;
	}
	
}
