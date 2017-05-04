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
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillCODStateServiceTest.java
 * 
 * FILE NAME        	: BillCODStateServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;

public class BillCODStateServiceTest extends BaseTestCase {

	@Autowired
	private IWaybillPickupService waybillPickupService;

	@Autowired
	private IBillCODStateService billCODStateService;

	@Autowired
	private ICodCommonService codCommonService;

	/**
	 * 
	 * 测试资金部冻结
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-24 上午10:12:07
	 */
	@Test
	@Rollback(true)
	public void testFundFreezeCOD() {
		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entity.getId());

		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 
	 * 测试资金部反冻结
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-24 上午10:14:30
	 */
	@Test
	@Rollback(true)
	public void testFundReleaseCOD() {
		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entity.getId());

		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

		billCODStateService.fundReleaseCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());
	}
}
