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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/SalesPayCODServceTest.java
 * 
 * FILE NAME        	: SalesPayCODServceTest.java
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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISalesPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;

/**
 * 
 * 营业部客户账号状态控制服务测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-5 下午3:16:29
 */
public class SalesPayCODServceTest extends BaseTestCase {

	@Autowired
	private IWaybillPickupService waybillPickupService;

	@Autowired
	private ISalesPayCODService salesPayCODService;

	/**
	 * 
	 * 代收货款始发申请查询服务测试
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午3:18:08
	 */
	@Test
	@Rollback(true)
	public void testQueryStartApplyCODService() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		List<String> statusList = new ArrayList<String>();
		statusList.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		List<CODDto> result = salesPayCODService.queryStartApplyBillCOD(ConsumerTestUtil.getCurrentInfo(),
				statusList, 0, 25);

		Assert.assertNotNull(result);
	}
}
