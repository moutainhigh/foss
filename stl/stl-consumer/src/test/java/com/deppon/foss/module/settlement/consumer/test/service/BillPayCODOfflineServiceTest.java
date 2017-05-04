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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillPayCODOfflineServiceTest.java
 * 
 * FILE NAME        	: BillPayCODOfflineServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
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
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOfflineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 汇代收货款给客户
 * 
 * @author dp-zengbinwen
 * @date 2012-10-23 下午2:50:36
 */
public class BillPayCODOfflineServiceTest extends BaseTestCase {

	@Autowired
	private IBillPayCODOfflineService billPayCODOfflineService;

	@Autowired
	private IWaybillPickupService waybillPickupService;

	@Autowired
	private IBillCODStateService billCODStateService;

	@Autowired
	private IBillPaymentService billPaymentService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillWriteoffService billWriteoffService;

	@Autowired
	private ICodCommonService codCommonService;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	// @Test
	// @Rollback(true)
	// public void makeBillCODOfflineData() {
	//
	// int batchNumber = 2;
	//
	// for (int k = 0; k < 10; k++) {
	// List<String> entityIds = new ArrayList<String>();
	// for (int i = 0; i < 100; i++) {
	// String str = String.valueOf(RandomUtils.nextInt(100000000));
	// String waybillNo = ("00000000" + str).substring(str.length());
	//
	// CODEntity entity = ConsumerTestUtil
	// .buildRandomCODEntity(waybillNo);
	// billPayCODService.addBillCOD(entity, "1", "德邦物流", "WDFQ1212",
	// "李四", "RD");
	// entityIds.add(entity.getId());
	// }
	//
	// billCODStateService.fundFreezeCOD(entityIds, "zbw", "046644");
	//
	// billPayCODOfflineService.doWithExportBillCOD(entityIds, "zbw",
	// "046644", String.valueOf(batchNumber++));
	// }
	// }

	/**
	 * 
	 * 测试处理线下汇代收货款给客户
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-23 下午4:37:22
	 
	@Test
	@Rollback(true)
	//@Ignore
	public void testDoWithExportBillCOD() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());
		
//		// 设置签收时间
//		List<BillPayableEntity> list = billPayableService.queryByWaybillNosAndOrgCodes(Arrays.asList(new String[]{entity.getWaybillNo()}),null, FossConstants.ACTIVE ,ConsumerTestUtil.getCurrentInfo());
//		BillPayableEntity billPayableEntity = list.get(0);
//		billPayableEntity.setSignDate(new Date());
//		billPayableService.updateBillPayableSignDateByConfirmIncome(list.get(0),  ConsumerTestUtil.getCurrentInfo());

		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entity.getId());

		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

		billPayCODOfflineService.doWithExportBillCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

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
		Assert.assertEquals(billPayables.get(0).getUnverifyAmount(),
				BigDecimal.ZERO);

		// 付款单
		List<String> payableBillNos = new ArrayList<String>();
		payableBillNos.add(billPayables.get(0).getPayableNo());
		List<BillPaymentEntity> billPayments = billPaymentService
				.queryBillPaymentBySourceBillNOs(
						payableBillNos,
						SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT,
						FossConstants.ACTIVE);

		Assert.assertNotNull(billPayments);
		Assert.assertEquals(billPayments.size(), 1);

		BillPaymentEntity billPayment = billPayments.get(0);
		Assert.assertEquals(billPayment.getBankBranchCode(),
				entity.getBankBranchCode());
		Assert.assertEquals(billPayment.getAmount(), entity.getCodAmount());

		List<BillWriteoffEntity> billWriteoffs = billWriteoffService
				.queryBillWriteoffByBeginOrEndNo(
						billPayment.getPaymentNo(),
						FossConstants.ACTIVE,
						SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		Assert.assertNotNull(billWriteoffs);
		Assert.assertEquals(billWriteoffs.size(), 1);
		Assert.assertEquals(billWriteoffs.get(0).getAmount(),
				billPayment.getAmount());
	}

	@Test
	public void testQueryBillCODPayable() {

		List<String> codTypes = new ArrayList<String>();
		codTypes.add("R1");
		codTypes.add("R3");

		String[] bankArray = { "中国工商银行" };

		List<CODDto> codPayable = billPayCODOfflineService.queryBillCODPayable(
				null, codTypes, Arrays.asList(bankArray), null,0,10,null,null);
		Assert.assertTrue(codPayable.size()>0);

	}
	*/
}
