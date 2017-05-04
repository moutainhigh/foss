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
 * PROJECT NAME	: stl-bho-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/bho/test/AccountServiceTest.java
 * 
 * FILE NAME        	: AccountServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.bho.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.accounting.GetAccountStatementDetailRequest;
import com.deppon.esb.inteface.domain.accounting.GetRefundRequest;
import com.deppon.foss.accountservice.AccountService;
import com.deppon.foss.accountservice.CommonException;

/**
 * WEB SERVICE客户端测试类
 * 
 * @author ibm-zhuwei
 * @date 2012-11-9 下午3:12:09
 */
@ContextConfiguration(locations = { "classpath:com/deppon/foss/module/settlement/bho/test/META-INF/spring.xml" })
public class AccountServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountService accountService;

	@Test
	@Ignore
	public void testGetAccountDetailInfo() {

		logger.info("calling...");
		Holder<ESBHeader> esbHeader=new Holder<ESBHeader>();
		try {
			GetAccountStatementDetailRequest request = new GetAccountStatementDetailRequest();
			request.setAccountStatementNo("abc");
			accountService.getAccountDetailInfo(esbHeader,request);
		} catch (CommonException e) {
			logger.info(e.getMessage(), e);
		}

		logger.info("end...");
	}

	@Test
	@Ignore
	public void testGetRefundInfo() {
		logger.info("calling ... ");
		Holder<ESBHeader> esbHeader=new Holder<ESBHeader>();
		try {
			GetRefundRequest request = new GetRefundRequest();

			List<String> waybillNos = request.getWayBillNo();
			waybillNos.add("1352709465187");
			waybillNos.add("1352709465281");
			waybillNos.add("1352709465390");
			waybillNos.add("1352709465500");
			waybillNos.add("1352709465671");
			waybillNos.add("1352709465843");
			waybillNos.add("1352709466015");
			waybillNos.add("1352709466171");
			waybillNos.add("1352709466328");

			request.setPageNo(1);
			request.setPageNo(3);
			accountService.getRefundInfo(esbHeader,request);
		} catch (CommonException e) {
			logger.error(e.getMessage(), e);
		}

		logger.info("end.");
	}

}
