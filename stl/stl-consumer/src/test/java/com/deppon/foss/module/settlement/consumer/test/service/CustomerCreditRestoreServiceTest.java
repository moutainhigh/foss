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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/CustomerCreditRestoreServiceTest.java
 * 
 * FILE NAME        	: CustomerCreditRestoreServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRestoreService;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;

/**
 * 代收货款额度还原服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-17 下午4:11:43
 */
public class CustomerCreditRestoreServiceTest extends BaseTestCase {

	@Autowired
	private ICustomerCreditRestoreService customerCreditRestoreService;

	/**
	 * 测试同步客户
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午4:13:13
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testSynCustomer() {
		Date current = new Date();
		Date lastDate = customerCreditRestoreService
				.getLastExecuteTime(current);

		this.customerCreditRestoreService.syncCustomer(lastDate, current);
	}

	/**
	 * 
	 * 测试同步组织
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午4:13:39
	 */
	@Test
	@Ignore
	public void testSynOrg() {
		this.customerCreditRestoreService.syncOrgBusiness();
	}

	/**
	 * 还原客户可用、已用额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午4:20:22
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void restoreCredit() {
		try {

			Date current = new Date();
			Date lastDate = customerCreditRestoreService
					.getLastExecuteTime(current);

			this.customerCreditRestoreService.restoreCredit(lastDate, current);

		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setCustomerCreditRestoreService(
			ICustomerCreditRestoreService customerCreditRestoreService) {
		this.customerCreditRestoreService = customerCreditRestoreService;
	}
}
