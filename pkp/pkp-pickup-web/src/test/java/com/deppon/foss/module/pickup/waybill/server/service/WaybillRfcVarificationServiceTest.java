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
 * PROJECT NAME	: pkp-pickup-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillRfcVarificationServiceTest.java
 * 
 * FILE NAME        	: WaybillRfcVarificationServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.ActualFreightService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillManagerService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;

public class WaybillRfcVarificationServiceTest {

//    private IWaybillRfcVarificationService WaybillRfcVarificationService;
//    private IActualFreightService actualFreightService;
//    private IWaybillManagerService waybillManagerService;
//    private String waybillNo = Math.round(Math.random() * 1000000000) + "";
//    WaybillRfcCondition con = new WaybillRfcCondition();
//    @Before
//    public void setUp() throws Exception {
//    	WaybillRfcVarificationService = TestHelper.get().getBeanByClass(WaybillRfcVarificationService.class);
//    	actualFreightService = (ActualFreightService) TestHelper.get().getBeanByClass(ActualFreightService.class);
//    	waybillManagerService = (WaybillManagerService) TestHelper.get().getBeanByClass(WaybillManagerService.class);
//    }
//    
//	@Test
//	public void testAgreeWaybillRfcOpinion(){
//		String waybillRfcId = "bac938d5-7fcb-4c11-8fa6-be3953b2ac10";
//		String notes = "测试notes";
//		boolean b = WaybillRfcVarificationService.agreeWaybillRfcOpinion(waybillRfcId, notes);
//		Assert.assertTrue(b);
//	}
//	
//	 @Test
//	 public void testInsertActualFreight(){
//		 ActualFreightEntity entity = new ActualFreightEntity();
//		 entity.setActualDeliverType("dfd");
//		 entity.setArrangeGoodsQty(5);
//		 entity.setArriveTime(new Date());
//		 entity.setWaybillNo("000000");
//		 entity.setGoodsName("废铁");
//		 entity.setWeight(new BigDecimal(123.33));
//		 entity.setVolume(new BigDecimal(23));
//		 entity.setGoodsQty(new Integer(56));
//		 entity.setDimension("fdf");
//		 entity.setInsuranceValue(new BigDecimal(33));
//		 entity.setPackingFee(new BigDecimal(45));
//		 entity.setDeliverFee(new BigDecimal(45));
//		 entity.setLaborFee(new BigDecimal(44));
//		 entity.setCodAmount(new BigDecimal(55));
//		 entity.setValueaddFee(new BigDecimal(2.3));
//		 entity.setFreight(new BigDecimal(20.366));
//		 entity.setSettleStatus("1");
//		 entity.setSettleTime(new Date());
//		 actualFreightService.insertWaybillActualFreight(entity);
//	 }
//	 
//	 @Test
//	 public void testGetWaybillById(){
//		 String waybillId = "d0252ce4-aa93-4153-b7a6-4fc61c80a32b";
//		 WaybillDto waybill = waybillManagerService.getWaybillDtoById(waybillId);
//		 Assert.assertNotNull(waybill);
//	 }
//	 
//		@Test
//		public void testQueryWaybillRfcDto() {
//			con.setWaybillNumber("12345678");
//			List<WaybillRfcChangeDto> list =  WaybillRfcVarificationService.queryWaybillRfcVarifyDto(con);
//			int len = list.size();
//			Assert.assertEquals(2, len);
//			
//			con.setWaybillNumber(null);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			cal.set(Calendar.DATE, 1);
//			con.setStartDate(new Date(cal.getTimeInMillis()));
//			con.setEndDate(new Date());
//			
//			list = WaybillRfcVarificationService.queryWaybillRfcVarifyDto(con);
//			Assert.assertNotNull(list);
//		}
//		
//		@Test
//		public void testQueryWayBillRfcProofByRfcId(){
//			String waybillRfcId = "b97bfb88-6f9c-4732-baf4-746c7937d533";
//			List<WaybillRfcProofEntity> list = WaybillRfcVarificationService.queryWayBillRfcProofByRfcId(waybillRfcId);
//			int size = list.size();
//			Assert.assertEquals(1, size);
//		}
}