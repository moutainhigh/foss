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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillChargeDtlServiceTest.java
 * 
 * FILE NAME        	: WaybillChargeDtlServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPaymentService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillPaymentService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;

public class WaybillChargeDtlServiceTest {
	private IWaybillChargeDtlService waybillChargeDtlService;
	private IWaybillDisDtlService waybillDisDtlService;
	private IWaybillPaymentService waybillPaymentService;
	private IWoodenRequirementsService woodenRequirementsService;
	@Before
	public void setUp() throws Exception {
		waybillChargeDtlService = (IWaybillChargeDtlService) TestHelper.get()
				.getBeanByClass(WaybillChargeDtlService.class);
		waybillDisDtlService = (IWaybillDisDtlService) TestHelper.get()
				.getBeanByClass(WaybillDisDtlService.class);
		waybillPaymentService = (IWaybillPaymentService) TestHelper.get()
				.getBeanByClass(WaybillPaymentService.class);
		woodenRequirementsService = (IWoodenRequirementsService)TestHelper.get().getBeanByClass(WoodenRequirementsService.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryNewChargeDtlEntityByNO() {
		String waybillNo = "837522308";
//		List<WaybillChargeDtlEntity> entityls = waybillChargeDtlService.queryNewChargeDtlEntityByNO(waybillNo);
//		System.out.println(entityls == null ? 0:entityls.size());
	}
	
	@Test
	public void testQueryNewDisDtlEntityByNo(){
		String waybillNo = "837522308";
//		List<WaybillDisDtlEntity> list = waybillDisDtlService.queryNewDisDtlEntityByNo(waybillNo);
//		System.out.println(list == null ? 0:list.size());
	}
	
	@Test
	public void testQueryNewWaybillPaymentEntityByNo(){
		String waybillNo = "837522308";
//		List<WaybillPaymentEntity> payList = waybillPaymentService.queryNewWaybillPaymentEntityByNo(waybillNo);
//		System.out.println(payList == null ? 0:payList.size());
	}
	
	@Test
	public void testQueryNewWoodenRequirements(){
		String waybillNo = "837522308";
//		WoodenRequirementsEntity  entity = woodenRequirementsService.queryNewWoodenRequirements(waybillNo);
//		System.out.println(entity==null);
	}
}