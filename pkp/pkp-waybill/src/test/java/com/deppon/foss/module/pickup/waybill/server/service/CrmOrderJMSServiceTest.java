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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/CrmOrderJMSServiceTest.java
 * 
 * FILE NAME        	: CrmOrderJMSServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;


public class CrmOrderJMSServiceTest {

	private ICrmOrderJMSService crmOrderJmsService;

	//@Before
	public void setUp() throws Exception {
		crmOrderJmsService = TestHelper.get().getBeanByClass(CrmOrderJMSService.class);
	}

	//@Test
	public void test() {
		CrmModifyInfoDto request = new CrmModifyInfoDto();
		request.setWaybillNumber("123123123123");
		request.setOrderNumber("213213");
		request.setBackInfo("adsfasfasfasdfasdfsa");
		request.setDriverMobile("1330010001");
		request.setDriverName("wangfei");
		request.setGoodsStatus("aa");
		request.setOprateDeptCode("123123");
		request.setOprateUserNum("123123213");
		ResultDto res = crmOrderJmsService.sendModifyOrder(request);
		System.out.println(res.getCode());
	}

}