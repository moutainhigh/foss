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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/ElectronicInvoiceSendServiceTest.java
 * 
 * FILE NAME        	: ElectronicInvoiceSendServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.consumer.api.server.service.IElectronicInvoiceSendService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.ElectronicInvoiceDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;

/**
 * 电子发票信息发送给发票系统测试类
 * 
 * @author ibm-zhuwei
 * @date 2013-1-7 上午9:42:50
 */
public class ElectronicInvoiceSendServiceTest extends BaseTestCase {

	@Autowired
	private IElectronicInvoiceSendService electronicInvoiceSendService;
	
	@Test
	@Ignore
	@Rollback(false)
	public void testSendCODMessage() throws Exception {
		
		ElectronicInvoiceDto dto = new  ElectronicInvoiceDto();
		dto.setWayBillNo("111111111");
		dto.setBillingTime(new Date());
		dto.setCompanyCode("WX");
		dto.setCompanyStandCode("DP12123");
		dto.setBuyerName("name");
		dto.setBuyerPhone("2123123");
		dto.setAmountTotal(BigDecimal.TEN);
		dto.setBusinessType("01");
		electronicInvoiceSendService.electronicInvoiceSend(dto);
		
	}

}
