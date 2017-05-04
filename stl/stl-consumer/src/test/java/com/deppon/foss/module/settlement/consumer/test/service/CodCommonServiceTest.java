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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/CodCommonServiceTest.java
 * 
 * FILE NAME        	: CodCommonServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;

/**
 * 代收货款测试
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-28 下午4:36:24
 */
public class CodCommonServiceTest extends BaseTestCase {

	@Autowired
	private ICodCommonService codCommonService;

	@Autowired
	private IWaybillPickupService waybillPickupService;

	/**
	 * 测试按单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-28 下午4:40:15
	 */
	@Test
	public void testQueryByWaybill() {
		// 最基本的测试
		Assert.assertNull(this.codCommonService.queryByWaybill("test"));
	}
	
	/**
	 * 测试检查运单对应的代收货款是否已付款（冻结之后）
	 * 
	 * @author guxinhua
	 * @date 2012-11-28 下午4:40:15
	 */
	@Test
	public void testCheckCodHasPaymentByWaybillNo() {
		Assert.assertTrue(this.codCommonService.checkCodHasPaymentByWaybillNo("73000006", "LS"));
	}

	/**
	 * 
	 * 测试：按单号查询可支付的代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-21 上午11:46:06
	 */
	@Test
	public void testQueryBillCODPayableByWaybillNos() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		Assert.assertNotNull(entity);

		List<CODDto> results = codCommonService
				.queryBillCODPayableByWaybillNos(Arrays
						.asList(new String[] { waybillDto.getWaybillNo() }),null,null);

		Assert.assertNotNull(results);
		Assert.assertEquals(results.size(), 0);
	}

	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

}
