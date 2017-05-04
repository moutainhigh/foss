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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillPayCODFailServiceTest.java
 * 
 * FILE NAME        	: BillPayCODFailServiceTest.java
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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODConfirmService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODFailService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOfflineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AuditResultEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 测试代收货款失败审核
 * 
 * @author dp-zengbinwen
 * @date 2012-10-25 下午3:11:33
 */
public class BillPayCODFailServiceTest extends BaseTestCase {

	@Autowired
	private IBillPayCODFailService billPayCODFailService;

	@Autowired
	private IWaybillPickupService waybillPickupService;

	@Autowired
	private ICodCommonService codCommonService;

	@Autowired
	private IBillCODStateService billCODStateService;

	@Autowired
	private IBillPayCODOfflineService billPayCODOfflineService;

	@Autowired
	private IBillPayCODConfirmService billPayCODConfirmService;
	
	@Autowired
	private IBillPayableService billPayableService;

	/**
	 * 审核代收货款失败审核通过
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-25 下午3:14:35
	 
	@Test
	@Rollback(true)
	public void testProcessPaymentFailed() {

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

		// 资金部冻结
		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

		billPayCODOfflineService.doWithExportBillCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

		billPayCODConfirmService.updatePayCODOfflineFailure(entityIds,
				ConsumerTestUtil.getCurrentInfo(), "未知原因");

		// 处理失败审核通过
		billPayCODFailService.processPaymentFailed(entityIds,
				AuditResultEnum.PASSED, ConsumerTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(entity.getWaybillNo());

		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE);

		List<CODDto> dto = codCommonService.queryBillCODByWaybillNo(waybillNos,
				statuses, null);

		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.size(), 1);
		Assert.assertEquals(dto.get(0).getVerReceivableAmount(),
				BigDecimal.ZERO);
	}
	*/

}
