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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillPayCODOnlineServiceTest.java
 * 
 * FILE NAME        	: BillPayCODOnlineServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 在线汇代收货款给客户服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-24 上午9:47:46
 */
public class BillPayCODOnlineServiceTest extends BaseTestCase {

	@Autowired
	private IWaybillPickupService waybillPickupService;

	@Autowired
	private IBillPayCODOnlineService billPayCODOnlineService;

	@Autowired
	private IBillCODStateService billCODStateService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private ICodCommonService codCommonService;
	
	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	/**
	 * 
	 * 在线汇代收货款给客户服务
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-24 上午9:49:02
	 
	@Test
	@Rollback(true)
	public void testDoWithSendBillPaybleToBank() {
		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		// 设置签收时间
//		List<BillPayableEntity> list = billPayableService.queryByWaybillNosAndOrgCodes(Arrays.asList(new String[]{entity.getWaybillNo()}),null, FossConstants.ACTIVE ,ConsumerTestUtil.getCurrentInfo());
//		BillPayableEntity billPayableEntity = list.get(0);
//		billPayableEntity.setSignDate(new Date());
//		billPayableService.updateBillPayableSignDateByConfirmIncome(list.get(0),  ConsumerTestUtil.getCurrentInfo());
		
		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entity.getId());

		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

		
//		billPayCODOnlineService.doWithSendBillPaybleToBank(entityIds,ConsumerTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(entity.getWaybillNo());

		// 应付单
		List<BillPayableEntity> billPayables = billPayableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.YES);

		Assert.assertNotNull(billPayables);
		Assert.assertEquals(billPayables.size(), 1);
	}
	*/
}
