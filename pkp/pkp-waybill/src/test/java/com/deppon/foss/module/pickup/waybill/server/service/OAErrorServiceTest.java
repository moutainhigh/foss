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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/OAErrorServiceTest.java
 * 
 * FILE NAME        	: OAErrorServiceTest.java
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
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CrmOrderService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.OAErrorService;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;

/**
 * 
 * 测试OA问题服务
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-12-1 下午2:37:52
 */
public class OAErrorServiceTest {

	IOAErrorService oAErrorService;

	@Before
	public void setUpBeforeClass() throws Exception {
		oAErrorService = TestHelper.get().getBeanByClass(OAErrorService.class);
	}
 
	//@Test
	public void testOAErrorService() {

		QueryVirtualResultDto response = oAErrorService
				.queryVirtrualWaybillFromOA("2012101519221");
		if (response != null) {
			System.out.println("运单号：" + response.getWayBillNo() + "\n");
			System.out.println("受理结果：" + response.isHandleResult() + "\n");
		}

	}

}