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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/crmitf/server/ws/FossToCrmServiceTest.java
 * 
 * FILE NAME        	: FossToCrmServiceTest.java
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

import com.deppon.crm._interface.crmservice.CommException;
import com.deppon.crm._interface.crmservice.FossToCrmService;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.crm.client.BackFreightCheckRequest;
import com.deppon.foss.esb.crm.client.BackFreightCheckResponse;
import com.deppon.foss.module.settlement.crmitf.server.BaseTestCase;

public class FossToCrmServiceTest extends BaseTestCase {

	private final Logger logger = LogManager.getLogger(getClass());

	private static final String SERVICE_CODE = "ESB_FOSS2ESB_BACK_FREIGHT_CHECK";

	@Autowired
	private FossToCrmService fossToCrmService;

	/**
	 * 
	 * 退运费状态校验
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-14 下午1:49:06
	 */
	@Test
	@Ignore
	public void testBackFreightCheck() throws CommException {

		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode(SERVICE_CODE);
		Holder<ESBHeader> esbHeader = new Holder<ESBHeader>(header);

		BackFreightCheckRequest request = new BackFreightCheckRequest();
//		request.setWaybillNum("222222222");
//		request.setWaybillNum("134234563");
		request.setWaybillNum("11111");
		BackFreightCheckResponse response = fossToCrmService.backFreightCheck(
				request, esbHeader);

		logger.info(response.isCheckResult());
	}
}
