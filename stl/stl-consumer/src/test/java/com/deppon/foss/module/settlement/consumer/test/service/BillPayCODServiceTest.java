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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillPayCODServiceTest.java
 * 
 * FILE NAME        	: BillPayCODServiceTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.define.FossConstants;

public class BillPayCODServiceTest extends BaseTestCase {

	@Autowired
	private IWaybillPickupService waybillPickupService;
	
	@Autowired
	private IBillPayCODService billPayCODService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillReceivableService billReceivableService;

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
	 * 测试新增代收货款服务
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-23 下午2:59:08
	 */
	@Test
	@Rollback(true)
	public void testAddBillCOD() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(waybillDto.getWaybillNo());

		List<CODDto> queryResult = codCommonService.queryBillCODByWaybillNo(
				waybillNos, null, null);

		Assert.assertNotNull(queryResult);
		Assert.assertEquals(queryResult.size(), 1);

		CODDto dto = queryResult.get(0);
		Assert.assertEquals(dto.getStatus(),
				SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		Assert.assertEquals(dto.getAmount(), waybillDto.getCodAmount());

		// 应付单
		List<BillPayableEntity> billPayables = billPayableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.YES);

		Assert.assertNotNull(billPayables);
		Assert.assertEquals(billPayables.size(), 1);

		// 应收单
		List<BillReceivableEntity> billReceivables = billReceivableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.YES);

		Assert.assertNotNull(billReceivables);
		Assert.assertEquals(billReceivables.size(), 1);
	}

	/**
	 * 
	 * 测试作废代收货款服务
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-23 下午2:59:28
	 */
	@Test
	@Rollback(true)
	public void testCancelBillCOD() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		billPayCODService.addBillCOD(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		billPayCODService.cancelBillCOD(entity,
				ConsumerTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(waybillDto.getWaybillNo());

		List<CODDto> queryResult = codCommonService.queryBillCODByWaybillNo(
				waybillNos, null, null);

		Assert.assertNotNull(queryResult);
		Assert.assertEquals(queryResult.size(), 0);

		/*
		// 应付单
		List<BillPayableEntity> billPayables = billPayableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.NO);

		Assert.assertNotNull(billPayables);
		Assert.assertEquals(billPayables.size(), 2);

		// 应收单
		List<BillReceivableEntity> billReceivables = billReceivableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.NO);

		Assert.assertNotNull(billReceivables);
		Assert.assertEquals(billReceivables.size(), 2);
		*/
	}
}
