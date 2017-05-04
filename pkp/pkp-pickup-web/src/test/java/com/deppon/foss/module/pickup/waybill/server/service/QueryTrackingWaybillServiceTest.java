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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/QueryTrackingWaybillServiceTest.java
 * 
 * FILE NAME        	: QueryTrackingWaybillServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.pickup.api.server.service.IQueryTrackingWaybillService;
import com.deppon.foss.module.pickup.pickup.server.service.impl.QueryTrackingWaybillService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;

/**
 * 
 * 追踪运单Service实现类Test
 * 
 * @author ibm-wangfei
 * @date Jan 4, 2013 3:23:29 PM
 */
public class QueryTrackingWaybillServiceTest {
	private IQueryTrackingWaybillService queryTrackingWaybillService;

	@Before
	public void setUp() throws Exception {
		queryTrackingWaybillService = TestHelper.get().getBeanByClass(QueryTrackingWaybillService.class);
	}
	@Test
	public void testQueryByWaybillNo() {
		String waybillNo = "";
		queryTrackingWaybillService.queryByWaybillNo(waybillNo);
	}
}