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
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/service/SalesPayCODServceTest.java
 * 
 * FILE NAME        	: SalesPayCODServceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISalesPayCODService;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;

/**
 * 
 * 营业部客户账号状态控制服务测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-5 下午3:16:29
 */
public class SalesPayCODServceTest extends BaseTestCase {

	private static final Logger logger = LogManager
			.getLogger(SalesPayCODServceTest.class);

	@Autowired
	private ISalesPayCODService salesPayCODService;

	/**
	 * 
	 * 查询客户银行账号信息
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-9 下午3:07:08
	 */
	@Test
	public void testQueryPayableBankAccount() {
		String customerCode = "0004";
		List<CusAccountEntity> accountList = salesPayCODService
				.getBillPayCODBankAccounts(customerCode);
		logger.info(accountList);
	}
}
