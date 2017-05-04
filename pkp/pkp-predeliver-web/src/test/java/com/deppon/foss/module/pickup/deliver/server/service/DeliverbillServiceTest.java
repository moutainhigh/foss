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
 * PROJECT NAME	: pkp-deliver-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/deliver/server/service/DeliverbillServiceTest.java
 * 
 * FILE NAME        	: DeliverbillServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.deliver.server.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.pickup.deliver.server.BaseTestCase;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;

/**
 * 
 * DeliverbillService测试类
 * 
 * @author ibm-wangxiexu
 * @date 2012-12-26 下午9:58:03
 */
public class DeliverbillServiceTest extends BaseTestCase {
	@Autowired
	private IDeliverbillService deliverbillService;

	@Test
	public void testQueryDeliveryInfoByDeliverbillNo() {
		// String deliverbillNo = "test_deliver_no_002";
		String deliverbillNo = null;

		DeliverbillDto deliverbillDto = this.deliverbillService
				.queryDeliveryInfoByDeliverbillNo(deliverbillNo);
		Assert.assertNull(deliverbillDto);
	}

	@Test
	public void testQueryWaybillDeliveryListByWaybillNo() {
		String waybillNo = "2012117";

		List<WaybillDeliveryDto> waybillDeliveryDtoList = this.deliverbillService
				.queryWaybillDeliveryListByWaybillNo(waybillNo);

		Assert.assertNotNull(waybillDeliveryDtoList);
	}
}