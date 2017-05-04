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
 * PROJECT NAME	: stl-crm-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/crmitf/server/ws/StlCustomerServiceTest.java
 * 
 * FILE NAME        	: StlCustomerServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.crmitf.server.ws;

import javax.xml.ws.Holder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.customerservice.CommonException;
import com.deppon.foss.customerservice.CustomerService;
import com.deppon.foss.esb.crm.server.order.ServiceChargeStatusUpdateRequest;
import com.deppon.foss.esb.crm.server.order.ServiceChargeStatusUpdateResponse;
import com.deppon.foss.module.settlement.crmitf.server.BaseTestCase;

public class StlCustomerServiceTest extends BaseTestCase {

	private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private CustomerService stlCustomerService;

	private static final String SERVICE_CODE = "ESB_CRM2ESB_RECIEVE_CUSTOMERINFOETC";

	/**
	 * 
	 * 装卸费状态修改测试
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-14 下午1:48:11
	 */
	@Test
	@Ignore
	public void testUpdateServiceChargeStatus() throws CommonException {

		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode(SERVICE_CODE);
		Holder<ESBHeader> esbHeader = new Holder<ESBHeader>(header);

		ServiceChargeStatusUpdateRequest request = new ServiceChargeStatusUpdateRequest();
		request.setWaybillNumber("655343607");
		request.setStatus(false);

		ServiceChargeStatusUpdateResponse response = stlCustomerService
				.updateServiceChargeStatus(esbHeader, request);

		logger.info(response.isResult());
		logger.info(response.getReason());

	}

}
