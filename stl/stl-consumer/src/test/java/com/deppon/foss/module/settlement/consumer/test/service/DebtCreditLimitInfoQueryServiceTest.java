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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/DebtCreditLimitInfoQueryServiceTest.java
 * 
 * FILE NAME        	: DebtCreditLimitInfoQueryServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.consumer.api.server.service.IDebtCreditLimitInfoQueryService;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;

/**
 * 查询最早欠款及已用信用额度的客户信息Service测试类
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 4:46:07 PM
 */
public class DebtCreditLimitInfoQueryServiceTest extends BaseTestCase{

	//查询最早欠款及已用信用额度的客户信息Service
	@Autowired
	private IDebtCreditLimitInfoQueryService debtCreditLimitInfoQueryService;
	
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * 测试最早欠款及已用信用额度的客户信息Service方法
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 15, 2013 4:49:08 PM
	 */
	@Test
	@Rollback(true)
	public void testProcess() {
		//日志打印
		this.logger.info("begin test ...");
		
		//查询最早欠款及已用信用额度的客户信息Service启动
		debtCreditLimitInfoQueryService.process();
		
		//test结束
		this.logger.info("end test ...");
	}
}
