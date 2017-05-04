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
 * PROJECT NAME	: stl-finance-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/financeitf/FossFinmanagerServiceTest.java
 * 
 * FILE NAME        	: FossFinmanagerServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.financeitf;


import javax.xml.ws.Holder;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.finance.QueryInvoiceAmountRequestType;
import com.deppon.foss.inteface.finmanager.CommonException;
import com.deppon.foss.inteface.finmanager.FossFinmanagerService;

/**
 * WEB SERVICE客户端测试类
 * 
 * @author ibm-guxinhua
 * @date 2012-11-9 下午3:12:09
 */
@ContextConfiguration(locations = { "classpath:com/deppon/foss/module/settlement/finance/test/META-INF/spring.xml" })
public class FossFinmanagerServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FossFinmanagerService fossFinmanagerService;

	@Test
	@Ignore
	public void testQueryInvoiceAmount() {

		logger.info("calling...");
		try {
			Holder<ESBHeader> esbHeader=new Holder<ESBHeader>();
			QueryInvoiceAmountRequestType request = new QueryInvoiceAmountRequestType();
			fossFinmanagerService.queryInvoiceAmount(request,esbHeader);
		
		} catch (CommonException e) {
			logger.info(e.getMessage(), e);
		}

		logger.info("end...");
	}


}
